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
public class Patient {
    public String alias;
    public String city;
    public String province;
    public String firstName;
    public String lastName;
    public String email;

    public int numReviews;
    public long latestReviewDate; // Stored as ms since epoch

    public Patient() {
        // Default constructor
    }
}
