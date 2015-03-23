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
public class Review {
    public String reviewId;
    public String doctorAlias;
    public String doctorFirstName;
    public int rating;
    public String comments;
    public long date; // Stored as ms since epoch
}
