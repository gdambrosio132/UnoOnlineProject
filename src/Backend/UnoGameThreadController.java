package Backend;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UnoGameThreadController implements Initializable{

    @FXML
    private static ArrayList<ImageView> enemyCardView = new ArrayList<ImageView>();

    @FXML
    private static ArrayList<ImageView> playerCardView = new ArrayList<ImageView>();

    @FXML
    private HBox enemyHbox;

    @FXML
    private HBox playerHbox;

    @FXML
    private ImageView discardPile = new ImageView();

    @FXML
    private ImageView drawingImage;

    private static int enemyCardTotal = 7;
    private static String[] initCards = new String[7];
    //private static String[] initCards = {"row-1-col-0.jpg", "row-4-col-4.jpg", "row-3-col-5.jpg", "row-4-col-9.jpg", "row-1-col-6.jpg", "row-6-col-0.jpg", "row-5-col-9.jpg"};
    private static String imageName;
    private static String newCardImage;
    private static String initDiscardCard;

    public UnoGameThreadController(){
        //empty body
    }


    //TODO: give the player cards to the respected players visually
    //      You have to add another thing in the parameter for the player to get its cards
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //create a for loop that gives off the initialize look

        drawingImage.setImage(new Image(new File("images/UnoBackSide.png").toURI().toString()));

        discardPile.setImage(new Image(new File("images/cardFolder/" + initDiscardCard).toURI().toString()));
        for(int i = 0; i < 7; i++) {
            File enemyFileCard = new File("images/UnoBackSide_2.png");
            Image enemyCardImage = new Image(enemyFileCard.toURI().toString());
            enemyCardView.add(new ImageView(enemyCardImage));
            enemyHbox.getChildren().add(enemyCardView.get(i));

            //TODO: variable i must be replace with the inputted arraylist from the parameter, get around to it!
            //      WARNING, might produce null pointer exception!
            //File playerFileCard = new File("images/UnoBackSide_2.png");
            File playerFileCard = new File("images/cardFolder/" + initCards[i]);
            Image playerCardImage = new Image(playerFileCard.toURI().toString());
            playerCardView.add(new ImageView(playerCardImage));
            //TODO: increment the arraylist of the card from paramenters in here to get the next card
            playerHbox.getChildren().add(playerCardView.get(i));
        }

        /*
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                // Call update method for every 2 sec.
                                update();
                            }
                        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();*/

    }


    //TODO: This returns a card to the player to their deck
    //      Just display the new card from input
    //      Insert new parameter that gets the card image file name
    //      might redo this just in case to save on mem, idk
    public void drawOnAction(MouseEvent event){
        String imageFileName = getImageName();
        //drawingImage.setImage(new Image("images/UnoBackSide.png"));
        File playerCardDrawn = new File("images/cardFolder/" + imageFileName);
        playerHbox.getChildren().add(new ImageView(new Image(playerCardDrawn.toURI().toString())));
    }

    public static void setImageName(String imName){
        imageName = imName;
    }

    private String getImageName(){
        return imageName;
    }


    //TODO: this gets rid of the specific card that was clicked
    //      just remove that card from the player's deck
    public void playCardOnAction(ActionEvent event){
        String imageFileName = getCardImage();
        File playerCardPlay = new File("images/cardFolder/" + imageFileName);
        playerHbox.getChildren().remove(new ImageView(new Image(playerCardPlay.toURI().toString())));
        discardPile.setImage(new Image(playerCardPlay.toURI().toString()));
    }

    private String getCardImage(){
        return newCardImage;
    }

    public static void setCardImage(String newCardImageSent){
        newCardImage = newCardImageSent;
    }

    //TODO: this is for the opponent to display the cards once the player plays a certain card
    public void setDiscardPile(String cardName){
        File playerCardPlay = new File("images/cardFolder/" + cardName);
        discardPile.setImage(new Image(playerCardPlay.toURI().toString()));
    }


    //TODO: create the update off the enemy's card deck view
    //      LOOK OVER THE LOGIC BEFORE EXECUTION
    public static void updateEnemyCardView(int cardAmount){
        int oldRef = enemyCardTotal; //contains old reference to card amount
        enemyCardTotal = cardAmount;
        //TODO: now update the card amount visually
        if (oldRef < enemyCardTotal){
            //TODO: update visually throughout the first 2, but not the else clause
            for (int i = oldRef; i < enemyCardTotal; i++){
                File enemyFileCard = new File("images/UnoBackSide.png");
                Image enemyCardImage = new Image(enemyFileCard.toURI().toString());
                enemyCardView.add(new ImageView(enemyCardImage));
                //enemyHbox.getChildren().add(new ImageView(enemyCardImage));
            }

        } else if (oldRef > enemyCardTotal) {
            for (int i = oldRef; i > enemyCardTotal; i--){
                File enemyFileCard = new File("images/UnoBackSide.png");
                Image enemyCardImage = new Image(enemyFileCard.toURI().toString());
                enemyCardView.remove(new ImageView(enemyCardImage));
                //enemyHbox.getChildren().remove(new ImageView(enemyCardImage));
            }

        } else {
            //they are the same
            //empty body for now until the program throws an error potentially
        }
    }

    public static void initPlayerCards(String[] firstSet){
        initCards = firstSet;
        //File playerCardPlay = new File("images/cardFolder/UnoBackSide_2.png");
        //discardPile.setImage(new Image(playerCardPlay.toURI().toString()));
    }

    public static void initDiscardCard(String discardInit){
        initDiscardCard = discardInit;
    }

    /*
     * This is our update method, it gets called like every second or so and updates all the values given to us
     *  simply update any values in here
     */
    public void update(){

    }


}
