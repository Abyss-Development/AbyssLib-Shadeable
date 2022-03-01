package net.abyssdev.abysslib.menu.interfaces;

import gg.optimalgames.library.menu.MenuBuilder;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * The {@link MenuClose} interface
 */
public interface MenuClose {

    /**
     * Called when a {@link org.bukkit.entity.Player} closes the {@link MenuBuilder}
     *
     * @param event The {@link InventoryCloseEvent}
     */
    void onClose(final InventoryCloseEvent event);

}
