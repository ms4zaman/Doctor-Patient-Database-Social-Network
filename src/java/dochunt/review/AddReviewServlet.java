/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.review;

import dochunt.ConnectionHub;
import dochunt.helpers.LoginUtil;
import dochunt.models.LoginInfo;
import dochunt.profile.PatientSearchResultsServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class AddReviewServlet extends HttpServlet {

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
        LoginInfo loginInfo = LoginUtil.getLoggedInUser(request.getSession());
        String patientAlias = loginInfo.alias;
        String doctorAlias = request.getParameter("doctorAlias"); // hidden element on form
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comments = request.getParameter("comments");

        try {
            addReview(patientAlias, doctorAlias, rating, comments);
        } catch (Exception ex) {
            Logger.getLogger(PatientSearchResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO: Redirect to doctor's profile
        response.sendRedirect("index.jsp");
    }

    private void addReview(
            String patientAlias,
            String doctorAlias,
            int rating,
            String comments) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionHub.getConnection();
            String sql =
                    "INSERT INTO Review "
                    + "(reviewer, reviewee, starRating, comments, creationDate) "
                    + "VALUES (?, ?, ?, ?, NOW())";
            statement = connection.prepareCall(sql);

            statement.setString(1, patientAlias);
            statement.setString(2, doctorAlias);
            statement.setInt(3, rating);
            statement.setString(4, comments);

            statement.execute();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
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
