/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.helpers;

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
        Object loginInfo = session.getAttribute("loginInfo");
        if (loginInfo != null) {
            return;
        }
        try {
            response.sendRedirect("UserLogin.jsp");
        } catch (IOException ex) {
            Logger.getLogger(LoginUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
