
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        connect();
        System.out.println("Hello, World!");
    }

    public static void connect() {
        // connection string
        var url = "jdbc:sqlite:C:/LVs/DBP2025/chinook.db";

        try (var conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}