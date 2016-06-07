package diplom.headbook.presenter;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Write a description of class FindDocumentsFrame here.
 *
 * @author aleksander_talismanov
 * @version 17.03.2016
 */
public class FindDocumentsFrame extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Connection connection = null;
    private JTextField fieldNumDog;
    private JTextField fieldDateSuccess;
    private JTextField fieldAccDeb;
    private JTextField fieldCurDeb;
    private JTextField fieldAccCred;
    private JTextField fieldCurCred;
    private JTextField fieldSumDeb;
    private JTextField fieldSumCred;
    private JPanel pane;
    private JButton buttonOK;
    private ResultSet rs = null;
    private PreparedStatement prepStmt = null;

    public FindDocumentsFrame(String nameOfFrame, Connection connect){
        super(nameOfFrame);
        this.connection = connect;

        setResizable(false);
        //Container pane = getContentPane();
        pane = (JPanel) getContentPane();
        //JPanel p = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //Insets insets = new Insets(5, 5, 5, 5);
        pane.setLayout(new GridLayout(9,2,5,5));
        initLabelsAndFields(pane);
        setBounds(500,250,400,100);
        getRootPane().setDefaultButton(buttonOK);
        pack();
        setVisible(true);
    }

    public void initLabelsAndFields(Container pane){
        pane.add(new JLabel("  Поиск по номеру документа"));
        fieldNumDog = new JTextField("");
        pane.add(fieldNumDog);

        pane.add(new JLabel("  Поиск за дату проведения"));
        fieldDateSuccess = new JTextField("");
        pane.add(fieldDateSuccess);

        pane.add(new JLabel("  Поиск по счету дебета"));
        fieldAccDeb = new JTextField("");
        pane.add(fieldAccDeb);

        pane.add(new JLabel("  Поиск по валюте дебета"));
        fieldCurDeb = new JTextField("");
        pane.add(fieldCurDeb);

        pane.add(new JLabel("  Поиск по счету кредита"));
        fieldAccCred = new JTextField("");
        pane.add(fieldAccCred);

        pane.add(new JLabel("  Поиск по валюте кредита"));
        fieldCurCred = new JTextField("");
        pane.add(fieldCurCred);

        pane.add(new JLabel("  Поиск по сумме дебета"));
        fieldSumDeb = new JTextField("");
        pane.add(fieldSumDeb);

        pane.add(new JLabel("  Поиск по сумме кредита"));
        fieldSumCred = new JTextField("");
        pane.add(fieldSumCred);

        buttonOK = new JButton(" OK ");
        pane.add(buttonOK);
        buttonOK.addActionListener(new OKitemListener());
        //слушателя на кнопку ОК добавить
        JButton buttonCancel = new JButton("Отмена");
        pane.add(buttonCancel);
        buttonCancel.addActionListener(new CancelItemListener());

        //pane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        //pane.setBorder(new EmptyBorder(30,30,30,30));
    }

    class CancelItemListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            dispose();
        }
    }

    class OKitemListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            getDataWithWhereOption();
            //запрашивает селект и возвращаем табличку
            dispose();
        }
    }

    public void getDataWithWhereOption(){
        String selectDataTrnWithValueOfTextFieldsSQL = getFinalSqlString();
        try {
            prepStmt = connection.prepareStatement(selectDataTrnWithValueOfTextFieldsSQL,rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_UPDATABLE);
            rs = prepStmt.executeQuery();

            ReestrDocumentsFrame.setDataSource(rs);
            //добавляем строчку как заглушку
            ReestrDocumentsFrame.getTableModel().addRow(new String[] {"empty","empty2"});
            //потом её удалим и проскролим наверх
            rs.last();
            int lastrow = rs.getRow();
            System.out.println("lastrow of ResultSet = " + lastrow);
            ReestrDocumentsFrame.getTableModel().removeRow(lastrow);


            ReestrDocumentsFrame.getJScrollPane().getVerticalScrollBar().setValue(0);

        } catch (SQLException SQLe){
            SQLe.printStackTrace();
        } catch (NullPointerException NPE){
            System.out.println("форма поиска документов в тестовом режиме "
                    + "connection null");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getFinalSqlString(){
        String result = "SELECT DOGNUM, DATE_SUCCESS, ACC_DEB, CUR_DEB, ACC_CRED, CUR_CRED, SUM_DEB, SUM_CRED\n"
                + " FROM TRN\n"
                + " WHERE 1 = 1\n";

        if (!fieldNumDog.getText().equals("")){
            result = result + " AND DOGNUM = " + fieldNumDog.getText() + "\n";
        }

        if (!fieldDateSuccess.getText().equals("")){
            result = result + " AND DATE_SUCCESS = TO_DATE('" + fieldDateSuccess.getText() + "','DD.MM.YYYY')\n";
        }

        if (!fieldAccDeb.getText().equals("")){
            result = result + " AND ACC_DEB LIKE '" + fieldAccDeb.getText() + "'\n";
        }

        if (!fieldCurDeb.getText().equals("")){
            result = result + " AND CUR_DEB = '" + getDebCurrencyInISO(fieldCurDeb.getText()) + "'\n";
        }

        if (!fieldAccCred.getText().equals("")){
            result = result + " AND ACC_CRED LIKE '" + fieldAccCred.getText() + "'\n";
        }

        if (!fieldCurCred.getText().equals("")){
            result = result + " AND CUR_CRED = '" + getDebCurrencyInISO(fieldCurCred.getText()) + "'\n";
        }

        if (!fieldSumDeb.getText().equals("")){
            result = result + " AND SUM_DEB = " + fieldSumDeb.getText() + "\n";
        }

        if (!fieldSumCred.getText().equals("")){
            result = result + " AND SUM_CRED = " + fieldSumCred.getText() + "\n";
        }

        result += " ORDER BY DOGNUM";

        System.out.println("SQL Запрос в result = " + result);

        return result;
    }

    private String getDebCurrencyInISO (String str){
        String result = null;
        if (str.equals("810")) result = "RUR";
        else if (str.equals("840")) result = "USD";
        else if (str.equals("978")) result = "EUR";
        else result = str;
        return result;
    }

    public static void main(String[] args){
        FindDocumentsFrame findDocumentsFrame =
                new FindDocumentsFrame("testing FindDocumentsFrame", null);
    }
}
