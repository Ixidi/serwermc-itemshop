package pl.serwermc.itemshop.gui

import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import java.util.*
import kotlin.collections.HashMap

class GuiManager {

    private val openGuiMap = HashMap<UUID, Gui>()

    fun get(humanEntity: HumanEntity) = openGuiMap[humanEntity.uniqueId]

    fun open(humanEntity: HumanEntity, gui: Gui) {
        openGuiMap[humanEntity.uniqueId] = gui
        gui.open(humanEntity)
    }

    fun remove(humanEntity: HumanEntity) {
        openGuiMap.remove(humanEntity.uniqueId)
    }

    fun closeAll() {
        openGuiMap.forEach { (uuid, _) ->
            openGuiMap.remove(uuid)
            Bukkit.getPlayer(uuid)?.closeInventory()
        }
    }

}