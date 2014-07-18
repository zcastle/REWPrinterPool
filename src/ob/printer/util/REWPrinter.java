package ob.printer.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Pilar
 */
public class REWPrinter {

    private FileWriter fw;
    private BufferedWriter bw;
    private final PrintWriter pw;
    private Socket socket=null;

    public REWPrinter(String printer) throws IOException {
        if (printer.substring(0, 2).equals("\\") || printer.substring(0, 1).equals("/")) {
            fw = new FileWriter(printer);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
        } else {
            socket = new Socket(printer, 9100);
            pw = new PrintWriter(socket.getOutputStream(), true);
        }
        
        pw.write(CLEAR_BUFFER);
    }
    
    public boolean test() throws IOException{
        println("MENSAJE DE PRUEBA");
        cut();
        return send();
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

    public boolean send() throws IOException {
        boolean success = !pw.checkError();
        pw.close();
        if(socket!=null){
            if(socket.isConnected()) socket.close();
        }
        return success;
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
