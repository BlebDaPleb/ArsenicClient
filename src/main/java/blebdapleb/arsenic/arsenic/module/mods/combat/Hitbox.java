package blebdapleb.arsenic.arsenic.module.mods.combat;

import blebdapleb.arsenic.arsenic.event.events.EventWorldRender;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.util.ArsenicLogger;
import blebdapleb.arsenic.arsenic.util.render.Renderer;
import blebdapleb.arsenic.arsenic.util.render.color.QuadColor;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;

public class Hitbox extends Module {

    public Hitbox()
    {
        super("Hitbox", KEY_UNBOUND, ModuleCategory.COMBAT,
                true, "Increases entity hitbox sizes");
    }

    public static double size = 10;

    @ArsenicSubscribe
    public void onWorldRender(EventWorldRender.Post event) {

        mc.inGameHud.getChatHud().addMessage(Text.literal("EventWorldRender.Post"));

        if(isEnabled())
        {
            for (Entity e : mc.world.getEntities())
            {
                if(e == mc.player)
                    return;
                if(!(e instanceof PlayerEntity) && !(e instanceof MobEntity))
                    return;

                Box hitbox = e.getBoundingBox().expand(size);

                float width = 3.0f;

                Renderer.drawBoxOutline(hitbox, QuadColor.single(255, 0, 0, 255), width);
            }
        }
    }

}
