package blebdapleb.arsenic.arsenic.module.mods.combat;

import blebdapleb.arsenic.arsenic.event.events.EventTick;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.Setting;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingBoolean;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class AutoTotem extends Module {

    private int delay;
    private boolean holdingTotem;

    public AutoTotem() {
        super("AutoTotem", KEY_UNBOUND, ModuleCategory.COMBAT, "Automatically equips a totem in your offend.",
                new SettingBoolean("Override", false),
                new SettingNumber("Delay", 0, 10, 0, 1),
                new SettingNumber("PopDelay", 0, 10, 0, 1)
        );
    }

    @ArsenicSubscribe
    public void onTick(EventTick event) {
        holdingTotem = mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING;

        if (holdingTotem && mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
            delay = Math.max(getSetting(2).asNumber().getValueInt(), delay);
        }

        if (delay > 0) {
            delay--;
            return;
        }

        if (holdingTotem || (!mc.player.getOffHandStack().isEmpty() && !getSetting(0).asBoolean().isEnabled())) {
            return;
        }

        if (mc.player.playerScreenHandler == mc.player.currentScreenHandler) {
            for (int i = 9; i < 45; i++) {
                if (mc.player.getInventory().getStack(i >= 36 ? i - 36 : i).getItem() == Items.TOTEM_OF_UNDYING) {
                    boolean itemInOffhand = !mc.player.getOffHandStack().isEmpty();
                    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);
                    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);

                    if (itemInOffhand)
                        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);

                    delay = getSetting(1).asNumber().getValueInt();
                    return;
                }
            }
        } else {
            for (int i = 0; i < 9; i++) {
                if (mc.player.getInventory().getStack(i).getItem() == Items.TOTEM_OF_UNDYING) {
                    if (i != mc.player.getInventory().selectedSlot) {
                        mc.player.getInventory().selectedSlot = i;
                        mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(i));
                    }

                    mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
                    delay = getSetting(1).asNumber().getValueInt();
                    return;
                }
            }
        }
    }
}
