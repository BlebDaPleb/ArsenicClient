package blebdapleb.arsenic.arsenic.module.mods.combat;

import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import net.minecraft.client.util.InputUtil;

public class Hitbox extends Module {

    public Hitbox()
    {
        super("Hitbox", InputUtil.GLFW_KEY_RIGHT, ModuleCategory.COMBAT, "Increases entity hitbox sizes",
                new SettingNumber("Size", 0.1, 1, 0.3, 0.1)
        );
    }


}
