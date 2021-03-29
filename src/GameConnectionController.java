import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Authentication.Backend.DatabaseConnection;


public class GameConnectionController {

    @FXML
    private TextField keyCodeTextField;
    @FXML
    private Label invalidCodeText;


    public void joinGameButtonOnAction(ActionEvent event){
        //see if there is a generated code with those numbers and allow them in the game, else prompt the message

        if (keyCodeTextField.getText() == null){
            invalidCodeText.setText("Please enter in a Game Code to Join");
        } else {
            validateGameCode();
        }

        invalidCodeText.setText("Invalid Game Code");
    }

    public void validateGameCode(){
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connectionDB = databaseConnection.getConnection();
            Statement statement = connectionDB.createStatement();
            String queryGameCode = "SELECT GameCodeID FROM gamecodeid";

            ResultSet resultSet = statement.executeQuery(queryGameCode);
            //list is for getting all the values from database and storing it in here temporarily
            List<Integer> gameCodeList = new ArrayList<>();
            while (resultSet.next()){
                gameCodeList.add(resultSet.getInt("GameCodeID"));
            }

            //if it is in there, we send our player to the game lobby thread
            if (gameCodeList.contains(Integer.parseInt(keyCodeTextField.getText()))){
                //TODO: Send player to the game lobby server thread
            } else {
                invalidCodeText.setText("Invalid Game Code");
            }

            connectionDB.close();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createGameButtonOnAction(ActionEvent event){
        try {
            //TODO: make sure you get the exact thread first before going forward to entering a game lobby
            //      Also, create a unique code and add it to the database
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connectionDB = databaseConnection.getConnection();
            Statement statement = connectionDB.createStatement();
            String queryGameCode = "SELECT GameCodeID FROM gamecodeid";

            ResultSet resultSet = statement.executeQuery(queryGameCode);
            //list is for getting all the values from database and storing it in here temporarily
            List<Integer> gameCodeList = new ArrayList<>();
            while (resultSet.next()){
                gameCodeList.add(resultSet.getInt("GameCodeID"));
            }

            int gameCode = 0;
            //maybe create a generate game id method
            Random random = new Random();
            if (gameCodeList.size() == 0){
                gameCode = random.nextInt();
            } else {
                //gameCode = generateGameID(gameCodeList);
                while (gameCodeList.contains(gameCode)){
                    gameCode = random.nextInt();
                }

            }


            //TODO: at this point, we then insert the unique game code into the database
            //      ALSO: Test to see if this is working




            Parent root = FXMLLoader.load(getClass().getResource("GameLobbyThread.fxml"));
            Stage gameLobbyStage = new Stage();
            gameLobbyStage.setScene(new Scene(root, 520, 508));
            gameLobbyStage.show();

            connectionDB.close();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

}
