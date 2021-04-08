package Extras;

import Objects.Card;
import Objects.CardDeck;

public class OutputTester {
    public static void main(String[] args){
        CardDeck drawing = CardDeck.initializeDeck();

        int total = drawing.getCardCount();
        for (int i = 0; i < total; i++){
            System.out.println(drawing.draw().toString());
        }
    }
}
