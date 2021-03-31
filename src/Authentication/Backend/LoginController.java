package Authentication.Backend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("images/unologologin.jpeg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);
    }

    public void loginButtonOnAction(ActionEvent event){
        loginMessageLabel.setText("You try to login");
        if (usernameTextField.getText().isEmpty() == false && passwordTextField.getText().isEmpty() == false){
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }


    public void validateLogin(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectionDB = databaseConnection.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + usernameTextField.getText() + "' AND password = '" + passwordTextField.getText() + "'";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            //TODO: needs updating, it only checks to see if the id is equal to 1
            //      update it so that we know it is in our database, I THINK
            while(queryResult.next()){
                if (queryResult.getInt(1) == 1){
                    loginMessageLabel.setText("Congrats");
                    //TODO: go to the game code screen
                    //      connect to other table to say that the user is online
                } else {
                    loginMessageLabel.setText("invalid login");
                }
            }

            connectionDB.close();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }

    public void createAccountForm(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FrontendUI/register.fxml"));
            Stage registerStage = new Stage();
            //registerStage.initStyle(StageStyle.UNDECORATED); this eliminates the application borders
            registerStage.setScene(new Scene(root, 520, 508));
            registerStage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
