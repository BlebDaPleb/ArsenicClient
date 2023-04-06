package blebdapleb.arsenic.arsenic.module;

import blebdapleb.arsenic.arsenic.Arsenic;
import blebdapleb.arsenic.arsenic.util.ArsenicLogger;
import net.minecraft.client.MinecraftClient;

public class Module {

    public static final int KEY_UNBOUND = -1481058891;

    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    private String name;
    private int key;

    private boolean enabled;
    private final boolean defaultEnabled;
    private boolean subscribed;

    private ModuleCategory category;
    private String desc;

    public Module(String name, int key, ModuleCategory category, String desc)
    {
        this(name, key, category, false ,desc);
    }

    public Module(String name, int key, ModuleCategory category, boolean enabled , String desc)
    {
        this.name = name;
        this.key = key;
        this.category = category;
        this.defaultEnabled = enabled;
        this.desc = desc;

        if (enabled) {
            setEnabled(true);
        }
    }

    public void onEnable(boolean inWorld) {
        subscribed = Arsenic.eventBus.subscribe(this);
        ArsenicLogger.info("Enabled " + this.name);
    }

    public void onDisable(boolean inWorld) {
        if (subscribed) {
            Arsenic.eventBus.unsubscribe(this);
        }

        ArsenicLogger.info("Disabled " + this.name);
    }

    public String getName() {
        return name;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public String getDesc() {
        return desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey() { return key; }

    public void setKey(int key) { this.key = key; }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDefaultEnabled() {
        return defaultEnabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled)
            toggle();
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) {
            onEnable(mc.world != null);
        } else {
            onDisable(mc.world != null);
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Module))
            return false;

        return name.equals(((Module) obj).name);
    }
}
