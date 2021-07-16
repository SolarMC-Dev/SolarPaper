import gg.solarmc.paper.itemserializer.ItemSerializerProvider;

module gg.solarmc.paper.itemserializer {
    requires transitive org.bukkit;
    requires transitive gg.solarmc.loader.kitpvp;
    requires net.kyori.adventure.text.serializer.plain;

    exports gg.solarmc.paper.itemserializer;
    provides gg.solarmc.loader.kitpvp.ItemSerializer with ItemSerializerProvider;
}