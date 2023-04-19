package blebdapleb.arsenic.arsenic.module.mods.misc;

import blebdapleb.arsenic.arsenic.gui.clickgui.ClickGui;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.ModuleManager;
import blebdapleb.arsenic.arsenic.module.setting.Setting;

public class SelfDestruct extends Module {

    public SelfDestruct() {
        super("SelfDestruct", KEY_UNBOUND, ModuleCategory.MISC, "Disables all modules and keybinds and clears strings");
    }

    @Override
    public void onEnable(boolean inWorld) {
        for (Module m : ModuleManager.getModules()) {
            m.setKey(KEY_UNBOUND);
            m.setEnabled(false);

            // SETTING STRINGS TO NULL
            m.setName(null);
            m.setDesc(null);
            for (Setting s : m.getSettings()) {
                s.setName(null);
            }
            ClickGui.name = null;
        }

        System.gc();

        super.onEnable(inWorld);
    }
}
