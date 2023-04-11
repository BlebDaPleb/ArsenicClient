package blebdapleb.arsenic.arsenic;

import blebdapleb.arsenic.arsenic.eventbus.ArsenicEventBus;
import blebdapleb.arsenic.arsenic.eventbus.handler.InexactEventHandler;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleManager;
import blebdapleb.arsenic.arsenic.util.ArsenicLogger;
import blebdapleb.arsenic.arsenic.util.FriendManager;
import blebdapleb.arsenic.arsenic.util.Watermark;
import net.fabricmc.api.ModInitializer;

public class Arsenic implements ModInitializer {

    private static Arsenic instance = null;

    public static Arsenic getInstance() { return instance; }

    public static ArsenicEventBus eventBus = new ArsenicEventBus(new InexactEventHandler("arsenic"), ArsenicLogger.logger);

    private static ModuleManager moduleManager;

    public static FriendManager friendMang;

    public static Watermark watermark;

    @Override
    public void onInitialize() {
        instance = this;
        moduleManager = new ModuleManager();
        friendMang = new FriendManager();
    }

    public void postInit()
    {

    }
}
