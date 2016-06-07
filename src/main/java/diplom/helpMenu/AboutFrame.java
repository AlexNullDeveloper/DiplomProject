package diplom.helpMenu;

/**
 * Created by a.talismanov on 28.04.2016.
 */
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Frame открывающийся по кнопке About.
 *
 * @author aleksander talismanov
 * @version alpha 1.00 11.03.2016
 */



public class AboutFrame extends JFrame{

    public AboutFrame(String nameOfFrame){
        super(nameOfFrame);
        final JFXPanel fxPanel = new JFXPanel();
        add(fxPanel);  //fx панель
        setResizable(false);
        pack();
        setBounds(600,350,640,230);
        setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }

    private static void initFX(JFXPanel fxPanel){
        //init FX components
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private static Scene createScene(){
        Group root = new Group();
        Scene scene = new Scene(root, Color.LIGHTBLUE);
        Text text = new Text();
        HBox box = new HBox();


        text.setX(20);
        text.setY(100);
        text.setFont(new Font(14));
        text.setText("Данное приложение разработано\n" +
                     "Талисмановым А.Ю. \n" +
                     "Студентом МГТУ им. Н.Э.Баумана\n\n" +
                     "Талисманов А.Ю. обладатель\n" +
                     "Сертификатов OCA Java Programmer\n" +
                     "и OCA PL\\SQL Developer\n\n" +
                     "Версия продукта 1.0.0");


        String img = "C:/Users/a.talismanov/IdeaProjects/DiplomProject/src/main/java/diplom/helpMenu/OCABadge.png";
        URL fileURL = null;
        try {
            fileURL = new File(img).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String fileURLString = fileURL.toString();
        Image image = new Image(fileURLString);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        Image imageMeAndDog = new Image("http://cs.nblz.ru/pp/c625223/v625223998/5d1f7/9SbpqyGJtB0.jpg");

        ImageView imageView2 = new ImageView(imageMeAndDog);
        imageView2.setFitHeight(200);
        imageView2.setFitWidth(200);
        //imageView.setX(250);
        box.getChildren().add(imageView2);
        box.getChildren().add(text);
        box.getChildren().add(imageView);


        root.getChildren().add(box);
        return scene;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AboutFrame aboutFrame  = new AboutFrame("About...");
            }
        });
    }
}