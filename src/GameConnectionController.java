import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


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

    }

    public void createGameButtonOnAction(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GameLobbyThread.fxml"));
            Stage gameLobbyStage = new Stage();
            gameLobbyStage.setScene(new Scene(root, 520, 508));
            gameLobbyStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
