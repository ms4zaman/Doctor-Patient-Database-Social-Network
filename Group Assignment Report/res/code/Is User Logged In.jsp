Doctor: <%= doctor.lastName %>, <%= doctor.firstName %> <br/>
<% if (LoginUtil.isDoctorLoggedIn(loginInfo)) { %>
    Email: <%= doctor.email %> <br/>
<% } %>
Gender: <%= doctor.getGenderText() %> <br/>
<!-- snipped -->
Review(s): <br/>
<table>
    <!-- snipped -->
</table>
<% if (LoginUtil.isPatientLoggedIn(loginInfo)) { %>
    <a href="WriteDoctorReview.jsp?doctorAlias=<%= doctor.alias %>">
        Write a new review!
    </a>
<% } %>