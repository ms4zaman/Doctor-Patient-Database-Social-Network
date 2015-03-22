/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt;

import dochunt.models.LoginInfo;
import dochunt.profile.PatientSearchResultsServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author henrychung
 */
public class UserLoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String alias = request.getParameter("alias");
        String password = request.getParameter("password");

        try {
            String saltedPassword = saltPassword(password);
            int userLevel = getUserLevel(alias, saltedPassword);

            if (userLevel == -1) { // authentication failed
                request.setAttribute("auth", "Failed to login");
                getServletContext()
                    .getRequestDispatcher("/UserLogin.jsp")
                    .forward(request, response);
            } else {
                HttpSession session = request.getSession();
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.alias = alias;
                loginInfo.level = userLevel;

                session.setAttribute("loginInfo", loginInfo);

                response.sendRedirect("index.jsp");
            }
        } catch (Exception ex) {
            Logger.getLogger(PatientSearchResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String saltPassword(String password)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;

        String saltedPassword = null;
        try {
            connection = ConnectionHub.getConnection();

            String sql = "SELECT PASSWORD(?) as salted_password";
            statement = connection.prepareStatement(sql);
            statement.setString(1, password);

            ResultSet results = statement.executeQuery();
            results.next();

            saltedPassword = results.getString("salted_password");
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return saltedPassword;
    }

    // -1 means user failed to authenticate
    private int getUserLevel(String alias, String saltedPassword)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;

        int userLevel = -1;
        try {
            connection = ConnectionHub.getConnection();
            String sql =
                    "SELECT level " +
                    "FROM Users " +
                    "WHERE alias = ? " +
                    "  AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, alias);
            statement.setString(2, saltedPassword);

            ResultSet results = statement.executeQuery();
            if (results.next()) { // There should only be 1 result
                userLevel = results.getInt("level");
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return userLevel;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
