package blebdapleb.arsenic.arsenic.module.mods.combat;

import blebdapleb.arsenic.arsenic.event.events.EventTick;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingBoolean;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import blebdapleb.arsenic.arsenic.util.world.EntityUtils;
import blebdapleb.arsenic.arsenic.util.world.WorldUtils;
import com.google.common.collect.Streams;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;

public class BowBot extends Module {

    public BowBot() {
        super("BowBot", KEY_UNBOUND, ModuleCategory.COMBAT, "Automatically aims with a bow for you.",
                new SettingBoolean("Players", true),
                new SettingBoolean("Mobs", false),
                new SettingNumber("Smoothing", 0, 1, 0.1, 0.01),
                new SettingNumber("FOV", 0, 360, 30, 1)
        );
    }

    @ArsenicSubscribe
    public void onTick(EventTick event) {
        if (!isEnabled()) return;

        if (!(mc.player.getMainHandStack().getItem() instanceof RangedWeaponItem) || !mc.player.isUsingItem())
            return;

        // Credit: https://github.com/Wurst-Imperium/Wurst7/blob/master/src/main/java/net/wurstclient/hacks/BowAimbotHack.java
        LivingEntity target = Streams.stream(mc.world.getEntities())
                .filter(e -> EntityUtils.isAttackable(e, true) // checks if entity is attackable
                        && mc.player.canSee(e)) // checks if player can see the entity
                .sorted(Comparator.comparing(mc.player::distanceTo)) // sorts the entities by shortest distance to player
                .map(e -> (LivingEntity) e) // maps all the entities
                .findFirst().orElse(null);

        if (target == null)
            return;

        if (target instanceof PlayerEntity && !getSetting(0).asBoolean().isEnabled())
            return;
        if (target instanceof MobEntity && !getSetting(1).asBoolean().isEnabled())
            return;

        // set velocity
        float velocity = (72000 - mc.player.getItemUseTimeLeft()) / 20F;
        velocity = Math.min(1f, (velocity * velocity + velocity * 2) / 3);

        // set position to aim at
        Vec3d newTargetVec = target.getPos().add(target.getVelocity());
        double d = mc.player.getEyePos().distanceTo(target.getBoundingBox().offset(target.getVelocity()).getCenter());
        double x = newTargetVec.x + (newTargetVec.x - target.getX()) * d - mc.player.getX();
        double y = newTargetVec.y + (newTargetVec.y - target.getY()) * d + target.getHeight() * 0.5 - mc.player.getY() - mc.player.getEyeHeight(mc.player.getPose());
        double z = newTargetVec.z + (newTargetVec.z - target.getZ()) * d - mc.player.getZ();


        float interpolation = getSetting(2).asNumber().getValueFloat();

        // calculate needed pitch
        double hDistance = Math.sqrt(x * x + z * z);
        double hDistanceSq = hDistance * hDistance;
        float g = 0.006F;
        float velocitySq = velocity * velocity;
        float velocityPow4 = velocitySq * velocitySq;
        float neededPitch = (float) -Math.toDegrees(Math.atan((velocitySq - Math
                .sqrt(velocityPow4 - g * (g * hDistanceSq + 2 * y * velocitySq)))
                / (g * hDistance)));

        float currentYaw = mc.player.getYaw();
        float targetYaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90;

        float currentPitch = mc.player.getPitch();

        float diffYaw = Math.abs(targetYaw - currentYaw);
        float diffPitch = Math.abs(neededPitch - currentPitch);

        float FOV = getSetting(3).asNumber().getValueFloat();

        // set yaw
        if (diffYaw <= FOV && diffPitch <= FOV) {
            mc.player.setYaw(MathHelper.lerp(interpolation, currentYaw, targetYaw));
            mc.player.setPitch(MathHelper.lerp(interpolation, currentPitch, neededPitch));
        }
    }
}
