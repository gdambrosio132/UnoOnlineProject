import Objects.Card;
import Objects.CardDeck;

import java.io.Serializable;
import java.net.Socket;

public class Player implements Serializable {
    private long id;
    private String username;
    private CardDeck playerDeck;
    private boolean playerTurn, canDraw, uno;
    private Socket socket;

    public Player(long id, String username){
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CardDeck getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(CardDeck playerDeck) {
        this.playerDeck = playerDeck;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isCanDraw() {
        return canDraw;
    }

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }

    public boolean isUno() {
        return uno;
    }

    public void setUno(boolean uno) {
        this.uno = uno;
    }

    public void togglePlayerTurn(){
        playerTurn = !playerTurn;
    }

    public void addCard(Card card){
        this.playerDeck.addCard(card);
    }
}
