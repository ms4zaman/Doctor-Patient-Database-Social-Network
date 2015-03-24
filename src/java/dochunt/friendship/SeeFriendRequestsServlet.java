/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.friendship;

import dochunt.ConnectionHub;
import dochunt.models.Patient;
import dochunt.profile.PatientSearchResultsServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author henrychung
 */
public class SeeFriendRequestsServlet extends HttpServlet {

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

        try {
            ArrayList<Patient> friendRequests = queryFriendRequests(alias);
            request.setAttribute("friendRequests", friendRequests);

            getServletContext()
                .getRequestDispatcher("/SeeFriendRequests.jsp")
                .forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PatientSearchResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Patient> queryFriendRequests(String alias) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;

        ArrayList<Patient> friendRequests = new ArrayList<>();
        try {
            connection = ConnectionHub.getConnection();

            String sql =
                    "SELECT " +
                    "  f.requester, " +
                    "  p.email " +
                    "FROM Friend f " +
                    "LEFT JOIN Patient p ON (p.alias = f.requestee) " +
                    "WHERE f.requestee = ? " +
                    "  AND NOT EXISTS( " +
                    "    SELECT 1 " +
                    "    FROM Friend inner_f " +
                    "    WHERE inner_f.requester = ? " +
                    "      AND inner_f.requestee = f.requester " +
                    "  )";
            statement = connection.prepareCall(sql);
            statement.setString(1, alias);
            statement.setString(2, alias);

            ResultSet results = statement.executeQuery();
            while(results.next()) {
                Patient patient = new Patient();
                patient.alias = results.getString("requester");
                patient.email = results.getString("email");
                friendRequests.add(patient);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return friendRequests;
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
