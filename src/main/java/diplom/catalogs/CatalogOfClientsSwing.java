package diplom.catalogs;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by a.talismanov on 07.06.2016.
 */
public class CatalogOfClientsSwing extends JFrame {
    private static ObservableList<BigInteger> numsOfClients;
    private static TextField fullNameTextField;
    private static TextField engNameTextField;
    private static TextField ogrnTextField;
    private static TextField dateRegTextField;
    private static TextField userEditTextField;
    private static TextField dateEditTextField;
    private static TextField userRegTextField;
    private static TextField dateZavTextField;
    private static TextField passportTextField;
    private static TextField placeRegTextField;
    private static TextField innTextField;
    private static TextField shortNameTextField;
    private static TextField regOrgTextField;
    private static TextField kppTextField;
    private static TextField numSvidTextField;
    private static TextField nalInspTextField;
    private static TextField emailTextField;
    private static ArrayList<TextField> listOfTextField = new ArrayList<>();
    private final Button exitButton;
    private Button adressesButton;
    private static BigInteger selectedId;
    private BigInteger adrressId;
    private Button editButton;
    private Button saveButton;
    //    private Map<String, TextField> map;
    private Button createButton;
    private static ArrayList<String> listOfInitValues;

    static BigInteger getSelectedId() {
        return selectedId;
    }

    private boolean isEditMode() {
        return editMode;
    }

    private void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    private boolean editMode = false;

    static ObservableList<BigInteger> getNumOfClients() {
        return numsOfClients;
    }

    static void setNumsOfClients(ObservableList<BigInteger> list) {
        numsOfClients = list;
    }
    private static final int APPLICATION_HEIGHT = 840;
    private static final int APPLICATION_WIDTH = 1040;
    private int topPointY;
    private int leftPointX;

//    static JFXPanel dummyJFXpanel;
    public CatalogOfClientsSwing(String str){
        super(str);
        JFXPanel fxPanel = new JFXPanel();
//        dummyJFXpanel = new JFXPanel();
        add(fxPanel);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        topPointY = (screen.height - APPLICATION_HEIGHT) / 2;
        leftPointX = (screen.width - APPLICATION_WIDTH) / 2;
        setBounds(leftPointX,topPointY,APPLICATION_WIDTH,APPLICATION_HEIGHT);

        exitButton = new Button("Выход");
//        exitButton.setOnAction(ae -> this.dispose());
        exitButton.setOnAction(ae -> this.setVisible(false));
        Platform.runLater(() -> initFX(fxPanel));
        setVisible(true);
    }

    private void initFX(JFXPanel fxPanel) {

        Group root = new Group();
        Scene scene = new Scene(root, 1024, 800, javafx.scene.paint.Color.WHITE);
        fxPanel.setScene(scene);
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1024, 800);
        FlowPane topFlowPane = new FlowPane(10, 10);
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

        topFlowPane.getChildren().add(new Button("3"));
        topFlowPane.getChildren().add(new Button("4"));
        final String cssDefault = "-fx-border-color: grey;\n"
                + "-fx-border-width: 1;\n";

        topFlowPane.setHgap(15);
        topFlowPane.setVgap(5);
        topFlowPane.setPadding(new Insets(15));


        topFlowPane.setStyle(cssDefault);
        borderPane.setTop(topFlowPane);

        BorderPane centralBorderPane = new BorderPane();
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
        tableCatGrp.setTooltip(new Tooltip("Категории и группы клиента"));
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
        centralBorderPane.setBottom(tableCatGrp);


        CatalogOfClientsModel catalogOfClientsModel = new CatalogOfClientsModel();
        catalogOfClientsModel.getCustomersFromDB();

        TableView<Customer> tableOfNumsOfClients = new TableView<>();
        tableOfNumsOfClients.setTableMenuButtonVisible(true);
        tableOfNumsOfClients.setTooltip(new Tooltip("Номер клиента"));
        tableOfNumsOfClients.setPrefWidth(150);
        tableOfNumsOfClients.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn clientIdColumn = new TableColumn("Id клиента.");
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusIdProp"));

        clientIdColumn.setPrefWidth(150);
        clientIdColumn.setResizable(false);
        clientIdColumn.setSortable(true);

        ArrayList<Customer> customerArrayList = new ArrayList<>();
        if (numsOfClients == null){
            System.out.println("Sos numOfClients NULL");
        }
        for (BigInteger itm : numsOfClients) {
            Customer tempCust = new Customer(itm);
            customerArrayList.add(tempCust);
        }

        ObservableList<Customer> custObsList = FXCollections.observableArrayList(customerArrayList);

        tableOfNumsOfClients.setItems(custObsList);

        tableOfNumsOfClients.getColumns().add(clientIdColumn);
        tableOfNumsOfClients.getSelectionModel().select(0);

        //Add change listener
        tableOfNumsOfClients.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (tableOfNumsOfClients.getSelectionModel().getSelectedItem() != null) {
                selectedId = BigInteger.valueOf(newValue.getCusIdProp());
                Customer currentCustomer = catalogOfClientsModel.getCustomerDetailsFromDB(selectedId);
                fillFieldWithCustomerData(currentCustomer);
                setEditMode(false);
            }
        });
        selectedId = tableOfNumsOfClients.getSelectionModel().getSelectedItem().getCusId();


        centralBorderPane.setLeft(tableOfNumsOfClients);


        GridPane mainContent = new GridPane();
        mainContent.setHgap(5);
        mainContent.setVgap(5);
        mainContent.setPadding(new Insets(15));

        //заполняем метками и полями
        makeLabelsAndTextField(mainContent);


        Customer currentCustomer = catalogOfClientsModel.getCustomerDetailsFromDB(selectedId);
        fillFieldWithCustomerData(currentCustomer);
        initArrayListOfTextField();

        centralBorderPane.setCenter(mainContent);

        VBox panelOfButtons = new VBox();
        panelOfButtons.minWidth(50);
        panelOfButtons.minHeight(20);
        panelOfButtons.setSpacing(10);
        panelOfButtons.setPadding(new Insets(5, 5, 5, 5));

        Button contactInfoButton = new Button("Контактная информация");
        Button rukovoditeliButton = new Button("Руководители");
        adressesButton = new Button("Адреса");

        Button codesOfGosComStatButton = new Button("Коды Госкомстата");
        Button fondsButton = new Button("Фонды");
        Button accountsButton = new Button("Счета");
        Button accConterAgentButton = new Button("Счета контрагента");
        Button accInOtherBanksButton = new Button("Счета в других банках");
        Button documentButton = new Button("Документ");
        Button bankAttributesButton = new Button("Атрибуты банка");
        panelOfButtons.getChildren().addAll(contactInfoButton, rukovoditeliButton, adressesButton,
                codesOfGosComStatButton, fondsButton, accountsButton, accConterAgentButton, accInOtherBanksButton,
                documentButton, bankAttributesButton);

        ObservableList<Node> listOfButtons = panelOfButtons.getChildren();

        for (Node node : listOfButtons) {
            if (node instanceof Button) {
                ((Button) node).setMaxWidth(Double.MAX_VALUE);
            }
        }


        panelOfButtons.setStyle(cssDefault);

        centralBorderPane.setRight(panelOfButtons);


        FlowPane bottomFlowPane = new FlowPane(10, 10);
        bottomFlowPane.setPadding(new Insets(3, 3, 3, 3));
        saveButton = new Button("Сохранить");
        bottomFlowPane.getChildren().add(saveButton);
        saveButton.setOnAction(ae -> saveChangesIfMade());
        bottomFlowPane.getChildren().add(exitButton);
        bottomFlowPane.setAlignment(Pos.BASELINE_RIGHT);
        borderPane.setBottom(bottomFlowPane);
        adressesButton.setOnAction(ae -> {
            try {
                Adress.setCurrentAdrId(adrressId);
                new Adress().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        root.getChildren().add(borderPane);
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
            CatalogOfClientsModel catalogOfClientsModel = new CatalogOfClientsModel();
            catalogOfClientsModel.updateClientDetails(dateEditTextField.getText(), fullNameTextField.getText(),
                    engNameTextField.getText(), shortNameTextField.getText(), ogrnTextField.getText(),
                    regOrgTextField.getText(), passportTextField.getText(), dateRegTextField.getText(),
                    placeRegTextField.getText(), innTextField.getText(), kppTextField.getText(),
                    numSvidTextField.getText(), nalInspTextField.getText(), emailTextField.getText(),
                    userRegTextField.getText(), dateZavTextField.getText(), userEditTextField.getText()
            );
        }
    }

    private void initArrayListOfTextField() {

        listOfTextField.add(fullNameTextField);
        listOfTextField.add(engNameTextField);
        listOfTextField.add(shortNameTextField);
        listOfTextField.add(ogrnTextField);
        listOfTextField.add(regOrgTextField);
        listOfTextField.add(passportTextField);
        listOfTextField.add(dateRegTextField);
        listOfTextField.add(placeRegTextField);
        listOfTextField.add(innTextField);
        listOfTextField.add(kppTextField);
        listOfTextField.add(numSvidTextField);
        listOfTextField.add(nalInspTextField);
        listOfTextField.add(emailTextField);
        listOfTextField.add(userRegTextField);
        listOfTextField.add(dateZavTextField);
        listOfTextField.add(userEditTextField);
        listOfTextField.add(dateEditTextField);

        listOfTextField.forEach(item -> {
            item.setDisable(true);
            listOfInitValues.add(item.getText());
        });
    }

    /*
    * all main Labels and TextField as set here
    * @mainContent Grid on which it sets
    * */
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

        javafx.scene.control.Label ogrnLabel = new javafx.scene.control.Label("ОГРН");

        ogrnLabel.setMinSize(ogrnLabel.getPrefWidth(), ogrnLabel.getPrefHeight());

        ogrnTextField = new javafx.scene.control.TextField();
        ogrnTextField.setPrefSize(550, ogrnTextField.getPrefHeight());
        ogrnTextField.setMinSize(550, ogrnTextField.getPrefHeight());

        mainContent.add(ogrnLabel, 0, 3);
        mainContent.add(ogrnTextField, 1, 3);

        javafx.scene.control.Label regOrgLabel = new javafx.scene.control.Label("Рег. организация");

        regOrgLabel.setMinSize(regOrgLabel.getPrefWidth(), ogrnLabel.getPrefHeight());

        regOrgTextField = new javafx.scene.control.TextField();
        regOrgTextField.setPrefSize(550, regOrgTextField.getPrefHeight());
        regOrgTextField.setMinSize(550, regOrgTextField.getPrefHeight());

        mainContent.add(regOrgLabel, 0, 4);
        mainContent.add(regOrgTextField, 1, 4);


        javafx.scene.control.Label passportLabel = new javafx.scene.control.Label("Паспорт");


        passportTextField = new javafx.scene.control.TextField();
        passportTextField.setPrefSize(200, passportTextField.getPrefHeight());
        passportTextField.setMinSize(200, passportTextField.getPrefHeight());


        mainContent.add(passportLabel, 0, 5);
        mainContent.add(passportTextField, 1, 5);


        javafx.scene.control.Label dateRegLabel = new javafx.scene.control.Label("Дата рег.");

        dateRegTextField = new javafx.scene.control.TextField();
        dateRegTextField.setPrefSize(200, dateRegTextField.getPrefHeight());
        dateRegTextField.setMinSize(200, dateRegTextField.getPrefHeight());


        mainContent.add(dateRegLabel, 0, 6);
        mainContent.add(dateRegTextField, 1, 6);


        javafx.scene.control.Label placeRegLabel = new javafx.scene.control.Label("Место рег.");
        placeRegTextField = new javafx.scene.control.TextField();
        placeRegTextField.setPrefSize(200, placeRegTextField.getPrefHeight());
        placeRegTextField.setMinSize(200, placeRegTextField.getPrefHeight());

        mainContent.add(placeRegLabel, 0, 7);
        mainContent.add(placeRegTextField, 1, 7);


        javafx.scene.control.Label innLabel = new javafx.scene.control.Label("ИНН");
        innTextField = new javafx.scene.control.TextField();
        innTextField.setPrefSize(200, innTextField.getPrefHeight());
        innTextField.setMinSize(200, innTextField.getPrefHeight());

        mainContent.add(innLabel, 0, 8);
        mainContent.add(innTextField, 1, 8);

        javafx.scene.control.Label kppLabel = new javafx.scene.control.Label("КПП");

        kppTextField = new javafx.scene.control.TextField();
        kppTextField.setPrefSize(200, kppTextField.getPrefHeight());
        kppTextField.setMinSize(200, kppTextField.getPrefHeight());

        mainContent.add(kppLabel, 0, 9);
        mainContent.add(kppTextField, 1, 9);

        javafx.scene.control.Label numSvidLabel = new javafx.scene.control.Label("Номер свид-ва");

        numSvidTextField = new javafx.scene.control.TextField();
        numSvidTextField.setPrefSize(200, numSvidTextField.getPrefHeight());
        numSvidTextField.setMinSize(200, numSvidTextField.getPrefHeight());

        mainContent.add(numSvidLabel, 0, 10);
        mainContent.add(numSvidTextField, 1, 10);

        javafx.scene.control.Label nalInspLabel = new javafx.scene.control.Label("Налог. исп.");

        nalInspTextField = new javafx.scene.control.TextField();
        nalInspTextField.setPrefSize(200, nalInspTextField.getPrefHeight());
        nalInspTextField.setMinSize(200, nalInspTextField.getPrefHeight());

        mainContent.add(nalInspLabel, 0, 11);
        mainContent.add(nalInspTextField, 1, 11);


        javafx.scene.control.Label emailLabel = new javafx.scene.control.Label("E-mail");

        emailTextField = new javafx.scene.control.TextField();
        emailTextField.setPrefSize(200, emailTextField.getPrefHeight());
        emailTextField.setMinSize(200, emailTextField.getPrefHeight());

        mainContent.add(emailLabel, 0, 12);
        mainContent.add(emailTextField, 1, 12);


        javafx.scene.control.Label userRegLabel = new javafx.scene.control.Label("Польз. зарег.");

        userRegTextField = new javafx.scene.control.TextField();
        userRegTextField.setPrefSize(200, userRegTextField.getPrefHeight());
        userRegTextField.setMinSize(200, userRegTextField.getPrefHeight());

        mainContent.add(userRegLabel, 0, 13);
        mainContent.add(userRegTextField, 1, 13);

        javafx.scene.control.Label dateZavLabel = new javafx.scene.control.Label("Дата зав.");

        dateZavTextField = new javafx.scene.control.TextField();
        dateZavTextField.setPrefSize(200, dateZavTextField.getPrefHeight());
        dateZavTextField.setMinSize(200, dateZavTextField.getPrefHeight());

        mainContent.add(dateZavLabel, 0, 14);
        mainContent.add(dateZavTextField, 1, 14);

        javafx.scene.control.Label userEditLabel = new javafx.scene.control.Label("Польз-ль ред.");

        userEditTextField = new javafx.scene.control.TextField();
        userEditTextField.setPrefSize(200, userEditTextField.getPrefHeight());
        userEditTextField.setMinSize(200, userEditTextField.getPrefHeight());

        mainContent.add(userEditLabel, 0, 15);
        mainContent.add(userEditTextField, 1, 15);

        javafx.scene.control.Label dateEditLabel = new javafx.scene.control.Label("Дата ред.");

        dateEditTextField = new javafx.scene.control.TextField();
        dateEditTextField.setPrefSize(200, dateEditTextField.getPrefHeight());
        dateEditTextField.setMinSize(200, dateEditTextField.getPrefHeight());

        mainContent.add(dateEditLabel, 0, 16);
        mainContent.add(dateEditTextField, 1, 16);
    }

    private void fillFieldWithCustomerData(Customer currentCustomer) {
        if (currentCustomer.getUserEditDate() != null)
            dateEditTextField.setText(currentCustomer.getUserEditDate().toString());
        else
            dateEditTextField.setText("");

        fullNameTextField.setText(currentCustomer.getCusName());
        engNameTextField.setText(currentCustomer.getCusEngName());
        shortNameTextField.setText(currentCustomer.getCusShortName());
        passportTextField.setText(currentCustomer.getPassport());
        ogrnTextField.setText(currentCustomer.getCusOgrn());
        regOrgTextField.setText(currentCustomer.getRegOrg());

        if (currentCustomer.getDateReg() != null)
            dateRegTextField.setText(currentCustomer.getDateReg().toString());
        else
            dateRegTextField.setText("");

        placeRegTextField.setText(currentCustomer.getPlaceReg());
        innTextField.setText(currentCustomer.getCusInn());
        kppTextField.setText(currentCustomer.getCusKpp());
        numSvidTextField.setText(currentCustomer.getNumSvid());
        nalInspTextField.setText(currentCustomer.getNalInspec());
        emailTextField.setText(currentCustomer.getEmail());
        userRegTextField.setText(currentCustomer.getUserReg());
        userEditTextField.setText(currentCustomer.getUserEdit());
        adrressId = currentCustomer.getAdressId();

        if (currentCustomer.getUserRegDate() != null) {
            dateZavTextField.setText(currentCustomer.getUserRegDate().toString());
        } else
            dateZavTextField.setText("");

        listOfInitValues = new ArrayList<String>();
        listOfTextField.forEach(item -> {
            item.setDisable(true);
            listOfInitValues.add(item.getText());
        });

    }

    public static void main(String[] args){
        new CatalogOfClientsSwing("test");
    }

}
