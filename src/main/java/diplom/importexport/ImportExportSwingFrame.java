package diplom.importexport;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.*;

/**
 * Created by a.talismanov on 07.06.2016.
 */
public class ImportExportSwingFrame extends JFrame{
    private static GridPane topGridPane;
    private static GridPane centerGridPane;
    private static GridPane bottomGridPane;
    private static ToggleGroup groupInFormat;
    private static BorderPane borderPane;
    private static final int APPLICATION_HEIGHT = 458;
    private static final int APPLICATION_WIDTH = 550;
    private int topPointY;
    private int leftPointX;

    private static ComboBox fromTableComboBox;
    private static Button exitButton;

    static ComboBox getFromTableComboBox() {
        return fromTableComboBox;
    }


    static ToggleGroup getGroupInFormat() {
        return groupInFormat;
    }


    public static BorderPane getBorderPane(){
        return borderPane;
    }


    public ImportExportSwingFrame(String str){
        super(str);
        final JFXPanel fxPanel = new JFXPanel();
        add(fxPanel);
        exitButton = new Button("Выход");
        exitButton.setOnAction(e -> dispose());
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        topPointY = (screen.height - APPLICATION_HEIGHT) / 2;
        leftPointX = (screen.width - APPLICATION_WIDTH) / 2;
        setBounds(leftPointX,topPointY,APPLICATION_WIDTH,APPLICATION_HEIGHT);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Platform.runLater(() -> initFX(fxPanel));
    }

    private static void initAndShowGui() {
        JFrame frame = new JFrame("Импорт/Экспорт данных");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setVisible(true);

        Platform.runLater(() -> initFX(fxPanel));
        
    }

    private static void initFX(JFXPanel fxPanel) {
        Group root = new Group();
        Scene scene = new Scene(root, 533,420, Color.WHITE);
        fxPanel.setScene(scene);
        borderPane = new BorderPane();
        borderPane.setPrefSize(533,420);


        topGridPane = new GridPane();
        topGridPane.setHgap(5);
        topGridPane.setVgap(5);
        topGridPane.setPadding(new Insets(15));

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(50);
        topGridPane.getColumnConstraints().addAll(column1,column2);


        ToggleGroup group = new ToggleGroup();
        RadioButton buttonImport = new RadioButton("Импорт");
        buttonImport.setToggleGroup(group);

        RadioButton buttonExport = new RadioButton("Экспорт");
        buttonExport.setToggleGroup(group);
        buttonExport.setSelected(true);

        GridPane.setHalignment(buttonExport, HPos.LEFT);
        topGridPane.add(buttonExport,0,0);
        GridPane.setHalignment(buttonImport, HPos.RIGHT);
        topGridPane.add(buttonImport,1,0);

        final String cssDefault =  "-fx-border-color: grey;\n"
                + "-fx-border-width: 1;\n";
        topGridPane.setStyle(cssDefault);
        borderPane.setTop(topGridPane);

        centerGridPane = new GridPane();
        centerGridPane.getColumnConstraints().addAll(column1,column2);
        GridPane.setColumnSpan(centerGridPane,2);
        centerGridPane.setHgap(15);
        centerGridPane.setVgap(15);
        centerGridPane.setPadding(new Insets(15));


        Label tableLabel = new Label("Таблица");
        GridPane.setConstraints(tableLabel,0,0);


        ObservableList<String> FromTableList = FXCollections.observableArrayList("TRN","ACC","CUS");
        fromTableComboBox = new ComboBox(FromTableList);
        fromTableComboBox.getSelectionModel().select("TRN");

        GridPane.setConstraints(fromTableComboBox,0,1);

        Label inFormatLabel = new Label("В формат:");

        GridPane.setConstraints(inFormatLabel,0,2);

        groupInFormat = new ToggleGroup();

        RadioButton buttonInJson = new RadioButton("JSON");
        buttonInJson.setToggleGroup(groupInFormat);
        GridPane.setConstraints(buttonInJson,0,3);
        buttonInJson.setSelected(true);

        RadioButton buttonInXML = new RadioButton("XML");
        GridPane.setConstraints(buttonInXML,0,4);
        buttonInXML.setToggleGroup(groupInFormat);

        RadioButton buttonInCSV = new RadioButton("CSV");
        GridPane.setConstraints(buttonInCSV,0,5);
        buttonInCSV.setToggleGroup(groupInFormat);


        Label fromFormatLabel =  new Label("Из формата");
        GridPane.setConstraints(fromFormatLabel,1,0);

        ToggleGroup groupOutFormat = new ToggleGroup();

        RadioButton buttonFromJson = new RadioButton("JSON");
        buttonFromJson.setToggleGroup(groupOutFormat);
        GridPane.setConstraints(buttonFromJson,1,1);
        buttonFromJson.setSelected(true);

        RadioButton buttonFromXML = new RadioButton("XML");
        GridPane.setConstraints(buttonFromXML,1,2);
        buttonFromXML.setToggleGroup(groupOutFormat);

        RadioButton buttonFromCSV = new RadioButton("CSV");
        GridPane.setConstraints(buttonFromCSV,1,3);
        buttonFromCSV.setToggleGroup(groupOutFormat);

        Label inTableLabel = new Label("В таблицу");
        GridPane.setConstraints(inTableLabel,1,4);

        ObservableList<String> InTableList = FXCollections.observableArrayList("TRN","ACC","CUS");
        ComboBox<String> inTableComboBox = new ComboBox<>(InTableList);
        inTableComboBox.getSelectionModel().select("TRN");
        GridPane.setConstraints(inTableComboBox,1,5);

        Button selectPathButton = new Button("Выбрать папку или файл");
        GridPane.setConstraints(selectPathButton,0,6);

        TextField selectedPath = new TextField("C:\\ProgramFiles\\Import");
        selectedPath.setPrefSize(300,selectedPath.getPrefHeight());
        GridPane.setConstraints(selectedPath,1,6);

        centerGridPane.getChildren().addAll(tableLabel,fromFormatLabel,fromTableComboBox,inFormatLabel,
                buttonInJson,buttonInXML,buttonInCSV,buttonFromJson,buttonFromXML,buttonFromCSV,inTableLabel,
                inTableComboBox,selectPathButton,selectedPath);
        borderPane.setCenter(centerGridPane);


        bottomGridPane = new GridPane();
        bottomGridPane.getColumnConstraints().addAll(column1,column2);
        GridPane.setColumnSpan(bottomGridPane,2);
        bottomGridPane.setHgap(15);
        bottomGridPane.setVgap(15);
        bottomGridPane.setPadding(new Insets(15));

        Button executeButton = new Button("Выполнить");
        GridPane.setConstraints(executeButton,0,0);
        executeButton.setOnAction( ae -> {
            if (buttonExport.isSelected()) {
                new ImportExportModel().exportFromDB();
            } else {
                // сделать импорт
                new ImportExportModel().importIntoDB();
            }
        });


        GridPane.setConstraints(exitButton,1,0);


        bottomGridPane.getChildren().addAll(executeButton, exitButton);

        bottomGridPane.setStyle(cssDefault);

        borderPane.setBottom(bottomGridPane);

        root.getChildren().add(borderPane);

    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new ImportExportSwingFrame("test"));
    }
}

