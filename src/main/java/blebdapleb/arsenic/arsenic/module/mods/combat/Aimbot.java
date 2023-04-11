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
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;

import java.util.Comparator;

import static net.minecraft.util.math.MathHelper.lerp;

public class Aimbot extends Module {

    private int buffer = 0;

    public Aimbot() {
        super("Aimbot", KEY_UNBOUND, ModuleCategory.COMBAT,"Automatically aims at entities for you",
                new SettingBoolean("Players", true),
                new SettingBoolean("Mobs", true),
                new SettingNumber("Smoothing", 0.01, 1, 0.1, 0.01),
                new SettingNumber("FOV", 5, 360, 30, 1)
        );
    }

    @ArsenicSubscribe
    public void onTick(EventTick event)
    {
        if (!isEnabled() || !mc.player.isAlive())
            return;

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

        double targetX = target.getX();
        double targetY = target.getY() + target.getHeight() / 2;
        double targetZ = target.getZ();

        float[] rot = WorldUtils.getViewingRotation(mc.player, targetX, targetY, targetZ);

        float currentYaw = mc.player.getYaw();
        float currentPitch = mc.player.getPitch();

        float targetYaw = mc.player.getYaw() + MathHelper.wrapDegrees(rot[0] - mc.player.getYaw());
        float targetPitch = mc.player.getPitch() + MathHelper.wrapDegrees(rot[1] - mc.player.getPitch());

        float diffYaw = Math.abs(targetYaw - currentYaw);
        float diffPitch = Math.abs(targetPitch - currentPitch);

        float FOV = getSetting(3).asNumber().getValueFloat();
        float interpolation = getSetting(2).asNumber().getValueFloat();

        if (diffYaw <= FOV && diffPitch <= FOV)
        {
            currentYaw = lerp(interpolation, currentYaw, targetYaw);
            currentPitch = lerp(interpolation, currentPitch, targetPitch);

            mc.player.setYaw(currentYaw);
            mc.player.setPitch(currentPitch);

            // SERVER REPLICATION

            if (!mc.player.hasVehicle()) {
                mc.player.headYaw = currentYaw;
                mc.player.bodyYaw = mc.player.headYaw;
                mc.player.renderPitch = currentPitch;
            }

            buffer++;

            if (buffer >= 15)
            {
                mc.player.networkHandler.sendPacket(
                        new PlayerMoveC2SPacket.LookAndOnGround(
                                currentYaw,
                                currentPitch,
                                mc.player.isOnGround())
                );

                buffer = 0;
            }
        }
    }
}
