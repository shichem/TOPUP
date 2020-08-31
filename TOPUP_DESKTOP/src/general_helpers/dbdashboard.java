/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general_helpers;

import custom_package.operatorUI;
import custom_package.simBoxTransaction;
import custom_package.simUI;
import custom_package.typesStatusUI;
import custom_package.userUI;
import java.util.Date;
import java.util.List;
import model_db.OfferInfo;
import model_db.Operator;
import model_db.ProviderClient;
import model_db.SimOffer;
import model_db.StatusInfo;
import model_db.Trader;
import model_db.TransactionSolde;
import model_db.TransactionTopup;
import model_db.TransactionType;
import model_db.UserInfo;
import model_helpers.OfferInfo_Util;
import model_helpers.Operator_Util;
import model_helpers.ProviderClient_Util;
import model_helpers.SimInfo_Util;
import model_helpers.SimOffer_Util;
import model_helpers.StatusInfo_Util;
import model_helpers.Trader_Util;
import model_helpers.TransactionSolde_Util;
import model_helpers.TransactionTopup_Util;
import model_helpers.TransactionType_Util;
import model_helpers.UserInfo_Util;
import model_util.HibernateUtil;
import org.hibernate.Session;
import custom_vars.staticVars;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import model_db.OfferInfoDetails;
import model_db.OfferType;
import model_db.PortInfo;
import model_db.ServerProfile;
import model_db.SimInfo;
import model_db.SimType;
import model_db.Station;
import model_db.StationType;
import model_db.TraderCategory;
import model_db.TraderType;
import model_db.UserCategory;
import model_helpers.OfferInfoDetails_Util;
import model_helpers.OfferType_Util;
import model_helpers.PortInfo_Util;
import model_helpers.ServerProfile_Util;
import model_helpers.SimType_Util;
import model_helpers.StationType_Util;
import model_helpers.TraderCategory_Util;
import model_helpers.TraderType_Util;
import model_helpers.UserCategory_Util;
import model_helpers.station_Util;
import model_util.hqlQueriesHelper;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author kaa aziz
 */
public class dbdashboard {

    intermediate_process proc_fct;

    public dbdashboard() {
        proc_fct = new intermediate_process();
    }

    public Integer countTransactionVeritule() {

        Session globalSession = HibernateUtil.getSessionFactory().openSession();
       // List  l =hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(globalSession, "FROM OfferInfo where flag=0 and offerDesc LIKE");
        return 0 ;
                
    }

    public Integer countTransactionTopUp() {
        return 0;
    }

}
