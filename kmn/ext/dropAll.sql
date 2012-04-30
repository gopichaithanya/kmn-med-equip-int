
-- table security
DROP TABLE FILIALE;
DROP SEQUENCE FILIALE_SEQ;
ALTER TABLE FILIALE DROP PRIMARY KEY;

DROP TABLE KUNDE;
DROP SEQUENCE KUNDE_SEQ;
ALTER TABLE KUNDE DROP PRIMARY KEY;

DROP TABLE ARTIKEL;
DROP SEQUENCE ARTIKEL_SEQ;
ALTER TABLE ARTIKEL DROP PRIMARY KEY;

DROP TABLE AUFTRAG;
DROP SEQUENCE AUFTRAG_SEQ;
ALTER TABLE AUFTRAG DROP PRIMARY KEY;

DROP TABLE AUFTRAGPOSITION;
DROP SEQUENCE AUFTRAGPOSITION_SEQ;
ALTER TABLE AUFTRAGPOSITION DROP PRIMARY KEY;

DROP TABLE BRANCHE;
DROP SEQUENCE BRANCHE_SEQ;
ALTER TABLE BRANCHE DROP PRIMARY KEY;

/* SECURITY */
DROP TABLE SEC_USER;
DROP SEQUENCE SEC_USER_SEQ;
ALTER TABLE SEC_USER DROP PRIMARY KEY;

DROP TABLE SEC_USERROLE;
DROP SEQUENCE SEC_USERROLE_SEQ;
ALTER TABLE SEC_USERROLE DROP PRIMARY KEY;

DROP TABLE SEC_ROLE;
DROP SEQUENCE SEC_ROLE_SEQ;
ALTER TABLE SEC_ROLE DROP PRIMARY KEY;

DROP TABLE SEC_ROLEGROUP;
DROP SEQUENCE SEC_ROLEGROUP_SEQ;
ALTER TABLE SEC_ROLEGROUP DROP PRIMARY KEY;

DROP TABLE SEC_GROUP;
DROP SEQUENCE SEC_GROUP_SEQ;
ALTER TABLE SEC_GROUP DROP PRIMARY KEY;

DROP TABLE SEC_GROUPRIGHT;
DROP SEQUENCE SEC_GROUPRIGHT_SEQ;
ALTER TABLE SEC_GROUPRIGHT DROP PRIMARY KEY;

DROP TABLE SEC_RIGHT;
DROP SEQUENCE SEC_RIGHT_SEQ;
ALTER TABLE SEC_RIGHT DROP PRIMARY KEY;
/* END: SECURITY */

DROP TABLE SYS_COUNTRYCODE;
DROP SEQUENCE SYS_COUNTRYCODE_SEQ;
ALTER TABLE SYS_COUNTRYCODE DROP PRIMARY KEY;

DROP TABLE SYS_IP4COUNTRY;
DROP SEQUENCE SYS_IP4COUNTRY_SEQ;
ALTER TABLE SYS_IP4COUNTRY DROP PRIMARY KEY;

DROP TABLE YOUTUBE_LINK;
DROP SEQUENCE YOUTUBE_LINK_SEQ;
ALTER TABLE YOUTUBE_LINK DROP PRIMARY KEY;

DROP TABLE IPC_IP2COUNTRY;
DROP SEQUENCE IPC_IP2COUNTRY_SEQ;
ALTER TABLE IPC_IP2COUNTRY DROP PRIMARY KEY;
 
DROP TABLE SEC_LOGINLOG;
DROP SEQUENCE SEC_LOGINLOG_SEQ;
ALTER TABLE SEC_LOGINLOG DROP PRIMARY KEY;
 
DROP TABLE LOG_IP2COUNTRY;
DROP SEQUENCE LOG_IP2COUNTRY_SEQ;
ALTER TABLE LOG_IP2COUNTRY DROP PRIMARY KEY;
 
DROP TABLE GUESTBOOK;
DROP SEQUENCE GUESTBOOK_SEQ;
ALTER TABLE GUESTBOOK DROP PRIMARY KEY;
 
DROP TABLE CALENDAR_EVENT;
DROP SEQUENCE CALENDAR_EVENT_SEQ;
ALTER TABLE CALENDAR_EVENT DROP PRIMARY KEY;

DROP TABLE APP_NEWS;
DROP SEQUENCE APP_NEWS_SEQ;
ALTER TABLE APP_NEWS DROP PRIMARY KEY;



DROP TABLE MSESIKULIAH;

DROP TABLE MHARI;

DROP TABLE TCUTIMHS;

DROP TABLE MGRADE;

DROP TABLE MSEKOLAH;

DROP TABLE MPEGAWAI;

DROP TABLE MCALAKADEMIK;

DROP TABLE MMHSPASCAKHS;

DROP TABLE MHISTKURSUSMHS;

DROP TABLE MHISTPNDDKMHS;

DROP TABLE MKEGIATAN;

DROP TABLE MPEMINATAN;

DROP TABLE MFASILITAS;

DROP TABLE MPROV;

DROP TABLE MUNIV;

DROP TABLE MKURMHS;

DROP TABLE TJADSIDANGTUGASMENULIS;

DROP TABLE MFASILITASRUANG;

DROP TABLE TJADUJIAN;

DROP TABLE MPANGKATGOLONGAN;

DROP TABLE TIRSPASCA;

DROP TABLE MBIDANGUSAHA;

DROP TABLE MKAB;

DROP TABLE MKEC;

DROP TABLE MHISTPANGKATMHS;

DROP TABLE THISTKERJA;

DROP TABLE TFEEDBACKINSTANSI;

DROP TABLE TFEEDBACKKULIAH;

DROP TABLE MKURIKULUM;

DROP TABLE MTBMTKL;

DROP TABLE MPPUMHSKHUSUS;

DROP TABLE TABSENDOSEN;

DROP TABLE MKEL;

DROP TABLE MPRODI;

DROP TABLE MPBAHASAMHS;

DROP TABLE TABSENMHS;

DROP TABLE TJADKULDETIL;

DROP TABLE MDETILKURIKULUM;

DROP TABLE MTERM;

DROP TABLE MSTATUSMHS;

DROP TABLE MKGTMHS;

DROP TABLE TPAKETKULIAH;

DROP TABLE MJENJANG;

DROP TABLE MALUMNI;

DROP TABLE MJABATAN;

DROP TABLE MKARYAMHS;

DROP TABLE MMAHASISWA;

DROP TABLE TJADKULMASTER;

DROP TABLE TLOGNILAI;

DROP TABLE TDAFTARTUGASMENULIS;

DROP TABLE TDAFTARWISUDA;

DROP TABLE MKODE_POS;

DROP TABLE MPRESTASIMHS;

DROP TABLE MRUANG;

/* drop the sequences */
DROP SEQUENCE MDETILKURIKULUM_SEQ;
DROP SEQUENCE MCALAKADEMIK_SEQ;
DROP SEQUENCE MFASILITAS_SEQ;
DROP SEQUENCE MFASILITASRUANG_SEQ;
DROP SEQUENCE MGRADE_SEQ;
DROP SEQUENCE MHARI_SEQ;
DROP SEQUENCE MHISTPNDDKMHS_SEQ;
DROP SEQUENCE MHISTKURSUSMHS_SEQ;
DROP SEQUENCE MHISTPANGKATMHS_SEQ;
DROP SEQUENCE MJABATAN_SEQ;
DROP SEQUENCE MJENJANG_SEQ;
DROP SEQUENCE MKARYAMHS_SEQ;
DROP SEQUENCE MKAB_SEQ;
DROP SEQUENCE MKEC_SEQ;
DROP SEQUENCE MKEL_SEQ;
DROP SEQUENCE MKEGIATAN_SEQ;
DROP SEQUENCE MKGTMHS_SEQ;
DROP SEQUENCE MKODE_POS_SEQ;
DROP SEQUENCE MKURIKULUM_SEQ;
DROP SEQUENCE MKURMHS_SEQ;
DROP SEQUENCE MMAHASISWA_SEQ;
DROP SEQUENCE MMHSPASCAKHS_SEQ;
DROP SEQUENCE MPANGKATGOLONGAN_SEQ;
DROP SEQUENCE MPBAHASAMHS_SEQ;
DROP SEQUENCE MPEGAWAI_SEQ;
DROP SEQUENCE MPEMINATAN_SEQ;
DROP SEQUENCE MPPUMHSKHUSUS_SEQ;
DROP SEQUENCE MPRESTASIMHS_SEQ;
DROP SEQUENCE MPRODI_SEQ;
DROP SEQUENCE MPROV_SEQ;
DROP SEQUENCE MRUANG_SEQ;
DROP SEQUENCE MSEKOLAH_SEQ;
DROP SEQUENCE MSESIKULIAH_SEQ;
DROP SEQUENCE MSTATUSMHS_SEQ;
DROP SEQUENCE MTBMTKL_SEQ;
DROP SEQUENCE MUNIV_SEQ;
DROP SEQUENCE TIRSPASCA_SEQ;
DROP SEQUENCE TPAKETKULIAH_SEQ;

commit;
 

