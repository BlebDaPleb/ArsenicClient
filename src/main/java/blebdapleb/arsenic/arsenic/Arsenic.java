package blebdapleb.arsenic.arsenic;

import blebdapleb.arsenic.arsenic.util.ArsenicLogger;
import net.fabricmc.api.ModInitializer;

public class Arsenic implements ModInitializer {

    private static Arsenic instance = null;

    public static Arsenic getInstance() { return instance; }

    @Override
    public void onInitialize() {
        instance = this;

        ArsenicLogger.info("ArsenicLogger Successfully Loaded!");
        ArsenicLogger.warn("Warning!");
        ArsenicLogger.error("Error!");
    }
}
