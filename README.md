# PRPO-Skavtek
Repository for a project at the PRPO subject

## Gradnja in zagon projekta
BUILD:
mvn clean package
ZAGON:
java -jar api/target/api-1.0.0-SNAPSHOT.jar
DOSTOP do aplikacije:
http://localhost:8080/

Zagon odcker compose fila
docker compose  -f .\entitete\src\main\resources\docker-compose.yaml up
Startaj container:
docker start resources-db-1 resources-adminer-1
Stop container:
docker stop resources-db-1 resources-adminer-1
Dostop do adminer
http://localhost:8081/
Database specifikacije za login (so tud u docker compose):
Tip Postgresql
Database: db
User: admin
Password: admin

## Database
Hibernate ima nastavitve, da se poveže na postgres DB

## Clan
* id
* username
* ime
* priimek
* datum rojstva
* steg
* mail
* password
* vloga (aktiven-pasiven)

## SKUPINA
* id
* ime
* opis
* []link, ime

## CLAN-SKUPINA
* id_clana
* id_skupine

## SRECANJE
* id
* ime
* belezenje (ja-ne)
* kraj
* datum-ura
* opis
* id_skupine

## PRISOTNOSTI
* id_clan
* id_srecanja
* prisotnost (pris, online, ods, ods_opr)
* komentar

## TERMINI
* id_osebe
* timestamp_od
* timestamp_do

## PERMISSION
* id_osebe
* id_skupine
* #tip premission (view, view_termini, crud, clani; view, upravljaj skupina; view_srecanja, crud_srecanje; view_prisotnosti, crud_priostnposti)
* token (?strategija)

## TIPI_PERMISSION
* tip
* ime
* opis

## LOG
* id_osebo
* id_skupino
* akcijo