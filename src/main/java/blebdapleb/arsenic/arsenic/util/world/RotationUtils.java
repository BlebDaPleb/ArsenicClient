package blebdapleb.arsenic.arsenic.util.world;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtils {

    public static Vec3d getPlayerLookVec(PlayerEntity player) {
        float f = 0.017453292F;
        float pi = (float) Math.PI;

        float f1 = MathHelper.cos(-player.getYaw() * f - pi);
        float f2 = MathHelper.sin(-player.getYaw() * f - pi);
        float f3 = -MathHelper.cos(-player.getPitch() * f);
        float f4 = MathHelper.sin(-player.getPitch() * f);

        return new Vec3d(f2 * f3, f4, f1 * f3).normalize();
    }

}
