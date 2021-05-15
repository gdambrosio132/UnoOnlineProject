package Network;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.*;
import java.io.*;

public class GameMultiServer extends Application {
    public static void main(String[] args) throws IOException {
        //Establish port number for main server
        //we set it as 4444 for now
        int portNumber = 4444;
        boolean listening = true;
        //launch(args);
        try (ServerSocket serverSocket = new ServerSocket(portNumber)){
            while (listening) {
                new GameMultiServerThread(serverSocket.accept()).start();
                launch(args);
            }
        } catch (IOException e) {
            System.err.println("Couldn't listen on port " + portNumber);
            System.exit(-1);
        }
    }


    @Override
    public void start(Stage mainStage) throws IOException{


        //Possible set up a wait condition
        mainStage.setTitle("Server");
        //FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getResource("/Frontend/UnoGameThread3.fxml"));
        mainStage.setScene(new Scene(root, 1280, 720));
        mainStage.show();




        //Establish port number for main server
        //we set it as 4444 for now
        /*int portNumber = 4444;
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)){
            //while (listening) {
                new GameMultiServerThread(serverSocket.accept()).start();
            //}
        } catch (IOException e) {
            System.err.println("Couldn't listen on port " + portNumber);
            System.exit(-1);
        }*/
    }

}
