package gg.solarmc.paper.itemserializer.test;

import gg.solarmc.loader.kitpvp.ItemSerializer;
import gg.solarmc.loader.kitpvp.KitItem;
import gg.solarmc.paper.itemserializer.BukkitKitItem;
import gg.solarmc.paper.itemserializer.ItemSerializerProvider;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BukkitItemSerializerTest {

    private final ItemSerializer itemSerializer = ItemSerializerProvider.provider();

    @Test
    public void serializeBukkitKitItem(@Mock OutputStream output,
                              @Mock SerializationReceiver serializationReceiver) throws IOException {
        ItemStack item = new ItemStackWithSerializationReceiver(Material.STONE, serializationReceiver);
        itemSerializer.serialize(BukkitKitItem.create(item), output);
        verify(serializationReceiver).serializeAsBytes(output);
    }

    @Test
    public void serializeOtherKitItem(@Mock OutputStream output,
                                      @Mock SerializationReceiver serializationReceiver,
                                      @Mock KitItem kitItem) throws IOException {
        ItemStack item = new ItemStackWithSerializationReceiver(Material.STONE, serializationReceiver);
        when(kitItem.getItem(ItemStack.class)).thenReturn(item);
        itemSerializer.serialize(kitItem, output);
        verify(serializationReceiver).serializeAsBytes(output);
    }
}
