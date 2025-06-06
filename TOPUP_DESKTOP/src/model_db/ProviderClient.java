package model_db;
// Generated Jun 27, 2020 1:59:00 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ProviderClient generated by hbm2java
 */
public class ProviderClient  implements java.io.Serializable {


     private Integer idproviderClient;
     private Operator operator;
     private Trader traderByIdclient;
     private Trader traderByIdprovider;
     private UserInfo userInfo;
     private Double solde;
     private Double pendedSolde;
     private Date dateAffect;
     private Date deactivationAffect;
     private Double limitTransact;
     private Integer flag;
     private Set<TransactionTopup> transactionTopups = new HashSet<TransactionTopup>(0);
     private Set<TransactionSolde> transactionSoldes = new HashSet<TransactionSolde>(0);

    public ProviderClient() {
    }

	
    public ProviderClient(Operator operator, Trader traderByIdclient, UserInfo userInfo, Date dateAffect) {
        this.operator = operator;
        this.traderByIdclient = traderByIdclient;
        this.userInfo = userInfo;
        this.dateAffect = dateAffect;
    }
    public ProviderClient(Operator operator, Trader traderByIdclient, Trader traderByIdprovider, UserInfo userInfo, Double solde, Double pendedSolde, Date dateAffect, Date deactivationAffect, Double limitTransact, Integer flag, Set<TransactionTopup> transactionTopups, Set<TransactionSolde> transactionSoldes) {
       this.operator = operator;
       this.traderByIdclient = traderByIdclient;
       this.traderByIdprovider = traderByIdprovider;
       this.userInfo = userInfo;
       this.solde = solde;
       this.pendedSolde = pendedSolde;
       this.dateAffect = dateAffect;
       this.deactivationAffect = deactivationAffect;
       this.limitTransact = limitTransact;
       this.flag = flag;
       this.transactionTopups = transactionTopups;
       this.transactionSoldes = transactionSoldes;
    }
   
    public Integer getIdproviderClient() {
        return this.idproviderClient;
    }
    
    public void setIdproviderClient(Integer idproviderClient) {
        this.idproviderClient = idproviderClient;
    }
    public Operator getOperator() {
        return this.operator;
    }
    
    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    public Trader getTraderByIdclient() {
        return this.traderByIdclient;
    }
    
    public void setTraderByIdclient(Trader traderByIdclient) {
        this.traderByIdclient = traderByIdclient;
    }
    public Trader getTraderByIdprovider() {
        return this.traderByIdprovider;
    }
    
    public void setTraderByIdprovider(Trader traderByIdprovider) {
        this.traderByIdprovider = traderByIdprovider;
    }
    public UserInfo getUserInfo() {
        return this.userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public Double getSolde() {
        return this.solde;
    }
    
    public void setSolde(Double solde) {
        this.solde = solde;
    }
    public Double getPendedSolde() {
        return this.pendedSolde;
    }
    
    public void setPendedSolde(Double pendedSolde) {
        this.pendedSolde = pendedSolde;
    }
    public Date getDateAffect() {
        return this.dateAffect;
    }
    
    public void setDateAffect(Date dateAffect) {
        this.dateAffect = dateAffect;
    }
    public Date getDeactivationAffect() {
        return this.deactivationAffect;
    }
    
    public void setDeactivationAffect(Date deactivationAffect) {
        this.deactivationAffect = deactivationAffect;
    }
    public Double getLimitTransact() {
        return this.limitTransact;
    }
    
    public void setLimitTransact(Double limitTransact) {
        this.limitTransact = limitTransact;
    }
    public Integer getFlag() {
        return this.flag;
    }
    
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
    public Set<TransactionTopup> getTransactionTopups() {
        return this.transactionTopups;
    }
    
    public void setTransactionTopups(Set<TransactionTopup> transactionTopups) {
        this.transactionTopups = transactionTopups;
    }
    public Set<TransactionSolde> getTransactionSoldes() {
        return this.transactionSoldes;
    }
    
    public void setTransactionSoldes(Set<TransactionSolde> transactionSoldes) {
        this.transactionSoldes = transactionSoldes;
    }




}


