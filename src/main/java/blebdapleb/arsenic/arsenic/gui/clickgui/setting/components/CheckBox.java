package blebdapleb.arsenic.arsenic.gui.clickgui.setting.components;

import blebdapleb.arsenic.arsenic.gui.clickgui.ModuleButton;
import blebdapleb.arsenic.arsenic.gui.clickgui.setting.Component;
import blebdapleb.arsenic.arsenic.module.setting.Setting;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingBoolean;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class CheckBox extends Component {

    private SettingBoolean boolSet = setting.asBoolean();

    public CheckBox(Setting setting, ModuleButton button, int offset) {
        super(setting, button, offset);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset,
                parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height,
                new Color(12, 2, 17, 180).getRGB());

        int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);

        mc.textRenderer.drawWithShadow(matrices, boolSet.getName() + ": " + boolSet.isEnabled(),
                parent.parent.x + textOffset + 3.5f, parent.parent.y + parent.offset + offset + textOffset, -1);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0)
            boolSet.toggle();
        super.mouseClicked(mouseX, mouseY, button);
    }
}
