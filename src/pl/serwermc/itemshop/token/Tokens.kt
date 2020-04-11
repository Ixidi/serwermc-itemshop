package pl.serwermc.itemshop.token

interface Tokens {

    class AccountMissingException(val name: String): Exception("Account $name is missing.")

    fun hasAccount(name: String): Boolean


    fun hasEnough(name: String, value: Int): Boolean

    @Throws(AccountMissingException::class)
    fun getTokens(name: String, createIfNotExist: Boolean = true): Int

    fun setTokens(name: String, value: Int)

    fun addTokens(name: String, value: Int)

    fun removeTokens(name: String, value: Int)

}