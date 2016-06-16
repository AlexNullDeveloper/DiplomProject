package diplom.launcher;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;


/**
 * Пускач
 *
 * @author талисманов александр
 * @version 14.03.2016
 */
public class Launcher extends JFrame {

    //SingleTon с условной блокировкой
    private volatile static Launcher launcherInstance;

    private static Dimension componentSize;
    private JTextField login;
    private JPasswordField password;
    private static String usr;
    private JButton buttonLogIn;
    private static final int APPLICATION_WIDTH = 300;
    private static final int APPLICATION_HEIGHT = 200;
    private static int topPointY = 0;
    private static int leftPointX = 0;
    private Dimension screen;

    /**
     * Constructor for objects of class launcher
     */
    private Launcher(){
        Container pane = getContentPane();
        setResizable(false);
        pane.setLayout(null);

        initComponents(pane);
        //кнопка войти по энтеру
        getRootPane().setDefaultButton(buttonLogIn);

        pack();
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        topPointY = (screen.height - APPLICATION_HEIGHT) / 2;
        leftPointX = (screen.width - APPLICATION_WIDTH) / 2;
        setBounds(leftPointX,topPointY,APPLICATION_WIDTH,APPLICATION_HEIGHT);
        setVisible(true);

    }
    /* метод для синглтона*/
    static Launcher getInstance(){
        if (launcherInstance == null){
            synchronized (Launcher.class){
                if (launcherInstance == null){
                    launcherInstance = new Launcher();
                }
            }
        }
        return launcherInstance;
    }

    private void initComponents(Container pane){
        //установка ярлыка логина
        JLabel labelLogin = new JLabel("Пользователь");
        componentSize = labelLogin.getPreferredSize();
        labelLogin.setBounds(10,10,componentSize.width, componentSize.height);
        pane.add(labelLogin);

        //установка поля логин
        login = new JTextField("");
        componentSize = login.getPreferredSize();
        login.setBounds(10,30,componentSize.width+275, componentSize.height+10);
        pane.add(login);

        //установка ярлыка логина
        JLabel labelPassword = new JLabel("Пароль");
        componentSize = labelPassword.getPreferredSize();
        labelPassword.setBounds(10,60,componentSize.width, componentSize.height);
        pane.add(labelPassword);

        //установка поля логин
        password = new JPasswordField("");
        componentSize = password.getPreferredSize();
        password.setBounds(10,80,componentSize.width+275, componentSize.height+10);
        pane.add(password);


        buttonLogIn = new JButton("Войти");
        componentSize = buttonLogIn.getPreferredSize();
        buttonLogIn.setBounds(10,120,componentSize.width+65, componentSize.height+10);
        buttonLogIn.addActionListener(new LogInItemListener());
        pane.add(buttonLogIn);

        JButton buttonExit = new JButton("Выход");
        componentSize = buttonExit.getPreferredSize();
        buttonExit.setBounds(150,120,componentSize.width+65, componentSize.height+10);
        buttonExit.addActionListener(new ExitItemListener());
        pane.add(buttonExit);

    }

    private class ExitItemListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }

    private class LogInItemListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //connection k baze
            LauncherModel launcherModel =  new LauncherModel();
            if (launcherModel.Connect(login, password)){
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e){
                e.printStackTrace();
            }

            Launcher puskach = Launcher.getInstance();
        });
    }
}
