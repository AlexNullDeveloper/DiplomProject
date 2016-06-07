package diplom.util;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class ConnectionSingleton {
    private volatile static Connection connection;
    private static String InstanceUrl;
    private static String InstanceLogin;
    private static String InstancePassword;

    //конструктор синглтона
    private ConnectionSingleton() throws SQLException{
        connection = DriverManager.getConnection(InstanceUrl, InstanceLogin, InstancePassword);
    }

    public static void setInstance(String url, String login, String password){
        InstanceUrl = url;
        InstanceLogin = login;
        InstancePassword = password;
    }


    public static Connection getInstance(){
        if (connection == null){
            synchronized (ConnectionSingleton.class){
                if (connection == null){
                    try {
                        connection = new ConnectionSingleton().connection;
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        System.out.println("Connection getInstance Exception");
                        //e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }
}