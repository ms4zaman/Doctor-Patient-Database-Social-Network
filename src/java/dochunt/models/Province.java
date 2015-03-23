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
public class Province {
    public String provId;
    public String provName;

    public Province(String provId, String provName) {
        this.provId = provId;
        this.provName = provName;
    }

    public Province() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
