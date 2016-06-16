package diplom.registration.platDocument;

/**
 * Created by a.talismanov on 28.04.2016.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import diplom.util.ConnectionSingleton;

public class DocumentModel {
    private Connection connection;
    private int nextValOfTrnSeq;
    private boolean isOk;
    private Document doc;

    public void addTestingDocument() {
        //заменить заглушки на реальные значения полей.

        isOk = true;

        //получить connection
        this.connection = ConnectionSingleton.getInstance();
        //засунуть данные

        //получить Sequence
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "SELECT TRN_SEQ.NEXTVAL FROM DUAL";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                nextValOfTrnSeq = rs.getInt(1);
            }
        } catch (SQLException SQLe) {
            System.out.println("SELECT SEQUENCE NEXTVAL EXCEPTION\n");
            SQLe.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        //System.out.println(sql);
        //запишем в TRN

        try {
            String sql = "INSERT INTO TRN VALUES ( ?, ?, ?,?, ?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, nextValOfTrnSeq);
            preparedStatement.setInt(2, 100);
            preparedStatement.setDate(3, java.sql.Date.valueOf("2016-01-17"));
            preparedStatement.setString(4, "40817810000000000573");
            preparedStatement.setString(5, "RUR");
            //тут надо и счет получателя и счет доходов во второй проводке, если есть комиссия. Функция получения счета доходов
            preparedStatement.setString(6, "70601810000000000573");
            //функция получения валюты счета из номера счета
            preparedStatement.setString(7, "RUR");
            preparedStatement.setDouble(8, 123000.56);
            //пересчет на сумму кредита по курсу
            preparedStatement.setDouble(9, 123000.56);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("TRN INSERT EXCEPTION");
            isOk = false;
            e.printStackTrace();
        }


        //в TRN_DETAILS запишем
        try {
            //доделать запрос
            //27 параметров
            String sql = "INSERT INTO TRN_DETAILS TRN_DETAILS ( BO_1, BO_2, PACHKA, DOC_NUM, OCH_PLAT, DATE_VAL, DATE_DOC,"
                    + "PAYMENT_COR_ACC, PAYMENT_ACC, PAYER_INN , PAYER_KPP, PAYER_NAME, RECIP_KU, RECIP_BIC, RECIP_COR_ACC,"
                    + "RECIP_ACC, RECIP_INN, RECIP_KPP, RECIP_RKC, RECIP_BANK, RECIP_NAME, SUMM, CURRENCY, "
                    + "TYPE_OPER, PAYMENT_PURPOSE, DATE_REG, DATE_RECEIPT, TRN_ID) "
                    + "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            //сначала заглушки со значениями для проверки работы
            //BO_1 NUMBER,
            preparedStatement.setInt(1, 4);
            //BO_2 NUMBER,
            preparedStatement.setInt(2, 20);
            // PACHKA NUMBER,
            preparedStatement.setInt(3, 777);
            // DOC_NUM NUMBER,
            preparedStatement.setInt(4, 100);
            //  OCH_PLAT NUMBER,
            preparedStatement.setInt(5, 6);
            // DATE_VAL DATE,
            preparedStatement.setDate(6, java.sql.Date.valueOf("2015-02-28"));
             /*DATE_DOC DATE,*/
            preparedStatement.setDate(7, java.sql.Date.valueOf("2015-02-27"));
            /* PAYMENT_COR_ACC VARCHAR2(25),*/
            preparedStatement.setString(8, "30102810000000000573");
			 /*PAYMENT_ACC VARCHAR2(25),*/
            preparedStatement.setString(9, "40817810000000000573");
			 /*PAYER_INN NUMBER(12),*/
            preparedStatement.setLong(10, 1234567890);
			 /*PAYER_KPP NUMBER(9),*/
            preparedStatement.setLong(11, 9876543);
			 /*PAYER_NAME VARCHAR2(200),*/
            preparedStatement.setString(12, "Иванов Иван Иванович");
			 /*RECIP_KU VARCHAR2(50),*/
            preparedStatement.setString(13, "Something we dont need");
			 /*RECIP_BIC VARCHAR2(20),*/
            preparedStatement.setString(14, "01234567890123456");
			 /*RECIP_COR_ACC VARCHAR2(25),*/
            preparedStatement.setString(15, "30102810000000000999");
			 /*RECIP_ACC VARCHAR2(25),*/
            preparedStatement.setString(16, "40817810000000000999");
			 /*RECIP_INN NUMBER(12),*/
            preparedStatement.setLong(17, 2134567890);
			 /*RECIP_KPP NUMBER(9),*/
            preparedStatement.setLong(18, 9876543);
			 /*RECIP_RKC VARCHAR2(50),*/
            preparedStatement.setString(19, "РКЦ ГОРОДА МОСКВЫ ПО РАЙОНУ КУНЦЕВО");
			 /*RECIP_BANK VARCHAR2(200),*/
            preparedStatement.setString(20, "ЗАО КБ СИТИБАНК");
			 /*RECIP_NAME VARCHAR2(200),*/
            preparedStatement.setString(21, "Петров Петр Петрович");
			 /*SUMM NUMBER(16,2),*/
            preparedStatement.setDouble(22, 120000.00);
			 /*CURRENCY VARCHAR2(3),*/
            preparedStatement.setString(23, "RUR");
			 /*TYPE_OPER VARCHAR2(2),*/
            preparedStatement.setString(24, "06");
			 /*PAYMENT_PURPOSE VARCHAR2(200),*/
            preparedStatement.setString(25, "Оплата обучения в МГТУ им. Баумана за 2 семестр 2016 года");
			 /*DATE_REG DATE,*/
            preparedStatement.setDate(26, java.sql.Date.valueOf("2015-02-26"));
			 /*DATE_RECEIPT DATE*/
            preparedStatement.setDate(27, java.sql.Date.valueOf("2015-02-25"));
			 /*TRN_ID NUMBER*/
            preparedStatement.setInt(28, nextValOfTrnSeq);

            //выполняем запрос
            preparedStatement.executeUpdate();
        } catch (SQLException SQLe) {
            System.out.println("TRN_DETAILS INSERT EXCEPTION");
            isOk = false;
            SQLe.printStackTrace();
        }


        if (isOk) {
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.getRegPlatDocumentsInNationalCurrency(),
                    "Платёжный документ успешно зарегистрирован", "Успешная регистрация", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showInputDialog(RegPlatDocumentsInNationalCurrency.getRegPlatDocumentsInNationalCurrency(),
                    "Ошибка регистрации платежного документа", "Ошибка регистрации", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void addDoc(Document doc) {
        //заменить заглушки на реальные значения полей.
        boolean isOk = true;

        this.doc = doc;

        //получить connection
        this.connection = ConnectionSingleton.getInstance();
        //засунуть данные

        //получить Sequence
        Statement statement = null;
        nextValOfTrnSeq = getNextValOfTrnSeq(statement);

        PreparedStatement preparedStatement = null;
        //INSERT INTO TRN
        insertIntoTrn(preparedStatement, nextValOfTrnSeq);

        int payerNextval = getPayerNextVal();

        insertIntoPayer(payerNextval);

        int recipientNextval = getFromDbRecipientNextVal();

        insertIntoRecipient(recipientNextval);

        getUserRegistered();

        insertIntoTrnDetail(preparedStatement, payerNextval, recipientNextval, nextValOfTrnSeq);


        if (isOk) {

            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.getRegPlatDocumentsInNationalCurrency(),
                    "Платёжный документ успешно зарегистрирован", "Успешная регистрация", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(RegPlatDocumentsInNationalCurrency.getRegPlatDocumentsInNationalCurrency(),
                    "Ошибка регистрации платежного документа", "Ошибка регистрации", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void getUserRegistered() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT USER FROM DUAL";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                doc.userRegistered = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.getMessage();
                }
            }
        }
    }

    private int getNextValOfTrnSeq(Statement statement) {
        int result = 0;
        try {
            statement = connection.createStatement();
            String sql = "SELECT TRN_SEQ.NEXTVAL FROM DUAL";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException SQLe) {
            System.out.println("SELECT SEQUENCE NEXTVAL EXCEPTION\n");
            SQLe.printStackTrace();
        }
        return result;
    }


    private void insertIntoTrnDetail(PreparedStatement preparedStatement, int payerNextval, int recipientNextval, int nextValOfTrnSeq) {
        //в TRN_DETAILS запишем
        try {

            String sql = "INSERT INTO TRN_DETAIL ( BO_1, BO_2, PACHKA, DOC_NUM, OCH_PLAT, DATE_VAL, DATE_DOC,"
                    + "PAYER_ID, RECIP_ID, SUMM, CURRENCY, "
                    + "TYPE_OPER, PAYMENT_PURPOSE, DATE_REG, DATE_RECEIPT, TRN_ID , USER_REG) "
                    + "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            //сначала заглушки со значениями для проверки работы
            //BO_1 NUMBER,
            preparedStatement.setInt(1, doc.bo1);
            //BO_2 NUMBER,
            preparedStatement.setInt(2, doc.bo2);
            // PACHKA NUMBER,
            preparedStatement.setInt(3, doc.pachka);
            // DOC_NUM NUMBER,
            preparedStatement.setInt(4, doc.docNo);
            //  OCH_PLAT NUMBER,
            preparedStatement.setInt(5, doc.ochPlat);
            // DATE_VAL DATE,
            //preparedStatement.setDate(6, java.sql.Date.valueOf("2015-02-28"));
            preparedStatement.setDate(6, doc.dateVal);
					 /*DATE_DOC DATE,*/
            //preparedStatement.setDate(7, java.sql.Date.valueOf("2015-02-27"));
            preparedStatement.setDate(7, doc.dateDoc);

            preparedStatement.setInt(8, payerNextval);
            preparedStatement.setInt(9, recipientNextval);

            /*SUMM NUMBER(16,2),*/
            preparedStatement.setDouble(10, doc.summOfOperation);
					 /*CURRENCY VARCHAR2(3),*/
            preparedStatement.setString(11, doc.currency);
					 /*TYPE_OPER VARCHAR2(2),*/
            preparedStatement.setString(12, doc.typeoper);
					 /*PAYMENT_PURPOSE VARCHAR2(200),*/
            preparedStatement.setString(13, doc.naznachiePlatezha);
					 /*DATE_REG DATE,*/
            //preparedStatement.setDate(26, java.sql.Date.valueOf("2015-02-26"));
            preparedStatement.setDate(14, doc.dateReg);
					 /*DATE_RECEIPT DATE*/
            //preparedStatement.setDate(27, java.sql.Date.valueOf("2015-02-25"));
            preparedStatement.setDate(15, doc.dateSupply);
					 /*TRN_ID NUMBER*/
            preparedStatement.setInt(16, nextValOfTrnSeq);

            preparedStatement.setString(17, doc.userRegistered);

            //выполняем запрос
            preparedStatement.executeUpdate();
        } catch (SQLException SQLe) {
            System.out.println("TRN_DETAILS INSERT EXCEPTION");
            isOk = false;
            SQLe.printStackTrace();
        }
    }

    private void insertIntoRecipient(int recipientNextval) {
        try {
            String sql = "INSERT INTO RECIPIENT VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prepState = connection.prepareStatement(sql);
            //RECIP_ID
            prepState.setInt(1, recipientNextval);
            //RECIP_BIC
            prepState.setString(2, doc.bicRecipient);
            // RECIP_COR_ACC
            prepState.setString(3, doc.ksRecipient);
            // RECIP_ACC
            prepState.setString(4, doc.lsRecipient);
            // RECIP_INN
            prepState.setLong(5, doc.innRecipient);
            // RECIP_KPP
            prepState.setLong(6, doc.kppRecipient);
            //RECIP_RKC
            prepState.setString(7, doc.rkc);
            //RECIP_BANK
            prepState.setString(8, doc.bank);
            //RECIP_NAME
            prepState.setString(9, doc.nameRecipient);
            //RECIP_KU
            prepState.setString(10, doc.kuRecipient);

            prepState.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertIntoPayer(int payerNextval) {
        try {
            String sql = "INSERT INTO PAYER VALUES (?,?,?,?,?,?)";

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
            //ID
            preparedStatement1.setInt(1, payerNextval);
            //COR_ACC
            System.out.println("doc.ksPayer =" + doc.ksPayer);
            preparedStatement1.setString(2, doc.ksPayer);
            //PAYMENT_ACC
            preparedStatement1.setString(3, doc.lsPayer);
            //PAYER_INN
            preparedStatement1.setLong(4, doc.innPayer);
            //PAYER_KPP
            preparedStatement1.setLong(5, doc.kppPayer);
            //PAYER_NAME
            preparedStatement1.setString(6, doc.namePayer);
            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            System.out.println("PAYER INSERT EXCEPTION");
            isOk = false;
            e.printStackTrace();
        }
    }

    private int getPayerNextVal() {
        int result = 0;
        try {
            String sql = "SELECT PAYER_SEQ.NEXTVAL FROM DUAL";
            PreparedStatement prepState = connection.prepareStatement(sql);
            ResultSet rs = prepState.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("PAYER_SEQ EXCEPTION");
            isOk = false;
            e.printStackTrace();
        }
        return result;
    }

    private void insertIntoTrn(PreparedStatement preparedStatement, int nextValOfTrnSeq) {

        try {
            String sql = "INSERT INTO TRN VALUES ( ?, ?, ?,?, ?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, nextValOfTrnSeq);
            preparedStatement.setInt(2, doc.docNo);
            //preparedStatement.setDate(3, java.sql.Date.valueOf("2016-01-17"));
            preparedStatement.setDate(3, null);
            preparedStatement.setString(4, doc.lsPayer);
            String curPayer = getCurFromAcc(doc.lsPayer);
            preparedStatement.setString(5, curPayer);
            //тут надо и счет получателя и счет доходов во второй проводке, если есть комиссия. Функция получения счета доходов
            preparedStatement.setString(6, doc.lsRecipient);
            //функция получения валюты счета из номера счета
            String curRecipient = getCurFromAcc(doc.lsRecipient);
            preparedStatement.setString(7, curRecipient);
            preparedStatement.setDouble(8, doc.summOfOperation);
            //пересчет на сумму кредита по курсу
            preparedStatement.setDouble(9, getSumForCreditFromDeb(doc.summOfOperation, curPayer, curRecipient));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("TRN INSERT EXCEPTION");
            isOk = false;
            e.printStackTrace();
        }
    }

    private int getFromDbRecipientNextVal() {
        int result = 0;
        try {
            String sql = "SELECT RECIP_SEQ.NEXTVAL FROM DUAL";
            PreparedStatement prepState = connection.prepareStatement(sql);
            ResultSet rs = prepState.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("PAYER_SEQ EXCEPTION");
            isOk = false;
            e.printStackTrace();
        }
        return result;
    }

    private double getSumForCreditFromDeb(Double summOfOperation, String CurFrom, String CurTo) {
        if (CurFrom.equals(CurTo)) {
            return summOfOperation;
        } else {
            System.out.println("заглушка. реализовать для других валют");
            return 0;
        }
    }

    private String getCurFromAcc(String acc) {
        System.out.println("getCurFromAcc");
        String result = "";
        if (acc.length() >= 20) {
            String numOfCur = acc.substring(5, 8);

            if (numOfCur.equals("810")) {
                result = "RUR";
            } else if (numOfCur.equals("840")) {
                result = "USD";
            } else if (numOfCur.equals("978")) {
                result = "EUR";
            } else {
                System.out.println("Wrong currency");
                result = "ERR";
            }
        } else {
            System.out.println("не настоящий счет");
            result = "ER1";
        }
        return result;
    }

}

