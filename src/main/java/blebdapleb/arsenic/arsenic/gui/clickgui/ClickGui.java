package blebdapleb.arsenic.arsenic.gui.clickgui;

import blebdapleb.arsenic.arsenic.module.ModuleCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends Screen {

    public static final ClickGui INSTANCE = new ClickGui();

    public List<Frame> frames;

    public static int frameColor = new Color(160, 39, 229).getRGB();

    private ClickGui()
    {
        super(Text.literal("Click GUI"));

        frames = new ArrayList<>();
        int offsetX = 20;
        int width = 100;
        int height = 20;
        int padding = 20;

        for (ModuleCategory category : ModuleCategory.values())
        {
            frames.add(new Frame(category, offsetX, 20, width, height, Color.magenta.getRGB()));
            offsetX += width + padding;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        for (Frame frame : frames)
        {
            frame.render(matrices, mouseX, mouseY, delta);
            frame.updatePosition(mouseX, mouseY);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Frame frame : frames)
        {
            frame.mouseClicked(mouseX, mouseY, button);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Frame frame : frames)
        {
            frame.mouseReleased(mouseX, mouseY, button);
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
