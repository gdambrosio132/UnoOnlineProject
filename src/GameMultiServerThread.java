import Objects.Card;
import Objects.CardDeck;

//TODO: WE AREN"T UPDATING THE DISCARD PILE HERE, THIS IS A MUST!

import java.net.*;
import java.io.*;

public class GameMultiServerThread extends Thread {
    private Socket socket = null;
    private Game game = null;

    public GameMultiServerThread(Socket socket){
        super("GameMultiServerThread");
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String inputLine, inputLineName, outputLine;
            Card inputCard, outputCard;

                game = new Game();
                //SimpleIMProtocol kkp = new SimpleIMProtocol();
                //outputLine = kkp.processInput(null);
                //get the cards for you and the player

                CardDeck clientCards = game.getClientCardDeck();
                CardDeck serverCards = game.getServerCardDeck();
                Card discardPileTop = game.peekAtTopCardDiscard();

                //TODO: we send in the client its cards that it has to work with
                //      therefore the client must put these cards in its own data deck
                out.writeObject(clientCards);
                //Message message = new Message("Server", outputLine);
                //out.writeObject(message);
                out.writeObject(discardPileTop);
                //TODO: read in back what the client has sent in to be the potential new discard top deck card
                Card clientCard = (Card) in.readObject();
                //Message inMessage = (Message) inObject.readObject();

                //TODO: game is always playing, so just have it running forever til we stop until no cards are left
                while(game.isPlaying()){
                    //get send info from client
                    //check to see if match, if not, tell the client that it is no good
                    //else, it is our turn

                    for (int i = 0; i < serverCards.getCardCount(); i++){
                        System.out.println(serverCards.getSpecificCardFromDeck(i).toString());
                    }

                    //TODO: our input should match what is potential
                    while (!game.regularLegalStatus(clientCard)){
                        out.writeObject(clientCard);
                        try {
                            clientCard = (Card) in.readObject();
                        }catch (ClassNotFoundException cnfe){
                            System.err.println("IMClient: Problem reading object: class not found");
                            System.exit(1);
                        }
                    }

                    //get the top card
                    //match to see if it is valid, if not, ask again, probably use a while loop
                    //send in info to client once done with the updated info
                    System.out.println("Match the discard pile: " + clientCard.toString());

                    //TODO: now it matches, so lets keep going
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                    String potential = stdIn.readLine();
                    if (potential.equals("add")){
                        serverCards.addCard(game.getFromDrawPile());
                        out.writeObject(clientCard);
                    } else {
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

                        out.writeObject(newPotentialDiscardCard);
                    }

                    try {
                        clientCard = (Card) in.readObject();
                    } catch (ClassNotFoundException cnfe){
                        System.err.println("IMClient: Problem reading object: class not found");
                        System.exit(1);
                    }
                }

                /*
                while((inputLine = inMessage.getResponse()) != null || (inputLineName = inMessage.getName()) != null){
                    System.out.println(inMessage.toString());
                    System.out.print("Server");

                    outputLine = kkp.processInput(inputLine);
                    message = new Message("Server", outputLine);
                    out.writeObject(message);

                    if (outputLine.equals("Bye"))
                        break;

                    try {
                        inMessage = (Message) in.readObject();
                    } catch (ClassNotFoundException cnfe) {
                        System.err.println("IMClient: Problem reading object: class not found");
                        System.exit(1);
                    }

                }*/

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
