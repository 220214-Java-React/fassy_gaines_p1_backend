package com.revature.proj1.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Singleton Design Pattern
// -> a design through which you only ever create ONE SINGLE instance
public class ConnectionFactory {


    private static Connection instance;
    // "jdbc:<dialect>://<host name>:<port>/<database name>?currentSchema=<schema name>"
    private static final String url = "jdbc:postgresql://java-react.clat2x7sdylw.us-east-1.rds.amazonaws.com:5432/postgres?currentSchema=\"Reimbursements\"";
    private static final String username = "postgres";
    private static final String password = "Williammatanzaswilliam664";


    // this method creates a single instance if needed otherwise, returns the existing one.
    public static Connection getConnection() throws SQLException {
        try{
            Class.forName("org.postgresql.Driver");
        }catch(Exception e){
            e.printStackTrace();
        }
        return (instance == null) ? DriverManager.getConnection(url, username, password) : instance;
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
