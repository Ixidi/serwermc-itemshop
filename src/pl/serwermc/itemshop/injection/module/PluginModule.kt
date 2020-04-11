package pl.serwermc.itemshop.injection.module

import org.bukkit.plugin.Plugin
import org.koin.dsl.module
import pl.serwermc.itemshop.command.Commands
import pl.serwermc.itemshop.command.TokensCommand
import pl.serwermc.itemshop.data.Configuration

fun pluginModule(plugin: Plugin) = module {
    single { plugin }
    single { plugin.server }

    single { TokensCommand(get<Configuration>().cooldown.tokensCommand, get(), get()) }

    single {
        Commands(
            "tokeny" to get<TokensCommand>()
        )
    }
}