package blebdapleb.arsenic.arsenic.module.mods.combat;

import blebdapleb.arsenic.arsenic.event.events.EventWorldRender;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingBoolean;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingMode;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import blebdapleb.arsenic.arsenic.util.ArsenicLogger;
import blebdapleb.arsenic.arsenic.util.render.Renderer;
import blebdapleb.arsenic.arsenic.util.render.color.QuadColor;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class Hitbox extends Module {

    public Hitbox()
    {
        super("Hitbox", InputUtil.GLFW_KEY_RIGHT, ModuleCategory.COMBAT, true, "Increases entity hitbox sizes",
                new SettingNumber("Size", 0.1, 1, 0.3, 0.1),
                new SettingBoolean("Test", false),
                new SettingMode("TestMode", "Default", "Default", "Mode 2", "Mode 3")
        );
    }

    @ArsenicSubscribe
    public void onWorldRender(EventWorldRender.Post event) {
        if(isEnabled())
        {
            for (Entity e : mc.world.getEntities())
            {
                if(e == mc.player)
                    return;
                if(!(e instanceof PlayerEntity) && !(e instanceof MobEntity))
                    return;

                double size = getSetting(0).asNumber().getValue();

                Box hitbox = e.getBoundingBox().expand(size);

                int[] color = {255, 0, 0};
                float width = 3.0f;

                Renderer.drawBoxOutline(hitbox, QuadColor.single(color[0], color[1], color[2], 255), width);
            }
        }
    }

}
