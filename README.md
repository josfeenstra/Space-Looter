# Space-Looter


## Probleemstelling:
Analoge puzzel bordspellen, zoals rush hour, moeten met de tijd meegaan om relevant te blijven. Dit soort spellen van bijvoorbeeld Thinkfun of Smart Games zijn vaak erg goed uitgewerkt, en bevatten (40) goed ontworpen "levels.". Helaas zal hier weinig van gebruikt worden, sinds men niet meer bereid is een plastic bordspel mee te nemen in de trein of auto. Men kan immers honderden spellen installeren op hun mobieltje of tablet. 

Daarnaast verwacht men nu ook meer van een spel of app. Het moet niet uitgespeeld zijn na 40 levels, er moet op een bepaalde manier verder gespeeld kunnen worden, of het spel zal snel weer worden vergeten. 

## Beschrijving:
deze app dient als een digitale port van een analoog puzzel bordspel, de naam en het thema zijn veranderd voor copyright redenen. 

De app bevat de 40 orginele levels, onderverdeelt in 4 catagorieën.

De app houdt je progress bij, en deze progress kan gereset worden. 

Daarnaast verschijnt er elke dag een nieuwe, "Daily puzzle", die de app zal lezen van een door een api verstuurde Json. Van deze zelfde server kan ook de Json voor de oplossing worden aangevraagd, tegen een micro transaction. 

## voorbeeld bord:
Het doel van het spel is om met de HERO (rode cirkel), de LOOT (gele blokken) naar de uitgang te duwen. Dit doe je doormiddel van met de HERO stappen (omhoog, omlaag, links, rechts) te zetten. Bepaalde blokken kunnen niet verplaatst worden (de links rechts blokken), andere kunnen alleen maar in 1 richting verplaatst worden (horizontaal / verticaal)

![boardstate](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/documentation/voorbeeld%20board.png)

## TODO: 
BASIC FUNCTIONALITY
- maak een hoofdmenu met de knoppen: 
-- LEVELS (easy, medium, hard, expert) 
-- DAILY
-- SETTINGS 

BACKEND
- bepaal de opzet van de board-JSON en de answer-JSON
- maak een server die deze json's returned bij het geven van een bepaalde request 

GRAPHICS
- Maak betere sprites voor alle elementen.
- maak animaties bij het schuiven van elementen. 
- verbeter de UI
- verbeter het hoofdmenu





