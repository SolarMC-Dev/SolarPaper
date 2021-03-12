package gg.solarmc.serverassistant;

import com.destroystokyo.paper.event.executor.ClassDefiner;
import com.destroystokyo.paper.event.executor.ClassDefinerFactory;
import com.destroystokyo.paper.event.executor.ModuleOpener;

import java.lang.invoke.MethodHandles;

/**
 * A class definer which uses the following approach to first export or open the listener module
 * to the server module, then to define classes using MethodHandles.Lookup#defineClass. <br>
 * <br>
 * If the listener module is in the module layer of the layer controller, the controller will be used
 * to export the package of the listener to this module. If not in the module layer, the listener
 * module must either open its package to org.bukkit or export its package unconditionally. If opened to
 * org.bukkit, ModuleOpener will then further open the listener package to this module.
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
        if (listenerModule.isExported(listenerPackage)) {
            // Exported unconditionally

        } else if (controller.layer().modules().contains(listenerModule)) {
            controller.addExports(listenerModule, listenerPackage, ourModule);

        } else {
            // Last attempt. The listener module must open the package to org.bukkit
            // If so, this will succeed and open the package to ourselves
            moduleOpener.openPackage(listenerClass, ourModule);
        }
        ourModule.addReads(listenerModule);
        try {
            return MethodHandles.lookup().defineClass(data);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Unable to generate EventExecutor", ex);
        }
    }

    @Override
    public String getDefiningPackage() {
        return getClass().getPackageName();
    }

}
