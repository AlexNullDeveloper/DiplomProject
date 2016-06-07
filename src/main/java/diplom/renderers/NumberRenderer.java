package diplom.renderers;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.SwingConstants;

public class NumberRenderer extends FormatRenderer
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*
     *  Use the specified number formatter and right align the text
     */
    public NumberRenderer(NumberFormat formatter)
    {
        super(formatter);
        setHorizontalAlignment( SwingConstants.RIGHT );
    }

    /*
     *  Use the default currency formatter for the default locale
     */
    public static NumberRenderer getCurrencyRenderer()
    {

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) numberFormat).getDecimalFormatSymbols();

        //убираем символ валюты для Locale ("ru", "RU") - "руб." А для us US - "$"
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) numberFormat).setDecimalFormatSymbols(decimalFormatSymbols);
        return new NumberRenderer( numberFormat );
        //return new NumberRenderer( NumberFormat.getCurrencyInstance(new Locale("ru", "RU")  ) );
    }

    /*
     *  Use the default integer formatter for the default locale
     */
    public static NumberRenderer getIntegerRenderer()
    {
        return new NumberRenderer( NumberFormat.getIntegerInstance(new Locale("ru", "RU")) );
    }

    /*
     *  Use the default percent formatter for the default locale
     */
    public static NumberRenderer getPercentRenderer()
    {
        return new NumberRenderer( NumberFormat.getPercentInstance() );
    }
}