package net.abyssdev.abysslib.menu.interfaces;

import net.abyssdev.abysslib.menu.MenuBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * The interface for Menu Clicks
 */

@FunctionalInterface
public interface MenuClick {

    /**
     * Called when a {@link org.bukkit.entity.Player} clicks the {@link MenuBuilder}
     *
     * @param event The {@link InventoryClickEvent}
     */
    void onClick(final InventoryClickEvent event);

}
