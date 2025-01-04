# TODO
## Backend
### Dodat put in post DTO-je
Samo s podatki, ki so potrebni
### Sinhronizacija podatkov vec uporabnikov (je na ravni komponente ali servisa?)
Spremeniti podatke in popsodobiti preden se klice funkcijo na frontendu (mozna resitev), pogledaj online kako se psroti posodablja podatke, kako preverjat ìi spremembe, klicati get pri vsaki metodi in posodabljati stalno? (ne samo prvic on Init, timeout => {update se klice po nekaj sekundah, oz ponovno klice funkcijo, sama pa returna})
### Clani MS
#### Error responsi api:
Gravity: lahko kasneje, sam da api vraca neke jasne napake
- Custom napake, ki obrazlozijo, kje je problem, namesto standard html-ji
- Clani POST 404 napaka, no master found
#### Bulk options za delete
Zbrisi vec clanov na enkrat
#### Nov endpoint
Dobi svoje Varovance
#### Dodatna tabela oziroma povezava
Vec voditeljev (masterjev) za vsakega varovanca
#### Varovanci LATER
Dodaj tagge varovancem, da jih je lazje izbirati (locis na npr. OU SV1, UM VV, Izvidniki Lj4...)
### SKupina MS
#### Dodajanje clanov
Ne klicati poizvedbe za vsakega clana posebej
#### Popolna prenova clanov LATER
Zbrisi clane za to skupino in daj seznam novih
#### Dodati lastnika skupinam
Vsaka skupina ima svoje lastnike, isti za skupino in clane
#### Prazne skupine
Ko ustvaris skupinio nima nobenega clana, lahko ostane sama, kako to resit? Delno se resi s tem, da imas admine skupin, ti so ze ob inicializacij dodani
### Srecanja MS
#### SetBelezenje
Endpoint za set belezenja na true
#### Dodati lastnike srecanju
Vsako srecanje ima svojega master, niso nujno isti kot za srecanje
### Prisotnosti MS
#### Lastniki
Lastniki so isti kot za srecanje
## Frontend
### UserDataService
Inicializacija varovancev, (dobiti podatke o ze obstojecih)