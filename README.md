# UnoOnlineProject
Computer Network Application (WIP)

## How to play

*This game is currently under development and is in the primary stage*
*We are setting up the network connection and pasing in data*

0. Make sure you have JDK 8 or 11 as it removes the hassle of installing JavaFX separately

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

## Requirement Analysis

### Objective

The goal of our project is to utilize our learnings within the computer network course and create a full stack application of the popular card game Uno. Our objective is to utilize the Java programming language along with its networking libraries to develop our online game. Our initial step is to make sure our application works in a basic form. What this means is that we need to set up the logistics of our application first such as making sure we are creating all necessary objects/classes that we need, we are passing data from server to client and vice versa, and we need to make sure that it follows the logistical requirements of the real work card game Uno. 
	The point of this document is to acquire all functional and non-functional requirements so that we can understand how to establish our product for the user when it is finished. We also plan to draw out user-case diagrams, develop user stories, create a UML diagram, and follow the model-view-controller system architecture model to help us make this application a reality. All of these blueprints will help us plan out our project effectively and efficiently and have it up and ready by the end of the semester.

### Business Process

There is no real business process for this application as it is merely just for a course. However, if this application is deemed technically feasible and appealing to the eye, then we might have plans on releasing this application on an open game library market for retail selling. Though we can face the potential of a copyright infringement, that is when we have to change all factors of our application to make it stray away from the Uno genre so that it can be its own thing. As far as any other practices goes, we aim to make the application COPPA friendly as we plan on adding a chat feature within the application and give terms and conditions before the application is installed on any device/hardware. Users 13 and above will have access to the chat feature while the other younger audience will have that feature disabled for the time being. All legal matters will be laid out again, in the terms and services of the application.

### Minimalistic Version (User Roles and Responsibilities)

We will first focus our attention to the server, which is running this whole fiasco. The server must be initialized at all times. The game server helps us set up the thread to send out info to and from our user. That can be either the objects of each individual or stack of cards, player statistics and their existence, and it updates the database. The server will be a multithreaded server so that we can have different copies/instances of the server running which act as our employees in a sense to our players. 
	Onto the real part, the Uno game itself. There are different variations of Uno that people play on differently but for now we will stick to one variation until we feel as if we can incorporate different variations throughout the software lifecycle. When the game is played, a player can say uno once they have one card, this is automatically done by the game itself so that will be a server thread function and it will send out a message to all other players/clients. Players can draw a card and place a card down the playing stack. What is unique about Uno is that we can incorporate different functions of the game thanks to several different unique card objects. This includes to draw 4, skip the next player, color request, go backwards, etc. So far we hope to get at least 2-3 of these functions down so that the game can be interesting; however, it isn’t impossible. 
	In the end of this minimalistic version, you can expect to see a text based uno game to be in the works as a fully functional game with all of these game features. Once we have a way to make sure all of our data is being sent in from client to server or vice versa, then we can move onto our enhanced version of the project.

### Enhanced Version (User Roles and Requirements)

For our enhanced version, we go over the UI experience of our game. This means that we want to incorporate a model-view-controller design pattern for our application in order to ensure that our game is a functional one. In order to do this, we will utilize an application such as scene builder to help us build our view for our Uno game. Scene builder is a front-end creation application that utilizes creating FXML files format to readily display our needs. It allows us to easily create a UI to our likening without writing all of the code. Communication between view and controller is necessary for this game to work so we would have to write up controller classes for both client and server respectively. We will first have to initialize our controller and create an update method that readily updates every now and then to what has been inputted by the server and/or client.
	Another potential feature that we are planning to implement is WAN connectivity from a server to a client. This means that you can port forward this application and can play with others not in your general LAN vicinity. This will really spark the true experience of creating a true network gaming experience to UNO. 
	Some features listed above will make our game stand out from our minimalistic design but as of our timeline, we hope to get as many of these features as possible so that our game meets the expectations that it well deserves.

### Use Case Diagram

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/UnoCaseDiagramDiagram.png" alt="alt text" width="800">

Description: Here is displayed our use-case diagram which establishes the narrow-down version of our application. Our two main actors are our player (client) and our game server. The host client is an extension of our player and gets more added functionality and the game server thread works in the format of performing a lot more functions than the game server. This version contains functional and non-functional requirements all mixed.

### Functional Requirements

For our Uno game to function as intended, we therefore need to set a basis of functional requirements so that we can ensure that this software will be useful to our clients. Below is a drop down list of various functional requirements for users:

1. A user should be presented with a responsive user interface for navigation and playing.
2. A user can join a game .
3. A user should be able to overall, play the game Uno, with all of its features and of course, be in the style of a game to either win or lose.
4. A user should be able check on their statistics such as how many cards they or the enemy has.

	As we progress through the basis for our user functional requirements, we then move onto the functional requirements for our server side of our application. Below is a drop down list of various functional requirements for the server:
1. Server should always be running 24/7, meaning it is a multithreaded server.
2. The server thread determines how the game is played among the joined user.
3. The server thread must initialize the game once the client are joined in.

	When it comes to creating an online Uno game, it is very important to have first the frontend design done and out of the way, then creating all the logistics of how an Uno game works, and finally, making sure that a valid game can be played from a connection over a network between various users and the server itself. With that said, we move over to the non-functional requirements.

### Non-Functional Requirements

While all the functionalities were listed in the previous section, we moved on to the non-functional requirements that are going to be listed. In it, these requirements are merely not mandatory and are purely not certain to be included in the base game or maybe even focused on during development. It can be in there but the amount of work put towards it won’t be as drastic. For the non-functional requirements for the user, they are listed below.
1. Extra UI assets; when a user wins or loses a game for example, we can have a sprite appear and make it stand out. This also applies to other moves and such but for now we focus on getting the basic UI in the game.
2. Passwords have to be unique and need to contain special characters/change passwords.
3. Set up email verification services.
4. Ability to mute users in chat and report a bug feature.
5. Users can kick players if they are the host.

	Most of these listed are just for quality of life experience if the game does get a good release with all of these features. However, due to time constraints, some features listed above won’t be implemented. The same can go for the server side non functional requirements as listed below:
1. Set a time limit for a waiting lobby when players join the game.
2. Update the database at every second it can get.
3. Check to see if User connects with a cracked client-side application.
4. Update chat history to database.
5. Scan for profanity in the chat.

	Most of what was listed are again for the quality of life for this application and are not all certain to be in the base game. We aim for this game to meet all functional aspects and hope to also include these non-functional requirements if there is extra time before deployment. For now, we focus on getting the basic UI up and running along with a good server to client connection.
	
### Appendices (Extra Topics)

As for what system can run this game, pretty much any hardware can run this game as this is a simple Java application and we plan on not taking up too much resources that can strain the clients hardware. Our first address is to work on the front end of our application so we can utilize all functionality of that end to help us write our back end. The front end again includes the UI and the basics of how the application works in terms of creating Uno objects. The back end of the application will consist of creating the necessary functions for our Uno game to work as intended over a network. Now as mentioned before, our network setup must be logically sound when forwarding information from either server or client and prevents any form of deadlock or other barriers that prevent clients from playing the game. 
	While not drawn out in this document, a UML diagram will be useful to plan out our intentions for our application. This will also include testing of our application. While test cases can be applied here, we want to focus mainly on whether or not both server and client receive what they have to receive and what they have to send out. Testing includes checking to see if the response time between the server and client is up and running so there won’t be any latency issues over the network; this will ensure that the game runs smoothly. 
	If any issues do get reported up and need attention, our main drive will be to focus on that component and debug it to see if we can fix it. All in all, our team has a long way to go and need to get started with the planning phase as soon as possible.


## Application Design

### Minimalistic Version

#### System Objectives 
The objective of this system is to function as a way to play uno over a socket connection. The server starts out and once the client joins in, cards are distributed all thanks to the server for calling in the game class. The game class will act as our protocol class that will speak with our serializable objects, which are the Card Class and the CardDeck Class. It will lay out the functions of the rules of the game, what cards it needs to distribute, what are we sending and receiving, and other various helper functions/methods here and there. What we can achieve by playing Uno is that we are going for the safe approach, which is sending a single card every time through the socket connection. This simple concept allows us to really develop the game at an easy pace since it can be difficult to send multiple objects at a time. Also, we want to have the player draw a card from the drawing pile so we have a function for that as well. So far the base game works and other commands such as special cards are a simple function to install since we can manipulate what we call in.


#### Anticipated Issues 
Some issues that are known to come up when working with this system have to be encountering any sort of deadlock when sending information. One side of the system, such as the server for example, may need to see what card was put down in the main deck and needs to be updated on both ends. One thing that is for sure is to make sure that we are following a pattern of sending and receiving a single card object back and forth. Another issue that can be faced up is the potential for not getting updated values. For example, on the server side, we must know at all times how many cards the other player has and vice versa. In addition to this, we must be able to both see what is on the discard pile at all times. 


#### Alternative Solutions to the issues above
Solutions to the potential problems above, especially the deadlock problem is to simply send out one object at a time and wait for the next object to come back. We have to be clever about this since the server will be handling all the rules. For example, let say we want a new card from the drawing pile, we send the request to the server that will fetch our card for us and it will send it back to us, however, we have to make note that this card is specifically for us and not for the pile so we add in a local variable to see if the card gets added back to us or not. Cards coming in from the server should be either for the pile or for us, one or the other. Also, since we are simply sending out one card per turn, updating all information won’t be a hassle since we are working with this basic method. All in all, our solutions are achievable if we stick with sending out one card at a time and clearing all protocol manners efficiently.


#### Application-Layer Protocols
As for a structure to go with, we will incorporate the transport-layer protocol for our network. Now we move onto the game class, our constructor will be responsible for initialization of the game which sets out to get the cards from the drawing deck to each player; it gives 7 cards to each player. It also sets the discard pile to the first card that isn’t a wild card. The game also checks for rules and regulations to see if the plays that are being played are legit and not phony. The game class will also be responsible for keeping the stored objects of the card decks of the game, so that includes the server, clients, drawing, making sure that the card played after a client or server chooses a color after playing a wild card matches the color chosen, and discard card decks. There should only be one card deck and nothing else. 
Onwards to the Client side, we want to check if the cards that are being sent in are always good and not null, if not null, we play the game. Once we get uno on our side, we send out a winning message card object and from there the server will take care of it. We also have various forms of request that can be made from here. We can request a draw card and the server will fetch us a card and it will be inserted into our pile; it will also update on their end about the changes that were made. 
For the server side, we aim to be the master controller while playing the game with blissful unawareness. The code will contain the instructions for us to play without knowing the players intentions along with it acting as the referee. Every card we get must be looked at with caution as it is our next step to a well throughout output. Like the client side, we too have features of adding and winning the game.


#### Algorithms
The algorithms are merely set to what should be executed in the protocol and Card/CardDeck classes. The Card Class doesn’t inherit any algorithms at all and we should be looking at the CardDeck class since it has more functionality. CardDeck will be responsible for creating a single set of cards, whether empty or to initialize a set of playing cards. The initialize method will use for loops to add in a card into an empty set until it is full and also it will be shuffled at the end. This class will also contain various String arrays that will hold the information of each unique specific attribute of a card. For example, a string array called colors will hold the names of all the unique colors, another string array contains the names of the specialties, etc. These string arrays will be useful for helping us initialize our card objects and also for creating our playing deck. Card decks have the option to be drawn, added to, to be peeked at, to remove at a certain index point, get the size, etc. 


#### Assumptions/Limitations/Restrictions of the system
One main limitation of this system is that we can only send out a card object one at a time. It would be nice to send out multiple cards at a time however, since this is the minimalistic approach, this is the only way to go about it. Another thing that would be considered a limitation is the game class only being restricted to the server. While it is possible for the client to call in the game class, it would disrupt the order of how the game is meant to be played over the socket connection. One last final note would be the lack of multiple players on this system. While not mentioned earlier, this system is aimed towards a single player and another player, maximum 2 players. It would be nice to play the game with multiple players but this is again the minimalistic version of the game.


### Enhanced Version

#### System Objectives 
The objective of this system is to build up from the minimalistic approach and add in more features that make the game more friendly. This version aims to incorporate a GUI in the game so that we can have a nice area to play with, on both server and client side. However, for our GUI to be totally functional and visually appealing, we need to add an update to our card class and give it an image from a file. We can find photos from the internet and get an individual .jpg file for each unique card. Also, we want to add in a label to which displays every action that is taken place, whether it's to exclaim that there is an uno, or there is a win or lose game message, etc. Moreover, while this is being discussed, planning on incorporating a database with our game can be relatively challenging so it might not show up in final production. A database will only be incorporated if time persists. Another thing is to add in other users. While this too is also a difficult feature to add in, because it requires us to rewrite how the overall game, we will only incorporate it if time persists. 


#### Anticipated Issues
One main concern of this project's version is the inconsistency of objects being displayed along with creating a dynamic display for each object. Since our data is being passed in all the time, we are obligated to update it at all times to ensure that the logic of the game stays consistent.  Other issues include not displaying what's going on on the other end of the socket. Let's say we are the client and the server is playing their cards as is us, but we see that their dynamic card view isn’t updating, therefore it is inconsistent. Apart from this, we can look into database design and see anticipated issues that may arise and that could be unsuccessful connections to the database. Issues like these will mess up overall data streams from everyone; however, databases again will probably be an added bonus feature if time persists.


#### Alternative Solutions to the issues above
First things first, we can create a new class to handle all frontend functionality such as what to display based off of the card decks, messages, etc. A solution to the other problem of not displaying other users info would be to call in the game class on the clients end for the sole purpose of to display a replica of the other users cards and nothing else. We can ponder up an algorithm to determine how many cards are on the other end based on single card input. As far as database work goes, a way to solve this problem is to not connect the client or server at all until an established connection to the database is secured.


#### Application-Layer Protocols
Working with GUI and networking can be a tassel in terms of establishing a blueprint on what to create and communicate. A new class would be made as a controller class to handle all frontend inputs to communicate with the backend client-server model class. Any input from here is communicated between the two as we are, in layman's terms, pressing buttons on one end to update the other. A feature like this will really enhance the visual aspect of the game at hand. 


#### Algorithms
There aren’t really any new added algorithms for the enhanced version of the game since we are just incorporating a GUI frontend to this and creating synchronized methods to make sure that if one player has a turn, other players may not play and must wait for the first player to play their turn. However, since we have discussed setting the displacement of other users' cards from the client side, we can go a little further into detail about its algorithm. Well, since we are passing in one object at a time, we can base off the other server cards off that. So if they pass in a card that is different from what we set on the deck, we know that they placed a card down on the deck so therefore we subtract their card mirror view by one. If the card deck remains the same, we know that they added a new card. However, in the game of uno, we know that we have doubles of almost every card (e.g. there can be 2 of the same blue of 0’s) and we need to distinguish between them. Luckily we have another relatively easy way to distinguish them. Remember how every card earlier is assigned a unique image file, we can compare the file names and if they are different yet the same in color and attribute, then we can determine what logical procedure to take it from there. All in all, this is how we would go about playing out our algorithm.


#### Assumptions/Limitations/Restrictions of the system
Some assumptions that can be made about this system would be that we still plan on sending in one information at a time yet when we perform operations such as placing a wild card against my opponent, it will act as if there are several parallel operations being performed yet it is simply just automation from our code that is being enforced. Limitations, and not restricted to the enhanced version, would be that the game can last for a long time. While this doesn’t concern anything specific to networking, it can leave a strain on how long the connection stays put depending on the server-side system if it can handle it. Some restrictions that can be thought about is the amount of cards that can be displayed. In short, we can have a long running game or have terrible luck and end up with a lot of cards. Displaying something like this would really be a lot of work to overcome yet it isn’t impossible. For instance, off the top of our head, we can incorporate a button that is displayed to view another section of the cards in your card deck. 
Due to the time constraints, we did not implement a database, although the database that we would have implemented would let players register and create a profile. 
A restriction that arose was though we created a multiplayer game, it is a game between two people. The GUI that we created was designed only for two players, where a player sees their cards, the discard pile, the draw pile, and the number of cards their opponent has. If the game had more than two people playing it, the GUI would not be able to show the number of cards of the other opponents. For example, we are playing the game and we have two opponents. In our current version, the GUI will show us the number of cards that our opponent has. If we had more players, we would not be able to see the number of cards each player has and the GUI would not be able to handle more than two players. Due to the time constraint, we were not able to implement a GUI for more players.


### UML Diagram

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/UMLUno2.png" alt="alt text" width="800">


## Screenshots

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(810).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(811).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(812).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(813).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(814).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(815).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(816).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(817).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(818).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(819).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(820).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(821).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(822).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(823).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(824).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(825).png" alt="alt text" width="800">

<img src="https://github.com/gdambrosio132/UnoOnlineProject/blob/master/images/Test%20Case%20Photos%20V.2/Screenshot%20(826).png" alt="alt text" width="800">
