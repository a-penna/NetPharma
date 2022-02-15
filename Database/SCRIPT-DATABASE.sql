SET GLOBAL time_zone = '+1:00';

DROP DATABASE IF EXISTS NETPHARMA;
CREATE DATABASE NETPHARMA; 
USE NETPHARMA;

DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL ON NETPHARMA.* TO 'admin'@'localhost';

DROP USER IF EXISTS 'user'@'localhost';
CREATE USER 'user'@'localhost' IDENTIFIED BY 'user';
GRANT SELECT, INSERT, UPDATE ON NETPHARMA.* TO 'user'@'localhost';

CREATE TABLE ACCOUNT
(
	ID	INT	NOT NULL AUTO_INCREMENT,
	USERNAME VARCHAR(20) NOT NULL UNIQUE, 	
	PASSWORD VARCHAR(32) NOT NULL,	
	ORDER_COUNT INT NOT NULL,
	PRIMARY KEY(ID)	
);

CREATE TABLE RUOLI
(
	ACCOUNT	INT	NOT NULL,
	RUOLO VARCHAR(2)	NOT NULL,
	PRIMARY KEY(ACCOUNT, RUOLO),
	FOREIGN KEY(ACCOUNT) REFERENCES ACCOUNT(ID)
);

CREATE TABLE UTENTE_REGISTRATO
(
	GENERE CHAR NOT NULL, 
	NOME VARCHAR(20) NOT NULL, 
	COGNOME VARCHAR(20) NOT NULL, 
	EMAIL VARCHAR(40) NOT NULL,
    NASCITA DATE NOT NULL,
    ACCOUNT INT NOT NULL UNIQUE,
    PRIMARY KEY(EMAIL),
    FOREIGN KEY(ACCOUNT) REFERENCES ACCOUNT(ID)
); 

CREATE TABLE ORDINE 
(
	ID	VARCHAR(15) NOT NULL,  
	NOME_RICEVENTE VARCHAR(20) NOT NULL,	
	COGNOME_RICEVENTE VARCHAR(20) NOT NULL,	
	EMAIL VARCHAR(40) NOT NULL,	
	CELLULARE VARCHAR(15) NOT NULL,	
	NCIVICO	INT	NOT NULL,	
	VIA	VARCHAR(50)	NOT NULL,	
	CITY VARCHAR(50) NOT NULL,	
	PAESE VARCHAR(50) NOT NULL,	
	PROVINCIA VARCHAR(50) NOT NULL,	
	CAP	CHAR(5)	NOT NULL,	
	DATA_ORDINE	DATE NOT NULL,	
	DATA_ARRIVO	DATE,	
	PREZZO DECIMAL(6,2)	NOT NULL,	
	STATO CHAR(2) NOT NULL,	
	CLIENTE	VARCHAR(20)	NOT NULL,	
    PRIMARY KEY(ID),
	FOREIGN KEY(CLIENTE) REFERENCES UTENTE_REGISTRATO(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE
); 


CREATE TABLE CATEGORIA
(
	ID INT NOT NULL AUTO_INCREMENT,
	NOME VARCHAR(50) UNIQUE NOT NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE PRODOTTO
(
	ID	INT	NOT NULL,	
	NOME VARCHAR(100) NOT NULL,	
    FOTO MEDIUMBLOB,
	MARCHIO	VARCHAR(50) NOT NULL,		
	PRODUTTORE VARCHAR(50),		
	FORMATO	VARCHAR(20),		
	DESCRIZIONE	TEXT NOT NULL,	
	DISPONIBILITA INT NOT NULL,	
	PREZZO DECIMAL(6,2)	NOT NULL,	
	CATEGORIA INT,
    PRIMARY KEY(ID),
    FOREIGN KEY(CATEGORIA) REFERENCES CATEGORIA(ID) ON DELETE SET NULL ON UPDATE CASCADE	
);

CREATE TABLE RIGA_ORDINE
(
	PRODOTTO INT NOT NULL,
	ORDINE VARCHAR(15) NOT NULL,
	QUANTITY SMALLINT NOT NULL,	
	PREZZO_AL_PEZZO	DECIMAL(6,2) NOT NULL,	
    PRIMARY KEY(PRODOTTO, ORDINE),
    FOREIGN KEY(PRODOTTO) REFERENCES PRODOTTO(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(ORDINE) REFERENCES ORDINE(ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CARRELLO
(
	CLIENTE VARCHAR(20)	NOT NULL,
	PRODOTTO INT NOT NULL,
	QUANTITA INT NOT NULL,	
    PRIMARY KEY(CLIENTE, PRODOTTO),
	FOREIGN KEY(CLIENTE) REFERENCES ACCOUNT(USERNAME) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(PRODOTTO) REFERENCES PRODOTTO(ID) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into account(username, password, order_count) values("Mrossi", MD5("pw"),1);
insert into ruoli(account, ruolo) values(1, "CL");
insert into utente_registrato(genere,nome,cognome,email,nascita,account) values ("M","Mario", "Rossi","m@rossi.com","1999-07-25",1);
insert into account(username,password, order_count) values ("SVerdi",MD5("pw1"), 0);
insert into ruoli(account, ruolo) values(2,"GO");
insert into utente_registrato(genere,nome,cognome,email,nascita,account) values ("F","Sara","Verdi","s@verdi.com","1989-01-22",2);
insert into account(username,password, order_count) values ("GVerdi",MD5("pw2"),0);
insert into ruoli(account, ruolo) values(3,"GC");
insert into ruoli(account, ruolo) values(3,"GO");
insert into utente_registrato(genere,nome,cognome,email,nascita,account) values ("M","Gianni","Verdi","g@verdi.com","1978-02-13",3);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo) values(883, "Salvelox Protettore Alluce", "Salvelox", "E.F.A.S S.P.A.", "Protettore Alluce", "La protezione in tessuto elastico per l\'alluce valgo e' dotata di un cusinetto interno di gel. Riduce il dolore della protuberanza ossea, evitando lo sfregamento sull\'alluce valgo.", 200, '8.60');
insert into carrello(cliente, prodotto, quantita) values("Mrossi", 883, 2);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo) values(884, "Tantum Verde Gola", "Tantum Verde", "Angelini S.R.L.", "Spray Gola", "Tantum Verde Gola e' usato come trattamento sintomatico di stati irritativo-infiammatori, anche associati a dolore del cavo orofaringeo, ad esempio faringiti.", 100, '5.50');
insert into carrello(cliente, prodotto, quantita) values("Mrossi", 884, 2);
insert into ordine(ID,NOME_RICEVENTE,COGNOME_RICEVENTE,EMAIL,CELLULARE,NCIVICO,VIA,CITY,PAESE,PROVINCIA,CAP,DATA_ORDINE,DATA_ARRIVO,PREZZO,STATO,CLIENTE) 
			values ("1-1","Mario","Rossi","m@rossi.com",333333,404,"Via Roma","Sant\'Antonio Abate","Sant\'Antonio Abate","NA","80057", '2022-01-01','2022-01-06',20,"Si","m@rossi.com");		
insert into riga_ordine(prodotto, ordine, quantity, prezzo_al_pezzo) values (883,"1-1",3,'4.35');
insert into categoria(nome) values ("Mamma & Bambino");
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(885, "Pampers Salviette Sensitive Aloe 63 Pezzi", "Pampers sensitive", "Pampers", "Salviette", "Sensitive Salviettine con estratti naturali ideali per le pelli piu sensibili, per il cambio e la pulizia di viso e mani. Il tessuto morbido e soffice garantisce la massima delicatezza sulla pelle. La lozione aiuta ad idratare e proteggere la pelle dalle irritazioni, ripristinando e mantenendo il pH al suo livello naturale. Senza profumo, senza alcool. Dermatologicamente testate.", 100, '3.99', 1);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(890, "Chicco Biberon Natural Feeling +2m Boy 250 ml", "Chicco", "Artsana", "Biberon", "Il silicone effetto mamma, la morbidezza e la flessibilita' della tettarella aiutano a riprodurre la suzione naturale e facilitano l'alternanza tra seno e biberon. Ideale per i bambini nei primi mesi di vita, quando iniziano a succhiare con piu' voracita'. Biberon da 250 ml.", 50, '19.99', 1);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(893, "Hipp Biologico Omogeneizzato Merenda Mela e Yogurt 125 g", "Hipp", "Hipp italia srl", "Barattolo", "Hipp Biologico Omogeneizzato al gusto di Mela e Yogurt e' la merenda perfetta per i tuoi bambini. Indicato dal quinto mese compiuto.", 200, '4.99', 1);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(895, "Humana 1 Probalance Latte in Polvere 1100 grammi", "Humama", "Humana italia ", "Bustina", "Latte in polvere per bambini", 30, '14.99', 1);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(920, "Chicco Gommotto PhysioForma Silicone Bimba 16-36 Mesi 2 Pezzi", "Chicco", "Artsana", "2 pezzi", "Il Gommotto PhysioForma della chicco per bimba e' al 100% in silicone ed e' stato pensato per dare sollievo ai piu' piccoli, rispettando la naturale crescita del cavo orale. Indicato dai 16 ai 36 mesi.", 100, '9.99', 1);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(921, "Curasept Biosmalto Junior Dentifricio 75 ml", "Curasept", "Curasept s.p.a.", "Tubetto", "Curasept Biosmalto Junior e' un dentifricio pensato per ragazzi dai 7 ai 12 anni che inibisce la formazione della placca nei denti da latte e permanenti.", 300, '2.99', 1);
insert into categoria(nome) values ("Raffreddore");
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(901, "Vicks Vaporub Unguento Inalatorio 100g", "Vicks", "Procter Gamble", "Barattolo", "Vicks Vaporub Unguento si usa nel trattamento balsamico nelle affezioni delle prime vie respiratorie.", 82, '4.99', 2);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(902, "Rinazina Spray Nasale Decongestionante", "Rinazina", "gsk", "Bottiglia", "Spray decongestionante ad azione rapida e prolungata che libera il naso anche in caso di sinusite.", 50, '5.99', 2);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(903, "Vivin C 330 mg Acido Acetilsalicilico + 200 mg Acido Ascorbico (Vit. C) 20 Compresse Effervescenti", "Vivin C", "Menarini", "Compresse", "Con Vivin C puoi stare alla larga dagli ecc� in tutti i periodi dell'anno. Vivin C grazie alla combinazione di Acido Acetilsalicilico e Vitamina C, e' indicato contro le sintomatologie dell'influenza e del raffreddamento, supportando il sistema immunitario", 1000, '8.79', 2);
insert into categoria(nome) values ("Protezione Antivirale");
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(1001, "Medel Pulsossimetro Pulse", "Medel", "Medel International", "Pulsossimetro", "Medel Pulse e' il pulsossimetro in grado di calcolare la saturazione dell'ossigeno (SpO2) e la frequenza cardiaca (pulsazioni).", 20, '10.50', 3);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(1002, "Amuchina Gel X Germ Disinfettante Mani 80 ml", "Amuchina", "Amuchina", "Flacone", "Gel disinfettante mani, specificatamente studiato per igienizzare e pulire a fondo la pelle. E' utile in situazioni in cui non si dispone di acqua e sapone.", 70, '30.00', 3);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo, categoria) values(1003, "Siria 20 Mascherina FFP2 Taglia Small Nera 1 Pezzo", "Siria", "Siria italia srl", "Mascherina", "La mascherina filtrante Siria 20 Small FFP2 NR, per i piu' piccoli, offre un'efficace protezione delle vie respiratorie da particelle solide o liquide non volatili (polveri, fumi, nebbie). Il ferretto stringinaso e' in grado di assicurare un'ottima adattabilita' ad ogni tipo di volto.", 1000, '1.99', 3);