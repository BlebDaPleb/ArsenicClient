package blebdapleb.arsenic.arsenic.module.mods.combat;

import blebdapleb.arsenic.arsenic.event.events.EventTick;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingBoolean;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import blebdapleb.arsenic.arsenic.util.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

public class AutoAnchor extends Module {

    private int timer = 0;
    private boolean hasAnchored;

    public AutoAnchor() {
        super("AutoAnchor", KEY_UNBOUND, ModuleCategory.COMBAT, "Automatically uses respawn anchors for you.",
                new SettingBoolean("OnRightClick", true),
                new SettingBoolean("ChargeOnly", false),
                new SettingNumber("Cooldown", 0, 10, 4, 1)
        );
    }

    @Override
    public void onEnable(boolean inWorld) {
        timer = 0;
        hasAnchored = false;

        super.onEnable(inWorld);
    }

    private boolean isBlock(Block block, BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock() == block;
    }

    private boolean isAnchorCharged(BlockPos anchor)
    {
        if (!isBlock(Blocks.RESPAWN_ANCHOR, anchor))
            return false;
        try
        {
            return mc.world.getBlockState(anchor).get(RespawnAnchorBlock.CHARGES) != 0;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
    }

    private boolean isAnchorUncharged(BlockPos anchor) {
        if (!isBlock(Blocks.RESPAWN_ANCHOR, anchor)) {
            return false;
        } else {
            try {
                return mc.world.getBlockState(anchor).get(RespawnAnchorBlock.CHARGES) == 0;
            } catch (IllegalArgumentException var2) {
                return false;
            }
        }
    }

    private boolean canPlaceAnchor() {
        BlockHitResult target = (BlockHitResult) mc.crosshairTarget;
        if (target == null)
            return false;

        BlockState blockState = mc.world.getBlockState(target.getBlockPos());

        return blockState.getBlock() != Blocks.RESPAWN_ANCHOR && blockState.getBlock() != Blocks.AIR;
    }

    @ArsenicSubscribe
    public void onTick(EventTick event) {
        if (!isEnabled() || mc.currentScreen != null) return;

        if (getSetting(0).asBoolean().isEnabled() && GLFW.glfwGetMouseButton
                (mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS) return;

        if (mc.player.isUsingItem()) {
            return;
        }

        if (hasAnchored) {
            if (timer != 0) {
                --timer;
                return;
            }
            timer = getSetting(2).asNumber().getValueInt();
            this.hasAnchored = false;
        }

        HitResult target = mc.crosshairTarget;
        if (target instanceof BlockHitResult hit) {
            BlockPos pos = hit.getBlockPos();
            if (isAnchorUncharged(pos)) {
                if (mc.player.isHolding(Items.GLOWSTONE)) {
                    ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                    if (result.isAccepted() && result.shouldSwingHand())
                        mc.player.swingHand(Hand.MAIN_HAND);
                    return;
                }

                InventoryUtils.selectItemFromHotbar(Items.GLOWSTONE);
                ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                if (result.isAccepted() && result.shouldSwingHand()) {
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
            } else if (isAnchorCharged(pos) && !getSetting(1).asBoolean().isEnabled()) {
                PlayerInventory inv = AutoAnchor.mc.player.getInventory();
                InventoryUtils.selectItemFromHotbar(Items.RESPAWN_ANCHOR);

                ActionResult result = mc.interactionManager.interactBlock(mc.player,  Hand.MAIN_HAND, hit);
                if (result.isAccepted() && result.shouldSwingHand())
                    mc.player.swingHand(Hand.MAIN_HAND);

                this.hasAnchored = true;
            }
        }

        /* MARLOW CRYSTAL
        if (mc.crosshairTarget instanceof BlockHitResult hit) {
            BlockPos pos = hit.getBlockPos();
            if (isAnchorCharged(pos)) {
                if (!mc.player.isHolding(Items.GLOWSTONE))
                {
                    ActionResult actionResult = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                    if (actionResult.isAccepted() && actionResult.shouldSwingHand())
                        mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }*/
    }

}
