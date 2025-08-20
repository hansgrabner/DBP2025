import java.sql.*;
import java.util.ArrayList;

public class DBHelper {
    private Connection conn;

    // Verbindung öffnen
    public void connectToDatabase() {
        var url = "jdbc:sqlite:C:/LVs/DBP2025/UrlaubsverwaltungJDBC2025.db";
        try {
            conn = DriverManager.getConnection(url);
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

        String sql = "SELECT MAId, Vorname, Nachname, Email, Eintrittsdatum FROM Mitarbeitende";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("MAId");
                String vorname = rs.getString("Vorname");
                String nachname = rs.getString("Nachname");
                String email = rs.getString("Email");
                String eintrittsdatum = rs.getString("Eintrittsdatum");

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
        try (PreparedStatement pStmt = conn.prepareStatement(sql))
        {
            pStmt.setInt(1,maId);
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
        int rowsAffected=0;

        String updateSql = "Update Mitarbeitende SET Vorname=?, Nachname=?, Email=?, Eintrittsdatum=?  Where MAId=?";
        try (PreparedStatement pStmt = conn.prepareStatement(updateSql))
        {
            pStmt.setString(1,geaenderterMitarbeiter.vorname);
            pStmt.setString(2,geaenderterMitarbeiter.nachname);
            pStmt.setString(3,geaenderterMitarbeiter.email);
            pStmt.setString(4,geaenderterMitarbeiter.eintrittsdatum);
            pStmt.setInt(5,geaenderterMitarbeiter.id);

            rowsAffected=pStmt.executeUpdate();


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
}

 