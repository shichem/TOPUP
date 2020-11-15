/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_package;

/**
 *
 * @author kaa aziz
 */
public class simBoxTransaction {

    private int status; //'0': En cours, '1': REUSSIE, '-1': ECHOUEE
    private String simNum;
    private String portName;
    private String sentMessage;
    private String receivedMessage;

    public simBoxTransaction(String simNum, String portName, String sentMessage) {
        this.simNum = simNum;
        this.portName = portName;
        this.sentMessage = sentMessage;
        this.status = 0;
        this.receivedMessage = "";
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
        /**
     * @return the simNum
     */
    public String getSimNum() {
        return simNum;
    }

    /**
     * @param simNum the simNum to set
     */
    public void setSimNum(String simNum) {
        this.simNum = simNum;
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
     * @return the sentMessage
     */
    public String getSentMessage() {
        return sentMessage;
    }

    /**
     * @param sentMessage the sentMessage to set
     */
    public void setSentMessage(String sentMessage) {
        this.sentMessage = sentMessage;
    }

    /**
     * @return the receivedMessage
     */
    public String getReceivedMessage() {
        return receivedMessage;
    }

    /**
     * @param receivedMessage the receivedMessage to set
     */
    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

}
