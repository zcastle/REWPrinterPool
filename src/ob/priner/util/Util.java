package ob.priner.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Pilar
 */
public class Util {

    private static final String PATTERN = "#,##0.00";

    public static final String left(String text, int v) {
        return text.concat(Util.repite(" ", v)).substring(0, v);
    }

    public static final String right(String text, int v) {
        String cadena = Util.repite(" ", v).concat(text);
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
}
