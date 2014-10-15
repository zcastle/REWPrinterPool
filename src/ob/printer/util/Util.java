package ob.printer.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Pilar
 */
public class Util {

    private static final String PATTERN = "#,##0.00";
    private static final Logger logger = Logger.getLogger("MyLog");
    private static FileHandler fh;

    static {
        try {
            fh = new FileHandler("data.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static final String left(String text, int v) {
        return text.concat(Util.repite(" ", v)).substring(0, v);
    }
    
    public static final String left(String text, int v, String c) {
        return text.concat(Util.repite(c, v)).substring(0, v);
    }

    public static final String right(String text, int v) {
        String cadena = Util.repite(" ", v).concat(text);
        return cadena.substring(cadena.length() - v);
    }
    
    public static final String right(String text, int v, String c) {
        String cadena = Util.repite(c, v).concat(text);
        return cadena.substring(cadena.length() - v);
    }

    public static final String repite(String str, int num) {
        int len = num * str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < num; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static final String format(Double value) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern(PATTERN);
        return df.format(value);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warning(String message) {
        logger.warning(message);
    }

    public static final boolean isFileshipAlreadyRunning() {
        try {
            final File file = new File("FileshipReserved");
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        try {
                            fileLock.release();
                            randomAccessFile.close();
                            file.delete();
                        } catch (Exception e) {
                            //log.error("Unable to remove lock file: " + lockFile, e);
                        }
                    }
                });
                return true;
            }
        } catch (Exception e) {
            // log.error("Unable to create and/or lock file: " + lockFile, e);
        }
        return false;
    }
}
