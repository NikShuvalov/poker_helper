package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    TableView<Player> mTable;

    public static void main(String[] args) {
        launch(args);
    }

    private static final String HAND_DIRECTORY  = "C:\\AmericasCardroom\\profiles\\Mithrandir89\\HandHistory\\Mithrandir89";

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Sets up the display
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //Gets the hand history file and passes it to the parser
        File file = new File(HAND_DIRECTORY + "\\HH20170703 T7513467-G43640769.txt");
        HandParser.parseHandHistory(file);

        StackPane layout = new StackPane();


        //Name Column
        TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

        //Hand Count column
        TableColumn<Player, Integer> handsColumn = new TableColumn<>("Hands");
        handsColumn.setMinWidth(100);
        handsColumn.setCellValueFactory(new PropertyValueFactory<>("totalHands"));

        //Vpip Column
        TableColumn<Player, Double> vpipColumn = new TableColumn<>("VPIP");
        vpipColumn.setMinWidth(100);
        vpipColumn.setCellValueFactory(new PropertyValueFactory<>("vpip"));

        //pfr column
        TableColumn<Player, Double> pfrColumn = new TableColumn<>("PFR");
        pfrColumn.setMinWidth(100);
        pfrColumn.setCellValueFactory(new PropertyValueFactory<>("preFlopRaise"));

        mTable = new TableView<>();
        mTable.setItems(TempDataStorage.getInstance().getPlayerList());
        mTable.getColumns().addAll(nameColumn, handsColumn, vpipColumn, pfrColumn);

        layout.getChildren().add(mTable);
        primaryStage.setTitle("Poker Helper");
        Scene mainScene = new Scene(layout, 1000,1000);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
