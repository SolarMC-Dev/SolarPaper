import gg.solarmc.serverassistant.ModularClassDefiner;

module gg.solarmc.serverassistant {

    requires org.bukkit;

    provides com.destroystokyo.paper.event.executor.ClassDefinerFactory with ModularClassDefiner;
}