package diplom.launcher;

/**
 * Created by a.talismanov on 28.04.2016.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import diplom.mainWindow.MainFrame;
import diplom.util.ConnectionSingleton;
import org.hibernate.cfg.Configuration;

public class LauncherModel {
    private boolean isOk = true;

    public boolean Connect(JTextField loginField, JTextField passwordField) {

        //String dbName = "XE";
        //URL DB дома
        //String dbURL="jdbc:oracle:thin:@talismanov-pc:1521:" + dbName;

        String dbName = "PSRF";
        //URL DB на работе
        String dbURL = "jdbc:oracle:thin:@10.12.44.10:1521:" + dbName;

        Locale.setDefault(new Locale("en", "US"));

        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String selectFromT1 = "select USER from dual";
        ConnectionSingleton.setInstance(dbURL, loginField.getText(), passwordField.getText());


        //приготовим Hibernate Configuration

        Configuration hibConfiguration = new Configuration();
        hibConfiguration.setProperty("hibernate.connection.url",dbURL);
        hibConfiguration.setProperty("hibernate.connection.username",loginField.getText());
        hibConfiguration.setProperty("hibernate.connection.password",passwordField.getText());





        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ClassEx) {
            System.out.println("класс oracle.jdbc.driver.OracleDriver не найден");
        }

        try {
            //connection = DriverManager.getConnection(dbURL, loginField.getText(), passwordField.getText());

            connection = ConnectionSingleton.getInstance();

            preparedStatement = connection.prepareStatement(selectFromT1);

            resultSet = preparedStatement.executeQuery();

            String usr = "";

            while (resultSet.next()) {
                usr = resultSet.getString(1);
            }

            MainFrame mainFrame;
            mainFrame = new MainFrame(usr, dbName);
            //dispose();

        } catch (SQLException SQLe) {
            isOk = false;
            //ErrorForm errorForm = new ErrorForm("не правильный логин или пароль");

            JOptionPane.showMessageDialog(Launcher.getInstance(), "Не правильный логин или пароль", "", JOptionPane.INFORMATION_MESSAGE);


        } catch (NullPointerException NPe) {
            isOk = false;
            JOptionPane.showMessageDialog(Launcher.getInstance(), "Не правильный логин или пароль", "", JOptionPane.WARNING_MESSAGE);
            //NPe.printStackTrace();
        } finally {
            return isOk;
        }

    }
}
