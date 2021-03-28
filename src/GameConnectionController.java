import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


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
}
