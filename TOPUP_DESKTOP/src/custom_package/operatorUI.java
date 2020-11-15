/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_package;

import java.util.Vector;
import model_db.OfferInfo;
import model_db.Operator;
import model_db.PortInfo;
import model_db.SimInfo;

/**
 *
 * @author kaa aziz
 */
public class operatorUI {

    /**
     * @return the operaror
     */
    public Operator getOperaror() {
        return operaror;
    }

    /**
     * @param operaror the operaror to set
     */
    public void setOperaror(Operator operaror) {
        this.operaror = operaror;
    }

    /**
     * @return the simVector
     */
    public Vector<SimInfo> getSimVector() {
        return simVector;
    }

    /**
     * @param simVector the simVector to set
     */
    public void setSimVector(Vector<SimInfo> simVector) {
        this.simVector = simVector;
    }

    /**
     * @return the offerVector
     */
    public Vector<OfferInfo> getOfferVector() {
        return offerVector;
    }

    /**
     * @param offerVector the offerVector to set
     */
    public void setOfferVector(Vector<OfferInfo> offerVector) {
        this.offerVector = offerVector;
    }
    
    private Operator operaror;
    private Vector<SimInfo> simVector;
    private Vector<String> portNamesVector;
    private Vector<OfferInfo> offerVector;
    
    public operatorUI(Operator operator){
        this.operaror = operator;
        simVector = new Vector<SimInfo>();
        portNamesVector = new Vector<String>();
        offerVector = new Vector<OfferInfo>();
    }
    
    public SimInfo getSimbyNumber(String number){
        for (int i = 0; i < simVector.size(); i++) {
            if(simVector.get(i).getSimnumber().equals(number))
                return simVector.get(i);
        }
        return null;
    }
    
    public OfferInfo getOfferByDesc(String desc){
        for (int i = 0; i < offerVector.size(); i++) {
            if(offerVector.get(i).getOfferDesc().equals(desc))
                return offerVector.get(i);
        }
        return null;
    }
    
    public String getPortbyName(String name){
        for (int i = 0; i < portNamesVector.size(); i++) {
            if(portNamesVector.get(i).equals(name))
                return portNamesVector.get(i);
        }
        return null;
    }
    
}
