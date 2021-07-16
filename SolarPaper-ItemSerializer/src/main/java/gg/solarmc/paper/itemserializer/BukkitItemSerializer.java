package gg.solarmc.paper.itemserializer;

import gg.solarmc.loader.kitpvp.ItemSerializer;
import gg.solarmc.loader.kitpvp.KitItem;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class BukkitItemSerializer implements ItemSerializer {

    @Override
    public void serialize(KitItem item, OutputStream output) throws IOException {
        ItemStack itemStack =
                (item instanceof BukkitKitItem bukkitItem) ? bukkitItem.getItemUncloned() : item.getItem(ItemStack.class);
        itemStack.serializeAsBytes(output);
    }

    @Override
    public KitItem deserialize(InputStream input) throws IOException {
        return new BukkitKitItem(ItemStack.deserializeBytes(input));
    }
}
