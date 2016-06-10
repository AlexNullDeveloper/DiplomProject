package diplom.catalogs.accounts;

import diplom.catalogs.CategoryGroup;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author a.talismanov
 * @version 1.0.0 on 08.06.2016.
 */
public class AccFrame extends JFrame {
    private static ObservableList<String> accIds;
    private static TextField fullNameTextField;
    private static TextField engNameTextField;
    private static TextField userEditTextField;
    private static TextField dateEditTextField;
    private static TextField userRegTextField;
    private static TextField dateZavTextField;
    private static TextField shortNameTextField;
    private static ArrayList<TextField> listOfTextField = new ArrayList<>();
    private final Button exitButton;
    private static String selectedId;

    private BigInteger adrressId;
    private Button editButton;
    private Button saveButton;
    private Button createButton;
    private static ArrayList<String> listOfInitValues;
    private TextField otdTextField;
    private TextField currentOstTextField;

    static String getSelectedId() {
        return selectedId;
    }

    private boolean isEditMode() {
        return editMode;
    }

    private void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    private boolean editMode = false;

    static ObservableList<String> getAccIds() {
        return accIds;
    }

    static void setAccIds(ObservableList<String> list) {
        accIds = list;
    }

    private static final int APPLICATION_HEIGHT = 840;
    private static final int APPLICATION_WIDTH = 1040;
    private int topPointY;
    private int leftPointX;
    private static final String cssDefault = "-fx-border-color: grey;\n"
            + "-fx-border-width: 1;\n";


    public AccFrame(String str) {
        super(str);
        JFXPanel fxPanel = new JFXPanel();
        add(fxPanel);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        topPointY = (screen.height - APPLICATION_HEIGHT) / 2;
        leftPointX = (screen.width - APPLICATION_WIDTH) / 2;
        setBounds(leftPointX, topPointY, APPLICATION_WIDTH, APPLICATION_HEIGHT);

        exitButton = new Button("Выход");
        exitButton.setOnAction(ae -> this.setVisible(false));
        Platform.runLater(() -> initFX(fxPanel));
//        Thread thread = new Runnable(){
//            @Override
//            public void run(){
//
//            }
//        };
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> setVisible(true));
        });
        thread.start();
    }

    private void initFX(JFXPanel fxPanel) {
        Group root = new Group();
        Scene scene = new Scene(root, 1024, 800, javafx.scene.paint.Color.WHITE);
        fxPanel.setScene(scene);
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1024, 800);
        FlowPane topFlowPane = new FlowPane(10, 10);
        setTopFlowPane(topFlowPane);
        borderPane.setTop(topFlowPane);

        BorderPane centralBorderPane = new BorderPane();
        TableView<CategoryGroup> tableCatGrp = initCenterBorderPane(borderPane, centralBorderPane);
        centralBorderPane.setBottom(tableCatGrp);


        AccModel accModel = new AccModel();
        accModel.getAccountsFromDB();

        TableView<Acc> tableOfAccIds = new TableView<>();
        initTableOfAccIds(tableOfAccIds);


        centralBorderPane.setLeft(tableOfAccIds);


        GridPane mainContent = new GridPane();
        initMainContent(mainContent);
        centralBorderPane.setCenter(mainContent);

        VBox panelOfButtons = new VBox();
        initVboxOfButtons(panelOfButtons);
        centralBorderPane.setRight(panelOfButtons);

        FlowPane bottomFlowPane = new FlowPane(10, 10);
        setBottomFlowPane(bottomFlowPane);
        borderPane.setBottom(bottomFlowPane);
        root.getChildren().add(borderPane);
    }

    private void initMainContent(GridPane mainContent) {
        mainContent.setHgap(5);
        mainContent.setVgap(5);
        mainContent.setPadding(new javafx.geometry.Insets(15));

        //заполняем метками и полями
        makeLabelsAndTextField(mainContent);


        Acc currentAcc = AccModel.getAccDetailsFromDB(selectedId);
        fillFieldsWithAccData(currentAcc);
        initArrayListOfTextField();
    }

    private void initTableOfAccIds(TableView<Acc> tableOfAccIds) {
        tableOfAccIds.setTableMenuButtonVisible(true);
        tableOfAccIds.setTooltip(new Tooltip("Номер счета"));
        tableOfAccIds.setPrefWidth(150);
        tableOfAccIds.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn accIdColumn = new TableColumn("№ счета.");
        accIdColumn.setCellValueFactory(new PropertyValueFactory<Acc, String>("accIdProp"));

        accIdColumn.setPrefWidth(150);
        accIdColumn.setResizable(false);
        accIdColumn.setSortable(true);

        ArrayList<Acc> customerArrayList = new ArrayList<>();
        if (accIds == null) {
            System.out.println("Sos numOfClients NULL");
        }
//        System.out.println(accIds);
        for (String itm : accIds) {
            Acc tempAcc = new Acc(itm);
            customerArrayList.add(tempAcc);
        }

        ObservableList<Acc> custObsList = FXCollections.observableArrayList(customerArrayList);

        tableOfAccIds.setItems(custObsList);

        tableOfAccIds.getColumns().add(accIdColumn);
        tableOfAccIds.getSelectionModel().select(0);

        AccModel accModel = new AccModel();

        tableOfAccIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (tableOfAccIds.getSelectionModel().getSelectedItem() != null) {
                selectedId = String.valueOf(newValue.getAccIdProp());
                Acc currentAcc = accModel.getAccDetailsFromDB(selectedId);
                fillFieldsWithAccData(currentAcc);
                setEditMode(false);
            }
        });
        selectedId = tableOfAccIds.getSelectionModel().getSelectedItem().getAccId();

//        //Add change listener
//        tableOfNumsOfClients.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
//            //Check whether item is selected and set value of selected item to Label
//            if (tableOfNumsOfClients.getSelectionModel().getSelectedItem() != null) {
//                selectedId = BigInteger.valueOf(newValue.getCusIdProp());
////                Customer currentCustomer = catalogOfClientsModel.getCustomerDetailsFromDB(selectedId);
////                fillFieldsWithAccData(currentCustomer);
//                setEditMode(false);
//            }
//        });
//        selectedId = tableOfNumsOfClients.getSelectionModel().getSelectedItem().getCusId();
    }

    private TableView<CategoryGroup> initCenterBorderPane(BorderPane borderPane, BorderPane centralBorderPane) {
        borderPane.setCenter(centralBorderPane);

        ObservableList<CategoryGroup> catGroup = FXCollections.observableArrayList(
                new CategoryGroup(6, 26, "Предприятия по Экономическим секторам", "Оптовая торговля"),
                new CategoryGroup(7, 1, "Подданство", "Резидент"),
                new CategoryGroup(15, 2, "Типы клиентов", "Юридические"),
                new CategoryGroup(55, 6, "Типы поставщиков", "Поставщики"),
                new CategoryGroup(64, 1, "Вид клиента", "Клиент банка")
        );

        TableView<CategoryGroup> tableCatGrp = new TableView<>();
        tableCatGrp.setTableMenuButtonVisible(true);
        tableCatGrp.setTooltip(new Tooltip("Категории и группы счета"));
        tableCatGrp.setPrefWidth(510);
        tableCatGrp.setPrefHeight(160);
        tableCatGrp.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn nameColCat = new TableColumn("Кат.");
        nameColCat.setCellValueFactory(new PropertyValueFactory<CategoryGroup, String>("category"));
        nameColCat.setPrefWidth(50);
        nameColCat.setResizable(false);
        nameColCat.setSortable(true);
        TableColumn nameColGrp = new TableColumn("Группа");
        nameColGrp.setCellValueFactory(new PropertyValueFactory<CategoryGroup, String>("group"));
        TableColumn nameColCatName = new TableColumn("Наименование категории");
        nameColCatName.setCellValueFactory(new PropertyValueFactory<CategoryGroup, String>("categoryName"));
        TableColumn nameColGrpName = new TableColumn("Наименование группы");
        nameColGrpName.setCellValueFactory(new PropertyValueFactory<CategoryGroup, String>("groupName"));
        tableCatGrp.setItems(catGroup);
        tableCatGrp.getColumns().addAll(nameColCat, nameColGrp, nameColCatName, nameColGrpName);
        return tableCatGrp;
    }


    private void setTopFlowPane(FlowPane topFlowPane) {
        createButton = new Button("Создать");
        topFlowPane.getChildren().add(createButton);
        editButton = new Button("Редактировать");
        topFlowPane.getChildren().add(editButton);

        editButton.setOnAction(ae -> {
            if (!isEditMode()) {
                listOfTextField.forEach(item -> item.setDisable(false));
                setEditMode(true);
            } else {
                listOfTextField.forEach(item -> item.setDisable(true));
                setEditMode(false);
            }
        });
        topFlowPane.setHgap(15);
        topFlowPane.setVgap(5);
        topFlowPane.setPadding(new javafx.geometry.Insets(15));
        topFlowPane.setStyle(cssDefault);
    }

    private void initVboxOfButtons(VBox panelOfButtons) {
        panelOfButtons.minWidth(50);
        panelOfButtons.minHeight(20);
        panelOfButtons.setSpacing(10);
        panelOfButtons.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));

        Button clientButton = new Button("Владелец счета");
        panelOfButtons.getChildren().addAll(clientButton);

        ObservableList<Node> listOfButtons = panelOfButtons.getChildren();

        for (Node node : listOfButtons) {
            if (node instanceof Button) {
                ((Button) node).setMaxWidth(Double.MAX_VALUE);
            }
        }
        panelOfButtons.setStyle(cssDefault);
    }

    private void setBottomFlowPane(FlowPane bottomFlowPane) {
        bottomFlowPane.setPadding(new javafx.geometry.Insets(3, 3, 3, 3));
        saveButton = new Button("Сохранить");
        bottomFlowPane.getChildren().add(saveButton);
        saveButton.setOnAction(ae -> saveChangesIfMade());
        bottomFlowPane.getChildren().add(exitButton);
        bottomFlowPane.setAlignment(Pos.BASELINE_RIGHT);
    }


    private void saveChangesIfMade() {
        boolean changed = true;

        ArrayList<String> listOfCurrentValues = new ArrayList<>();
        for (javafx.scene.control.TextField field : listOfTextField) {
            listOfCurrentValues.add(field.getText());
        }

        if (listOfCurrentValues.equals(listOfInitValues))
            changed = false;

        System.out.println("changed = " + changed);

        if (changed) {
            AccModel accModel = new AccModel();
            accModel.updateAccDetails(fullNameTextField.getText(), engNameTextField.getText(), shortNameTextField.getText(),
                    otdTextField.getText(), userRegTextField.getText(), dateZavTextField.getText(),
                    userEditTextField.getText(), dateEditTextField.getText(), currentOstTextField.getText(), selectedId
            );
//            CatalogOfClientsModel catalogOfClientsModel = new CatalogOfClientsModel();
//            catalogOfClientsModel.updateClientDetails(dateEditTextField.getText(), fullNameTextField.getText(),
//                    engNameTextField.getText(), shortNameTextField.getText(), ogrnTextField.getText(),
//                    regOrgTextField.getText(), passportTextField.getText(), dateRegTextField.getText(),
//                    placeRegTextField.getText(), innTextField.getText(), kppTextField.getText(),
//                    numSvidTextField.getText(), nalInspTextField.getText(), emailTextField.getText(),
//                    userRegTextField.getText(), dateZavTextField.getText(), userEditTextField.getText()
//            );
        }
    }

    private void initArrayListOfTextField() {

        listOfTextField.add(fullNameTextField);
        listOfTextField.add(engNameTextField);
        listOfTextField.add(shortNameTextField);
        listOfTextField.add(otdTextField);
        listOfTextField.add(userRegTextField);
        listOfTextField.add(dateZavTextField);
        listOfTextField.add(userEditTextField);
        listOfTextField.add(dateEditTextField);
        listOfTextField.add(currentOstTextField);

        listOfTextField.forEach(item -> {
            item.setDisable(true);
            listOfInitValues.add(item.getText());
        });
    }

    private void makeLabelsAndTextField(GridPane mainContent) {
        javafx.scene.control.Label fullNameLabel = new javafx.scene.control.Label("Полное наименование");

        fullNameLabel.setMinSize(fullNameLabel.getPrefWidth(), fullNameLabel.getPrefHeight());

        fullNameTextField = new javafx.scene.control.TextField();
        fullNameTextField.setPrefSize(550, fullNameTextField.getPrefHeight());
        fullNameTextField.setMinSize(550, fullNameTextField.getPrefHeight());


        mainContent.add(fullNameLabel, 0, 0);
        mainContent.add(fullNameTextField, 1, 0);

        javafx.scene.control.Label engNameLabel = new javafx.scene.control.Label("Англ. наименование");

        engNameLabel.setMinSize(engNameLabel.getPrefWidth(), engNameLabel.getPrefHeight());

        engNameTextField = new javafx.scene.control.TextField();
        engNameTextField.setPrefSize(550, engNameTextField.getPrefHeight());
        engNameTextField.setMinSize(550, engNameTextField.getPrefHeight());

        mainContent.add(engNameLabel, 0, 1);
        mainContent.add(engNameTextField, 1, 1);


        javafx.scene.control.Label shortNameLabel = new javafx.scene.control.Label("Сокращ. наименование");
        shortNameLabel.setMinSize(shortNameLabel.getPrefWidth(), shortNameLabel.getPrefHeight());

        shortNameTextField = new javafx.scene.control.TextField();
        shortNameTextField.setPrefSize(550, shortNameTextField.getPrefHeight());
        shortNameTextField.setMinSize(550, shortNameTextField.getPrefHeight());

        mainContent.add(shortNameLabel, 0, 2);
        mainContent.add(shortNameTextField, 1, 2);

        javafx.scene.control.Label otdLabel = new javafx.scene.control.Label("Отделение");

        otdLabel.setMinSize(otdLabel.getPrefWidth(), otdLabel.getPrefHeight());

        otdTextField = new javafx.scene.control.TextField();
        otdTextField.setPrefSize(550, otdTextField.getPrefHeight());
        otdTextField.setMinSize(550, otdTextField.getPrefHeight());

        mainContent.add(otdLabel, 0, 3);
        mainContent.add(otdTextField, 1, 3);

        javafx.scene.control.Label userRegLabel = new javafx.scene.control.Label("Польз. зарег.");

        userRegTextField = new javafx.scene.control.TextField();
        userRegTextField.setPrefSize(200, userRegTextField.getPrefHeight());
        userRegTextField.setMinSize(200, userRegTextField.getPrefHeight());

        mainContent.add(userRegLabel, 0, 4);
        mainContent.add(userRegTextField, 1, 4);

        javafx.scene.control.Label dateZavLabel = new javafx.scene.control.Label("Дата зав.");

        dateZavTextField = new javafx.scene.control.TextField();
        dateZavTextField.setPrefSize(200, dateZavTextField.getPrefHeight());
        dateZavTextField.setMinSize(200, dateZavTextField.getPrefHeight());

        mainContent.add(dateZavLabel, 0, 5);
        mainContent.add(dateZavTextField, 1, 5);

        javafx.scene.control.Label userEditLabel = new javafx.scene.control.Label("Польз-ль ред.");

        userEditTextField = new javafx.scene.control.TextField();
        userEditTextField.setPrefSize(200, userEditTextField.getPrefHeight());
        userEditTextField.setMinSize(200, userEditTextField.getPrefHeight());

        mainContent.add(userEditLabel, 0, 6);
        mainContent.add(userEditTextField, 1, 6);

        javafx.scene.control.Label dateEditLabel = new javafx.scene.control.Label("Дата ред.");

        dateEditTextField = new javafx.scene.control.TextField();
        dateEditTextField.setPrefSize(200, dateEditTextField.getPrefHeight());
        dateEditTextField.setMinSize(200, dateEditTextField.getPrefHeight());

        mainContent.add(dateEditLabel, 0, 7);
        mainContent.add(dateEditTextField, 1, 7);

        javafx.scene.control.Label currentOst = new javafx.scene.control.Label("Текущий остаток");

        currentOstTextField = new javafx.scene.control.TextField();
        currentOstTextField.setPrefSize(200, dateEditTextField.getPrefHeight());
        currentOstTextField.setMinSize(200, dateEditTextField.getPrefHeight());

        mainContent.add(currentOst, 0, 8);
        mainContent.add(currentOstTextField, 1, 8);

    }

    private void fillFieldsWithAccData(Acc acc) {

        if (acc.getNameOfOwner() != null) {
            fullNameTextField.setText(acc.getNameOfOwner());
        }

        if (acc.getShortNameOfOwner() != null) {
            shortNameTextField.setText(acc.getShortNameOfOwner());
        }

        if (acc.getEngNameOfOwner() != null) {
            engNameTextField.setText(acc.getEngNameOfOwner());
        }

        if (acc.getUserReg() != null) {
            userRegTextField.setText(acc.getUserReg());
        }

        if (acc.getUserRegDate() != null) {
            dateZavTextField.setText(acc.getUserRegDate().toString());
        }

        if (acc.getUserEdit() != null) {
            userEditTextField.setText(acc.getUserEdit());
        }

        if (acc.getUserEditDate() != null) {
            dateEditTextField.setText(acc.getUserEditDate().toString());
        }

        if (acc.getCurrentOst() != null)
            currentOstTextField.setText(acc.getCurrentOst().toString());

        if (acc.getOtd() != null)
            otdTextField.setText(acc.getOtd().toString());

        listOfInitValues = new ArrayList<String>();
        listOfTextField.forEach(item -> {
            item.setDisable(true);
            listOfInitValues.add(item.getText());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccFrame("Счета клиентов"));
    }
}

