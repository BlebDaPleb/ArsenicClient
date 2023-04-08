package blebdapleb.arsenic.arsenic.gui.clickgui;

import blebdapleb.arsenic.arsenic.gui.clickgui.setting.Component;
import blebdapleb.arsenic.arsenic.gui.clickgui.setting.components.CheckBox;
import blebdapleb.arsenic.arsenic.gui.clickgui.setting.components.ModeBox;
import blebdapleb.arsenic.arsenic.gui.clickgui.setting.components.Slider;
import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.setting.Setting;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingBoolean;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingMode;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton {

    public Module module;
    public Frame parent;
    public int offset;
    public List<Component> components;
    public boolean extended;

    public ModuleButton(Module module, Frame parent, int offset)
    {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
        this.extended = false;
        this.components = new ArrayList<>();

        int setOffset = parent.height;
        for (Setting setting : module.getSettings())
        {
            if (setting instanceof SettingBoolean)
                components.add(new CheckBox(setting, this, setOffset));
            else if (setting instanceof SettingMode)
                components.add(new ModeBox(setting, this, setOffset));
            else if (setting instanceof SettingNumber)
                components.add(new Slider(setting, this, setOffset));
            setOffset += parent.height;
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        int textOffset = ((parent.height / 2) - parent.mc.textRenderer.fontHeight / 2);

        DrawableHelper.fill(matrices, parent.x, parent.y + offset,
                parent.x + parent.width, parent.y + offset + parent.height,
                new Color(12, 2, 17, 180).getRGB());
        if (isHovered(mouseX, mouseY))
        {
            DrawableHelper.fill(matrices, parent.x, parent.y + offset,
                    parent.x + parent.width, parent.y + offset + parent.height,
                    new Color(41, 10, 63, 140).getRGB());
        }
        parent.mc.textRenderer.drawWithShadow(matrices, module.getName(), parent.x + textOffset, parent.y + offset + textOffset,
                module.isEnabled() ? ClickGui.frameColor : -1);

        if (extended) {
            for (Component component : components) {
                component.render(matrices, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button)
    {
        if (isHovered(mouseX, mouseY)) // LEFT CLICK
        {
            if (button == 0)
                module.toggle();
            else if (button == 1) // RIGHT CLICK
            {
                extended = !extended;
                parent.updateButtons();
            }
        }

        for (Component component : components) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button)
    {
        for (Component component : components) {
            component.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(double mouseX, double mouseY)
    {
        return mouseX > parent.x && mouseX < parent.x + parent.width &&
                mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }

}
