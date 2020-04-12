package pl.serwermc.itemshop.shop.offer

import pl.serwermc.itemshop.data.Configuration
import pl.serwermc.itemshop.util.Mapper
import pl.serwermc.itemshop.util.map

class ConfigurationOffers(
    configuration: Configuration,
    mapper: Mapper<Configuration.Offer, Offer>
) : Offers {

    private val offerList = configuration.offers.values.map { it.map(mapper) }

    override fun getAll(): List<Offer> = offerList.toList()

}