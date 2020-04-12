package pl.serwermc.itemshop.gui

import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import pl.serwermc.itemshop.util.color

abstract class Gui(
    rows: Int,
    title: String
) {

    private val size = (if (rows > 6) 6 else if (rows <= 0) 1 else rows) * 9
    private val inventory = Bukkit.createInventory(null, size, title.color())
    private var init = false

    private val slotMap = HashMap<Int, GuiAction>()

    fun open(humanEntity: HumanEntity) {
        if (!init) {
            init = true
            initialize()
        }
        humanEntity.openInventory(inventory)
    }

    fun handleClick(inventoryClickEvent: InventoryClickEvent) {
        inventoryClickEvent.isCancelled = true

        val action = slotMap[inventoryClickEvent.slot] ?: GuiAction.NOTHING
        action.handle(inventoryClickEvent)
    }

    protected fun fill(itemStack: ItemStack, action: GuiAction) {
        for (slot in 0 until size) {
            setSlot(slot, itemStack, action)
        }
    }

    protected fun clear() {
        inventory.clear()
        slotMap.clear()
    }

    protected fun setSlot(slot: Int, itemStack: ItemStack, action: GuiAction) {
        if (slot < 0 || slot >= size) return

        slotMap[slot] = action
        inventory.setItem(slot, itemStack)
    }

    protected abstract fun initialize()
}