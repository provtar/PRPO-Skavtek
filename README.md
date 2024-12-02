# PRPO-Skavtek
Repository for a project at the PRPO subject

## Gradnja in zagon projekta
BUILD:
```
mvn clean package
```
ZAGON:
```
java -jar api/target/api-1.0.0-SNAPSHOT.jar
```
DOSTOP do aplikacije:

http://localhost:8080/

#### Preden to delas spomni se zagnat docker

Zagon odcker compose fila
```
docker compose  -f .\entitete\src\main\resources\docker-compose.yaml up
```
Startaj container:
```
docker start resources-db-1 resources-adminer-1
```
Stop container:
```
docker stop resources-db-1 resources-adminer-1
```
Dostop do adminer

http://localhost:8081/

Database specifikacije za login (so tud u docker compose):
```
Tip Postgresql
Database: db
User: admin
Password: admin
```
### API Dokumentacija


## Warning
Rabimo hibernate core dependency in ne kumuluz-hibernate, ker ce kumuluz rabm je s persistence.xml nekaj narobe

## TODO
* ustvari en class, v katerem so definirana imena vseh column, ki vsebujejo imena atributov po katerih se preslika med relacijami
* Dodaj equal in hash vsem entitetam @Override

## Pametni linki do dokumentacij
* JPQL
* https://docs.oracle.com/cd/E11035_01/kodo41/full/html/ejb3_langref.html
* Hibernate
* https://docs.jboss.org/hibernate/orm/6.6/introduction/html_single/Hibernate_Introduction.html
* HQL
* https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/queryhql.html
### Entity dodatno
* https://www.baeldung.com/jpa-hibernate-associations
* https://www.baeldung.com/hibernate-inheritance
* Rabiti enum kot diskriminatorje za podtipe
* https://stackoverflow.com/questions/3639225/single-table-inheritance-strategy-using-enums-as-discriminator-value#:~:text=If%20you%20try%20to%20use%20an%20enum%20as,discriminator%20types%20allowed%20are%20String%2C%20Char%20and%20Integer.
* Raba getReference vs find
* https://stackoverflow.com/questions/1607532/when-to-use-entitymanager-find-vs-entitymanager-getreference-with-jpa
* HQL Query z null paarmetri (2. odgovor)
* https://stackoverflow.com/questions/2123438/hibernate-how-to-set-null-query-parameter-value-with-hql


## Database
Hibernate ima nastavitve, da se poveže na postgres DB

## CLAN
* id generated
* ime required
* priimek required
* skavtsko_ime
* steg
* datumRojstav (moramo èe dodat)
* vloga (aktiven-pasiven-admin) required

### AktivenClan (podtip)
## CLAN
* id generated
* ime required
* priimek required
* skavtsko_ime
* steg
* datumRojstva (moramo èe dodat)
* vloga (aktiven-pasiven-admin) required

### AktivenClan (podtip)
* username
* password
* mail

### PasivenClan
* master - skupina kateri pripada
* mail

### PasivenClan
* master - skupina kateri pripada

## SKUPINA
* id
* ime
* opis
* []link, ime

## CLAN-SKUPINA
* id_clana
* id_skupine
* vloga
* vloga

## SRECANJE
* id
* ime
* belezenje (ja-ne)
* kraj
* datum-ura od
* datum-ura do
* opis
* id_skupine

## PRISOTNOSTI
* id_clan
* id_srecanja
* prisotnost (pris, online, ods, ods_opr)
* komentar

## TERMINI
* id_clanAktiven
* timestamp_od
* timestamp_do

## PERMISSION
* id_clanAktiven
* id_clanAktiven
* id_skupine
* #tip premission (view, view_termini, crud, clani; view, upravljaj skupina; view_srecanja, crud_srecanje; view_prisotnosti, crud_priostnposti)
* token (?strategija)

## TIPI_PERMISSION
* tip
* ime
* opis

## LOG
* id_oseba
* id_skupina
* id_oseba
* id_skupina
* akcijo

## API na kratko
### Path /registracija
Dodajanje active userjev
### Path /clani
Dodajanje navadnih userjev
### Path /clani/id
Upravljaniìje z userji po id-ju
### Path /skupina
Upravljanje skupin
### Path /skupina/id/clani
Upravljanje s clani skupine
### Path /srecanje
### Path /prisotnosti/id
### Path prisotnost/id/clani/clanId
### Termini /clanId

## Zrna
### Clan:
* Dodaj uporabnika
* Dodaj aktivnega uporabnika
* Posodabljaj uporabnika
* Dostopaj do javnih podatkov uporabnikov
