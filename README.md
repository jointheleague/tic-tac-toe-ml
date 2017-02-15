# Tic-Tac-Toe-ML

A platform for machine learning based around the simple game of tic-tac-toe, our project allows you to train, test, and play against many differing Brains. Even develop your own if that's what you fancy!

## Installation

### User

To install for use without development, simply download our public release on GitHub! Un-zip the archive and use the executable ```.jar``` file.

### Developer

To install and build on your desktop device for development, simply clone our project on GitHub!
At this point in time, all libraries, such as our rendering library, are included in the "/lib" directory. 

*Note : You may need to add ```awtutils.jar```, located in the "/lib" directory to your Java Build Path in your IDE before running.*


## Developing Your Own Custom AI
To develop your own custom AI for the TTTML framework, you will need to complete four steps :
<ul>
<li> Define a Base Implementation
<li> Create an Implementation of the Brain Interface
<li> Write your own Simulation Controller
<li> Package your code for deployment
</ul>


*Let's get started...*


### The Base Implementation


The Base Implementation is essentially the core of how your AI works. For example : one possible base implementation is the Neural Network Implementation. Another might be a Decision Tree Implementation. Think of the base implementation as a bunch of utility classes that are needed for your AI to work...


Because most Base Implementations will be large and span over more than one file, you can store your base implementation classes and other files under the package ``` com.ttt.ai.yourAIname ```.


Other than that, your base implementation can work however you want. If your AI is really small, such as just a random guesser, you may not have an base implementation at all. The base implementation is simply a clean place for you to store classes and other files needed for your AI to work! Next we will learn how to interface between your base implementation and the game board by writing an implementation for the Brain interface.


### The Brain Implementation


The Brain interfaces between your custom base implementation, and the game board. To start, create a class and implement the ```Brain``` interface. This will give you one method, titled, ```getNextMove```. You can store this class in the package of your AI the we created in the previous step, "The Base Implementation."


This method will be called every time it is your AI's turn to make a move on the game board.


Here is it's definition : ```TilePosition getNextMove(Tile[][] tiles)```


You are given a 2D array of type ```Tile```, which is an enum. The Three possible tile types are :


 ```Tile.EMPTY```, ```Tile.X```, and ```Tile.O```
 
 From that information, use classes in your base implementation to determine the next move, 
and return it as a ```Tile Position```.
 
 *For Example,* ```return new TilePosition(Tile.X, 2, 1);```
 
 Once you've filled out the body for the ```getNextMove``` method, you're all done with the Brain!

 
### The Simulation Controller

 
 Your simulation controller will be run before humans start to play against your AI. Use it to have your AI learn and become more "smart." Simulation Controllers will manage the simulation of multiple games while your AI is in the "learning" phase.
 
 Much like the previous two steps, there are few strict rules about how you go about running your simulation, however your simulation controller ```implements SimulationController```, to be compatible with the framework... You can store this class in your AI's package.
 
 The last thing you need to do to finish the Simulation Controller is to fill in the body of the method ```getAI(Tile tile)```, which is inherited from the superinterface, ```SimulationController```.
 
 The simulation controller can work however you like, however, it must return the finished ```Brain``` object when it's done. This finished ```Brain``` will be used to play against a human.
 
 *Here are a few things you may need to know whilst writing your simulation controller...*
 
 Define a new ```Player``` by executing ```Player myPlayer = new Player(String label, Tile startingTile, Brain ai)```,
 where the ```ai``` argument is an instance of the Brain you wish to run. It is of type Brain.
 
 *Remember : Your Brain interfaces directly with your base implementation, so no need to tell the player object which base implementation you are going to use!*
 
 To define a new game, use ```GameController myGC = new GameController(Player a, Player b)```
 Start the game by calling ```myGC.playGame()```, which will return the winner as an object of the type ```Player```. 
 
  *Remember : When finished with the simulation, return the Brain object that you want to play against humans on the live game board!*

### Packaging And Deploying

At this point you should have all the implementation done for your AI. Now it’s time to get it ready for deployment!

In your preferred IDE, build a ```.jar``` with only the files in your AI package. For example, in Eclipse, ```File->Export->Java->Jar``` will bring up the ```.jar``` build wizard.
Simply de-select all the files that are not the package encompassing your AI.

Once you ensure that only these files are included, build your ```.jar```

You can now run the base ```Tic-Tac-Toe-ML``` application and select ```Import``` from the Main Menu. Navigate to your AI's built ```.jar``` file and test it out!

You're all set!
Good Luck, Have Fun!
In the next section we will go over installing pre-existing AIs.

### Installing AI's

As of now we do not have a central server setup to host user created AI's. If you want to test someone's AI, and have found a link to the ```.jar``` file, this section will walk you through the simple process
of installing an AI.

See the section, "Installation," above to get information on getting your runtime setup.

You can then run ```TicTacToe-ML.jar``` and select the AI you wish to load from the "Import" button. 

*Note : If you find the application running out of RAM, you can run the jar with this launch argument 	```-Xmx 3GB```*	




