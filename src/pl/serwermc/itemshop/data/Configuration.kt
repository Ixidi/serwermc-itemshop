package pl.serwermc.itemshop.data

data class Configuration(
    val mysql: MySQL,
    val messages: Map<String, String>,
    val cooldown: Cooldown
) {

    data class Cooldown(
        val tokensCommand: Long
    )

    data class MySQL(
        val host: String,
        val port: Int?,
        val user: String,
        val password: String,
        val database: String,
        val table: String
    )

}