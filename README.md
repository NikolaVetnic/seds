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

![seds_03](http://nikolapacekvetnic.rs/wp-content/uploads/2022/12/seds_03_upd-scaled.jpg)

Relacioni model u SQL Developer-u:

![seds_02](http://nikolapacekvetnic.rs/wp-content/uploads/2022/12/seds_02-upd2-scaled.jpg)

Sada je moguce generisati skriptu za kreiranje tabela. Moguci su problemi vezani za duzinu naziva nekih stranih kljuceva, sto je moguce izmeniti direktno u skripti ili na relacionom modelu. Preciscena skripta nalazi se [ovde](https://github.com/NikolaVetnic/seds/blob/master/seds_OtplGenerationScript.md). Skripta se kopira u SQL konzolu, pokrece se i tako se generisu tabele u bazi.

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

Skripta za generisanje tabela za DW bazu se nalazi [ovde](https://github.com/NikolaVetnic/seds/blob/master/seds_DwGenerationScript.md).

Sledeci korak je popunjavanje OLTP baze nekakvim podacima, a posle toga cemo popuniti DW bazu transformacijom sadrzaja OLTP baze.

### 2.3 Popunjavanje OLTP baze

Potrebno je popuniti svaku od sedam tabela OLTP baze:

-   `NV_SRC_Region` : ova tabela se popunjava pomocu [skripte](https://github.com/NikolaVetnic/seds/blob/master/seds_fillScript_Region.md)
-   `NV_SRC_City` : ova tabela se takodje popunjava pomocu [skripte](https://github.com/NikolaVetnic/seds/blob/master/seds_fillScript_City.md)
-   `NV_SRC_Salesperson` : podatke za ovu tabelu generisemo preko [Mockaroo-a](https://www.mockaroo.com/) gde su potrebna polja `spId`, `spFirstName`, `spLastName` i `spHireDate`, primer skripte je dostupan [ovde](https://github.com/NikolaVetnic/seds/blob/master/seds_fillScript_Salesperson.md)
-   `NV_SRC_Customer` : podatke za ovu tabelu takodje generisemo preko [Mockaroo-a](https://www.mockaroo.com/) gde su potrebna polja `custId`, `custFirstName`, `custLastName`, `custDateOfBirth` (bitno je navesti Oracle format datuma, odnosno `dd-Mon-yyyy`), `custAddress`, `custPhone`, `NV_SRC_City_cityId` (), `NV_SRC_Salesperson_spId` (ovo polje predstavlja strani kljuc odnosno vezu sa tabelom `NV_SRC_City`, tako da i to mozemo izgenerisati kao celobrojnu vrednost u opsegu u kom su kljucevi koje smo uneli - na Mockaroo-u je to `number` sa parametrima `min: 1`, `max: 25`, `decimals: 0`), primer skripte je dostupan [ovde](https://github.com/NikolaVetnic/seds/blob/master/seds_fillScript_Customer.md)
-   `NV_SRC_Product` : podatke za ovu tabelu takodje je moguce generisati preko [Mockaroo-a](https://www.mockaroo.com/), s tim da je u mom slucaju koriscena sledeca [skripta](https://github.com/NikolaVetnic/seds/blob/master/seds_fillScript_Product.md)
-   `NV_SRC_Order`: ova tabela se popunjava pomocu [skripte](https://github.com/NikolaVetnic/seds/blob/master/seds_fillScript_Order.md)
-   `NV_SRC_OrderItem`: ovu tabelu je moguce generisati ili popuniti pomocu [skripte](https://github.com/NikolaVetnic/seds/blob/master/seds_fillScript_OrderItem.md)

## 3 Vezbe

Posmatramo semu DW odnosno OLAP baze podataka: `NV_DW_Sales` je tabela cinjenica, `NV_DW_Time` predstavlja vremensku dimenziju, `NV_DW_Customer` je prostorna dimenzija (dalje povezana sa `NV_DW_City`, `NV_DW_Region` i `NV_DW_Country`), na kraju imamo i `NV_DW_Product` koja se razlikuje od izvorne (`ProductFamily` obelezje je izdvojeno u zasebnu tabelu).

Za ECTL procese, odnosno za transformaciju koristi se alat Pentaho Data Integration, konkretno Spoon je alat u kome se crtaju ECTL procesi. To radimo tako sto kreiramo novu transformaciju (buduci da transformisemo podatke iz jednog oblika u drugi) ciji input mogu biti razlicite stvari (CSV fajlovi, _data grid_-ovi, _table input_ - sto cemo i mi koristiti, itd.), a isto vazi i za output (konkretno cemo koristiti _table output_).

## X Projekat

### X.1 Beleske sa casa

Napraviti prvo OLTP bazu koja ima bar 6-7 tabela (u paru bar 10), podatke izgenerisati ili iz fajlova koje smo skinuli ili nesto bzvz (Mockaroo), bitno je da tabele imaju dosta podataka (bar nekih 1000 redova)

Treba osmisliti sta su poslovni procesi koje cemo pratiti (koji su studenti polozili ispita, ispita u toku godine, studenata polozilo neki predmet, upiti - na kraju za izvestaje, po tri izvestaja na kraju - u paru znaci tri po coveku)

U zavisnosti od upita (izvestaja na kraju) tako ce se praviti i DW baza, moze biti pokriveno jednom tabelom cinjenica (ako se moze izvuci sve sto treba), ali moze biti i sema pahuljice (dve tabele cinjenica, zavisi sta su ciljevi)

Kad se napravi DW sema prave se transformacije, da popunimo sve tabele dimenzija i na kraju tabelu cinjenica, transformisati podatke, agregatne f-je, zavisi od modela

Na kraju izvestavanje - ta pitanja koja smo postavili, napraviti izvestaje za njih, izvestaj je u obliku tabele ili u obliku grafikona (bar jedan u vidu grafikona, prosecna ocena studenata, linijski ili stubicasti ili nesto)

### X.2 Beleske sa snimka

Treba da u prvom delu napisemo sta je neka motivacija i ciljevi, odnosno o cemu se radi u primeru, opise se da u tom nasem preduzecu ili sta vec podaci su sacuvani u OLTP bazi podataka i u nekim spoljnim datotekama i posto su potrebne slozenije analize onda se javila potreba za uvodjenjem DW-a, u nasem primeru za porudzbine on ce objediniti sve podatke o porudzbinama i omogucice analizu prodaje po proizvodima, kupcima, gradovima ili kako vec odlucimo, zavisi od granularnosti.

Opis zadatka i plan realizacije, sta je izvorni sistem (samo OLTP baza, eventualno neki Excel fajlovi), treba da predstavimo nasu OLTP bazu podataka, znaci imacemo evidenciju o porudzbinama, evidenciju o zaposlenima (moze biti TXT ili Excel fajl sa podacima), mozemo recimo imati spoljni fajl koji definise opsege starosti (bracketing dimensions) pa da se na osnovu toga odredjuje kategorija korisnika (uzimajuci u obzir datum rodjenja).

Zatim treba opisati kako izgleda DW baza, koje se dimenzije koriste (mi smo rekli jedna dimenzija je prostorna - kupci, gradovi, regije, drzave - a imamo i dimenziju za proizvod - product i productFamily - i vremensku dimenziju, a u tabeli cinjenica - Sales - imacemo podatke o prodaji odredjenih proizvoda po kupcima). Prikazemo OLTP i DW baze... Prikazemo sta su izvorni podaci (sajt za generisanje, TXT fajlovi). Uoceni poslovni procesi - rec je o BUS matrici koja je pravljena (kod nas je jedan poslovni proces u primeru, moze i vise).

Zatim pitanja na koja treba odgovoriti: iz kojih gradova/regiona se najvise kupuje, iz kojih gradova/regiona se najvise kupuju proizvodi neke vrste, koji proizvod je najprodavaniji u prethodnih mesec/godinu dana?

Ocekivani rezultati su analiza kupovina po gradovima/regijama i po vrstama proizvoda (vrste analize koje se ovom STAR shemom mogu pokriti i za koje se mogu dobiti izvestaji iz ovakve DW - jos se naziva i OLAP - baze).

Opis ECTL procesa - kako se popunjavaju tabele cinjenica i tabele dimenzija - bice da se nacrtaju procesi koji su sluzili za transformaciju podataka, i na kraju u okviru dobijenih rezultat i izvestaja treba napraviti nekoliko izvestaja.

U zakljucku - da li su dobijeni odgovori na postavljena pitanja.

### X.3 Primeri projekata sa snimka

Neki primeri:

-   pracenje vremenske prognoze
-   rezervacija avio karata
-   evidencija telefonskih razgovora
-   pracenje nekog poslovanja u periodima vremena
-   pracenje tekucih racuna
-   rezervacije turistickih putovanja

## Linkovi

[https://www.mockaroo.com/](https://www.mockaroo.com/)
[https://png2jpg.com/](https://png2jpg.com/)
