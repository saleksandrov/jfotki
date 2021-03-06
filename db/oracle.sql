

CREATE TABLE ALBUM
(
  A_ID       NUMBER                             NOT NULL,
  A_CREATOR  VARCHAR2(100),
  A_NAME     VARCHAR2(100)                      NOT NULL,
  A_USER_ID  NUMBER                             NOT NULL
)
/



CREATE TABLE PICTURE
(
  A_ID             NUMBER                       NOT NULL,
  A_NAME           VARCHAR2(100),
  A_DESCRIPTION    VARCHAR2(2000),
  A_NOTE           VARCHAR2(100),
  A_BIG_PICTURE    BLOB,
  A_SMALL_PICTURE  BLOB,
  A_ALBUM_ID       NUMBER
)
/



CREATE TABLE SECURITY_ROLE
(
  A_ID        NUMBER                            NOT NULL,
  A_ROLENAME  VARCHAR2(100)                     NOT NULL
)
/



CREATE TABLE SECURITY_ROLE_USER
(
  A_USER_ID  NUMBER                             NOT NULL,
  A_ROLE_ID  NUMBER                             NOT NULL
)
/



CREATE TABLE SECURITY_USER
(
  A_ID        NUMBER                            NOT NULL,
  A_USERNAME  VARCHAR2(100)                     NOT NULL,
  A_PASSWORD  VARCHAR2(100)                     NOT NULL,
  A_FULLNAME  VARCHAR2(300),
  A_EMAIL     varchar2(100),
  A_ENABLED   INTEGER
)
/



ALTER TABLE ALBUM ADD (
  PRIMARY KEY (A_ID))
/


ALTER TABLE PICTURE ADD (
  PRIMARY KEY (A_ID))
/


ALTER TABLE SECURITY_ROLE ADD (
  PRIMARY KEY (A_ID))
/


ALTER TABLE SECURITY_USER ADD (
  PRIMARY KEY (A_ID))
/


ALTER TABLE PICTURE ADD (
  CONSTRAINT FK_ALBUM_ID FOREIGN KEY (A_ALBUM_ID) 
    REFERENCES ALBUM (A_ID))
/


ALTER TABLE SECURITY_ROLE_USER ADD (
  CONSTRAINT FK_SECURITY_ROLE_ID FOREIGN KEY (A_ROLE_ID) 
    REFERENCES SECURITY_ROLE (A_ID))
/

ALTER TABLE SECURITY_ROLE_USER ADD (
  CONSTRAINT FK_SECURITY_USER_ID FOREIGN KEY (A_USER_ID) 
    REFERENCES SECURITY_USER (A_ID))
/

ALTER TABLE ALBUM ADD (
  CONSTRAINT FK_USER_ID FOREIGN KEY (A_USER_ID) 
    REFERENCES SECURITY_USER (A_ID))
/

CREATE UNIQUE INDEX UN_USERNAME ON SECURITY_USER (A_USERNAME)
/



ALTER TABLE SECURITY_USER ADD (
  CONSTRAINT UN_USERNAME UNIQUE (A_USERNAME))
/


CREATE SEQUENCE ALBUM_SEQ INCREMENT BY 1
/
CREATE SEQUENCE PICTURE_SEQ INCREMENT BY 1
/
CREATE SEQUENCE SECURITY_ROLE_SEQ INCREMENT BY 1
/
CREATE SEQUENCE SECURITY_USER_SEQ INCREMENT BY 1
/


CREATE OR REPLACE TRIGGER ALBUM_TR
BEFORE INSERT
ON ALBUM 
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
BEGIN
     SELECT album_seq.NEXTVAL INTO :NEW.a_id FROM DUAL;
END;
/




CREATE OR REPLACE TRIGGER PICTURE_TR
BEFORE INSERT
ON PICTURE 
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
BEGIN
     SELECT picture_seq.NEXTVAL INTO :NEW.a_id FROM DUAL;
END;
/




CREATE OR REPLACE TRIGGER SECURITY_ROLE_TR
BEFORE INSERT
ON SECURITY_ROLE 
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
BEGIN
     SELECT security_role_seq.NEXTVAL INTO :NEW.a_id FROM DUAL;
END;
/




CREATE OR REPLACE TRIGGER SECURITY_USER_TR
BEFORE INSERT
ON SECURITY_USER 
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
BEGIN
     SELECT security_user_seq.NEXTVAL INTO :NEW.a_id FROM DUAL;
END;
/






