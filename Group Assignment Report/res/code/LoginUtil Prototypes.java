public class LoginUtil {
    public static void assertUserLoggedIn(
        HttpSession session, HttpServletResponse response) {};
    public static void assertPatientLoggedIn(
        HttpSession session, HttpServletResponse response) {};
    public static void assertDoctorLoggedIn(
        HttpSession session, HttpServletResponse response) {};
    public static boolean isPatientLoggedIn(LoginInfo loginInfo) {};
    public static boolean isDoctorLoggedIn(LoginInfo loginInfo) {};
    public static LoginInfo getLoggedInUser(HttpSession session) {};
}