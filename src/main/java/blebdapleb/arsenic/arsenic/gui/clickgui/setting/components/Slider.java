package blebdapleb.arsenic.arsenic.gui.clickgui.setting.components;

import blebdapleb.arsenic.arsenic.gui.clickgui.ModuleButton;
import blebdapleb.arsenic.arsenic.gui.clickgui.setting.Component;
import blebdapleb.arsenic.arsenic.module.setting.Setting;
import blebdapleb.arsenic.arsenic.module.setting.settings.SettingNumber;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Component {

    private SettingNumber numSet = setting.asNumber();

    private boolean sliding = false;

    public Slider(Setting setting, ModuleButton button, int offset) {
        super(setting, button, offset);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset,
                parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height,
                new Color(12, 2, 17, 180).getRGB());

        double diff = Math.min(parent.parent.width, Math.max(0, mouseX - parent.parent.x));
        int renderWidth = (int) (parent.parent.width * (numSet.getValue() - numSet.getMin())
                        / (numSet.getMax() - numSet.getMin()));

        DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset,
                parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height,
                new Color(0x8200c8).getRGB());

        if (sliding)
        {
            if (diff == 0)
                numSet.setValue(numSet.getMin());
            else
                numSet.setValue(roundToPlace(((diff / parent.parent.width) *
                        (numSet.getMax() - numSet.getMin()) + numSet.getMin()), 2));
        }

        int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);

        mc.textRenderer.drawWithShadow(matrices, numSet.getName() + ": " + roundToPlace(numSet.getValue(), 2),
                parent.parent.x + textOffset + 2.5f, parent.parent.y + parent.offset + offset + textOffset, -1);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY))
            sliding = true;
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        sliding = false;
        super.mouseReleased(mouseX, mouseY, button);
    }

    public double roundToPlace(double value, int place)
    {
        if (place < 0)
            return value;

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(place, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
