package diplom.administration;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SettingsFrame extends JFrame{

    private int topPointY;
    private int leftPointX;
    private static JCheckBox isTestingRegistration;
    private static int FRAME_HEIGHT = 480;
    private static int FRAME_WIDTH = 480;

    public SettingsFrame(String nameOfFrame){
        super(nameOfFrame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel pane = (JPanel) getContentPane();
        pane.setLayout(new GridLayout(9,1,20,20));
        initFrameComponents(pane);


        pack();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        topPointY = (screen.height - FRAME_HEIGHT) / 2;
        leftPointX = (screen.width - FRAME_WIDTH) / 2;
        setBounds(leftPointX,topPointY,FRAME_WIDTH,FRAME_HEIGHT);
        setResizable(false);
        setVisible(true);

    }

    public static boolean isSelectedTestingRegistration(){
        if (isTestingRegistration == null)
            return false;
        return isTestingRegistration.isSelected();
    }

    private void initFrameComponents(JPanel panel) {

        isTestingRegistration = new JCheckBox("Тестирование регистрации клиентов");
        isTestingRegistration.setMnemonic(KeyEvent.VK_N);
        isTestingRegistration.setSelected(true);
        JCheckBox someOtherOption = new JCheckBox("Другая опция");


        panel.add(isTestingRegistration);
        panel.add(someOtherOption);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SettingsFrame sf = new SettingsFrame("Настройки");
    }

}
