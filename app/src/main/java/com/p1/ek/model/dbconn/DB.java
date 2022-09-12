package com.p1.ek.model.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static Connection db;

    public static Connection connectToDb() {
        if (db == null) {
            // String url = "jdbc:sqlserver://localhost:1433;" +
            //             "databaseName=master;"+
            //             "TrustServerCertificate=True;"+
            //             "user=sa;password=123456a@;";
            // String username = System.getenv("${env:dbusername");
            // String password = System.getenv("${env:dbpassword");
            // String username = "eugmaker@eugmyserver";
            // String password = "123456a@";
            String url = "jdbc:sqlserver://eugmyserver.database.windows.net:1433;"+
                            "database=masterkey;encrypt=true;"+
                            "trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            try {
                db = DriverManager.getConnection(url, Key.getUserName(), Key.getPassword());
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return db;
    }


}
