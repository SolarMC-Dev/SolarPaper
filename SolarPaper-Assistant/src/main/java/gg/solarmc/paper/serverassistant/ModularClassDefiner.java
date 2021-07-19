package gg.solarmc.paper.serverassistant;

import com.destroystokyo.paper.event.executor.ClassDefiner;
import com.destroystokyo.paper.event.executor.ClassDefinerFactory;
import com.destroystokyo.paper.event.executor.ModuleOpener;

import java.lang.invoke.MethodHandles;

/**
 * A class definer which uses the following approach to first export or open the listener module
 * to the server module, then to define classes using MethodHandles.Lookup#defineClass. <br>
 * <br>
 * If the listener module is in the module layer of the layer controller, the controller will be used
 * to open the package of the listener to this module. If not in the module layer, the listener
 * module must open its package either to org.bukkit or unconditionally. If opened to org.bukkit,
 * ModuleOpener will then further open the listener package to this module. <br>
 * <br>
 * Finally, this module will make itself read the listener module and use MethodHandles.Lookup#defineClass
 * to generate the EventExecutor.
 */
public final class ModularClassDefiner implements ClassDefiner {

    private final ModuleLayer.Controller controller;
    private final ModuleOpener moduleOpener;

    ModularClassDefiner(ModuleLayer.Controller controller, ModuleOpener moduleOpener) {
        this.controller = controller;
        this.moduleOpener = moduleOpener;
    }

    public ModularClassDefiner(ModuleLayer.Controller controller) {
        this(controller, new ModuleOpener());
    }

    public static ClassDefinerFactory provider() {
        return ModularClassDefiner::new;
    }

    @Override
    public Class<?> defineClass(Class<?> listenerClass, String name, byte[] data) {
        Module listenerModule = listenerClass.getModule();
        Module ourModule = getClass().getModule();
        String listenerPackage = listenerClass.getPackageName();
        if (listenerModule.isOpen(listenerPackage)) {
            // Opened unconditionally

        } else if (controller.layer().modules().contains(listenerModule)) {
            controller.addOpens(listenerModule, listenerPackage, ourModule);

        } else {
            // Last attempt. The listener module must open the package to org.bukkit
            // If so, this will succeed and open the package to ourselves
            moduleOpener.openPackage(listenerClass, ourModule);
        }
        ourModule.addReads(listenerModule);
        try {
            return MethodHandles.privateLookupIn(listenerClass, MethodHandles.lookup()).defineClass(data);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Unable to generate EventExecutor", ex);
        }
    }

    @Override
    public String getDefinedClassName(Class<?> listenerClass, int uniqueId) {
        return listenerClass.getName() + "$$SolarMcGeneratedCaller" + uniqueId;
    }

}
