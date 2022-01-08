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

CREATE TABLE DATI_SPEDIZIONE 
(
	ID	INT NOT NULL,  
	NOME_RICEVENTE VARCHAR(20) NOT NULL,	
	COGNOME_RICEVENTE VARCHAR(20) NOT NULL,	
	EMAIL VARCHAR(40) NOT NULL,	
	CELLULARE VARCHAR(15) NOT NULL,	
	NCIVICO	INT	NOT NULL,	
	VIA	VARCHAR(20)	NOT NULL,	
	CITY VARCHAR(20) NOT NULL,	
	PAESE VARCHAR(30) NOT NULL,	
	PROVINCIA VARCHAR(20) NOT NULL,	
	CAP	CHAR(5)	NOT NULL,	
	CLIENTE	VARCHAR(20)	NOT NULL,	
    PRIMARY KEY(ID),
	FOREIGN KEY(CLIENTE) REFERENCES UTENTE_REGISTRATO(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE
); 

CREATE TABLE ORDINE 
(
	DATA_ORDINE	DATE NOT NULL,	
	DATA_ARRIVO	DATE NOT NULL,	
	ID INT NOT NULL,
	PREZZO DECIMAL(6,2)	NOT NULL,	
	STATO CHAR(2) NOT NULL,	
	CLIENTE	VARCHAR(20)	NOT NULL,
	DATI_SPEDIZIONE	INT	NOT NULL,
	PRIMARY KEY(ID),
    FOREIGN KEY(CLIENTE) REFERENCES UTENTE_REGISTRATO(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(DATI_SPEDIZIONE) REFERENCES DATI_SPEDIZIONE(ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CATEGORIA
(
	NOME VARCHAR(50) NOT NULL,
	PRIMARY KEY(NOME)
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
	CATEGORIA VARCHAR(50),
    PRIMARY KEY(ID),
    FOREIGN KEY(CATEGORIA) REFERENCES CATEGORIA(NOME) ON DELETE CASCADE ON UPDATE CASCADE	
);

CREATE TABLE RIGA_ORDINE
(
	PRODOTTO INT NOT NULL,
	ORDINE INT NOT NULL,
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

insert into account(username, password) values("Mrossi", MD5("pw"));
insert into ruoli(account, ruolo) values(1, "CL");
insert into utente_registrato(genere,nome,cognome,email,nascita,account) values ("M","Mario", "Rossi","m@rossi.com","1999-07-25",1);
insert into dati_spedizione(ID,NOME_RICEVENTE,COGNOME_RICEVENTE,EMAIL,CELLULARE,NCIVICO,VIA,CITY,PAESE,PROVINCIA,CAP,CLIENTE) values (12,"Mario","Rossi","m@rossi.com",333333,404,"Via Roma","Sant'Antonio Abate","Sant'Antonio Abate","NA","80057","m@rossi.com");
insert into ordine(DATA_ORDINE,DATA_ARRIVO,ID,PREZZO,STATO,CLIENTE,DATI_SPEDIZIONE) values ('2022-01-01','2022-01-06',1,20,"OK","m@rossi.com",12);
insert into account(username,password) values ("SVerdi",MD5("pw1"));
insert into ruoli(account, ruolo) values(2,"GO");
insert into utente_registrato(genere,nome,cognome,email,nascita,account) values ("F","Sara","Verdi","s@verdi.com","1989-01-22",2);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo) values(883, "prodotto1", "marchio1", "produttore1", "formato1", "descrizione1", 100, '4.35');
insert into carrello(cliente, prodotto, quantita) values("Mrossi", 883, 2);
insert into prodotto(id, nome, marchio, produttore, formato, descrizione, disponibilita, prezzo) values(884, "prodotto2", "marchio2", "produttore2", "formato2", "descrizione2", 100, '7.35');
insert into carrello(cliente, prodotto, quantita) values("Mrossi", 884, 2);