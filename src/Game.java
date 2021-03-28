import Objects.CardDeck;
import Objects.SpecialCard;

import java.util.List;

public class Game {
    private CardDeck drawing = CardDeck.initializeDeck();
    //private CardDeck presentDeck = new CardDeck();
    private CardDeck discard = new CardDeck();
    private int turn = 0;
    private int turnCount = 0;
    private int direction = 1;
    private boolean action = false;
    //private List<Player> players;
    private boolean playing = false;

    //This initializes the game by assigning players with a list of 7 cards each from the drawing pile
    //inside the parameters goes List<Client> players;
    public Game(){
        for (int i = 0; i < 4; i++){ //side note, the 4 is replace by the joined player count
            for (int j = 0; j < 7; j++){
                //players.get(i).giveCard(drawing.draw());
            }
        }

        Object firstCard = drawing.draw();
        //logically, we don't want the first card to be a wild card, so we keep picking until we see something regular
        while (firstCard.equals(SpecialCard.class)){
            drawing.addCard(firstCard);
            firstCard = drawing.draw();
        }
        discard.addCard(firstCard);
        playing = true;
    }


}
