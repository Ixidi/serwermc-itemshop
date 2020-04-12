package pl.serwermc.itemshop.data

data class Configuration(
    val mysql: MySQL,
    val messages: Map<String, String>,
    val cooldown: Cooldown,
    val offers: Map<String, Offer>,
    val gui: Gui,
    val loreAppender: LoreAppender
) {

    data class Cooldown(
        val tokensCommand: Long,
        val shopCommand: Long
    )

    data class MySQL(
        val host: String,
        val port: Int?,
        val user: String,
        val password: String,
        val database: String,
        val table: String
    )

    data class Offer(
        val name: String,
        val cost: Int,
        val commands: List<String>,
        val guiSlot: Int,
        val item: Item
    )

    data class Item(
        val material: String,
        val amount: Int,
        val name: String,
        val lore: String
    )

    data class LoreAppender(
        val enabled: Boolean,
        val text: String
    )

    data class Gui(
        val rows: Int,
        val title: String,
        val headItem: HeadItem,
        val loadingItem: Item
    ) {

        data class HeadItem(
            val guiSlot: Int,
            val amount: Int,
            val name: String,
            val lore: String
        )

    }
}