package blebdapleb.arsenic.arsenic.module.mods.render;

import blebdapleb.arsenic.arsenic.event.events.EventRenderInGameHud;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import net.minecraft.client.util.InputUtil;

public class Hud extends Module {

    public Hud()
    {
        super("Hud", InputUtil.GLFW_KEY_6, ModuleCategory.RENDER,
                true, "Shows all enabled modules on the hud.");
    }

}
