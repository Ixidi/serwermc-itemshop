package pl.serwermc.itemshop.util

import org.bukkit.plugin.Plugin

fun <T> Plugin.runAsync(action: () -> T, syncCallback: (T) -> Unit, syncError: (Throwable) -> Unit) {
    val run: () -> Unit = {

        val sync: () -> Unit = try {
            val t = action(); { syncCallback(t) }
        } catch (throwable: Throwable) {
            { syncError(throwable) }
        }

        this.server.scheduler.runTask(this, sync)
    }

    this.server.scheduler.runTaskAsynchronously(this, run)
}

fun Plugin.runAsync(action: () -> Unit, syncError: (Throwable) -> Unit) {
    val run: () -> Unit = {

        try {
            action()
        } catch (throwable: Throwable) {
            val error: () -> Unit = { syncError(throwable) }
            this.server.scheduler.runTask(this, error)
        }

    }

    this.server.scheduler.runTaskAsynchronously(this, run)
}