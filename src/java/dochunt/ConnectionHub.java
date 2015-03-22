/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author henrychung
 */
public class ConnectionHub {
    public static final String host = "eceweb";
    public static final String url = "jdbc:mysql://" + host + ":3306/";
    public static final String nid = "dctittel";
    public static final String user = "user_" + nid;
    public static final String pwd = "user_" + nid;

    public static void testConnection()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            con = OLDgetConnection();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public static Connection getConnection() throws NamingException, SQLException {
        InitialContext cxt = new InitialContext();
        if (cxt == null) {
            throw new RuntimeException("Unable to create naming context");
        }
        Context dbContext = (Context)cxt.lookup("java:comp/env");
        DataSource ds = (DataSource)dbContext.lookup("jdbc/myDatasource");

        if (ds == null) {
            throw new RuntimeException("Data source not found");
        }
        Connection connection = ds.getConnection();
        Statement stmt = null;
        try {
            connection.createStatement();
            stmt = connection.createStatement();
            stmt.execute("USE ece356db_" + nid);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return connection;
    }

    public static Connection OLDgetConnection()
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
