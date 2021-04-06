package Objects;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck implements Serializable {
    //We must initialize new card deck
    //this is how it goes
    //4 different colors - red, yellow, green, and blue
    //each card has listing of 1-9 which are repeated twice
    //each color has unique 0
    //deal with special cards later

    private List<Card> cardDeck;
    private static String[] colors = {"Red", "Yellow", "Green", "Blue", "Any"};
    private static String[] specialty = {"Skip", "Reverse", "AddTwo"};
    private static String[] otherSpecialty = {"AddFour", "AddFour", "WildCard", "WildCard"};
    //No need for any other constructor as we are merely just creating a stack of default cards
    //maybe the operations take place here when we need it to be

    public CardDeck(){
        this.cardDeck = new ArrayList<>();
    }

    public CardDeck(List<Card> cardDeck){
        this.cardDeck = cardDeck;
    }

    //Next we got to add and remove stuff from our deck of cards
    //We also have to plan on distributing our cards from our deck to our players

    //Add card to a deck //probably deletes these first two add methods

    public void addCard(Card card){
        cardDeck.add(card);
    }

    public Card draw(){
        //maybe add !assert for cardDeck.size() so we won't encounter an error
        //might show up as an error if we run this at the end
        if (cardDeck.size() < 0 || cardDeck.isEmpty()){
            return null;
        }
        return cardDeck.remove(0);
    }

    public Card peekCardAt(int i){
        if (cardDeck.size() < i || cardDeck.isEmpty()){
            return null;
        }
        return cardDeck.remove(i);
    }

    public Card removeFirstCard(){
        return cardDeck.remove(0);
    }

    public Card displayFrontalCard(){
        return cardDeck.get(0);
    }

    public int getCardCount(){
        return cardDeck.size();
    }

    public Card getSpecificCardFromDeck(int i){
        return cardDeck.get(i);
    }


    //maybe make this into a static method but for now, leave it as it is
    //UPDATE: THIS WORKS! EUREKA
    public static CardDeck initializeDeck(){
        //insert all regular card variants from 0 - 9 of different colors
        //Along with getting the card image ids
        List<Card> cardDeckInit = new ArrayList<>();
        List<String> cardFileNames = cardReader();
        int cardTracker = 0;
        int specialCardTracker = 40;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 10; j++){
                cardDeckInit.add(new Card(j, colors[i],  cardFileNames.get(cardTracker)));
                //maybe add if-else clause in here to add in extra pairings
                if (j > 0){
                    cardDeckInit.add(new Card(j, colors[i], cardFileNames.get(cardTracker)));
                }
                cardTracker++;
            }

            //insert specialty cards
            for (int k = 0; k < 3; k++){
                cardDeckInit.add(new Card(colors[i], specialty[k], cardFileNames.get(specialCardTracker)));
                specialCardTracker++;
                //add if-else clause as before i think
            }
        }

        for (int l = 0; l < 4; l++){
            cardDeckInit.add(new Card(otherSpecialty[l], cardFileNames.get(specialCardTracker)));
            specialCardTracker++;
        }


        Collections.shuffle(cardDeckInit);

        //we should have our card deck all set and ready by this point
        return new CardDeck(cardDeckInit);
    }

    //Gets all listed items in the file, this will be the helper method for initialize method above
    public static List<String> cardReader(){
        List<String> fileNames = new ArrayList<String>();
        File[] files = new File("images/cardfolder").listFiles();

        for (File file : files){
            if (file.isFile()){
                fileNames.add(file.getName());
            }
        }

        return fileNames;
    }
}
