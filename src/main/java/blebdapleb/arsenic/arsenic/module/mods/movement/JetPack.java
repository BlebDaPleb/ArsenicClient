package blebdapleb.arsenic.arsenic.module.mods.movement;

import blebdapleb.arsenic.arsenic.event.events.EventTick;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.Setting;

public class JetPack extends Module {

    public JetPack() {
        super("JetPack", KEY_UNBOUND, ModuleCategory.MOVEMENT, "Self Explanatory");
    }

    @ArsenicSubscribe
    public void onTick(EventTick eventTick) {
        if (!isEnabled()) return;

        if (mc.options.jumpKey.isPressed() && !mc.options.sneakKey.isPressed()) {
            mc.player.setVelocity(0, 0.42f, 0);
        }
    }

}
