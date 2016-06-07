package diplom.formats;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;


public class AppFormat {
    public static NumberFormat getMoneyFormat() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) numberFormat).getDecimalFormatSymbols();

        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) numberFormat).setDecimalFormatSymbols(decimalFormatSymbols);
        return numberFormat;
    }

    public static DateFormat getMyDateFormat(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat;
    }

}
