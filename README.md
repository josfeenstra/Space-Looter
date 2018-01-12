# Space-Looter

## TOELICHTING PROTOTYPE PHASE

Gezien de afwijkende nature van deze app in vergelijking met andere, heb ik het onderdeel "prototype" niet opgevat als het framework van de app maken. Daarentegen is voor deze week het daadwerkelijk prototype van het spel gemaakt (zie screenshots). Dit is de core van de app, waar alle andere functies aan ondergeschikt zijn, zoals navigatie. 

Het is nu mogelijk om de speler, 'O', met de pijltoetsen te verplaatsen over het board, en bepaalde blokken te verschuiven. Het spelboard kan geladen en opgeslagen worden met behulp van csv's. 

Functies zoals een history om stappen terug te zetten, of een win condition, zijn nog niet geimplementeerd.
Door "datatest" te runnen, gelocaliseerd in de folder scripts, kan dit prototype gespeeld worden. Het is wel van belang dat de Path naar het csv bestand veranderd wordt naar zijn huidige locatie. 

EDIT: de complete functionaliteit van het prototype spel zijn nu wel geimplementeerd, het is ook niet meer nodig om manual de path te veranderen. De prototype folder bevat dit complete script. To run: >java prototype

### screenshots 
![afb1](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/documentation/cmd1.PNG)![afb2](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/documentation/cmd2PNG.PNG)


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





