
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        connect();
        System.out.println("Hello, World!");
    }

    /* Database
     1. Neue DB anlegen - SQLite Studio Add Database UrlaubsverwaltungJDBC2025.db
     CREATE TABLE Mitarbeitende (
     CREATE TABLE Urlaubsarten (
     CREATE TABLE Urlaube (

     CREATE TABLE Mitarbeitende (
    MAId           INTEGER PRIMARY KEY
                           NOT NULL,
    Vorname        TEXT    NOT NULL,
    Nachname       TEXT,
    Email          TEXT    UNIQUE,
    Eintrittsdatum TEXT
);


CREATE TABLE Urlaubsarten (
    UrlaubsartId INTEGER PRIMARY KEY,
    Code         TEXT    NOT NULL
                         UNIQUE,
    Bezeichnung  TEXT    NOT NULL
);

CREATE TABLE Urlaube (
    UrlaubId      INTEGER PRIMARY KEY,
    MitarbeiterId INTEGER NOT NULL,
    UrlaubsartId  INTEGER NOT NULL,
    Startdatum    TEXT    NOT NULL,
    Enddatum      TEXT    NOT NULL,
    Kommentar     TEXT,
    FOREIGN KEY (
        MitarbeiterId
    )
    REFERENCES Mitarbeitende (MAId) ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    FOREIGN KEY (
        UrlaubsartId
    )
    REFERENCES Urlaubsarten (UrlaubsartId) ON UPDATE CASCADE,
    CHECK (julianday(enddatum) >= julianday(startdatum) )
);

INSERT INTO Mitarbeitende(Vorname,Nachname,email) VALUES('Johann','Grabner','j@g.at');
INSERT INTO Mitarbeitende(Vorname,Nachname,email) VALUES('Donna','Rohm','52418206@edu.campus02.at');

INSERT INTO Urlaubsarten(Code,Bezeichnung) VALUES('U','Urlaub');
INSERT INTO Urlaubsarten(Code,Bezeichnung) VALUES('ZA','Zeitausgleich');

INSERT INTO Urlaube(MitarbeiterId, UrlaubsartId, Startdatum, Enddatum,Kommentar)
VALUES(1,2,'01.07.2025','03.07.2025','Erholung');

INSERT INTO Urlaube(MitarbeiterId, UrlaubsartId, Startdatum, Enddatum,Kommentar)
VALUES(2,1,'03.05.2025','09.05.2025','echter Urlaub - Erholung pur ohne Arbeit und FH');

SELECT m.MAId, m.Vorname, ua.Code, ua.Bezeichnung, u.Startdatum, u.Enddatum, u.Kommentar
FROM  Urlaube AS u FULL OUTER JOIN Mitarbeitende AS m
ON u.MitarbeiterId = m.MAId
FULL OUTER JOIN Urlaubsarten AS ua
ON ua.UrlaubsartId = u.UrlaubsartId;

select * from Mitarbeitende;
SELECT * FROM Urlaube;



     Connection String anpassen



     */

    public static void connect() {
        // connection string
        var url = "jdbc:sqlite:C:/LVs/DBP2025/UrlaubsverwaltungJDBC2025.db";
        try (var conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}