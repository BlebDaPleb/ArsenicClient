package blebdapleb.arsenic.arsenic.module.setting;

public class Setting {

    private String name;

    public Setting(String name)
    {
        this.name = name;
    }

    public boolean visible = true;

    public String getName() { return name; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }



}
