package blebdapleb.arsenic.arsenic.module.mods.client;

import blebdapleb.arsenic.arsenic.event.events.EventOpenScreen;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import net.minecraft.client.util.InputUtil;

public class ClickGui extends Module {

    public ClickGui() {
        super("ClickGUI", InputUtil.GLFW_KEY_RIGHT_SHIFT, ModuleCategory.CLIENT,
                false, "Shows GUI.");
    }

    @Override
    public void onEnable(boolean inWorld)
    {
        super.onEnable(inWorld);

        mc.setScreen(blebdapleb.arsenic.arsenic.gui.clickgui.ClickGui.INSTANCE);
    }

    @Override
    public void onDisable(boolean inWorld)
    {
        super.onDisable(inWorld);

        if (mc.currentScreen instanceof blebdapleb.arsenic.arsenic.gui.clickgui.ClickGui)
        mc.setScreen(null);
    }

    @ArsenicSubscribe
    public void onOpenScreen(EventOpenScreen event) {
        if (event.getScreen() == null) {
            setEnabled(false);
        }
    }

}
