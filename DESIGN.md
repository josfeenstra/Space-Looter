# design document

![design document](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/documentation/design%20document.png)

## modules, classes and functions
NOTE: the inner workings of these classes in terms of functions cannot be written down in full detail, this is too specific for the current state of the game.

- **class Menu**
  - Corresponding to menu activity.
  - Function: basic navigation by using buttons.

- **class Submenu**
  - Corresponding to submenu activity
  - Function: basic navigation by using buttons .
            buttons change according to progress.

- **class Level select**
  - Corresponding to level select activity .
  - Function: navigation by using a listview.
  - Starts a game activity.

- **class Settings**
  - Corresponding to menu activity.
  - Function: show and change specific data using listviews.

- **class Play level**
  - Class containing all in-game screen elements.  
  - Manages the input and output of the game class.

- **class Game**
  - class containing the core of the game.
  - accepts 6 input "buttons":
    - up, down, left, right, go back, reset 
  - after every input, return if the move succeeded, and the current state of the board
  - keeps track of all previous states of the board, up to a limit, so moves can be undone.

- **class Board**
    - manages the state of the board, and, according to input, if and how the board should change

    - **subclass Tile**
      - childs:
        - Player
        - Wall
        - Treasure
        - Blockh
        - Blockv

## APIs / frameworks / plugins
- If time allows it, Firebase will be used for storing users and their data (see readme).

## Data sources
- Levels are based upon Thinkfun Games's Treasure Quest.
  DISCLAIMER: I do not own the design of these levels, this app is for non-commercial use (study).

## Database tables and fields
- Not applicable for app.
