package Network;

import Backend.Game;
import Backend.UnoGameClientController;
import Backend.UnoGameServerController;
import Backend.UnoGameThreadController;
import Objects.Card;
import Objects.CardDeck;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * This is the client side of the application
 * TODO: This is for not just the client, but also for the server and the server thread
 *       get rid of the public static void main so it runs as we launch the application in another window
 */

public class Client extends Application{

    private CardDeck playerDeck;
    private Socket client;
    private int userID;
    private String name;
    private static Game game = new Game();


    public static void main(String[] args) throws IOException{
        new Thread(new ClientThread()).start();
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws IOException{
        mainStage.setTitle("Client");
        Parent root = FXMLLoader.load(getClass().getResource("/Frontend/UnoGameThread2.fxml"));
        mainStage.setScene(new Scene(root, 1280, 720));
        mainStage.show();
    }

}

class ClientThread implements Runnable {
    @Override
    public void run(){
        try {
            String hostName = "localhost";
            int portNumber = 4444;

            //Server card amount logic
            //This is to keep track of how many cards the server has
            int serverAmount = 7;

            ObjectInputStream in = null;
            Socket IMSocket = null;
            ObjectOutputStream outObject = null;
            CardDeck clientCards = null;
            Card discardCard = null;

            //We connect to the server from this base client
            try {
                IMSocket = new Socket(hostName, portNumber);
                outObject = new ObjectOutputStream(IMSocket.getOutputStream());
                in = new ObjectInputStream(IMSocket.getInputStream());
                clientCards = (CardDeck) in.readObject();
                discardCard = (Card) in.readObject();
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
            Scanner stdIn = new Scanner(System.in);
            String myChoiceCard;
            String wildCardChoice = null;
            Card checkDiscardCard;
            Card checkIfSameCard = new Card();
            Card checkIfSameCard2 = new Card();
            boolean initialization = true;
            boolean addingValue = false;
            boolean careful = false;
            boolean exit = false;

            //launch(args);
            ArrayList<String> initCards = new ArrayList<String>();
            for (int i = 0; i < clientCards.getCardCount(); i++) {
                initCards.add(clientCards.getSpecificCardFromDeck(i).getImage());
            }

            UnoGameClientController.updateEnemyCardAmount(serverAmount);
            UnoGameClientController.updatePlayerCardAmount(clientCards.getCardCount());
            UnoGameClientController.initPlayerCards(initCards);
            UnoGameClientController.initDiscardCard(discardCard.getImage());


            while ((checkDiscardCard = discardCard) != null) {

                if ((!checkIfSameCard.toString().equals(checkDiscardCard.toString())) && (!checkDiscardCard.toString().equals(checkIfSameCard2.toString())) && !initialization && !addingValue && !careful) {
                    serverAmount--;
                }

                //TODO: make sure to skip this process if we get back our own card when we choose the skip/reverse feature
                //      THIS IS FOR WHEN A SERVER SENDS IN THEIR SPECIALTY CARDS! WE HAVE TO HANDLE IT HERE
                if (checkDiscardCard.getCardNumber() == -1 && !checkDiscardCard.toString().equals(checkIfSameCard2.toString()) && !addingValue) {
                    switch (checkDiscardCard.getAbility()) {
                        case "Skip":
                        case "Reverse":
                            outObject.writeObject(checkDiscardCard);
                            UnoGameClientController.updateEnemyCardAmount(serverAmount);
                            UnoGameClientController.initDiscardCard(checkDiscardCard.getImage());
                            try {
                                checkIfSameCard = checkDiscardCard;
                                discardCard = (Card) in.readObject();
                                checkDiscardCard = discardCard; //TODO: add conditions here if server adds more cards afterwards
                                if (checkDiscardCard.getCardNumber() >= 10) {
                                    int temp = checkDiscardCard.getCardNumber() - 10;
                                    serverAmount += temp;
                                    outObject.writeObject(checkIfSameCard);
                                    checkDiscardCard = (Card) in.readObject();
                                }
                            } catch (ClassNotFoundException cnfe) {
                                System.err.println("IMClient: Problem reading object: class not found");
                                System.exit(1);
                            }
                            serverAmount--;
                            break;
                        case "AddTwo": //TODO: method illegal, adds two every time if we call in draw
                            for (int i = 0; i < 2; i++) {
                                Card addTwoNewCards = new Card(-2, "color", "image");
                                outObject.writeObject(addTwoNewCards);
                                try {
                                    Card getBackObject = (Card) in.readObject();
                                    clientCards.addCard(getBackObject);
                                    initCards.add(getBackObject.getImage());
                                    UnoGameClientController.updatePlayerCardAmount(clientCards.getCardCount());
                                    UnoGameClientController.initPlayerCards(initCards);
                                } catch (ClassNotFoundException cnfe) {
                                    System.err.println("IMClient: Problem reading object: class not found");
                                    System.exit(1);
                                }
                            }
                            break;
                        case "AddFour":
                            for (int i = 0; i < 4; i++) {
                                Card addFourNewCards = new Card(-2, "color", "image");
                                outObject.writeObject(addFourNewCards);
                                try {
                                    Card getBackObject = (Card) in.readObject();
                                    clientCards.addCard(getBackObject);
                                    initCards.add(getBackObject.getImage());
                                    UnoGameClientController.updatePlayerCardAmount(clientCards.getCardCount());
                                    UnoGameClientController.initPlayerCards(initCards);
                                } catch (ClassNotFoundException cnfe) {
                                    System.err.println("IMClient: Problem reading object: class not found");
                                    System.exit(1);
                                }
                            }
                            if (checkDiscardCard.getCardColor().charAt(3) == 'b') {
                                checkDiscardCard.setCardColor("Blue");
                                UnoGameClientController.setWildCardDisplay("Blue");
                            } else if (checkDiscardCard.getCardColor().charAt(3) == 'r') {
                                checkDiscardCard.setCardColor("Red");
                                UnoGameClientController.setWildCardDisplay("Red");
                            } else if (checkDiscardCard.getCardColor().charAt(3) == 'y') {
                                checkDiscardCard.setCardColor("Yellow");
                                UnoGameClientController.setWildCardDisplay("Yellow");
                            } else {
                                checkDiscardCard.setCardColor("Green");
                                UnoGameClientController.setWildCardDisplay("Green");
                            }
                            UnoGameClientController.toggleWildCardDisplay(true);
                            break;
                        case "WildCard":
                            System.out.println("Server has choose a wild card!");
                            if (checkDiscardCard.getCardColor().charAt(3) == 'b') {
                                checkDiscardCard.setCardColor("Blue");
                                UnoGameClientController.setWildCardDisplay("Blue");
                            } else if (checkDiscardCard.getCardColor().charAt(3) == 'r') {
                                checkDiscardCard.setCardColor("Red");
                                UnoGameClientController.setWildCardDisplay("Red");
                            } else if (checkDiscardCard.getCardColor().charAt(3) == 'y') {
                                checkDiscardCard.setCardColor("Yellow");
                                UnoGameClientController.setWildCardDisplay("Yellow");
                            } else {
                                checkDiscardCard.setCardColor("Green");
                                UnoGameClientController.setWildCardDisplay("Green");
                            }
                            UnoGameClientController.toggleWildCardDisplay(true);
                            break;
                    }
                }

                if (serverAmount == 1) {
                    System.out.println("SERVER HAS AN UNO!");
                }

                if (serverAmount == 0) {
                    System.out.println("You Lose!");
                    break;
                }
                careful = false;
                addingValue = false;
                initialization = false;
                System.out.println("This is how many cards the server has left: " + serverAmount);

                for (int i = 0; i < clientCards.getCardCount(); i++) {
                    System.out.println(clientCards.getSpecificCardFromDeck(i).toString());
                }

                UnoGameClientController.updateEnemyCardAmount(serverAmount);
                UnoGameClientController.updatePlayerCardAmount(clientCards.getCardCount());
                UnoGameClientController.initPlayerCards(initCards);
                UnoGameClientController.initDiscardCard(checkDiscardCard.getImage());
                System.out.println("This is the discard card, see if you have a match: " + checkDiscardCard.toString());
                UnoGameClientController.togglePlayerTurn(true);

                //TODO: simply send out a card via text input, should be a while loop
                boolean validText = false;
                boolean addingValidation = false;
                Card newCardIncoming = new Card();
                do {
                    myChoiceCard = stdIn.nextLine();
                    if (myChoiceCard.equals("draw")) {
                        addingValidation = true;
                        break;
                    }
                    //we have the text, now check to see if it matches one of our card toString
                    for (int i = 0; i < clientCards.getCardCount(); i++) {
                        if (clientCards.getSpecificCardFromDeck(i).toString().equals(myChoiceCard)) {
                            validText = true;
                            newCardIncoming = clientCards.getSpecificCardFromDeck(i);
                            break;
                        }
                    }

                    //Now that we have a real card, there's another trial to check
                    //the card must be logically correct in correlation to the discard card
                    if (validText) {
                        if (!checkUp(newCardIncoming, checkDiscardCard)) {
                            validText = false;
                            System.out.println("Invalid text or Card not there/found");
                        }
                        if (newCardIncoming.getCardColor().equals("Any")) {
                            System.out.println("WildCard has been chosen, what color do you want?: ");
                            wildCardChoice = stdIn.nextLine(); //TODO: add loop condition until they spell it right
                        }
                    } else {
                        System.out.println("Invalid text or Card not there/found");
                    }

                } while (!validText);


                //TODO: try to deprecate this as the block of code above does it work, but check first
                if (myChoiceCard.equals("draw")) {
                    //TODO: HOW ABOUT WE SEND IN AN EMPTY CARD OBJECT AND HAVE IT INITIALIZE IT FOR US AND GET RID OF THE OG
                    Card clientDrawCard = new Card(-2, "color", "image");
                    outObject.writeObject(clientDrawCard);
                    addingValidation = true;
                } else {
                    //TODO: try to deprecate this as the block of code above does it work, but check first
                    boolean checker = false;
                    Card newPotentialDiscardCard = new Card();
                    while (!checker) {
                        for (int i = 0; i < clientCards.getCardCount(); i++) {
                            if (myChoiceCard.equals(clientCards.getSpecificCardFromDeck(i).toString())) {
                                checker = true;
                                newPotentialDiscardCard = clientCards.peekCardAt(i);
                                break;
                            }
                        }
                    }

                    if (wildCardChoice != null) {
                        switch (wildCardChoice) {
                            case "blue":
                                newPotentialDiscardCard.setCardColor("Anyb");
                                break;
                            case "red":
                                newPotentialDiscardCard.setCardColor("Anyr");
                                break;
                            case "yellow":
                                newPotentialDiscardCard.setCardColor("Anyy");
                                break;
                            case "green":
                                newPotentialDiscardCard.setCardColor("Anyg");
                                break;
                        }
                        wildCardChoice = null;
                    }

                    if (newPotentialDiscardCard.getAbility() != null) {
                        if (newPotentialDiscardCard.getAbility().equals("AddTwo"))
                            serverAmount += 2;
                        if (newPotentialDiscardCard.getAbility().equals("AddFour"))
                            serverAmount += 4;
                    }

                    //TODO: now send the info to the server for checking
                    outObject.writeObject(newPotentialDiscardCard);
                    checkIfSameCard2 = newPotentialDiscardCard;
                    if (clientCards.getCardCount() == 0) {
                        System.out.println("You Win!");
                        exit = true;
                    }

                    initCards.remove(newPotentialDiscardCard.getImage());

                    UnoGameClientController.updateEnemyCardAmount(serverAmount);
                    UnoGameClientController.updatePlayerCardAmount(clientCards.getCardCount());
                    UnoGameClientController.initPlayerCards(initCards);
                    UnoGameClientController.initDiscardCard(newPotentialDiscardCard.getImage());
                    UnoGameClientController.togglePlayerTurn(false);
                    UnoGameClientController.toggleWildCardDisplay(false);

                }

                if (exit)
                    break;


                try {
                    if (addingValidation) {
                        Card newDrawedCard = (Card) in.readObject();
                        clientCards.addCard(newDrawedCard);
                        //UnoGameClientController.setImageName(newDrawedCard.getImage());
                        initCards.add(newDrawedCard.getImage());
                        UnoGameClientController.updatePlayerCardAmount(clientCards.getCardCount());
                        UnoGameClientController.initPlayerCards(initCards);
                        addingValue = true;
                    } else {
                        checkIfSameCard = discardCard;
                        discardCard = (Card) in.readObject();
                        if (discardCard.getCardNumber() >= 10) {
                            int temp = discardCard.getCardNumber() - 10 - 1;
                            serverAmount += temp;
                            outObject.writeObject(checkIfSameCard); //og discard card here
                            discardCard = (Card) in.readObject();
                            careful = true;
                        }

                        //TODO: say you win
                    }
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkUp(Card client, Card discard){
        if ((client.getCardColor().equals("Any") && !discard.getCardColor().equals("Any")) ||
                (!client.getCardColor().equals("Any") && discard.getCardColor().equals("Any"))){
            return true;
        }

        if ((client.getCardNumber() == discard.getCardNumber() || client.getCardColor().equals(discard.getCardColor()))){
            if (client.getCardNumber() == -1 && discard.getCardNumber() == -1){ //if both cards are specialty, then no!
                return false;
            }
            return true;
        }
        return false;
    }
}

