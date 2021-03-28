/*import java.net.*;
import java.io.*;

public class GameMultiServer {
    public static void main(String[] args) throws IOException {
        //Establish port number for main server
        //we set it as 4444 for now
        int portNumber = 4444;
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)){
            while (listening) {
                new GameMultiServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Couldn't listen on port " + portNumber);
            System.exit(-1);
        }
    }
}*/
