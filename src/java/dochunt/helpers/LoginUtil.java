/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.helpers;

import dochunt.models.LoginInfo;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author henrychung
 */
public class LoginUtil {
    public static void assertUserLoggedIn(HttpSession session, HttpServletResponse response) {
        LoginInfo loginInfo = getLoggedInUser(session);
        if (loginInfo != null) {
            return;
        }
        try {
            response.sendRedirect("UserLogin.jsp");
        } catch (IOException ex) {
            Logger.getLogger(LoginUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void assertPatientLoggedIn(HttpSession session, HttpServletResponse response) {
        LoginInfo loginInfo = getLoggedInUser(session);
        if (loginInfo != null && loginInfo.level == 0) {
            return;
        }
        session.setAttribute("loginInfo", loginInfo);
        try {
            response.sendRedirect("UserLogin.jsp");
        } catch (IOException ex) {
            Logger.getLogger(LoginUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void assertDoctorLoggedIn(HttpSession session, HttpServletResponse response) {
        LoginInfo loginInfo = getLoggedInUser(session);
        if (loginInfo != null && loginInfo.level == 1) {
            return;
        }
        session.setAttribute("loginInfo", loginInfo);
        try {
            response.sendRedirect("UserLogin.jsp");
        } catch (IOException ex) {
            Logger.getLogger(LoginUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isPatientLoggedIn(LoginInfo loginInfo) {
        return loginInfo != null && loginInfo.level == 0;
    }

    public static boolean isDoctorLoggedIn(LoginInfo loginInfo) {
        return loginInfo != null && loginInfo.level == 1;
    }

    public static LoginInfo getLoggedInUser(HttpSession session) {
        return (LoginInfo)session.getAttribute("loginInfo");
    }
}
