package diplom.moduleDate;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

/*
 * 22.03.2016
 * */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import diplom.formats.AppFormat;
import diplom.mainWindow.MainFrame;

public class ModuleDateFrame extends JFrame{

    private static final long serialVersionUID = 1L;
    private static JPanel mainPanel;
    private JLabel labelModuleDate;
    private JButton okButton;
    private JButton cancelButton;
    private static JFormattedTextField fieldSystemDate;
    private static JFormattedTextField fieldModuleDate;
    private static String sysdate;

    public ModuleDateFrame(){
        mainPanel = (JPanel) getContentPane();
        setResizable(false);

        initComponent();
        ModuleDateModel moduleDateModel = new ModuleDateModel();
        //moduleDateModel.getSystemDateFromDB();

        ModuleDateFrame.getFieldSystemDate().setText(sysdate);

        if (MainFrame.getModuleDate() == null){
            fieldModuleDate.setText(sysdate);
            MainFrame.setModuleDate(sysdate);
        } else {
            fieldModuleDate.setText(MainFrame.getModuleDate());
        }

        setBounds(500, 600, 200, 100);
        pack();
        setVisible(true);
    }

    public static String getSysdate(){
        return sysdate;
    }

    public static void setSysdate(String value){
        sysdate = value;
    }


    public static JFormattedTextField getFieldSystemDate(){
        return fieldSystemDate;
    }
    private void initComponent() {
        mainPanel.setLayout(new GridLayout(3,1));
        JPanel topPanel =  new JPanel();
        topPanel.setLayout(new GridLayout(2,1,10,10));
        labelModuleDate = new JLabel("Дата модуля (dd.mm.yyyy)");
        labelModuleDate.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(labelModuleDate);

        fieldModuleDate =  new JFormattedTextField(AppFormat.getMyDateFormat());
        fieldModuleDate.setHorizontalAlignment(JTextField.CENTER);
        topPanel.add(fieldModuleDate);

        JPanel centerPanel =  new JPanel();
        centerPanel.setLayout(new GridLayout(2,1,10,10));
        centerPanel.add(new JLabel("Системная дата (SYSDATE)"));
        fieldSystemDate =  new JFormattedTextField(AppFormat.getMyDateFormat());
        fieldSystemDate.setHorizontalAlignment(JTextField.CENTER);
        fieldSystemDate.setEditable(false);
        centerPanel.add(fieldSystemDate);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2,2,10,10));
        //decoration
        bottomPanel.add(new JLabel());
        bottomPanel.add(new JLabel());


        okButton = new JButton("OK");
        bottomPanel.add(okButton);
        okButton.addActionListener(new OkButtonListener());
        cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new CancelButtonListener());
        bottomPanel.add(cancelButton);

        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                        "Дата модуля и системная дата"),
                BorderFactory.createEmptyBorder(0, 20, 0, 20)));

        mainPanel.add(topPanel);
        mainPanel.add(centerPanel);
        mainPanel.add(bottomPanel);
    }

    class OkButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (fieldModuleDate.getText().equals("")){
                JOptionPane.showMessageDialog(ModuleDateFrame.this, "Поле дата модуля должно быть заполнено", "", JOptionPane.ERROR_MESSAGE);
                //ErrorForm errorForm = new ErrorForm("Поле дата модуля должно быть заполнено");
            } else {
                MainFrame.setModuleDate(fieldModuleDate.getText());
                dispose();
            }
        }
    }

    class CancelButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            dispose();
        }
    }

    public static void main(String...args){
        ModuleDateFrame moduleDateFrame = new ModuleDateFrame();
    }
}