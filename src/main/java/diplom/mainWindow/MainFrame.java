package diplom.mainWindow;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;

import diplom.catalogs.CatalogOfClientsSwing;
import diplom.catalogs.accounts.AccFrame;
import diplom.catalogs.version.VersionFrame;
import diplom.administration.SettingsFrame;
import diplom.helpMenu.AboutFrame;
import diplom.importexport.ImportExportSwingFrame;
import diplom.registration.platDocument.RegPlatDocumentsInNationalCurrency;
import diplom.headbook.presenter.ReestrDocumentsFrame;
import diplom.moduleDate.ModuleDateFrame;
import diplom.moduleDate.ModuleDateModel;
import diplom.util.ConnectionSingleton;

/**
 * Основное меню приложения
 *
 * @author aleksander_talismanov
 * @version alpha 1.00 11.03.2016
 */

public class MainFrame extends JFrame
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Connection connection;
    private static final int APPLICATION_HEIGHT = 768;
    private static final int APPLICATION_WIDTH = 1024;
    private int topPointY;
    private int leftPointX;
    private static String sysdate = null;

    public MainFrame(String nameOfFrame, String nameOfDB)
    {
        //super("Бухгалтерия talismanoffTM (Пользователь " + nameOfFrame + " ) База " + nameOfDB);
        super("JavABS. Бухгалтерия (Пользователь " + nameOfFrame + " ) База " + nameOfDB);
        setResizable(false);
        this.connection = ConnectionSingleton.getInstance();

        JMenuBar mainMenuBar = new JMenuBar();

        //инициализируем элементы меню
        initComponents(mainMenuBar);
        //устанавливаем menuBar
        setJMenuBar(mainMenuBar);
        pack();

        System.out.println("дата модуля " + ModuleDateFrame.getSysdate());
        ModuleDateModel mdm = new ModuleDateModel();
        mdm.getSystemDateFromDB();
        System.out.println("дата модуля после " + ModuleDateFrame.getSysdate());


        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        topPointY = (screen.height - APPLICATION_HEIGHT) / 2;
        leftPointX = (screen.width - APPLICATION_WIDTH) / 2;
        setBounds(leftPointX,topPointY,APPLICATION_WIDTH,APPLICATION_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void setModuleDate(String str){
        sysdate =  str;
    }

    public static String getModuleDate(){
        return sysdate;
    }


    private class ExitItemListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (connection != null){
                try{
                    connection.close();
                } catch (SQLException SQLe){
                    SQLe.printStackTrace();
                }
            }
            System.exit(0);
        }
    }

    private class AboutItemListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            AboutFrame aboutFrame = new AboutFrame("About");
        }
    }

    private class ReestrDocumentsListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            ReestrDocumentsFrame reestrDocumentsFrame = new ReestrDocumentsFrame("Реестр документопроводок", connection);
        }
    }

    private class PlatDocRegListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            RegPlatDocumentsInNationalCurrency regPlatDocumentsInNationalCurrency
                    = new RegPlatDocumentsInNationalCurrency("Регистрация платежных документов");
        }
    }

    private JMenu initCatalogMenu(){
        JMenu catalogsMenu = new JMenu("Каталоги");
        JMenuItem accCatalogItem = new JMenuItem("Каталог счетов");
        accCatalogItem.addActionListener(ae -> new AccFrame("Счета клиентов"));
        catalogsMenu.add(accCatalogItem);

        JMenuItem clientCatalogItem = new JMenuItem("Каталог клиентов");
        catalogsMenu.add(clientCatalogItem);
        clientCatalogItem.addActionListener(ae -> new CatalogOfClientsSwing("Каталог клиентов"));

        JMenuItem versionItem = new JMenuItem("Версия АБС");
        versionItem.addActionListener( e -> new VersionFrame("Версия"));
        catalogsMenu.add(versionItem);

        JMenuItem exitItem  = new JMenuItem("Выход");
        catalogsMenu.add(exitItem);
        //слушаем нажатие кнопки выход
        exitItem.addActionListener(new ExitItemListener());

        return catalogsMenu;
    }

    private JMenu iniRegistrationMenu(){
        JMenu registrationMenu = new JMenu("Регистрация");
// до лучших времен
//        JMenuItem cashDocumentsRegistration = new JMenuItem("Регистрация кассовых документов");
//        registrationMenu.add(cashDocumentsRegistration);
//
//        JMenuItem memOrderRegistration = new JMenuItem("Регистрация мемориального ордера");
//        registrationMenu.add(memOrderRegistration);

        JMenuItem PlatDocRegistration = new JMenuItem("Регистрация платежного документа");
        PlatDocRegistration.addActionListener(new PlatDocRegListener());
        registrationMenu.add(PlatDocRegistration);

        return registrationMenu;
    }

    private JMenu initHeadBookMenu(){
        JMenu headBookMenu = new JMenu("Главная книга");

        JMenuItem documentsReestrItem = new JMenuItem("Реестр документов");
        headBookMenu.add(documentsReestrItem);
        documentsReestrItem.addActionListener(new ReestrDocumentsListener());

        return headBookMenu;
    }

    private JMenu initHelpMenu(){
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About...");
        helpMenu.add(aboutItem);

        aboutItem.addActionListener(new AboutItemListener());

        return helpMenu;
    }


    private void initComponents(JMenuBar menuBar){
        menuBar.add(initCatalogMenu());
        menuBar.add(iniRegistrationMenu());
        menuBar.add(initHeadBookMenu());
        menuBar.add(initDateOfModule());
        menuBar.add(initAdministationMenu());
        menuBar.add(initImportMenu());
        menuBar.add(initHelpMenu());
    }

    private JMenu initImportMenu(){
        JMenu importMenu = new JMenu("Импорт/Экспорт");

        JMenuItem importMenuItem = new JMenuItem("Импорт/Экспорт данных");
        importMenu.add(importMenuItem);

        importMenuItem.addActionListener( ae -> new ImportExportSwingFrame("Импорт/экспорт данных"));

        return importMenu;
    }

    private JMenu initAdministationMenu() {
        JMenu administrationMenu = new JMenu("Управление");

        JMenuItem settingsItem = new JMenuItem("Настройки");
        administrationMenu.add(settingsItem);

        settingsItem.addActionListener((ae) -> new SettingsFrame("Настройки"));
        return administrationMenu;
    }

    private JMenu initDateOfModule() {
        JMenu dateOfModuleMenu = new JMenu("Дата модуля");

        JMenuItem setModuleDateItem =  new JMenuItem("Установить дату модуля");
        dateOfModuleMenu.add(setModuleDateItem);
        setModuleDateItem.addActionListener(ae -> {
            ModuleDateFrame moduleDateFrame = new ModuleDateFrame();
        });

        return dateOfModuleMenu;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            } catch (Exception e){
                e.printStackTrace();
            }
            MainFrame mainFrame = new MainFrame("Бухгалтерия talismanoffTM","testBaza");
        });
    }
}