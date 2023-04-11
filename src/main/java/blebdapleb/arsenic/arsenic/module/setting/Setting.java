package blebdapleb.arsenic.arsenic.module.setting;

import blebdapleb.arsenic.arsenic.module.setting.settings.SettingBoolean;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingKey;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingMode;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;

public class Setting {

    private String name;

    public Setting(String name)
    {
        this.name = name;
    }

    private boolean visible = true;

    public String getName() { return name; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }

    public SettingMode asMode()
    {
        try {
            return (SettingMode) this;
        } catch (Exception e) {
            throw new ClassCastException("Exception parsing setting: " + this);
        }
    }

    public SettingNumber asNumber()
    {
        try {
            return (SettingNumber) this;
        } catch (Exception e) {
            throw new ClassCastException("Exception parsing setting: " + this);
        }
    }

    public SettingBoolean asBoolean()
    {
        try {
            return (SettingBoolean) this;
        } catch (Exception e) {
            throw new ClassCastException("Exception parsing setting: " + this);
        }
    }

    public SettingKey asKey()
    {
        try {
            return (SettingKey) this;
        } catch (Exception e) {
            throw new ClassCastException("Exception parsing setting: " + this);
        }
    }

}
