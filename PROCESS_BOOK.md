# process book
Jos Feenstra

Architecture Student TU Delft

Software Developer Student UvA

student nr. 4465768

## LOG WEEK 2

### maandag 15 jan
Hoewel ik ziek thuis ben, heb ik stevig voortgang geboekt. In de ochtend heb ik de laatste paar problemen met de prototype game kunnen oplossen, waardoor ik dit hoofdstuk van het project kon afsluiten. In plaats van dat ik de objecten van coordinaat laat wisselen, wissel ik enkel het type om van de twee objecten, terwijl ze dus op dezelfde coordinaten blijven staan. Deze conclusie leidde tot het kunnen herschrijven van veel code, waardoor het nu een stuk duidelijker en lichter werkt.

In de middag vooral geworsteld met de stommigheden van android studio. Waarom worden afbeeldingen één voor één ingeladen, waarom moeten er 30 verschillende type afbeeldingen worden aangemaakt per toevoeging? Het spel zelf was zo gemaakt, maar dit soort kleine hickups gaan waarschijnlijk zon 90% van de tijd in beslag nemen. Achja, morgen weer een dag.   

Ik gok wel dat mijn plannen voor deze week iets te ambitieus zijn, maar ik zet graag hoog in. We zullen zien waar het schip strandt.

### dinsdag 16 jan
Stuck on the little things. Opnieuw android studio project aangemaakt. Probeer classes op correcte manier in te laden, en de folder aan csv's op te roepen die elders opgeslagen staan, weinig succes. Misschien moet ik de csv's omschrijven tot 1 Json, en die ergens hardcoden. Dan moet er eerst een programma geschreven worden om buiten android studio om de 40 levels om te zetten in 1 json.

### woensdag 17 jan
Ok, de de plannen voor deze week waren dus niet té ambtieus. Vandaag is veel vooruitgang geboekt. De game zelf is nu volledig speelbaar, met alle gewilde functionaliteit en een enigzins correcte weergave (zie screenshots). De csv's lezen bleek geen probleem te zijn, de CSV lees class moest alleen omgebouwd worden. Deze werkt prima buiten android studio, maar het is logisch dat je dingen als "huidige werkfolder" niet op kan roepen in android studio. Na wat googelen bleek het gewoon mogelijk te zijn om de csv's in een raw folder de plaatsen, en deze vervolgens in de res folder neer te zetten.

Alle code van het buiten android studio om gebouwde prototype zijn ingeladen als de GameCore class. Ik hou did voorlopig graag centraal om duidelijk onderscheid te maken tussen de java files met een corresponderende activity, en de ondersteunende java files.

Ook is het gelukt alle sprites (afbeeldingen) in een gridview te plaatsen. de GameCore zend keurig een array van indexen uit (getallen 0 tm 7), waar de custom base adapter vervolgens raad mee weet, en de correcte sprite mee kiest. Wel moet er nog gewerkt worden aan de correcte grootte en positionering van deze afbeeldingen, op dit moment stretchen of krimpen de afbeeldingen op semi-onverklaarbare wijze.

Verder werken de knoppen, kan het board "teruggespoeld" worden indien een foute move is gemaakt, en wordt bij het voltooien van het spel correct gereageerd. Wat nu nog gedaan moet worden is vooral in de trend van polish (mooi maken, user feedback, settings), en de game waterdicht maken.

### donderdag 18 jan

"Donderdag middag / vrijdag ochtend zal de game in app vorm volledig speelbaar zijn, met werkende level select & progress.
". Dit melde ik maandag, en dit is gelukt. (zie screenshots). Er zitten nog ogen en haken aan de app, maar in principe is alle basiscontent af. De app bevat nu netjes een hoofd- en submenu, waarmee alle 40 levels bereikt kunnen worden. Er is gewerkt met dynamische popup listviews, en deze zijn zo min mogelijk gehardcode. Alleen de "range" van levels zijn geharcode, zoals "11 - 20". Level id's worden opgevraagd met GetInstance. Wel is dit dus afhankelijk van de namen van de csv files, dus er moet op gelet worden dat deze niet worden veranderd. Het zorgt er wel voor dat het updaten van de levels erg makkelijk is. Als we nu besluiten om 5 extra expert levels toe te voegen, hoef ik alleen get getal 40 in 45 te veranderen, en de levels 41 tot 45 toevoegen aan de raw folder.

Het viel me op dat het bekende probleem van het schermm roteren het spel in de war brengt. Het straight up reset. Ik hou gelukkig alle data goed bij, maar de GameCore class moet dus nog wel slim gaan reageren op deze veranderingen met wat OnRestore() acties.

### vrijdag 19 jan

Vandaag wat extra UI functionaliteit toegevoegd. De "terug" knop is eindelijk goed gewired naar het vorige scherm, Er is een mooi level complete scherm met opties, en de titel van het level wordt nu in-game weergegeven.

Niet erg veel nuttige feedback gekregen van de groep.

De navigatie kan beter / meer secure. Ik weet niet of de methode die ik nu gebruik de beste zijn. Ook zijn er functies ge-copy-paste-d, en dat mag natuurlijk niet. ik kan het methods maken van een algemene class, maar ik weet niet wat de netste manier is om dit volgens java - conventies te doen. Not pressing matters tho, in de loop van komende week zal ik dit vragen.

huidige vragen:
vraag: layout.weight=1
       wat doet dit?

vraag: tips over datastructuur / waar zet ik vaak gebruikte functies die ik in meedere classes gebruik?

vraag: is de navigatiestructuur die ik nu gebruik correct / foolproof?

### EXTRA: TOELICHTING PROTOTYPE PHASE

Gezien de afwijkende nature van deze app in vergelijking met andere, heb ik het onderdeel "prototype" niet opgevat als het framework van de app maken. Daarentegen is voor deze week het daadwerkelijk prototype van het spel gemaakt (zie screenshots). Dit is de core van de app, waar alle andere functies aan ondergeschikt zijn, zoals navigatie. 

Het is nu mogelijk om de speler, 'O', met de pijltoetsen te verplaatsen over het board, en bepaalde blokken te verschuiven. Het spelboard kan geladen en opgeslagen worden met behulp van csv's. 

Functies zoals een history om stappen terug te zetten, of een win condition, zijn nog niet geimplementeerd.
Door "datatest" te runnen, gelocaliseerd in de folder scripts, kan dit prototype gespeeld worden. Het is wel van belang dat de Path naar het csv bestand veranderd wordt naar zijn huidige locatie. 

EDIT: de complete functionaliteit van het prototype spel zijn nu wel geimplementeerd, het is ook niet meer nodig om manual de path te veranderen. De prototype folder bevat dit complete script. To run: >java prototype

### screenshots 
![afb1](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/documentation/cmd1.PNG)![afb2](https://raw.githubusercontent.com/josfeenstra/Space-Looter/master/doc/documentation/cmd2PNG.PNG)

## LOG WEEK 3

### maandag 15 jan

Het begint een soort patroon te worden om de week te beginnen met buggs die me vasthouden. Ditmaal gaat het over het inladen van fonts, afbeeldingen en andere mooimakers. Android studio is daar niet de makkelijkste in.

Wat wel gelukt is, is progress. Ik kwam spontaan op het idee om de levels 'unlockable' te maken, zodat het leuker wordt om vooruitgang te boeken in het spel. De progress wordt nu keurig opgeslagen als preferences, een boolean Completion, en een integer number of steps. Dat laatste ga ik morgen uitvogelen.

### dinsdag 16 jan

Het is gelukt om het aantal stappen waarin je het level hebt voltooid op te slaan, en te tonen bij de level select. Dit wil ik uiteindelijk om gaan zetten in een 1 tot 5 sterren beoordeling, gebaseerd op de bijgeleverde oplossingen van het fisieke spel. Hier is een begin aan gemaakt, maar het valt niet mee om "aan en uit sterren" keurig naast elkaar op te stellen.

EDIT: oeps, maandag & dinsdag vergeten te committen

### woensdag 17 jan

Het werd eens tijd dat de app een facelift kreeg. Niet alleen voor de leuk, maar als de app er al wat meer "final" uit begint te zien, worden de delen waar nog aan gewerkt moeten worden duidelijker. Ik besloot daarom vandaag te besteden aan het maken van een mooie menu titel, icoon, achtergronden en lettertype / knoppen. Ik ben erg tevreden over het eindresultaat. Het openingsscherm is uitnodigend, en de app begint eindelijk, naast substantie, ook vorm te krijgen.

### donderdag 18 jan

Vandaag heb ik alle features die ik nog graag wilde toevoegen, toegevoegd. Er kunnen nu zelfgemaakte levels gecreerd en gespeeld worden. Het stoeien met de interface van android was uiteindelijk lastiger dan de daadwerkelijke back-end schrijven. Doordat de gamecore een redelijk solide element is, kon ik veel van zijn utilility gebruiken om bijvoorbeeld snel te checken of de user al een uitgang heeft toegevoegd aan het level, en zo niet om tegen hem/haar te schreeuwen.

de interface schiet nog steeds alle kanten op. Komende week kan helemaal in het teken staan van de in-game interface en boardview te fixen.

ALs ik nog wat tijd over heb morgen, zal ik het eerder genoemde "rating system" toepassen.

### vrijdag 19 jan

vanochtend zijn nog wat visualisatie dingen gefixed, en is natuurlijk gepresenteerd. Wederom weinig feedback, I guess thats a good thing. Ik kan mezelf in ieder geval genoeg feedback geven. Bijvoorbeeld, er moet geen aandacht meer gestoken worden in de settings. Ik wilde bijvoorbeeld nog linkshandig / rechtshandig fuctionaliteit toevoegen voor landscape layout, maar daar is het nu te laat voor. Wat nog wel gedaan moet worden:

BELANGRIJKE TOEVOEGINGEN (op relevantie):
- Zorg ervoor dat de custom levels kunnen worden verwijdered. 
- Fix een leuker scoresysteem.

VOOR VOLGENDE WEEK
- HEADERS
- Zorg ervoor dat landscape mode werkt -> dat het board niet gereset wordt door draaien.
- Maak een robuuste interface In-Game -> eerst knoppen, dan board.  
- geluidjes
- Verbeter popup schermen visuals
- verbeter de Create Game visuals

## LOG WEEK 4

### maandag 15 jan

Er ging goed veel mis met het uploaden van de android studio files naar git. ik heb daarom alle files gemigrate naar een andere locatie, en geupload via een nieuwe naam, SpaceLooterNew.

Verder is veel van deze dag in de ban geweest van het afwerken van deze twee todo's:

BELANGRIJKE TOEVOEGINGEN (op relevantie):
- Zorg ervoor dat de custom levels kunnen worden verwijdered. - DONE 
- Fix een leuker scoresysteem. - BUSY

### dinsdag 16 jan

Het scoresysteem is vandaag eindelijk goed geimplementeerd. Het gevecht met xml is officieel begonnen, sinds ik nu de boardview en ingame button interface eindelijk naar me wil laten luisteren. Het is gevaarlijk om dit zo laat te doen, sinds alle bijsluiteres nog gemaakt en geschreven moeten worden, maar ik wil gewoon graag dat ik mijn app af mag noemen. 

VAN GISTER
- Fix een leuker scoresysteem. - DONE

XML GEVECHTEN
- kijk of de interface nog een stapje beter kan 
- kijk of de gridview nog te fixen valt

POLISH 
- verbeter de UI sprites (knoppen ingame)  
- verbeter de boardView sprites 

### woensdag 17 jan

De XML gevechten gaan moeizaam. De oplossingen die ik vind zijn niet per sé logisch, en roepen meer vragen op dan dat ze beantwoorden, maar ik heb mezelf erbij neergelegd dat ik bij dit onderdeel ga focussen op het resultaat, niet de implementatie. Ik waarschuw dus dat de code die gaat over de boardview en interface controlleren dus erg slordig gaat zijn. 

Ik ben er wel aan toegekomen het Game scherm er in het algemeen wat beter uit te laten zien. De sprites zijn zelf gemaakt met Sketchup, het was de enige manier hoe ik weet om makkelijk shading / textures toe te passen, simpele, strakke vormen te tekenen, en te stoeien met perspectief. Het Title Screen Logo is tevens ook zo gecreerd destijds. 

De UI interface is van internet geplukt. Ik moet onthouden dit goed te documenteren in de readme. 

XML GEVECHTEN
- kijk of de interface nog een stapje beter kan BUSY 
- kijk of de gridview nog te fixen valt BUSY

POLISH 
- verbeter de UI sprites (knoppen ingame) DONE 
- verbeter de boardView sprites DONE

### donderdag 18 jan

Het gevecht is gewonnen. XML kan mij niks meer maken, de draak is getemd. De boardview past zich nu correct aan aan de afmetingen van het board. Een 7x7 board heeft dus grotere sprites dan een 13x13 board. Ook de knoppen van de interface dansen minder rond als voorheen als ik switch van schermgrootte, en blijven enigzins in dezelfde structuur. Ik had de keuze deze onzichtbaar te maken, maar hier is niet voor gekozen uiteindelijk. Er is een kans dat op bepaalde mobieltjes de knoppen alsog gekke taferelen uithalen, en ik wil dan graag weten wat deze "onzichtbare" knoppen uitspoken.  

Het is nu ook eindelijk tijd om de bijsluiters te schrijven. Ik schrijf deze een stuk fijner, wetende dat de build nu stabieler en mooier is dan voorheen. 

XML GEVECHTEN
- kijk of de interface nog een stapje beter kan DONE 
- kijk of de gridview nog te fixen valt DONE

BIJSLUITERS
- maak final report
- verbeter readme
- zorg dat aan alle voorwaarden wordt voldaan
### vrijdag 19 jan
