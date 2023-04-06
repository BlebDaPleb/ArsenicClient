package blebdapleb.arsenic.arsenic.module;

import blebdapleb.arsenic.arsenic.Arsenic;
import blebdapleb.arsenic.arsenic.module.mods.combat.Hitbox;
import blebdapleb.arsenic.arsenic.util.ArsenicLogger;
import blebdapleb.arsenic.arsenic.util.collections.NameableStorage;
import com.google.gson.annotations.SerializedName;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.apache.commons.io.IOUtils;
import org.lwjgl.glfw.GLFW;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {

    private static final NameableStorage<Module> MODULES = new NameableStorage<>(Module::getName);

    public ModuleManager() { init(); }

    public void init() {
        // COMBAT
        MODULES.add(new Hitbox());

        // MOVEMENT

        // RENDER

        // PLAYER

        // MISC

    }

    public static Iterable<Module> getModules() {
        return MODULES.values();
    }

    public static Module getModule(String name) {
        return MODULES.get(name);
    }

    public static <M extends Module> M getModule(Class<M> class_) {
        return MODULES.get(class_);
    }

    public static List<Module> getModulesInCat(ModuleCategory cat) {
        return MODULES.stream().filter(m -> m.getCategory().equals(cat)).collect(Collectors.toList());
    }

    public static void handleKey(int key) {
        if (!InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_F3)) {
            for (Module m: getModules()) {
                if (m.getKey() == key) {
                    m.toggle();
                }
            }
        }
    }
}
