package viewer;

import java.sql.*;
import java.util.ArrayList;

public class DbConnection {
    public static ArrayList<String> connect(String databaseName) {
        Connection conn = null;
        ArrayList<String> listOfTables = new ArrayList<>();
        try {
            // db parameters
//            String url = "jdbc:sqlite:/home/muy/IdeaProjects/SQLite Viewer/SQLite Viewer/task/src/" + databaseName;
            String url = "jdbc:sqlite:" + databaseName;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            Statement statement = conn.createStatement();
            try (ResultSet tableNames = statement.executeQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%'")) {
                while (tableNames.next()) {
                    // Retrieve column values
                    String name = tableNames.getString("name");
                    listOfTables.add(name);

                    System.out.printf("\tName: %s%n", name);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return listOfTables.isEmpty() ? null : listOfTables;
    }
}
