# SEDS

## 1 Vezbe I

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

Sada je moguce generisati skriptu za kreiranje tabela. Moguci su problemi vezani za duzinu naziva nekih stranih kljuceva, sto je moguce izmeniti direktno u skripti ili na relacionom modelu. Preciscena skripta nalazi se [ovde](#).
