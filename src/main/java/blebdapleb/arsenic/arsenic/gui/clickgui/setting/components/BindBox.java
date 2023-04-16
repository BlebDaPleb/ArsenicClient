package blebdapleb.arsenic.arsenic.gui.clickgui.setting.components;

import blebdapleb.arsenic.arsenic.gui.clickgui.ModuleButton;
import blebdapleb.arsenic.arsenic.gui.clickgui.setting.Component;
import blebdapleb.arsenic.arsenic.module.setting.Setting;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingKey;
import blebdapleb.arsenic.arsenic.util.ArsenicLogger;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

import static blebdapleb.arsenic.arsenic.gui.clickgui.ClickGui.keyDown;

public class BindBox extends Component {

    private SettingKey keySet = setting.asKey();
    public boolean selected = false;

    public BindBox(Setting setting, ModuleButton button, int offset) { super(setting, button, offset); }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset,
                parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height,
                new Color(12, 2, 17, 180).getRGB());

        int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);

        int key = keySet.getValue();
        String name = key < 0 || key == InputUtil.GLFW_KEY_DELETE ? "NONE" : InputUtil.fromKeyCode(keySet.getValue(), -1).getLocalizedText().getString();
        if (name == null)
            name = "KEY" + key;
        else if (name.isEmpty())
            name = "NONE";

        if (selected)
        {
            mc.textRenderer.drawWithShadow(matrices, keySet.getName() + ": " + name,
                    parent.parent.x + textOffset + 2.5f, parent.parent.y + parent.offset + offset + textOffset, Color.RED.getRGB());

            if (keyDown >= 0) {
                keySet.setValue(keyDown);
                selected = false;
            }
        } else {
            mc.textRenderer.drawWithShadow(matrices, keySet.getName() + ": " + name,
                    parent.parent.x + textOffset + 2.5f, parent.parent.y + parent.offset + offset + textOffset, -1);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY))
        {
            selected = true;
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}
