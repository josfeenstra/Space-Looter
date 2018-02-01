/*


    NOTE VRAAG: CHAR'S EN STRINGS, ik heb nu beunoplossing, maar dat kan beter

    LOGBOEK;
    maandag - woensdag = ontwikkelen van de conceptmatische app
    donderdag = begonnen aan de game "op het droge",
                - het is nu mogelijk om csvs te laden

*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays;

/*
    yiels data shared between classes
*/
class GeneralData {

    // csv characteristic
    public static final String csvSep = ",";
    public static final String levelFolder = "levels";

    // static data directions
    public static final int left  = 4;
    public static final int down  = 2;
    public static final int right = 6;
    public static final int up    = 8;
    public static final int back  = 1;
    public static final int reset = 0;

    // static data object representatives
    static public final char empty    = ' ';
    static public final char wall     = 'X';
    static public final char player   = 'O';
    static public final char treasure = 'T';
    static public final char blockH   = 'H';
    static public final char blockV   = 'V';
    static public final char exit     = 'E';

    // board pieces data
    static public final String[] names      = {"empty", "wall", "player", "treasure", "blockH", "blockV", "exit"};
    static public final char[] symbols      = {empty  , wall  , player  , treasure  , blockH  , blockV  , exit  };
    static public final boolean[] pushables = {false  , false , false   , true      , true    , true    , false };

    // rewrite print statement
    public void print(String string) {
       System.out.println(string);
    }

    // get something's index (i cant believe this cant be done in java my god what a horrible language)
    public int getIndex(char item, char[] list) {
        for (int i = 0; i < list.length; i++) {
            char checkItem = list[i];
            if (item == checkItem) {
                return i;
            }
        }

        // default
        return -1;
    }
}

/*
    holds all csv type operations
*/
class CsvData extends GeneralData{

    // data
    private String userDir;
    private String[] levelFileNames = {"level1", "levelx"};
    public String[] levelPaths;

    // fill data above
    public CsvData() {

        // get the directory to the user
        userDir = System.getProperty("user.dir");

        // // store filenames
        // levelFileNames = new String[2];
        // levelFileNames =

        // make and fill all paths to the levels
        levelPaths = new String[levelFileNames.length];
        for (int i = 0; i < levelFileNames.length; i++) {
            String name = levelFileNames[i];
            levelPaths[i] = userDir + "\\" + levelFolder + "\\" + name + ".csv";
        }
    }

    // use loadfromCSV with the arrording level path
    public String getLevel(int index) {

        // test if index is valid
        if (index < 0 || index >= levelPaths.length) {return null;}

        // if all is good, select and load according level
        return loadFromCSV(levelPaths[index]);
    }

    // read a csv file, write it into a single string
    public String loadFromCSV(String path) {

        String rawLine = "";
        String fineLine = "";
        String boardData = "";

       // try to read the file
       try(BufferedReader br = new BufferedReader(new FileReader(path));) {

           // read a rawline of the file per iteration
           while ((rawLine = br.readLine()) != null) {

               // init a edited line
               fineLine = "";

               // read the line per character
               for (int i = 0; i < rawLine.length(); i++) {
                   char charchar = rawLine.charAt(i);
                   String c = "" + charchar;

                   // detect if a spot is empty, so that ;;a; becomes _;_;a;_
                   if (c.equals(csvSep)) {
                       if (i == 0) {
                           fineLine += empty + c;  // character is at the start of rawline
                       } else if (i == rawLine.length() - 1) {
                           fineLine += c + empty; // character is at the end of rawline
                       } else {
                           char charchar2 = rawLine.charAt(i + 1);
                           String c2 = "" + charchar2;
                           if (c.equals(c2)) {
                               fineLine += c + empty; // character is in the middle, and is followed by ';'
                           } else {
                               fineLine += c;
                           }
                       }
                   } else {

                       // proceed as normal
                       fineLine += c;

                  }

               }

               // add the edited string to boardData
               boardData += fineLine + "\n";

           }
       } catch(IOException tartaarsaus) {
           System.out.println("kaas");
           return null;
       }
       return boardData;
    }

}

/*
    Tile represents all objects which can be placed as a tile
*/
class Tile extends GeneralData {

  // geometry
  public int x;
  public int y;

  // characteristics
  public char type;
  public int index;
  public boolean pushable;

  // init tile
  public Tile(int anX, int anY, char aType) {
      x = anX;
      y = anY;
      changeType(aType);
  }

  /*
      check if the object can be moved.
      return -1: techincal error, codewise
      return 0: cant be moved
      return 1: player makes a step
      return 2: player moves something
      return 3: treasure is pushed outside of the thing
  */
  public int move(int direction, Tile[][] tileset) {
      // make sure in which direction to check
      Tile tileCheck;
      switch(direction) {
          case left:  tileCheck = tileset[x - 1][y]; // left
                      break;
          case down:  tileCheck = tileset[x][y + 1]; // down
                      break;
          case right: tileCheck = tileset[x + 1][y]; // right
                      break;
          case up:    tileCheck = tileset[x][y - 1]; // up
                      break;
          default:    return -1;
      }
      // if i can move, move
      if (tileCheck.type == empty) {

          // switch the type of both blocks
          char temptype = type;
          changeType(tileCheck.type);
          tileCheck.changeType(temptype);

          //
          return 1;
      }
      // else, if i am the player, and the other block is movable, move block
      else if (type == player && tileCheck.pushable) {
          // dont move a block if its against the rules
          if (!(tileCheck.type == blockH && (direction == up   || direction == down  )) &&
              !(tileCheck.type == blockV && (direction == left || direction == right))) {

              // if the block can be move, move it. recurse, rejoice!
              int response = tileCheck.move(direction, tileset);
              if (response > 0) {

                  // switch the type of both blocks
                  char temptype = type;
                  changeType(tileCheck.type);
                  tileCheck.changeType(temptype);

                  return response + 1;
              }
          }
      }
      // if the treasure if pushed in the exit delete the block and move the player
      else if (type == treasure && tileCheck.type == exit) {
          // dont change the other's type, but do change your own
          changeType(empty);
          return 2;
      }

      // if code arrives at this point, move cannot be made
      return 0;
  }

  // changes the type of the Tile, and all subsequential values
  public void changeType(char aType) {
      type = aType;
      index = getIndex(type, symbols);
      pushable = pushables[index];
  }
}

/*
    Board represents the joined effort of all tiles
*/
class Board extends GeneralData {

   // all dem variables
   public Tile[][] tile;
   public char[][] tilec;
   public int height;
   public int width;

   // memory data
   private static final int HISTORY_LIM = 1000;
   private String[] boardHistory;
   public int boardHistoryState;

   // make board
   public Board(int aWidth, int aHeight) {
       width = aWidth;
       height = aHeight;

       // instanciate board
       // tilec = new char[width][height];
       tile = new Tile[width][height];

       // configure history
       boardHistory = new String[HISTORY_LIM];
       boardHistoryState = 0;
   }

   // fill board with data from csv
   public boolean loadFromString(String boardData) {
       // keep track of what 'coordinate' is currently being read.
       int Xread = 0;
       int Yread = 0;

       // get necesairy values
       int boardLength = boardData.length();

       // read the boarddata per character
       for (int i = 0; i < boardLength; i++) {

           // read the current char
           char c = boardData.charAt(i);

           // test if the reader is still within bounds
           if (Xread >= width || Yread >= height) {
               // operation failed
               print("Failed: csvString does not match map coordinates.");
               return false;
           }

           // every time a ';' is read, increment x
           if (c == csvSep.charAt(0)) {
               Xread += 1;
           }

           // every time a '\n' is read, increment y, reset x
           else if (c == '\n') {
               Xread = 0;
               Yread += 1;
           }

           // otherwise assign 'c' to the tile array
           else {
               tile[Xread][Yread] = new Tile(Xread, Yread, c);
           }
       }

       // save the initial state
       boardHistory[boardHistoryState] = saveToString();

       // success
       return true;
   }

   // save the current boardstate to a string
   public String saveToString() {
       // init boardData
       String boardData = "";

       // go trough all objects
       for (int y = 0; y < height; y += 1) {
           // begin string

           for (int x = 0; x < width; x += 1) {
               // add the character
               boardData += "" + tile[x][y].type;

               // add csvseparator, or \n, depending on if we are at the end
               if (x >= width - 1) {
                   boardData += "\n";
               }
               else {
                   boardData += "" + csvSep;
               }

           }
       }
       return boardData;
   }

   // print the state of the board
   public void printState() {
       // whitespace
       System.out.println();

       // whitespace
       System.out.print("\n +");
       for (int x = 0; x < width; x += 1) {System.out.print("---+");}
       System.out.println();

       // get length and width of tile[][]
       for (int y = 0; y < height; y += 1) {


           // tile side
           System.out.print(" | ");

           for (int x = 0; x < width; x += 1) {

               // print data
               System.out.print("" + tile[x][y].type);

               // tile side
               System.out.print(" | ");
           }

           // whitespace
           System.out.print("\n +");
           for (int x = 0; x < width; x += 1) {System.out.print("---+");}
           System.out.println();

       }
   }

   // find an object
   private Tile getObjectByType(char type) {
       // go trough all objects
       for (int y = 0; y < height; y += 1) {
           for (int x = 0; x < width; x += 1) {
               // check if this tile's type is type
               if (tile[x][y].type == type) {
                   return tile[x][y];
               }
           }
       }
       // could find object
       return null;
   }

   /*
        GAMEPLAY METHODS
   */

   // redirect raw game input to other functions of board class
   public int gameInput(int input) {

       // save response
       switch (input) {
           case left:   print("left");
                        return movePlayer(left);
           case down:   print("down");
                        return movePlayer(down);
           case right:  print("right");
                        return movePlayer(right);
           case up:     print("up");
                        return movePlayer(up);
           case back:   print("back");
                        return boardPreviousState(boardHistoryState - 1);
           case reset:  print("reset");
                        return boardPreviousState(0);
       }
       print("invalid input.");
       return -1;

   }

   // "move" input
   public int movePlayer(int direction) {

       // get player object
       Tile plr = getObjectByType(player);

       // quit if the player couldnt be found
       if (plr == null) {return -1;}

       // before moving, save boardstate
       boardHistory[boardHistoryState] = saveToString();

       // try to move player
       int response = plr.move(direction, tile);

       // if a change has occured
       if (response > 0) {
           // add to history
           boardHistoryState += 1;

           // save this new state
           boardHistory[boardHistoryState] = saveToString();
       }

       return response;
   }

   // "back" & "reset" input
   public int boardPreviousState(int aState) {
       // make sure its possible
       if (boardHistoryState <= 0) {
           print("can't go back");
           return 10;
       }

       // set the new boardstate integer, and load its corresponding boardstate
       boardHistoryState = aState;
       loadFromString(boardHistory[boardHistoryState]);
       return 11;
   }

   // test if winconditions are met
   public boolean isGameWon() {
       // if a single piece of treasure cant be found
       if (getObjectByType(treasure) == null) {
           return true;
       }
       return false;
   }
}

/*
    datatest is the control module
*/
class prototype {
   public static void main(String[] args) {

     // construct board class, csv read class & scanner class
     Board b     = new Board(9, 9);
     CsvData csv = new CsvData();
     Scanner sc  = new Scanner(System.in);

     // ask for a level
     b.print("select level (0 of 1):");
     int lvl = sc.nextInt();

     // read board data out of csv
     String aLevel = csv.getLevel(lvl);

     // then load that into the board
     b.loadFromString(aLevel);
     String test = b.saveToString();
     b.print(test + "\n");

     // first test
     b.printState();

     // prompt user for next move
     while(true) {
         int i = sc.nextInt();

         // process input
         int response = b.gameInput(i);

         // sound feedback
         switch(response) {
             case -1:   b.print("error");
                        break;
             case 0:    b.print("*tok*");
                        break;
             case 1:    b.print("*step...*");
                        break;
             case 2:    b.print("*schhuif...*");
                        break;
             case 3:    b.print("*CA-CHING!*");
                        break;
             case 10:   b.print("Wha wha");
                        break;
             case 11:   b.print("Whoooshh");
                        break;

         }

         // show changed state
         b.printState();

         // check if the game is won
         if (b.isGameWon()) {
             b.print("YOU WON THE GAME!!");
             return;
         }

     }

   }

}
