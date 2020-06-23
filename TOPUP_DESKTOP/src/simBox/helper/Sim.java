/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simBox.helper;

/**
 *
 * @author Soulsoft
 *
 * SIMCard Object
 */
public class Sim {

    private String NameOperator="";

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
    private String PortCom="";
    private String Phone="";
    private float Sold=-1; // The current sold balance
    private String Etat="";//ON OFF READY
    private String Imei="";
    private String Pin="";

    public Sim() {

    }
    
    public Sim(String PortCom ) {
    this.PortCom = PortCom;
    }
    /**
     * @return the NameOperator
     */
    public String getNameOperator() {
        return NameOperator;
    }

    /**
     * @param NameOperator the NameOperator to set
     */
    public void setNameOperator(String NameOperator) {
        this.NameOperator = NameOperator;
    }

    /**
     * @return the PortCom
     */
    public String getPortCom() {
        return PortCom;
    }

    /**
     * @param PortCom the PortCom to set
     */
    public void setPortCom(String PortCom) {
        this.PortCom = PortCom;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String Imei) {
        this.Imei = Imei;
    }

    /**
     * @return the Sold
     */
    public Float getSold() {
        return this.Sold;
    }

    /**
     * @param Sold the Sold to set
     */
    public void setSold(Float Sold) {
        this.Sold = Sold;
    }

    /**
     * @return the Etat
     */
    public String getEtat() {
        return Etat;
    }

    /**
     * @param Etat the Etat to set
     */
    public void setEtat(String Etat) {
        this.Etat = Etat;
    }

}
