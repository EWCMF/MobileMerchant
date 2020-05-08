Aflevering til programmering


## Devs ##
Tommy
Marc


# MobileMerchant

En Android app der kan hjælpe dig med at agere som 'merchant'.
Den har tre understøttet sprog: dansk, engelsk og hebraisk.


Der er tre kernefeatures:

## Valuta omregner ##
En simpel valuta omregner med 7 valutaer der kan omregnes mellem.
Via et HTTP request hentes en JSON fil ned fra nettet som har valutakursen for den valuta du vælger at omregne med.
Dataen fra JSON filen gemmes i en database, således at et nyt request ikke behøves med samme valuta, og API'ens anbefaling kan følges.
De gemte data bliver så erstattet hvis de er mere end 24 timer gamle.

## Calculator ##
En lommeregner til at regne tal med.
Mozilla's Rhino engine bruges til at evaluere den konstrueret string som JavaScript og derved kan f.eks. parantes let håndteres.

## Gæld til andre / Gæld til dig ##
Navne kan tilføjes til en dropdown og i de navne kan du tilføje genstande med værdi, 
således at du kan holde styr på den gæld der er blandt venner/fjender.
CRUD er mulig med både personer og genstande.
Alt dataen er gemt med Room database og ligger lokalt på dit device.


### Note om sprogskift ###
At implementere sprogskifte på Android apps er ikke noget som Google anbefaler,
og forskellige omveje er nødvendige for at få det til at fungere.
Disse omveje skifter fra version til version så det er ikke sikkert at sprogskifte virker på dit device.


API til valuta omregner:
https://exchangeratesapi.io/

