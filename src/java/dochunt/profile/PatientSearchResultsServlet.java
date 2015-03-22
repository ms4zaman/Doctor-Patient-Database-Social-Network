/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.profile;

import dochunt.ConnectionHub;
import dochunt.helpers.StringHelper;
import dochunt.models.Patient;
import java.io.IOException;
import java.sql.CallableStatement;
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
 * @author henrychung
 */
public class PatientSearchResultsServlet extends HttpServlet {

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
        String provId = request.getParameter("provId");
        String city = request.getParameter("city");

        try {
            ArrayList<Patient> patients = searchPatients(alias, provId, city);
            request.setAttribute("patients", patients);
        } catch (Exception ex) {
            Logger.getLogger(PatientSearchResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext()
                .getRequestDispatcher("/PatientSearchResults.jsp")
                .forward(request, response);
    }

    private ArrayList<Patient> searchPatients(
            String alias,
            String provId,
            String city) throws SQLException, ClassNotFoundException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;

        ArrayList<Patient> patients = new ArrayList<>();
        try {
            String sql =
                    "SELECT " +
                    "    pat.alias, " +
                    "    prov.provinceName, " +
                    "    pat.city, " +
                    "    COUNT(r.reviewID) numReviews, " +
                    "    MAX(r.creationDate) latestReviewDate " +
                    "FROM Patient pat " +
                    "INNER JOIN Province prov ON (pat.provinceID = prov.provinceID)" +
                    "LEFT JOIN Review r ON (r.reviewee = pat.alias) " +
                    "WHERE TRUE ";
            if (!StringHelper.isNullOrEmpty(alias)) {
                sql += " AND pat.alias = ? ";
            }
            if (!StringHelper.isNullOrEmpty(provId)) {
                sql += " AND pat.provinceID = ? ";
            }
            if (!StringHelper.isNullOrEmpty(city)) {
                sql += " AND pat.city = ? ";
            }
            sql += " GROUP BY pat.alias";
            connection = ConnectionHub.getConnection();
            statement = connection.prepareStatement(sql);

            int currentParamIndex = 1;
            if (!StringHelper.isNullOrEmpty(alias)) {
                statement.setString(currentParamIndex++, alias);
            }
            if (!StringHelper.isNullOrEmpty(provId)) {
                statement.setString(currentParamIndex++, provId);
            }
            if (!StringHelper.isNullOrEmpty(city)) {
                statement.setString(currentParamIndex++, city);
            }

            ResultSet results = statement.executeQuery();

            while(results.next()) {
                Patient patient = new Patient();
                patient.alias = results.getString("alias");
                patient.city = results.getString("city");
                patient.province = results.getString("provinceName");

                patient.numReviews = results.getInt("numReviews");
                Timestamp timestamp = results.getTimestamp("latestReviewDate");
                if (timestamp != null) {
                    patient.latestReviewDate = timestamp.getTime();
                }

                patients.add(patient);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return patients;
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
