
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //connectAndDisplayMitarbeitende();
        //connectAndDisplayMitarbeiterUrlaub(1);
        System.out.println("Hello, World!");

        DBHelper myHelper =new DBHelper();
        myHelper.connectToDatabase();
        //get einen Mitarbeitenden

        /*
        Mitarbeitende mNr1 = myHelper.getMitarbeitenden(1);
        System.out.println(mNr1);
        mNr1.vorname="Karl";
        System.out.println(myHelper.updateMitarbeitenden(mNr1));
*/
        // Mitarbeitenden wieder löschen
        /*
        int maId=7;
        int rowsAffected = myHelper.deleteMitarbeitender(maId);
        if (rowsAffected==0){
            System.out.println("Mitarbeitender mit der ID " + maId + " wurde nicht gefunden.");
        }else {
            System.out.println("Mitarbeitender mit ID " + maId + " gelöscht.");
        }
        */

        //Delete mit Übergabe der ID

        //Aufgabe für Nachmittag
        //myHelpder.deleteMitarbeitender(1)
        //Mitarbeitender maNeu =new Mitarbeitender(-99,'Josef','Huber','','')
        //INSERT INTO Mitarbeitende(Vorname,Nachname,email) VALUES
        //myHelper.insertNewMitarbeitender(maNeu);
        //Der neue Mitarbeitende bekam die Id 37 ???

//AUFGABENSTELLUNG BEGINN: nur PreparedStatements verwenden

        // Neuen Mitarbeitenden hinzufügen
        /*
        Mitarbeitende maNeu = new Mitarbeitende(-1, "Josef", "Huber", "j.hubeabcr@mail.at", "2025-08-20");
        myHelper.insertNewMitarbeitender(maNeu);
        System.out.println("Neue ID vom DB vergeben: " + maNeu.id);
        //Insert mit auto-ID + Rückgabe der ID
        */


/*
    1 Insert neue Urlaubsart ----> Adventures --- > neue ID ist 3(Generated Keys)
    2. Insert eines Urlaubs mit der neuen Urlaubsart (MAId: 1, UrlaubsID: 3, 01.02.2025 04.05.2025
    3.Delete Urlaubsart VORSICHT ForeignKey-Constraint berücksichtigen


 */

        try
        {
            int urlaubsArtId= myHelper.insertNewUrlaubsArt("av3","Adventures3");
            myHelper.addUrlaub(1,urlaubsArtId,"01.09.2025","10.09.2025");
            myHelper.deleteUrlaubsart(urlaubsArtId);

        }
        catch (SQLException ex){

        }



//AUFGABENSTELLUNG ENDE

        /*
        ArrayList<Mitarbeitende> alleMitarbeitende = myHelper.getMitarbeitende();
        for (Mitarbeitende m : alleMitarbeitende) {
            System.out.println(m);
        }

        myHelper.getUrlaubProMitarbeiter();
*/

        /* eigene Helper-Klasse
        DbHelper myHelper =new DbHelper();
        myHelper.connectToDatbase();
        ArrayList<Mitarbeitende> alleMitarbeitende = myHelper.getMitarbeitende();
        getUrlaubProMitarbeiter ---- Johann 7 Tage, Donna 3 Tage
        myHelper.closeDatabaseConnectioin();
        */

        //1     5 + 7 + 3


/*
        System.out.println(System.lineSeparator() + "3️⃣ Alle Mitarbeitenden als Objekte aus einer ArrayList ausgeben: ");
        ArrayList<Mitarbeitende> alleMitarbeitende = getMitarbeitende();
        for (Mitarbeitende mitarbeiter : alleMitarbeitende) {
            System.out.println(mitarbeiter);
        }
*/
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

    public static void connectAndDisplayMitarbeitende() {
        // connection string



        var url = "jdbc:sqlite:C:/LVs/DBP2025/UrlaubsverwaltungJDBC2025.db";
        try (var conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "select MAId, Vorname, Nachname, Email, Eintrittsdatum from Mitarbeitende" );
            while ( rs.next() )
                System.out.printf( "%d, Vorname: %s, Nachname: %s Email: %s Eintritt: %s %n",
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                );
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connectAndDisplayMitarbeiterUrlaub(int mitarbeiterId) {
        // connection string
        var url = "jdbc:sqlite:C:/LVs/DBP2025/UrlaubsverwaltungJDBC2025.db";
        try (var conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
            Statement stmt = conn.createStatement();
            StringBuilder selectStringUrlaube =new StringBuilder("SELECT m.MAId, m.Vorname, ua.Code, ua.Bezeichnung, u.Startdatum, u.Enddatum, u.Kommentar\n" +
                    "FROM  Urlaube AS u JOIN Mitarbeitende AS m\n" +
                    "ON u.MitarbeiterId = m.MAId\n" +
                    " JOIN Urlaubsarten AS ua\n" +
                    "ON ua.UrlaubsartId = u.UrlaubsartId");

            selectStringUrlaube.append(" WHERE MAId=" + mitarbeiterId);
            ResultSet rs = stmt.executeQuery( selectStringUrlaube.toString());
            while ( rs.next() )
                System.out.printf( "%d, Vorname: %s, Code: %s Bezeichnung: %s Start: %s Ende: %s %n",
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //   O/R-Mapping von Relation zu Objekt
    public static ArrayList<Mitarbeitende> getMitarbeitende()  {
        var url = "jdbc:sqlite:C:/LVs/DBP2025/UrlaubsverwaltungJDBC2025.db";
        ArrayList<Mitarbeitende> mitarbeitendeList = new ArrayList<>();

        try ( var conn = DriverManager.getConnection(url)) {
            System.out.println("🛜 Connection to SQLite has been established.");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  MAId, Vorname, Nachname, Email, Eintrittsdatum FROM Mitarbeitende");

            while (rs.next()) {
                int id = rs.getInt("MAId");

                String vorname = rs.getString("Vorname");
                String nachname = rs.getString("Nachname");
                String email = rs.getString("Email");
                String eintrittsdatum = rs.getString("Eintrittsdatum");

                Mitarbeitende mitarbeiter = new Mitarbeitende(id, vorname, nachname, email, eintrittsdatum);
                mitarbeitendeList.add(mitarbeiter);
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Fehler bei der Datenbankoperation: " + e.getMessage());

        }
        return mitarbeitendeList;
    }

   // public static ArrayList<Mitarbeitende> getMitarbeitdeen(
    //anstelle von System.out soll im while ObjectArray befüllen
    //main
    //ArrayList<Mitarbeitende> =  getMitarbeitdeen();
}