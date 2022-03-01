package net.abyssdev.abysslib.menu.listeners;

import net.abyssdev.abysslib.menu.MenuBuilder;
import net.abyssdev.abysslib.menu.interfaces.MenuClick;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Set;

/**
 * Listens, and handles Menu Clicks
 */
public class MenuClickListener implements Listener {

    /**
     * The event for Menu Clicks
     *
     * @param event The {@link InventoryClickEvent}
     */
    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        final Inventory inventory = event.getInventory();
        final InventoryHolder inventoryHolder = inventory.getHolder();

        if (inventoryHolder == null) {
            return;
        }

        if (!(inventoryHolder instanceof MenuBuilder)) {
            return;
        }

        event.setCancelled(true);

        final MenuBuilder menuBuilder = (MenuBuilder) inventoryHolder;
        final Set<MenuClick> menuClicks = menuBuilder.getMenuClick(event.getSlot());

        if (menuClicks == null || menuClicks.isEmpty()) {
            return;
        }

        for (final MenuClick menuClick : menuClicks) {
            menuClick.onClick(event);
        }
    }

}
