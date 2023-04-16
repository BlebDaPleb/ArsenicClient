package blebdapleb.arsenic.arsenic.module.mods.combat;

import blebdapleb.arsenic.arsenic.event.events.EventTick;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingBoolean;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import blebdapleb.arsenic.arsenic.util.CrystalDataTracker;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.EndCrystalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AutoCrystal extends Module {

    public AutoCrystal() {
        super("AutoCrystal", KEY_UNBOUND, ModuleCategory.COMBAT, "Automatically crystals for you.",
                new SettingBoolean("RandomizedInterval", true),
                new SettingNumber("Interval", 0, 20, 0, 0.1),
                new SettingBoolean("On Right Click", true)
        );
    }


    private int crystalBreakClock = 0;

    @Override
    public void onEnable(boolean inWorld) {
        crystalBreakClock = 0;

        super.onEnable(inWorld);
    }

    private boolean canPlaceCrystal() {
        HitResult target = mc.crosshairTarget;
        if (target == null || target.getType() != HitResult.Type.BLOCK)
            return false;

        BlockHitResult blockTarget = (BlockHitResult) target;
        BlockState blockState = mc.world.getBlockState(blockTarget.getBlockPos());

        return blockState.getBlock() == Blocks.OBSIDIAN;
    }

    private boolean canBreakCrystal() {
        HitResult target = mc.crosshairTarget;
        if (target == null || target.getType() != HitResult.Type.ENTITY)
            return false;

        EntityHitResult entityTarget = (EntityHitResult) target;
        Entity entity = entityTarget.getEntity();

        return entity instanceof EndCrystalEntity;
    }

    public static boolean canPlaceCrystalServer(BlockPos block) {
        final BlockState blockState = mc.world.getBlockState(block);
        if (!blockState.isOf(Blocks.OBSIDIAN) && !blockState.isOf(Blocks.BEDROCK))
            return false;

        final BlockPos blockPos2 = block.up();
        if (!mc.world.isAir(blockPos2))
            return false;

        final double d = blockPos2.getX();
        final double e = blockPos2.getY();
        final double f = blockPos2.getZ();

        final List<Entity> list = mc.world.getOtherEntities((Entity) null, new Box(d, e, f, d + 1.0D, e + 2.0D, f + 1.0D));
        //list.removeIf(entity -> entity instanceof ItemEntity); // items will be picked up by the nearby player
        // crystals can be placed down a lot faster in citying
        list.removeIf(entity -> {
            if (!(entity instanceof EndCrystalEntity))
                return false;
            return CrystalDataTracker.INSTANCE.isCrystalAttacked(entity);
        }); // crystal placement will be faster since on the server side the crystal have already been removed (probably)
        return list.isEmpty();
    }


    @ArsenicSubscribe
    public void onTick(EventTick event) {
        if (isEnabled()) {
            boolean dontBreakCrystal = crystalBreakClock != 0;

            if (dontBreakCrystal)
                crystalBreakClock--;

            if (mc.currentScreen != null)
                return;

            if (getSetting(2).asBoolean().isEnabled() && GLFW.glfwGetMouseButton
                    (mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS) return;

            ItemStack mainHandStack = mc.player.getMainHandStack();

            if (!mainHandStack.isOf(Items.END_CRYSTAL))
                return;

            boolean randomized = getSetting(0).asBoolean().isEnabled();
            int interval = getSetting(1).asNumber().getValueInt();

            if (!dontBreakCrystal && canPlaceCrystal() && mc.player.getMainHandStack().getItem() instanceof EndCrystalItem) {
                crystalBreakClock = randomized ? ThreadLocalRandom.current().nextInt(2, 11) : interval;
                BlockHitResult blockHitResult = (BlockHitResult) mc.crosshairTarget;
                if (canPlaceCrystalServer(blockHitResult.getBlockPos())) {
                    ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHitResult);
                    if (result.isAccepted() && result.shouldSwingHand())
                        mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
            if (canBreakCrystal()) {
                EntityHitResult entityHitResult = (EntityHitResult) mc.crosshairTarget;
                mc.interactionManager.attackEntity(mc.player, entityHitResult.getEntity());
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }
}
