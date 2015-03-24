/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dochunt.models;

import java.util.ArrayList;

/**
 *
 * @author henrychung
 */
public class Doctor {
    public String alias;
    public String firstName;
    public String lastName;
    public int gender;
    public String email;
    public int numYearsLicensed;

    public ArrayList<String> specializations = new ArrayList<>();
    public ArrayList<Address> addresses = new ArrayList<>();
    public ArrayList<Review> reviews = new ArrayList<>();

    public double rating;
    public int numReviews;

    public String getGenderText() {
        switch(gender) {
            case 1:
                return "Male";
            case 2:
                return "Female";
            case 9:
                return "Not Applicable";
            case 0: // Fall through
            default:
                return "Not Known";
        }
    }
}
