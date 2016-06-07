package diplom.renderers;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.text.Format;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import javax.swing.table.DefaultTableCellRenderer;

/*
 *	Use a formatter to format the cell Object
 */
public class FormatRenderer extends DefaultTableCellRenderer
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Format formatter;

    /*
     *   Use the specified formatter to format the Object
     */
    public FormatRenderer(Format formatter)
    {
        this.formatter = formatter;
    }

    public void setValue(Object value)
    {
        //  Format the Object before setting its value in the renderer

        try
        {
            if (value != null)
                value = formatter.format(value);
        }
        catch(IllegalArgumentException e) {}

        super.setValue(value);
    }

    /*
     *  Use the default date/time formatter for the default locale
     */
    public static FormatRenderer getDateTimeRenderer()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        return new FormatRenderer( dateFormat );
    }

    /*
     *  Use the default time formatter for the default locale
     */
    public static FormatRenderer getTimeRenderer()
    {
        return new FormatRenderer( DateFormat.getTimeInstance() );
    }
}