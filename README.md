# UnoOnlineProject
Computer Network Application (WIP)

## How to play

*This game is currently under development and is in the primary stage*
*We are setting up the network connection and pasing in data*

1. First download the project from github or from the moodle submission page.

2. This project runs idealy with intellij, try not to run this on bluej!

3. Locate the java file called GameMultiServer (./src/network/GameMultiServer.java) and run it.

4. Locate the java file called Client and run it after running GameMultiServer, it should be in the same file.

5. You will be prompted with various text like the ones shown in the photo, this is how to read them:

This is for regular cards
Ex: Red 5 row-1-col-5.jpg

    Red -> the card color
    5 -> the card number
    row-1-col-5.jpg -> the card image file from its location that wil be useful when we deal with GUI step.
    **Warning** -> sometimes you can see the card twice, that is okay, there should be an extra keyword
                   in the image file name which states that it is a copy.
	           Ex: Red 5 row-1-col-5 - Copy.jpg

This is for specialty cards
There are numerous specialty cards that we are dealing with in Uno. They are listed below:

*AddTwo -> adds two more cards to the opponent regardless
*Skip -> skips the opponent regardless
*Reverse -> does the same as the skip function for the time being

Ex: Green -1 AddTwo row-5-col-3.jpg

    Green -> card color
    -1 -> hints that the card is a specialty
    AddTwo -> the card specialty
    row-5-col-3.jpg -> card image file



There are the other cards as well.

**WildCard -> Player can decide what the new card color is (not the number)
**WildCard-AddFour -> Player can decide what the new card color is (not the number) and also add 4 cards to the opponent.

Ex: Any -1 AddFour row-6-col-3.jpg

    Any -> specifies that it is a wildcard type
    -1 -> specifies that it belongs in the specialty category
    AddFour -> card specialty
    row-6-col-3.jpg -> card image file



6. Now on the client side, you can copy a line and paste it in the area at the bottom. Same goes for the server side of the game.

7. You can draw a card when it is your turn by simply typing up "draw"

8. Also for specialty wild cards, you have to pick a color so when that happens, you can type in "red", or "yellow", or etc.