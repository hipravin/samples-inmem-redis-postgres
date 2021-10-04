CREATE SEQUENCE EMP_ID_SEQ
    START WITH 2000
    INCREMENT BY 1000;

CREATE TABLE EMPLOYEE (
                          ID NUMERIC(19,0) NOT NULL PRIMARY KEY,
                          EMAIL VARCHAR(50) NOT NULL,
                          FIRST_NAME VARCHAR(50) NOT NULL,
                          LAST_NAME VARCHAR(50) NOT NULL
);

CREATE TABLE EMPLOYEE_JSONB (
                                ID NUMERIC(19,0) NOT NULL PRIMARY KEY,
                                CONTENT JSONB,
                                CONSTRAINT fk_employee
                                    FOREIGN KEY(ID)
                                        REFERENCES EMPLOYEE(ID)
);



CREATE INDEX idxgin ON EMPLOYEE_JSONB USING GIN (content);

create index EMAIL_IDX on EMPLOYEE(email);
