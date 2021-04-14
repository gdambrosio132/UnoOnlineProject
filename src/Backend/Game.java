package Backend;

import Objects.Card;
import Objects.CardDeck;
import Objects.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * The point of this whole Backend.Game class is to establish the instructions of the game
 * This means when the game starts on a thread, we utilize the methods within this class
 * to start by calling the constructor
 *
 */

public class Game {
    private CardDeck drawing = CardDeck.initializeDeck(); //this is the drawing pile
    //private CardDeck presentDeck = new CardDeck();
    private CardDeck discard = new CardDeck(); //this is our dump pile
    private int turn = 0;
    private int turnCount = 0;
    private int direction = 1;
    private boolean action = false;
    private List<Player> players = new ArrayList<Player>();
    private boolean playing = false;
    private CardDeck server;
    private CardDeck client;
    private String what = null;

    //This initializes the game by assigning players with a list of 7 cards each from the drawing pile
    //inside the parameters goes List<Network.Client> players;
    public Game(int playerCount){
        for (int i = 0; i < playerCount; i++){ //side note, the 4 is replace by the joined player count
            for (int j = 0; j < 7; j++){
                players.get(i).addCard(drawing.draw());
            }
        }

        Card firstCard = drawing.draw();
        //logically, we don't want the first card to be a wild card, so we keep picking until we see something regular
        //TODO: It should work now as special cards are label as -1
        while (firstCard.getCardNumber() < 0){
            drawing.addCard(firstCard);
            firstCard = drawing.draw();
        }
        discard.addCard(firstCard);
        playing = true;
    }

    public Game(String what, CardDeck client){
        this.what = what;
        this.client = new CardDeck();
        this.client = client;
    }

    //New and improved Backend.Game Constructor
    public Game(){
        client = new CardDeck();
        server = new CardDeck();
        for (int i = 0; i < 7; i++){
            client.addCard(drawing.draw());
            server.addCard(drawing.draw());
        }

        Card firstCard = drawing.draw();
        while (firstCard.getCardNumber() < 0){
            drawing.addCard(firstCard);
            firstCard = drawing.draw();
        }
        discard.addCard(firstCard);
        playing = true;

    }

    public CardDeck getClientCardDeck(){
        return client;
    }

    public void addCardToClientDeck(Card c){
        client.addCard(c);
    }

    public void removeCardFromClientDeck(Card c){
        //TODO: find the index of that card position
        int i = getClientCardIndex(client, c);
        client.removeSpecificCardFromDeck(i);
    }

    private int getClientCardIndex(CardDeck cardDeck, Card card){
        for (int i = 0; i < cardDeck.getCardCount(); i++){
            if (cardDeck.getSpecificCardFromDeck(i).toString().equals(card.toString())){
                return i;
            }
        }
        return -1;
    }

    public void updateClientDeck(CardDeck cardDeck){
        client = cardDeck;
    }

    public CardDeck getServerCardDeck(){
        return server;
    }

    public void addCardToServerDeck(Card c){
        server.addCard(c);
    }

    //TODO: DONT USE THIS METHOD
    public void removeCardFromServerDeck(Card c){
        server.removeSpecificCardFromDeck(1);
    }

    public void updateServerDeck(CardDeck cardDeck){
        server = cardDeck;
    }


    public void turn(){
        turnCount++;
        if (turnCount <= 0){
            turn = players.size();
        }

        //TODO: This signals that it is a wild card
        //      FILL IN THE LOGIC
        if (getTopDiscard().getCardNumber() == -1){
            switch(discard.displayFrontalCard().getAbility()){
                case "Skip":
                    //Action is performed, player goes again
                    break;

                case "Reverse":
                    //this is lowkey useless since we are having two players lol
                    break;

                case "AddTwo":
                    //give the opponent two cards from the deck regardless
                    break;

                case "AddFour":
                    //give them four cards if and only if they can't back themselves up with similar colors
                    break;

                case "WildCard":
                    //the wild card is to change the motto of the game, different color and must match
                    break;

                default:
                    break;
            }
        }

        Player currentPlayer = players.get(turn % players.size());
        boolean inTurn = true;
        boolean drew = false;
        boolean showInfo = true;
        Card last = getTopDiscard(); //this gets us a peek of the card to match

        //TODO: IMPORTANT - we dont want to include double down feature such as one wild card over the other, too much work
        //                  stick with one wild card and one draw at a time
        while(inTurn){
            //TODO: if the card is a wild card, check that first, probably not in the first condition
            //      since we purposely said no wild card first in the first instance
            if (last.getCardNumber() == -1){
                //TODO: this is busy work, figure out logistics later
            } else {
                //TODO: NOW!

            }
        }
    }

    public void putInCardInDiscardPile(Card card){
        Card newCard = discard.removeFirstCard();
        drawing.addCard(newCard);
        discard.addCard(card);
    }

    public void putInCardInDrawingPile(Card c){
        drawing.addCard(c);
    }

    private Card getTopDiscard(){
        return discard.peekCardAt(discard.getCardCount());
    }

    public Card peekAtTopCardDiscard(){
        return discard.displayFrontalCard();
    }

    public boolean regularLegalStatus(Card card){
        if (card.getCardNumber() == peekAtTopCardDiscard().getCardNumber() || card.getCardColor().equals(peekAtTopCardDiscard().getCardColor())){
            return true;
        }
        return false;
    }

    public boolean advanceLegalStatus(Card card){
        if (card.getCardColor().equals(getTopDiscard().getCardColor())){

        }
        return false;
    }

    public void printDrawingDeck(){
        System.out.println("Drawing Deck: ");
        for (int i = 0; i < drawing.getCardCount(); i++){
            System.out.println(drawing.getSpecificCardFromDeck(i).toString());
        }
    }


    public boolean isPlaying(){
        return playing;
    }

    public Card getFromDrawPile(){
        return drawing.draw();
    }

    public String processInput(String theInput) {
        String theOutput = null;
        //System.out.print("Server:");
        Scanner scan = new Scanner(System.in);
        /*
        if (state == WAITING) {
            theOutput = "Connection Established!";
            //start deck and give cards to players here

            state = IN_GAMEPLAY;
        } else if (state == IN_GAMEPLAY) {

            System.out.print("Server: ");
            theOutput =scan.nextLine();
        } else {
            theOutput = "Bye.";
            state = WAITING;
        }*/

        theOutput = scan.nextLine();
        return theOutput;
    }

}
