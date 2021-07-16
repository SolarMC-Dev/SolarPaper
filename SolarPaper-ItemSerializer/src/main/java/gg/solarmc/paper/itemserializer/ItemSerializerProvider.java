package gg.solarmc.paper.itemserializer;

import gg.solarmc.loader.kitpvp.ItemSerializer;

public final class ItemSerializerProvider {

    private ItemSerializerProvider() { }

    public static ItemSerializer provider() {
        return new BukkitItemSerializer();
    }
}
