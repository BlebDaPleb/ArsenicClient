package blebdapleb.arsenic.arsenic.gui.clickgui.setting;

import blebdapleb.arsenic.arsenic.gui.clickgui.ModuleButton;
import blebdapleb.arsenic.arsenic.module.setting.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class Component {

    public Setting setting;
    public ModuleButton parent;
    public int offset;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Component(Setting setting, ModuleButton button, int offset)
    {
        this.setting = setting;
        this.parent = button;
        this.offset = offset;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {

    }

    public void mouseClicked(double mouseX, double mouseY, int button)
    {

    }

    public void mouseReleased(double mouseX, double mouseY, int button)
    {
        
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {

    }

    public boolean isHovered(double mouseX, double mouseY)
    {
        return mouseX > parent.parent.x && mouseX < parent.parent.x + parent.parent.width &&
                mouseY > parent.parent.y + parent.offset + offset && mouseY < parent.parent.y + parent.offset + offset + parent.parent.height;
    }

}
