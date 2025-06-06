package model_db;
// Generated Jun 27, 2020 1:59:00 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * StationLoginSession generated by hbm2java
 */
public class StationLoginSession  implements java.io.Serializable {


     private Integer idsession;
     private Station station;
     private String token;
     private Date createdAt;
     private Integer flag;

    public StationLoginSession() {
    }

    public StationLoginSession(Station station, String token, Date createdAt, Integer flag) {
       this.station = station;
       this.token = token;
       this.createdAt = createdAt;
       this.flag = flag;
    }
   
    public Integer getIdsession() {
        return this.idsession;
    }
    
    public void setIdsession(Integer idsession) {
        this.idsession = idsession;
    }
    public Station getStation() {
        return this.station;
    }
    
    public void setStation(Station station) {
        this.station = station;
    }
    public String getToken() {
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Integer getFlag() {
        return this.flag;
    }
    
    public void setFlag(Integer flag) {
        this.flag = flag;
    }




}


