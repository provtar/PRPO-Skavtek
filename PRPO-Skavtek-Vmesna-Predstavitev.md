# Skavtek v2.0

## Osnovni podatki

### Člani skupine

Št. skupine: 01
Peter Rovtar
Peter Šavron

### Github povezava

<https://github.com/provtar/PRPO-Skavtek>

## Kratek opis

Oba sva skavtska voditelja. Pri izbiri teme smo pomislili na potrebe skavtske organizacije. Cilj projekta je sestaviti mikrostoritve, ki se bodo integrirale z mobilno aplikacijo, ki olajša administrativno upravljanje srečanj preko beleženja prisotnosti članov na srečanjih ter pomaga pri iskanju terminov preko mikrostoritve, ki omogoča članom izbrati termine, ko so na razpolago.

## Ogrodje in razvojno okolje

Uporabljali bomo IDE Visual Studio Code.
Naš build tool bo Maven.
Ogrodje za mikrostoritve bo KumuluzEE.
Za vsebnike bomo uporabljali tehnologijo Docker.
Mikrostoritve bomo namestili na Kubernetes gručo.
Frontend bomo razviali v Node.js-u.
Uporabljali bomo Amazon Azure cloud.
Za podatkovno bazo bomo uporabljali MySQL bazo.

## Mikrostoritve

### 1 - Upravljanje članov

* Registracija uporabnikov
* Posodabljenje podatkov in pravic uporabnikov
* Vpis v storitev
* Dostop do podatkov drugih uporabnikov

### 2 - Delovne skupine

* Dodeljevanje skupin članom
* Posodabljanje skupin
* Dostop do podatkov o skupini

### 3 - Beleženje prisotnosti

* Pridobivanje prisotnosti clanov na enem srečanju
* Pridobivanje prisotnosti člana skozi časovno obdobje
* Priodobivanje prisotnosti za vsa srečanje skupine (v časovnem intervalu)
* Pridobivanje statistike za skupino (kolkokrat vsak prisoten odsoten)
* Dodatne analize po potrebi
* Dodajanje prisotnosti na srečanju (trenutno za vse, se da status ni bilo namenjeno njemu - siv)
* Posodabljanje posameznih prisotnosti
* Brisanje prisotnosti(za srečanje)
* Vpisovanje prisotnosti članov na srečanjih

### 4 - Srečanja

* Omogoča, da ustvariš novo srečanje
* Udeležencem srečanja komunicira podatke o srečanju
* Posodobi razpoložljivost udeležencev srečanja
* Udeleženec potrdi svojo prisotnost na srečanju

### 5 - Izbiranje razpoložljivosti za srečanja

* Vsak uporabnik posreduje svoje proste termine
* Posodabljanje izbire prostih terminov
* Dostop do podatkov

### 6 - Admin dashboard

* Ustvarjanje in urejanje skupin
* Pregled in upravljanje z uporabniki
* Pregled in upravljanje s srečanji

## OPIS STANJA PROJEKTA

Napisal bom imena postavk iz navodil za projekt, ki jih izpolnjujeva

* Repozitorij
* Razvojno okolje in projekt
* REST
* Vsebniki
* Dokumentacija (opisan zagon aplikacije v README.md)
* Podatkovna baza
