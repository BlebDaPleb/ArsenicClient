package blebdapleb.arsenic.arsenic.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArsenicLogger {

    public static final Logger logger = LogManager.getFormatterLogger("BleachHack");

    public static int ARSENIC_COLOR = 0x321637;

    public static int INFO_COLOR = Formatting.GREEN.getColorValue();
    public static int WARN_COLOR = Formatting.YELLOW.getColorValue();
    public static int ERROR_COLOR = Formatting.RED.getColorValue();

    public static void info(String s) { info(Text.literal(s)); }

    public static void info(Text t) {
        try {
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(getArsenicText(INFO_COLOR)
                            //.append("\u00a73\u00a7lINFO: \u00a73")
                            .append(((MutableText) t).styled(s -> s.withColor(INFO_COLOR))));
        } catch (Exception e) {
            logger.log(Level.INFO, t.getString());
        }
    }

    public static void warn(String s) { warn(Text.literal(s)); }

    public static void warn(Text t) {
        try {
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(getArsenicText(WARN_COLOR)
                            //.append("\u00a7e\u00a7lWARN: \u00a7e")
                            .append(((MutableText) t).styled(s -> s.withColor(WARN_COLOR))));
        } catch (Exception e) {
            logger.log(Level.WARN, t.getString());
        }
    }

    public static void error(String s) {
        error(Text.literal(s));
    }

    public static void error(Text t) {
        try {
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(getArsenicText(ERROR_COLOR)
                            //.append("\u00a7c\u00a7lERROR: \u00a7c")
                            .append(((MutableText) t).styled(s -> s.withColor(ERROR_COLOR))));
        } catch (Exception e) {
            logger.log(Level.ERROR, t.getString());
        }
    }

    private static MutableText getArsenicText(int color) {
        return Text.literal("[").styled(s -> s.withColor(color))
                .append(Text.literal("Arsenic")).styled(s -> s.withColor(ARSENIC_COLOR))
                .append(Text.literal("] ").styled(s -> s.withColor(color)));
    }

}
