# TODO
## Backend
### Dodat put in post DTO-je
Samo s podatki, ki so potrebni
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
#### Dodati lastnika skupinam
Vsaka skupina ima svoje lastnike, isti za skupino in clane
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