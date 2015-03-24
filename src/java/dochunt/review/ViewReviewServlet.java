/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.review;

import dochunt.ConnectionHub;
import dochunt.models.Review;
import dochunt.profile.PatientSearchResultsServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class ViewReviewServlet extends HttpServlet {

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
        String reviewId = request.getParameter("reviewId");

        try {
            Review review = queryReview(reviewId);

            if (review != null) {
                String prevReviewId = getPrevReviewId(
                        review.reviewId, review.doctorAlias);
                String nextReviewId = getNextReviewId(
                        review.reviewId, review.doctorAlias);

                request.setAttribute("review", review);
                request.setAttribute("prevReviewId", prevReviewId);
                request.setAttribute("nextReviewId", nextReviewId);
            }
        } catch (Exception ex) {
            Logger.getLogger(PatientSearchResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext()
            .getRequestDispatcher("/ViewReview.jsp")
            .forward(request, response);
    }

    private Review queryReview(String reviewId) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;

        Review review = null;
        try {
            connection = ConnectionHub.getConnection();
            String sql =
                    "SELECT " +
                    "  r.reviewID, " +
                    "  r.reviewee, " +
                    "  d.firstname, " +
                    "  d.lastname, " +
                    "  r.starRating, " +
                    "  r.comments, " +
                    "  r.creationDate " +
                    "FROM Review r " +
                    "INNER JOIN Doctor d ON (d.alias = r.reviewee) " +
                    "WHERE r.reviewID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, reviewId);

            ResultSet results = statement.executeQuery();
            if (results.next()) { // Only 1 result
                review = new Review();
                review.reviewId = reviewId;
                review.doctorAlias = results.getString("reviewee");
                review.doctorFirstName = results.getString("firstname");
                review.doctorLastName = results.getString("lastname");
                review.rating = results.getDouble("starRating");
                review.comments = results.getString("comments");
                review.date = results.getTimestamp("creationDate").getTime();
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return review;
    }

    public String getNextReviewId(String reviewId, String doctorAlias)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        String nextReviewId = null;
        try {
            connection = ConnectionHub.getConnection();

            String sql =
                    "SELECT reviewID " +
                    "FROM Review " +
                    "WHERE reviewID > ? " +
                    "  AND reviewee = ? " +
                    "ORDER BY reviewID ASC " +
                    "LIMIT 1 ";
            statement = connection.prepareStatement(sql);
            statement.setString(1, reviewId);
            statement.setString(2, doctorAlias);

            ResultSet results = statement.executeQuery();
            if (results.next()) { // should only have 1 result
                nextReviewId = results.getString("reviewID");
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return nextReviewId;
    }

    public String getPrevReviewId(String reviewId, String doctorAlias)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        String prevReviewId = null;
        try {
            connection = ConnectionHub.getConnection();

            String sql =
                    "SELECT reviewID " +
                    "FROM Review " +
                    "WHERE reviewID < ? " +
                    "  AND reviewee = ? " +
                    "ORDER BY reviewID DESC " +
                    "LIMIT 1 ";
            statement = connection.prepareStatement(sql);
            statement.setString(1, reviewId);
            statement.setString(2, doctorAlias);

            ResultSet results = statement.executeQuery();
            if (results.next()) { // should only have 1 result
                prevReviewId = results.getString("reviewID");
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return prevReviewId;
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
