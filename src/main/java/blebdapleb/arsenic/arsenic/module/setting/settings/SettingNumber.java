package blebdapleb.arsenic.arsenic.module.setting.settings;

import blebdapleb.arsenic.arsenic.module.setting.Setting;

public class SettingNumber extends Setting {

    private double min;
    private double max;

    private double increment;
    private double value;

    public SettingNumber(String name, double min, double max, double value, double increment) {
        super(name);
        this.min = min;
        this.max = max;
        this.value = value;
        this.increment = increment;
    }

    public static double clamp(double value, double min, double max)
    {
        value = Math.max(min, value);
        value = Math.min(max, value);
        return value;
    }

    public double getIncrement() { return increment; }

    public double getValue() { return value; }
    public float getValueFloat() { return (float) value; }
    public int getValueInt() { return (int) value; }

    public void setValue(double value) {
        value = clamp(value, this.min, this.max);
        value = Math.round(value * (1.0 / increment)) / (1.0 / increment);
        this.value = value;
    }

    public void increment(boolean positive)
    {
        if (positive)
            setValue(getValue() + getIncrement());
        else
            setValue(getValue() - getIncrement());
    }

    public double getMin() { return min; }

    public double getMax() { return max; }
}
