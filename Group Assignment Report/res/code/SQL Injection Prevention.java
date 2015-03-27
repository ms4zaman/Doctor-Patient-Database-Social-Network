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