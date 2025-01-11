# TODO
## Za locenje na mikrostoritve:
- Eno po eno loci in ustvari drugi mvn projekt, ki se razlicno imenuje
- Clan : ne spreminjati nic, samo loci
## Backend
### Srecanje prisotnosti jih dodaja multiple, preveri, da se ne obstaja v POST metodi

### Splosno
Dodat order by, limit, offsest za paginacijo in vizualizacijo
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
#### Dodati linke na srecanje QUICK
- Dodaj v entiteto, v dtoje
- V frontend, dodaj v Srecanja/PutData/postData in vizualizacijo
- V frontend - dodaj v form CV iz kupin
### Prisotnosti MS
#### Lastniki
Lastniki so isti kot za srecanje
### Termin MS
#### Validacija inputa, da s ene prekrivajo
## Frontend
### Urejanje vizualizacij:
Uredi po imenu, priimku al id-ju
### UserDataService
#### Form user-post ga poscistit po vnosu podatkov (onsubmit())
### Skupina post put form LATER:
custom validator za povezave, da vidis, da je link (da ljudje manj trpijo)
### Form data po submit:
Daj jih poslat iz class z event emitter in ne z output
### Skupina POST kdaj se klice backend:
Backend se klice istocasno v zaporedju skupina pa clani, ali skupina ob submitu pa clani ob submitu?
### Clani skupine, user default v skupini
Za zdaj tako, treba pomislit, ce dobro narejeno
### Napake pri odgovoru z iskanjem po id-ju, ker clan az id-jem ni, handlat v componenti
# Ideje za katere nimamo casa in nimjo smisla
### Ideja: shrani spremembe namesto submit (deluje mal razlicno, ampak ne menja dosti)
### Clani skupine:
Dodajanje clanov preko buttonov, kjer pritisk naredije, ni notri, ob put se zbrise vse clane in ustvari vse na novo, je zamudno, ne doda nek enove funkcionalnosti
### Prisotnosti se ne dodajajo na stara srecanja
Ne mores dodajati se prisotnosti, ki si jih dodal v skupino po ustvarjenju belezena, treba urediti
### Onemogociti, da samega sebe odstranis iz skupine
### Dodat podatke o tistemu, ki je opombo za OS napisal, da se ve, kdo je kaj napisal