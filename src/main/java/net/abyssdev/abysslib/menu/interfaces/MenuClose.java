package net.abyssdev.abysslib.menu.interfaces;

import net.abyssdev.abysslib.menu.MenuBuilder;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * The {@link MenuClose} interface
 */

@FunctionalInterface
public interface MenuClose {

    /**
     * Called when a {@link org.bukkit.entity.Player} closes the {@link MenuBuilder}
     *
     * @param event The {@link InventoryCloseEvent}
     */
    void onClose(final InventoryCloseEvent event);

}
