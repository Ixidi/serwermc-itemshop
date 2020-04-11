package pl.serwermc.itemshop.token

import org.jetbrains.exposed.sql.Table

class TokensTable(tableName: String) : Table(tableName) {

    val name = varchar("name", 20)
    val tokens = integer("tokens")

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(name)

}