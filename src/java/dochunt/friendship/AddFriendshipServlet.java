/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.friendship;

import dochunt.ConnectionHub;
import dochunt.profile.PatientSearchResultsServlet;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author henrychung
 */
public class AddFriendshipServlet extends HttpServlet {

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
        String requester = request.getParameter("requester");
        String requestee = request.getParameter("requestee");
        try {
            boolean isAlreadyFriends = isAlreadyFriends(requester, requestee);
            String addFriendStatus;
            if (isAlreadyFriends) {
                addFriendStatus = "You are already friends with " + requestee;
            } else {
                addFriendship(requester, requestee);
                addFriendStatus =
                        "You have added/requested a friendship with "
                        + requestee;
            }

            request.setAttribute("addFriendStatus", addFriendStatus);
            request.setAttribute("alias", requester);
            getServletContext()
                .getRequestDispatcher("/SeeFriendship.jsp")
                .forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PatientSearchResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isAlreadyFriends(String requester, String requestee)
            throws ClassNotFoundException, SQLException {
        Connection connection = ConnectionHub.getConnection();
        String sql =
                "SELECT 1 " +
                "FROM Friend " +
                "WHERE requester = ? " +
                "  AND requestee = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, requester);
        statement.setString(2, requestee);

        ResultSet results = statement.executeQuery();

        return results.next();
    }

    private void addFriendship(String requester, String requestee)
            throws ClassNotFoundException, SQLException {
        Connection connection = ConnectionHub.getConnection();

        String sql = "{CALL AddFriendship(?, ?)}";
        CallableStatement statement = connection.prepareCall(sql);

        statement.setString(1, requester);
        statement.setString(2, requestee);

        statement.execute();
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
