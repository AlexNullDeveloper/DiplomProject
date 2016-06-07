package diplom.registration;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import diplom.headbook.initializers.TopLineButtonAndFieldsInitializer;
/**
 * Окно каталога клиентов
 *
 * @author александр талисманов
 * @version альфа 1.00 14.03.2016
 */
public class ClientsCatalogFrame extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static int x = 0;

    public ClientsCatalogFrame(String nameOfFrame)   {
        super(nameOfFrame);
        Container pane = getContentPane();
        setResizable(false);
        pane.setLayout(null);

        initTopLine(pane);

        //незабыть рамку
        //скролл с табличкой центральная группа растянутая по всю ширину
        JScrollPane jscrollPane = makeJScrollPane();
        jscrollPane.setBounds(2,25,125,245);
        pane.add(jscrollPane);


        pack();
        setBounds(500,250,800,600);
        setVisible(true);
    }

    public static JScrollPane makeJScrollPane(){
        Object[] headers = initTableHeaders();
        Object[][] clientIds = initTableData();

        //Таблица с выравниванием по ширине
        JTable jTableClientIds = new JTable(clientIds,headers){
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };

        //jTableDocuments.setPreferredScrollableViewportSize(new Dimension(640,480));
        jTableClientIds.setPreferredScrollableViewportSize(new Dimension(200,100));
        JScrollPane jscrlp = new JScrollPane(jTableClientIds);

        return jscrlp;
    }

    public static Object[] initTableHeaders(){
        Object[] headers = {"№ клиента"};

        return headers;
    }

    public static Object[][] initTableData(){
        Object[][] data = {
                {"00001"},
                {"00005"},
                {"00006"},
                {"00123"},
                {"0021323"},
                {"000333"},
                {"0003323"},
                {"100005"},
                {"0033233005"},
                {"003323005"},
                {"003211005"},
                {"003333005"},
                {"00344005"},
                {"0055553005"},
                {"00777005"},
                {"00883005"},
                {"009923005"},
        };

        return data;
    }



    public void initTopLine(Container pane){
        JButton button1 = new JButton("first");
        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane, button1, x);

        JButton button2 = new JButton("second");
        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane, button2, x);

        JButton button3 = new JButton("third");
        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane, button3, x);

        JButton button4 = new JButton("fourth");
        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane, button4, x);

        JButton button5 = new JButton("fifth");
        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane, button5, x);

        JButton button6 = new JButton("sixth");
        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane, button6, x);

        JButton button7 = new JButton("Seventh");
        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane, button7, x);

        JButton button8 = new JButton("eighth");
        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane, button8, x);
        //сброс позиции икс
        x = 0;
    }

    public static void main(String[] args){
        ClientsCatalogFrame clientsCatalogFrame = new ClientsCatalogFrame("Каталог клиентов");
    }
}
