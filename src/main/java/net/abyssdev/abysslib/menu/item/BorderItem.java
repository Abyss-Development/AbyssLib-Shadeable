package net.abyssdev.abysslib.menu.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.abyssdev.abysslib.utils.ImmutablePair;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

@Getter
@AllArgsConstructor
public class BorderItem {

    private final ItemStack borderItem;
    private final Set<ImmutablePair<Integer, Integer>> minMax;

}
