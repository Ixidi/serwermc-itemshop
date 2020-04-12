package pl.serwermc.itemshop.injection.module

import org.bukkit.plugin.Plugin
import org.koin.dsl.module
import pl.serwermc.itemshop.command.Commands
import pl.serwermc.itemshop.command.ShopCommand
import pl.serwermc.itemshop.command.TokensCommand
import pl.serwermc.itemshop.data.Configuration
import pl.serwermc.itemshop.gui.GuiListener
import pl.serwermc.itemshop.gui.GuiManager
import pl.serwermc.itemshop.listener.Listeners
import pl.serwermc.itemshop.shop.ConfigurationShopGuiFactory
import pl.serwermc.itemshop.shop.ShopGuiFactory

fun pluginModule(plugin: Plugin) = module {
    single { plugin }
    single { plugin.server }

    single { GuiManager() }
    single<ShopGuiFactory> { ConfigurationShopGuiFactory(get(), get(), get(), get()) }

    single { TokensCommand(get<Configuration>().cooldown.tokensCommand, get(), get()) }
    single { ShopCommand(get<Configuration>().cooldown.shopCommand, get(), get()) }
    single {
        Commands(
            "tokeny" to get<TokensCommand>(),
            "sklep" to get<ShopCommand>()
        )
    }

    single { GuiListener(get()) }
    single {
        Listeners(
            get<GuiListener>()
        )
    }
}