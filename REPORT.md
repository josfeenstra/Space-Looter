# Final Report

### Summary

Deze app is een "digitalisatie" van het éénpersoons puzzel bordspel "Treasure Quest" van ThinkFun. Het is gemaakt in de bedoeling dat Thinkfun's spel niet zou worden vergeten, maar ook in deze tijd nog vollop gespeeld kan en zal worden.

De app bevat de originele 40 levels. Daarnaast wordt de speler, anders dan in het echte spel, uitgedaagd om de puzzels op te lossen in zo min mogelijk stappen door middel van high scores, en is het mogelijk om zelf levels te maken en vervolgens te spelen.

In het spel zelf is het de bedoeling om schatten / 'Treasure' / 'Loot' naar de uitgang te duwen. Dit doe je doormiddel van het besturen van een robot. Er liggen ook diverse obstakels in de weg, die de robot ook weg kan duwen, maar enkel in 1 richting. Logisch denken is nodig om de duwvolgorde te bepalen, en alle Loot naar de uitgang te duwen.

<img src="doc/documentation/phase%20final%20screenshots/Menu.png" alt="Drawing" width="220" height="380"/><img src="doc/documentation/phase%20final%20screenshots/device-2018-02-01-152404.png" alt="Drawing" width="220" height="380"/><img src="doc/documentation/phase%20final%20screenshots/Game.png" alt="Drawing" width="220" height="380"/><img src="doc/documentation/phase%20final%20screenshots/Create.png" alt="Drawing" width="220" height="380"/>

(meerdere screenshots, om de samenhang met het orginele design te tonen)

## Technical Design

![design document](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/documentation/design%20document.png)

De structuur van de app spreekt redelijk voor zich, en is in grote lijnen hetzelfde als in het design document. Vanuit het hoofdmenu kan er via het submenu, en de subverdeling daarvan, een level worden gekozen. als een level voltooid is, kan men kiezen om door te gaan naar de volgende, het level overnieuw te spelen, of terug te gaan naar het submenu. Afgezien van hoe de classes zijn uitgelegd, is alle functionaliteit die in het design document bescheven staat, toegepast. 

Een aparte AlertDialog voor de settingsknop in het hoofdmenu is weggelaten, aangezien het bleek, dat alleen "reset progress" een gewilde setting is die de speler nodig heeft. De "view progress" functionaliteit is vervangen door meer informatie te geven in het Submenu. 

### toevoeging: Progress en Reward 

Toen de basisfunctionaliteit van de game overeind stond, ontbraken voor mijn gevoel een aantal elementen. Ik merkte bijvoorbeeld dat sommige levels makkelijker waren als je jezelf veel stappen gaf om het op te lossen. Ook dat de speler alle levels in 1 keer kon spelen voelde overweldigend, en het voltooien van een level voelde niet als een accomplishment. 

Ik besloot daarom om Progress en Reward toe te voegen aan de app. De speler kan nu het eerste level van de 4 catagorieën spelen, en de rest 1 voor 1 vrijspelen. De Reward heb ik toegevoegd door middel van high scores. De speler krijgt nu één, twee of drie sterren, gebaseerd op de prestatie. Dit geeft tevens het spel meer Replayability, je kan terugkeren naar eerder gewonnen levels om ze op drie sterren te krijgen.  

### toevoeging: Custom levels 

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
    
Ook moesten de 40 levels van Treasure Quest op een bepaalde manier opgeslagen worden, om ze in te laden in de GameCore. Ik koos voor csv, aangezien het op deze wijze eenvoudig in te voeren was. Het had een veel groter probleem kunnen zijn, als ik de data op de fisieke kaartjes handmatig had moeten overzetten in bijvoorbeeld een json met coordinaten. 

Wat ook fijn is van de csv, is dat deze in zn geheel eenvoudig om te zetten is als string. Ik gebruik deze strings aan data daarom ook als geschiedenis. In een grote array van strings worden alle voorgaande 'boardstates' opgeslagen, die vervolgens teruggeroepen kunnen worden om "terug in de tijd" te gaan.  

### functies 

De hoeveelheid functies is zo omvangrijk, dat het moeilijk te beschrijven is als geheel. Voor de volledige technische werking van de app verwijs ik graag naar de pseudocode in de headers van de 5 java files, die het complete process van de app globaal doorlopen. Als een Highlight, is de pseudo code & functionaliteit van de GameCore hieronder getoond. \

    Gamecore 

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

Het schrijven van deze app ging gepaard met ontelbare kleine ontwerpproblemen en -oplossingen, en heel veel tweaken. Wederom teveel om op te noemen. Het logboek beschrijft wel veel van de gemaakte tussenstappen en kleine slimmigheden. Graag verwijs ik hiernaar als je meer wilt weten van mijn specifieke handelingen.  

Je zou denken dat het spel zelf het lastigste deel van de app zou zijn. Dit bleek redelijk mee te vallen. Door het probleem te isoleren, en de eerste week puur en alleen de core het spel te maken, was het spel zelf een niet al te intimiderende opdracht. De grote "aha" momenten kwamen van het besluit om elke tile een Tile object te maken, en deze objecten niet van coordinaat te laten veranderen, maar hun type te laten aanpassen. De in Programmeren 1 geleerde recursie fuctionaliteit is zelfs toegepast. Als de speler bijvoorbeeld een blokje duwt, wordt het "kan ik mezelf naar links verpaatsen" vraagstuk van de speler recursief doorgegeven naar het blokje, aangezien deze ook moet checken of hij naar links kan bewegen  

Het was ook nog even puzzelen om de csv's goed in te laden en manipuleren. Gelukkig hebben we in Programmeren 1 veel te maken gehad met de manipulatie van strings en het laden van files, dus hier kwam ik uiteindelijk wel uit. Nu ik het zeg, de opdrachten van Programmeren sloten eigelijk perfect aan op deze app. Het spel kan ook gezien worden als een variant op de Game of Fifteen.

Gedurende het schrijven van deze app, ben ik ook steeds confortabeler geworden met het stoeien met xml-gerelateerde elementen zoals gridViews en spinners. Toch ontkwan ik er niet aan om veel te worsterelen met hoe android bepaalde elementen visualiseerde. De Gridview van het speelboard bijvoorbeeld liet zich niet makkelijk manipuleren. Helaas kunnen dit soort problemen niet opgelost worden met bepaalde hogere wijsheid, het is trial and error. Ik merkte wel steeds beter te kunnen raden wat Android van mij wilde, ongetwijfeld een resultaat van alle gemaakte uren met Android Studio. 

Het was misschien wel het lastigst om van te voren aan te geven hoe de code van de app eruit zou komen te zien. De uiteindelijk geschreven code is een accumulatie van design-oplossingen gemaakt terwijl de code geschreven wordt. Van tevoren vastleggen hoe de code er eruit komt de zien is in een ontwerpproces is daarom niet iets wat ik graag doe. Het is wel zo dat doormiddel van schetsen je uiteindelijk tot een ontwerp kan komen. Toch werk ik met het schrijven van code het liefst direct naar een bepaald resultaat toe, dan dat ik eerst de code "schets". Dit Onderdeel kon dus beter.

## changes from initial design document

Bij technical design zijn de verschillende veranderingen al aangegeven en toegeligd. Het zijn voornamelijk toevoegingen, geen veranderingen, dus vereisen geen verdere toelichting. 

Wel licht ik nog graag toe waarom de gebruikte classes van de GameCore totaal niet lijken op de voorgestelde elementen van het design document. Ik heb bij het schrijven van de classes het design document er namelijk niet meer bijgepakt, of ze gebruikt als referentie. Ik heb voornamelijk de "advanced sketch" gebruikt als een Roadmap van hoe de app er uiteindelijk uit zou moeten zien, en dit was genoeg om het process richting te geven. De classes van het designdocument zijn dus niets anders dan een ruwe schatting. 

Er is niet meer gekeken naar online functionaliteit. Dit kan wellicht in mijn eigen tijd nog uitgewerkt worden, maar het had naar mijn mening geen functionele noodzaak, anders dan dat men wellicht iets wil doen met alle User Created Levels. 

Ik ben ook niet meer toegekomen aan het verbeteren van de knoppen / UI in-game. Deze zweven nu als vierkantjes, boven de afbeeldingen daadwerkelijk bedoelt als interface. Android wordt niet heel erg blij van het idee van driehoekige knoppen, dus ik moest ergens de knoop doorhakken. Ook weet ik dat ik deze 'Boxes' had kunnen laten verwijnen, maar ik wil er zeker van zijn dat de knoppen bestaan, en de user enige feedback geven dat hij/zij een knop heeft ingedrukt. 

Daarover gesproken, Het design document speekt ook van wat uitspraken rond Design principles. Deze heb ik gedeeltelijk toe weten te passen. De sprites bijvoorbeeld, waren eerste 256 x 256 pixels, wat het spel trager en groter maakte. Daarom zijn de sprites nu meer Low-Res. Andere dingen zijn niet toegepast vanwege een gebrek aan tijd. Sommige schermen hebben niet dezelfde layout als de rest van de app, De knoppen van het Create-scherm zijn nog basic, en nergens in de app worden geluidjes gebruikt voor feedback, terwijl de GameCore dit wel ondersteund (hij stuurt Log.d() berichtjes met de gewenste geluidseffecten). Toch zijn deze features niet van levensbelang, en de app heeft ze niet nodig om bruikbaar of "leuk" te zijn.   

## Closure 

Ten slotte wil ik Martijn bedanken, sinds hij het toestond dat ik geen API hoefde te gebruiken. De tijd die ik niet hoefde te besteden in het zoeken en managen van een api, heb ik kunnen besteden aan het verder begrijpen van de mogelijkheden van Android Studio, en dit is de rede dat De app nu (naar eigen zeggen) in zo'n compleet stadium ingeleverd kon worden. 



