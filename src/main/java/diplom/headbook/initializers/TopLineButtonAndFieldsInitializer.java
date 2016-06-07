package diplom.headbook.initializers;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JComponent;

/**
 * Класс формирования верхнего полосы с кнопками и полями
 *
 * @author александр талисманов
 * @version альфа 1.00 14.03.2016
 */
public class TopLineButtonAndFieldsInitializer {

    public TopLineButtonAndFieldsInitializer() {

    }

    public static int addNextComponentInLine(Container pane, JComponent component, int x){
        Dimension componentSize = component.getPreferredSize();
        Insets insets = pane.getInsets();
        if (component.getClass().getName().equals("javax.swing.JTextField")){
            componentSize.height = 27;
            component.setBounds(insets.left+x,insets.top,componentSize.width+150, componentSize.height-5);
            x += componentSize.width+150;
        } else {
            component.setBounds(insets.left+x,insets.top,componentSize.width, componentSize.height);
            x += componentSize.width;
        }

        pane.add(component);
        return x;
    }
}

