##### Create a report (REPORT.md), based on your design document, containing important decisions that you’ve made, e.g. where you changed your mind during the past weeks. This is how you show the reviewer that you actually understand what you have done.

### Summary
#### Start with a short description of your application (like in the README.md, but very short, including a single screen shot).

Deze app is een "digitalisatie" van het éénpersoons puzzel bordspel "Treasure Quest" van ThinkFun. Het is gemaakt in de bedoeling dat Thinkfun's spel niet zou worden vergeten, maar ook in deze tijd nog vollop gespeeld kan en zal worden.

De app bevat de originele 40 levels. Daarnaast wordt de speler, anders dan in het echte spel, uitgedaagd om de puzzels op te lossen in zo min mogelijk stappen door middel van high scores, en is het mogelijk om zelf levels te maken en vervolgens te spelen.

In het spel zelf is het de bedoeling om schatten / 'Treasure' / 'Loot' naar de uitgang te duwen. Dit doe je doormiddel van het besturen van een robot. Er liggen ook diverse obstakels in de weg, die de robot ook weg kan duwen, maar enkel in 1 richting. Logisch denken is nodig om de duwvolgorde te bepalen, en alle Loot naar de uitgang te duwen.

(SCREENSHOT)

### Technical Design
#### Clearly describe the technical design: how is the functionality implemented in your code? This should be like your DESIGN.md but updated to reflect the final application. First, give a high level overview, which helps us navigate and understand the total of your code (which components are there?). Second, go into detail, and describe the modules/classes (apps) files/functions (data) and how they relate.

Natuurlijk is er een class per activity gebruikt, die de functionaliteit van zijn bijbehorende activity regelt. Daarnaast bevat het android project de zogehete GameCore class, een module bestaande uit 4 classes. deze 4 classes kunnen gezien worden als de "inner workings", de core van het spel.   


De hoeveelheid functies is zo omvangrijk, dat het moeilijk te beschrijven is als geheel.


### challenges
##### Clearly describe challenges that your have met during development. Document all important changes that your have made with regard to your design document (from the PROCESS.md). Here, we can see how much you have learned in the past month.

Je zou denken dat het spel zelf het lastigste deel van de app zou zijn. In tegendeel, door het probleem te isoleren, en de eerste week puur en alleen het spel te maken

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

er is wel gekeken om het spel zelf "leuker" te maken, door de toevoeging van high scores. Ikzelf merkte dat sommige levels makkelijk waren als je jezelf veel stappen gaf om het op te lossen. Het spel wordt dus uitdagender door deze ingreep. Ook krijgt het spel replayability, je kan terugkeren naar eerder gewonnen levels om ze op drie sterren te krijgen.  
