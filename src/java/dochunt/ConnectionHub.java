/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt;

import java.sql.*;

/**
 *
 * @author henrychung
 */
public class ConnectionHub {
    public static final String host = "eceweb";
    public static final String url = "jdbc:mysql://" + host + ":3306/";
    public static final String nid = "ms4zaman";
    public static final String user = "user_" + nid;
    public static final String pwd = "password";

    public static void testConnection()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            con = getConnection();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, pwd);
        Statement stmt = null;
        try {
            con.createStatement();
            stmt = con.createStatement();
            stmt.execute("USE ece356db_" + nid);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }
}
