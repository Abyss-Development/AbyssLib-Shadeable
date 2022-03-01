package net.abyssdev.abysslib.item;

import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Data;
import lombok.experimental.Accessors;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.text.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Chubbyduck1
 */

@Data
@Accessors(chain = true)
public class ItemBuilder implements Cloneable {

    private final String material, displayName, skull, potionType;
    private final List<String> lore;
    private final boolean enchanted, hideItemFlags;
    private final int color, customModelData;
    private final short data;

    public ItemBuilder(FileConfiguration config, String path) {
        this.material = config.getString(path + ".material");
        this.displayName = config.getString(path + ".name");
        this.skull = config.getString(path + ".skull");
        this.lore = config.getStringList(path + ".lore");
        this.enchanted = config.getBoolean(path + ".enchanted");
        this.hideItemFlags = config.getBoolean(path + ".hide-flags");
        this.potionType = config.getString(path + ".potion-type");
        this.data = (short) config.getInt(path + ".data");
        this.color = config.getInt(path + ".color", 999999999);
        this.customModelData = config.getInt(path + ".Custom-Model-Data", 0);
    }

    public ItemStack parse() {
        return parse(new PlaceholderReplacer());
    }

    public ItemStack parse(final PlaceholderReplacer placeholders) {
        final Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(this.material);
        if (!xMaterial.isPresent()) {
            throw new NullPointerException("The material cannot be null");
        }

        ItemStack itemStack = xMaterial.get().parseItem();

        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Color.parse(placeholders.parse(this.displayName)));
        itemMeta.setLore(Color.parse(placeholders.parse(new ArrayList<>(this.lore))));

        if (this.enchanted) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (this.hideItemFlags) {
            itemMeta.addItemFlags(ItemFlag.values());
        }

        itemStack.setItemMeta(itemMeta);

        if (itemStack.getType().name().contains("LEATHER_") && this.color != 999999999) {
            final LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
            meta.setColor(org.bukkit.Color.fromRGB(this.color));
            itemStack.setItemMeta(meta);
        }

        if (this.enchanted) {
            itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }

        itemStack = parseSkull(itemStack, placeholders);
        itemStack = applyPotionMeta(itemStack);
        itemStack = applyCustomModelData(itemStack);

        return itemStack;
    }

    private boolean isSkull() {
        return this.material.toUpperCase().startsWith("SKULL_ITEM") || this.material.toUpperCase().startsWith("PLAYER_HEAD");
    }

    private ItemStack parseSkull(final ItemStack item, final PlaceholderReplacer placeholders) {
        if (!this.isSkull()) {
            return item;
        }

        final SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        final String owner = placeholders.parse(this.skull);

        if (owner.length() <= 16) {
            itemMeta.setOwner(owner);

            item.setItemMeta(itemMeta);
            return item;
        }

        final GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", owner));

        final Field profileField;

        try {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        item.setItemMeta(itemMeta);
        return item;
    }

    private ItemStack applyPotionMeta(final ItemStack itemStack) {
        try {
            if (this.potionType == null || this.potionType.isEmpty()) {
                return itemStack;
            }

            final PotionType potionBase = PotionType.valueOf(this.potionType.toUpperCase());
            final PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();

            Objects.requireNonNull(potionMeta).setBasePotionData(new PotionData(potionBase));

            itemStack.setItemMeta(potionMeta);
        } catch (final Exception ignored) {}
        return itemStack;
    }

    private ItemStack applyCustomModelData(final ItemStack itemStack) {
        try {
            final ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setCustomModelData(this.customModelData);
            itemStack.setItemMeta(itemMeta);
        } catch(final NoSuchMethodError ignored) {}

        return itemStack;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}