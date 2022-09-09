package com.p1.ek.model.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private Connection db;
    public Connection getDb() { return db; }
    public void setDb(Connection db) { this.db = db; }

    public DB() { db = null; }

    public void connectToDb() {
        if (db == null) {
            String url = "jdbc:sqlserver://localhost:1433;" +
                        "databaseName=bookstore;"+
                        "TrustServerCertificate=True;"+
                        "user=sa;password=P@SSWORD123;";
            try {
                db = DriverManager.getConnection(url);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
