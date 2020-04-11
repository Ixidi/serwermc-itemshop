package pl.serwermc.itemshop.token

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class DatabaseTokens(
    private val database: Database,
    private val table: TokensTable
) : Tokens {

    class NegativeValueException(val value: Int) : Exception("Value $value is not positive.")
    class MaxTokensValueException(val value: Long) : Exception("$value is bigger than max tokens value ${Int.MAX_VALUE}.")
    class NotEnoughTokensException(val value: Int) : Exception("$value is bigger than player's current tokens.")

    override fun hasAccount(name: String): Boolean = table.select { table.name eq name }.singleOrNull() != null

    override fun hasEnough(name: String, value: Int): Boolean {
        if (value < 0) throw NegativeValueException(value)
        if (value == 0) return true

        return getTokens(name) >= value
    }

    override fun getTokens(name: String, createIfNotExist: Boolean): Int = transaction(database) {
        val row = table.select {
            table.name eq name
        }.singleOrNull()

        if (row != null) return@transaction row[table.tokens]

        if (!createIfNotExist) throw Tokens.AccountMissingException(name)

        table.insert {
            it[table.name] = name
            it[table.tokens] = 0
        }

        return@transaction 0
    }

    override fun setTokens(name: String, value: Int) {
        if (value < 0) throw NegativeValueException(value)

        val row = table.select { table.name eq name }.singleOrNull()
        if (row != null) {
            table.update({ table.name eq name }) {
                it[table.tokens] = value
            }
            return
        }

        table.insert {
            it[table.name] = name
            it[table.tokens] = value
        }
    }

    override fun addTokens(name: String, value: Int) {
        if (value < 0) throw NegativeValueException(value)
        if (value == 0) return

        val row = table.select { table.name eq name }.singleOrNull()
        if (row != null) {
            val tokens = row[table.tokens]
            val newValue = tokens.toLong() + value.toLong()
            if (newValue > Int.MAX_VALUE) throw MaxTokensValueException(newValue)

            table.update({ table.name eq name }) {
                it[table.tokens] = newValue.toInt()
            }
            return
        }

        table.insert {
            it[table.name] = name
            it[table.tokens] = value
        }
    }

    override fun removeTokens(name: String, value: Int) {
        if (value < 0) throw NegativeValueException(value)
        if (value == 0) return

        val row = table.select { table.name eq name }.singleOrNull()
        if (row != null) {
            val tokens = row[table.tokens]
            val newValue = tokens - value
            if (newValue < 0) throw NotEnoughTokensException(value)

            table.update({ table.name eq name }) {
                it[table.tokens] = newValue
            }
            return
        }

        throw NotEnoughTokensException(value)
    }

}