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

CREATE TABLE UTENTE_REGISTRATO
(
	GENERE CHAR NOT NULL, 
	NOME VARCHAR(20) NOT NULL, 
	COGNOME VARCHAR(20) NOT NULL, 
	USERNAME VARCHAR(20) NOT NULL,  
	EMAIL VARCHAR(40) NOT NULL UNIQUE,
    PW VARCHAR(32) NOT NULL,
    NASCITA DATE NOT NULL,
    RUOLO CHAR(2) NOT NULL,
    PRIMARY KEY(USERNAME)
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
	FOREIGN KEY(CLIENTE) REFERENCES UTENTE_REGISTRATO(USERNAME) ON DELETE CASCADE ON UPDATE CASCADE
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
    FOREIGN KEY(CLIENTE) REFERENCES UTENTE_REGISTRATO(USERNAME) ON DELETE CASCADE ON UPDATE CASCADE,
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
	FOREIGN KEY(CLIENTE) REFERENCES UTENTE_REGISTRATO(USERNAME) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(PRODOTTO) REFERENCES PRODOTTO(ID) ON DELETE CASCADE ON UPDATE CASCADE
);


insert into utente_registrato(genere,nome,cognome,username,email,pw,nascita,ruolo) values ("M","Mario", "Rossi", "Mrossi","m@rossi.com", MD5("pw"),"1999-07-25", "CL");