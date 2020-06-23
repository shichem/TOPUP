/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simBox.helper;

/**
 *
 * @author Soulsoft
 */
public class Operator {

    private String operatorName;
    private String balanceCommand;
    private String phoneNumberCommand;
    private String operatorCommand;
    private String topupCommand;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getBalanceCommand() {
        return balanceCommand;
    }

    public void setBalanceCommand(String balanceCommand) {
        this.balanceCommand = balanceCommand;
    }

    public String getPhoneNumberCommand() {
        return phoneNumberCommand;
    }

    public void setPhoneNumberCommand(String phoneNumberCommand) {
        this.phoneNumberCommand = phoneNumberCommand;
    }

    public String getOperatorCommand() {
        return operatorCommand;
    }

    public void setOperatorCommand(String operatorCommand) {
        this.operatorCommand = operatorCommand;
    }

    public String getTopupCommand() {
        return topupCommand;
    }

    public void setTopupCommand(String topupCommand) {
        this.topupCommand = topupCommand;
    }

    public Operator() {

    }

}
