package blebdapleb.arsenic.arsenic.module.mods.combat;

import blebdapleb.arsenic.arsenic.event.events.EventWorldRender;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import blebdapleb.arsenic.arsenic.util.render.Renderer;
import blebdapleb.arsenic.arsenic.util.render.color.QuadColor;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;

public class Hitbox extends Module {

    public Hitbox()
    {
        super("Hitbox", InputUtil.GLFW_KEY_RIGHT, ModuleCategory.COMBAT, "Increases entity hitbox sizes",
                new SettingNumber("Size", 0.1, 1, 0.3, 0.1)
        );
    }

    @ArsenicSubscribe
    public void onWorldRender(EventWorldRender.Post event) {
        if(isEnabled())
        {
            double size = getSetting(0).asNumber().getValue();

            for (Entity e : mc.world.getEntities())
            {
                int[] color = {0, 255, 0};
                float width = 1.0f;

                if (e != mc.player && e instanceof LivingEntity)
                {
                    Box hitbox = e.getBoundingBox().expand(size);

                    Renderer.drawBoxOutline(hitbox, QuadColor.single(color[0], color[1], color[2], 255), width);
                }
            }
        }
    }

}
