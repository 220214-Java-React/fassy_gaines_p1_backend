package com.revature.proj1.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Singleton Design Pattern
// -> a design through which you only ever create ONE SINGLE instance
public class ConnectionFactory {

    private static Connection instance;

    // "jdbc:<dialect>://<host name>:<port>/<database name>?currentSchema=<schema name>"
    private static final String url =

            "jdbc:postgresql://javareact.cjurl0s0kcsf.us-east-2.rds.amazonaws.com:5432/postgres?currentSchema=p1";

    private static final String username = "postgres";
    private static final String password = "12345678";


    // this method creates a single instance if needed otherwise, returns the existing one.
    public static Connection getConnection() throws SQLException {

        if (instance == null || instance.isClosed()) {
            // url, username, password ->
//            Class.forName("org.postgresql.Driver"); <- this may be necessary to solve no suitable driver
            instance = DriverManager.getConnection(url, username, password);
        }

        return instance;
    }

    private ConnectionFactory() {
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }


}
