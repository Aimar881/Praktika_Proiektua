-- =============================================================
--  NOTEN KUDEAKETA SISTEMA — DDL MySQL
--  Empresa B · Proyecto 1 · Java EE (javax) + MySQL
-- =============================================================

CREATE DATABASE IF NOT EXISTS empresa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE empresa;

CREATE USER IF NOT EXISTS 'noten_user'@'localhost' IDENTIFIED BY 'noten_pass';
GRANT ALL PRIVILEGES ON empresa.* TO 'noten_user'@'localhost';
FLUSH PRIVILEGES;

-- -------------------------------------------------------------
-- 1. ERABILTZAILEAK — tabla base (herencia)
-- -------------------------------------------------------------
CREATE TABLE Erabiltzaileak (
    nan           VARCHAR(9)   PRIMARY KEY,
    izen_abizenak VARCHAR(70)  NOT NULL,
    tlfn_zenbakia VARCHAR(9),
    pasahitza     VARCHAR(255) NOT NULL,
    rola          ENUM('irakasle','ikasle','tutore') NOT NULL,
    aktibo        BOOLEAN      NOT NULL DEFAULT TRUE,
    sortze_data   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- -------------------------------------------------------------
-- 2. IRAKASLEAK — especialización de Erabiltzaileak
-- -------------------------------------------------------------
CREATE TABLE Irakasleak (
    nan VARCHAR(9) PRIMARY KEY,
    FOREIGN KEY (nan) REFERENCES Erabiltzaileak(nan)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- -------------------------------------------------------------
-- 3. IKASLEAK — especialización de Erabiltzaileak
-- -------------------------------------------------------------
CREATE TABLE Ikasleak (
    nan    VARCHAR(9)  PRIMARY KEY,
    taldea VARCHAR(10),
    FOREIGN KEY (nan) REFERENCES Erabiltzaileak(nan)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- -------------------------------------------------------------
-- 4. IKASTURTEAK — curso académico
-- -------------------------------------------------------------
CREATE TABLE Ikasturteak (
    id       INT         AUTO_INCREMENT PRIMARY KEY,
    izena    VARCHAR(10) NOT NULL UNIQUE,
    hasiera  DATE        NOT NULL,
    bukaera  DATE        NOT NULL,
    aktibo   BOOLEAN     NOT NULL DEFAULT FALSE,
    CONSTRAINT chk_ikasturte_datak CHECK (bukaera > hasiera)
);

-- -------------------------------------------------------------
-- 5. IKASGAIAK — asignatura ligada a un profesor y curso
-- -------------------------------------------------------------
CREATE TABLE Ikasgaiak (
    id           INT          AUTO_INCREMENT PRIMARY KEY,
    izena        VARCHAR(100) NOT NULL,
    irakasle_nan VARCHAR(9)   NOT NULL,
    ikasturte_id INT          NOT NULL,
    FOREIGN KEY (irakasle_nan) REFERENCES Irakasleak(nan)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (ikasturte_id) REFERENCES Ikasturteak(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE KEY uq_ikasgaia_ikasturte (izena, irakasle_nan, ikasturte_id)
);

-- -------------------------------------------------------------
-- 6. MATRIKULAK — qué alumnos están en qué asignatura
-- -------------------------------------------------------------
CREATE TABLE Matrikulak (
    ikasle_nan     VARCHAR(9) NOT NULL,
    ikasgai_id     INT        NOT NULL,
    matrikula_data DATE       NOT NULL DEFAULT (CURDATE()),
    PRIMARY KEY (ikasle_nan, ikasgai_id),
    FOREIGN KEY (ikasle_nan) REFERENCES Ikasleak(nan)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ikasgai_id) REFERENCES Ikasgaiak(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- -------------------------------------------------------------
-- 7. EBALUAZIO_ZATIAK — partes de una asignatura con peso
-- -------------------------------------------------------------
CREATE TABLE Ebaluazio_Zatiak (
    id                 INT          AUTO_INCREMENT PRIMARY KEY,
    ikasgai_id         INT          NOT NULL,
    izena              VARCHAR(100) NOT NULL,
    pisua              DECIMAL(4,3) NOT NULL,
    ebaluazio_zenbakia TINYINT      NOT NULL,
    CONSTRAINT chk_pisua  CHECK (pisua > 0 AND pisua <= 1),
    CONSTRAINT chk_ebalua CHECK (ebaluazio_zenbakia IN (1, 2, 3)),
    FOREIGN KEY (ikasgai_id) REFERENCES Ikasgaiak(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY uq_zatia (ikasgai_id, izena, ebaluazio_zenbakia)
);

-- -------------------------------------------------------------
-- 8. NOTAK — nota de un alumno en una parte
-- -------------------------------------------------------------
CREATE TABLE Notak (
    ikasle_nan      VARCHAR(9)   NOT NULL,
    zati_id         INT          NOT NULL,
    nota_zenbakia   DECIMAL(4,2) NOT NULL,
    eguneratze_data TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
                                 ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (ikasle_nan, zati_id),
    CONSTRAINT chk_nota CHECK (nota_zenbakia >= 0 AND nota_zenbakia <= 10),
    FOREIGN KEY (ikasle_nan) REFERENCES Ikasleak(nan)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (zati_id) REFERENCES Ebaluazio_Zatiak(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- =============================================================
-- VISTAS
-- =============================================================

-- Nota pisatua por alumno, asignatura y evaluación
CREATE OR REPLACE VIEW v_ebaluazio_notak AS
SELECT
    n.ikasle_nan,
    ez.ikasgai_id,
    ez.ebaluazio_zenbakia,
    ROUND(SUM(n.nota_zenbakia * ez.pisua), 2) AS nota_pisatua
FROM Notak n
JOIN Ebaluazio_Zatiak ez ON n.zati_id = ez.id
GROUP BY n.ikasle_nan, ez.ikasgai_id, ez.ebaluazio_zenbakia;

-- Nota final por alumno y asignatura
CREATE OR REPLACE VIEW v_nota_finala AS
SELECT
    ikasle_nan,
    ikasgai_id,
    ROUND(AVG(nota_pisatua), 2)            AS nota_finala,
    COUNT(DISTINCT ebaluazio_zenbakia)     AS ebaluazio_kopurua
FROM v_ebaluazio_notak
GROUP BY ikasle_nan, ikasgai_id;

-- Boletín completo
CREATE OR REPLACE VIEW v_buletina AS
SELECT
    e.izen_abizenak     AS ikaslea,
    ik.taldea,
    ig.izena            AS ikasgaia,
    it.izena            AS ikasturtea,
    vn.ebaluazio_zenbakia,
    vn.nota_pisatua,
    vf.nota_finala,
    er.izen_abizenak    AS irakaslea
FROM v_ebaluazio_notak vn
JOIN v_nota_finala   vf  ON vn.ikasle_nan = vf.ikasle_nan
                        AND vn.ikasgai_id = vf.ikasgai_id
JOIN Erabiltzaileak  e   ON vn.ikasle_nan = e.nan
JOIN Ikasleak        ik  ON vn.ikasle_nan = ik.nan
JOIN Ikasgaiak       ig  ON vn.ikasgai_id = ig.id
JOIN Ikasturteak     it  ON ig.ikasturte_id = it.id
JOIN Irakasleak      ir  ON ig.irakasle_nan = ir.nan
JOIN Erabiltzaileak  er  ON ir.nan = er.nan;

-- =============================================================
-- DATOS DE PRUEBA
-- =============================================================

INSERT INTO Erabiltzaileak VALUES
  ('12345678A','Ane Miren Etxeberria','688001001','$2a$12$hashedpwd1','irakasle',TRUE,NOW()),
  ('87654321B','Jokin Aizpuru Zubieta','688002002','$2a$12$hashedpwd2','ikasle',TRUE,NOW()),
  ('11223344C','Leire Urrutia Goikoetxea','688003003','$2a$12$hashedpwd3','ikasle',TRUE,NOW()),
  ('44332211D','Patxi Dorronsoro','688004004','$2a$12$hashedpwd4','tutore',TRUE,NOW());

INSERT INTO Irakasleak VALUES ('12345678A');
INSERT INTO Ikasleak   VALUES ('87654321B','DAW2A'),('11223344C','DAW2A');

INSERT INTO Ikasturteak (izena, hasiera, bukaera, aktibo) VALUES
  ('2024-25','2024-09-16','2025-06-20',TRUE);

INSERT INTO Ikasgaiak (izena, irakasle_nan, ikasturte_id) VALUES
  ('Programazioa','12345678A',1),
  ('Datu-baseak','12345678A',1);

INSERT INTO Matrikulak VALUES
  ('87654321B',1,CURDATE()),('87654321B',2,CURDATE()),
  ('11223344C',1,CURDATE()),('11223344C',2,CURDATE());

INSERT INTO Ebaluazio_Zatiak (ikasgai_id,izena,pisua,ebaluazio_zenbakia) VALUES
  (1,'1.Ebal - Teoria',  0.400,1),(1,'1.Ebal - Praktika',0.600,1),
  (1,'2.Ebal - Teoria',  0.400,2),(1,'2.Ebal - Praktika',0.600,2),
  (1,'3.Ebal - Teoria',  0.400,3),(1,'3.Ebal - Praktika',0.600,3);

INSERT INTO Notak (ikasle_nan,zati_id,nota_zenbakia) VALUES
  ('87654321B',1,7.50),('87654321B',2,8.00),
  ('87654321B',3,6.00),('87654321B',4,7.00),
  ('87654321B',5,8.50),('87654321B',6,9.00);