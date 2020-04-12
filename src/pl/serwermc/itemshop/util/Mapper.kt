package pl.serwermc.itemshop.util

interface Mapper<FROM, TO> {

    fun FROM.map() : TO

}

fun <FROM, TO> FROM.map(mapper: Mapper<FROM, TO>) = mapper.run { map() }