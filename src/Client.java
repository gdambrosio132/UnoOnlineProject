/*import Objects.CardDeck;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/*
 * This is the client side of the application
 * TODO: This is for not just the client, but also for the server and the server thread
 *       get rid of the public static void main so it runs as we launch the application in another window
 */

/*public class Client {

    private CardDeck playerDeck;
    private Socket client;
    private int userID;
    private String name;


    /*public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(System.in);
        String clientName = "";
        boolean firstIteration = true;

        //we require the hostName and the portNumber for this simulation
        String hostName = "localhost";
        int portNumber = 4444;

        Message inMessage = null;
        ObjectInputStream in = null;
        Socket IMSocket = null;
        ObjectOutputStream outObject = null;

        //We connect to the server from this base client
        try {
            IMSocket = new Socket(hostName, portNumber);
            outObject = new ObjectOutputStream(IMSocket.getOutputStream());
            in = new ObjectInputStream(IMSocket.getInputStream());
            inMessage = (Message) in.readObject();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the: localhost");
            System.exit(1);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Problem reading object: class not found");
            System.exit(1);
        }
        //read in information
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromServerName;
        String fromUser;


        //Make sure to run until we don't get a valid object back to us
        while ((fromServer = inMessage.getResponse()) != null || (fromServerName = inMessage.getName()) != null)  {
            //Print content of object that was sent to us
            System.out.println(inMessage.toString());

            //First Iteration for user to prompt in their name
            if (firstIteration) {
                System.out.print("Enter in your name: ");
                clientName = scan.nextLine();
                firstIteration = false;
            }


            //When server prompts Bye message, we break out
            if (fromServer.equals("Bye"))
                break;


            //User-Side view of Messaging Screen
            System.out.print(clientName + ": ");
            fromUser = stdIn.readLine();

            //Object to sent out to the Server -- takes in clients name and message
            Message outMessage = new Message(clientName, fromUser);

            //Before we send it out, make sure it isn't empty
            if (fromUser != null) {
                outObject.writeObject(outMessage);
            }

            //Second Case to see if
            try {
                inMessage = (Message) in.readObject();
            } catch (ClassNotFoundException cnfe) {
                System.err.println("IMClient: Problem reading object: class not found");
                System.exit(1);
            }
        }

        //out.close();
        outObject.close();
        in.close();
        stdIn.close();
        IMSocket.close();

    }*/
/*
    public Client(Socket client, String name, int userID){
        this.client = client;
        this.name = name;
        this.userID = userID;
        this.playerDeck = new CardDeck();
    }

    public String getName(){
        return this.name;
    }

    public int getUserID(){
        return this.userID;
    }

    public void retrieveCard(Object card){
        this.playerDeck.addCard(card);
    }

    public CardDeck getCard(){
        return this.playerDeck;
    }




}*/

