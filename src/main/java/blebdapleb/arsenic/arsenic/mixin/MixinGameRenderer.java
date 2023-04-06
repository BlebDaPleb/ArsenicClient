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
import blebdapleb.arsenic.arsenic.event.events.EventRenderShader;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

	@Shadow private PostEffectProcessor postProcessor;

	/*
	@Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
	private void onTiltViewWhenHurt(MatrixStack matrixStack, float f, CallbackInfo ci) {
		if (ModuleManager.getModule(NoRender.class).isOverlayToggled(2)) {
			ci.cancel();
		}
	}*/

	/*
	@Inject(method = "showFloatingItem", at = @At("HEAD"), cancellable = true)
	private void showFloatingItem(ItemStack floatingItem, CallbackInfo ci) {
		if (ModuleManager.getModule(NoRender.class).isWorldToggled(1) && floatingItem.getItem() == Items.TOTEM_OF_UNDYING) {
			ci.cancel();
		}
	}*/

	/*
	@Redirect(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 0),
			require = 0)
	private float nauseaWobble(float delta, float first, float second) {
		if (ModuleManager.getModule(NoRender.class).isOverlayToggled(5)) {
			return 0;
		}

		return MathHelper.lerp(delta, first, second);
	}*/

	@Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;postProcessor:Lnet/minecraft/client/gl/PostEffectProcessor;", ordinal = 0))
	private PostEffectProcessor render_Shader(GameRenderer renderer, float tickDelta) {
		EventRenderShader event = new EventRenderShader(postProcessor);
		Arsenic.eventBus.post(event);

		if (event.getEffect() != null) {
			RenderSystem.disableBlend();
			RenderSystem.disableDepthTest();
			RenderSystem.resetTextureMatrix();
			event.getEffect().render(tickDelta);
		}

		return null;
	}
}
