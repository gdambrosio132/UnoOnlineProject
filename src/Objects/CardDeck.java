package Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    //We must initialize new card deck
    //this is how it goes
    //4 different colors - red, yellow, green, and blue
    //each card has listing of 1-9 which are repeated twice
    //each color has unique 0
    //deal with special cards later

    private static List<Card> cards;
    private static List<SpecialCard> specialCards;
    private List<Object> cardDeck;
    private static String[] colors = {"Red", "Yellow", "Green", "Blue"};
    private static String[] specialty = {"SwitchDeck", "AddFour", "AddTwo", "Skip", "WildColor", "Reverse"};
    //No need for any other constructor as we are merely just creating a stack of default cards
    //maybe the operations take place here when we need it to be

    public CardDeck(){
        this.cardDeck = new ArrayList<>();
    }

    public CardDeck(List<Object> cardDeck){
        this.cardDeck = cardDeck;
    }

    //Next we got to add and remove stuff from our deck of cards
    //We also have to plan on distributing our cards from our deck to our players

    //Add card to a deck //probably deletes these first two add methods
    public void addCard(Card card){
        cardDeck.add(card);
    }

    public void addCard(SpecialCard card){
        cardDeck.add(card);
    }

    public void addCard(Object card){
        cardDeck.add(card);
    }

    public Object draw(){
        //maybe add !assert for cardDeck.size() so we won't encounter an error
        //might show up as an error if we run this at the end
        if (cardDeck.size() < 0 || cardDeck.isEmpty()){
            return null;
        }
        return cardDeck.remove(0);
    }

    public int getCardCount(){
        return cardDeck.size();
    }



    //maybe make this into a static method but for now, leave it as it is
    public static CardDeck initializeDeck(){
        //insert all regular card variants from 0 - 9 of different colors
        List<Object> cardDeckInit = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            for (int j = 0; i < 10; i++){
                cardDeckInit.add(cards.add(new Card(j, colors[i])));
                //maybe add if-else clause in here to add in extra pairings
                if (j != 0){
                    cardDeckInit.add(cards.add(new Card(j, colors[i])));
                }
            }

            //insert specialty cards
            for (int k = 0; k < 6; k++){
                cardDeckInit.add(specialCards.add(new SpecialCard(colors[i], specialty[k])));
                //add if-else clause as before i think
            }
        }

        Collections.shuffle(cardDeckInit);

        //we should have our card deck all set and ready by this point
        return new CardDeck(cardDeckInit);
    }
}
