package blebdapleb.arsenic.arsenic.module.mods.client;

import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import net.minecraft.client.util.InputUtil;

public class Hud extends Module {

    public Hud()
    {
        super("Hud", InputUtil.GLFW_KEY_6, ModuleCategory.CLIENT,
                true, "Shows all enabled modules on the hud.");
    }

}
