/*
 * This file is part of the BleachHack distribution (https://github.com/BleachDev/BleachHack/).
 * Copyright (c) 2021 Bleach and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package blebdapleb.arsenic.arsenic.mixin;

import blebdapleb.arsenic.arsenic.Arsenic;
import blebdapleb.arsenic.arsenic.event.events.EventLightTex;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightmapTextureManager.class)
public class MixinLightmapTextureManager {

	@Inject(method = "getBrightness", at = @At("RETURN"), cancellable = true)
	private static void getBrightness(DimensionType type, int lightLevel, CallbackInfoReturnable<Float> cir) {
		EventLightTex.Brightness event = new EventLightTex.Brightness(type, lightLevel, cir.getReturnValueF());
		Arsenic.eventBus.post(event);
		cir.setReturnValue(event.getBrightness());
	}

	@Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1))
	private float update_floatValue(Double instance) {
		EventLightTex.Gamma event = new EventLightTex.Gamma(instance.floatValue());
		Arsenic.eventBus.post(event);
		return event.getGamma();
	}
}
