package diplom.moduleDate;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import diplom.util.ConnectionSingleton;

public class ModuleDateModel {
    public void getSystemDateFromDB() {
        PreparedStatement preparedStatement =  null;
        ResultSet resultSet = null;
        String sysdate = ModuleDateFrame.getSysdate();
        String selectSysdate = "SELECT TRUNC(SYSDATE) FROM DUAL";

        try {
            Connection connection = ConnectionSingleton.getInstance();
            preparedStatement = connection.prepareStatement(selectSysdate);
            resultSet =  preparedStatement.executeQuery();

            while(resultSet.next()){
                sysdate = resultSet.getString(1);
                System.out.println("inside " + sysdate);
                //sysdate.format("dd.MM.yyyy", resultSet.getString(1)) ;
            }
            sysdate =
                    //день
                    sysdate.substring(8,10)
                            //месяц
                            + "." + sysdate.substring(5, 7)
                            //год
                            + "." + sysdate.substring(0, 4);
            ModuleDateFrame.setSysdate(sysdate);
            System.out.println("outside " + sysdate);


            if (ModuleDateFrame.getFieldSystemDate() != null){
                ModuleDateFrame.getFieldSystemDate().setText(sysdate);
            }

        } catch (SQLException SQLe){
            SQLe.printStackTrace();
        } catch (NullPointerException NPe){
            System.out.println("окно в тестовом режиме. Connection пустой");
        }
    }
}

