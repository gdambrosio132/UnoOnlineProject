package Objects;

public class Card {
    private int cardNumber;
    private String cardColor;

    //default constructor
    public Card(){
        //nothing
    }

    public Card(int cardNumber, String cardColor){
        this.cardNumber = cardNumber;
        this.cardColor = cardColor;
    }

    public Card(Card otherCard){
        this.cardNumber = otherCard.cardNumber;
        this.cardColor = otherCard.cardColor;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardColor() {
        return cardColor;
    }

    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }
}
