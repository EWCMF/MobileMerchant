Aflevering til programmering


## Devs ##
<p>Tommy<br>
Marc</p>


# MobileMerchant

<p>En Android app der kan hjælpe dig med at agere som 'merchant'.<br>
Den har tre understøttet sprog: dansk, engelsk og hebraisk.<p>

---

# Features #

## Valuta omregner ##
<p>En simpel valuta omregner med 7 valutaer der kan omregnes mellem.<br>
Via et HTTP request hentes en JSON fil ned fra nettet som har valutakursen for den valuta du vælger at omregne med.<br>
Dataen fra JSON filen gemmes i en database, således at et nyt request ikke behøves med samme valuta, og API'ens anbefaling kan følges.<br>
De gemte data bliver så erstattet hvis de er mere end 24 timer gamle.</p>

<br>

## Calculator ##
<p>En lommeregner til at regne tal med.<br>
Mozilla's Rhino engine bruges til at evaluere den konstrueret string som JavaScript og derved kan f.eks. parantes let håndteres.</p>

<br>

## Gæld til andre / Gæld til dig ##
<p>Navne kan tilføjes til en dropdown og i de navne kan du tilføje genstande med værdi,<br> 
således at du kan holde styr på den gæld der er blandt venner/fjender.<br> 
CRUD er mulig med både personer og genstande.<br> 
Alt dataen er gemt med Room database og ligger lokalt på dit device.</p>

<br>

#### Note om sprogskift ####
<p>At implementere sprogskifte på Android apps er ikke noget som Google anbefaler,<br> 
og forskellige omveje er nødvendige for at få det til at fungere.<br> 
Disse omveje skifter fra version til version så det er ikke sikkert at sprogskifte virker på dit device.<p>

<br>
<br>

#### Ekstra ####

API til valuta omregner:
https://exchangeratesapi.io/

