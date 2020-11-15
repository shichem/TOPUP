/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_package;

import java.util.Vector;
import model_db.UserInfo;

/**
 *
 * @author kaa aziz
 */
public class userUI {

    /**
     * @return the simUIVestor
     */
    public Vector<simUI> getSimUIVestor() {
        return simUIVescor;
    }

    /**
     * @param simUIVestor the simUIVestor to set
     */
    public void setSimUIVestor(Vector<simUI> simUIVescor) {
        this.simUIVescor = simUIVescor;
    }

    /**
     * @return the actualUser
     */
    public UserInfo getActualUser() {
        return actualUser;
    }

    /**
     * @param actualUser the actualUser to set
     */
    public void setActualUser(UserInfo actualUser) {
        this.actualUser = actualUser;
    }

    /**
     * @return the opUIVestor
     */
    public Vector<operatorUI> getOpUIVestor() {
        return opUIVestor;
    }

    /**
     * @param opUIVestor the opUIVestor to set
     */
    public void setOpUIVestor(Vector<operatorUI> opUIVestor) {
        this.opUIVestor = opUIVestor;
    }

    /**
     * @return the tsUI
     */
    public typesStatusUI getTsUI() {
        return tsUI;
    }

    /**
     * @param tsUI the tsUI to set
     */
    public void setTsUI(typesStatusUI tsUI) {
        this.tsUI = tsUI;
    }

    private UserInfo actualUser;
    private Vector<operatorUI> opUIVestor;
    private Vector<simUI> simUIVescor;
    private typesStatusUI tsUI;

    public userUI(UserInfo actualUser, Vector<operatorUI> opUIVestor, typesStatusUI tsUI) {
        this.actualUser = actualUser;
        this.opUIVestor = opUIVestor;
        this.tsUI = tsUI;
    }

    public operatorUI getOperatorUIbyName(String name) {
        for (int i = 0; i < opUIVestor.size(); i++) {
            if (opUIVestor.get(i).getOperaror().getOperatorDesc().equals(name)) {
                return opUIVestor.get(i);
            }
        }
        return null;
    }
    
    public Vector<simUI> getSimUIbyOperatorName(String name) {
        Vector<simUI> simUIVect =  new Vector<simUI>();
        for (int i = 0; i < simUIVescor.size(); i++) {
            if (simUIVescor.get(i).getOperatorName().equals(name)) {
                simUIVect.add(simUIVescor.get(i));
            }
        }
        return simUIVect;
    }

}
