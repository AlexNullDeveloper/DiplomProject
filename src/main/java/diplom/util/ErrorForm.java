package diplom.util;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * Форма по которой позывается ошибка
 *
 * @author aleksander_talsimanov
 * @version 15.03.2016
 */
public class ErrorForm extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Dimension componentSize;
    private JButton buttonOK;

    public ErrorForm(String errorText){
        super();
        Container pane = getContentPane();
        setResizable(false);
        pane.setLayout(null);

        initError(pane, errorText);
        getRootPane().setDefaultButton(buttonOK);

        pack();
        setBounds(500,500,500,160);
        setVisible(true);
    }

    public void initError(Container pane, String textOfError){
        JLabel labelError = new JLabel(textOfError);
        componentSize = labelError.getPreferredSize();
        labelError.setBounds(125,40,componentSize.width,componentSize.height);
        pane.add(labelError);

        buttonOK = new JButton("OK");
        componentSize = buttonOK.getPreferredSize();
        buttonOK.setBounds(225,90,componentSize.width, componentSize.height);
        buttonOK.addActionListener(new OkButtonListener());
        pane.add(buttonOK);
    }

    class OkButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            dispose();
        }
    }


    public static void main(String[] args){
        ErrorForm errorForm = new ErrorForm("Не правильный логин или пароль");
    }
}
