package blebdapleb.arsenic.arsenic.module.setting.settings;

import blebdapleb.arsenic.arsenic.module.setting.Setting;

import java.util.Arrays;
import java.util.List;

public class SettingMode extends Setting {

    private String mode;
    private List<String> modes;
    private int index;

    public SettingMode(String name, String defaultMode, String... modes) {
        super(name);
        this.modes = Arrays.asList(modes);
        this.mode = defaultMode;
        this.index = this.modes.indexOf(defaultMode);
    }

    public String getMode() { return mode; }
    public void setMode(String mode) {
        this.mode = mode;
        this.index = modes.indexOf(mode);
    }

    public boolean isMode(String mode)
    {
        return this.mode.equals(mode);
    }

    public List<String> getModes() { return modes; }

    public int getIndex() { return index; }
    public void setIndex(int index) {
        this.index = index;
        this.mode = modes.get(index);
    }

    public void cycle()
    {
        if (index < modes.size() - 1)
        {
            index++;
            mode = modes.get(index);
        } else if (index >= modes.size() - 1)
        {
            index = 0;
            mode = modes.get(0);
        }
    }
}
