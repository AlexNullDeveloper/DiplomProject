package diplom.catalogs;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.math.BigInteger;

/**
 * Created by a.talismanov on 06.06.2016.
 */
public class Adress extends Application {
    private static BigInteger currentAdrId;
    private static TextArea jurAdrTextArea;
    private static TextArea pochtAdrTextArea;
    private static TextArea fizAdrTextArea;

    private static String jurAdr;
    private static String fizAdr;
    private static String pochtAdr;


    public static BigInteger getCurrentAdrId() {
        return currentAdrId;
    }

    public static void setCurrentAdrId(BigInteger id) {
        System.out.println("current Id = " + id);
        currentAdrId = id;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Адреса");

        Group root = new Group();
        Scene scene = new Scene(root, 466, 290, Color.WHITE);

        BorderPane rootPane = new BorderPane();

        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(5, 5, 5, 5));

        mainGrid.setHgap(5);
        mainGrid.setVgap(5);
        mainGrid.setPadding(new Insets(15));


        Label fizAdrLabel = new Label("Физ. адрес");

        fizAdrLabel.setMinSize(fizAdrLabel.getPrefWidth(), fizAdrLabel.getPrefHeight());

        fizAdrTextArea = new TextArea();
        fizAdrTextArea.setPrefRowCount(3);
        fizAdrTextArea.setPrefColumnCount(300);
        fizAdrTextArea.setWrapText(true);
        fizAdrTextArea.setPrefWidth(375);
        GridPane.setHalignment(fizAdrTextArea, HPos.CENTER);

        mainGrid.add(fizAdrLabel, 0, 0);
        mainGrid.add(fizAdrTextArea, 1, 0);

        Label jurAdrLabel = new Label("Юр. адрес");

        jurAdrLabel.setMinSize(jurAdrLabel.getPrefWidth(), jurAdrLabel.getPrefHeight());

        jurAdrTextArea = new TextArea();
        jurAdrTextArea.setPrefRowCount(3);
        jurAdrTextArea.setPrefColumnCount(300);
        jurAdrTextArea.setWrapText(true);
        jurAdrTextArea.setPrefWidth(300);
        GridPane.setHalignment(jurAdrTextArea, HPos.CENTER);

        mainGrid.add(jurAdrLabel, 0, 1);
        mainGrid.add(jurAdrTextArea, 1, 1);

        Label pochtAdrLabel = new Label("Почт. адрес");

        pochtAdrLabel.setMinSize(pochtAdrLabel.getPrefWidth(), pochtAdrLabel.getPrefHeight());

        pochtAdrTextArea = new TextArea();
        pochtAdrTextArea.setPrefRowCount(3);
        pochtAdrTextArea.setPrefColumnCount(300);
        pochtAdrTextArea.setWrapText(true);
        pochtAdrTextArea.setPrefWidth(300);
        GridPane.setHalignment(pochtAdrTextArea, HPos.CENTER);

        mainGrid.add(pochtAdrLabel, 0, 2);
        mainGrid.add(pochtAdrTextArea, 1, 2);

        AdressModel adressModel = new AdressModel();

        AdressJpa adressJpa = adressModel.getAdressFromDBbyId();
        fillTextAreasWithData(adressJpa);

        HBox confirmOrExitPane = new HBox();

        confirmOrExitPane.setPadding(new Insets(3, 3, 3, 7));


        Button saveButton = new Button("Сохранить");
        saveButton.setAlignment(Pos.CENTER);
        confirmOrExitPane.getChildren().add(saveButton);


        Button exitButton = new Button("Выход");
        exitButton.setAlignment(Pos.CENTER);
        confirmOrExitPane.setSpacing(330);
        confirmOrExitPane.getChildren().add(exitButton);

        exitButton.setOnAction(ae -> primaryStage.hide());
        saveButton.setOnAction(e -> {
            if (!fizAdrTextArea.getText().equals(fizAdr) || !jurAdrTextArea.getText().equals(jurAdr)
                    || !pochtAdrTextArea.getText().equals(pochtAdr)){
                adressModel.updateAdresses(fizAdrTextArea.getText(),jurAdrTextArea.getText(),pochtAdrTextArea.getText());
            }
            primaryStage.close();
        });

        rootPane.setCenter(mainGrid);
        rootPane.setBottom(confirmOrExitPane);

        root.getChildren().add(rootPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void fillTextAreasWithData(AdressJpa adressJpa) {
        if (adressJpa != null) {
            jurAdr = adressJpa.getUrAdr();
            fizAdr = adressJpa.getFizAdr();
            pochtAdr = adressJpa.getPochtAdr();

            fizAdrTextArea.setText(fizAdr);
            jurAdrTextArea.setText(jurAdr);
            pochtAdrTextArea.setText(pochtAdr);
        }
    }

    public static void main(String[] args) {
        Adress.launch(Adress.class, args);
    }

}
