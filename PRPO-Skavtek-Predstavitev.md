# Skavtek v2.0

## Osnovni podatki

### Člani skupine

Št. skupine: 01
Peter Rovtar
Peter Šavron

### Github povezava

<https://github.com/provtar/PRPO-Skavtek>

## Kratek opis

Oba sva skavtska voditelja. Pri izbiri teme smo pomislili na potrebe skavtske organizacije. Cilj projekta je sestaviti mikrostoritve, ki se bodo integrirale z mobilno aplikacijo, ki olajša administrativno upravljanje srečanj preko beleženja prisotnosti članov ter pomaga pri iskanju terminov preko mikrostoritve, ki omogoča članom izbrati termine, ko so na razpolago.

## Ogrodje in razvojno okolje

Uporabljali bomo IDE Visual Studio Code.
Naš build tool bo Maven.
Ogrodje za mikrostoritve bo KumuluzEE.
Za vsebnike bomo uporabljali tehnologijo Docker.
Mikrostoritve bomo namestili na Kubernetes gručo.
Frontend bomo razviali v Node.js-u.
Uporabljali bomo Amazon Azure cloud.
Za podatkovno bazo bomo uporabljali MySQL bazo.

## Shema arhitekture

![image](https://github.com/user-attachments/assets/e051d697-7713-4a44-b330-c76288e284d5)

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
- Pridobivanje prisotnosti clanov na enem srečanju
- Pridobivanje prisotnosti člana skozi časovno obdobje
- Priodobivanje prisotnosti za vsa srečanje skupine (v časovnem intervalu)
- Pridobivanje statistike za skupino (kolkokrat vsak prisoten odsoten)
- Dodatne analize po potrebi
- Dodajanje prisotnosti na srečanju (trenutno za vse, se da status ni bilo namenjeno njemu - siv)
- Posodabljanje posameznih prisotnosti
- Brisanje prisotnosti(za srečanje)

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

## Primeri uporabe

### Jurij si beleži prisotnost udeležencev skupine Bobri

Jurij si želi na enostaven in preprost način beležiti prisotnost udeležencev na njegovih srečanj in želi do teh podatkov dostopati na več platformah. Ustvaril si bo račun na Skavtku, potem bo ustvaril skupino Bobri, pa dodal v njo člane skupine. Prihodnje srečanje začne z beleženjem prisotnosti. Po pomoti je napisal, da je bil Pavel odsoten, ko se tega zave, popravi zapis in ga označi za prisotnega. Po nekaj časa se Franc zapusti skupino, zato ga Jurij odstrani iz skupine. Konec leta pregleda prisotnosti vseh članov, da ugotovi kdo je bil prisoten na več kot 80% srečanjih.

### Srečanje skupine Štirih

Ana, Blaž, Cene in Dana organizirajo športne igre, in se morajo dobiti vsi skupaj na sestanku med 13. in 20. oktobrom, da se pomenijo. Ana jih prepriča naj se vsi vpišejo v Skavtka. Ustvari skupino, doda ostale tri v skupino in jim pošlje anketo, kjer vsak označi, kdaj je tiste dni razpoložljiv. Vsi odgovorijo, Cene si po nekaj urah premisli in odstrani svojo razpoložljivost 19. oktobra med 8.00 in 10.30, Dana pa se spomne, da je tudi 14. oktobra med 4.00 in 6.00 razpoložljiva in ta doda. Ana pregleda rezultate in se odloči, da se bodo dobili 18. oktobra med 12.00 in 15.00, na Skavtku ustvari srečanje, vsi ji potrdijo prisotnost v živo, samo Blaž si potem premisli in označi, da bo prisoten na daljavo. Ana med vsebino srečanje doda link na Zoom.
