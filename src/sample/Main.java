package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    private static final String HAND_DIRECTORY  = "C:\\AmericasCardroom\\profiles\\Mithrandir89\\HandHistory\\Mithrandir89";

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Sets up the display
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Poker Helper");
        Scene mainScene = new Scene(root, 300, 275);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        //Gets the hand history file and passes it to the parser
        File file = new File(HAND_DIRECTORY + "\\HH20170703 T7513467-G43640769.txt");
        HandParser.parseHandHistory(file);

        findPokerWindows();
    }


    public void findPokerWindows(){


    }
    public static void main(String[] args) {
        launch(args);
    }
}
