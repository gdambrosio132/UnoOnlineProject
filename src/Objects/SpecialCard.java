package Objects;


//TODO: UPDATE - THIS CLASS IS ABANDON :)

import javafx.scene.image.Image;

public class SpecialCard {
    private String cardColor, ability;
    private Texture image;

    public SpecialCard(){
        //nothing
    }

    public SpecialCard(String cardColor, String ability, Texture image) {
        this.cardColor = cardColor;
        this.ability = ability;
        this.image = image;
    }

    public SpecialCard(SpecialCard otherSpecialCard){
        this.cardColor = otherSpecialCard.cardColor;
        this.ability = otherSpecialCard.ability;
        this.image = otherSpecialCard.image;
    }

    public String getCardColor() {
        return cardColor;
    }

    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }
}
