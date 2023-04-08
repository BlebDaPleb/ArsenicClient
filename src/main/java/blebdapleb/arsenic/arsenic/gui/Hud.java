package blebdapleb.arsenic.arsenic.gui;

import blebdapleb.arsenic.arsenic.module.Module;
import blebdapleb.arsenic.arsenic.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Comparator;
import java.util.List;

public class Hud {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    private static final int aCOLOR = 0x6F2DA8;

    private static TextRenderer tr = mc.textRenderer;

    static String arsenic = "[Arsenic V0.2]";

    public static void render(MatrixStack matrices, float tickDelta)
    {
        if (ModuleManager.getModule(blebdapleb.arsenic.arsenic.module.mods.client.Hud.class).isEnabled())
            renderArrayList(matrices);
    }

    public static void renderArrayList(MatrixStack matrices)
    {
        int index = 0;

        int sWidth = mc.getWindow().getScaledWidth();

        tr.drawWithShadow(matrices, arsenic, (sWidth - 4) - tr.getWidth(arsenic), 10, aCOLOR);

        List<Module> sortedEnabled = ModuleManager.getEnabledModules();
        sortedEnabled.sort(Comparator.comparingInt(m -> tr.getWidth(((Module) m).getName())).reversed());

        for (Module m : sortedEnabled)
        {
            tr.drawWithShadow(matrices, m.getName(), (sWidth - 4) - tr.getWidth(m.getName()),
                    20 + (index * tr.fontHeight), -1);

            index++;
        }
    }
}
