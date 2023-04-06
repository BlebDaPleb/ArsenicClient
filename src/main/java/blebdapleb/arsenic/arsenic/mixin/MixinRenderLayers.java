package blebdapleb.arsenic.arsenic.mixin;

import blebdapleb.arsenic.arsenic.Arsenic;
import blebdapleb.arsenic.arsenic.event.events.EventRenderBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderLayers.class)
public class MixinRenderLayers {

	@Inject(method = "getBlockLayer", at = @At("HEAD"), cancellable = true)
	private static void getBlockLayer(BlockState state, CallbackInfoReturnable<RenderLayer> callback) {
		EventRenderBlock.Layer event = new EventRenderBlock.Layer(state);
		Arsenic.eventBus.post(event);

		if (event.getLayer() != null)
			callback.setReturnValue(event.getLayer());
	}
}
