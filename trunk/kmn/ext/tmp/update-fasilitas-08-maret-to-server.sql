DROP TABLE DB2ADMIN.MFASILITAS;

CREATE TABLE DB2ADMIN.MFASILITAS (
		ID INTEGER NOT NULL,
		CKDFASILITAS CHAR(3) NOT NULL,
		CNAMA_FASILITAS VARCHAR(35) NOT NULL,
		DCREATEDDATE DATE,
		CCREATEDBY VARCHAR(35),
		DUPDATEDDATE DATE,
		CUPDATEDBY VARCHAR(35)
	);

ALTER TABLE DB2ADMIN.MFASILITAS ADD CONSTRAINT PK_FASILITAS PRIMARY KEY ( ID) ADD CONSTRAINT UN_KDFAS UNIQUE ( CKDFASILITAS) ;

ALTER TABLE DB2ADMIN.MFASILITASRUANG ADD CONSTRAINT FK_FAS_FASRUANG FOREIGN KEY (FASILITAS_ID) REFERENCES DB2ADMIN.MFASILITAS (ID)  ON DELETE NO ACTION ON UPDATE NO ACTION ENFORCED  ENABLE QUERY OPTIMIZATION ;

commit;