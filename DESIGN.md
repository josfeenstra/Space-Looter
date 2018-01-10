# design document 

![design document](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/documentation/design%20document.png)

## modules, classes and functions

- **class Menu** 
  Corresponding to menu activity.
  Function: basic navigation by using buttons.
  
- **class Submenu** 
  Corresponding to submenu activity 
  Function: basic navigation by using buttons .
            buttons change according to progress.
            
- **class Level select** 
  Corresponding to level select activity .
  Function: navigation by using a listview.
            starts a game activity.
            
- **class Settings** 
  Corresponding to menu activity.
  Function: show and change specific data using listviews.
  
  

- **class Play level**
  Class containing all in-game screen elements.  
  Manages the input and output of the game class.

- **class Game**
  class containing the core of the game.
  accepts 6 input "buttons": 
  up | down | left | right | go back | reset 
  
- **class Board**
  Function | Purpose
  --- | ---
  loadFromCSV() | self explanatory 
  
    - **subclass Tile** 
      tile childs:
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
- No other external data sources used.  

## Database tables and fields 
- Not applicable for app. 
