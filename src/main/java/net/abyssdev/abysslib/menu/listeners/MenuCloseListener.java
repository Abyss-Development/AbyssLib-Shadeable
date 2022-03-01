package net.abyssdev.abysslib.menu.listeners;

import net.abyssdev.abysslib.menu.MenuBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Called for {@link MenuBuilder} closes
 */
public class MenuCloseListener implements Listener {

    /**
     * The event which is called when the inventory is closed
     *
     * @param event The {@link InventoryCloseEvent}
     */
    @EventHandler
    public void onClose(final InventoryCloseEvent event) {
        final Inventory inventory = event.getInventory();
        final InventoryHolder inventoryHolder = inventory.getHolder();

        if (inventoryHolder == null) {
            return;
        }

        if (!(inventoryHolder instanceof MenuBuilder)) {
            return;
        }

        final MenuBuilder menuBuilder = (MenuBuilder) inventoryHolder;
        menuBuilder.onClose(event);
    }

}
