/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

/**
 *
 * @author HITCHI
 */
public class topupResponseModel {

    /**
     * @return the responseStatus
     */
    public int getResponseStatus() {
        return responseStatus;
    }

    /**
     * @param responseStatus the responseStatus to set
     */
    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }
    
    private int responseCode;
    private int responseStatus; 
    private String responseMessage;
    private String responseSubMessage;
    private String responseTag;

  
    public topupResponseModel(int responseStatus, int responseCode, String responseSubMessage, String responseTag) {
        this.responseCode = responseCode;
        this.responseSubMessage = responseSubMessage;
        this.responseTag = responseTag;
        this.responseStatus = responseStatus;
    }

    public topupResponseModel(int responseCode, String responseSubMessage, String responseTag) {
        this.responseCode = responseCode;
        this.responseSubMessage = responseSubMessage;
        this.responseTag = responseTag;
    }
    
    public topupResponseModel(int responseCode, String responseMessage, String responseSubMessage, String responseTag) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseSubMessage = responseSubMessage;
        this.responseTag = responseTag;
    }
    
    public topupResponseModel(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseSubMessage() {
        return responseSubMessage;
    }

    public void setResponseSubMessage(String responseSubMessage) {
        this.responseSubMessage = responseSubMessage;
    }

    public String getResponseTag() {
        return responseTag;
    }

    public void setResponseTag(String responseTag) {
        this.responseTag = responseTag;
    }

 
}
