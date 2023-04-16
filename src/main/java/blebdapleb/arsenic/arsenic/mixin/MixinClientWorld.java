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
import blebdapleb.arsenic.arsenic.event.events.EventEntity;
import blebdapleb.arsenic.arsenic.event.events.EventSkyRender;
import blebdapleb.arsenic.arsenic.event.events.EventTick;
import blebdapleb.arsenic.arsenic.util.ArsenicQueue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public class MixinClientWorld {

	@Shadow @Final private DimensionEffects dimensionEffects;

	private MinecraftClient mc = MinecraftClient.getInstance();

	@Inject(method = "tickEntities", at = @At("HEAD"), cancellable = true)
	private void tickEntities(CallbackInfo info) {
		ArsenicQueue.nextQueue();

		EventTick event = new EventTick();
		Arsenic.eventBus.post(event);
		if (event.isCancelled())
			info.cancel();
	}

	@Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
	public void getSkyColor(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable<Vec3d> ci) {
		EventSkyRender.Color.SkyColor event = new EventSkyRender.Color.SkyColor(tickDelta);
		Arsenic.eventBus.post(event);

		if (event.isCancelled()) {
			ci.setReturnValue(Vec3d.ZERO);
		} else if (event.getColor() != null) {
			ci.setReturnValue(event.getColor());
		}
	}

	@Inject(method = "getCloudsColor", at = @At("HEAD"), cancellable = true)
	private void getCloudsColor(float f, CallbackInfoReturnable<Vec3d> ci) {
		EventSkyRender.Color.CloudColor event = new EventSkyRender.Color.CloudColor(f);
		Arsenic.eventBus.post(event);

		if (event.isCancelled()) {
			ci.setReturnValue(Vec3d.ZERO);
		} else if (event.getColor() != null) {
			ci.setReturnValue(event.getColor());
		}
	}

	@Inject(method = "addEntity", at = @At("RETURN"))
	public void addEntity(int id, Entity entity, CallbackInfo ci) {
		EventEntity.Spawn event = new EventEntity.Spawn(mc.world.getEntityById(id));
		Arsenic.eventBus.post(event);
	}

	@Inject(method = "removeEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onRemoved()V"))
	public void removeEntity(int entityId, Entity.RemovalReason removalReason, CallbackInfo ci) {
		EventEntity.Remove event = new EventEntity.Remove(mc.world.getEntityById(entityId));
		Arsenic.eventBus.post(event);
	}

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public DimensionEffects getDimensionEffects() {
		if (MinecraftClient.getInstance().world == null) {
			return dimensionEffects;
		}

		EventSkyRender.Properties event = new EventSkyRender.Properties(dimensionEffects);
		Arsenic.eventBus.post(event);

		return event.getSky();
	}
}
