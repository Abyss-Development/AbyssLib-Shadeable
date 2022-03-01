package net.abyssdev.abysslib.menu;

import lombok.AllArgsConstructor;
import net.abyssdev.abysslib.item.ItemBuilder;
import net.abyssdev.abysslib.menu.interfaces.MenuClick;
import net.abyssdev.abysslib.menu.interfaces.MenuClose;
import net.abyssdev.abysslib.menu.item.BorderItem;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.scheduler.AbyssScheduler;
import net.abyssdev.abysslib.text.Color;
import net.abyssdev.abysslib.utils.ImmutablePair;
import net.abyssdev.abysslib.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * The main MenuBuilder system
 */
@SuppressWarnings("unused") // Library - OFC it's unused
@AllArgsConstructor
public class MenuBuilder implements InventoryHolder {

    private String title;
    private int size;

    private final Map<Integer, ItemBuilder> itemBuilderMap;
    private final Map<Integer, ItemStack> itemStackMap, borderMap;
    private final Map<Integer, Set<MenuClick>> menuClickMap;
    private final Set<MenuClose> menuCloseSet;
    private final Set<Integer> currentlyTemp;
    private Inventory inventory;

    /**
     * Construct a new {@link MenuBuilder}
     */
    public MenuBuilder() {
        this.title = "&8&lNO TITLE";
        this.size = 54;

        this.itemBuilderMap = new HashMap<>();
        this.itemStackMap = new HashMap<>();
        this.borderMap = new HashMap<>();
        this.menuClickMap = new HashMap<>();
        this.menuCloseSet = new HashSet<>();
        this.currentlyTemp = new HashSet<>();
    }

    /**
     * Construct a new {@link MenuBuilder} with a title, and size
     *
     * @param title The title of the inventory
     * @param size  The size of the inventory
     */
    public MenuBuilder(final String title, final int size) {
        this.title = title;
        this.size = size;

        this.itemBuilderMap = new HashMap<>();
        this.menuClickMap = new HashMap<>();
        this.itemStackMap = new HashMap<>();
        this.borderMap = new HashMap<>();
        this.menuCloseSet = new HashSet<>();
        this.currentlyTemp = new HashSet<>();
    }

    /**
     * Construct a new {@link MenuBuilder} with a {@link FileConfiguration} to load from
     *
     * @param config The {@link FileConfiguration} to load from
     */
    public MenuBuilder(final FileConfiguration config) {
        this.title = config.getString("Title");
        this.size = config.getInt("Size");

        this.itemBuilderMap = new HashMap<>();
        this.menuClickMap = new HashMap<>();
        this.itemStackMap = new HashMap<>();
        this.borderMap = new HashMap<>();
        this.menuCloseSet = new HashSet<>();
        this.currentlyTemp = new HashSet<>();

        this.loadItems(config);
    }

    /**
     * Load items from a {@link FileConfiguration} section
     *
     * @param config The {@link FileConfiguration}
     */
    private void loadItems(final FileConfiguration config) {
        if (!config.isConfigurationSection("Items")) {
            return;
        }

        final ConfigurationSection configSection = config.getConfigurationSection("Items");

        if (configSection == null) {
            return;
        }

        for (final String key : configSection.getKeys(false)) {
            final String itemPath = "Items" + "." + key;

            final ItemBuilder itemBuilder = new ItemBuilder(config, itemPath + ".Display-Item");
            final List<Integer> slots = Utils.getSlots(config.getStringList(itemPath + ".Slots"));

            for (final int slot : slots) {
                this.itemBuilderMap.put(slot, itemBuilder);
            }
        }
    }

    /**
     * Set an {@link ItemBuilder} at a slot
     *
     * @param slot        The slot
     * @param itemBuilder The ItemBuilder
     */
    public void setItem(final int slot, final ItemBuilder itemBuilder) {
        this.itemBuilderMap.put(slot, itemBuilder);
    }

    /**
     * Set an {@link ItemStack} at a slot
     *
     * @param slot      The slot
     * @param itemStack The ItemStack
     */
    public void setItem(final int slot, final ItemStack itemStack) {
        this.itemStackMap.put(slot, itemStack);
    }

    /**
     * Set an {@link ItemStack} as a temporary item on click.
     *
     * @param slot     The slot in which to add the temp item
     * @param temp     The ItemStack to set temporarily
     * @param duration The duration on how long it will be set each click
     */
    public void setTempItem(final int slot, final ItemStack temp, final int duration) {
        final Set<MenuClick> clicks = this.getMenuClick(slot);

        clicks.add(event -> {
            if (this.inventory == null) {
                this.build();
                this.setTempItem(slot, temp, duration);
                return;
            }

            if (this.currentlyTemp.contains(slot)) {
                return;
            }

            final ItemStack oldItem = this.inventory.getItem(slot);

            this.inventory.setItem(slot, temp);
            this.currentlyTemp.add(slot);

            AbyssScheduler.sync().runLater(() -> {
                this.inventory.setItem(slot, oldItem);
                this.currentlyTemp.remove(slot);
            }, duration);
        });

        this.menuClickMap.put(slot, clicks);
    }

    /**
     * Set a {@link MenuClick}
     *
     * @param slot      The slot to set it for
     * @param menuClick The {@link MenuClick} to set
     */
    public void addClickEvent(final int slot, final MenuClick menuClick) {
        final Set<MenuClick> clicks = this.menuClickMap.getOrDefault(slot, new HashSet<>());
        clicks.add(menuClick);
        this.menuClickMap.put(slot, clicks);
    }

    /**
     * Get a {@link MenuClick} at a slot
     *
     * @param slot The slot to get
     * @return The {@link MenuClick} retrieved
     */
    public Set<MenuClick> getMenuClick(final int slot) {
        return this.menuClickMap.getOrDefault(slot, new HashSet<>());
    }

    /**
     * Build the GUI with placeholders
     *
     * @return The built {@link Inventory}
     */
    public Inventory build() {
        return this.build(new PlaceholderReplacer());
    }

    /**
     * Build the GUI with placeholders
     *
     * @param replacer The placeholders to build with
     * @return The built {@link Inventory}
     */
    public Inventory build(final PlaceholderReplacer replacer) {
        final Inventory inventory = this.inventory == null
                ? Bukkit.createInventory(this, this.size, replacer.parse(this.title))
                : this.inventory;

        for (final Map.Entry<Integer, ItemStack> itemEntry : this.borderMap.entrySet()) {
            if (itemEntry.getKey() > inventory.getSize()) {
                continue;
            }

            inventory.setItem(itemEntry.getKey(), itemEntry.getValue());
        }

        for (final Map.Entry<Integer, ItemBuilder> itemEntry : this.itemBuilderMap.entrySet()) {
            inventory.setItem(itemEntry.getKey(), itemEntry.getValue().parse(replacer));
        }

        for (final Map.Entry<Integer, ItemStack> itemEntry : this.itemStackMap.entrySet()) {
            if (itemEntry.getKey() > inventory.getSize()) {
                continue;
            }

            final ItemStack item = itemEntry.getValue();
            final ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(replacer.parse(meta.getDisplayName()));
            }

            if (meta != null && meta.getLore() != null) {
                meta.setLore(replacer.parse(meta.getLore()));
            }

            item.setItemMeta(meta);
            inventory.setItem(itemEntry.getKey(), item);
        }

        this.inventory = inventory;
        return inventory;
    }

    public Inventory getInventory(final PlaceholderReplacer replacer) {
        return this.build(replacer);
    }

    @SuppressWarnings("all")
    @NotNull
    @Override
    public Inventory getInventory() {
        return this.build();
    }

    /**
     * Create a clone of the {@link MenuBuilder}
     *
     * @return The {@link MenuBuilder} clone
     */
    public MenuBuilder createClone() {
        return new MenuBuilder(
                this.title,
                this.size,
                new HashMap<>(this.itemBuilderMap),
                new HashMap<>(this.itemStackMap),
                new HashMap<>(this.borderMap),
                new HashMap<>(this.menuClickMap),
                new HashSet<>(this.menuCloseSet),
                new HashSet<>(this.currentlyTemp),
                null);
    }

    /**
     * @param title Sets the title in the Menu Builder
     * @return returns {@link MenuBuilder}
     */
    public MenuBuilder title(final String title) {
        this.title = Color.parse(title);
        return this;
    }


    /**
     * @param size Sets the size in the Menu Builder
     * @return returns {@link MenuBuilder}
     */
    public MenuBuilder size(final int size) {
        this.size = size;
        return this;
    }

    /**
     * @param event sets the {@link MenuClose} event.
     */
    public void onClose(final InventoryCloseEvent event) {
        for (final MenuClose menuClose : this.menuCloseSet) {
            menuClose.onClose(event);
        }
    }

    /**
     * Set {@link BorderItem}s
     *
     * @param borders The {@link BorderItem}s
     */
    public void setBorders(final BorderItem... borders) {
        for (final BorderItem borderItem : borders) {
            for (final ImmutablePair<Integer, Integer> pair : borderItem.getMinMax()) {
                for (int i = pair.getKey(); i <= pair.getValue(); i++) {
                    this.borderMap.put(i, borderItem.getBorderItem());
                }
            }
        }
    }

    /**
     * Get the {@link Set} of {@link MenuClose}s
     *
     * @return returns {@link Set<MenuClose>}
     */
    protected Set<MenuClose> getMenuCloseSet() {
        return this.menuCloseSet;
    }

}
