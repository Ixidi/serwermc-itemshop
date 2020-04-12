package pl.serwermc.itemshop.token

interface Tokens {

    class AccountMissingException(val name: String): Exception("Account $name is missing.")
    class NotEnoughTokensException(val value: Int) : Exception("$value is bigger than player's current tokens.")
    class MaxTokensValueException(val value: Long) : Exception("$value is bigger than max tokens value ${Int.MAX_VALUE}.")
    class NegativeValueException(val value: Int) : Exception("Value $value is not positive.")

    fun hasAccount(name: String): Boolean

    fun hasEnough(name: String, value: Int): Boolean

    fun getTokens(name: String, createIfNotExist: Boolean = true): Int

    fun setTokens(name: String, value: Int)

    fun addTokens(name: String, value: Int)

    fun removeTokens(name: String, value: Int)

}