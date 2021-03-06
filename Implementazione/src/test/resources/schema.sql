CREATE TABLE IF NOT EXISTS ACCOUNT
(
	ID	INT	NOT NULL AUTO_INCREMENT,
	USERNAME VARCHAR(20) NOT NULL UNIQUE, 	
	PASSWORD VARCHAR(32) NOT NULL,	
	ORDER_COUNT INT NOT NULL,
	PRIMARY KEY(ID)	
);

CREATE TABLE IF NOT EXISTS RUOLI
(
	ACCOUNT	INT	NOT NULL,
	RUOLO VARCHAR(2)	NOT NULL,
	PRIMARY KEY(ACCOUNT, RUOLO),
	FOREIGN KEY(ACCOUNT) REFERENCES ACCOUNT(ID)
);

CREATE TABLE IF NOT EXISTS UTENTE_REGISTRATO
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

CREATE TABLE IF NOT EXISTS ORDINE 
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


CREATE TABLE IF NOT EXISTS CATEGORIA
(
	ID INT NOT NULL AUTO_INCREMENT,
	NOME VARCHAR(50) UNIQUE NOT NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS PRODOTTO
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

CREATE TABLE IF NOT EXISTS RIGA_ORDINE
(
	PRODOTTO INT NOT NULL,
	ORDINE VARCHAR(15) NOT NULL,
	QUANTITY SMALLINT NOT NULL,	
	PREZZO_AL_PEZZO	DECIMAL(6,2) NOT NULL,	
    PRIMARY KEY(PRODOTTO, ORDINE),
    FOREIGN KEY(PRODOTTO) REFERENCES PRODOTTO(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(ORDINE) REFERENCES ORDINE(ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS CARRELLO
(
	CLIENTE VARCHAR(20)	NOT NULL,
	PRODOTTO INT NOT NULL,
	QUANTITA INT NOT NULL,	
    PRIMARY KEY(CLIENTE, PRODOTTO),
	FOREIGN KEY(CLIENTE) REFERENCES ACCOUNT(USERNAME) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(PRODOTTO) REFERENCES PRODOTTO(ID) ON DELETE CASCADE ON UPDATE CASCADE
);