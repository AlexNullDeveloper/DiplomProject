package diplom.headbook.presenter;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import diplom.headbook.initializers.BottomLineReestrInitializer;
import diplom.headbook.initializers.TopLineButtonAndFieldsInitializer;
import diplom.renderers.FormatRenderer;
import diplom.renderers.NumberRenderer;

import diplom.headbook.model.*;
/**
 * Фрейм для реестра документов
 *
 * @author aleksander_talismanov
 * @version alpha 1.00 11.03.2016
 */
public class ReestrDocumentsFrame extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static int x = 0;
    private static Connection connect = null;
    private static JTable jTableDocuments = null;
    private static JPanel pane;
    private static TableColumnModel tableColumnModel = null;
    private static DefaultTableModel tableModelTRN = null;
    private static JScrollPane jscrollPane = null;
    private static JButton buttonFindDocuments = null;
    private static JButton buttonEdit = null;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 450;
    //

    // здесь мы будем хранить названия столбцов
    private static Vector<Object> columnNames = new Vector<Object>();
    // список типов столбцов
    private static Vector<Object> columnTypes = new Vector<Object>();
    // хранилище для полученных данных из базы данных
    private static Vector<Vector<Object>> data = new Vector<Vector<Object>>();

    private static DefaultTableModel tableModel;

    private JButton buttonDelete;

    public static DefaultTableModel getTableModel(){
        return tableModel;
    }

    public static JScrollPane getJScrollPane(){
        return jscrollPane;
    }


    /**
     * конструктор фрейма реестра документов
     */
    public ReestrDocumentsFrame(String nameOfFrame,Connection connection){
        super(nameOfFrame);
        //берем размеры скрина на будущее
        //Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        connect = connection;
        pane = (JPanel) getContentPane();
        setResizable(false);
        pack();
        pane.setLayout(null);

        //верхний ряд
        initTopLine(pane);
        //скролл с табличкой центральная группа растянутая по всю ширину
        jscrollPane = makeJScrollPane();

        //установить формат для полей таблицы.
        setCurrencyAndDatesForTable();

        //баг jscrollPane.getVerticalScrollBar().addAdjustmentListener(new MyAdjustmentListener());
        jscrollPane.setBounds(2,25,790,200);
        pane.add(jscrollPane);
        //нижние ярлыки и поля
        initBottomFieldsAndLabels(pane);
        //размер фрейма
        setBounds(500,250,FRAME_WIDTH,FRAME_HEIGHT);
        setVisible(true);


        jTableDocuments.setRowSelectionInterval(0,0);
        fillBottomFieldWithData();


        jTableDocuments.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                fillBottomFieldWithData();
        });

    }

    private void fillBottomFieldWithData() {

        //System.out.println(selectedRowIndex);
        //индекс первой колонки в которой номер документа
        final int DOG_NUM_INDEX = 0;
        final int DATE_SUCCESS_INDEX = 1;

        int selectedRowIndex = jTableDocuments.getSelectedRow();
        Integer selectedDocNum =  ((BigDecimal) jTableDocuments.getModel().getValueAt(selectedRowIndex, DOG_NUM_INDEX)).intValue();

        java.sql.Timestamp tempTimeStamp = ((java.sql.Timestamp) jTableDocuments.getModel().getValueAt(selectedRowIndex, DATE_SUCCESS_INDEX));
        if (tempTimeStamp != null) {
            java.sql.Date dateSuccess = new java.sql.Date(tempTimeStamp.getTime());
            //Доделать
            BottomLineReestrInitializer.fillWithData(selectedDocNum, dateSuccess);
        }
    }

    public void setCurrencyAndDatesForTable(){
        tableColumnModel = jTableDocuments.getColumnModel();
        try {
            tableColumnModel.getColumn(1).setCellRenderer(FormatRenderer.getDateTimeRenderer());
            tableColumnModel.getColumn(6).setCellRenderer(NumberRenderer.getCurrencyRenderer());
            tableColumnModel.getColumn(7).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        } catch (ArrayIndexOutOfBoundsException AIOOBe) {
            System.out.println("Вектор колонок пуст, тестовый режим?");
        }
    }

    class DeleteDocumentListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int result = JOptionPane.showOptionDialog(ReestrDocumentsFrame.this, "Вы точно хотите удалить документ?",
                    "Удаление документа", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Да", "Нет"}, "Да");

            if (result == JOptionPane.YES_OPTION){

                ReestrDocumentsModel reestrDocumentsModel = new ReestrDocumentsModel();
                int selectedRowIndex = jTableDocuments.getSelectedRow();
                //индекс первой колонки в которой номер документа
                final int DOG_NUM_INDEX = 0;
                Integer selectedDogNum =  ((BigDecimal) jTableDocuments.getModel().getValueAt(selectedRowIndex,
                        DOG_NUM_INDEX)).intValue();
                reestrDocumentsModel.deleteDocument(selectedDogNum);

                tableModel.removeRow(selectedRowIndex);

                jscrollPane.getVerticalScrollBar().setValue(0);

                JOptionPane.showMessageDialog(ReestrDocumentsFrame.this, "Документ успешно удалён.",
                        "Документ удалён",JOptionPane.INFORMATION_MESSAGE);
                jscrollPane.getVerticalScrollBar().setValue(0);
            }

        }
    }



    class FindButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            FindDocumentsFrame findDocumentsFrame = new FindDocumentsFrame("Поиск документа по параметрам", connect);
        }
    }
    /**
     * делаем скролл для панели в него добавляем табличку
     */

    public static JScrollPane makeJScrollPane(){
        ReestrDocumentModel reestrDocumentModel = new ReestrDocumentModel();
        ResultSet resultSet = reestrDocumentModel.getResultSetFromTableTRN();


        try {
            //Таблица с выравниванием по ширине
            if (tableModelTRN == null) {
                tableModelTRN = buildTableModelTRN(resultSet);
            }

            if (jTableDocuments == null) {
                jTableDocuments = new JTable(tableModelTRN) {
                    @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        Component component = super.prepareRenderer(renderer, row, column);
                        int rendererWidth = component.getPreferredSize().width;
                        TableColumn tableColumn = getColumnModel().getColumn(column);
                        tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                        return component;
                    }
                };
            }
        } catch (SQLException SQLe){
            SQLe.printStackTrace();
        }
        jTableDocuments.setPreferredScrollableViewportSize(new Dimension(640,100));
        JScrollPane jscrlp = new JScrollPane(jTableDocuments){
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible(){
                return true;
            }

        };

        return jscrlp;
    }

    /***
     * получение русского названия колонки для отображения
     * @param str входная строка с название колонки в базе данных
     * @return возращаем русское название колонки
     */
    public static String getRusColNameFromEngColName(String str){
        String result = null;
        if (str.equals("DOGNUM")){
            result = "№ документа";
        } else if (str.equals("DATE_SUCCESS")) {
            result = "Дата Проведения";
        } else if (str.equals("ACC_DEB")){
            result = "Счет дебета";
        } else if (str.equals("CUR_DEB")){
            result = "Валюта";
        } else if (str.equals("ACC_CRED")){
            result = "Счет кредита";
        } else if (str.equals("CUR_CRED")){
            result = "Валюта";
        } else if (str.equals("SUM_DEB")){
            result = "Сумма деб.";
        } else if (str.equals("SUM_CRED")){
            result = "Сумма кред.";
        } else {
            //если такой не занесли, то возвращаем какое есть.
            result = str;
        }

        return result;
    }
    /**
     * Делаем модель таблички TRN
     *
     */
    public static DefaultTableModel buildTableModelTRN(ResultSet rs) throws SQLException{
        //System.out.println("buildTableModelTRN method");
        try {
            ResultSetMetaData metaData = rs.getMetaData();

            //получаем количество колонок
            int columnCount = metaData.getColumnCount();
            //имена колонок
            columnNames = new Vector<Object>();
            for (int column = 1; column <= columnCount; column++){
                //реальные названия колонок
                String columnName = metaData.getColumnName(column);
                //получить русские имена для названия колонок.
                String russianColumnName = getRusColNameFromEngColName(columnName);
                columnNames.add(russianColumnName);
            }

            //данные

            while(rs.next()){

                Vector<Object> trnRow = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++){
                    trnRow.add(rs.getObject(columnIndex));
                }
                data.add(trnRow);

            }
        } catch (SQLException SQLe){
            SQLe.printStackTrace();
        } catch (NullPointerException NullPointer){
            System.out.println("connection пустой, тестовый режим окна");
        }

        tableModel = new DefaultTableModel(data,columnNames){
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 6 || column == 7){
                    return BigDecimal.class;
                }
                return Object.class;
            }

        };

        return tableModel;
    }

    /**
     * инициализируем верхнюю панель приложения кнопками и полями
     */
    public void initTopLine(Container pane){
        //верхняя группа
        //buttonFindDocuments = new JButton(new ImageIcon("бинокль2.png"));
        buttonFindDocuments = new JButton("Найти");
        buttonFindDocuments.addActionListener(new FindButtonListener());
        //JButton second = new JButton(new ImageIcon("edit3.png"));
        buttonEdit = new JButton("Редактировать");
        buttonDelete = new JButton("Удалить");
        buttonDelete.addActionListener(new DeleteDocumentListener());

        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane,buttonFindDocuments,x);
        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane,buttonEdit,x);
        x = TopLineButtonAndFieldsInitializer.addNextComponentInLine(pane,buttonDelete,x);

       //На будущее, сейчас нет необходимости

        //обнуляем x для следующего использования
        x = 0;
    }

    /**
     * добавление следующего компонента в строке
     * @param pane Панель на которую добавляем компонент
     *        component сам компонент, который добавляем
     */
    public static void addNextComponentInLine(Container pane, JComponent component){

        Dimension componentSize = component.getPreferredSize();
        Insets insets = pane.getInsets();
        if (component.getClass().getName().equals("javax.swing.JTextField")){
            componentSize.height = 27;
            component.setBounds(insets.left+x,insets.top,componentSize.width+150, componentSize.height-5);
            x += componentSize.width+150;
        } else {
            component.setBounds(insets.left+x,insets.top,componentSize.width, componentSize.height);
            x += componentSize.width;
        }

        pane.add(component);
    }
    /**
     * инициализируем комноненты нижней части панели
     */
    public static void initBottomFieldsAndLabels(Container pane){

        BottomLineReestrInitializer.initBO1LabelsAndFields(pane);

        BottomLineReestrInitializer.initBO2LabelsAndFields(pane);

        BottomLineReestrInitializer.initEquivalentLabelsAndFields(pane);

        BottomLineReestrInitializer.initPachkaLabelsAndFields(pane);

        BottomLineReestrInitializer.initPayerLabelsAndFields(pane);

        BottomLineReestrInitializer.initRecipientLabelsAndFields(pane);

        BottomLineReestrInitializer.initDateDocLabelsAndFields(pane);
        //INN
        BottomLineReestrInitializer.initInnLabelsAndFields(pane);
        //КПП
        BottomLineReestrInitializer.initKPPLabelsAndFields(pane);

        BottomLineReestrInitializer.initDateRegDocLabelsAndFields(pane);

        BottomLineReestrInitializer.initCurrencyDateLabelsAndFields(pane);

        BottomLineReestrInitializer.initSupplyDateLabelsAndFields(pane);

        BottomLineReestrInitializer.initTypeOperLabelsAndFields(pane);

        BottomLineReestrInitializer.initQueuePlatLabelsAndFields(pane);

        BottomLineReestrInitializer.initNaznPlatLabelsAndFields(pane);

        BottomLineReestrInitializer.initWhoRegisteredLabelsAndFields(pane);

        BottomLineReestrInitializer.initWhoControlledLabelsAndFields(pane);

    }

    // получение данных из объекта ResultSet
    public static void setDataSource(ResultSet rs) throws Exception {

        System.out.println("setDataSource method");

        // удаляем прежние данные
        data.clear();
        columnNames.clear();
        columnTypes.clear();

        // получаем вспомогательную информацию о столбцах
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for ( int i=0; i<columnCount; i++) {
            // название столбца
            columnNames.add(getRusColNameFromEngColName(rsmd.getColumnName(i+1)));
            // тип столбца
            Class<?> type = Class.forName(rsmd.getColumnClassName(i+1));
            columnTypes.add(type);
        }
        // получаем данные

        while ( rs.next() ) {
            // здесь будем хранить ячейки одной строки
            Vector<Object> row = new Vector<Object>();
            for ( int i=0; i<columnCount; i++) {
                if (columnTypes.get(i) == String.class)
                    row.add(rs.getString(i+1));
                else
                    row.add(rs.getObject(i+1));
            }
            synchronized (data) {
                data.add(row);
            }
        }

        tableModelTRN = new DefaultTableModel(data,columnNames){
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 6 || column == 7){
                    return BigDecimal.class;
                }
                return Object.class;
            }
        };



        jTableDocuments = new JTable(tableModelTRN);
        jscrollPane = new JScrollPane(jTableDocuments){
            @Override
            public boolean isVisible(){
                return true;
            }

        };


        pane.add(jscrollPane);

        jTableDocuments.revalidate();
        jTableDocuments.repaint();

        pane.repaint();

        // сообщаем об изменениях в структуре данных
        tableModelTRN.fireTableStructureChanged();

        System.out.println("fireTableStructureChanged()");
        jscrollPane.setBounds(2,25,790,200);
        jscrollPane.revalidate();
        jscrollPane.repaint();
        jscrollPane.setVisible(true);
        jscrollPane.revalidate();
        pane.repaint();
        jTableDocuments.revalidate();
        jscrollPane.setVisible(true);
        //jTableDocuments.updateUI();
    }

    public static void main(String[] args){
        ReestrDocumentsFrame reestrDocumentsFrame = new ReestrDocumentsFrame("Реестр документопроводок",connect);
    }
}
