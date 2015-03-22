/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.models;

/**
 *
 * @author henrychung
 */
public class LoginInfo {
    public String alias;
    public int level;

    public String getLevelAsString() {
        return level == 0 ? "Patient" : "Doctor";
    }
}
