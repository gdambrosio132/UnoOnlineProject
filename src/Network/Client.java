package Network;

import Backend.Game;
import Objects.Card;
import Objects.CardDeck;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/*
 * This is the client side of the application
 * TODO: This is for not just the client, but also for the server and the server thread
 *       get rid of the public static void main so it runs as we launch the application in another window
 */

public class Client {

    private CardDeck playerDeck;
    private Socket client;
    private int userID;
    private String name;
    private static Game game = new Game();


    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String clientName = "";
        boolean firstIteration = true;
        //we require the hostName and the portNumber for this simulation
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
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
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

        while ((checkDiscardCard = discardCard) != null) {

            if ((!checkIfSameCard.toString().equals(checkDiscardCard.toString())) && (!checkDiscardCard.toString().equals(checkIfSameCard2.toString())) && !initialization && !addingValue && !careful){
                serverAmount--;
            }

            if (!checkDiscardCard.toString().equals(checkIfSameCard.toString()))
                System.out.println("Yes");
            else
                System.out.println("No");

            if (!checkDiscardCard.toString().equals(checkIfSameCard2.toString()))
                System.out.println("Yes");
            else
                System.out.println("No");

            //TODO: make sure to skip this process if we get back our own card when we choose the skip/reverse feature
            //      THIS IS FOR WHEN A SERVER SENDS IN THEIR SPECIALTY CARDS! WE HAVE TO HANDLE IT HERE
            if (checkDiscardCard.getCardNumber() == -1 && !checkDiscardCard.toString().equals(checkIfSameCard2.toString()) && !addingValue){
                switch (checkDiscardCard.getAbility()){
                    case "Skip": case "Reverse":
                        outObject.writeObject(checkDiscardCard);
                        try {
                            checkIfSameCard = checkDiscardCard;
                            discardCard = (Card) in.readObject();
                            checkDiscardCard = discardCard; //TODO: add conditions here if server adds more cards afterwards
                            if (checkDiscardCard.getCardNumber() >= 10){
                                int temp = checkDiscardCard.getCardNumber() - 10;
                                serverAmount += temp;
                                outObject.writeObject(checkIfSameCard);
                                checkDiscardCard = (Card) in.readObject();
                            }
                        } catch (ClassNotFoundException cnfe){
                            System.err.println("IMClient: Problem reading object: class not found");
                            System.exit(1);
                        }
                        serverAmount--;
                        break;
                    case "AddTwo": //TODO: method illegal, adds two every time if we call in draw
                        for (int i = 0; i < 2; i++){
                            Card addTwoNewCards = new Card(-2, "color", "image");
                            outObject.writeObject(addTwoNewCards);
                            try{
                                Card getBackObject = (Card) in.readObject();
                                clientCards.addCard(getBackObject);
                            } catch (ClassNotFoundException cnfe){
                                System.err.println("IMClient: Problem reading object: class not found");
                                System.exit(1);
                            }
                        }
                        break;
                    case "AddFour":
                        for (int i = 0; i < 4; i++){
                            Card addFourNewCards = new Card(-2, "color", "image");
                            outObject.writeObject(addFourNewCards);
                            try{
                                Card getBackObject = (Card) in.readObject();
                                clientCards.addCard(getBackObject);
                            } catch (ClassNotFoundException cnfe){
                                System.err.println("IMClient: Problem reading object: class not found");
                                System.exit(1);
                            }
                        }
                        if (checkDiscardCard.getCardColor().charAt(3) == 'b'){
                            checkDiscardCard.setCardColor("Blue");
                        } else if (checkDiscardCard.getCardColor().charAt(3) == 'r'){
                            checkDiscardCard.setCardColor("Red");
                        } else if (checkDiscardCard.getCardColor().charAt(3) == 'y'){
                            checkDiscardCard.setCardColor("Yellow");
                        } else {
                            checkDiscardCard.setCardColor("Green");
                        }
                        break;
                    case "WildCard":
                        System.out.println("Server has choose a wild card!");
                        if (checkDiscardCard.getCardColor().charAt(3) == 'b'){
                            checkDiscardCard.setCardColor("Blue");
                        } else if (checkDiscardCard.getCardColor().charAt(3) == 'r'){
                            checkDiscardCard.setCardColor("Red");
                        } else if (checkDiscardCard.getCardColor().charAt(3) == 'y'){
                            checkDiscardCard.setCardColor("Yellow");
                        } else {
                            checkDiscardCard.setCardColor("Green");
                        }
                        break;
                }
            }

            if (serverAmount == 1){
                System.out.println("SERVER HAS AN UNO!");
            }

            if (serverAmount == 0){
                System.out.println("You Lose!");
                break;
            }
            careful = false;
            addingValue = false;
            initialization = false;
            System.out.println("This is how many cards the server has left: " + serverAmount);

            for (int i = 0; i < clientCards.getCardCount(); i++){
                System.out.println(clientCards.getSpecificCardFromDeck(i).toString());
            }
            System.out.println("This is the discard card, see if you have a match: " + checkDiscardCard.toString());

            //TODO: simply send out a card via text input, should be a while loop
            boolean validText = false;
            boolean addingValidation = false;
            Card newCardIncoming = new Card();
            do {
                myChoiceCard = stdIn.nextLine();
                if (myChoiceCard.equals("draw")){
                    addingValidation = true;
                    break;
                }
                //we have the text, now check to see if it matches one of our card toString
                for (int i = 0; i < clientCards.getCardCount(); i++){
                    if (clientCards.getSpecificCardFromDeck(i).toString().equals(myChoiceCard)){
                        validText = true;
                        newCardIncoming = clientCards.getSpecificCardFromDeck(i);
                        break;
                    }
                }

                //Now that we have a real card, there's another trial to check
                //the card must be logically correct in correlation to the discard card
                if (validText){
                    if (!checkUp(newCardIncoming,checkDiscardCard)) {
                        validText = false;
                        System.out.println("Invalid text or Card not there/found");
                    }
                    if (newCardIncoming.getCardColor().equals("Any")){
                        System.out.println("WildCard has been chosen, what color do you want?: ");
                        wildCardChoice = stdIn.nextLine(); //TODO: add loop condition until they spell it right
                    }
                } else {
                    System.out.println("Invalid text or Card not there/found");
                }

            } while (!validText);


            //TODO: try to deprecate this as the block of code above does it work, but check first
            if (myChoiceCard.equals("draw")){
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

                if (wildCardChoice != null){
                    switch (wildCardChoice){
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

                if (newPotentialDiscardCard.getAbility() != null){
                    if (newPotentialDiscardCard.getAbility().equals("AddTwo"))
                        serverAmount += 2;
                    if (newPotentialDiscardCard.getAbility().equals("AddFour"))
                        serverAmount += 4;
                }

                //TODO: now send the info to the server for checking
                outObject.writeObject(newPotentialDiscardCard);
                checkIfSameCard2 = newPotentialDiscardCard;
                if (clientCards.getCardCount() == 0){
                    System.out.println("You Win!");
                    exit = true;
                }
            }

            if (exit)
                break;

            try {
                if (addingValidation){
                    Card newDrawedCard = (Card) in.readObject();
                    clientCards.addCard(newDrawedCard);
                    addingValue = true;
                } else {
                    checkIfSameCard = discardCard;
                    discardCard = (Card) in.readObject();
                    if (discardCard.getCardNumber() >= 10){
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

