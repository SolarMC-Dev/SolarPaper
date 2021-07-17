import gg.solarmc.paper.serverassistant.ModularClassDefiner;

module gg.solarmc.paper.serverassistant {

    requires org.bukkit;

    exports gg.solarmc.paper.serverassistant;
    provides com.destroystokyo.paper.event.executor.ClassDefinerFactory with ModularClassDefiner;
}
