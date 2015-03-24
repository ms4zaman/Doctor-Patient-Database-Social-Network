/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.profile;

import dochunt.ConnectionHub;
import dochunt.helpers.StringHelper;
import dochunt.models.Doctor;
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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specializationName = request.getParameter("specializationName");
        String city = request.getParameter("city");
        String provId = request.getParameter("provId");
        String postalCode = request.getParameter("postalCode");
        boolean reviewedByFriend = request.getParameter("reviewedByFriend") != null;
        String comment = request.getParameter("comment");
        
        try {
            ArrayList<Doctor> doctors = searchDoctors(
                    firstName,
                    lastName,
                    specializationName,
                    city,
                    provId,
                    reviewedByFriend,
                    comment);
            request.setAttribute("doctors", doctors);
        } catch (Exception ex) {
            Logger.getLogger(PatientSearchResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext()
                .getRequestDispatcher("/DoctorSearchResults.jsp")
                .forward(request, response);
    }

    private ArrayList<Doctor> searchDoctors(
            String firstName,
            String lastName,
            String specializationName,
            String city,
            String provId,
            boolean reviewedByFriend,
            String comment) throws SQLException, ClassNotFoundException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;

        ArrayList<Doctor> doctors = new ArrayList<>();
        try {
            String sql =
                    "";
            if (!StringHelper.isNullOrEmpty(firstName)) {
                sql += " AND pat.alias = ? ";
            }
            if (!StringHelper.isNullOrEmpty(lastName)) {
                sql += " AND pat.provinceID = ? ";
            }
            if (!StringHelper.isNullOrEmpty(specializationName)) {
                sql += " AND pat.city = ? ";
            }
            if (!StringHelper.isNullOrEmpty(city)) {
                sql += " AND pat.provinceID = ? ";
            }
            if (!StringHelper.isNullOrEmpty(provId)) {
                sql += " AND pat.provinceID = ? ";
            }
            if (reviewedByFriend) {
                // TODO: ??
            }
            if (!StringHelper.isNullOrEmpty(comment)) {
                sql += " AND pat.provinceID = ? ";
            }
            sql += " GROUP BY pat.alias";
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
            if (reviewedByFriend) {
                // TODO: ??
            }
            if (!StringHelper.isNullOrEmpty(comment)) {
                statement.setString(currentParamIndex++, comment);
            }

            ResultSet results = statement.executeQuery();

            while(results.next()) {
                Doctor doctor = new Doctor();

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
