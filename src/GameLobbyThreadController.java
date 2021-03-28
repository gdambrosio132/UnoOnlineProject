import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
        //TODO: when the button is pressed, we navigate back to the GameConnectionController.java screen and also we
        //      update the current Lobby state
        //      Also, check to see if the owner leaves as well
    }
}
