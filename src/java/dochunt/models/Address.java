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
public class Address {
    public int number;
    public String streetName;
    public String city;
    public Province province;
    public String postalCode;

    @Override
    public String toString() {
        return number + " " + streetName + ", "
                + city + ", " + province.provName + ", "
                + postalCode;
    }
}
