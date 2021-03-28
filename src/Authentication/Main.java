package Authentication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FrontendUI/login.fxml"));

        //Scene scene = new Scene(root);

        //primaryStage.setScene(scene);
        //primaryStage.setTitle("Login");
        //primaryStage.sizeToScene();
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 520, 400));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
