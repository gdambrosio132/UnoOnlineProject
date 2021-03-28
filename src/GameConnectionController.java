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
        /*
        if (){

        } else {

        }*/

        invalidCodeText.setText("Invalid Game Code");
    }
}
