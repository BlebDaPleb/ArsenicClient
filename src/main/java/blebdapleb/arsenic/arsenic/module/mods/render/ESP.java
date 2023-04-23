package blebdapleb.arsenic.arsenic.module.mods.render;

import blebdapleb.arsenic.arsenic.event.events.EventEntityRender;
import blebdapleb.arsenic.arsenic.event.events.EventWorldRender;
import blebdapleb.arsenic.arsenic.eventbus.ArsenicSubscribe;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import blebdapleb.arsenic.arsenic.module.ModuleManager;
import blebdapleb.arsenic.arsenic.module.mods.combat.Hitbox;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingMode;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import blebdapleb.arsenic.arsenic.util.render.Renderer;
import blebdapleb.arsenic.arsenic.util.render.color.QuadColor;
import blebdapleb.arsenic.arsenic.util.shader.ArsenicCoreShaders;
import blebdapleb.arsenic.arsenic.util.shader.ColorVertexConsumerProvider;
import blebdapleb.arsenic.arsenic.util.shader.ShaderEffectWrapper;
import blebdapleb.arsenic.arsenic.util.shader.ShaderLoader;
import com.google.gson.JsonSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;

import java.io.IOException;

public class ESP extends Module {

    private ShaderEffectWrapper shader;
    private ColorVertexConsumerProvider colorVertexer;

    private Identifier id = new Identifier("arsenic", "shaders/post/entity_outline.json");
    private boolean shaderInitialized = false;

    public ESP() {
        super("ESP", KEY_UNBOUND, ModuleCategory.RENDER, "Shows entities & blocks throw walls.",
                new SettingMode("Render", "Shader", "Shader", "Box"),
                new SettingNumber("ShaderFill", 1, 255, 50, 1),
                new SettingNumber("Box", 1, 5, 1, 1),
                new SettingNumber("BoxFill", 0, 255, 50, 1)
        );
    }

    private void initShader() {
        if (!shaderInitialized) {
            try {
                shader = new ShaderEffectWrapper(
                        ShaderLoader.loadEffect(mc.getFramebuffer(), id));

                colorVertexer = new ColorVertexConsumerProvider(shader.getFramebuffer("main"), ArsenicCoreShaders::getColorOverlayShader);
            } catch (JsonSyntaxException | IOException e) {
                throw new RuntimeException("Failed to initialize ESP Shader! loaded too early?", e);
            }
        }
        shaderInitialized = true;
    }

    @ArsenicSubscribe
    public void onWorldRender(EventWorldRender.Pre event) {
        initShader();

        shader.prepare();
        shader.clearFramebuffer("main");
    }

    @ArsenicSubscribe
    public void onEntityRender(EventEntityRender.Single.Pre event) {
        if (getSetting(0).asMode().isMode("Box"))
            return;

        int[] color = getColor(event.getEntity());
        int fill = getSetting(1).asNumber().getValueInt();

        if (color != null)
            event.setVertex(colorVertexer.createDualProvider(event.getVertex(), color[0], color[1], color[2], fill));
    }

    @ArsenicSubscribe
    public void onWorldRender(EventWorldRender.Post event) {
        if (!isEnabled()) return;

        if (getSetting(0).asMode().isMode("Shader")) {
            colorVertexer.draw();
            shader.render();
            shader.drawFramebufferToMain("main");
        } else {
            float width = getSetting(2).asNumber().getValueFloat();
            int fill = getSetting(3).asNumber().getValueInt();

            for (Entity e : mc.world.getEntities()) {
                int[] color = getColor(e);

                if (color != null){
                    Module hitbox = ModuleManager.getModule(Hitbox.class);
                    double expand = hitbox.getSetting(0).asNumber().getValue();

                    Box box = hitbox.isEnabled() ? e.getBoundingBox().expand(expand) : e.getBoundingBox();

                    Renderer.drawBoxOutline(box, QuadColor.single(color[0], color[1], color[2], 255), width);

                    if (fill != 0)
                        Renderer.drawBoxFill(box, QuadColor.single(color[0], color[1], color[2], fill));
                }
            }
        }
    }

    private int[] getColor(Entity e)
    {
        if (e == mc.player)
            return null;

        if (e instanceof PlayerEntity)
            return new int[]{255, 0, 0};
        else if (e instanceof MobEntity)
            return new int[]{0, 255, 0};

        return null;
    }

}
