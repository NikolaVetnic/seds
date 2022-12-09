# SEDS

## 1 Vezbe

### 1.1 Parametri za konekciju na fakultetsku Oracle bazu

Kreiranje nove konekcije koriscenjem sledecih parametara:

-   `hostname` : `nastava.is.pmf.uns.ac.rs`
-   `port`: `1521`
-   `SID`: `xe`
-   `username`: `seds2022`
-   `password`: `seds2022`

### 1.2 Kreiranje OLTP seme

OLTP sema se kreira sa nekim problemima, u smislu da ce neki podaci biti denormalizovani tako da bismo mogli kasnije da ih transformisemo u ECTL procesima cime pokazujemo kako moze da se izvrsi i ciscenje podataka.

Za crtanje seme baze podataka u Oracle SQL Developer-u koristimo Data Modeler: `View` / otvaramo browser / unutar browsera kreiramo `New Design`. Ovako prvo kreiramo logicki model nakon cega se on prevodi u relacioni, a takodje se tako dobija i skripta za generisanje tabela.

![seds_01](http://nikolapacekvetnic.rs/wp-content/uploads/2022/12/seds_01.jpg)

Logicki model u SQL Developer-u:

![seds_02](http://nikolapacekvetnic.rs/wp-content/uploads/2022/12/seds_02-scaled.jpg)

Relacioni model u SQL Developer-u:

![seds_03](http://nikolapacekvetnic.rs/wp-content/uploads/2022/12/seds_03-scaled.jpg)

Sada je moguce generisati skriptu za kreiranje tabela. Moguci su problemi vezani za duzinu naziva nekih stranih kljuceva, sto je moguce izmeniti direktno u skripti ili na relacionom modelu. Preciscena skripta nalazi se [ovde](https://github.com/NikolaVetnic/seds/blob/master/seds_OtplGenerationScript.txt). Skripta se kopira u SQL konzolu, pokrece se i tako se generisu tabele u bazi.

## 2 Vezbe

Tokom proslih vezbi je kreiran OLTP model baze podataka koja se odnosi na prodaju odredjenih proizvoda u odredjenim gradovima. Sada je bitno reci sta su nam ciljevi, sta su pitanja koja postavljamo, odnosno sta je cilj pravljenja ovakvog skladista podataka, videcemo sta su neophodne dimenzije i na osnovu toga pravimo i DW semu baze podataka. Zatim cemo popuniti izvorne tabele iz OLTP baze podacima da bismo nakon transformacija te podatke iz OLTP baze prebacili u DW bazu.

### 2.1 _BUS_ matrica

Prvo pravimo _BUS_ matricu koja prikazuje dimenzije i poslovne procese koje pratimo:
|dim|proces: prodaja|
|--|--|
|time (vreme)|:white_check_mark:|
|product (proizvod)|:white_check_mark:|
|city / region / customer|:white_check_mark:|

Pitanja:

-   pratimo prodaju odredjenog proizvoda u nekom vremenskom intervalu
-   prodaja proizvoda po gradovima / regijama / drzavama
-   kojih pet proizvoda su najprodavaniji (i po gradu, i u nekom vremenskom intervalu)

Dakle u izvornoj semi imamo proizvode - to bi bila jedna dimenzija, dakle zelimo da pratimo koliko je nekog proizvoda prodato u nekom vremenskom periodu. "Vreme" je dakle takodje jedna dimenzija, i za svaki poslovni proces za koji zelimo da pratimo ovu dimenziju potrebno je dodati :white_check_mark:. Zatim, treba nam dimenzija "proizvod". Konacno, buduci da nije bitno koliko konkretan kupac kupuje proizvoda, zanima nas zapravo prodaja po gradovima, tako da su "gradovi" jos jedna (prostorna) dimenzija, eventualno "regioni", vec u zavisnosti od granulacije koja nas interesuje.

Data _BUS_ matrica odgovara za sva tri pitanja, ali je potencijalno, u zavisnosti od pitanja, moguce imati ih i vise.

### 2.2 _Data Warehouse_ sema baze podataka

U skladu sa matricom i pitanjima ocekivano je da nam "prodaja" (`Sales`) bude neka centralna tabela, tabela cinjenica. Sto se kupaca tice, mozemo ih ignorisati ili ne (u zavisnosti da li postoje neki redovni kupci koje bi trebalo pratiti ili ne), pri cemu je preferabilno ignorisati ih ukoliko granulacija dozvoljava pracenje samo vecih jedinica poput gradova, regija i slicno. Vrsta proizvoda se takodje moze izdvojiti kao jedna dimenzija prodaje (veza `Sales` sa `Product` i dalje njegova sa `ProductFamily`). Prodaja je povezana i sa vremenskom dimenzijom (tabela `Time`). Za klijenta iz tabele `Customer` uzecemo sve podatke, pored toga dodajemo i ime i prezime prodavca zaduzenog za odredjenu kupovinu.

![seds_04](http://nikolapacekvetnic.rs/wp-content/uploads/2022/12/seds_04.jpg)

Kad smo pravili OLTP bazu namerno smo u `Region` tabeli stavili `countryName` i `countryId` iako tabele tako nisu normalizovane, zato da bismo uradili izdvajanje ID-a i naziva drzave iz tabele `Region` i da na taj nacin napravimo jos jednu tabelu u skladistu podataka.

![seds_05](http://nikolapacekvetnic.rs/wp-content/uploads/2022/12/seds_05-scaled.jpg)

Kod veza izmedju tabela dimenzija veze su uvek `(0, N) - (1, 1)`, gde je leva strana "veci" entitet (recimo regija-grad, grad-kupac).

![seds_06](http://nikolapacekvetnic.rs/wp-content/uploads/2022/12/seds_06-scaled.jpg)

U vremenskoj dimenziji imacemo `timeId` kao kljuc vremenske dimenzije (svaka tabela ce imati svoj ID). Razlog izdvajanja vremenske dimenzije je taj sto, buduci da za svaku kupovinu imamo datum, zelimo da kupovinu povezemo sa unosom u bazi `Time`, odnosno da vise kupovina bude vezana za jednu jedinicu vremena granulacije koju smo mi odredili. Na taj nacin mozemo brzo da pronadjemo sve prodaje koje su se desile npr. u novembru 2021. godine.

Skripta za generisanje tabela za DW bazu se nalazi [ovde](https://github.com/NikolaVetnic/seds/blob/master/seds_DwGenerationScript.txt).

Sledeci korak je popunjavanje OLTP baze nekakvim podacima, a posle toga cemo popuniti DW bazu transformacijom sadrzaja OLTP baze. `STAO NA SEDS_20211110_090606-Meeting Recording.mp4 00:47:53`
