### Skavtek v2.0
## Osnovni podatki
# Člani skupine
Št. skupine: 01
Peter Rovtar
Peter Šavron
# Github povezava

## Kratek opis
Oba sva skavtska voditelja. Pri izbiri teme smo pomislili na potrebe skavtske organizacije. Cilj projekta je sestaviti mikrostoritve, ki se bodo integrirale z mobilno aplikacijo, ki olajša administrativno upravljanje srečanj preko beleženja prisotnosti članov ter pomaga pri iskanju terminov preko mikrostoritve, ki omogoča članom izbrati termine, ko so na razpolago.

# Ogrodje in razvojno okolje
Uporabljali bomo IDE Visual Studio Code.
Naš build tool bo MAven.
Ogrodje za mikrostoritve bo KumuluzEE.
Za vsebnike bomo uporabljali tehnologijo Docker.
Mikrostoritve bomo namestili na Kubernetes gručo.
Frontend bomo razviali v Node.js-u.
Uporabljali bomo Amazon Azure cloud.
Za podatkovno bazo bomo uporabljali MySQL bazo.

# Shema arhitekture
GUI interface

API
Poslovna logika
DB

## Mikrostoritve
# 1 - Uporabniki
Funkcionalnosti:
* Registracija uporabnikov
* Vpis v storitev
* Dostop do podatkov drugih uporabnikov
* Delitev uporabnikov v delavne skupine


# 2 - Člani vej
* Dodajanje članov veje, ki niso uporbniki, katerim beležiš atribute
* Dostop do podatkov o članih

# 3 - Beleženje prisotnosti
* Vpisovanje prisotnosti članov na srečanjih

# 4 - Srečanja - registrirane uporabnike registrirati v gruče
* Omogoča, da ustvariš novo srečanje
* Udeležencem srečanja komunicira podatke o srečanju
* Posodobi razpoložljivost udeležencev srečanja
* Udeleženec potrdi svojo prisotnost na srečanju

# 5 - Izbiranje razpoložljivosti za srečanja
* Vsak uporabnik posreduje svoje proste termine
* Posodabljanje izbire prostih terminov
* Dostop do podatkov

# 6 - Admin dashboard
* Ustvarjanje in urejanje skupin
* Pregled in upravljanje z uporabniki
* Pregled in upravljanje s srečanji 