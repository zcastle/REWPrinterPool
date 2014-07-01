package ob.priner.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Pilar
 */
public class REWPrinter {

    private final FileWriter fw;
    private final BufferedWriter bw;
    private final PrintWriter pw;

    public REWPrinter(String printer) throws IOException {
        fw = new FileWriter(printer);
        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw);
        pw.write(CLEAR_BUFFER);
    }

    public void println(String text) {
        pw.println(text.toUpperCase());
    }

    public void print(String text) {
        pw.print(text.toUpperCase());
    }

    public void cut() {
        pw.write(CUT_PAPER);
    }

    public void setFont(char[] font) {
        pw.write(font);
    }

    public void setDoubleHeight(boolean doble) {
        if (doble) {
            pw.write(FONT_DOUBLE_HEIGHT);
        } else {
            pw.write(FONT_NORMAL_HEIGHT);
        }
    }

    public void feed(int line) {
        pw.write(new char[]{0x1B, 0x64, (char) line});
    }

    public void feed() {
        pw.write(FEED_LINE);
    }

    public void DrawerKick() {
        pw.write(DRAWER_KICK);
    }

    public void setCenter(boolean center) {
        if (center) {
            pw.write(TEXT_CENTER);
        } else {
            pw.write(TEXT_LEFT);
        }
    }

    public void send() {
        pw.close();
    }

    //private final char[] CUT_PAPER = new char[]{0x1B, 'm'};
    private final char[] CLEAR_BUFFER = new char[]{0x1B, 0x40};

    private final char[] CUT_PAPER = new char[]{0x1D, 0x56, 66, 0};

    public static final char[] FONT_A = new char[]{0x1B, 0x4D, 0};
    public static final char[] FONT_B = new char[]{0x1B, 0x4D, 1};
    public static final char[] FONT_C = new char[]{0x1B, 0x4D, 2};
    //public static final char[] FONT_D = new char[]{0x1B, 0x4D, 41};

    private final char[] FONT_DOUBLE_HEIGHT = new char[]{0x1B, 0x21, 17};
    private final char[] FONT_NORMAL_HEIGHT = new char[]{0x1B, 0x21, 0};

    private final char[] DRAWER_KICK = new char[]{0x70, 0x0, 60, 120};

    private final char[] TEXT_CENTER = new char[]{0x1B, 'a', 1};
    private final char[] TEXT_LEFT = new char[]{0x1B, 'a', 0};

    private final char[] FEED_LINE = new char[]{0xA};

}
