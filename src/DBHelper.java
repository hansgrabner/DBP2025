import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBHelper {
    private Connection conn;

    // Verbindung öffnen
    public void connectToDatabase() {
        var url = "jdbc:sqlite:C:/LVs/DBP2025/UrlaubsverwaltungJDBC2025.db";
        try {
            conn = DriverManager.getConnection(url);

            conn.createStatement().execute("PRAGMA foreign_keys = ON");
            System.out.println("Verbindung zur SQLite DB hergestellt.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Verbinden: " + e.getMessage());
        }
    }

    // Verbindung schließen
    public void closeDatabaseConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Verbindung geschlossen.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Schließen: " + e.getMessage());
        }
    }

    // Mitarbeitende auslesen
    public ArrayList<Mitarbeitende> getMitarbeitende() {
        ArrayList<Mitarbeitende> mitarbeitendeList = new ArrayList<>();

        String searchVorname = "Donna";
        String sql = "SELECT MAId, Vorname, Nachname, Email, Eintrittsdatum FROM Mitarbeitende";
        sql = "SELECT MAId, Vorname, Nachname, Email, Eintrittsdatum FROM Mitarbeitende";
        // schlecht lesbar wartbar sql += " WHERE Vorname='" + searchVorname + "' and email like '%jkljsdf%'";
        //besser einfacher sicherer schneller sql += " WHERE Vorname=? and email LIKE ?";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("MAId");
                String vorname = rs.getString("Vorname");
                String nachname = rs.getString("Nachname");
                String email = rs.getString("Email");
                String eintrittsdatum = rs.getString("Eintrittsdatum");
                String kommentar = rs.getString("Kommentar");
                //kommentar =""
                if (rs.wasNull() == true) {
                    kommentar = "wurde nicht vergeben, ist NULL";
                }

                Mitarbeitende m = new Mitarbeitende(id, vorname, nachname, email, eintrittsdatum);
                mitarbeitendeList.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Mitarbeitenden: " + e.getMessage());
        }

        return mitarbeitendeList;
    }


    public Mitarbeitende getMitarbeitenden(int maId) {
        Mitarbeitende mitarbeitender = new Mitarbeitende();

        String sql = "SELECT MAId, Vorname, Nachname, Email, Eintrittsdatum FROM Mitarbeitende Where MAId=?";
        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, maId);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("MAId");
                String vorname = rs.getString("Vorname");
                String nachname = rs.getString("Nachname");
                String email = rs.getString("Email");
                String eintrittsdatum = rs.getString("Eintrittsdatum");
                mitarbeitender = new Mitarbeitende(id, vorname, nachname, email, eintrittsdatum);
            } else {
                mitarbeitender = new Mitarbeitende(-1, "nicht vorhanden", "", "", "");
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Mitarbeitenden: " + e.getMessage());
        }

        return mitarbeitender;
    }

    public int updateMitarbeitenden(Mitarbeitende geaenderterMitarbeiter) {
        int rowsAffected = 0;

        String updateSql = "Update Mitarbeitende SET Vorname=?, Nachname=?, Email=?, Eintrittsdatum=?  Where MAId=?";
        try (PreparedStatement pStmt = conn.prepareStatement(updateSql)) {
            pStmt.setString(1, geaenderterMitarbeiter.vorname);
            pStmt.setString(2, geaenderterMitarbeiter.nachname);
            pStmt.setString(3, geaenderterMitarbeiter.email);
            pStmt.setString(4, geaenderterMitarbeiter.eintrittsdatum);
            pStmt.setInt(5, geaenderterMitarbeiter.id);

            rowsAffected = pStmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Fehler beim Ändern eines Mitarbeitendn: " + e.getMessage());
        }

        return rowsAffected;
    }

    // Urlaubstage pro Mitarbeiter berechnen
    public void getUrlaubProMitarbeiter() {
        String sql = " SELECT m.Vorname, m.Nachname,                  SUM(julianday(u.Enddatum) - julianday(u.Startdatum) + 1) AS Urlaubstage             FROM Mitarbeitende m             LEFT JOIN Urlaube u ON m.MAId = u.MitarbeiterId             GROUP BY m.MAId, m.Vorname, m.Nachname             ";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("Vorname") + " " + rs.getString("Nachname");
                int tage = rs.getInt("Urlaubstage");
                System.out.printf("%s hat %d Urlaubstage.%n", name, tage);
            }

        } catch (SQLException e) {
            System.out.println("Fehler bei Urlaubsauswertung: " + e.getMessage());
        }
    }

    // 🆕 Einen Mitarbeitenden löschen (Aufgabenstellung)
    // Übergabe: ID eines Mitarbeitenden
    // Rückgabe: Anzahl der gelöschten Zeilen (0 = keiner gefunden, 1 = gelöscht)
    public int deleteMitarbeitender(int maId) {
        int rowsAffected = 0;
        String sql = "DELETE FROM Mitarbeitende WHERE MAId=?";
        // String eingabe=" 17 OR 1=1; DELETE FROM Kunden";
        //String boeserString="Select * from kunden where id = " + eingabe;

        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            // ID wird an das SQL übergeben (statt direkt im String → Schutz vor SQL-Injection)
            pStmt.setInt(1, maId);

            // Ausführung des DELETE-Befehls
            rowsAffected = pStmt.executeUpdate();

            /*
            if (rowsAffected==0){
                System.out.println("Mitarbeitender mit der ID " + maId + " wurde nicht gefunden.");
            }else {
                System.out.println("Mitarbeitender mit ID " + maId + " gelöscht.");
            }*/

        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen: " + e.getMessage());
        }

        return rowsAffected;
    }


    // 🆕 Einen neuen Mitarbeitenden einfügen (Aufgabenstellung)
    // Übergabe: Objekt vom Typ Mitarbeitende (id = -1, weil DB sie selbst vergibt)
    // Rückgabe: Die von der DB vergebene ID
    public int insertNewMitarbeitender(Mitarbeitende neuerMitarbeiter) {
        int newId = -1;

        // SQL-Statement: ID NICHT setzen → DB generiert automatisch eine neue
        String sql = "INSERT INTO Mitarbeitende (Vorname, Nachname, Email, Eintrittsdatum) VALUES (?, ?, ?, ?)";

        // SELECT last_insert_rowid();
        try (PreparedStatement pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Werte aus dem übergebenen Objekt einsetzen
            pStmt.setString(1, neuerMitarbeiter.vorname);
            pStmt.setString(2, neuerMitarbeiter.nachname);
            pStmt.setString(3, neuerMitarbeiter.email);
            pStmt.setString(4, neuerMitarbeiter.eintrittsdatum);

            // Ausführung des INSERT-Befehls
            int rows = pStmt.executeUpdate();

            if (rows > 0) {
                // Abfragen der automatisch vergebenen ID
                try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        newId = generatedKeys.getInt(1);
                        // ID auch im Objekt speichern → bleibt im Programm verfügbar
                        neuerMitarbeiter.id = newId;
                    }
                }
            }

            System.out.println("Neuer Mitarbeitender eingefügt mit ID = " + newId);

        } catch (SQLException e) {
            System.out.println("Fehler beim Einfügen: " + e.getMessage());
        }

        return newId;
    }


    public int insertNewUrlaubsArt(String code, String bezeichnung) throws SQLException {
        int urlaubsartId = -99;

        String sql = "INSERT INTO Urlaubsarten (Code, Bezeichnung) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, code);
            ps.setString(2, bezeichnung);
            int rowsAffect = ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    urlaubsartId = rs.getInt(1);
                }
            }
        }

        return urlaubsartId;
    }

    public void addUrlaub(int MAId, int UId, String start, String end) {

        // language=SQLite
        String sql = "INSERT INTO Urlaube (MitarbeiterId, UrlaubsartId, Startdatum, Enddatum)  VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, MAId);
            ps.setInt(2, UId);
            ps.setString(3, start);
            ps.setString(4, end);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("New Urlaub was added");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // 🆕 3. Urlaubsart löschen
//    Übergabe: ID der Urlaubsart
//    Rückgabe: Anzahl der gelöschten Zeilen (0 = nichts gelöscht)
//    ⚠️ Achtung: Löschen geht nur, wenn KEIN Urlaub mehr mit dieser Urlaubsart existiert
    public int deleteUrlaubsart(int urlaubsartId) {
        int rowsAffected = 0;
        String sql = "DELETE FROM Urlaubsarten WHERE UrlaubsartId = ?";

        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, urlaubsartId);
            rowsAffected = pStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Urlaubsart mit ID " + urlaubsartId + " gelöscht.");
            } else {
                System.out.println("Keine Urlaubsart gelöscht (evtl. noch mit Urlaube verknüpft).");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen der Urlaubsart: " + e.getMessage());
        }

        return rowsAffected;
    }

    public void printMitarbeiterMitMaxUrlaubOhneZaArt() {
        Map<Integer, Integer> sumById = new HashMap<>();
        Map<Integer, String> nameById = new HashMap<>();
        // language=SQLite
        String query = "SELECT m.MAId, m.Vorname, m.Nachname, u.Startdatum, u.Enddatum, ua.Code " +
                "FROM  Urlaube AS u JOIN Mitarbeitende AS m " +
                "ON u.MitarbeiterId = m.MAId " +
                "LEFT JOIN Urlaubsarten ua ON u.UrlaubsartId = ua.UrlaubsartId" +
                " WHERE Code!='ZA'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int MAId = rs.getInt(1);
                String vorname = rs.getString(2);
                String nachname = rs.getString(3);
                String start = rs.getString(4);
                String end = rs.getString(5);
                String code = rs.getString(6);

                if (code.equalsIgnoreCase("za")) {
                    continue;
                }
                LocalDate startDate = LocalDate.parse(start);
                LocalDate endDate = LocalDate.parse(end);

                int tage = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

                int sum = sumById.getOrDefault(MAId, 0);
                sumById.put(MAId, sum + tage);

                nameById.putIfAbsent(MAId, vorname + " " + nachname);

            }

            int maxDays = 0;
            for (Map.Entry<Integer, Integer> entry : sumById.entrySet()) {
                int days = entry.getValue();
                if (days > maxDays) {
                    maxDays = days;
                }
            }

            for (Map.Entry<Integer, Integer> entry : sumById.entrySet()) {
                if (maxDays == entry.getValue()) {
                    String name = nameById.get(entry.getKey());
                    System.out.println("Meisten Urlaubstage (" + maxDays + " Tage) (ohne ZA Urlaubsart) hat " + name);
                }
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
}





 