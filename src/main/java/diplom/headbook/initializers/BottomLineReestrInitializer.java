package diplom.headbook.initializers;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import diplom.headbook.presenter.ReestrDocumentModel;
import diplom.registration.platDocument.Document;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

/**
 * Инициализация нижней части реестра документов
 *
 * @author aleksander_talismanov
 * @version alpha 1.00
 */
public class BottomLineReestrInitializer
{
    // instance variables
    private static Dimension componentSize;
    private static JLabel labelBO1;
    private static JTextField fieldBO1;
    private static JLabel LabelBO2;
    private static JTextField fieldBO2;
    private static JTextField fieldMeaningOfBO2;
    private static JLabel labelPachka;
    private static JTextField fieldPachka;
    private static JTextField fieldWhoControlled;
    private static JLabel labelPayer;
    private static JLabel labelPayerBIC;
    private static JLabel labelEquivalent;
    private static JTextField fieldEquivalent;
    private static JTextField fieldDateDoc;
    private static JLabel labelDateDoc;
    private static JLabel labelWhoControlled;
    private static JTextField fieldCurrencyDate;
    private static JLabel labelCurrencyDate;
    private static JLabel labelRecipientName;
    private static JLabel labelSupplyDate;
    private static JTextField fieldSupplyDate;
    private static JLabel labelDateRegDoc;
    private static JTextField fieldDateRegDoc;
    private static JLabel labelINN;
    private static JTextField fieldINN;
    private static JTextField fieldKPP;
    private static JLabel labelKPP;
    private static JTextField fieldPayerBIC;
    private static JLabel labelPayerCorrAcc;
    private static JTextField fieldPayerCorrAcc;
    private static JLabel labelPayAcc;
    private static JTextField fieldPayAcc;
    private static JLabel labelPayerName;
    private static JTextField fieldPayerName;
    private static JTextField fieldRecipientBIC;
    private static JLabel labelRecipient;
    private static JTextField fieldRecipientCorrAcc;
    private static JTextField fieldRecipientAcc;
    private static JTextField fieldRecipientName;
    private static JLabel labelTypeOper;
    private static JTextField fieldTypeOper;
    private static JLabel labelQueuePlat;
    private static JTextField fieldQueuePlat;
    private static JTextField fieldNaznPlat;
    private static JLabel labelNaznPlat;
    private static JLabel labelWhoRegistered;
    private static JTextField fieldWhoRegistered;

    /**
     * Constructor for objects of class BottomLineInitializer
     */
    public BottomLineReestrInitializer(){

    }

    public static void initBO1LabelsAndFields(Container pane){
        //установка ярлыка бо1
        labelBO1 = new JLabel("БО 1");
        componentSize = labelBO1.getPreferredSize();
        labelBO1.setBounds(25,225,componentSize.width, componentSize.height);
        pane.add(labelBO1);
        //установка поля бо1
        fieldBO1 = new JTextField("");
        componentSize = fieldBO1.getPreferredSize();
        fieldBO1.setBounds(60,225,componentSize.width+50, componentSize.height);
        pane.add(fieldBO1);
    }

    public static void initBO2LabelsAndFields(Container pane){
        //установка ярлыка бо2
        LabelBO2 = new JLabel("БО 2");
        componentSize = LabelBO2.getPreferredSize();
        LabelBO2.setBounds(120,225,componentSize.width, componentSize.height);
        pane.add(LabelBO2);
        //установка поля бо2

        fieldBO2 = new JTextField("");
        componentSize = fieldBO2.getPreferredSize();
        fieldBO2.setBounds(155,225,componentSize.width+50, componentSize.height);
        pane.add(fieldBO2);
        //установка поля значения(смысла) бо2
        fieldMeaningOfBO2 = new JTextField("");
        componentSize = fieldMeaningOfBO2.getPreferredSize();
        fieldMeaningOfBO2.setBounds(210,225,componentSize.width+350, componentSize.height);
        pane.add(fieldMeaningOfBO2);
    }

    public static void initEquivalentLabelsAndFields(Container pane){
        //установка ярлыка эквивалент
        labelEquivalent = new JLabel("Экв.");
        componentSize = labelEquivalent.getPreferredSize();
        labelEquivalent.setBounds(580,225,componentSize.width, componentSize.height);
        pane.add(labelEquivalent);

        //установка поля эквивалент
        fieldEquivalent = new JTextField("");
        componentSize = fieldEquivalent.getPreferredSize();
        fieldEquivalent.setBounds(610,225,componentSize.width+178, componentSize.height);
        pane.add(fieldEquivalent);
    }

    public static void initPachkaLabelsAndFields(Container pane){
        //установка ярлыка Пачка
        labelPachka = new JLabel("Пачка");
        componentSize = labelPachka.getPreferredSize();
        labelPachka.setBounds(20,245,componentSize.width, componentSize.height);
        pane.add(labelPachka);
        //установка поля Пачка
        fieldPachka = new JTextField("");
        componentSize = fieldPachka.getPreferredSize();
        fieldPachka.setBounds(60,245,componentSize.width+75, componentSize.height);
        pane.add(fieldPachka);
    }

    public static void initDateDocLabelsAndFields(Container pane){
        //установка ярлыка дата документа
        labelDateDoc = new JLabel("Дата док.");
        componentSize = labelDateDoc.getPreferredSize();
        labelDateDoc.setBounds(1,265,componentSize.width, componentSize.height);
        pane.add(labelDateDoc);
        //установка поля дата документа
        fieldDateDoc = new JTextField("");
        componentSize = fieldDateDoc.getPreferredSize();
        fieldDateDoc.setBounds(60,265,componentSize.width+100, componentSize.height);
        pane.add(fieldDateDoc);
    }

    public static void initCurrencyDateLabelsAndFields(Container pane){
        //установка ярлыка даты валютирование
        labelCurrencyDate = new JLabel("Валют.");
        componentSize = labelCurrencyDate.getPreferredSize();
        labelCurrencyDate.setBounds(15,305,componentSize.width, componentSize.height);
        pane.add(labelCurrencyDate);
        //установка поля дата валютирование
        fieldCurrencyDate = new JTextField("");
        componentSize = fieldCurrencyDate.getPreferredSize();
        fieldCurrencyDate.setBounds(60,305,componentSize.width+100, componentSize.height);
        pane.add(fieldCurrencyDate);
    }

    public static void initSupplyDateLabelsAndFields(Container pane){
        //установка ярлыка даты поступления
        labelSupplyDate = new JLabel("Поступл.");
        componentSize = labelSupplyDate.getPreferredSize();
        labelSupplyDate.setBounds(5,325,componentSize.width, componentSize.height);
        pane.add(labelSupplyDate);
        //установка поля даты поступления
        fieldSupplyDate = new JTextField("");
        componentSize = fieldSupplyDate.getPreferredSize();
        fieldSupplyDate.setBounds(60,325,componentSize.width+100, componentSize.height);
        pane.add(fieldSupplyDate);
    }

    public static void initDateRegDocLabelsAndFields(Container pane){
        //установка ярлыка дата регистрации
        labelDateRegDoc = new JLabel("Регистр.");
        componentSize = labelDateRegDoc.getPreferredSize();
        labelDateRegDoc.setBounds(7,285,componentSize.width, componentSize.height);
        pane.add(labelDateRegDoc);
        //установка поля дата регистрации
        fieldDateRegDoc = new JTextField("");
        componentSize = fieldDateRegDoc.getPreferredSize();
        fieldDateRegDoc.setBounds(60,285,componentSize.width+100, componentSize.height);
        pane.add(fieldDateRegDoc);
    }

    public static void initInnLabelsAndFields(Container pane){
        //установка ярлыка ИНН
        labelINN = new JLabel("ИНН");
        componentSize = labelINN.getPreferredSize();
        labelINN.setBounds(570,245,componentSize.width, componentSize.height);
        pane.add(labelINN);
        //установка поля ИНН
        fieldINN = new JTextField("");
        componentSize = fieldINN.getPreferredSize();
        fieldINN.setBounds(565,265,componentSize.width+110, componentSize.height);
        pane.add(fieldINN);
    }

    public static void initKPPLabelsAndFields(Container pane){
        //установка ярлыка КПП
        labelKPP = new JLabel("КПП");
        componentSize = labelKPP.getPreferredSize();
        labelKPP.setBounds(685,245,componentSize.width, componentSize.height);
        pane.add(labelKPP);
        //установка поля КПП
        fieldKPP = new JTextField("");
        componentSize = fieldKPP.getPreferredSize();
        fieldKPP.setBounds(680,265,componentSize.width+110, componentSize.height);
        pane.add(fieldKPP);
    }

    public static void initPayerLabelsAndFields(Container pane){
        //установка ярлыка Плательщик
        labelPayer = new JLabel("Плат.");
        componentSize = labelPayer.getPreferredSize();
        labelPayer.setBounds(167,265,componentSize.width, componentSize.height);
        pane.add(labelPayer);

        //установка ярлыка БИК
        labelPayerBIC = new JLabel("БИК/BIC");
        componentSize = labelPayerBIC.getPreferredSize();
        labelPayerBIC.setBounds(205,248,componentSize.width, componentSize.height);
        pane.add(labelPayerBIC);

        //установка поля БИК
        fieldPayerBIC = new JTextField("");
        componentSize = fieldPayerBIC.getPreferredSize();
        fieldPayerBIC.setBounds(200,265,componentSize.width+70, componentSize.height);
        pane.add(fieldPayerBIC);

        //установка ярлыка Корсчет
        labelPayerCorrAcc = new JLabel("Корсчет");
        componentSize = labelPayerCorrAcc.getPreferredSize();
        labelPayerCorrAcc.setBounds(283,248,componentSize.width, componentSize.height);
        pane.add(labelPayerCorrAcc);
        //установка поля Плательщик Корсчет
        fieldPayerCorrAcc = new JTextField("");
        componentSize = fieldPayerCorrAcc.getPreferredSize();
        fieldPayerCorrAcc.setBounds(278,265,componentSize.width+135, componentSize.height);
        pane.add(fieldPayerCorrAcc);

        //установка ярлыка Корсчет
        labelPayAcc = new JLabel("Счет");
        componentSize = labelPayAcc.getPreferredSize();
        labelPayAcc.setBounds(426,248,componentSize.width, componentSize.height);
        pane.add(labelPayAcc);
        //установка поля Плательщик
        fieldPayAcc = new JTextField("");
        componentSize = fieldPayAcc.getPreferredSize();
        fieldPayAcc.setBounds(421,265,componentSize.width+135, componentSize.height);
        pane.add(fieldPayAcc);

        //установка ярлыка наименование плательщика
        labelPayerName = new JLabel("Наим.");
        componentSize = labelPayerName.getPreferredSize();
        labelPayerName.setBounds(166,285,componentSize.width, componentSize.height);
        pane.add(labelPayerName);

        //установка поля наим
        fieldPayerName = new JTextField("");
        componentSize = fieldPayerName.getPreferredSize();
        fieldPayerName.setBounds(200,285,componentSize.width+356, componentSize.height);
        pane.add(fieldPayerName);
    }

    public static void initRecipientLabelsAndFields(Container pane){
        //установка ярлыка получатель
        labelRecipient = new JLabel("Получ.");
        componentSize = labelRecipient.getPreferredSize();
        labelRecipient.setBounds(163,305,componentSize.width, componentSize.height);
        pane.add(labelRecipient);

        //установка поля Б�?К получ
        fieldRecipientBIC = new JTextField("");
        componentSize = fieldRecipientBIC.getPreferredSize();
        fieldRecipientBIC.setBounds(200,305,componentSize.width+70, componentSize.height);
        pane.add(fieldRecipientBIC);


        //установка поля получ Корсчет
        fieldRecipientCorrAcc = new JTextField("");
        componentSize = fieldRecipientCorrAcc.getPreferredSize();
        fieldRecipientCorrAcc.setBounds(278,305,componentSize.width+135, componentSize.height);
        pane.add(fieldRecipientCorrAcc);


        //установка поля счет получателя
        fieldRecipientAcc = new JTextField("");
        componentSize = fieldRecipientAcc.getPreferredSize();
        fieldRecipientAcc.setBounds(421,305,componentSize.width+135, componentSize.height);
        pane.add(fieldRecipientAcc);

        //установка ярлыка наименование плательщика
        labelRecipientName = new JLabel("Наим.");
        componentSize = labelRecipientName.getPreferredSize();
        labelRecipientName.setBounds(167,325,componentSize.width, componentSize.height);
        pane.add(labelRecipientName);

        //установка поля наим получателя
        fieldRecipientName = new JTextField("");
        componentSize = fieldRecipientName.getPreferredSize();
        fieldRecipientName.setBounds(200,325,componentSize.width+356, componentSize.height);
        pane.add(fieldRecipientName);
    }

    public static void initTypeOperLabelsAndFields(Container pane){
        //установка ярлыка Вид операции
        labelTypeOper = new JLabel("Вид оп.");
        componentSize = labelTypeOper.getPreferredSize();
        labelTypeOper.setBounds(580,325,componentSize.width, componentSize.height);
        pane.add(labelTypeOper);

        //установка поля Вид операции
        fieldTypeOper = new JTextField("");
        componentSize = fieldTypeOper.getPreferredSize();
        fieldTypeOper.setBounds(625,325,componentSize.width+50, componentSize.height);
        pane.add(fieldTypeOper);
    }

    public static void initQueuePlatLabelsAndFields(Container pane){
        //установка ярлыка очередность платежа
        labelQueuePlat = new JLabel("Оч плат.");
        componentSize = labelQueuePlat.getPreferredSize();
        labelQueuePlat.setBounds(710,325,componentSize.width, componentSize.height);
        pane.add(labelQueuePlat);

        //установка поля очередность платежа
        fieldQueuePlat = new JTextField("");
        componentSize = fieldQueuePlat.getPreferredSize();
        fieldQueuePlat.setBounds(762,325,componentSize.width+26, componentSize.height);
        pane.add(fieldQueuePlat);
    }

    public static void initNaznPlatLabelsAndFields(Container pane){
        //установка ярлыка назн плат
        labelNaznPlat = new JLabel("Назн. пл.");
        componentSize = labelNaznPlat.getPreferredSize();
        labelNaznPlat.setBounds(5,345,componentSize.width, componentSize.height);
        pane.add(labelNaznPlat);
        //установка поля назн плат
        fieldNaznPlat = new JTextField("");
        componentSize = fieldNaznPlat.getPreferredSize();
        fieldNaznPlat.setBounds(60,345,componentSize.width+729, componentSize.height);
        pane.add(fieldNaznPlat);
    }

    public static void initWhoRegisteredLabelsAndFields(Container pane){
        //установка ярлыка кто зарегистрировал
        labelWhoRegistered = new JLabel("Регист.");
        componentSize = labelWhoRegistered.getPreferredSize();
        labelWhoRegistered.setBounds(5,365,componentSize.width, componentSize.height);
        pane.add(labelWhoRegistered);
        //установка поля кто зарегистрировал
        fieldWhoRegistered = new JTextField("");
        componentSize = fieldWhoRegistered.getPreferredSize();
        fieldWhoRegistered.setBounds(60,365,componentSize.width+100, componentSize.height);
        pane.add(fieldWhoRegistered);
    }

    public static void initWhoControlledLabelsAndFields(Container pane){
        //установка ярлыка кто проконтролировал
        labelWhoControlled = new JLabel("Контрол.");
        componentSize = labelWhoControlled.getPreferredSize();
        labelWhoControlled.setBounds(5,385,componentSize.width, componentSize.height);
        pane.add(labelWhoControlled);
        //установка поля кто проконтролировал
        fieldWhoControlled = new JTextField("");
        componentSize = fieldWhoControlled.getPreferredSize();
        fieldWhoControlled.setBounds(60,385,componentSize.width+100, componentSize.height);
        pane.add(fieldWhoControlled);
    }

    public static void fillWithData(int docnum, java.sql.Date dateSuccess) {
        Document document = ReestrDocumentModel.getDocumentByDocnumAndDate(docnum, dateSuccess);
        try {
            fieldBO1.setText(((Integer) document.bo1).toString());
            fieldBO2.setText(((Integer) document.bo2).toString());
            fieldPachka.setText(((Integer) document.pachka).toString());
            if (document.dateVal != null)
                fieldCurrencyDate.setText((document.dateVal).toString());
            if (document.dateDoc != null)
                fieldDateDoc.setText((document.dateDoc).toString());
            if (document.dateReg != null)
                fieldDateRegDoc.setText((document.dateReg).toString());
//        fieldEquivalent.setText(((Integer) document.).toString());
            fieldINN.setText(((Long) document.innRecipient).toString());
            fieldKPP.setText(((Long) document.kppRecipient).toString());
//        fieldMeaningOfBO2.setText(((Integer) document.bo1).toString());
            if (document.naznachiePlatezha != null)
                fieldNaznPlat.setText((document.naznachiePlatezha).toString());
            if (document.lsPayer != null)
                fieldPayAcc.setText(document.lsPayer);
            if (document.ksPayer != null)
                fieldPayerCorrAcc.setText(document.ksPayer);
            if (document.namePayer != null)
                fieldPayerName.setText(document.namePayer);
            if (document.nameRecipient != null)
                fieldRecipientName.setText(document.nameRecipient);
            if (document.bicRecipient != null)
                fieldRecipientBIC.setText(document.bicRecipient);
            if (document.dateSupply != null)
                fieldSupplyDate.setText(document.dateSupply.toString());
            if (document.lsPayer != null)
                fieldPayAcc.setText(document.lsPayer);
            if (document.ksPayer != null)
                fieldPayerCorrAcc.setText(document.ksPayer);
            if (document.typeoper != null)
                fieldTypeOper.setText((document.typeoper));
            fieldQueuePlat.setText(((Integer) document.ochPlat).toString());
            if (document.ksRecipient != null)
                fieldRecipientCorrAcc.setText((document.ksRecipient));
            if (document.lsRecipient != null)
                fieldRecipientAcc.setText((document.lsRecipient));
            if (document.userRegistered != null)
                fieldWhoRegistered.setText(document.userRegistered);

            fieldRecipientName.setCaretPosition(0);

        } catch (Exception e){
            e.getMessage();
        }
    }
}
