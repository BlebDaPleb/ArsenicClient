package blebdapleb.arsenic.arsenic.module.setting.settings;

import blebdapleb.arsenic.arsenic.module.setting.Setting;

public class SettingBoolean extends Setting {

    private boolean enabled;

    public SettingBoolean(String name, boolean defaultValue)
    {
        super(name);
        this.enabled = defaultValue;
    }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public void toggle()
    {
        this.enabled = !this.enabled;
    }

}
