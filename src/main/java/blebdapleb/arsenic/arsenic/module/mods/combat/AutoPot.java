package blebdapleb.arsenic.arsenic.module.mods.combat;

import blebdapleb.arsenic.arsenic.event.events.EventTick;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.Setting;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.math.MathHelper;

public class AutoPot extends Module {

    public AutoPot() {
        super("AutoPot", KEY_UNBOUND, ModuleCategory.COMBAT, "Automatically pots when your low.",
                new SettingNumber("MinHealth", 0.5, 9.5, 6, 0.5)
        );
    }

    private int timer;

    @ArsenicSubscribe
    public void onTick(EventTick eventTick) {
        int potionInHotbar = findPotion(0, 9);

        if (potionInHotbar != -1){
            if (timer > 0) {
                timer--;
                return;
            }

            if(mc.player.getHealth() > getSetting(0).asNumber().getValueFloat() * 2F)
                return;

            int oldSlot = mc.player.getInventory().selectedSlot;

            float currentPitch = mc.player.getPitch();
            float targetPitch = 90;

            mc.player.getInventory().selectedSlot = potionInHotbar;

            mc.player.networkHandler.sendPacket(
                    new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw() , 90,
                            mc.player.isOnGround()));

            currentPitch = MathHelper.lerp(1, currentPitch, targetPitch);
            mc.player.setPitch(currentPitch);

            mc.interactionManager.interactItem(mc.player, mc.player.getActiveHand());

            mc.player.getInventory().selectedSlot = oldSlot;
            mc.player.networkHandler.sendPacket(
                    new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(),
                            mc.player.getPitch(), mc.player.isOnGround()));

            timer = 10;
        }
    }

    private int findPotion(int startSlot, int endSlot)
    {
        for(int i = startSlot; i < endSlot; i++)
        {
            ItemStack stack = mc.player.getInventory().getStack(i);

            // filter out non-splash potion items
            if(stack.getItem() != Items.SPLASH_POTION)
                continue;

            // search for instant health effects
            if(hasEffect(stack, StatusEffects.INSTANT_HEALTH))
                return i;
        }

        return -1;
    }

    private boolean hasEffect(ItemStack stack, StatusEffect effect)
    {
        for(StatusEffectInstance effectInstance : PotionUtil
                .getPotionEffects(stack))
        {
            if(effectInstance.getEffectType() != effect)
                continue;

            return true;
        }

        return false;
    }

    @Override
    public void onDisable(boolean inWorld) {
        timer = 0;

        super.onDisable(inWorld);
    }
}
