package diplom.catalogs;

/**
 * Created by a.talismanov on 06.06.2016.
 */
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class TableDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Label lblTool = new Label();


        HBox hBox = new HBox();
        hBox.setSpacing(5.0);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.getChildren().add(lblTool);


        final TableView<TableProps> table = new TableView<>();

        //Table columns
        TableColumn<TableProps, String> clmTool = new TableColumn<>("Tool");
        clmTool.setMinWidth(160);
        clmTool.setCellValueFactory(new PropertyValueFactory<>("tool"));

        TableColumn<TableProps, String> clmChart = new TableColumn<>("Chart");
        clmChart.setMinWidth(160);
        clmChart.setCellValueFactory(new PropertyValueFactory<>("chart"));

        //Add data
        final ObservableList<TableProps> data = FXCollections.observableArrayList(
                new TableProps("Volume", ""),
                new TableProps("Shannon entropy", "")
        );
        table.setItems(data);
        table.getColumns().addAll(clmTool, clmChart);

        //Add change listener
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (table.getSelectionModel().getSelectedItem() != null) {
                lblTool.setText(newValue.getTool());
            }
        });

        BorderPane pane = new BorderPane();
        pane.setTop(hBox);
        pane.setCenter(table);
        stage.setScene(new Scene(pane, 640, 480));
        stage.show();

    }

    public class TableProps {
        private final SimpleStringProperty tool;
        private final SimpleStringProperty chart;

        public TableProps(String tool, String chart) {
            this.tool = new SimpleStringProperty(tool);
            this.chart = new SimpleStringProperty(chart);
        }

        public String getTool() {
            return tool.get();
        }

        public SimpleStringProperty toolProperty() {
            return tool;
        }

        public void setTool(String tool) {
            this.tool.set(tool);
        }

        public String getChart() {
            return chart.get();
        }

        public SimpleStringProperty chartProperty() {
            return chart;
        }

        public void setChart(String chart) {
            this.chart.set(chart);
        }
    }

}
