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
public class topupOfferModel {
    int id;
    String content;
    String activationCode;
    String operator;

    public topupOfferModel(int id, String content, String activationCode, String operator) {
        this.id = id;
        this.content = content;
        this.activationCode = activationCode;
        this.operator = operator;
    }

    public topupOfferModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
        
    
    
}
