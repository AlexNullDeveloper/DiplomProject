package diplom.headbook.presenter;

import diplom.registration.platDocument.Document;
import diplom.util.ConnectionSingleton;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by a.talismanov on 01.06.2016.
 */
public class ReestrDocumentModel {
    private static Connection connect = ConnectionSingleton.getInstance();
    /**
     * получаем resultSet в данными из таблички TRN
     */
    public static ResultSet getResultSetFromTableTRN(){
        ResultSet rs = null;
        String selectDataTrnSQL = "SELECT DOGNUM, DATE_SUCCESS, ACC_DEB, CUR_DEB, ACC_CRED, CUR_CRED, SUM_DEB, SUM_CRED "
                + " FROM TRN "
                + " ORDER BY DOGNUM"
                ;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connect.prepareStatement(selectDataTrnSQL);
            rs = preparedStatement.executeQuery();
        } catch (SQLException SQLe){
            SQLe.printStackTrace();
        } catch (NullPointerException NullPointer){
            System.out.println("connection пустой, Тестовый режим");
            //NullPointer.printStackTrace();
        }
        return rs;
    }

    public static Document getDocumentByDocnumAndDate(int docId, java.sql.Date dateSuccess){
        int id = -1;
        try{
            String sql = "SELECT ID FROM TRN WHERE DOGNUM = ? AND DATE_SUCCESS = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1,docId);
            preparedStatement.setDate(2,dateSuccess);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt("ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Document document = new Document();
        int payerId = -1;
        int recipientId = -1;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            String sql = "SELECT BO_1, BO_2, PACHKA, DOC_NUM, OCH_PLAT, DATE_VAL, DATE_DOC, " +
                    "SUMM, CURRENCY, " +
                    "TYPE_OPER, PAYMENT_PURPOSE, DATE_REG, DATE_RECEIPT, PAYER_ID, RECIP_ID, USER_REG  " +
                    "FROM TRN_DETAIL " +
                    "WHERE TRN_ID = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
//            System.out.println("перед resultSEt.next");
//            System.out.println("id " + id);

            if (resultSet.next()) {
                document.bo1 = resultSet.getInt("BO_1");
                document.bo2 = resultSet.getInt("BO_2");
                document.pachka = resultSet.getInt("PACHKA");
                document.docNo = resultSet.getInt("DOC_NUM");
                document.ochPlat = resultSet.getInt("OCH_PLAT");
                document.dateVal = resultSet.getDate("DATE_VAL");
                document.dateDoc = resultSet.getDate("DATE_DOC");
                document.summOfOperation = resultSet.getDouble("SUMM");
                document.currency = resultSet.getString("CURRENCY");
                document.typeoper = resultSet.getString("TYPE_OPER");
                document.naznachiePlatezha = resultSet.getString("PAYMENT_PURPOSE");
                document.dateReg = resultSet.getDate("DATE_REG");
                document.dateSupply = resultSet.getDate("DATE_RECEIPT");
                document.userRegistered = resultSet.getString("USER_REG");
                payerId = resultSet.getInt("PAYER_ID");
                recipientId = resultSet.getInt("RECIP_ID");
            }

            sql = "SELECT PAYMENT_COR_ACC, PAYMENT_ACC, PAYER_INN, PAYER_KPP, PAYER_NAME " +
                    "FROM PAYER "+
                    "WHERE PAYER_ID = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1,payerId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                document.ksPayer = resultSet.getString(1);
                document.lsPayer = resultSet.getString(2);
                document.innPayer = resultSet.getLong(3);
                document.kppPayer = resultSet.getLong(4);
                document.namePayer = resultSet.getString(5);
            }


            sql = "SELECT RECIP_BIC, RECIP_COR_ACC, RECIP_ACC, RECIP_INN, RECIP_KPP, " +
                    "RECIP_RKC, RECIP_BANK, RECIP_NAME, RECIP_KU " +
                    "FROM RECIPIENT "+
                    "WHERE RECIP_ID = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1,recipientId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                document.bicRecipient = resultSet.getString(1);
                document.ksRecipient = resultSet.getString(2);
                document.lsRecipient = resultSet.getString(3);
                document.innRecipient = resultSet.getLong(4);
                document.kppRecipient = resultSet.getLong(5);
                document.rkc = resultSet.getString(6);
                document.bank = resultSet.getString(7);
                document.nameRecipient = resultSet.getString(8);
                document.kuRecipient = resultSet.getString(9);
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

            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return document;
    }
}
