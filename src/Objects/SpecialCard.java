package Objects;

public class SpecialCard {
    String cardColor, ability;

    public SpecialCard(){
        //nothing
    }

    public SpecialCard(String cardColor, String ability) {
        this.cardColor = cardColor;
        this.ability = ability;
    }

    public SpecialCard(SpecialCard otherSpecialCard){
        this.cardColor = otherSpecialCard.cardColor;
        this.ability = otherSpecialCard.ability;
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
}
