import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Authentication.Backend.DatabaseConnection;
import Authentication.Backend.LoginController;

public class GameConnectionController {

    @FXML
    private TextField keyCodeTextField;
    @FXML
    private Label invalidCodeText;
    @FXML
    private Button joinGameButton;
    @FXML
    private Button createGameButton;


    public void joinGameButtonOnAction(ActionEvent event){
        //see if there is a generated code with those numbers and allow them in the game, else prompt the message

        if (keyCodeTextField.getText() == null){
            invalidCodeText.setText("Please enter in a Game Code to Join");
        } else {
            validateGameCode();
            //TODO: establish game connection logic I think lol its late a night
            Stage stage1 = (Stage) joinGameButton.getScene().getWindow();
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
                //      we should also set the joining player's database
                //      gamecodeID to the same ID of the game

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
            //      ALSO: Test to see if this is working - UPDATE: we got it to work as in
            //      transfering to the new screen. Now work on sending information and also
            //      delete information once a user exits so it doesn't stay forever
            //      ALSO: add ip column in database for one end user and use that info to
            //            insert it right into our new class that we forward to

            //TODO: UPDATE - this is useless for now, probably will be benificial when we setup connections over networks
            String getIP = getIPAddress();
            //fetch the ip address here
            String getIPQuery = "SELECT username FROM user_account WHERE user_ip = '" + getIP + "'";

            ResultSet getTheUsername = statement.executeQuery(getIPQuery);
            String username = "";
            while (getTheUsername.next()){
                 username = getTheUsername.getString("username");
            }

            //String username = LoginController.getClientUserName();
            //now we have the username, get the id
            String queryStatementForID = "SELECT account_id FROM user_account WHERE username = '" + username + "'";
            ResultSet currentID = statement.executeQuery(queryStatementForID);
            int id = 0;
            while (currentID.next()){
                id = currentID.getInt("account_id");
            }

            String createGameInstanceQuery = "INSERT INTO gamecodeid(GameCodeID, account_id, host_privileges) VALUES ('";
            String gameIDValues = gameCode + "','" + id + "','" + 1 + "')";
            String initializeGameLobbyQuery = createGameInstanceQuery + gameIDValues;

            statement.executeUpdate(initializeGameLobbyQuery);

            Parent root = FXMLLoader.load(getClass().getResource("GameLobbyThread.fxml"));
            Stage gameLobbyStage = new Stage();
            gameLobbyStage.setScene(new Scene(root, 600, 400));
            gameLobbyStage.show();

            //close current stage once other stage is open
            Stage stage2 = (Stage) createGameButton.getScene().getWindow();
            stage2.close();


            connectionDB.close();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    private String getIPAddress() throws Exception {
        //Don't need the line of code below, TODO: delete it!
        InetAddress getIP = InetAddress.getLocalHost();
        String thisSystemAddress = "";
        try{
            URL thisUrl = new URL("http://bot.whatismyipaddress.com");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(thisUrl.openStream()));
            thisSystemAddress = bufferedReader.readLine().trim();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return thisSystemAddress;
    }

}
