package gg.solarmc.paper.itemserializer;

import gg.solarmc.loader.kitpvp.KitItem;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * A kit item for bukkit
 *
 */
public final class BukkitKitItem implements KitItem {

    private final ItemStack item;

    // Clone-free package-private constuctor
    BukkitKitItem(ItemStack item) {
        this.item = Objects.requireNonNull(item, "item");
    }

    public static BukkitKitItem create(ItemStack item) {
        //noinspection UseOfClone
        return new BukkitKitItem(item.clone());
    }

    @Override
    public String getMaterial() {
        return item.getType().name();
    }

    @Override
    public String getDisplayName() {
        return PlainComponentSerializer.plain().serialize(item.displayName());
    }

    @Override
    public int getAmount() {
        return item.getAmount();
    }

    /**
     * Gets a clone of the underlying item stack
     *
     * @return the underlying item
     */
    @Override
    public ItemStack getItem() {
        //noinspection UseOfClone
        return item.clone();
    }

    ItemStack getItemUncloned() {
        return item;
    }

    @Override
    public <T> T getItem(Class<T> type) {
        return type.cast(getItem());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BukkitKitItem that = (BukkitKitItem) o;
        return item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return item.hashCode();
    }

    @Override
    public String toString() {
        return "BukkitKitItem{" +
                "item=" + item +
                '}';
    }
}
