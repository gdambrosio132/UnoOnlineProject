/*import java.net.*;
import java.io.*;

public class GameMultiServerThread extends Thread {
    private Socket socket = null;

    public GameMultiServerThread(Socket socket){
        super("GameMultiServerThread");
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String inputLine, inputLineName, outputLine;

            /*
                SimpleIMProtocol kkp = new SimpleIMProtocol();
                outputLine = kkp.processInput(null);
                Message message = new Message("Server", outputLine);
                out.writeObject(message);
                Message inMessage = (Message) inObject.readObject();

                while((inputLine = inMessage.getResponse()) != null || (inputLineName = inMessage.getName()) != null){
                    System.out.println(inMessage.toString());
                    System.out.print("Server");

                    outputLine = kkp.processInput(inputLine);
                    message = new Message("Server", outputLine);
                    out.writeObject(message);

                    if (outputLine.equals("Bye"))
                        break;

                    try {
                        inMessage = (Message) inObject.readObject();
                    } catch (ClassNotFoundException cnfe) {
                        System.err.println("IMClient: Problem reading object: class not found");
                        System.exit(1);
                    }

                }

                //Close Streams
                System.out.println("Close Connection");
                out.close();
                inObject.close();

             */
        /*} catch (IOException e) {
            e.printStackTrace();
        } //catch (ClassNotFoundException cnfe) {
            //System.err.println("IMClient: Problem reading object: class not found");
            //System.exit(1);
        //}

    }
}*/
