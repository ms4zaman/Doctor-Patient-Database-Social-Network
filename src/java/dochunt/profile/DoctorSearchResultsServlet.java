/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.profile;

import dochunt.ConnectionHub;
import dochunt.helpers.LoginUtil;
import dochunt.helpers.StringHelper;
import dochunt.models.Doctor;
import dochunt.models.LoginInfo;
import dochunt.models.Patient;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
 * @author Dylan
 */
public class DoctorSearchResultsServlet extends HttpServlet {

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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specializationName = request.getParameter("specializationName");
        String city = request.getParameter("city");
        String provId = request.getParameter("provId");
        String postalCode = request.getParameter("postalCode");
        boolean reviewedByFriend = request.getParameter("reviewedByFriend") != null;
        String comment = request.getParameter("comment");
        double rating = Double.parseDouble(request.getParameter("rating"));
        
        try {
            ArrayList<Doctor> doctors = searchDoctors(
                    patientAlias,
                    firstName,
                    lastName,
                    specializationName,
                    city,
                    provId,
                    postalCode,
                    reviewedByFriend,
                    comment,
                    rating);
            request.setAttribute("doctors", doctors);
        } catch (Exception ex) {
            Logger.getLogger(PatientSearchResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext()
                .getRequestDispatcher("/DoctorSearchResults.jsp")
                .forward(request, response);
    }

    private ArrayList<Doctor> searchDoctors(
            String patientAlias,
            String firstName,
            String lastName,
            String specializationName,
            String city,
            String provId,
            String postalCode,
            boolean reviewedByFriend,
            String comment,
            double rating) throws SQLException, ClassNotFoundException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;

        ArrayList<Doctor> doctors = new ArrayList<>();
        try {
            String sql =
                    "SELECT Doctor.alias, Doctor.firstName, Doctor.lastName, avg(starRating) rating, count(Distinct reviewID) numReviews " +
                    "FROM Doctor " +
                    "LEFT JOIN Review ON (Review.Reviewee = Doctor.alias) " +
                    "LEFT JOIN hasSpecialization ON (Doctor.alias = hasSpecialization.alias) " +
                    "LEFT JOIN Specialization ON (hasSpecialization.hasSpecialization = Specialization.specializationID) " +
                    "LEFT JOIN hasAddress ON (Doctor.alias = hasAddress.alias) " +
                    "LEFT JOIN Address ON (hasAddress.hasAddress = Address.addressID) " +
                    "WHERE TRUE ";
            if (!StringHelper.isNullOrEmpty(firstName)) {
                sql += " AND Doctor.firstName = ? ";
            }
            if (!StringHelper.isNullOrEmpty(lastName)) {
                sql += " AND Doctor.lastName = ? ";
            }
            if (!StringHelper.isNullOrEmpty(specializationName)) {
                sql += " AND Specialization.specializationName = ? ";
            }
            if (!StringHelper.isNullOrEmpty(city)) {
                sql += " AND Address.city = ? ";
            }
            if (!StringHelper.isNullOrEmpty(provId)) {
                sql += " AND Address.provinceID = ? ";
            }
            if (!StringHelper.isNullOrEmpty(postalCode)) {
                sql += " AND Address.postalCode LIKE ? ";
            }
            if (reviewedByFriend) {
                sql +=
                        " AND Reviewer IN ( " +
                        "  SELECT f.Requestee " +
                        "  FROM Friend f " +
                        "  WHERE Requester = ? " +
                        "  AND f.Requestee IN ( " +
                        "    SELECT i.Requester " +
                        "    FROM Friend i " +
                        "    WHERE i.Requestee = ? " +
                        "  ) " +
                        ") ";
            }
            if (!StringHelper.isNullOrEmpty(comment)) {
                sql += " AND comments like ? ";
            }
            sql += " GROUP BY Doctor.alias ";

            if (rating > 0) {
                sql += " HAVING avg(starRating) >= ? ";
            }

            connection = ConnectionHub.getConnection();
            statement = connection.prepareStatement(sql);

            int currentParamIndex = 1;
            if (!StringHelper.isNullOrEmpty(firstName)) {
                statement.setString(currentParamIndex++, firstName);
            }
            if (!StringHelper.isNullOrEmpty(lastName)) {
                statement.setString(currentParamIndex++, lastName);
            }
            if (!StringHelper.isNullOrEmpty(specializationName)) {
                statement.setString(currentParamIndex++, specializationName);
            }
            if (!StringHelper.isNullOrEmpty(city)) {
                statement.setString(currentParamIndex++, city);
            }
            if (!StringHelper.isNullOrEmpty(provId)) {
                statement.setString(currentParamIndex++, provId);
            }
            if (!StringHelper.isNullOrEmpty(postalCode)) {
                statement.setString(currentParamIndex++, "%" + postalCode + "%");
            }
            if (reviewedByFriend) {
                statement.setString(currentParamIndex++, patientAlias);
                statement.setString(currentParamIndex++, patientAlias);
            }
            if (!StringHelper.isNullOrEmpty(comment)) {
                statement.setString(currentParamIndex++, "%" + comment + "%");
            }
            if (rating > 0) {
                statement.setDouble(currentParamIndex++, rating);
            }

            ResultSet results = statement.executeQuery();

            while(results.next()) {
                Doctor doctor = new Doctor();
                doctor.alias = results.getString("alias");
                doctor.firstName = results.getString("firstName");
                doctor.lastName = results.getString("lastName");
                doctor.rating = results.getObject("rating") == null ?
                        -1 : results.getDouble("rating");
                doctor.numReviews = results.getInt("numReviews");

                doctors.add(doctor);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return doctors;
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
