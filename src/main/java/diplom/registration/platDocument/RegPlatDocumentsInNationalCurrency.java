package diplom.registration.platDocument;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import diplom.formats.AppFormat;
import diplom.formats.MyAppAbstractFormatterFactory;
import diplom.administration.SettingsFrame;
import diplom.moduleDate.ModuleDateFrame;

public class RegPlatDocumentsInNationalCurrency extends JFrame{

    private static final long serialVersionUID = 1L;

    private static int leftPointX;
    private static int topPointY;
    private static Dimension screen;
    private static Dimension prefferedDim;
    private static final int FRAME_WIDTH = 850;
    private static final int FRAME_HEIGHT = 625;
    private static JPanel mainPanel;
    private static JPanel eastPanel;
    private static JButton buttonExit;
    private static RegPlatDocumentsInNationalCurrency regPlatDocumentsInNationalCurrency;

    private static JTextField fieldBO1;
    private static JTextField fieldBO2;
    private static JTextField fieldPachkaNumber;
    private static JTextField fieldDocumentNumber;

    //плательщик
    private static JTextField fieldKSPayer;
    private static JTextField fieldLSPayer;
    private static JTextField fieldInnPayer;
    private static JTextField fieldKppPayer;
    //получатель
    private static JTextField fieldKURecipient;
    private static JTextField fieldBICRecipient;
    private static JTextField fieldKSRecipient;
    private static JTextField fieldLSRecipient;
    private static JTextField fieldInnRecipient;
    private static JTextField fieldKppRecipient;
    //очередность платежа, дата валютирования, дата документа
    //private static JTextField fieldOchPlat;
    private static JTextField fieldDateCur;
    private static JTextField fieldDateDoc;

    private static JTextField fieldRKC;
    private static JTextField fieldBank;
    private static JTextField fieldNameRecipient;

    private static JFormattedTextField fieldSummOfOperation;

    private static JTextField fieldNaznachiePlatezha;

    private static JFormattedTextField fieldCourse;

    private static JButton buttonReg;

    private static JTextField fieldDateReg;

    private static JTextField fieldDateSupply;
    private static JComboBox OchPlatComboBox;
    private static JComboBox curComboBox;
    private static JComboBox typeOpComboBox;
    private static JTextField nameOfPayer;
    private static JButton buttonNaKart;

    private Document document;
    private static int usabilityCounter;

    private static ArrayList<JTextField> requiredComponentsToFill = new ArrayList<JTextField>();

    public RegPlatDocumentsInNationalCurrency(String nameOfFrame){
        super(nameOfFrame);
        setResizable(false);

        mainPanel = (JPanel) getContentPane();
		/*prefferedDim = mainPanel.getPreferredSize();
		mainPanel.setMinimumSize(prefferedDim);*/
        initSidesOfFrame();
        initDefaultValuesOfFrame();

        addListenersToComponents();

        //запишем все нужные поля для проверки на пустое значение
        addRequredComponentsToFill();

        //создаем документ
        document =  new Document();

        screen = Toolkit.getDefaultToolkit().getScreenSize();
        topPointY = (screen.height - FRAME_HEIGHT) / 2;
        leftPointX = (screen.width - FRAME_WIDTH) / 2;
        setBounds(leftPointX,topPointY,FRAME_WIDTH,FRAME_HEIGHT);

        setVisible(true);
    }

    private void addRequredComponentsToFill() {
        //requiredComponentsToFill.add()
        requiredComponentsToFill.add(fieldBO1);
        requiredComponentsToFill.add(fieldBO2);
        requiredComponentsToFill.add(fieldPachkaNumber);
        requiredComponentsToFill.add(fieldDocumentNumber);
        //плательщик
        requiredComponentsToFill.add(fieldKSPayer);
        requiredComponentsToFill.add(fieldLSPayer);
        requiredComponentsToFill.add(fieldInnPayer);
        requiredComponentsToFill.add(fieldKppPayer);
        requiredComponentsToFill.add(nameOfPayer);
        //получатель
        requiredComponentsToFill.add(fieldBICRecipient);
        requiredComponentsToFill.add(fieldKSRecipient);
        requiredComponentsToFill.add(fieldLSRecipient);
        requiredComponentsToFill.add(fieldInnRecipient);
        requiredComponentsToFill.add(fieldKppRecipient);
        //очередность платежа, дата валютирования, дата документа
        //private static JTextField fieldOchPlat;
        requiredComponentsToFill.add(fieldDateCur);
        requiredComponentsToFill.add(fieldDateDoc);
        requiredComponentsToFill.add(fieldBank);
        requiredComponentsToFill.add(fieldNameRecipient);

        requiredComponentsToFill.add(fieldSummOfOperation);
        requiredComponentsToFill.add(fieldNaznachiePlatezha);
        requiredComponentsToFill.add(fieldDateReg);
        requiredComponentsToFill.add(fieldDateSupply);
    }

    private void initDefaultValuesOfFrame() {
        fieldDateCur.setText(ModuleDateFrame.getSysdate());
        fieldDateDoc.setText(ModuleDateFrame.getSysdate());
        fieldDateSupply.setText(ModuleDateFrame.getSysdate());
        fieldDateReg.setText(ModuleDateFrame.getSysdate());

    }

    public static RegPlatDocumentsInNationalCurrency getRegPlatDocumentsInNationalCurrency(){
        return regPlatDocumentsInNationalCurrency;
    }

    class ButtonExitActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            document = null;
            dispose();
        }
    }

    public void addListenersToComponents(){
        buttonExit.addActionListener(new ButtonExitActionListener());
        buttonReg.addActionListener(ae -> {
            DocumentModel dm = new DocumentModel();
            if (SettingsFrame.isSelectedTestingRegistration()){
                dm.addTestingDocument();
            } else {
                boolean isSuccess =  prepareDocument();
                if (isSuccess){
                    dm.addDoc(document);
                }
            }
        });
    }

    private boolean prepareDocument() {
        //TODO Для дат сделать метод и позволять вводить данные в других форматах кроме dd.mm.yyyy
        try {
            //StringUtils
            document.bo1 = Integer.parseInt(fieldBO1.getText());
        } catch( NumberFormatException e){
            String fieldName = "БО1";
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this,
                    "Введите правильное значение для поля " + fieldName, "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            document.bo2 = Integer.parseInt(fieldBO2.getText());
        } catch( NumberFormatException e){
            String fieldName = "БО2";
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this,
                    "Введите правильное значение для поля " + fieldName, "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            document.pachka = Integer.parseInt(fieldPachkaNumber.getText());
        } catch( NumberFormatException e){
            String fieldName = "Пачка";
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this,
                    "Введите правильное значение для поля " + fieldName, "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            document.docNo = Integer.parseInt(fieldDocumentNumber.getText());
        } catch( NumberFormatException e){
            String fieldName = "номер документа";
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this,
                    "Введите правильное значение для поля " + fieldName, "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        document.ochPlat = Integer.parseInt((String) OchPlatComboBox.getSelectedItem());
        String tempStr = fieldDateCur.getText();
        String tempValForDate = tempStr.substring(6, 10) + "-" + tempStr.substring(3, 5) + "-" + tempStr.substring(0, 2);
        //document.dateVal = Date.valueOf("yyyy-mm-dd");
        document.dateVal = Date.valueOf(tempValForDate);

        tempStr = fieldDateDoc.getText();
        tempValForDate = tempStr.substring(6, 10) + "-" + tempStr.substring(3, 5) + "-" + tempStr.substring(0, 2);
        document.dateDoc =  Date.valueOf(tempValForDate);

        document.lsPayer = fieldLSPayer.getText();

        try {
            document.innPayer = Integer.parseInt(fieldInnPayer.getText());
        } catch( NumberFormatException e){
            String fieldName = "ИНН плательщика";
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this,
                    "Введите правильное значение для поля " + fieldName, "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            document.kppPayer = Integer.parseInt(fieldKppPayer.getText());
        } catch( NumberFormatException e){
            String fieldName = "КПП плательщика";
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this,
                    "Введите правильное значение для поля " + fieldName, "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        document.namePayer = nameOfPayer.getText();
        document.kuRecipient = fieldKURecipient.getText();
        document.bicRecipient = fieldBICRecipient.getText();
        document.ksPayer = fieldKSPayer.getText();
        document.ksRecipient = fieldKSRecipient.getText();
        document.lsRecipient = fieldLSRecipient.getText();

        try {
            document.innRecipient = Integer.parseInt(fieldInnRecipient.getText());
        } catch( NumberFormatException e){
            String fieldName = "ИНН получателя";
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this,
                    "Введите правильное значение для поля " + fieldName, "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            document.kppRecipient = Integer.parseInt(fieldKppRecipient.getText());
        } catch( NumberFormatException e){
            String fieldName = "КПП получателя";
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this,
                    "Введите правильное значение для поля " + fieldName, "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        document.rkc = fieldRKC.getText();
        document.bank = fieldBank.getText();
        document.nameRecipient = fieldNameRecipient.getText();

        try {
            String tempForDouble = getStringThatDoubleCanParse(fieldSummOfOperation.getText());
            System.out.println(tempForDouble);
            document.summOfOperation = Double.parseDouble(tempForDouble);
        } catch( NumberFormatException e){
            String fieldName = "сумма";
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this,
                    "Введите правильное значение для поля " + fieldName, "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        document.currency = (String) curComboBox.getSelectedItem();
        document.typeoper = (String) typeOpComboBox.getSelectedItem();
        document.naznachiePlatezha = fieldNaznachiePlatezha.getText();

        try {
            document.course = Double.parseDouble(fieldCourse.getText());
        } catch( NumberFormatException e){
            String fieldName = "курс";
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this,
                    "Введите правильное значение для поля " + fieldName, "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        //document.dateReg = fieldDateReg.getText();

        tempStr = fieldDateReg.getText();
        tempValForDate = tempStr.substring(6, 10) + "-" + tempStr.substring(3, 5) + "-" + tempStr.substring(0, 2);
        document.dateReg = Date.valueOf(tempValForDate);
        //document.dateSupply = fieldDateSupply.getText();
        tempStr = fieldDateSupply.getText();
        tempValForDate = tempStr.substring(6, 10) + "-" + tempStr.substring(3, 5) + "-" + tempStr.substring(0, 2);
        document.dateSupply = Date.valueOf(tempValForDate);


        for (JTextField item :requiredComponentsToFill){
            if (item.getText().trim().equals("") || item.getText().trim().equals(null) || item.getText().trim().equals("0.00")){
                JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.this, "Заполните все необходимые поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }



        return true;
    }

    private String getStringThatDoubleCanParse(String text) {
// TODO Auto-generated method stub
        StringBuffer result = new StringBuffer("");
        for (int i = 0; i < text.length(); i++){
            if (text.charAt(i) != ','){
                result.append(text.charAt(i));
            }
        }
        //System.out.println("after loop " +  result);
        return result.toString();
    }

    public static void initSidesOfFrame(){
        mainPanel.setLayout(new BorderLayout(10,10));

        initCenterSide();

        initTopSide();

        initBottomSide();

        initEastSide();

    }

    public static void addLabelHorizAlignRightToPanel(JPanel pane, String nameOfLabel){

        JLabel temp = new JLabel(nameOfLabel);
        temp.setHorizontalAlignment(JLabel.RIGHT);
        pane.add(temp);
    }

    private static void initCenterSide() {

        JPanel jPanelCenter = new JPanel();
        jPanelCenter.setLayout(new BorderLayout());

        initTopOfCenter(jPanelCenter);
        initCenterOfCenter(jPanelCenter);
        initBottomOfCenter(jPanelCenter);

        mainPanel.add(jPanelCenter,BorderLayout.CENTER);
    }

    private static void initBottomOfCenter(JPanel jPanelCenter) {

        JPanel jPanelBottomOfCenter = new JPanel();
        initjPanelBottomOfCenter(jPanelBottomOfCenter);
        jPanelCenter.add(jPanelBottomOfCenter, BorderLayout.SOUTH);

    }

    /*
     * центральная часть центральной панели
     * */
    private static void initCenterOfCenter(JPanel pane) {

        JPanel jPanelCenterOfCenter = new JPanel();
        jPanelCenterOfCenter.setLayout(new GridLayout(2,1));

        JPanel topSideCenterOfCenter = new JPanel();
        topSideCenterOfCenter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                        "Плательщик"),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        topSideCenterOfCenter.setLayout(new GridLayout(1, 2));

        JPanel leftTopSideCenterOfCenter = new JPanel();
        initLeftTopSideCenterOfCenter(leftTopSideCenterOfCenter);
        topSideCenterOfCenter.add(leftTopSideCenterOfCenter);

        JPanel rightTopSideCenterOfCenter = new JPanel();
        JPanel bottomRightTopSideCenterOfCenter = new JPanel();
        initBottomRightTopSideCenterOfCenter(bottomRightTopSideCenterOfCenter);
        rightTopSideCenterOfCenter.add(bottomRightTopSideCenterOfCenter, BorderLayout.SOUTH);

        topSideCenterOfCenter.add(rightTopSideCenterOfCenter);
        jPanelCenterOfCenter.add(topSideCenterOfCenter);

        //нижняя часть центральной панельки
        JPanel bottomSideCenterOfCenter = new JPanel();
        bottomSideCenterOfCenter.setLayout(new GridLayout(1,2));

        JPanel leftBottomSideCenterOfCenter = new JPanel();
        initLeftBottomSideCenterOfCenter(leftBottomSideCenterOfCenter);
        bottomSideCenterOfCenter.add(leftBottomSideCenterOfCenter);

        JPanel rightBottomSideCenterOfCenter = new JPanel();
        initRightBottomSideCenterOfCenter(rightBottomSideCenterOfCenter);
        bottomSideCenterOfCenter.add(rightBottomSideCenterOfCenter);

        bottomSideCenterOfCenter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                        "Получатель"),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        jPanelCenterOfCenter.add(bottomSideCenterOfCenter);



        pane.add(jPanelCenterOfCenter, BorderLayout.CENTER);



    }

    private static void initTopOfCenter(JPanel jPanelCenter) {

        JPanel jPanelTopOfCenter = new JPanel();
        initjPanelTopOfCenter(jPanelTopOfCenter);
        jPanelCenter.add(jPanelTopOfCenter, BorderLayout.NORTH);

    }

    private static void initjPanelBottomOfCenter(JPanel pane) {

        pane.setLayout(new GridLayout(2,1));

        JPanel topSideOfBottomOfCenter = new JPanel();
        topSideOfBottomOfCenter.setLayout(new FlowLayout(FlowLayout.LEFT));
        topSideOfBottomOfCenter.add(new JLabel("Сумма"));

        NumberFormat numberFormatter = AppFormat.getMoneyFormat();

        fieldSummOfOperation =  new JFormattedTextField(numberFormatter);
        fieldSummOfOperation.setColumns(10);
        fieldSummOfOperation.setValue(new Double(0.00));
        fieldSummOfOperation.setFormatterFactory(new MyAppAbstractFormatterFactory());
        topSideOfBottomOfCenter.add(fieldSummOfOperation);

        String[] curItems = {
                "RUR",
                "USD",
                "EUR"
        };
        curComboBox =  new JComboBox(curItems);
        topSideOfBottomOfCenter.add(curComboBox);
        topSideOfBottomOfCenter.add(new JLabel("Курс"));

        fieldCourse = new JFormattedTextField(numberFormatter);
        fieldCourse.setColumns(8);
        fieldCourse.setValue(new Double(1.00));
        fieldCourse.setEditable(false);
        fieldCourse.setFormatterFactory(new MyAppAbstractFormatterFactory());
        topSideOfBottomOfCenter.add(fieldCourse);
        topSideOfBottomOfCenter.add(new JLabel("Вид оп."));

        String[] typeOpItems = {
                "01",
                "02",
                "06",
                "16"
        };

        typeOpComboBox =  new JComboBox(typeOpItems);

        topSideOfBottomOfCenter.add(typeOpComboBox);
        pane.add(topSideOfBottomOfCenter);

        JPanel bottomSideOfBottomOfCenter = new JPanel();
        bottomSideOfBottomOfCenter.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomSideOfBottomOfCenter.add(new JLabel("Назначение"));
        fieldNaznachiePlatezha = new JTextField(43);
        bottomSideOfBottomOfCenter.add(fieldNaznachiePlatezha);
        pane.add(bottomSideOfBottomOfCenter);

    }

    private static void initRightBottomSideCenterOfCenter(JPanel pane) {

        pane.setLayout(new GridLayout(8,2,5,0));
        addLabelHorizAlignRightToPanel(pane,"РКЦ");
        fieldRKC = new JTextField();
        pane.add(fieldRKC);
        addLabelHorizAlignRightToPanel(pane,"Банк");
        fieldBank = new JTextField();
        pane.add(fieldBank);
        addLabelHorizAlignRightToPanel(pane,"Наим. получателя");
        fieldNameRecipient =  new JTextField();
        pane.add(fieldNameRecipient);
        //decoration
        pane.add(new JLabel());
        pane.add(new JLabel());
        pane.add(new JLabel());
        pane.add(new JLabel());
        pane.add(new JLabel());
        pane.add(new JLabel());
        pane.add(new JLabel());
        pane.add(new JLabel());
        pane.add(new JLabel());
        pane.add(new JLabel());
    }

    private static void initLeftBottomSideCenterOfCenter(JPanel pane) {

        pane.setLayout(new GridLayout(8,2,5,0));
        addLabelHorizAlignRightToPanel(pane,"К/У");
        fieldKURecipient =  new JTextField();
        pane.add(fieldKURecipient);
        addLabelHorizAlignRightToPanel(pane,"БИК");
        fieldBICRecipient = new JTextField();
        pane.add(fieldBICRecipient);
        addLabelHorizAlignRightToPanel(pane,"К/С");
        fieldKSRecipient = new JTextField();
        pane.add(fieldKSRecipient);
        addLabelHorizAlignRightToPanel(pane,"Л/С");
        fieldLSRecipient = new JTextField();
        pane.add(fieldLSRecipient);
        addLabelHorizAlignRightToPanel(pane,"ИНН");
        fieldInnRecipient = new JTextField();
        pane.add(fieldInnRecipient);
        addLabelHorizAlignRightToPanel(pane,"КПП");
        fieldKppRecipient = new JTextField();
        pane.add(fieldKppRecipient);
        //decoration
        pane.add(new JLabel());
        pane.add(new JLabel());
        pane.add(new JLabel());
        pane.add(new JLabel());
    }

    private static void initBottomRightTopSideCenterOfCenter(JPanel panel) {

        panel.setLayout(new GridLayout(1,2,5,0));

        addLabelHorizAlignRightToPanel(panel,"Наим. плательщика");
        //расширил тексфилд для того, чтобы получше смотрелся
        nameOfPayer = new JTextField(11);
        panel.add(nameOfPayer);

    }

    private static void initLeftTopSideCenterOfCenter(JPanel leftTopSideCenterOfCenter) {

        leftTopSideCenterOfCenter.setLayout(new GridLayout(7,2,5,0));

        addLabelHorizAlignRightToPanel(leftTopSideCenterOfCenter,"К/С");
        fieldKSPayer = new JTextField();
        leftTopSideCenterOfCenter.add(fieldKSPayer);

        addLabelHorizAlignRightToPanel(leftTopSideCenterOfCenter,"Л/С");
        fieldLSPayer = new JTextField();
        leftTopSideCenterOfCenter.add(fieldLSPayer);

        addLabelHorizAlignRightToPanel(leftTopSideCenterOfCenter,"ИНН");
        fieldInnPayer = new JTextField();
        leftTopSideCenterOfCenter.add(fieldInnPayer);

        addLabelHorizAlignRightToPanel(leftTopSideCenterOfCenter,"КПП");
        fieldKppPayer = new JTextField();
        leftTopSideCenterOfCenter.add(fieldKppPayer);
    }

    private static void initjPanelTopOfCenter(JPanel pane) {

        pane.setLayout(new GridLayout(1, 2));
        JPanel LeftSideTopOfCenter = new JPanel();
        initLeftSideTopOfCenter(LeftSideTopOfCenter);

        JPanel RightSideTopOfCenter = new JPanel();
        initRightSideTopOfCenter(RightSideTopOfCenter);

        pane.add(LeftSideTopOfCenter);
        pane.add(RightSideTopOfCenter);

    }

    private static void initRightSideTopOfCenter(JPanel pane) {

        pane.setLayout(new GridLayout(4,2,5,0));
        addLabelHorizAlignRightToPanel(pane,"Очер. плат.");
        //fieldOchPlat = new JTextField();

        String[] ochPlatItems = {
                "1",
                "2",
                "3",
                "4",
                "5"
        };
        OchPlatComboBox =  new JComboBox(ochPlatItems);


        pane.add(OchPlatComboBox);
        addLabelHorizAlignRightToPanel(pane,"Дата валют.");
        fieldDateCur = new JFormattedTextField(AppFormat.getMyDateFormat());
        pane.add(fieldDateCur);
        addLabelHorizAlignRightToPanel(pane, "Дата док.");
        //.pane.add(new JLabel("Дата док."));
        fieldDateDoc = new JTextField();
        pane.add(fieldDateDoc);
    }
    /*
        private static void initCentralSideTopOfCenter(JPanel panel) {

            panel.setLayout(new BorderLayout());
            JPanel DateOfDocs = new JPanel();
            DateOfDocs.setLayout(new GridLayout(1,2));
            DateOfDocs.add(new JLabel("Дата док."));
            DateOfDocs.add(new JTextField(10));
            panel.add(DateOfDocs, BorderLayout.SOUTH);

        }
    */
    private static void initLeftSideTopOfCenter(JPanel pane) {

        pane.setLayout(new GridLayout(4,2,5,0));
        addLabelHorizAlignRightToPanel(pane, "БО 1");
        fieldBO1 = new JTextField();
        pane.add(fieldBO1);

        addLabelHorizAlignRightToPanel(pane, "БО 2");
        fieldBO2 = new JTextField();
        pane.add(fieldBO2);

        addLabelHorizAlignRightToPanel(pane, "Пачка №");
        fieldPachkaNumber = new JTextField();
        pane.add(fieldPachkaNumber);
        addLabelHorizAlignRightToPanel(pane,"Документ №");
        fieldDocumentNumber = new JTextField();
        pane.add(fieldDocumentNumber);

    }

    private static void initEastSide() {
        if (eastPanel == null){
            eastPanel = new JPanel();

            eastPanel.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(Color.GRAY, 1, true),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)));
            //12 Rows, 1 column
            eastPanel.setLayout(new GridLayout(11,1));
            //1
            initEastPanelLabel();
            //2
            initEastPanelDates();
            //3
            initEastPanelPlatPos();
            //4
            initEastPanelSummsTecDocPlanOst();
            //5
            initEastPanelSummsArestedAndBlocked();
            //6
            initEastPanelSummsCartOneAndPriost();
            //7
            initEastPanelSummsCartTwoAndNullLabels();
            //8
            initEastLabelLastDocs();
            //9
            initEastPanelLastDocumentsPrtOne();
            //10
            initEastPanelLastDocumentsPrtTwo();
            //11
            initEastPanelPachka();


        }
        mainPanel.add(eastPanel,BorderLayout.EAST);
		/*
		eastPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		//12 Rows, 1 column
		eastPanel.setLayout(new GridLayout(11,1));
		//1
		initEastPanelLabel();
		//2
		initEastPanelDates();
		//3
		initEastPanelPlatPos();
		//4
		initEastPanelSummsTecDocPlanOst();
		//5
		initEastPanelSummsArestedAndBlocked();
		//6
		initEastPanelSummsCartOneAndPriost();
		//7
		initEastPanelSummsCartTwoAndNullLabels();
		//8
		initEastLabelLastDocs();
		//9
		initEastPanelLastDocumentsPrtOne();
		//10
		initEastPanelLastDocumentsPrtTwo();
		//11
		initEastPanelPachka();

		mainPanel.add(eastPanel,BorderLayout.EAST);
*/
    }

    private static void initEastPanelPachka() {
        JPanel eastPanelPachka = new JPanel();
        eastPanelPachka.setLayout(new GridLayout(2,2));

        String documentsInPachka = "Документов в пачке";
        eastPanelPachka.add(new JLabel(documentsInPachka));
        eastPanelPachka.add(new JTextField());

        String SummOfPachka = "Cумма пачки";
        eastPanelPachka.add(new JLabel(SummOfPachka));
        eastPanelPachka.add(new JTextField());

        eastPanel.add(eastPanelPachka);

    }

    private static void initEastPanelLastDocumentsPrtTwo() {
        JPanel eastPanelLastDocs2 = new JPanel();
        eastPanelLastDocs2.setLayout(new GridLayout(2,2));
        //номер документа
        eastPanelLastDocs2.add(new JTextField());
        //сумма документа
        eastPanelLastDocs2.add(new JTextField());
        eastPanelLastDocs2.add(new JTextField());
        eastPanelLastDocs2.add(new JTextField());

        eastPanel.add(eastPanelLastDocs2);
    }

    private static void initEastPanelLastDocumentsPrtOne() {
        JPanel eastPanelLastDocs1 = new JPanel();
        eastPanelLastDocs1.setLayout(new GridLayout(2,2));
        //номер документа
        eastPanelLastDocs1.add(new JTextField());
        //сумма документа
        eastPanelLastDocs1.add(new JTextField());
        eastPanelLastDocs1.add(new JTextField());
        eastPanelLastDocs1.add(new JTextField());

        eastPanel.add(eastPanelLastDocs1);
    }

    private static void initEastLabelLastDocs() {
        eastPanel.add(new JLabel("Последние документы"));
    }

    private static void initEastPanelSummsCartTwoAndNullLabels() {
        JPanel eastPanelSummsCartTwoAndNullLabels = new JPanel();
        eastPanelSummsCartTwoAndNullLabels.setLayout(new GridLayout(2,1));

        JPanel eastPanelSummsCartTwo = new JPanel();
        eastPanelSummsCartTwo.setLayout(new GridLayout(1,2));
        eastPanelSummsCartTwo.add(new JLabel("Карт №2"));
        eastPanelSummsCartTwo.add(new JTextField());
        eastPanelSummsCartTwoAndNullLabels.add(eastPanelSummsCartTwo);

        JPanel eastPanelSummsNull = new JPanel();
        eastPanelSummsNull.setLayout(new GridLayout(1,2));
        eastPanelSummsNull.add(new JLabel(""));
        eastPanelSummsNull.add(new JLabel(""));
        eastPanelSummsCartTwoAndNullLabels.add(eastPanelSummsNull);

        eastPanel.add(eastPanelSummsCartTwoAndNullLabels);
    }

    private static void initEastPanelSummsCartOneAndPriost() {
        JPanel eastPanelSummsCartOneAndPriost = new JPanel();
        eastPanelSummsCartOneAndPriost.setLayout(new GridLayout(2,1));


        JPanel eastPanelSummsCartOne = new JPanel();
        eastPanelSummsCartOne.setLayout(new GridLayout(1,2));
        eastPanelSummsCartOne.add(new JLabel("Карт №1"));
        eastPanelSummsCartOne.add(new JTextField());
        eastPanelSummsCartOneAndPriost.add(eastPanelSummsCartOne);

        JPanel eastPanelSummsPriost = new JPanel();
        eastPanelSummsPriost.setLayout(new GridLayout(1,2));
        eastPanelSummsPriost.add(new JLabel("Приост."));
        eastPanelSummsPriost.add(new JTextField());
        eastPanelSummsCartOneAndPriost.add(eastPanelSummsPriost);

        eastPanel.add(eastPanelSummsCartOneAndPriost);
    }

    private static void initEastPanelSummsArestedAndBlocked() {
        JPanel eastPanelSummsArestedAndBlocked = new JPanel();
        eastPanelSummsArestedAndBlocked.setLayout(new GridLayout(2,1));

        JPanel eastPanelSummsArest = new JPanel();
        eastPanelSummsArest.setLayout(new GridLayout(1,2));
        eastPanelSummsArest.add(new JLabel("Арест."));
        eastPanelSummsArest.add(new JTextField());
        eastPanelSummsArestedAndBlocked.add(eastPanelSummsArest);

        JPanel eastPanelSummsBlock = new JPanel();
        eastPanelSummsBlock.setLayout(new GridLayout(1,2));
        eastPanelSummsBlock.add(new JLabel("Блокир."));
        eastPanelSummsBlock.add(new JTextField());
        eastPanelSummsArestedAndBlocked.add(eastPanelSummsBlock);

        eastPanel.add(eastPanelSummsArestedAndBlocked);
    }

    private static void initEastPanelSummsTecDocPlanOst() {
        JPanel eastPanelSummsTecDocPlanOst = new JPanel();
        eastPanelSummsTecDocPlanOst.setLayout(new GridLayout(2,1));

        JPanel eastPanelSummsTecDoc = new JPanel();
        eastPanelSummsTecDoc.setLayout(new GridLayout(1,2));
        eastPanelSummsTecDoc.add(new JLabel("Тек. док."));
        eastPanelSummsTecDoc.add(new JTextField());
        eastPanelSummsTecDocPlanOst.add(eastPanelSummsTecDoc);

        JPanel eastPanelSummsPlanOst = new JPanel();
        eastPanelSummsPlanOst.setLayout(new GridLayout(1,2));
        eastPanelSummsPlanOst.add(new JLabel("План. ост."));
        eastPanelSummsPlanOst.add(new JTextField());
        eastPanelSummsTecDocPlanOst.add(eastPanelSummsPlanOst);

        eastPanel.add(eastPanelSummsTecDocPlanOst);

    }

    private static void initEastPanelPlatPos() {
        JPanel eastPanelPlatPos = new JPanel();
        eastPanelPlatPos.setLayout(new GridLayout(2,1));
        eastPanelPlatPos.add(new JLabel("Платежная позиция"));
        eastPanelPlatPos.add(new JTextField());
        eastPanel.add(eastPanelPlatPos);

    }

    private static void initEastPanelDates() {
        JPanel eastPanelDates = new JPanel();
        eastPanelDates.setLayout(new GridLayout(2,2));
        //2
        JPanel eastPanelDateReg = new JPanel();
        eastPanelDateReg.setLayout(new GridLayout(1,2));
        eastPanelDateReg.add(new JLabel("Дата регистрации"));
        fieldDateReg = new JTextField();
        eastPanelDateReg.add(fieldDateReg);
        eastPanelDates.add(eastPanelDateReg);
        //3
        JPanel eastPanelDateSupply = new JPanel();
        eastPanelDateSupply.setLayout(new GridLayout(1,2));
        eastPanelDateSupply.add(new JLabel("Дата поступления"));
        fieldDateSupply = new JTextField();
        eastPanelDateSupply.add(fieldDateSupply);
        eastPanelDates.add(eastPanelDateSupply);

        eastPanel.add(eastPanelDates);
    }

    private static void initEastPanelLabel() {
        JPanel eastPanelLabel = new JPanel();
        eastPanelLabel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        eastPanelLabel.add(new JLabel("Справочная Информация"));
        eastPanel.add(eastPanelLabel);
    }

    private static void initTopSide() {
        JPanel JPanelTop = new JPanel();
        JPanelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanelTop.add(new JButton("Справочник"));


        JPanelTop.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        mainPanel.add(JPanelTop,BorderLayout.NORTH);
    }

    private static void initBottomSide() {
        JPanel JPanelBottom = new JPanel();
        JPanelBottom.setLayout(new GridLayout(1,2));
        JPanel JPanelBottomLeft = new JPanel();
        JPanelBottomLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonReg = new JButton("Зарегистрировать");
        //buttonReg.addActionListener(ae -> {new ButtonRegListener();});
        JPanelBottomLeft.add(buttonReg);
        buttonNaKart = new JButton("На картотеку");
        JPanelBottomLeft.add(buttonNaKart);

        JPanelBottomLeft.add(new JButton("Отложить"));

        JPanel JPanelBottomRight = new JPanel();
        JPanelBottomRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonExit =  new JButton("Выход");
        //buttonExit.addActionListener(new ButtonExitActionListener());
        JPanelBottomRight.add(buttonExit);


        JPanelBottom.add(JPanelBottomLeft);
        JPanelBottom.add(JPanelBottomRight);
        mainPanel.add(JPanelBottom,BorderLayout.SOUTH);

    }

    public static void main(String[] args){
        regPlatDocumentsInNationalCurrency = new RegPlatDocumentsInNationalCurrency("Регистрация платежных документов");
    }
}

