/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.profile;

import dochunt.ConnectionHub;
import dochunt.models.Address;
import dochunt.models.Doctor;
import dochunt.models.Province;
import dochunt.models.Review;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class DoctorProfileServlet extends HttpServlet {

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
        String doctorAlias = request.getParameter("doctorAlias");

        try {
            Doctor doctor = queryDoctor(doctorAlias);
            if (doctor != null) {
                doctor.specializations.addAll(
                        querySpecializationsForDoctor(doctorAlias));
                doctor.addresses.addAll(
                        queryAddressesForDoctor(doctorAlias));
                doctor.reviews.addAll(
                        queryReviewsForDoctor(doctorAlias));

                double avgRating = 0;
                int numReviews = doctor.reviews.size();
                for(Review review : doctor.reviews) {
                    avgRating += review.rating;
                }
                avgRating = numReviews > 0 ?
                        avgRating / numReviews : -1;

                doctor.rating = avgRating;
                doctor.numReviews = numReviews;

                request.setAttribute("doctor", doctor);
            }
        } catch (Exception ex) {
            Logger.getLogger(DoctorProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext()
            .getRequestDispatcher("/DoctorProfile.jsp")
            .forward(request, response);
    }

    private Doctor queryDoctor(String doctorAlias) throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        Doctor doctor = null;

        try {
            connection = ConnectionHub.getConnection();

            String sql =
                    "SELECT firstName, lastName, gender, email, licenseYear " +
                    "FROM Doctor " +
                    "WHERE Doctor.alias = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, doctorAlias);

            ResultSet results = statement.executeQuery();
            if (results.next()) { // Should have only 1 result
                doctor = new Doctor();
                doctor.alias = doctorAlias;
                doctor.firstName = results.getString("firstName");
                doctor.lastName = results.getString("lastName");
                doctor.gender = results.getInt("gender");
                doctor.email = results.getString("email");

                int licenseYear = results.getInt("licenseYear");
                int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                doctor.numYearsLicensed = thisYear - licenseYear;
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return doctor;
    }

    private ArrayList<Address> queryAddressesForDoctor(String doctorAlias)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        ArrayList<Address> addresses = new ArrayList<>();
        try {
            connection = ConnectionHub.getConnection();

            String sql =
                    "SELECT Address.number, streetName, city, provinceName, Address.provinceID, postalCode " +
                    "FROM Address " +
                    "JOIN Province, hasAddress " +
                    "WHERE Province.provinceID = Address.provinceID " +
                    "  AND hasAddress.hasAddress = Address.addressID " +
                    "  AND hasAddress.alias = ? ";
            statement = connection.prepareStatement(sql);
            statement.setString(1, doctorAlias);

            ResultSet results = statement.executeQuery();
            while(results.next()) {
                Province province = new Province(
                        results.getString("provinceID"),
                        results.getString("provinceName"));

                Address address = new Address();
                address.number = results.getInt("number");
                address.streetName = results.getString("streetName");
                address.city = results.getString("city");
                address.postalCode = results.getString("postalCode");
                address.province = province;

                addresses.add(address);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return addresses;
    }

    private ArrayList<String> querySpecializationsForDoctor(String doctorAlias)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        ArrayList<String> specializations = new ArrayList<>();
        try {
            connection = ConnectionHub.getConnection();

            String sql =
                "SELECT specializationName " +
                "FROM Specialization " +
                "JOIN hasSpecialization " +
                "WHERE hasSpecialization.hasSpecialization = Specialization.specializationID " +
                "AND hasSpecialization.alias = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, doctorAlias);

            ResultSet results = statement.executeQuery();
            while(results.next()) {
                specializations.add(
                        results.getString("specializationName"));
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return specializations;
    }

    private ArrayList<Review> queryReviewsForDoctor(String doctorAlias)
            throws NamingException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        ArrayList<Review> reviews = new ArrayList<>();
        try {
            connection = ConnectionHub.getConnection();

            String sql =
                    "SELECT reviewID, reviewer, starRating, comments, creationDate " +
                    "FROM Review " +
                    "WHERE reviewee = ? " +
                    "ORDER BY reviewID DESC ";
            statement = connection.prepareStatement(sql);
            statement.setString(1, doctorAlias);

            ResultSet results = statement.executeQuery();
            while(results.next()) {
                // TODO: Do we want to query the reviewer? Confidentiality?
                Review review = new Review();
                review.reviewId = results.getString("reviewID");
                review.rating = results.getDouble("starRating");
                review.comments = results.getString("comments");
                review.date = results.getTimestamp("creationDate").getTime();

                reviews.add(review);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return reviews;
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
