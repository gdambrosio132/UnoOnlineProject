package Backend.Controllers;

import Authentication.Backend.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

/*
 * TODO: Make this into a game thread. What does that mean, it means that
 *       we need to grab the host users ip and set him up as the main server
 *       thread host and then have other players connect to this.
 *
 * TODO: This is what might happen, we either create two instances of server-client
 *       connection which involves writing some code for sockets in here and then
 *       writing it again in the main base game. Setting up a server thread is top
 *       priority for this point
 */

public class GameLobbyThreadController implements Initializable {

    @FXML
    private ImageView sendMessageImageView;
    @FXML
    private ImageView startGameButton;
    @FXML
    private TextField messageLobbyTextField;
    @FXML
    private Label waitingTextPrompt;
    @FXML
    private ImageView brexitButton;

    public void initialize(URL url, ResourceBundle resourceBundle){
        File sendMessageButtonFile = new File("images/send-message-icon.png");
        Image sendMessageButtonImage = new Image(sendMessageButtonFile.toURI().toString());
        sendMessageImageView.setImage(sendMessageButtonImage);

        File startGameButtonFile = new File("images/start-game-button.png");
        Image startGameButtonImage = new Image(startGameButtonFile.toURI().toString());
        startGameButton.setImage(startGameButtonImage);

        File brexitButtonFile = new File("images/brexit-button-2.png");
        Image brexitButtonImage = new Image(brexitButtonFile.toURI().toString());
        brexitButton.setImage(brexitButtonImage);
    }

    public void startGameOnAction(){
        //TODO: if there at least 2 players in the lobby (1 host and 1 player), then we cannot start the game
        //      else, we start the game by sending all players to the game arena
    }

    public void sendMessageOnAction(){
        //TODO: if there is nothing in the message box on one client end, then that client can't send a message
        //      else, send the message
    }

    public void brexitButtonOnAction(){
        //TODO: when the button is pressed, we navigate back to the Backend.Controllers.GameConnectionController.java screen and also we
        //      update the current Lobby state
        //      Also, check to see if the owner leaves as well, this is important because if the host leaves, then the
        //      game is over.
        //      UPDATE: brexit button complete for database and UI side, just set up Networking

        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connectionDB = databaseConnection.getConnection();

            Statement statement = connectionDB.createStatement();
            String exitQuery = "SELECT account_id FROM user_account WHERE user_ip = '" + getIPAddress() + "'";
            ResultSet resultSet = statement.executeQuery(exitQuery);
            if (resultSet.next()){
                int id = resultSet.getInt("account_id");
                String exitUpdate = "DELETE FROM gamecodeid WHERE account_id = '" + id + "'";
                statement.executeUpdate(exitUpdate);
            }

            Parent root = FXMLLoader.load(getClass().getResource("Frontend/KeyConnect.fxml"));
            Stage gameLobbyStage = new Stage();
            gameLobbyStage.setScene(new Scene(root, 600, 400));
            gameLobbyStage.show();

            Stage stage = (Stage) brexitButton.getScene().getWindow();
            stage.close();
            connectionDB.close();
        } catch (Exception e) {
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
