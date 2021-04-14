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

            game = new Game();

            CardDeck clientCards = game.getClientCardDeck();
            CardDeck serverCards = game.getServerCardDeck();
            Card discardPileTop = game.peekAtTopCardDiscard();

            //TODO: we send in the client its cards that it has to work with
            //      therefore the client must put these cards in its own data deck
            out.writeObject(clientCards);

            out.writeObject(discardPileTop);
            //TODO: read in back what the client has sent in to be the potential new discard top deck card
            Card clientCard = (Card) in.readObject();


            //TODO: game is always playing, so just have it running forever til we stop until no cards are left
            while(game.isPlaying() || clientCard != null){
                //get send info from client
                //check to see if match, if not, tell the client that it is no good
                //else, it is our turn

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

                System.out.println("Server Cards:");
                for (int i = 0; i < serverCards.getCardCount(); i++){
                    System.out.println(serverCards.getSpecificCardFromDeck(i).toString());
                }

                //TODO: UPDATE CLIENTS CARD ON THIS END AS WELL SO WE KNOW WHAT WE ARE DOING AND NOT LOST
                //      UPDATE: WTF THIS IS SUPPOSE TO WORK! WHY IS IT NOT WORKING!
                clientCards.removeSpecificCardFromDeck(clientCard);


                //TODO: good news, cards are that were once on the discard pile are sucessfully going back to the
                //      drawing pile but like on the bottom and not on top to where we can draw from, lol!
                System.out.println();
                //TODO: THIS IS A TEST CASE
                System.out.println("Client Cards:");
                for (int i = 0; i < clientCards.getCardCount(); i++){
                    System.out.println(clientCards.getSpecificCardFromDeck(i).toString());
                }

                //get the top card
                //match to see if it is valid, if not, ask again, probably use a while loop
                //send in info to client once done with the updated info
                System.out.println("Match the discard pile: " + clientCard.toString());

                game.putInCardInDiscardPile(clientCard);

                Scanner stdIn = new Scanner(System.in);
                String potential;
                Card potentiallyNewServerCard = new Card();
                boolean validText = false;
                do {
                    potential = stdIn.nextLine();
                    while (potential.equals("draw")){
                        serverCards.addCard(game.getFromDrawPile());
                        System.out.println("Server Cards:");
                        for (int i = 0; i < serverCards.getCardCount(); i++){
                            System.out.println(serverCards.getSpecificCardFromDeck(i).toString());
                        }
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

                    //Check to see if card is playable
                    //game.updateServerDeck(serverCards);
                    if (validText){
                        if (!game.regularLegalStatus(potentiallyNewServerCard)){
                            validText = false;
                            System.out.println("Invalid text or Card not there/found");
                        }
                    } else {
                        System.out.println("Invalid text or Card not there/found");
                    }
                } while (!validText);


                //TODO: try to deprecate this as the block of code above does it work, but check first
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

                game.putInCardInDiscardPile(newPotentialDiscardCard);
                game.putInCardInDrawingPile(clientCard);
                out.writeObject(newPotentialDiscardCard);


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
