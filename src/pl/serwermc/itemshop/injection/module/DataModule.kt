package pl.serwermc.itemshop.injection.module

import com.mysql.cj.jdbc.MysqlDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module
import pl.serwermc.itemshop.data.Configuration
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.bukkit.plugin.Plugin
import pl.serwermc.itemshop.token.DatabaseTokens
import pl.serwermc.itemshop.token.Tokens
import pl.serwermc.itemshop.token.TokensTable
import java.io.File

val dataModule = module {

    single { ObjectMapper(YAMLFactory()).apply { registerModule(KotlinModule()) } }

    single {
        val plugin = get<Plugin>()
        if (!plugin.dataFolder.exists()) plugin.dataFolder.mkdirs()

        val file = File(plugin.dataFolder, "config.yml")
        if (!file.exists()) plugin.saveResource("config.yml", true)

        get<ObjectMapper>().readValue(file, Configuration::class.java)
    }

    single { TokensTable(get<Configuration>().mysql.table) }

    single(createdAtStart = true) {
        get<Configuration>().mysql.let { config ->
            Database.connect(
                MysqlDataSource().apply {
                    serverName = config.host
                    port = config.port ?: 3306
                    user = config.user
                    if (config.password.isNotBlank()) password = config.password
                    useSSL = false
                    serverTimezone = "UTC"
                    databaseName = config.database
                }
            ).apply {
                transaction(this) {
                    SchemaUtils.createMissingTablesAndColumns(get<TokensTable>())
                }
            }
        }
    }

    single<Tokens> { DatabaseTokens(get(), get()) }

}