package model_db;
// Generated Jun 27, 2020 1:59:00 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * OfferInfo generated by hbm2java
 */
public class OfferInfo  implements java.io.Serializable {


     private Integer idofferInfo;
     private OfferType offerType;
     private Operator operator;
     private UserInfo userInfoByIduserInfo;
     private UserInfo userInfoByIduserInfoUpdate;
     private String offerDesc;
     private String prenumber;
     private String postnumber;
     private String postPinCode;
     private Date dateCrea;
     private Double limitTransact;
     private double realValue;
     private double transferedValue;
     private Integer isStatic;
     private Integer flag;
     private Double minLimitTransact;
     private Set<OfferInfoDetails> offerInfoDetailses = new HashSet<OfferInfoDetails>(0);
     private Set<SimOffer> simOffers = new HashSet<SimOffer>(0);

    public OfferInfo() {
    }

	
    public OfferInfo(OfferType offerType, Operator operator, UserInfo userInfoByIduserInfo, String offerDesc, Date dateCrea, double realValue, double transferedValue) {
        this.offerType = offerType;
        this.operator = operator;
        this.userInfoByIduserInfo = userInfoByIduserInfo;
        this.offerDesc = offerDesc;
        this.dateCrea = dateCrea;
        this.realValue = realValue;
        this.transferedValue = transferedValue;
    }
    public OfferInfo(OfferType offerType, Operator operator, UserInfo userInfoByIduserInfo, UserInfo userInfoByIduserInfoUpdate, String offerDesc, String prenumber, String postnumber, String postPinCode, Date dateCrea, Double limitTransact, double realValue, double transferedValue, Integer isStatic, Integer flag, Double minLimitTransact, Set<OfferInfoDetails> offerInfoDetailses, Set<SimOffer> simOffers) {
       this.offerType = offerType;
       this.operator = operator;
       this.userInfoByIduserInfo = userInfoByIduserInfo;
       this.userInfoByIduserInfoUpdate = userInfoByIduserInfoUpdate;
       this.offerDesc = offerDesc;
       this.prenumber = prenumber;
       this.postnumber = postnumber;
       this.postPinCode = postPinCode;
       this.dateCrea = dateCrea;
       this.limitTransact = limitTransact;
       this.realValue = realValue;
       this.transferedValue = transferedValue;
       this.isStatic = isStatic;
       this.flag = flag;
       this.minLimitTransact = minLimitTransact;
       this.offerInfoDetailses = offerInfoDetailses;
       this.simOffers = simOffers;
    }
   
    public Integer getIdofferInfo() {
        return this.idofferInfo;
    }
    
    public void setIdofferInfo(Integer idofferInfo) {
        this.idofferInfo = idofferInfo;
    }
    public OfferType getOfferType() {
        return this.offerType;
    }
    
    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }
    public Operator getOperator() {
        return this.operator;
    }
    
    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    public UserInfo getUserInfoByIduserInfo() {
        return this.userInfoByIduserInfo;
    }
    
    public void setUserInfoByIduserInfo(UserInfo userInfoByIduserInfo) {
        this.userInfoByIduserInfo = userInfoByIduserInfo;
    }
    public UserInfo getUserInfoByIduserInfoUpdate() {
        return this.userInfoByIduserInfoUpdate;
    }
    
    public void setUserInfoByIduserInfoUpdate(UserInfo userInfoByIduserInfoUpdate) {
        this.userInfoByIduserInfoUpdate = userInfoByIduserInfoUpdate;
    }
    public String getOfferDesc() {
        return this.offerDesc;
    }
    
    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }
    public String getPrenumber() {
        return this.prenumber;
    }
    
    public void setPrenumber(String prenumber) {
        this.prenumber = prenumber;
    }
    public String getPostnumber() {
        return this.postnumber;
    }
    
    public void setPostnumber(String postnumber) {
        this.postnumber = postnumber;
    }
    public String getPostPinCode() {
        return this.postPinCode;
    }
    
    public void setPostPinCode(String postPinCode) {
        this.postPinCode = postPinCode;
    }
    public Date getDateCrea() {
        return this.dateCrea;
    }
    
    public void setDateCrea(Date dateCrea) {
        this.dateCrea = dateCrea;
    }
    public Double getLimitTransact() {
        return this.limitTransact;
    }
    
    public void setLimitTransact(Double limitTransact) {
        this.limitTransact = limitTransact;
    }
    public double getRealValue() {
        return this.realValue;
    }
    
    public void setRealValue(double realValue) {
        this.realValue = realValue;
    }
    public double getTransferedValue() {
        return this.transferedValue;
    }
    
    public void setTransferedValue(double transferedValue) {
        this.transferedValue = transferedValue;
    }
    public Integer getIsStatic() {
        return this.isStatic;
    }
    
    public void setIsStatic(Integer isStatic) {
        this.isStatic = isStatic;
    }
    public Integer getFlag() {
        return this.flag;
    }
    
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
    public Double getMinLimitTransact() {
        return this.minLimitTransact;
    }
    
    public void setMinLimitTransact(Double minLimitTransact) {
        this.minLimitTransact = minLimitTransact;
    }
    public Set<OfferInfoDetails> getOfferInfoDetailses() {
        return this.offerInfoDetailses;
    }
    
    public void setOfferInfoDetailses(Set<OfferInfoDetails> offerInfoDetailses) {
        this.offerInfoDetailses = offerInfoDetailses;
    }
    public Set<SimOffer> getSimOffers() {
        return this.simOffers;
    }
    
    public void setSimOffers(Set<SimOffer> simOffers) {
        this.simOffers = simOffers;
    }

  




}


