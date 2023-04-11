package blebdapleb.arsenic.arsenic.module.setting.settings;

import blebdapleb.arsenic.arsenic.module.setting.Setting;

public class SettingKey extends Setting {

    private int key;

    public SettingKey(String name, int key) {
        super(name);

        this.key = key;
    }

    public int getValue() { return key; }
    public void setValue(int key) { this.key = key; }
}
