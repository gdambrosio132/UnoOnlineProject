package Backend;

import Objects.Card;
import Objects.CardDeck;
import Objects.Player;

import java.util.ArrayList;
import java.util.List;

/*
 * The point of this whole Backend.Game class is to establish the instructions of the game
 * This means when the game starts on a thread, we utilize the methods within this class
 * to start by calling the constructor
 *
 */

public class Game {
    private CardDeck drawing = CardDeck.initializeDeck(); //this is the drawing pile
    private CardDeck discard = new CardDeck(); //this is our dump pile
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
        int i = getServerCardIndex(client, c);
        server.removeSpecificCardFromDeck(i);
    }

    private int getServerCardIndex(CardDeck cardDeck, Card card){
        for (int i = 0; i < cardDeck.getCardCount(); i++){
            if (cardDeck.getSpecificCardFromDeck(i).toString().equals(card.toString())){
                return i;
            }
        }
        return -1;
    }

    public void updateServerDeck(CardDeck cardDeck){
        server = cardDeck;
    }

    public void putInCardInDiscardPile(Card card){
        Card newCard = discard.removeFirstCard();
        drawing.addCard(newCard);
        discard.addCard(card);
    }

    public String[] getStringCardDeckArray(CardDeck cardDeck){
        String[] temp = new String[cardDeck.getCardCount()];
        for (int i = 0; i < temp.length; i++){
            temp[i] = cardDeck.getSpecificCardFromDeck(i).getImage();
        }
        return temp;
    }

    public ArrayList<String> getStringCardDeckArrayList(CardDeck cardDeck){
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < cardDeck.getCardCount(); i++){
            temp.add(cardDeck.getSpecificCardFromDeck(i).getImage());
        }

        return temp;
    }

    public Card peekDrawingDeckCard(){
        return drawing.displayFrontalCard();
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
        if ((card.getCardColor().equals("Any") && !peekAtTopCardDiscard().getCardColor().equals("Any")) ||
                (!card.getCardColor().equals("Any") && peekAtTopCardDiscard().getCardColor().equals("Any"))){
            return true;
        }

        if (card.getCardNumber() == peekAtTopCardDiscard().getCardNumber() || card.getCardColor().equals(peekAtTopCardDiscard().getCardColor())){
            if (card.getCardNumber() == -1 && peekAtTopCardDiscard().getCardNumber() == -1){ //if both cards are specialty, then no!
                return false;
            }
            return true;
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

}
