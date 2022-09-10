package com.p1.ek.model.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static Connection db;

    public static Connection connectToDb() {
        if (db == null) {
            String url = "jdbc:sqlserver://localhost:1433;" +
                        "databaseName=master;"+
                        "TrustServerCertificate=True;"+
                        "user=sa;password=123456a@;";
            try {
                db = DriverManager.getConnection(url);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return db;
    }


}
