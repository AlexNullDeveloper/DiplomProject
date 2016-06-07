package diplom.importexport;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by a.talismanov on 02.06.2016.
 */
public class ImportExportFrame extends Application {
    private Stage primaryStage;
    private static BorderPane borderPane;
    private GridPane topGridPane;
    private GridPane centerGridPane;
    private GridPane bottomGridPane;
    private static ToggleGroup groupInFormat;


    private static ComboBox fromTableComboBox;

    public static ComboBox getFromTableComboBox() {
        return fromTableComboBox;
    }


    public static ToggleGroup getGroupInFormat() {
        return groupInFormat;
    }


    public static BorderPane getBorderPane(){
        return borderPane;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Импорт/Экспорт данных");

        Group root = new Group();
//        Scene scene = new Scene(root, 640,480, Color.WHITE);
        Scene scene = new Scene(root, 533,420, Color.WHITE);

        borderPane = new BorderPane();
        borderPane.setPrefSize(533,420);


        topGridPane = new GridPane();
        topGridPane.setHgap(5);
        topGridPane.setVgap(5);
        topGridPane.setPadding(new Insets(15));
//        topGridPane.getColumnConstraints().add(new ColumnConstraints(75));
//        topGridPane.getColumnConstraints().add(new ColumnConstraints(270));

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
                // + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 1;\n"
                // + "-fx-border-style: dashed;\n"
                ;

        //topGridPane.setStyle("-fx-background-color: palegreen; -fx-padding: 2; -fx-hgap: 2; -fx-vgap: 2;");
        topGridPane.setStyle(cssDefault);
        borderPane.setTop(topGridPane);

        centerGridPane = new GridPane();
//        ColumnConstraints column1 = new ColumnConstraints();
//        column1.setPercentWidth(50);
//        ColumnConstraints column2 = new ColumnConstraints();
//        column1.setPercentWidth(50);
        centerGridPane.getColumnConstraints().addAll(column1,column2);
        //centerGridPane.getColumnConstraints().add(new ColumnConstraints(75));
        //centerGridPane.getColumnConstraints().add(new ColumnConstraints(300));
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
        //centerGridPane.getColumnConstraints().add(new ColumnConstraints(75));
        //centerGridPane.getColumnConstraints().add(new ColumnConstraints(300));
        GridPane.setColumnSpan(bottomGridPane,2);
        bottomGridPane.setHgap(15);
        bottomGridPane.setVgap(15);
        bottomGridPane.setPadding(new Insets(15));

        Button executeButton = new Button("Выполнить");
        GridPane.setConstraints(executeButton,0,0);
        executeButton.setOnAction( ae -> new ImportExportModel().exportFromDB());

        Button exitButton = new Button("Выход");
        GridPane.setConstraints(exitButton,1,0);
        exitButton.setOnAction(e -> primaryStage.hide());

        bottomGridPane.getChildren().addAll(executeButton, exitButton);

        bottomGridPane.setStyle(cssDefault);

        borderPane.setBottom(bottomGridPane);

        root.getChildren().add(borderPane);
        //root.getChildren().add(tableCatGrp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String... args){
        Application.launch(ImportExportFrame.class);
    }

}
