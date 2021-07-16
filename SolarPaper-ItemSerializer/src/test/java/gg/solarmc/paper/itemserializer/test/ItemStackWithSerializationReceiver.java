package gg.solarmc.paper.itemserializer.test;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.OutputStream;
import java.util.Objects;

public class ItemStackWithSerializationReceiver extends ItemStack {

    private final SerializationReceiver serializationReceiver;

    public ItemStackWithSerializationReceiver(Material material, SerializationReceiver serializationReceiver) {
        super(material);
        this.serializationReceiver = Objects.requireNonNull(serializationReceiver);
    }

    @Override
    public void serializeAsBytes(@NonNull OutputStream output) {
        serializationReceiver.serializeAsBytes(output);
    }

    @Override
    public ItemStack clone() {
        return super.clone();
    }
}
