# Space-Looter

<img src="doc/documentation/phase%20final%20screenshots/Game.png" alt="Drawing" width="250" height="400"/><img src="doc/documentation/phase%20final%20screenshots/Game.png" alt="Drawing" width="250" height="400"/><img src="doc/documentation/phase%20final%20screenshots/Game.png" alt="Drawing" width="250" height="400"/><img src="doc/documentation/phase%20final%20screenshots/Game.png" alt="Drawing" width="250" height="400"/>

## Probleemstelling:
Analoge puzzel bordspellen, zoals rush hour, moeten met de tijd meegaan om relevant te blijven. Dit soort spellen van bijvoorbeeld Thinkfun of Smart Games zijn vaak erg goed uitgewerkt, en bevatten (40) goed ontworpen "levels.". Helaas zal hier weinig van gebruikt worden, sinds men niet meer bereid is een plastic bordspel mee te nemen in de trein of auto. Men kan immers honderden spellen installeren op hun mobieltje of tablet. 

Daarnaast verwacht men nu ook meer van een spel of app. Het moet niet uitgespeeld zijn na 40 levels, er moet op een bepaalde manier verder gespeeld kunnen worden, of het spel zal snel weer worden vergeten. 

## Beschrijving:
deze app dient als een digitale port van een analoog puzzel bordspel, de naam en het thema zijn veranderd voor copyright redenen. 

De app bevat de 40 orginele levels, onderverdeelt in 4 catagorieën.

De app houdt je progress bij, en deze progress kan gereset worden. 

## sketch
Structuur van de app
![boardstate](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/schets%20alles.png)
de daily puzzle is geschrapt.

## voorbeeld bord:
Het doel van het spel is om met de HERO (rode cirkel), de LOOT (gele blokken) naar de uitgang te duwen. Dit doe je doormiddel van met de HERO stappen (omhoog, omlaag, links, rechts) te zetten. Bepaalde blokken kunnen niet verplaatst worden, andere kunnen alleen maar in 1 richting verplaatst worden (horizontaal / verticaal)

![boardstate](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/documentation/voorbeeld%20board.png)

## TODO: 
BASIC FUNCTIONALITY
- maak een hoofdmenu met de knoppen: 
-- LEVELS (easy, medium, hard, expert) 
-- SETTINGS 

GRAPHICS
- Maak betere sprites voor alle elementen.
- maak animaties bij het schuiven van elementen. 
- verbeter de UI
- verbeter het hoofdmenu

HET SPEL 
- maak een class datastructuur waarin de functionaliteit van de objecten logisch is.
- 

## BONUS:
Als de tijd het toelaat, of als de app minder complex dan gedacht lijkt te zijn worden (een van de) volgende features toegepast: 
- je progresss wordt gebonden aan een account.  
- een "create your own level" knop in het hoofdmenu. Spelers kunnen zelf een level maken, deze validaten en opslaan. 
  - nieuwe levels worden gebonden aan het account, en spelers kunnen de spellen van anderen zien en spelen

### BetterCodeHub 
[![BCH compliance](https://bettercodehub.com/edge/badge/josfeenstra/Space-Looter?branch=master)](https://bettercodehub.com/)

BCH is niet erg blij met mn prestatie. Terecht, sommige stukken code zijn ontzettend lang, en wat ingewikkeld, met classes in classes. Echter, als ik deze uit elkaar had willen trekken, levert dit bijna net zoveel onduidelijkheid en complicaties op, met de grote hoeveelheid variablelen die dan doorgeschoven moeten worden, functies die niet werken in een static context, variablelen die ik niet zomaal final kan maken, enzovoort. Dat ik een spel heb gemaakt in een context die eigenlijk niet bedoelt is om spellen in te maken, is ook iets waardoor de code soms gecompliceerd is. Ik heb dit gecompenseerd door veel comments toe te voegen. 

Ik heb dus geprobeerd zoveel mogelijk problemen aangekaart door BHC op te lossen, als dit niet al te grote gevolgen hadden voor de structuur van de code. 

BHC geeft mij ook een 3 in plaats van een 5 als ik de files van mijn prototype bijlever. Het prototype bevat precies hetzelfde als de zogehete GameCore in mijn Android project, dus hij wordt boos dat het dubbel is.  

