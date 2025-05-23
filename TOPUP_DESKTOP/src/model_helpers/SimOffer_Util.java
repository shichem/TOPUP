package model_helpers;

import java.util.List;
import model_db.OfferInfo;
import model_db.SimInfo;
import model_db.SimOffer;
import model_db.StatusInfo;
import model_util.hqlQueriesHelper;
import org.hibernate.Query;
import org.hibernate.Session;



/**
 * SimOffer generated by hbm2java
 */
public class SimOffer_Util {

 public List getAllSimOffer(Session session, String suffix) {
        return hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM SimOffer where flag=0", suffix);

    }

    public SimOffer getSimOffer_by_id(Session session, int id, String suffix) {
        List list = hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM SimOffer where flag=0 and idsimOffer = " + id, suffix);
        if (list.isEmpty()) {
            return null;
        } else {
            return ((SimOffer) list.get(0));
        }
    }

    public List getSimOffer_by_offerInfo(Session session, OfferInfo offerInfo, String suffix) {
        return hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM SimOffer where flag=0 and offerInfo = " + offerInfo.getIdofferInfo(), suffix);

    }
    
    public List getSimOffer_by_offerInfo_ActiveSims(Session session, OfferInfo offerInfo, StatusInfo statusInfo, String suffix) {
        return hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM SimOffer where flag=0 "
                + "and offerInfo = " + offerInfo.getIdofferInfo()
                + "and simInfo.statusInfo = " + statusInfo.getIdstatusInfo(), suffix);

    }

    public List getSimOffer_by_simInfo(Session session, SimInfo simInfo, String suffix) {
        return hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM SimOffer where flag=0 and simInfo = " + simInfo.getIdsimInfo(), suffix);

    }

    public List getSimOffer_by_limitTransact(Session session, double limitTransactmin, double limitTransactmax, String suffix) {
        return hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM SimOffer where flag=0 "
                + "and (limitTransact >= " + limitTransactmin + " and limitTransact <= " + limitTransactmax + ")",
                suffix);
    }

    public void addSimInfo(SimOffer adt, Session session) {
        hqlQueriesHelper.executeAddHQLQuery_WithPreparedSession(adt, session);
    }

    public void updateSimInfo(SimOffer adt, Session session) {
        hqlQueriesHelper.executeUpdateHQLQuery_WithPreparedSession(adt, session);
    }

     public void deleteOfferInfo(Session session,Integer offerInfo,String suffix) {
        Query query = session.createQuery( "Delete SimOffer where offerInfo="+offerInfo);
         query.executeUpdate();
    }



}


