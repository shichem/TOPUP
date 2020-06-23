/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_package;

import java.util.List;
import model_db.OfferType;
import model_db.SimType;
import model_db.StationType;
import model_db.StatusInfo;
import model_db.TraderCategory;
import model_db.TraderType;
import model_db.TransactionType;
import model_db.UserCategory;

/**
 *
 * @author kaa aziz
 */
public class typesStatusUI {

    /**
     * @return the traderCategoryVector
     */
    public List<TraderCategory> getTraderCategoryVector() {
        return traderCategoryVector;
    }

    /**
     * @param traderCategoryVector the traderCategoryVector to set
     */
    public void setTraderCategoryVector(List<TraderCategory> traderCategoryVector) {
        this.traderCategoryVector = traderCategoryVector;
    }

    /**
     * @return the stationTypeVector
     */
    public List<StationType> getStationTypeVector() {
        return stationTypeVector;
    }

    /**
     * @param stationTypeVector the stationTypeVector to set
     */
    public void setStationTypeVector(List<StationType> stationTypeVector) {
        this.stationTypeVector = stationTypeVector;
    }

    /**
     * @return the userCategoryVector
     */
    public List<UserCategory> getUserCategoryVector() {
        return userCategoryVector;
    }

    /**
     * @param userCategoryVector the userCategoryVector to set
     */
    public void setUserCategoryVector(List<UserCategory> userCategoryVector) {
        this.userCategoryVector = userCategoryVector;
    }

    /**
     * @return the simTypeVector
     */
    public List<SimType> getSimTypeVector() {
        return simTypeVector;
    }

    /**
     * @param simTypeVector the simTypeVector to set
     */
    public void setSimTypeVector(List<SimType> simTypeVector) {
        this.simTypeVector = simTypeVector;
    }

    /**
     * @return the offerTypeVector
     */
    public List<OfferType> getOfferTypeVector() {
        return offerTypeVector;
    }

    /**
     * @param offerTypeVector the offerTypeVector to set
     */
    public void setOfferTypeVector(List<OfferType> offerTypeVector) {
        this.offerTypeVector = offerTypeVector;
    }

    /**
     * @return the transactionTypeVector
     */
    public List<TransactionType> getTransactionTypeVector() {
        return transactionTypeVector;
    }

    /**
     * @param transactionTypeVector the transactionTypeVector to set
     */
    public void setTransactionTypeVector(List<TransactionType> transactionTypeVector) {
        this.transactionTypeVector = transactionTypeVector;
    }

    /**
     * @return the statusInfoVector
     */
    public List<StatusInfo> getStatusInfoVector() {
        return statusInfoVector;
    }

    /**
     * @param statusInfoVector the statusInfoVector to set
     */
    public void setStatusInfoVector(List<StatusInfo> statusInfoVector) {
        this.statusInfoVector = statusInfoVector;
    }

    /**
     * @return the traderTypeVector
     */
    public List<TraderType> getTraderTypeVector() {
        return traderTypeVector;
    }

    /**
     * @param traderTypeVector the traderTypeVector to set
     */
    public void setTraderTypeVector(List<TraderType> traderTypeVector) {
        this.traderTypeVector = traderTypeVector;
    }
    private List<UserCategory> userCategoryVector;
    private List<SimType> simTypeVector;
    private List<OfferType> offerTypeVector;
    private List<TransactionType> transactionTypeVector;
    private List<StatusInfo> statusInfoVector;
    private List<TraderType> traderTypeVector;
    private List<TraderCategory> traderCategoryVector;
    private List<StationType> stationTypeVector;
    
    public typesStatusUI(List<UserCategory> userCategoryVector, 
            List<SimType> simTypeVector, 
            List<OfferType> offerTypeVector, 
            List<TransactionType> transactionTypeVector, 
            List<StatusInfo> statusInfoVector, 
            List<TraderType> traderTypeVector,
            List<TraderCategory> traderCategoryVector,
            List<StationType> stationTypeVector){
        
        this.userCategoryVector = userCategoryVector;
        this.simTypeVector = simTypeVector;
        this.offerTypeVector = offerTypeVector;
        this.transactionTypeVector = transactionTypeVector;
        this.statusInfoVector = statusInfoVector;
        this.traderTypeVector = traderTypeVector;
        this.traderCategoryVector = traderCategoryVector;
        this.stationTypeVector = stationTypeVector;
    }
    
     public UserCategory getUserCategoryByDesc(String desc){
        for (int i = 0; i < userCategoryVector.size(); i++) {
            if(userCategoryVector.get(i).getUserCategoryDesc().equals(desc))
                return userCategoryVector.get(i);
        }
        return null;
    }
    
     public SimType getSimTypeByDesc(String desc){
        for (int i = 0; i < simTypeVector.size(); i++) {
            if(simTypeVector.get(i).getSimTypeDesc().equals(desc))
                return simTypeVector.get(i);
        }
        return null;
    }
    
     public OfferType getOfferTypeByDesc(String desc){
        for (int i = 0; i < offerTypeVector.size(); i++) {
            if(offerTypeVector.get(i).getOfferTypeDesc().equals(desc))
                return offerTypeVector.get(i);
        }
        return null;
    }
    
     public TransactionType getTransactionTypeByDesc(String desc){
        for (int i = 0; i < transactionTypeVector.size(); i++) {
            if(transactionTypeVector.get(i).getTransactionTypeDesc().equals(desc))
                return transactionTypeVector.get(i);
        }
        return null;
    }
    
     public StatusInfo getStatusInfoByDesc(String desc){
        for (int i = 0; i < statusInfoVector.size(); i++) {
            if(statusInfoVector.get(i).getStatusInfoDesc().equals(desc))
                return statusInfoVector.get(i);
        }
        return null;
    }
    
     public TraderType getTraderTypeByDesc(String desc){
        for (int i = 0; i < traderTypeVector.size(); i++) {
            if(traderTypeVector.get(i).getTraderTypeDesc().equals(desc))
                return traderTypeVector.get(i);
        }
        return null;
    }
     
     public TraderCategory getTraderCategoryByDesc(String desc){
        for (int i = 0; i < traderCategoryVector.size(); i++) {
            if(traderCategoryVector.get(i).getTraderCategoryDesc().equals(desc))
                return traderCategoryVector.get(i);
        }
        return null;
    }
     
     public StationType getStationTypeByDesc(String desc){
        for (int i = 0; i < stationTypeVector.size(); i++) {
            if(stationTypeVector.get(i).getStationTypeDesc().equals(desc))
                return stationTypeVector.get(i);
        }
        return null;
    }
    
}
