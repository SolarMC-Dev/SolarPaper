import gg.solarmc.serverassistant.ModularClassDefiner;

module gg.solarmc.serverassistant {

    requires org.bukkit;

    exports gg.solarmc.serverassistant to java.base;
    provides com.destroystokyo.paper.event.executor.ClassDefinerFactory with ModularClassDefiner;
}