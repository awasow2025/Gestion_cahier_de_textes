package gestion_cahiers_textes.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnection {
    private static final String DB_HOST = "localhost";
    private static final String DB_NAME = "gestion_cahiers_textes";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://" + DB_HOST + "/" + DB_NAME + "?useSSL=false",
                DB_USER,
                DB_PASSWORD
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String checkCredentials(String username, String password) {
        try (Connection conn = getConnection()) {
            if (conn == null) return null;

            String query = "SELECT role FROM utilisateur WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role"); // retourne le rôle
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
