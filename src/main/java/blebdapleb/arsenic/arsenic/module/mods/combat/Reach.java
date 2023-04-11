package blebdapleb.arsenic.arsenic.module.mods.combat;

import blebdapleb.arsenic.arsenic.event.events.EventReach;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;

public class Reach extends Module {

    public Reach() {
        super("Reach", KEY_UNBOUND, ModuleCategory.COMBAT, "Allows you to reach further.",
                new SettingNumber("Distance", 4.5, 6, 5, 0.1)
        );
    }

    @ArsenicSubscribe
    public void onReachDistance(EventReach event)
    {
        if (isEnabled())
            event.setReach(getSetting(0).asNumber().getValueFloat());
    }

}
