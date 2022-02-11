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
	NOME VARCHAR(50) NOT NULL,
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
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo) values(883, "prodotto1", "marchio1", "produttore1", "formato1", "descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1 descrizione1", 100, '4.35');
insert into carrello(cliente, prodotto, quantita) values("Mrossi", 883, 2);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo) values(884, "prodotto2", "marchio2", "produttore2", "formato2", "descrizione2", 100, '7.35');
insert into carrello(cliente, prodotto, quantita) values("Mrossi", 884, 2);
insert into ordine(ID,NOME_RICEVENTE,COGNOME_RICEVENTE,EMAIL,CELLULARE,NCIVICO,VIA,CITY,PAESE,PROVINCIA,CAP,DATA_ORDINE,DATA_ARRIVO,PREZZO,STATO,CLIENTE) 
			values ("1-1","Mario","Rossi","m@rossi.com",333333,404,"Via Roma","Sant'Antonio Abate","Sant'Antonio Abate","NA","80057", '2022-01-01','2022-01-06',20,"Si","m@rossi.com");		
insert into riga_ordine(prodotto, ordine, quantity, prezzo_al_pezzo) values (883,"1-1",3,'4.35');