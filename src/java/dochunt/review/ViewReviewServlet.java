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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            request.setAttribute("review", review);
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
                review.rating = results.getInt("starRating");
                review.comments = results.getString("comments");
                review.date = results.getDate("creationDate").getTime();
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
