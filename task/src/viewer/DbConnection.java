package viewer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DbConnection {
    public static ArrayList<ArrayList<String>> connect(String databaseName,String query) {
        Connection conn = null;
        ArrayList<ArrayList<String>> columnValues = new ArrayList<>();
        ArrayList<String> columnNames = new ArrayList<>();
        try {
            // db parameters
//            String url = "jdbc:sqlite:/home/muy/" + databaseName;
            String url = "jdbc:sqlite:" + databaseName;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int columnNumber = 1; columnNumber <= columnCount; columnNumber++) {
                    String name = metaData.getColumnName(columnNumber);
                    columnNames.add(name);
                }
                columnValues.add(columnNames);
                while (resultSet.next()) {
                    // Retrieve column values
                    ArrayList<String> data = new ArrayList<>();
                    for (int columnNumber = 1; columnNumber <= columnCount; columnNumber++) {
                        data.add(resultSet.getString(columnNumber));
                    }
                    columnValues.add(data);

                }
                System.out.println(columnNames);
                System.out.println(columnValues);
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
        return columnValues;
    }

    public static ArrayList<ArrayList<String>> runQuery(String database, String query) {

        return connect(database,query);
    }

    public static ArrayList<ArrayList<String>> runQuery(String database) {
        String query = "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%'";

        return connect(database,query);
    }
}
