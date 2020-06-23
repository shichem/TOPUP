/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_package;

import custom_vars.staticVars;
import custom_vars.uiVars;
import general_helpers.intermediate_process;
import java.awt.Dimension;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import model_db.SimInfo;
import model_helpers.SimInfo_Util;
import sun.swing.SwingAccessor;
import topup_desktop.main_interface;

/**
 *
 * @author kaa aziz
 */
public class simUI {

    /**
     * @return the soldeLabel
     */
    public JLabel getSoldeLabel() {
        return soldeLabel;
    }

    /**
     * @param soldeLabel the soldeLabel to set
     */
    public void setSoldeLabel(JLabel soldeLabel) {
        this.soldeLabel = soldeLabel;
    }

    /**
     * @return the pinCode
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * @param pinCode the pinCode to set
     */
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    /**
     * @return the simNumButton
     */
    public JButton getSimNumButton() {
        return simNumButton;
    }

    /**
     * @param simNumButton the simNumButton to set
     */
    public void setSimNumButton(JButton simNumButton) {
        this.simNumButton = simNumButton;
    }

    /**
     * @return the pinCodeButton
     */
    public JButton getPinCodeButton() {
        return pinCodeButton;
    }

    /**
     * @param pinCodeButton the pinCodeButton to set
     */
    public void setPinCodeButton(JButton pinCodeButton) {
        this.pinCodeButton = pinCodeButton;
    }

    /**
     * @return the saveButton
     */
    public JButton getSaveButton() {
        return saveButton;
    }

    /**
     * @param saveButton the saveButton to set
     */
    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    /**
     * @return the simNumber
     */
    public String getSimNumber() {
        return simNumber;
    }

    /**
     * @param simNumber the simNumber to set
     */
    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    /**
     * @return the operatorName
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * @param operatorName the operatorName to set
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * @return the portName
     */
    public String getPortName() {
        return portName;
    }

    /**
     * @param portName the portName to set
     */
    public void setPortName(String portName) {
        this.portName = portName;
    }

    /**
     * @return the actualSolde
     */
    public double getActualSolde() {
        return actualSolde;
    }

    /**
     * @param actualSolde the actualSolde to set
     */
    public void setActualSolde(double actualSolde) {
        this.actualSolde = actualSolde;
    }

    /**
     * @return the isNew
     */
    public int getIsNew() {
        return isNew;
    }

    /**
     * @param isNew the isNew to set
     */
    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    private String simNumber;
    private String operatorName;
    private String portName;
    private String pinCode;
    private double actualSolde;
    private int isNew;

    private JButton simNumButton;
    private JButton pinCodeButton;
    private JButton saveButton;
    private JLabel soldeLabel;

    public simUI(String simNumber, String operatorName, String portName, double actualSolde) {
        this.simNumber = simNumber;
        this.operatorName = operatorName;
        this.portName = portName;
        this.actualSolde = actualSolde;
        this.isNew = 1;
        this.simNumButton = new JButton("Num " + portName + "?");
        this.simNumButton.setEnabled(false);
        this.simNumButton.setPreferredSize(new Dimension(105, 30));
        this.simNumButton.setHorizontalTextPosition(SwingConstants.CENTER);
        this.pinCodeButton = new JButton("Code PIN?");
        this.pinCodeButton.setEnabled(false);
        this.pinCodeButton.setPreferredSize(new Dimension(80, 30));
        this.pinCodeButton.setHorizontalTextPosition(SwingConstants.CENTER);
        this.saveButton = new JButton("Enregistrer");
        this.saveButton.setEnabled(false);
        this.saveButton.setPreferredSize(new Dimension(80, 30));
        this.saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        this.soldeLabel = new JLabel(String.valueOf(actualSolde) + " DA.");

        this.simNumButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            String number;
            number = JOptionPane.showInputDialog(main_interface.getFrames()[0], "Veuillez entrer le numéro de cette puce", "Numéro de puce", JOptionPane.INFORMATION_MESSAGE);
            if (operatorName.equals("Djezzy")) {
                if (new intermediate_process().isDjezzyValidMobileNumber_draft(number)) {
                    this.simNumButton.setText(number);
                    this.setSimNumber(number);
                    this.updateSimStates(number);
                } else {
                    this.simNumButton.setText("Num " + portName + "?");
                    this.saveButton.setEnabled(false);
                }
                if (!this.pinCodeButton.getText().equals("Code PIN?") && !this.simNumButton.getText().equals("Num " + portName + "?")) {
                    this.saveButton.setEnabled(true);
                }

            }
            if (operatorName.equals("Mobilis")) {
                if (new intermediate_process().isMobilisValidMobileNumber_draft(number)) {
                    this.simNumButton.setText(number);
                    this.setSimNumber(number);
                    this.updateSimStates(number);
                } else {
                    this.simNumButton.setText("Num " + portName + "?");
                    this.saveButton.setEnabled(false);
                }
                if (!this.pinCodeButton.getText().equals("Code PIN?") && !this.simNumButton.getText().equals("Num " + portName + "?")) {
                    this.saveButton.setEnabled(true);
                }
            }
            if (operatorName.equals("Ooredoo")) {
                if (new intermediate_process().isOoredooValidMobileNumber_draft(number)) {
                    this.simNumButton.setText(number);
                    this.setSimNumber(number);
                    this.updateSimStates(number);
                } else {
                    this.simNumButton.setText("Num " + portName + "?");
                    this.saveButton.setEnabled(false);
                }
                if (!this.pinCodeButton.getText().equals("Code PIN?") && !this.simNumButton.getText().equals("Num " + portName + "?")) {
                    this.saveButton.setEnabled(true);
                }
            }
        });
        this.pinCodeButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            String pin;
            pin = JOptionPane.showInputDialog(main_interface.getFrames()[0], "Veuillez entrer le code PIN de cette puce", "code PIN de la puce", JOptionPane.INFORMATION_MESSAGE);
            try {
                Integer.parseInt(pin);
                this.pinCodeButton.setText(pin);
                this.setPinCode(pin);
                if (!this.pinCodeButton.getText().equals("Code PIN?") && !this.simNumButton.getText().equals("Num " + portName + "?")) {
                    this.saveButton.setEnabled(true);
                } else {
                    this.saveButton.setEnabled(false);
                }
            } catch (Exception e) {
                this.pinCodeButton.setText("Code PIN?");
                this.saveButton.setEnabled(false);
            }
        });
    
           this.saveButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            int rp =  JOptionPane.showConfirmDialog(main_interface.getFrames()[0], 
                    "Voulez vous vraiment enregistrer la puce : "+this.getSimNumber()
                            +" \navec le code PIN : "+this.getPinCode(), 
                    "Ajout d'une nouevlle puce", JOptionPane.YES_NO_OPTION);
            
            if(rp == JOptionPane.YES_OPTION){
                 int addRp = staticVars.requestBroker.addSim(this);
                 if(addRp == staticVars.onGoingProcessOK)
                     uiVars.defaultOKMessage(main_interface.getFrames()[0], "Ajout d'une nouvelle puce", "La puce a été ajoutée avec succès");
                 else
                     uiVars.defaultErrorMessage(main_interface.getFrames()[0], "Ajout d'une nouvelle puce", "Erreur lors de l'ajout de la puce!!!");
            }
        });
     
    }

    private void updateSimStates(String numebr) {
        List simList = new SimInfo_Util().getSimInfo_by_simnumber(staticVars.globalSession, simNumber, "");
        if (!simList.isEmpty()) {
            SimInfo sim = (SimInfo) simList.get(0);
            int rp = staticVars.requestBroker.updateSimParametres(sim, this);
            if (rp == staticVars.onGoingProcessOK) {
                this.simNumButton.setEnabled(false);
                this.pinCode = sim.getSimPinCode();
                this.pinCodeButton.setText(this.getPinCode());
                this.pinCodeButton.setEnabled(false);
                this.saveButton.setEnabled(false);
            }
        }
    }

    }
