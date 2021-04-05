package Objects;

import javafx.scene.image.Image;

public class Card {
    private int cardNumber;
    private String cardColor;
    private String image;
    private String ability;


    //default constructor
    public Card(){
        //nothing
    }

    public Card(int cardNumber, String cardColor, String image){
        this.cardNumber = cardNumber;
        this.cardColor = cardColor;
        this.ability = null;
        this.image = image;
    }

    public Card(String cardColor, String ability, String image){
        this.cardNumber = -1;
        this.cardColor = cardColor;
        this.ability = ability;
        this.image = image;
    }

    public Card(String ability, String image){
        this.cardNumber = -1;
        this.cardColor = "Any";
        this.ability = ability;
        this.image = image;
    }

    public Card(Card otherCard){
        this.cardNumber = otherCard.cardNumber;
        this.cardColor = otherCard.cardColor;
        this.ability = ability;
        this.image = otherCard.image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String toString(){
        if (getAbility() == null){
            return getCardColor() + " " + getCardNumber() + " " + getImage();
        } else if (getCardColor() == null){
            return getCardNumber() + " " + getAbility() + " " + getImage();
        } else {
            return getCardColor() + " " + getCardNumber() + " " + getAbility() + " " + getImage();
        }
    }
}
