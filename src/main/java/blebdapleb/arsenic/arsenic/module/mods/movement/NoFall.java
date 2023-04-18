package blebdapleb.arsenic.arsenic.module.mods.movement;

import blebdapleb.arsenic.arsenic.event.events.EventTick;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingMode;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall", KEY_UNBOUND ,ModuleCategory.MOVEMENT, "Prevents you from taking no fall damage",
            new SettingMode("Mode", "Simple", "Simple", "Packet")
        );
    }

    @ArsenicSubscribe
    public void onTick(EventTick event) {
        if (isEnabled()) {
            if (mc.player.fallDistance > 2.5f && getSetting(0).asMode().isMode("Simple")) {
                if (mc.player.isFallFlying())
                    return;
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
            }

            if (mc.player.fallDistance > 2.5f && getSetting(0).asMode().isMode("Packet") &&
                    mc.world.getBlockState(mc.player.getBlockPos().add(
                            0, (int) (-1.5 + (mc.player.getVelocity().y * 0.1)), 0)).getBlock() != Blocks.AIR) {
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(false));
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        mc.player.getX(), mc.player.getY() - 420.69, mc.player.getZ(), true));
                mc.player.fallDistance = 0;
            }
        }
    }

}
