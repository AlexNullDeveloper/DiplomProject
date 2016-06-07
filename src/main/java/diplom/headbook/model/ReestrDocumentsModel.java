package diplom.headbook.model;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import diplom.util.ConnectionSingleton;

public class ReestrDocumentsModel {
    private Connection connection;

    public ReestrDocumentsModel(){
        connection = ConnectionSingleton.getInstance();
    }

    public void deleteDocument(Integer dogNumToDelete){

        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM TRN WHERE DOGNUM = ?";

        //заглушка
        //int value = 1;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, dogNumToDelete);

            int result = preparedStatement.executeUpdate();
            System.out.println("result of preparedStatmt delete " + result);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("DELETE FROM TRN EXCEPTION");
            e.printStackTrace();
        }
    }
    public ResultSet getAllRecResultSet() {
        // TODO Auto-generated method stub
        PreparedStatement preparedStatement =  null;
        String sql = "SELECT DOGNUM, DATE_SUCCESS, ACC_DEB, CUR_DEB, ACC_CRED, CUR_CRED, SUM_DEB, SUM_CRED \n" +
                "FROM TRN";
        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("SELECT FROM TRN EXCEPTION");
            e.printStackTrace();
        }

        return rs;
    }
}
