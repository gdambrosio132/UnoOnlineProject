package Authentication.Backend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.InetAddress;
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
    @FXML
    private Button loginButton;

    private boolean signInSuccess = false;


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
            if (signInSuccess){
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
            }
        } else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }


    public void validateLogin(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectionDB = databaseConnection.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + usernameTextField.getText() + "' AND password = '" + passwordTextField.getText() + "'";
        String updateUserStatus = "UPDATE user_account SET visibility = 1 WHERE username = '" + usernameTextField.getText() + "'";

        String ipUpdate = "";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            //TODO: needs updating, it only checks to see if the id is equal to 1
            //      update it so that we know it is in our database, I THINK
            //      UPDATE: nvm it works regardless - now delete this to do
            while(queryResult.next()){
                if (queryResult.getInt(1) == 1){
                    loginMessageLabel.setText("Congrats");
                    //TODO: go to the game code screen
                    //      connect to other table to say that the user is online
                    //      This code has to be here just in case for exception handling
                    String getIP = getIPAddress();
                    ipUpdate = "UPDATE user_account SET user_ip = '" + getIP + "' WHERE username = '" + usernameTextField.getText() + "'";

                    signInSuccess = true;
                    gameCodeScreen();

                } else {
                    loginMessageLabel.setText("invalid login");
                }
            }

            //Insert updates into database
            statement.executeUpdate(ipUpdate);

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

    public void gameCodeScreen(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/KeyConnect.fxml"));
            Stage gameCodeScreen = new Stage();
            gameCodeScreen.setScene(new Scene(root, 600, 400));
            gameCodeScreen.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    /*
     * Gets the IP Address of a unique user
     * Returns a String value
     */
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

    /*
    public static String getClientUserName(){
        String usernameString = usernameTextField.getText();
        return usernameString;
    }*/

}
