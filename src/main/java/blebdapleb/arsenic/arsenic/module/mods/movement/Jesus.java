package blebdapleb.arsenic.arsenic.module.mods.movement;

import blebdapleb.arsenic.arsenic.event.events.EventBlockShape;
import blebdapleb.arsenic.arsenic.event.events.EventTick;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingMode;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;

public class Jesus extends Module {

    public Jesus() {
        super("Jesus", KEY_UNBOUND, ModuleCategory.MOVEMENT, "Allows you to walk on water.",
                new SettingMode("Mode", "Solid", "Solid", "Vibrate")
        );
    }

    @ArsenicSubscribe
    public void onTick(EventTick event) {
        Entity e = mc.player.getRootVehicle();

        if (e.isSneaking() || e.fallDistance > 3f)
            return;

        if (isSubmerged(e.getPos().add(0, 0.3, 0))) {
            e.setVelocity(e.getVelocity().x, 0.08, e.getVelocity().z);
        } else if (isSubmerged(e.getPos().add(0, 0.1, 0))) {
            e.setVelocity(e.getVelocity().x, 0.05, e.getVelocity().z);
        } else if (isSubmerged(e.getPos().add(0, 0.05, 0))) {
            e.setVelocity(e.getVelocity().x, 0.01, e.getVelocity().z);
        } else if (isSubmerged(e.getPos())) {
            e.setVelocity(e.getVelocity().x, -0.005, e.getVelocity().z);
            e.setOnGround(true);
        }
    }

    @ArsenicSubscribe
    public void onBlockShape(EventBlockShape event) {
        if (getSetting(0).asMode().isMode("Solid")
                && !mc.world.getFluidState(event.getPos()).isEmpty()
                && !mc.player.isSneaking()
                && !mc.player.isTouchingWater()
                && mc.player.getY() >= event.getPos().getY() + 0.9) {
            event.setShape(VoxelShapes.cuboid(0, 0, 0, 1, 0.9, 1));
        }
    }

    private boolean isSubmerged(Vec3d pos) {
        BlockPos bp = BlockPos.ofFloored(pos);
        FluidState state = mc.world.getFluidState(bp);

        return !state.isEmpty() && pos.y - bp.getY() <= state.getHeight();
    }

}
