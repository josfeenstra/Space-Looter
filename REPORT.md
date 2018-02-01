# Final Report

### Summary

Deze app is een "digitalisatie" van het éénpersoons puzzel bordspel "Treasure Quest" van ThinkFun. Het is gemaakt in de bedoeling dat Thinkfun's spel niet zou worden vergeten, maar ook in deze tijd nog vollop gespeeld kan en zal worden.

De app bevat de originele 40 levels. Daarnaast wordt de speler, anders dan in het echte spel, uitgedaagd om de puzzels op te lossen in zo min mogelijk stappen door middel van high scores, en is het mogelijk om zelf levels te maken en vervolgens te spelen.

In het spel zelf is het de bedoeling om schatten / 'Treasure' / 'Loot' naar de uitgang te duwen. Dit doe je doormiddel van het besturen van een robot. Er liggen ook diverse obstakels in de weg, die de robot ook weg kan duwen, maar enkel in 1 richting. Logisch denken is nodig om de duwvolgorde te bepalen, en alle Loot naar de uitgang te duwen.

<img src="doc/documentation/phase%20final%20screenshots/Game.png" alt="Drawing" width="200" height="350"/>

## Technical Design

![design document](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/documentation/design%20document.png)

De structuur van de app spreekt redelijk voor zich, en is in grote lijnen hetzelfde als in het design document. Vanuit het hoofdmenu kan er via het submenu, en de subverdeling daarvan, een level worden gekozen. als een level voltooid is, kan men kiezen om door te gaan naar de volgende, het level overnieuw te spelen, of terug te gaan naar het submenu. Afgezien van hoe de classes zijn uitgelegd, is alle functionaliteit die in het design document bescheven staat, toegepast. 

Een aparte AlertDialog voor de settingsknop in het hoofdmenu is weggelaten, aangezien het bleek, dat alleen "reset progress" een gewilde setting is die de speler nodig heeft. De "view progress" functionaliteit is vervangen door meer informatie te geven in het Submenu. 

### toevoeging: PROGRESS AND REWARD 

Toen de basisfunctionaliteit van de game overeind stond, ontbraken voor mijn gevoel een aantal elementen. Ik merkte bijvoorbeeld dat sommige levels makkelijker waren als je jezelf veel stappen gaf om het op te lossen. Ook dat de speler alle levels in 1 keer kon spelen voelde overweldigend, en het voltooien van een level voelde niet als een accomplishment. 

Ik besloot daarom om PROGRESS en REWARD toe te voegen aan de app. De speler kan nu het eerste level van de 4 catagorieën spelen, en de rest 1 voor 1 vrijspelen. De REWARD heb ik toegevoegd door middel van high scores. De speler krijgt nu één, twee of drie sterren, gebaseerd op de prestatie. Dit geeft tevens het spel meer replayability, je kan terugkeren naar eerder gewonnen levels om ze op drie sterren te krijgen.  

### toevoeging: CUSTOM LEVELS 

Ook wilde ik graag de kracht van de GameCore (zal weldra uitgelegd worden) op een bepaalde manier kunnen tonen. Het accepteerd veel meer formats dan de levels van het orginele spel. Ik besloot om een "Create Game" knop toe te voegen aan het hoofdmenu, gekoppeld aan een nieuwe Create class. Hierin kan een nieuw level worden gemaakt en opgeslagen. Vervolgens heeft het Submenu een knop gekregen waar deze levels vervolgens speelbaar zijn. 

Naast dat het dus de GameCore test en toont, is het zelf ontwerpen van levels een vaak gewilde functionaliteit in games, als uitlaatpunt voor de creativelingen onder ons. 

### classes 

Natuurlijk is er een class per activity gebruikt, die de functionaliteit van zijn bijbehorende activity regelt. De functionaliteit de classes Menu, Submenu, Game en Create bestaan voornamelijk aan het koppelen van specifieke clicklisteners aan buttons, en het vullen van ListViews, GridViews en spinners doormiddel van adapters.

Een belangrijk onderdeel van de technische werking van het spel zelf in de Game en Create class, is de zogehete GameCore; een module bestaande uit 4 classes. deze 4 classes kunnen gezien worden als de "inner workings", die core van het spel managen. De gehele eerste week van dit project is besteed aan het schrijven van deze code, volledig buiten android studio om. Het spel was speelbaar in de command prompt, zie doc & het logboek voor details.    

de classes zijn dus uiteindelijk geïsoleerd geplaatst in het android project, om nadruk te leggen op het feit dat ze een aparte, centrale rol spelen in het geheel. De gehele app dient eigenlijk als niets anders dan een interface voor deze GameCore. 

### data 

    public static final String PREFDATA_NAME = "progress";
    public static final String PREFDATA_HIGHSCORE = "high_score";
    public static final String PREFDATA_UCL = "user_created_levels";

Om deze functionaliteit toe te staan moesten 3 sets aan data lokaal worden opgeslagen: Voortgang, High-Score waarden, en zelf gemaakte levels. Deze separatie is handig zodat 1 van de 3 datasets compleet kan worden verwijderd, zonder dat de rest daar last van heeft. Deze worden in de app opgeslagen met de SharedPreferences functionaliteit. 

    X,X,X,X,X,X,X,X,X
    X,,,,,,,,X
    X,,T,X, ,X,,X,X
    X,,,H, ,H,,,X
    X,,T,,V,H,V,O,E
    X,,,H,,H,,,X
    X,,T,X,,X,,X,X
    X,,,,,,,,X
    X,X,X,X,X,X,X,X,X
    

### functies 

De hoeveelheid functies is zo omvangrijk, dat het moeilijk te beschrijven is als geheel. Voor de volledige technische werking van de app verwijs ik graag naar de pseudocode in de headers van de 5 java files, die het complete process van de app doorlopen. Als een Highlight, is de pseudo code - functionaliteit van de GameCore hieronder getoond. 


    Structure: 4 classes:

     1. GeneralData
        - Contains all static data
        - name of game elements, the characters used to respresent them in csv files, etc
        - 3 other classes are extentions from this class

     2. CsvReader
        - load a csv file from the raw resource folder
        - replace any empty places, like ",,," with spaces " , , ,"
        - return all csv data in 1 string, named gameData

     3. Tile
        - represents a single object on the board which can be placed as a tile (also empty ones).
        - handles events like "can i move left?" and changeType.

     4. Board
        - can be seen as the actual core of the game
        - construction:
            - construct with gameData, gathered from the csv reader
            - get the width and length of the board from gameData
            - make a 2d array of Tile classes, based upon this width and height
        - accepts game input, checks game input (check if move is legal), and update Tile array accordingly
        - Keeps track of history, so undo's can be made
        - show state of Tile array / board with functions printBoardState() and getBoardViewData()

## challenges
##### Clearly describe challenges that your have met during development. Document all important changes that your have made with regard to your design document (from the PROCESS.md). Here, we can see how much you have learned in the past month.

Je zou denken dat het spel zelf het lastigste deel van de app zou zijn. In tegendeel, door het probleem te isoleren, en de eerste week puur en alleen het spel te maken, was het spel

XML, boardview, interface was een drama

listviews goed laten luisteren was lastig

core was makkelijk

csv was makkelijk



### changes from initial design document
##### Defend your decisions by writing an argument of a most a single paragraph. Why was it good to do it different than you thought before? Are there trade-offs for your current solution? In an ideal world, given much more time, would you choose another solution?

Bij het maken van het originele Design document, had ik al opgemerkt niet te kunnen zeggen hoe grote delen van de app eruit zouden komen

navigatie is in grote delen hetzelfde

Ik heb na het schrijven van de classes de design document deze er ook niet meer bijgepakt, of ze gebruikt als referentie. Ik heb voornamelijk de "advanced sketch" gebruikt als een Roadmap van hoe de app er uiteindelijk uit zou moeten zien, en dit was genoeg richting. De uiteindelijk geschreven code is een accumulatie van Design-solutions gemaakt terwijl de code geschreven wordt. Van tevoren vastleggen hoe de code er eruit komt de zien is in een ontwerpproces is daarom niet handig.

er is niet meer gekeken naar online functionaliteit


