/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.helpers;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author henrychung
 */
public class DateUtil {
    public static String formatDateFromEpoch(long ms) {
        if (ms <= 0) {
            return "n/a";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(new Date(ms));
    }
}
