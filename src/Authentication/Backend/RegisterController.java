package Authentication.Backend;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private ImageView registerImageView;
    @FXML
    private Button closeButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File imageFile = new File("images/edit-user-male.png");
        Image imageImage = new Image(imageFile.toURI().toString());
        registerImageView.setImage(imageImage);
    }

    public void registerButtonOnAction(ActionEvent event){
        if (setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();
            confirmPasswordLabel.setText("");
        } else {
            confirmPasswordLabel.setText("Password does not match");
        }

    }

    public void closeButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void registerUser(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectionDB = databaseConnection.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();

        String insertFields = "INSERT INTO user_account(lastname, firstname, username, password) VALUES ('";
        String insertValues = lastname + "','" + firstname + "','" +  username + "','" +  password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("User has been registered successfully!");
            //TODO: enter the wait condition here and go back to the login screen
            connectionDB.close();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
}
