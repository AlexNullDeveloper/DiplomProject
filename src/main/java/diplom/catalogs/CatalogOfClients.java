package diplom.catalogs;

import com.sun.org.apache.xml.internal.resolver.Catalog;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by a.talismanov on 10.05.2016.
 */
public class CatalogOfClients extends Application {
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
    private Button adressesButton;
    private static BigInteger selectedId;
    private BigInteger adrressId;
    private Button editButton;
    private Button saveButton;
    private Map<String, TextField> map;

    public static BigInteger getSelectedId() {
        return selectedId;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    private boolean editMode = false;

    static ObservableList<BigInteger> getNumOfClients() {
        return numsOfClients;
    }

    static void setNumsOfClients(ObservableList<BigInteger> list) {
        numsOfClients = list;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Каталог клиентов");

        Group root = new Group();
        Scene scene = new Scene(root, 1024, 800, Color.WHITE);

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1024, 800);
//        borderPane.setLayoutY(10);
//        borderPane.setLayoutX(10);
//        borderPane.setCursor(Cursor.TEXT);
//        borderPane.setStyle("-fx-font: bold 14pt Arial; -fx-text-fill:#a0522d;");
//        Label label1 = new Label("label");
//        borderPane.setTop(label1);
        FlowPane topFlowPane = new FlowPane(10, 10);
        topFlowPane.getChildren().add(new Button("1"));
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
                // + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 1;\n"
                // + "-fx-border-style: dashed;\n"
                ;

        //topGridPane.setStyle("-fx-background-color: palegreen; -fx-padding: 2; -fx-hgap: 2; -fx-vgap: 2;");

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

        TableView<CategoryGroup> tableCatGrp = new TableView<CategoryGroup>();
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
        //centralBorderPane.getChildren().add(tableCatGrp);
        centralBorderPane.setBottom(tableCatGrp);


        CatalogOfClientsModel catalogOfClientsModel = new CatalogOfClientsModel();
        catalogOfClientsModel.getCustomersFromDB();

        TableView<Customer> tableOfNumsOfClients = new TableView<Customer>();
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
        for (BigInteger itm : numsOfClients) {
            Customer tempCust = new Customer(itm);
            customerArrayList.add(tempCust);//numsOfClients
        }

        ObservableList<Customer> custObsList = FXCollections.observableArrayList(customerArrayList);

        tableOfNumsOfClients.setItems(custObsList);

        tableOfNumsOfClients.getColumns().add(clientIdColumn);
//        tableOfNumsOfClients.requestFocus();
        tableOfNumsOfClients.getSelectionModel().select(0);

        //Add change listener
        tableOfNumsOfClients.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (tableOfNumsOfClients.getSelectionModel().getSelectedItem() != null) {
                System.out.println(newValue.getCusIdProp());
                selectedId = BigInteger.valueOf(newValue.getCusIdProp());
                Customer currentCustomer = catalogOfClientsModel.getCustomerDetailsFromDB(selectedId);
                fillFieldWithCustomerData(currentCustomer);
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
        initArrayListOfTextField();


        Customer currentCustomer = catalogOfClientsModel.getCustomerDetailsFromDB(selectedId);
        fillFieldWithCustomerData(currentCustomer);


        centralBorderPane.setCenter(mainContent);

        VBox panelOfButtons = new VBox();
        panelOfButtons.minWidth(50);
        panelOfButtons.minHeight(20);
        //panelOfButtons.fillWi(100);
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
        saveButton.setOnAction(ae -> {
            saveChangesIfMade();
        });
        Button exitButton = new Button("Выход");
        exitButton.setOnAction(ae -> primaryStage.hide());
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
        //root.getChildren().add(tableCatGrp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveChangesIfMade() {
        boolean changed = false;

//        for (Iterator iterator = listOfTextField.iterator(); iterator.hasNext(); ) {
//            TextField item = (TextField) iterator.next();
//            String itemString = item.getText();
//            System.out.print(itemString + "\t");
//            TextField tempTextField = map.get(itemString);
//
        for (Map.Entry<String,TextField> entry : map.entrySet()){
                System.out.println("key = " + entry.getKey());
               if (!listOfTextField.contains(entry.getKey())){
                   changed = true;
               }
            System.out.println(changed + "= changed");
//            if (tempTextField != null) {
//                String tempTextFieldString = tempTextField.getText();
//                System.out.println(tempTextFieldString);
//                if (!tempTextFieldString.
//                        equals(item.getText())) {
//                    changed = true;
//                    break;
//                }
//            }
        }
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
        listOfTextField.add(dateEditTextField);
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


        map = new HashMap<String, TextField>();
        listOfTextField.forEach(item -> {
                    item.setDisable(true);
                    map.put(item.getText(), item);
                }
        );
    }

    /*
    * all main Labels and TextField as set here
    * @mainContent Grid on which it sets
    * */
    private void makeLabelsAndTextField(GridPane mainContent) {
        Label fullNameLabel = new Label("Полное наименование");

        fullNameLabel.setMinSize(fullNameLabel.getPrefWidth(), fullNameLabel.getPrefHeight());

        fullNameTextField = new TextField();
        fullNameTextField.setPrefSize(550, fullNameTextField.getPrefHeight());
        fullNameTextField.setMinSize(550, fullNameTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(fullNameLabel, 0, 0);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(fullNameTextField, 1, 0);

        Label engNameLabel = new Label("Англ. наименование");

        engNameLabel.setMinSize(engNameLabel.getPrefWidth(), engNameLabel.getPrefHeight());

        engNameTextField = new TextField();
        engNameTextField.setPrefSize(550, engNameTextField.getPrefHeight());
        engNameTextField.setMinSize(550, engNameTextField.getPrefHeight());

        mainContent.add(engNameLabel, 0, 1);
        mainContent.add(engNameTextField, 1, 1);


        Label shortNameLabel = new Label("Сокращ. наименование");
        shortNameLabel.setMinSize(shortNameLabel.getPrefWidth(), shortNameLabel.getPrefHeight());

        shortNameTextField = new TextField();
        shortNameTextField.setPrefSize(550, shortNameTextField.getPrefHeight());
        shortNameTextField.setMinSize(550, shortNameTextField.getPrefHeight());

        mainContent.add(shortNameLabel, 0, 2);
        mainContent.add(shortNameTextField, 1, 2);

        Label ogrnLabel = new Label("ОГРН");

        ogrnLabel.setMinSize(ogrnLabel.getPrefWidth(), ogrnLabel.getPrefHeight());

        ogrnTextField = new TextField();
        ogrnTextField.setPrefSize(550, ogrnTextField.getPrefHeight());
        ogrnTextField.setMinSize(550, ogrnTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(ogrnLabel, 0, 3);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(ogrnTextField, 1, 3);

        Label regOrgLabel = new Label("Рег. организация");

        regOrgLabel.setMinSize(regOrgLabel.getPrefWidth(), ogrnLabel.getPrefHeight());

        regOrgTextField = new TextField();
        regOrgTextField.setPrefSize(550, regOrgTextField.getPrefHeight());
        regOrgTextField.setMinSize(550, regOrgTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(regOrgLabel, 0, 4);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(regOrgTextField, 1, 4);


        Label passportLabel = new Label("Паспорт");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        passportTextField = new TextField();
        passportTextField.setPrefSize(200, passportTextField.getPrefHeight());
        passportTextField.setMinSize(200, passportTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(passportLabel, 0, 5);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(passportTextField, 1, 5);


        Label dateRegLabel = new Label("Дата рег.");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        dateRegTextField = new TextField();
        dateRegTextField.setPrefSize(200, dateRegTextField.getPrefHeight());
        dateRegTextField.setMinSize(200, dateRegTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(dateRegLabel, 0, 6);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(dateRegTextField, 1, 6);


        Label placeRegLabel = new Label("Место рег.");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        placeRegTextField = new TextField();
        placeRegTextField.setPrefSize(200, placeRegTextField.getPrefHeight());
        placeRegTextField.setMinSize(200, placeRegTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(placeRegLabel, 0, 7);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(placeRegTextField, 1, 7);


        Label innLabel = new Label("ИНН");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        innTextField = new TextField();
        innTextField.setPrefSize(200, innTextField.getPrefHeight());
        innTextField.setMinSize(200, innTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(innLabel, 0, 8);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(innTextField, 1, 8);

        Label kppLabel = new Label("КПП");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        kppTextField = new TextField();
        kppTextField.setPrefSize(200, kppTextField.getPrefHeight());
        kppTextField.setMinSize(200, kppTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(kppLabel, 0, 9);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(kppTextField, 1, 9);

        Label numSvidLabel = new Label("Номер свид-ва");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        numSvidTextField = new TextField();
        numSvidTextField.setPrefSize(200, numSvidTextField.getPrefHeight());
        numSvidTextField.setMinSize(200, numSvidTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(numSvidLabel, 0, 10);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(numSvidTextField, 1, 10);

        Label nalInspLabel = new Label("Налог. исп.");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        nalInspTextField = new TextField();
        nalInspTextField.setPrefSize(200, nalInspTextField.getPrefHeight());
        nalInspTextField.setMinSize(200, nalInspTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(nalInspLabel, 0, 11);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(nalInspTextField, 1, 11);


        Label emailLabel = new Label("E-mail");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        emailTextField = new TextField();
        emailTextField.setPrefSize(200, emailTextField.getPrefHeight());
        emailTextField.setMinSize(200, emailTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(emailLabel, 0, 12);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(emailTextField, 1, 12);


        Label userRegLabel = new Label("Польз. зарег.");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        userRegTextField = new TextField();
        userRegTextField.setPrefSize(200, userRegTextField.getPrefHeight());
        userRegTextField.setMinSize(200, userRegTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(userRegLabel, 0, 13);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(userRegTextField, 1, 13);

        Label dateZavLabel = new Label("Дата зав.");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        dateZavTextField = new TextField();
        dateZavTextField.setPrefSize(200, dateZavTextField.getPrefHeight());
        dateZavTextField.setMinSize(200, dateZavTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(dateZavLabel, 0, 14);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(dateZavTextField, 1, 14);

        Label userEditLabel = new Label("Польз-ль ред.");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        userEditTextField = new TextField();
        userEditTextField.setPrefSize(200, userEditTextField.getPrefHeight());
        userEditTextField.setMinSize(200, userEditTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(userEditLabel, 0, 15);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(userEditTextField, 1, 15);

        Label dateEditLabel = new Label("Дата ред.");

        //passportLabel.setMinSize(passportLabel.getPrefWidth(),passportLabel.getPrefHeight());

        dateEditTextField = new TextField();
        dateEditTextField.setPrefSize(200, dateEditTextField.getPrefHeight());
        dateEditTextField.setMinSize(200, dateEditTextField.getPrefHeight());


        //GridPane.setHalignment(fullNameLabel, HPos.LEFT);
        mainContent.add(dateEditLabel, 0, 16);
        //GridPane.setHalignment(fullNameTextField, HPos.LEFT);
        mainContent.add(dateEditTextField, 1, 16);
    }

    private void fillFieldWithCustomerData(Customer currentCustomer) {
        if (currentCustomer.getUserEditDate() != null)
            dateEditTextField.setText(currentCustomer.getUserEditDate().toString());

        fullNameTextField.setText(currentCustomer.getCusName());
        engNameTextField.setText(currentCustomer.getCusEngName());
        shortNameTextField.setText(currentCustomer.getCusShortName());
        passportTextField.setText(currentCustomer.getPassport());
        ogrnTextField.setText(currentCustomer.getCusOgrn());
        regOrgTextField.setText(currentCustomer.getRegOrg());

        if (currentCustomer.getDateReg() != null)
            dateRegTextField.setText(currentCustomer.getDateReg().toString());

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
        }
    }

    public static void main(String[] args) {
        Application.launch(CatalogOfClients.class, args);
    }

}
