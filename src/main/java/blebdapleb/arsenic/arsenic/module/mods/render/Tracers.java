package blebdapleb.arsenic.arsenic.module.mods.render;

import blebdapleb.arsenic.arsenic.event.events.EventWorldRender;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingBoolean;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import blebdapleb.arsenic.arsenic.util.render.Renderer;
import blebdapleb.arsenic.arsenic.util.render.color.LineColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class Tracers extends Module {

    public Tracers() {
        super("Tracers", KEY_UNBOUND, ModuleCategory.RENDER, "Shows lines that traces entities.",
                new SettingBoolean("Players", true),
                new SettingBoolean("Mobs", false),
                new SettingNumber("Width", 0.1, 5, 1.5, 0.1),
                new SettingNumber("Opacity", 0, 1, 0.75, 0.1)
        );
    }

    @ArsenicSubscribe
    public void onWorldRender(EventWorldRender.Post event) {
        if (!isEnabled())
            return;

        float width = getSetting(2).asNumber().getValueFloat();
        int opacity = (int) (getSetting(3).asNumber().getValueFloat() * 255);

        for (Entity e : mc.world.getEntities()) {
            int[] color = getColor(e);

            if (color != null) {
                Vec3d vec = e.getPos().subtract(Renderer.getInterpolationOffset(e));
                Vec3d vec2 = new Vec3d(0, 0, 75)
                        .rotateX(-(float) Math.toRadians(mc.gameRenderer.getCamera().getPitch()))
                        .rotateY(-(float) Math.toRadians(mc.gameRenderer.getCamera().getYaw()))
                        .add(mc.cameraEntity.getEyePos());

                LineColor lineColor =  LineColor.single(color[0], color[1], color[2], opacity);
                Renderer.drawLine(vec2.x, vec2.y, vec2.z, vec.x, vec.y, vec.z, lineColor, width);
                Renderer.drawLine(vec.x, vec.y, vec.z, vec.x, vec.y + e.getHeight() * 0.9, vec.z, lineColor, width);
            }
        }
    }

    private int[] getColor(Entity e)
    {
        if (e == mc.player)
            return null;

        if (e instanceof PlayerEntity)
            return new int[]{255, 0, 0};
        else if (e instanceof MobEntity)
            return new int[]{0, 255, 0};

        return null;
    }

}
