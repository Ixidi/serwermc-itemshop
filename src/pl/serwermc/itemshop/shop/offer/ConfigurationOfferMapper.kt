package pl.serwermc.itemshop.shop.offer

import org.bukkit.Material
import pl.serwermc.itemshop.data.Configuration
import pl.serwermc.itemshop.util.Mapper
import pl.serwermc.itemshop.util.color
import pl.serwermc.itemshop.util.itemStack

class ConfigurationOfferMapper(
    private val appendLore: String = ""
) : Mapper<Configuration.Offer, Offer> {

    class MapperException(message: String) : Exception(message)

    override fun Configuration.Offer.map() = Offer(
        name = name.color(),
        cost = run {
            if (cost < 0) throw MapperException(
                "Cost must be bigger than or equal to zero."
            )
            cost
        },
        commands = commands,
        guiSlot = run {
            if (guiSlot < 0) throw MapperException(
                "Gui slot must be bigger than or equal to zero."
            )
            guiSlot
        },
        itemStack = item.run {
            itemStack(
                material = Material.matchMaterial(material.toUpperCase().replace(" ", "_"))
                    ?: throw MapperException(
                        "Unknown material $material."
                    ),
                amount = amount,
                name = name,
                lore = lore.split("\n").let {
                    if (appendLore.isNotBlank()) {
                        return@let listOf(appendLore.replace("{tokens}", "$cost")).plus(it)
                    }
                    return@let it
                }
            )
        }
    )

}