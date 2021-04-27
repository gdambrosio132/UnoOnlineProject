package Network;

import Backend.Game;
import Objects.Card;
import Objects.CardDeck;

//TODO: WE AREN'T UPDATING THE DISCARD PILE HERE, THIS IS A MUST!
//TODO: FUTURE GIUSEPPE, MAKE SURE YOU ARE COMMUNICATING WITH THE GAME CLASS AS WELL TO UPDATE IT
//      UPDATE: MAYBE NOT
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class GameMultiServerThread extends Thread {
    private Socket socket = null;
    private Game game = null;

    public GameMultiServerThread(Socket socket){
        super("Network.GameMultiServerThread");
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String inputLine, inputLineName, outputLine;
            Card inputCard, outputCard;
            Card checkIfSameCard = new Card();

            game = new Game();

            CardDeck clientCards = game.getClientCardDeck();
            CardDeck serverCards = game.getServerCardDeck();
            Card discardPileTop = game.peekAtTopCardDiscard();
            String wildCardChoice = null;
            boolean opertationSpecialty = false;
            boolean drawingAfterSkipping = false;

            System.out.println("Server Cards:");
            for (int i = 0; i < serverCards.getCardCount(); i++){
                System.out.println(serverCards.getSpecificCardFromDeck(i).toString());
            }

            //TODO: we send in the client its cards that it has to work with
            //      therefore the client must put these cards in its own data deck
            out.writeObject(clientCards);

            out.writeObject(discardPileTop);
            //TODO: read in back what the client has sent in to be the potential new discard top deck card
            Card clientCard = (Card) in.readObject();


            //TODO: game is always playing, so just have it running forever til we stop until no cards are left
            while(clientCard != null){

                //this means the client is drawing the card and we have to bring back its card
                while (clientCard.getCardNumber() == -2){
                    //draw a card and mirror its stuff on it
                    clientCard = game.getFromDrawPile();
                    clientCards.addCard(clientCard);// this is for our side visual and game info
                    out.writeObject(clientCard);
                    try {
                        clientCard = (Card) in.readObject();
                    }catch (ClassNotFoundException cnfe){
                        System.err.println("IMClient: Problem reading object: class not found");
                        System.exit(1);
                    }
                }

                //TODO: Check the specialty cards here

                /*
                if (!checkIfSameCard.toString().equals(clientCard.toString()))
                    System.out.println("Yes");
                else
                    System.out.println("No");
                */

                if(checkIfSameCard.toString().equals(clientCard.toString()) && clientCard.getCardNumber() == -1)
                    opertationSpecialty = true;

                if (clientCard.getCardNumber() == -1 && !checkIfSameCard.toString().equals(clientCard.toString())){
                    switch (clientCard.getAbility()){
                        case "Skip": case "Reverse":
                            game.removeCardFromClientDeck(clientCard);
                            game.putInCardInDiscardPile(clientCard);
                            out.writeObject(clientCard);//TODO: this is an error, maybe not
                            game.putInCardInDrawingPile(clientCard);
                            try {
                                clientCard = (Card) in.readObject();
                                while (clientCard.getCardNumber() == -2){
                                    //draw a card and mirror its stuff on it
                                    drawingAfterSkipping = true;
                                    clientCard = game.getFromDrawPile();
                                    clientCards.addCard(clientCard);// this is for our side visual and game info
                                    out.writeObject(clientCard);
                                    try {
                                        clientCard = (Card) in.readObject();
                                    }catch (ClassNotFoundException cnfe){
                                        System.err.println("IMClient: Problem reading object: class not found");
                                        System.exit(1);
                                    }
                                }
                                game.removeCardFromClientDeck(clientCard);
                                opertationSpecialty = true;
                            } catch (ClassNotFoundException cnfe){
                                System.err.println("IMClient: Problem reading object: class not found");
                                System.exit(1);
                            }
                            break;
                        case "AddTwo":
                            for (int i = 0; i < 2; i++)
                                serverCards.addCard(game.getFromDrawPile());
                            break;
                        case "AddFour":
                            for (int i = 0; i < 4; i++)
                                serverCards.addCard(game.getFromDrawPile());
                            Card test2 = new Card(clientCard);
                            test2.setCardColor("Any");
                            test2.setAbility("AddFour");
                            Card removerCardForClientAdding = new Card();
                            for (int i = 0; i < clientCards.getCardCount(); i++){
                                if (clientCards.getSpecificCardFromDeck(i).toString().equals(test2.toString())){
                                    removerCardForClientAdding = clientCards.getSpecificCardFromDeck(i);
                                }
                            }
                            game.removeCardFromClientDeck(removerCardForClientAdding);
                            if (clientCard.getCardColor().charAt(3) == 'b'){ //TODO: for some reason, if client draws after, it goes to green
                                clientCard.setCardColor("Blue");
                            } else if (clientCard.getCardColor().charAt(3) == 'r'){
                                clientCard.setCardColor("Red");
                            } else if (clientCard.getCardColor().charAt(3) == 'y'){
                                clientCard.setCardColor("Yellow");
                            } else {
                                clientCard.setCardColor("Green");
                            }
                            opertationSpecialty = true;
                            break;
                        case "WildCard":
                            System.out.println("Client has choose a wild card!");
                            Card test = new Card(clientCard);
                            test.setCardColor("Any");
                            test.setAbility("WildCard");
                            Card removerCardForClient = new Card();
                            for (int i = 0; i < clientCards.getCardCount(); i++){
                                if (clientCards.getSpecificCardFromDeck(i).toString().equals(test.toString())){
                                    removerCardForClient = clientCards.getSpecificCardFromDeck(i);
                                }
                            }
                            game.removeCardFromClientDeck(removerCardForClient);
                            if (clientCard.getCardColor().charAt(3) == 'b'){ //TODO: for some reason, if client draws after, it goes to green
                                clientCard.setCardColor("Blue");
                            } else if (clientCard.getCardColor().charAt(3) == 'r'){
                                clientCard.setCardColor("Red");
                            } else if (clientCard.getCardColor().charAt(3) == 'y'){
                                clientCard.setCardColor("Yellow");
                            } else {
                                clientCard.setCardColor("Green");
                            }
                            opertationSpecialty = true;
                            break;
                    }
                }

                /*
                System.out.println("Client Cards:");
                for (int i = 0; i < clientCards.getCardCount(); i++){
                    System.out.println(clientCards.getSpecificCardFromDeck(i).toString());
                }*/

                System.out.println("Server Cards:");
                for (int i = 0; i < serverCards.getCardCount(); i++){
                    System.out.println(serverCards.getSpecificCardFromDeck(i).toString());
                }

                if (drawingAfterSkipping){
                    opertationSpecialty = false;
                    drawingAfterSkipping = false;
                }

                if (!opertationSpecialty){
                    game.removeCardFromClientDeck(clientCard);
                }                                          //TODO: error when sending out reverse/skip card here from client
                opertationSpecialty = false;               //   Because when we get back the skip/reverse card we sent in, it wants to remove that
                                                           //   card from the client deck even though it was in the server deck, so its an error
                clientCards = game.getClientCardDeck();
                System.out.println("The client has this many cards left: " + clientCards.getCardCount());
                if (clientCards.getCardCount() == 1){
                    System.out.println("CLIENT HAS AN UNO!");
                }

                if (clientCards.getCardCount() == 0){
                    System.out.println("You Lose!");
                    break;
                } else if (serverCards.getCardCount() == 0){
                    System.out.println("You Win!");
                    break;
                }


                System.out.println("Match the discard pile: " + clientCard.toString());

                game.putInCardInDiscardPile(clientCard);

                Scanner stdIn = new Scanner(System.in);
                String potential;
                Card potentiallyNewServerCard = new Card();
                boolean validText = false;
                int adding = 0;
                do {
                    potential = stdIn.nextLine();
                    while (potential.equals("draw")){
                        serverCards.addCard(game.getFromDrawPile());
                        adding++;
                        System.out.println("Server Cards:");
                        for (int i = 0; i < serverCards.getCardCount(); i++){
                            System.out.println(serverCards.getSpecificCardFromDeck(i).toString());
                        }
                        System.out.println("Match the discard pile: " + clientCard.toString());
                        System.out.println("Do you want to draw again? Say draw");
                        potential = stdIn.nextLine();
                    }
                    //we have the text, now check to see if it matches one of our card toString
                    for (int i = 0; i < serverCards.getCardCount(); i++){
                        if (serverCards.getSpecificCardFromDeck(i).toString().equals(potential)){
                            potentiallyNewServerCard = serverCards.getSpecificCardFromDeck(i);
                            validText = true;
                            break;
                        }
                    }

                    if (validText){
                        if (!game.regularLegalStatus(potentiallyNewServerCard)){
                            validText = false;
                            System.out.println("Invalid text or Card not there/found");
                        }
                        if (potentiallyNewServerCard.getCardColor().equals("Any")){
                            System.out.println("WildCard has been chosen, what color do you want?: ");
                            wildCardChoice = stdIn.nextLine(); //TODO: add loop condition until they spell it right
                        }
                    } else {
                        System.out.println("Invalid text or Card not there/found");
                    }

                } while (!validText);


                if (adding > 0){
                    Card mockCard = new Card(10 + adding, "color", "image");
                    out.writeObject(mockCard);
                    try {
                        clientCard = (Card) in.readObject();
                    } catch (ClassNotFoundException cnfe){
                        System.err.println("IMClient: Problem reading object: class not found");
                        System.exit(1);
                    }

                }

                //TODO: try to deprecate this as the block of code above does it work, but check first
                //TODO: Yeah we might need this
                boolean checker = false;
                Card newPotentialDiscardCard = new Card();
                while (!checker) {
                    for (int i = 0; i < serverCards.getCardCount(); i++) {
                        if (potential.equals(serverCards.getSpecificCardFromDeck(i).toString())) {
                            checker = true;
                            newPotentialDiscardCard = serverCards.peekCardAt(i);
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

                game.putInCardInDiscardPile(newPotentialDiscardCard);
                game.putInCardInDrawingPile(clientCard);
                out.writeObject(newPotentialDiscardCard);
                checkIfSameCard = newPotentialDiscardCard;

                //TODO: test to see our drawing card pile


                try {
                    clientCard = (Card) in.readObject();
                } catch (ClassNotFoundException cnfe){
                    System.err.println("IMClient: Problem reading object: class not found");
                    System.exit(1);
                }
            }

            //Close Streams
            System.out.println("Close Connection");
            out.close();
            in.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            System.err.println("IMClient: Problem reading object: class not found");
            System.exit(1);
        }

    }

}
