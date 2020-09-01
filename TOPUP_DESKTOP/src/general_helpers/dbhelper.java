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
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author kaa aziz
 */
public class dbhelper {

    intermediate_process proc_fct;

    public dbhelper() {
        proc_fct = new intermediate_process();
    }

    public userUI checkAUthentification(String username, String password) {

        Session globalSession = HibernateUtil.getSessionFactory().openSession();

        globalSession.getTransaction().begin();

        userUI user_ui = null;
        List<UserInfo> userInfoList = new UserInfo_Util().getUserInfo_by_username_password(staticVars.globalSession, username, password, "");
        if (!userInfoList.isEmpty()) {
            user_ui = new userUI((UserInfo) userInfoList.get(0), this.loadOperatorsData(staticVars.globalSession), this.loadTypesStatusData(staticVars.globalSession));
            user_ui.setSimUIVestor(this.loadSimsPortsData(staticVars.globalSession, proc_fct.getAvailableSimUI_FromSimBox()));
        }

        globalSession.getTransaction().commit();
        return user_ui;
    }

    public userUI checkAUthentification(Session session, String username, String password) {

        userUI user_ui = null;
        List<UserInfo> userInfoList = new UserInfo_Util().getUserInfo_by_username_password(session, username, password, "");
        if (userInfoList.size() != 0) {
            user_ui = new userUI((UserInfo) userInfoList.get(0), this.loadOperatorsData(), this.loadTypesStatusData());
            user_ui.setSimUIVestor(this.loadSimsPortsData(session, proc_fct.getAvailableSimUI_FromSimBox()));
        }

        return user_ui;
    }

    public Vector<simUI> loadSimsPortsData(Session session, Vector<simUI> simUIVector) {

        Operator_Util operatorUtil = new Operator_Util();
        Operator actualOperator;

        String deactivateAllSimsSQL = "UPDATE sim_info SET sim_info.idstatus_info = "
                + "(SELECT status_info.idstatus_info FROM status_info WHERE status_info.status_info_desc = '" + staticVars.status_ENT_Inactif + "')"
                + "WHERE sim_info.idstatus_info = "
                + "(SELECT status_info.idstatus_info FROM status_info WHERE status_info.status_info_desc = '" + staticVars.status_ENT_Actif + "')";
        session.createSQLQuery(deactivateAllSimsSQL);
        staticVars.globalSolde = 0.0;
        staticVars.globalSoldeDjezzy = 0.0;
        staticVars.globalSoldeMobilis = 0.0;
        staticVars.globalSoldeOoredoo = 0.0;
        for (int i = 0; i < simUIVector.size(); i++) {
            simUI elementAt = simUIVector.elementAt(i);
            String operatorName = elementAt.getOperatorName();
            System.out.println();
            actualOperator = (Operator) operatorUtil.getOperator_by_operatorDesc(session, elementAt.getOperatorName(), "").get(0);
            switch (operatorName) {
                case "Djezzy":
                    staticVars.globalSoldeDjezzy += elementAt.getActualSolde();
                    break;
                case "Mobilis":
                    staticVars.globalSoldeMobilis += elementAt.getActualSolde();
                    break;
                case "Ooredoo":
                    staticVars.globalSoldeOoredoo += elementAt.getActualSolde();
                    break;
            }
            staticVars.globalSolde += elementAt.getActualSolde();
            List simList = new SimInfo_Util().getSimInfo_by_operator_simnumber(session, actualOperator, elementAt.getSimNumber(), "");
            if (!simList.isEmpty()) {
                this.updateSimParametres(session, (SimInfo) simList.get(0), elementAt);
            }
        }
        return simUIVector;
    }

    public void updateSimParametres(Session session, SimInfo actualSim, simUI actualSimUI) {

        actualSimUI.setIsNew(-1);
        actualSimUI.setPinCode(actualSim.getSimPinCode());
        actualSim.setStatusInfo(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""));

        if (actualSim.getLastEstimatedSolde() < actualSimUI.getActualSolde() && actualSimUI.getActualSolde() != -1) {

            TransactionTopup tctTopUp = new TransactionTopup(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Reussie, ""),
                    new TransactionType_Util().getTransacType_by_transactionTypeDesc(session, staticVars.transactType_AlimentSolde, ""),
                    actualSim.getSimnumber(), actualSimUI.getActualSolde(), (actualSimUI.getActualSolde() - actualSim.getLastEstimatedSolde()),
                    (actualSimUI.getActualSolde() - actualSim.getLastEstimatedSolde()), new Date(), 0, "", "");

            new TransactionTopup_Util().addTransactionTopup(tctTopUp, session);
            actualSim.setLastEstimatedSolde(actualSimUI.getActualSolde());
            actualSim.setLastSolde(actualSimUI.getActualSolde());
        } else {
            if (actualSim.getLastEstimatedSolde() > actualSimUI.getActualSolde() && actualSimUI.getActualSolde() != -1) {
                TransactionTopup tctTopUp = new TransactionTopup(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Reussie, ""),
                        new TransactionType_Util().getTransacType_by_transactionTypeDesc(session, staticVars.transactType_DebitSolde, ""),
                        actualSim.getSimnumber(), actualSimUI.getActualSolde(), (actualSimUI.getActualSolde() - actualSim.getLastEstimatedSolde()),
                        (actualSimUI.getActualSolde() - actualSim.getLastEstimatedSolde()), new Date(), 0, "", "");

                new TransactionTopup_Util().addTransactionTopup(tctTopUp, session);
                actualSim.setLastEstimatedSolde(actualSimUI.getActualSolde());
                actualSim.setLastSolde(actualSimUI.getActualSolde());
            }
        }

        List portList = new PortInfo_Util().getPortInfo_by_portDesc(session, actualSimUI.getPortName(), "");
        if (portList.isEmpty()) {
            actualSim.getPortInfo().setPortDesc(actualSimUI.getPortName());
            new PortInfo_Util().updatePortInfo(actualSim.getPortInfo(), session);
        } else {
            actualSim.setPortInfo((PortInfo) portList.get(0));
        }
        actualSim.setDateConsul(new Date());
        new SimInfo_Util().updateSimInfo(actualSim, session);
    }

    public int updateSimParametres(SimInfo actualSim, simUI actualSimUI) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            actualSimUI.setIsNew(-1);
            actualSimUI.setPinCode(actualSim.getSimPinCode());
            actualSim.setStatusInfo(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""));

            if (actualSim.getLastEstimatedSolde() < actualSimUI.getActualSolde() && actualSimUI.getActualSolde() != -1) {

                TransactionTopup tctTopUp = new TransactionTopup(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Reussie, ""),
                        new TransactionType_Util().getTransacType_by_transactionTypeDesc(session, staticVars.transactType_AlimentSolde, ""),
                        actualSim.getSimnumber(), actualSimUI.getActualSolde(), (actualSimUI.getActualSolde() - actualSim.getLastEstimatedSolde()),
                        (actualSimUI.getActualSolde() - actualSim.getLastEstimatedSolde()), new Date(), 0, "", "");

                new TransactionTopup_Util().addTransactionTopup(tctTopUp, session);
                actualSim.setLastEstimatedSolde(actualSimUI.getActualSolde());
                actualSim.setLastSolde(actualSimUI.getActualSolde());
            } else {
                if (actualSim.getLastEstimatedSolde() > actualSimUI.getActualSolde() && actualSimUI.getActualSolde() != -1) {
                    TransactionTopup tctTopUp = new TransactionTopup(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Reussie, ""),
                            new TransactionType_Util().getTransacType_by_transactionTypeDesc(session, staticVars.transactType_DebitSolde, ""),
                            actualSim.getSimnumber(), actualSimUI.getActualSolde(), (actualSimUI.getActualSolde() - actualSim.getLastEstimatedSolde()),
                            (actualSimUI.getActualSolde() - actualSim.getLastEstimatedSolde()), new Date(), 0, "", "");

                    new TransactionTopup_Util().addTransactionTopup(tctTopUp, session);
                    actualSim.setLastEstimatedSolde(actualSimUI.getActualSolde());
                    actualSim.setLastSolde(actualSimUI.getActualSolde());
                }
            }

            List portList = new PortInfo_Util().getPortInfo_by_portDesc(session, actualSimUI.getPortName(), "");
            if (portList.isEmpty()) {
                actualSim.getPortInfo().setPortDesc(actualSimUI.getPortName());
                new PortInfo_Util().updatePortInfo(actualSim.getPortInfo(), session);
            } else {
                actualSim.setPortInfo((PortInfo) portList.get(0));
            }
            actualSim.setDateConsul(new Date());
            new SimInfo_Util().updateSimInfo(actualSim, session);

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.updateSimParametres() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addSim(simUI actualSimUI) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            SimInfo actualSim;
            PortInfo portInfo = null;
            List portList = new PortInfo_Util().getPortInfo_by_portDesc(session, actualSimUI.getPortName(), "");
            if (portList.isEmpty()) {
                portInfo = new PortInfo(staticVars.actualUser.getTsUI().getStatusInfoByDesc(staticVars.status_ENT_Actif), actualSimUI.getPortName());
                new PortInfo_Util().addPortInfo(portInfo, session);
                session.refresh(portInfo);

            } else {
                portInfo = (PortInfo) portList.get(0);
            }
            actualSim = new SimInfo(staticVars.actualUser.getOperatorUIbyName(actualSimUI.getOperatorName()).getOperaror(),
                    portInfo,
                    staticVars.actualUser.getTsUI().getStatusInfoByDesc(staticVars.status_ENT_Actif),
                    actualSimUI.getSimNumber(), actualSimUI.getPinCode());
            actualSim.setDateConsul(new Date());
            actualSim.setFlag(0);
            actualSim.setAverageResponseTime(-1.0);
            actualSim.setLastSignalStrength(-1.0);
            actualSim.setWeightedIndex(-1.0);
            actualSim.setTotalTransactions(0);
            actualSim.setSuccededTransactions(0);
            actualSim.setLastEstimatedSolde(actualSimUI.getActualSolde());
            actualSim.setLastSolde(actualSimUI.getActualSolde());

            new SimInfo_Util().addSimInfo(actualSim, session);

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.addSim() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public Vector<operatorUI> loadOperatorsData() {
        if (!staticVars.globalSession.isOpen()) {
            staticVars.globalSession = HibernateUtil.getSessionFactory().openSession();
        }
        staticVars.globalSession.getTransaction().begin();

        Vector<operatorUI> opUIVestor = new Vector<operatorUI>();
        List opList = new Operator_Util().getAllOperator(staticVars.globalSession, "");
        for (Object object : opList) {
            operatorUI opUI = new operatorUI((Operator) object);
            for (Iterator iterator = ((Operator) object).getSimInfos().iterator(); iterator.hasNext();) {
                Object next = iterator.next();
                if (((SimInfo) next).getFlag() == 0) {
                    opUI.getSimVector().add((SimInfo) next);
                }
            }
            for (Iterator iterator = ((Operator) object).getOfferInfos().iterator(); iterator.hasNext();) {
                Object next = iterator.next();
                if (((OfferInfo) next).getFlag() == 0) {
                    opUI.getOfferVector().add((OfferInfo) next);
                }
            }
            opUIVestor.add(opUI);
        }

        staticVars.globalSession.getTransaction().commit();
        return opUIVestor;
    }

    public typesStatusUI loadTypesStatusData() {
        if (!staticVars.globalSession.isOpen()) {
            staticVars.globalSession = HibernateUtil.getSessionFactory().openSession();
        }
        staticVars.globalSession.getTransaction().begin();

        List<UserCategory> userCategoryVector = new UserCategory_Util().getAllUserCategory(staticVars.globalSession, "");
        List<SimType> simTypeVector = new SimType_Util().getAllSimType(staticVars.globalSession, "");
        List<OfferType> offerTypeVector = new OfferType_Util().getAllOfferType(staticVars.globalSession, "");
        List<TransactionType> transactionTypeVector = new TransactionType_Util().getAllTransactionType(staticVars.globalSession, "");
        List<StatusInfo> statusInfoVector = new StatusInfo_Util().getAllStatusInfo(staticVars.globalSession, "");
        List<TraderType> traderTypeVector = new TraderType_Util().getAllTraderType(staticVars.globalSession, "");
        List<TraderCategory> traderCategoryVector = new TraderCategory_Util().getAllTraderCategory(staticVars.globalSession, "");
        List<StationType> stationTypevector = new StationType_Util().getAllStationType(staticVars.globalSession, "");

        staticVars.globalSession.getTransaction().commit();
        return new typesStatusUI(userCategoryVector, simTypeVector, offerTypeVector, transactionTypeVector,
                statusInfoVector, traderTypeVector, traderCategoryVector, stationTypevector);
    }

    public Vector<operatorUI> loadOperatorsData(Session session) {

        Vector<operatorUI> opUIVestor = new Vector<operatorUI>();
        List opList = new Operator_Util().getAllOperator(session, "");
        for (Object object : opList) {
            operatorUI opUI = new operatorUI((Operator) object);
            for (Iterator iterator = ((Operator) object).getSimInfos().iterator(); iterator.hasNext();) {
                Object next = iterator.next();
                if (((SimInfo) next).getFlag() == 0) {
                    opUI.getSimVector().add((SimInfo) next);
                }
            }
            for (Iterator iterator = ((Operator) object).getOfferInfos().iterator(); iterator.hasNext();) {
                Object next = iterator.next();
                if (((OfferInfo) next).getFlag() == 0) {
                    opUI.getOfferVector().add((OfferInfo) next);
                }
            }
            opUIVestor.add(opUI);
        }
        return opUIVestor;
    }

    public typesStatusUI loadTypesStatusData(Session session) {

        List<UserCategory> userCategoryVector = new UserCategory_Util().getAllUserCategory(session, "");
        List<SimType> simTypeVector = new SimType_Util().getAllSimType(session, "");
        List<OfferType> offerTypeVector = new OfferType_Util().getAllOfferType(session, "");
        List<TransactionType> transactionTypeVector = new TransactionType_Util().getAllTransactionType(session, "");
        List<StatusInfo> statusInfoVector = new StatusInfo_Util().getAllStatusInfo(session, "");
        List<TraderType> traderTypeVector = new TraderType_Util().getAllTraderType(session, "");
        List<TraderCategory> traderCategoryVector = new TraderCategory_Util().getAllTraderCategory(session, "");
        List<StationType> stationTypevector = new StationType_Util().getAllStationType(session, "");

        return new typesStatusUI(userCategoryVector, simTypeVector, offerTypeVector, transactionTypeVector, statusInfoVector, traderTypeVector, traderCategoryVector, stationTypevector);
    }

    public int addUser(int userID, int traderID, String username, String password, String userCatName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            UserInfo_Util userUtil = new UserInfo_Util();
            if (userUtil.getUserInfo_by_username(session, username, "").size() > 0) {
                return staticVars.userNameAlreadyExists;
            }
            UserInfo user = userUtil.getUserInfo_by_id(session, userID, "");
            UserCategory userCat = new UserCategory_Util().getUserCategory_by_UserCategoryDesc(session, userCatName, "");

            UserInfo userInfo = new UserInfo(userCat, username, password);
            if (traderID != -1) {
                Trader client = new Trader_Util().getTradfer_by_id(session, traderID, "");
                userInfo.setTrader(client);
            }
            userInfo.setUserInfo(user);
            userInfo.setFlag(0);
            userUtil.addUserInfo(userInfo, session);

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.AddUSER() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addUser(userUI user, int traderID, String username, String password, String userCatName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            UserInfo_Util userUtil = new UserInfo_Util();
            if (userUtil.getUserInfo_by_username(session, username, "").size() > 0) {
                return staticVars.userNameAlreadyExists;
            }

            UserCategory userCat = user.getTsUI().getUserCategoryByDesc(userCatName);

            UserInfo userInfo = new UserInfo(userCat, username, password);
            if (user.getActualUser().getTrader() != null) {
                userInfo.setTrader(user.getActualUser().getTrader());
            } else {
                Trader client = new Trader_Util().getTradfer_by_id(session, traderID, "");
                userInfo.setTrader(client);
            }
            userInfo.setUserInfo(user.getActualUser());
            userInfo.setFlag(0);
            userUtil.addUserInfo(userInfo, session);

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.addUser() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addUser(userUI user, Trader client, String username, String password, String userCatName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            UserInfo_Util userUtil = new UserInfo_Util();
            if (userUtil.getUserInfo_by_username(session, username, "").size() > 0) {
                return staticVars.userNameAlreadyExists;
            }

            UserCategory userCat = user.getTsUI().getUserCategoryByDesc(userCatName);

            UserInfo userInfo = new UserInfo(userCat, username, password);
            if (user.getActualUser().getTrader() != null) {
                userInfo.setTrader(user.getActualUser().getTrader());
            } else {
                userInfo.setTrader(client);
            }
            userInfo.setUserInfo(user.getActualUser());
            userInfo.setFlag(0);
            userUtil.addUserInfo(userInfo, session);

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.addUser() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int updateUser(int userID, String oldusername, String oldpassword, String username, String password, String userCatName, int flag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            UserInfo_Util userUtil = new UserInfo_Util();

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            UserCategory userCat = new UserCategory_Util().getUserCategory_by_UserCategoryDesc(session, userCatName, "");

            List userList = userUtil.getUserInfo_by_username_password(session, oldusername, oldpassword, "");
            if (userList.isEmpty()) {
                return staticVars.userNameError;
            }
            UserInfo userInfo = (UserInfo) userList.get(0);

            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setUserCategory(userCat);
            userInfo.setUserInfo(user);
            userInfo.setFlag(flag);

            userUtil.updateUserInfo(userInfo, session);

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.AddUSER() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int updateUser(userUI user, String oldusername, String oldpassword, String username, String password, String userCatName, int flag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            UserInfo_Util userUtil = new UserInfo_Util();
            UserCategory userCat = user.getTsUI().getUserCategoryByDesc(userCatName);

            List userList = userUtil.getUserInfo_by_username_password(session, oldusername, oldpassword, "");
            if (userList.isEmpty()) {
                return staticVars.userNameError;
            }
            UserInfo userInfo = (UserInfo) userList.get(0);

            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setUserCategory(userCat);
            userInfo.setFlag(flag);
            userInfo.setUserInfo(user.getActualUser());
            new UserInfo_Util().updateUserInfo(userInfo, session);

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.updateUser() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int updateUserSU(int userID, String oldusername, String username, String password, String userCatName, int flag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            UserInfo_Util userUtil = new UserInfo_Util();

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            UserCategory userCat = new UserCategory_Util().getUserCategory_by_UserCategoryDesc(session, userCatName, "");

            List userList = userUtil.getUserInfo_by_username(session, oldusername, "");
            if (userList.isEmpty()) {
                return staticVars.userNameError;
            }
            UserInfo userInfo = (UserInfo) userList.get(0);

            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setUserCategory(userCat);
            userInfo.setUserInfo(user);
            userInfo.setFlag(flag);

            userUtil.updateUserInfo(userInfo, session);

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.updateUser() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int updateUserSU(userUI user, String oldusername, String username, String password, String userCatName, int flag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            UserInfo_Util userUtil = new UserInfo_Util();
            UserCategory userCat = user.getTsUI().getUserCategoryByDesc(userCatName);

            List userList = userUtil.getUserInfo_by_username(session, oldusername, "");
            if (userList.isEmpty()) {
                return staticVars.userNameError;
            }
            UserInfo userInfo = (UserInfo) userList.get(0);

            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setUserCategory(userCat);
            userInfo.setFlag(flag);
            userInfo.setUserInfo(user.getActualUser());
            new UserInfo_Util().updateUserInfo(userInfo, session);

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.addUser() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addProviderClientLink(int userID, int providerID, int clientID, int operatorID, double soldeLimit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Trader_Util traderutil = new Trader_Util();
            ProviderClient_Util prClientUtil = new ProviderClient_Util();

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Trader provider = traderutil.getTradfer_by_id(session, providerID, "");
            Trader client = traderutil.getTradfer_by_id(session, clientID, "");
            Operator operator = new Operator_Util().getOperator_by_id(session, operatorID, "");

            if (provider != null) {
                if (prClientUtil.getActiveProviderClient_by_provider_client_operator(session, provider, client, operator, "") != null) {

                    ProviderClient prClient = new ProviderClient(operator, client, user, new Date());
                    prClient.setTraderByIdprovider(provider);
                    prClient.setLimitTransact(soldeLimit);
                    prClient.setSolde(0.0);
                    prClient.setFlag(0);
                    prClientUtil.addProviderClient(prClient, session);

                } else {
                    session.getTransaction().rollback();
                    session.close();
                    return staticVars.providerClientLinkAlreadyExists;
                }

            } else {
                if (prClientUtil.getProviderClient_by_operatror_client(session, client, operator, "").isEmpty()) {

                    ProviderClient prClient = new ProviderClient(operator, client, user, new Date());
                    prClient.setLimitTransact(soldeLimit);
                    prClient.setSolde(-1.0);
                    prClient.setFlag(0);
                    prClientUtil.addProviderClient(prClient, session);

                } else {
                    session.getTransaction().rollback();
                    session.close();
                    return staticVars.providerClientLinkAlreadyExists;
                }
            }
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.addProviderClientLink() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addProviderClientLink(Session session, UserInfo user, Trader provider, Trader client, Operator operator, double soldeLimit) {

        try {
            //session.getTransaction().begin();
            ProviderClient_Util prClientUtil = new ProviderClient_Util();

            if (provider != null) {
                if (prClientUtil.getActiveProviderClient_by_provider_client_operator1(session, provider, client, operator, "") != null) {
                    System.out.println("general_helpers.dbhelper.addProviderClientLink()" + "in first if  ");
                    ProviderClient prClient = new ProviderClient(operator, client, user, new Date());
                    prClient.setTraderByIdprovider(provider);
                    prClient.setLimitTransact(soldeLimit);
                    prClient.setSolde(0.0);
                    prClient.setFlag(0);
                    prClientUtil.addProviderClient(prClient, session);

                } else {
                    if (provider.getTraderCategory().getTraderCategoryDesc().equals(staticVars.traderCategory_Grossiste)) {
                        ProviderClient prClient = new ProviderClient(operator, client, user, new Date());
                        prClient.setLimitTransact(soldeLimit);
                        prClient.setSolde(-1.0);
                        prClient.setFlag(0);
                        prClient.setTraderByIdprovider(provider);
                        prClientUtil.addProviderClient(prClient, session);
                    }

                }
            } else {
                if (prClientUtil.getProviderClient_by_operatror_client(session, provider, operator, "").isEmpty()) {
                    System.out.println("general_helpers.dbhelper.addProviderClientLink()" + "in secend if  ");

                    ProviderClient prClient = new ProviderClient(operator, client, user, new Date());
                    prClient.setLimitTransact(soldeLimit);
                    prClient.setSolde(-1.0);
                    prClient.setFlag(0);
                    prClientUtil.addProviderClient(prClient, session);

                }
            }
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("helpers.dbhelper.addProviderClientLink() : UNKNOWN ERROR");
            return staticVars.unknownError;
        }
    }

    public int addTrader(int userID, int traderParentID, String traderCategory, String traderType, String traderFname, String traderLname, String traderCompany, String simnumber, String adresse, String commune, String wilaya, String email1, String email2, String tel1, String tel2, Vector<Integer> operators, Vector<Double> limitSolde) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Trader_Util traderutil = new Trader_Util();

            if (!traderutil.getTrader_by_traderCompany(session, traderCompany, "").isEmpty()) {
                session.getTransaction().commit();
                session.close();
                return staticVars.TraderAlreadyExists;
            }

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Trader parent = traderutil.getTradfer_by_id(session, traderParentID, "");

            Trader trader2add = new Trader(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    new TraderCategory_Util().getTraderCategory_by_traderCategoryDesc(session, traderCategory, ""),
                    new TraderType_Util().getTraderType_by_traderTypeDesc(session, traderType, ""),
                    user, user, traderFname, traderLname, traderCompany, simnumber, adresse, commune, wilaya, email1, email2, tel1, tel2);

            trader2add.setFlag(0);
            traderutil.addTrader(trader2add, session);

            for (int i = 0; i < operators.size(); i++) {
                this.addProviderClientLink(session, user, parent, trader2add, new Operator_Util().getOperator_by_id(session, operators.get(i), ""), limitSolde.get(i));
            }

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.addTrader() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addTrader(userUI user, int traderParentID, String traderCategory, String traderType, String traderFname, String traderLname, String traderCompany, String simnumber, String adresse, String commune, String wilaya, String email1, String email2, String tel1, String tel2, Vector<Operator> operators, Vector<Double> limitSolde) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Trader_Util traderutil = new Trader_Util();

            if (!traderutil.getTrader_by_traderCompany(session, traderCompany, "").isEmpty()) {
                session.getTransaction().commit();
                session.close();
                return staticVars.TraderAlreadyExists;
            }

            Trader parent = traderutil.getTradfer_by_id(session, traderParentID, "");

            Trader trader2add = new Trader(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    new TraderCategory_Util().getTraderCategory_by_traderCategoryDesc(session, traderCategory, ""),
                    new TraderType_Util().getTraderType_by_traderTypeDesc(session, traderType, ""),
                    user.getActualUser(), user.getActualUser(), traderFname, traderLname, traderCompany, simnumber, adresse, commune, wilaya, email1, email2, tel1, tel2);

            trader2add.setFlag(0);
            traderutil.addTrader(trader2add, session);

            for (int i = 0; i < operators.size(); i++) {
                this.addProviderClientLink(session, user.getActualUser(), parent, trader2add, operators.get(i), limitSolde.get(i));
            }

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.addTrader() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addTrader(int userID, String traderParentCompany, String traderCategory, String traderType, String traderFname, String traderLname, String traderCompany, String simnumber, String adresse, String commune, String wilaya, String email1, String email2, String tel1, String tel2, Vector<Integer> operators, Vector<Double> limitSolde) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Trader_Util traderutil = new Trader_Util();

            if (!traderutil.getTrader_by_traderCompany(session, traderCompany, "").isEmpty()) {
                session.getTransaction().commit();
                session.close();
                return staticVars.TraderAlreadyExists;
            }

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Trader parent = (Trader) traderutil.getTrader_by_traderCompany(session, traderParentCompany, "").get(0);

            Trader trader2add = new Trader(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    new TraderCategory_Util().getTraderCategory_by_traderCategoryDesc(session, traderCategory, ""),
                    new TraderType_Util().getTraderType_by_traderTypeDesc(session, traderType, ""),
                    user, user, traderFname, traderLname, traderCompany, simnumber, adresse, commune, wilaya, email1, email2, tel1, tel2);

            trader2add.setFlag(0);
            traderutil.addTrader(trader2add, session);

            for (int i = 0; i < operators.size(); i++) {
                this.addProviderClientLink(session, user, parent, trader2add, new Operator_Util().getOperator_by_id(session, operators.get(i), ""), limitSolde.get(i));
            }

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.addTrader() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addTrader(userUI user, String traderParentCompany, String traderCategory, String traderType, String traderFname, String traderLname, String traderCompany, String simnumber, String adresse, String commune, String wilaya, String email1, String email2, String tel1, String tel2, Vector<Operator> operators, Vector<Double> limitSolde) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Trader_Util traderutil = new Trader_Util();

            if (!traderutil.getTrader_by_traderCompany(session, traderCompany, "").isEmpty()) {
                session.getTransaction().commit();
                session.close();
                return staticVars.TraderAlreadyExists;
            }

            Trader parent = (Trader) traderutil.getTrader_by_traderCompany(session, traderParentCompany, "").get(0);

            Trader trader2add = new Trader(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    new TraderCategory_Util().getTraderCategory_by_traderCategoryDesc(session, traderCategory, ""),
                    new TraderType_Util().getTraderType_by_traderTypeDesc(session, traderType, ""),
                    user.getActualUser(), user.getActualUser(), traderFname, traderLname, traderCompany, simnumber, adresse, commune, wilaya, email1, email2, tel1, tel2);

            trader2add.setFlag(0);
            traderutil.addTrader(trader2add, session);

            for (int i = 0; i < operators.size(); i++) {
                this.addProviderClientLink(session, user.getActualUser(), parent, trader2add, operators.get(i), limitSolde.get(i));
            }

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.addTrader() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addTrader_forActualUser(int userID, String traderCategory, String traderType, String traderFname, String traderLname, String traderCompany, String simnumber, String adresse, String commune, String wilaya, String email1, String email2, String tel1, String tel2, Vector<Integer> operators, Vector<Double> limitSolde) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Trader_Util traderutil = new Trader_Util();

            if (!traderutil.getTrader_by_traderCompany(session, traderCompany, "").isEmpty()) {
                session.getTransaction().commit();
                session.close();
                return staticVars.TraderAlreadyExists;
            }

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Trader parent = user.getTrader();

            Trader trader2add = new Trader(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    new TraderCategory_Util().getTraderCategory_by_id(session, Integer.parseInt(traderCategory), ""),
                    new TraderType_Util().geTraderType_by_id(session, Integer.parseInt(traderType), ""),
                    user, user, traderFname, traderLname, traderCompany, simnumber, adresse, commune, wilaya, email1, email2, tel1, tel2);

            trader2add.setFlag(0);
            traderutil.addTrader(trader2add, session);

            for (int i = 0; i < operators.size(); i++) {
                this.addProviderClientLink(session, user, parent, trader2add, new Operator_Util().getOperator_by_id(session, operators.get(i), ""), limitSolde.get(i));
            }

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.addTrader() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addTrader_forActualUser_AndLink(int userID, String providerTrader, String traderCategory, String traderType,
            String traderFname, String traderLname, String traderCompany, String simnumber, String adresse, String commune, String wilaya, String email1,
            String email2, String tel1, String tel2, Vector<Integer> operators, Vector<Double> limitSolde, String sn1, String sn2,
            String typeStation, String serverProfile) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Trader_Util traderutil = new Trader_Util();

            if (!traderutil.getTrader_by_traderCompany(session, traderCompany, "").isEmpty()) {
                session.getTransaction().commit();
                session.close();
                return staticVars.TraderAlreadyExists;
            }

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Trader parent = new Trader_Util().getTradfer_by_id(session, Integer.parseInt(providerTrader), "");

            Trader trader2add = new Trader(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    new TraderCategory_Util().getTraderCategory_by_id(session, Integer.parseInt(traderCategory), ""),
                    new TraderType_Util().geTraderType_by_id(session, Integer.parseInt(traderType), ""),
                    user, user, traderFname, traderLname, traderCompany, simnumber, adresse, commune, wilaya, email1, email2, tel1, tel2);

            trader2add.setFlag(0);
            traderutil.addTrader(trader2add, session);
            System.out.println("general_helpers.dbhelper.addTrader_forActualUser_AndLink()" + parent.getTraderCompany());
            for (int i = 0; i < operators.size(); i++) {
                System.out.println("general_helpers.dbhelper.addTrader_forActualUser_AndLink()");
                this.addProviderClientLink(session, user, parent, trader2add, new Operator_Util().getOperator_by_id(session, operators.get(i), ""), limitSolde.get(i));
            }
            System.out.println("general_helpers.dbhelper.addTrader_forActualUser_AndLink()traderType =>" + traderType);
            int rep = 1;
            if (traderType.equals("1")) {
                StationType stationType = (StationType) new StationType_Util().getStationType_by_id(session, Integer.parseInt(typeStation), "");
                ServerProfile profile = (ServerProfile) new ServerProfile_Util().getStationType_by_id(session, Integer.parseInt(serverProfile), "");

                rep = this.addStation(session, user, trader2add, stationType, trader2add.getTraderCompany(), trader2add.getTraderCompany(), sn1, sn2, "", profile);
                if (rep == staticVars.unknownError) {
                    throw new Exception();
                }

            }
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.addTrader() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addTrader_forActualUser(userUI user, String traderCategory, String traderType, String traderFname, String traderLname, String traderCompany, String simnumber, String adresse, String commune, String wilaya, String email1, String email2, String tel1, String tel2, Vector<Operator> operators, Vector<Double> limitSolde) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Trader_Util traderutil = new Trader_Util();

            if (!traderutil.getTrader_by_traderCompany(session, traderCompany, "").isEmpty()) {
                session.getTransaction().commit();
                session.close();
                return staticVars.TraderAlreadyExists;
            }

            Trader parent = user.getActualUser().getTrader();

            Trader trader2add = new Trader(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    new TraderCategory_Util().getTraderCategory_by_traderCategoryDesc(session, traderCategory, ""),
                    new TraderType_Util().getTraderType_by_traderTypeDesc(session, traderType, ""),
                    user.getActualUser(), user.getActualUser(), traderFname, traderLname, traderCompany, simnumber, adresse, commune, wilaya, email1, email2, tel1, tel2);

            trader2add.setFlag(0);
            traderutil.addTrader(trader2add, session);

            for (int i = 0; i < operators.size(); i++) {
                this.addProviderClientLink(session, user.getActualUser(), parent, trader2add, operators.get(i), limitSolde.get(i));
            }

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.addTrader() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }

    }

    public int addDistributor(int userID, String traderType, String traderFname, String traderLname, String traderCompany, String simnumber, String adresse, String commune, String wilaya, String email1, String email2, String tel1, String tel2, Vector<Integer> operators) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Trader_Util traderutil = new Trader_Util();

            if (!traderutil.getTrader_by_traderCompany(session, traderCompany, "").isEmpty()) {
                session.getTransaction().commit();
                session.close();
                return staticVars.TraderAlreadyExists;
            }

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");

            Trader trader2add = new Trader(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    new TraderCategory_Util().getTraderCategory_by_traderCategoryDesc(session, staticVars.traderCategory_Grossiste, ""),
                    new TraderType_Util().getTraderType_by_traderTypeDesc(session, traderType, ""),
                    user, user, traderFname, traderLname, traderCompany, simnumber, adresse, commune, wilaya, email1, email2, tel1, tel2);

            trader2add.setFlag(0);
            traderutil.addTrader(trader2add, session);

            for (int i = 0; i < operators.size(); i++) {
                this.addProviderClientLink(session, user, null, trader2add, new Operator_Util().getOperator_by_id(session, operators.get(i), ""), -1);
            }

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {

            System.out.println("helpers.dbhelper.addTrader() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addDistributor_forActualUser(userUI user, String traderType, String traderFname, String traderLname, String traderCompany, String simnumber, String adresse, String commune, String wilaya, String email1, String email2, String tel1, String tel2, Vector<Operator> operators) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Trader_Util traderutil = new Trader_Util();

            if (!traderutil.getTrader_by_traderCompany(session, traderCompany, "").isEmpty()) {
                session.getTransaction().commit();
                session.close();
                return staticVars.TraderAlreadyExists;
            }

            Trader trader2add = new Trader(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    new TraderCategory_Util().getTraderCategory_by_traderCategoryDesc(session, staticVars.traderCategory_Grossiste, ""),
                    new TraderType_Util().getTraderType_by_traderTypeDesc(session, traderType, ""),
                    user.getActualUser(), user.getActualUser(), traderFname, traderLname, traderCompany, simnumber, adresse, commune, wilaya, email1, email2, tel1, tel2);

            trader2add.setFlag(0);
            traderutil.addTrader(trader2add, session);

            //session.refresh(trader2add);
            for (int i = 0; i < operators.size(); i++) {
                this.addProviderClientLink(session, user.getActualUser(), null, trader2add, operators.get(i), -1);
            }

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("helpers.dbhelper.addTrader() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addStation(Session session, UserInfo userInfo, Trader trader, String stationType, String stationBrand, String stationReference, String stationSn1, String stationSn2, String appversion) {
        try {
            session.getTransaction().begin();
            station_Util stationUtil = new station_Util();
            Station station = new Station(new StationType_Util().getStationType_by_StationTypeDesc(session, stationType, ""),
                    new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    trader, userInfo, stationBrand, stationReference, stationSn1, stationSn2, appversion);
            stationUtil.addStation(station, session);
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.addStation() : UNKNOWN ERROR");
            return staticVars.unknownError;
        }
    }

    public int addStation(Session session, UserInfo userInfo, Trader trader, StationType stationType, String stationBrand, String stationReference, String stationSn1, String stationSn2, String appversion) {
        try {
            session.getTransaction().begin();
            station_Util stationUtil = new station_Util();
            Station station = new Station(stationType,
                    new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    trader, userInfo, stationBrand, stationReference, stationSn1, stationSn2, appversion);
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("general_helpers.dbhelper.addStation()" + e.getMessage());
            System.out.println("helpers.dbhelper.addStation() : UNKNOWN ERROR");
            return staticVars.unknownError;
        }
    }

    public int addStation(Session session, UserInfo userInfo, Trader trader, StationType stationType, String stationBrand, String stationReference, String stationSn1, String stationSn2, String appversion, ServerProfile profile) {
        try {
            station_Util stationUtil = new station_Util();
            Station station = new Station(stationType,
                    new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    trader, userInfo, stationBrand, stationReference, stationSn1, stationSn2, appversion);
            station.setStationName(trader.getTraderFname()+"-"+stationType.getStationTypeDesc());
            station.setServerProfile(profile);
            stationUtil.addStation(station, session);
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("general_helpers.dbhelper.addStation()" + e.getMessage());
            System.out.println("helpers.dbhelper.addStation() : UNKNOWN ERROR");
            return staticVars.unknownError;
        }
    }

    public int addStation(UserInfo userInfo, Trader trader, String stationType, String stationBrand, String stationReference, String stationSn1, String stationSn2, String appversion) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            station_Util stationUtil = new station_Util();
            Station station = new Station(new StationType_Util().getStationType_by_StationTypeDesc(session, stationType, ""),
                    new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    trader, userInfo, stationBrand, stationReference, stationSn1, stationSn2, appversion);
            stationUtil.addStation(station, session);
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addStation(int userID, String traderParentCompany, String stationType, String stationBrand, String stationReference, String stationSn1, String stationSn2, String appversion) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            station_Util stationUtil = new station_Util();

            UserInfo userInfo = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Trader trader = (Trader) new Trader_Util().getTrader_by_traderCompany(session, traderParentCompany, "").get(0);

            Station station = new Station(new StationType_Util().getStationType_by_StationTypeDesc(session, stationType, ""),
                    new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    trader, userInfo, stationBrand, stationReference, stationSn1, stationSn2, appversion);
            stationUtil.addStation(station, session);
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addStation(int userID, int traderID, String stationType, String stationBrand, String stationReference, String stationSn1, String stationSn2, String appversion) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            station_Util stationUtil = new station_Util();

            UserInfo userInfo = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Trader trader = new Trader_Util().getTradfer_by_id(session, traderID, "");

            Station station = new Station(new StationType_Util().getStationType_by_StationTypeDesc(session, stationType, ""),
                    new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""),
                    trader, userInfo, stationBrand, stationReference, stationSn1, stationSn2, appversion);
            stationUtil.addStation(station, session);
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addStation(userUI userInfo, int traderID, String stationType, String stationBrand, String stationReference, String stationSn1, String stationSn2, String appversion) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            station_Util stationUtil = new station_Util();

            Trader trader = new Trader_Util().getTradfer_by_id(session, traderID, "");

            Station station = new Station(userInfo.getTsUI().getStationTypeByDesc(stationType),
                    userInfo.getTsUI().getStatusInfoByDesc(staticVars.status_ENT_Actif),
                    trader, userInfo.getActualUser(), stationBrand, stationReference, stationSn1, stationSn2, appversion);
            stationUtil.addStation(station, session);
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addOffer(userUI user, String operatorName, String offerDesc, String offerTypeDesc, String prenumber, String postnumber, String postpincode,
            double transfertValue, double realValue, int isStatic, Vector<String> precolumns, Vector<String> columnsDescs, Vector<String> postColumns, Vector<Integer> positions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            OfferInfo_Util OfferUtil = new OfferInfo_Util();
            if (OfferUtil.getOfferInfo_by_offerDesc(session, offerDesc, "").size() > 0) {
                return staticVars.OfferAlreadyExists;
            }

            UserInfo userInfo = user.getActualUser();
            OfferType offerType = user.getTsUI().getOfferTypeByDesc(offerTypeDesc);
            Operator operator = user.getOperatorUIbyName(operatorName).getOperaror();

            OfferInfo offer = new OfferInfo(offerType, operator, userInfo, offerDesc, new Date(), transfertValue, realValue);
            offer.setIsStatic(isStatic);
            offer.setPrenumber(prenumber);
            offer.setPostnumber(postnumber);
            offer.setPostPinCode(postpincode);

            session.getTransaction().commit();
            session.getTransaction().begin();

            if (isStatic == 1) {
                //ajouter offer details
                for (int i = 0; i < positions.size(); i++) {
                    this.addOfferDetails(session, offer, precolumns.get(i), columnsDescs.get(i), postColumns.get(i), positions.get(i));
                }
            }
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.addOffer() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addOffer(int userID, String operatorName, String offerDesc, String offerTypeDesc, String prenumber, String postnumber, String postpincode,
            double transfertValue, double realValue, int isStatic, Vector<String> precolumns, Vector<String> columnsDescs, Vector<String> postColumns, Vector<Integer> positions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            OfferInfo_Util OfferUtil = new OfferInfo_Util();
            if (OfferUtil.getOfferInfo_by_offerDesc(session, offerDesc, "").size() > 0) {
                return staticVars.OfferAlreadyExists;
            }

            UserInfo userInfo = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            OfferType offerType = new OfferType_Util().getOfferType_by_offerTypeDesc(session, offerTypeDesc, "");
            Operator operator = (Operator) new Operator_Util().getOperator_by_operatorDesc(session, operatorName, "").get(0);

            OfferInfo offer = new OfferInfo(offerType, operator, userInfo, offerDesc, new Date(), transfertValue, realValue);
            offer.setIsStatic(isStatic);
            offer.setPrenumber(prenumber);
            offer.setPostnumber(postnumber);
            offer.setPostPinCode(postpincode);

            session.getTransaction().commit();
            session.getTransaction().begin();

            if (isStatic == 1) {
                //ajouter offer details
                for (int i = 0; i < positions.size(); i++) {
                    this.addOfferDetails(session, offer, precolumns.get(i), columnsDescs.get(i), postColumns.get(i), positions.get(i));
                }
            }
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.addOffer() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addOffer(int userID, int operatorID, String offerDesc, int offerTypeID, String prenumber, String postnumber, String postpincode,
            double transfertValue, double realValue, int isStatic, Vector<String> precolumns, Vector<String> columnsDescs, Vector<String> postColumns, Vector<Integer> positions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            OfferInfo_Util OfferUtil = new OfferInfo_Util();
            if (OfferUtil.getOfferInfo_by_offerDesc(session, offerDesc, "").size() > 0) {
                return staticVars.OfferAlreadyExists;
            }

            UserInfo userInfo = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            OfferType offerType = new OfferType_Util().getOfferType_by_id(session, offerTypeID, "");
            Operator operator = new Operator_Util().getOperator_by_id(session, operatorID, "");

            OfferInfo offer = new OfferInfo(offerType, operator, userInfo, offerDesc, new Date(), transfertValue, realValue);
            offer.setIsStatic(isStatic);
            offer.setPrenumber(prenumber);
            offer.setPostnumber(postnumber);
            offer.setPostPinCode(postpincode);

            session.getTransaction().commit();
            session.getTransaction().begin();

            if (isStatic == 1) {
                //ajouter offer details
                for (int i = 0; i < positions.size(); i++) {
                    this.addOfferDetails(session, offer, precolumns.get(i), columnsDescs.get(i), postColumns.get(i), positions.get(i));
                }
            }
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.addOffer() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addOfferDetails(Session session, OfferInfo offer, String precolumn, String columnDesc, String postColumn, int position) {
        try {
            session.getTransaction().begin();

            OfferInfoDetails_Util OfferDetailsUtil = new OfferInfoDetails_Util();

            OfferInfoDetails offerDetails = new OfferInfoDetails(offer, precolumn, columnDesc, postColumn, position, 0);
            OfferDetailsUtil.addOfferInfoDetails(offerDetails, session);

            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.addOfferDetails() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int updateVirtualBalance(int userID, int clientID, int operatorID, double amount) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.getTransaction().begin();
            System.out.println("general_helpers.dbhelper.updateVirtualBalance() etap 0");

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Trader client = new Trader_Util().getTradfer_by_id(session, clientID, "");
            Operator operator = new Operator_Util().getOperator_by_id(session, operatorID, "");
            System.out.println("general_helpers.dbhelper.updateVirtualBalance() etap 001" + operator.getOperatorDesc() + " == " + user.getUsername() + " === " + client.getTraderCompany());
            ProviderClient affectation = new ProviderClient_Util().getProviderClient_by_provider_client_operator(session, user.getTrader(), client, operator, "");
            System.out.println("general_helpers.dbhelper.updateVirtualBalance() etap 1");
            StatusInfo enCoursST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_EnInstance, "");
            System.out.println("general_helpers.dbhelper.updateVirtualBalance() etap 1");
            StatusInfo termineST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Reussie, "");
            System.out.println("general_helpers.dbhelper.updateVirtualBalance() etap 1");
            StatusInfo interrompueST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Annule, "");
            double newBalance = affectation.getSolde() + amount;
            System.out.println("general_helpers.dbhelper.updateVirtualBalance() etap 1");
            TransactionSolde transactSolde = new TransactionSolde(affectation, enCoursST, user, affectation.getSolde(), newBalance, amount, new Date());
            new TransactionSolde_Util().addTransactionSolde(transactSolde, session);
            session.getTransaction().commit();
            System.out.println("general_helpers.dbhelper.updateVirtualBalance() etap 2");

            session.getTransaction().begin();
            if (((amount < 0) && (Math.abs(amount) > affectation.getSolde()))
                    || (amount + affectation.getLimitTransact() > affectation.getLimitTransact() && affectation.getLimitTransact() != -1)
                    || (amount > operator.getTransactLimit() && operator.getTransactLimit() != -1)) {
                System.out.println("general_helpers.dbhelper.updateVirtualBalance() etap 3");

                transactSolde.setStatusInfo(interrompueST);
                session.getTransaction().commit();
                session.close();
                return staticVars.LimitExceeded;
            } else {
                System.out.println("general_helpers.dbhelper.updateVirtualBalance() etap 4");

                affectation.setSolde(newBalance);
                transactSolde.setStatusInfo(termineST);
                session.getTransaction().commit();
                session.close();
                return staticVars.onGoingProcessOK;
            }
        } catch (Exception e) {
            System.out.println("general_helpers.dbhelper.updateVirtualBalance()" + e.getMessage());
            System.out.println("helpers.dbhelper.updateVirtualBalance() : UNKNOWN ERROR");
            session.getTransaction().commit();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int updateVirtualBalance(Session session, int userID, int clientID, int operatorID, double amount) {
        try {
            session.getTransaction().begin();

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Trader client = new Trader_Util().getTradfer_by_id(session, clientID, "");
            Operator operator = new Operator_Util().getOperator_by_id(session, operatorID, "");
            ProviderClient affectation = new ProviderClient_Util().getProviderClient_by_provider_client_operator(session, user.getTrader(), client, operator, "");
            StatusInfo enCoursST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_EnInstance, "");
            StatusInfo termineST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Reussie, "");
            StatusInfo interrompueST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Annule, "");
            double newBalance = affectation.getSolde() + amount;

            TransactionSolde transactSolde = new TransactionSolde(affectation, enCoursST, user, affectation.getSolde(), newBalance, amount, new Date());
            new TransactionSolde_Util().addTransactionSolde(transactSolde, session);
            session.getTransaction().commit();

            session.getTransaction().begin();
            if (((amount < 0) && (Math.abs(amount) > affectation.getSolde()))
                    || (amount + affectation.getLimitTransact() > affectation.getLimitTransact() && affectation.getLimitTransact() != -1)
                    || (amount > operator.getTransactLimit() && operator.getTransactLimit() != -1)) {
                transactSolde.setStatusInfo(interrompueST);
                session.getTransaction().commit();
                return staticVars.LimitExceeded;
            } else {
                affectation.setSolde(newBalance);
                transactSolde.setStatusInfo(termineST);
                session.getTransaction().commit();
                return staticVars.onGoingProcessOK;
            }
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.updateVirtualBalance() : UNKNOWN ERROR");
            session.getTransaction().commit();
            return staticVars.unknownError;
        }
    }

    public int updateTopUpBalance(int userID, int operatorID, int offerID, String simNUM, double amount) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Session session_solde = HibernateUtil.getSessionFactory().openSession();
        int checkStatus = -1;

        TransactionTopup_Util topupUtil = new TransactionTopup_Util();
        SimInfo_Util simUtil = new SimInfo_Util();
        ProviderClient_Util providerClientUtil = new ProviderClient_Util();

        long start = System.currentTimeMillis();
        long end;
        double transactDuration;

        try {
            session.getTransaction().begin();
            session_solde.getTransaction().begin();

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Operator operator = new Operator_Util().getOperator_by_id(session, operatorID, "");
            OfferInfo offer = new OfferInfo_Util().getOfferInfo_by_id(session, offerID, "");
            StatusInfo enCoursST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_EnInstance, "");
            StatusInfo termineST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Reussie, "");
            StatusInfo interrompueST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Annule, "");
            TransactionType transfertCreditTYPE = new TransactionType_Util().getTransacType_by_transactionTypeDesc(session, staticVars.transactType_TrasfertCredit, "");

            String sentMessage;

            /*
            CHECK CLIENT'S SOLDES FOR THIS OPERATOR
             */
            double removedSolde = amount;
            int index = 0;
            List providerClientList = providerClientUtil.getProviderClient_by_operatror_client(session, user.getTrader(), operator, "");

            while (removedSolde > 0 && index < providerClientList.size()) {
                ProviderClient proCl = (ProviderClient) providerClientList.get(index);
                if (proCl.getTraderByIdprovider() != null) {// SI c'est le distributeur principale
                    if (proCl.getSolde() >= removedSolde) {
                        proCl.setSolde(proCl.getSolde() - removedSolde);
                        removedSolde = 0;
                        providerClientUtil.updateProviderClient(proCl, session_solde);
                    } else {
                        removedSolde = removedSolde - proCl.getSolde();
                        proCl.setSolde(0.0);
                        providerClientUtil.updateProviderClient(proCl, session_solde);
                    }
                    index++;
                } else {
                    removedSolde = 0;
                }
            }

            if (removedSolde == 0) {
                /*
                ROUND ROBIN OU POIDS PONDERE
                 */
                //ROUND ROBIN
                //recuperer que  les offres des sim actives et simOffer where deactivation date = null
                List sim_offer_LIST = new SimOffer_Util().getSimOffer_by_offerInfo_ActiveSims(session, offer, new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""), " ORDER BY simInfo.totalTransactions ASC");
                //List sim_offer_LIST = new SimOffer_Util().getSimOffer_by_offerInfo(session, offer, " ORDER BY simInfo.totalTransactions ASC");
                //POIDS PONDERE
                //List sim_offer_LIST = new SimOffer_Util().getSimOffer_by_offerInfo(session, offer, " ORDER BY simInfo.weightedIndex DESC");
                int i = 0;
                checkStatus = staticVars.noValidSim;
                if (sim_offer_LIST.isEmpty()) {
                    session.getTransaction().commit();
                    session.close();
                    session_solde.getTransaction().rollback();
                    session_solde.close();
                    return staticVars.noValidSim;
                }
                while ((i < sim_offer_LIST.size()) && (checkStatus != staticVars.onGoingProcessOK)) {
                    SimOffer simo_offer = (SimOffer) sim_offer_LIST.get(i);
                    double realValue = amount;
                    if (simo_offer.getOfferInfo().getRealValue() != -1) {
                        realValue = simo_offer.getOfferInfo().getRealValue();
                    }
                    sentMessage = offer.getPrenumber() + simNUM + offer.getPostnumber() + simo_offer.getSimInfo().getSimPinCode() + offer.getPostPinCode();
                    TransactionTopup transactTopUp = new TransactionTopup(enCoursST, transfertCreditTYPE, simNUM, simo_offer.getSimInfo().getLastEstimatedSolde() - amount, amount, realValue, new Date(), 0, "", sentMessage);
                    transactTopUp.setSimOffer(simo_offer);
                    transactTopUp.setUserInfo(user);
                    topupUtil.addTransactionTopup(transactTopUp, session);
                    session.getTransaction().commit();
                    session.getTransaction().begin();
                    try {
                        if (amount > operator.getTransactLimit() && operator.getTransactLimit() != -1) {
                            transactTopUp.setRecievedMessage("LIMITE FIXÈE POUR L'OPÈRATEUR EST " + String.valueOf(operator.getTransactLimit()));
                            checkStatus = staticVars.LimitExceeded;
                        } else {
                            if (amount > offer.getLimitTransact() && offer.getLimitTransact() != -1) {
                                transactTopUp.setRecievedMessage("LIMITE FIXÈE POUR L'OFFRE EST " + String.valueOf(offer.getLimitTransact()));
                                checkStatus = staticVars.LimitExceeded;
                            }
                        }
                        if (checkStatus == staticVars.LimitExceeded) {
                            end = System.currentTimeMillis();
                            transactDuration = (end - start) / 1000F;
                            transactTopUp.setStatusInfo(interrompueST);
                            transactTopUp.setTransactDuration(transactDuration);
                            topupUtil.updateTransactionTopup(transactTopUp, session);
                            session.getTransaction().commit();
                            session_solde.getTransaction().rollback();
                        } else {

                            try {
                                // send request to SIMBOB
                                simBoxTransaction request
                                        = proc_fct.sendTopUp_FromSimBox(new simBoxTransaction(simo_offer.getSimInfo().getSimnumber(), simo_offer.getSimInfo().getPortInfo().getPortDesc(), sentMessage));

                                //wait for SIMBOX response : it shoumd fill the simBoxTransaction object with new STATUS and RECIEVED MESSAGE
                                if (request.getStatus() == 1) {
                                    transactTopUp.setStatusInfo(termineST);

                                    end = System.currentTimeMillis();
                                    transactDuration = (end - start) / 1000F;

                                    simo_offer.getSimInfo().setLastEstimatedSolde(simo_offer.getSimInfo().getLastEstimatedSolde() - amount);
                                    simo_offer.getSimInfo().setAverageResponseTime((simo_offer.getSimInfo().getAverageResponseTime() + transactDuration) / 2.0);
                                    simo_offer.getSimInfo().setSuccededTransactions(simo_offer.getSimInfo().getSuccededTransactions() + 1);

                                    if (simo_offer.getSimInfo().getLastSignalStrength() != 0) {
                                        simo_offer.getSimInfo().setWeightedIndex((simo_offer.getSimInfo().getAverageResponseTime()
                                                * simo_offer.getSimInfo().getLastSignalStrength() * simo_offer.getSimInfo().getSuccededTransactions())
                                                / (simo_offer.getSimInfo().getTotalTransactions() + 1));
                                    }

                                    simUtil.updateSimInfo(simo_offer.getSimInfo(), session_solde);

                                    checkStatus = staticVars.onGoingProcessOK;
                                    session_solde.getTransaction().commit();
                                } else {
                                    transactTopUp.setStatusInfo(interrompueST);
                                    checkStatus = staticVars.TopUpError;
                                    session_solde.getTransaction().rollback();
                                }

                                simo_offer.getSimInfo().setTotalTransactions(simo_offer.getSimInfo().getTotalTransactions() + 1);
                                simUtil.updateSimInfo(simo_offer.getSimInfo(), session_solde);
                                simUtil.updateSimInfo(simo_offer.getSimInfo(), session);

                                transactTopUp.setRecievedMessage(request.getReceivedMessage());
                                end = System.currentTimeMillis();
                                transactDuration = (end - start) / 1000F;
                                transactTopUp.setTransactDuration(transactDuration);

                                topupUtil.updateTransactionTopup(transactTopUp, session);
                                session.getTransaction().commit();

                            } catch (Exception e) {
                                System.out.println("helpers.dbhelper.updateVirtualBalance() : UNKNOWN ERROR");
                                end = System.currentTimeMillis();
                                transactDuration = (end - start) / 1000F;
                                transactTopUp.setStatusInfo(interrompueST);
                                transactTopUp.setTransactDuration(transactDuration);
                                simo_offer.getSimInfo().setTotalTransactions(simo_offer.getSimInfo().getTotalTransactions() + 1);
                                simUtil.updateSimInfo(simo_offer.getSimInfo(), session_solde);
                                simUtil.updateSimInfo(simo_offer.getSimInfo(), session);
                                session.getTransaction().commit();
                                session_solde.getTransaction().rollback();
                                session.close();
                                session_solde.close();
                                return staticVars.unknownError;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("helpers.dbhelper.updateVirtualBalance() : UNKNOWN ERROR");
                        end = System.currentTimeMillis();
                        transactDuration = (end - start) / 1000F;
                        transactTopUp.setStatusInfo(interrompueST);
                        transactTopUp.setTransactDuration(transactDuration);
                        simo_offer.getSimInfo().setTotalTransactions(simo_offer.getSimInfo().getTotalTransactions() + 1);
                        simUtil.updateSimInfo(simo_offer.getSimInfo(), session_solde);
                        simUtil.updateSimInfo(simo_offer.getSimInfo(), session);
                        session.getTransaction().commit();
                        session_solde.getTransaction().rollback();
                        session.close();
                        session_solde.close();
                        return staticVars.unknownError;
                    }
                }
            } else {

                session_solde.getTransaction().rollback();
                checkStatus = staticVars.insufficientBalance;
            }
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.updateVirtualBalance() : UNKNOWN ERROR");
            session_solde.getTransaction().rollback();
            session.close();
            session_solde.close();
            return staticVars.unknownError;
        }
        session.close();
        session_solde.close();
        return checkStatus;
    }

    public int updateTopUpBalance(Session session, int userID, int operatorID, int offerID, String simNUM, double amount) {
        Session session_solde = HibernateUtil.getSessionFactory().openSession();
        int checkStatus = -1;

        TransactionTopup_Util topupUtil = new TransactionTopup_Util();
        SimInfo_Util simUtil = new SimInfo_Util();
        ProviderClient_Util providerClientUtil = new ProviderClient_Util();

        long start = System.currentTimeMillis();
        long end;
        double transactDuration;

        try {
            session.getTransaction().begin();
            session_solde.getTransaction().begin();

            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            Operator operator = new Operator_Util().getOperator_by_id(session, operatorID, "");
            OfferInfo offer = new OfferInfo_Util().getOfferInfo_by_id(session, offerID, "");
            StatusInfo enCoursST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_EnInstance, "");
            StatusInfo termineST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Reussie, "");
            StatusInfo interrompueST = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_TCT_Annule, "");
            TransactionType transfertCreditTYPE = new TransactionType_Util().getTransacType_by_transactionTypeDesc(session, staticVars.transactType_TrasfertCredit, "");

            String sentMessage;

            /*
            CHECK CLIENT'S SOLDES FOR THIS OPERATOR
             */
            double removedSolde = amount;
            int index = 0;
            List providerClientList = providerClientUtil.getProviderClient_by_operatror_client(session, user.getTrader(), operator, "");

            while (removedSolde > 0 && index < providerClientList.size()) {
                ProviderClient proCl = (ProviderClient) providerClientList.get(index);
                if (proCl.getTraderByIdprovider() != null) {// SI c'est le distributeur principale
                    if (proCl.getSolde() >= removedSolde) {
                        proCl.setSolde(proCl.getSolde() - removedSolde);
                        removedSolde = 0;
                        providerClientUtil.updateProviderClient(proCl, session_solde);
                    } else {
                        removedSolde = removedSolde - proCl.getSolde();
                        proCl.setSolde(0.0);
                        providerClientUtil.updateProviderClient(proCl, session_solde);
                    }
                    index++;
                } else {
                    removedSolde = 0;
                }
            }
            if (removedSolde == 0) {
                /*
                ROUND ROBIN OU POIDS PONDERE
                 */
                //ROUND ROBIN
                //recuperer que  les offres des sim actives et simOffer where deactivation date = null
                List sim_offer_LIST = new SimOffer_Util().getSimOffer_by_offerInfo_ActiveSims(session, offer, new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""), " ORDER BY simInfo.totalTransactions ASC");
                //List sim_offer_LIST = new SimOffer_Util().getSimOffer_by_offerInfo(session, offer, " ORDER BY simInfo.totalTransactions ASC");
                //POIDS PONDERE
                //List sim_offer_LIST = new SimOffer_Util().getSimOffer_by_offerInfo(session, offer, " ORDER BY simInfo.weightedIndex DESC");
                checkStatus = staticVars.noValidSim;
                if (sim_offer_LIST.isEmpty()) {
                    session.getTransaction().commit();
                    session.close();
                    session_solde.getTransaction().rollback();
                    session_solde.close();
                    return staticVars.noValidSim;
                }
                int i = 0;
                while ((i < sim_offer_LIST.size()) && (checkStatus != staticVars.onGoingProcessOK)) {
                    SimOffer simo_offer = (SimOffer) sim_offer_LIST.get(i);
                    double realValue = amount;
                    if (simo_offer.getOfferInfo().getRealValue() != -1) {
                        realValue = simo_offer.getOfferInfo().getRealValue();
                    }
                    sentMessage = offer.getPrenumber() + simNUM + offer.getPostnumber() + simo_offer.getSimInfo().getSimPinCode() + offer.getPostPinCode();
                    TransactionTopup transactTopUp = new TransactionTopup(enCoursST, transfertCreditTYPE, simNUM, simo_offer.getSimInfo().getLastEstimatedSolde() - amount, amount, realValue, new Date(), 0, "", sentMessage);
                    transactTopUp.setSimOffer(simo_offer);
                    transactTopUp.setUserInfo(user);
                    topupUtil.addTransactionTopup(transactTopUp, session);
                    session.getTransaction().commit();
                    session.getTransaction().begin();
                    try {
                        if (amount > operator.getTransactLimit() && operator.getTransactLimit() != -1) {
                            transactTopUp.setRecievedMessage("LIMITE FIXÈE POUR L'OPÈRATEUR EST " + String.valueOf(operator.getTransactLimit()));
                            checkStatus = staticVars.LimitExceeded;
                        } else {
                            if (amount > offer.getLimitTransact() && offer.getLimitTransact() != -1) {
                                transactTopUp.setRecievedMessage("LIMITE FIXÈE POUR L'OFFRE EST " + String.valueOf(offer.getLimitTransact()));
                                checkStatus = staticVars.LimitExceeded;
                            }
                        }
                        if (checkStatus == staticVars.LimitExceeded) {
                            end = System.currentTimeMillis();
                            transactDuration = (end - start) / 1000F;
                            transactTopUp.setStatusInfo(interrompueST);
                            transactTopUp.setTransactDuration(transactDuration);
                            topupUtil.updateTransactionTopup(transactTopUp, session);
                            session.getTransaction().commit();
                            session_solde.getTransaction().rollback();
                        } else {

                            try {
                                // send request to SIMBOB
                                simBoxTransaction request
                                        = proc_fct.sendTopUp_FromSimBox(new simBoxTransaction(simo_offer.getSimInfo().getSimnumber(), simo_offer.getSimInfo().getPortInfo().getPortDesc(), sentMessage));

                                //wait for SIMBOX response : it shoumd fill the simBoxTransaction object with new STATUS and RECIEVED MESSAGE
                                if (request.getStatus() == 1) {
                                    transactTopUp.setStatusInfo(termineST);

                                    end = System.currentTimeMillis();
                                    transactDuration = (end - start) / 1000F;

                                    simo_offer.getSimInfo().setLastEstimatedSolde(simo_offer.getSimInfo().getLastEstimatedSolde() - amount);
                                    simo_offer.getSimInfo().setAverageResponseTime((simo_offer.getSimInfo().getAverageResponseTime() + transactDuration) / 2.0);
                                    simo_offer.getSimInfo().setSuccededTransactions(simo_offer.getSimInfo().getSuccededTransactions() + 1);

                                    if (simo_offer.getSimInfo().getLastSignalStrength() != 0) {
                                        simo_offer.getSimInfo().setWeightedIndex((simo_offer.getSimInfo().getAverageResponseTime()
                                                * simo_offer.getSimInfo().getLastSignalStrength() * simo_offer.getSimInfo().getSuccededTransactions())
                                                / (simo_offer.getSimInfo().getTotalTransactions() + 1));
                                    }

                                    simUtil.updateSimInfo(simo_offer.getSimInfo(), session_solde);

                                    checkStatus = staticVars.onGoingProcessOK;
                                    session_solde.getTransaction().commit();
                                } else {
                                    transactTopUp.setStatusInfo(interrompueST);
                                    checkStatus = staticVars.TopUpError;
                                    session_solde.getTransaction().rollback();
                                }

                                simo_offer.getSimInfo().setTotalTransactions(simo_offer.getSimInfo().getTotalTransactions() + 1);
                                simUtil.updateSimInfo(simo_offer.getSimInfo(), session_solde);
                                simUtil.updateSimInfo(simo_offer.getSimInfo(), session);

                                transactTopUp.setRecievedMessage(request.getReceivedMessage());
                                end = System.currentTimeMillis();
                                transactDuration = (end - start) / 1000F;
                                transactTopUp.setTransactDuration(transactDuration);

                                topupUtil.updateTransactionTopup(transactTopUp, session);
                                session.getTransaction().commit();

                            } catch (Exception e) {
                                System.out.println("helpers.dbhelper.updateVirtualBalance() : UNKNOWN ERROR");
                                end = System.currentTimeMillis();
                                transactDuration = (end - start) / 1000F;
                                transactTopUp.setStatusInfo(interrompueST);
                                transactTopUp.setTransactDuration(transactDuration);
                                simo_offer.getSimInfo().setTotalTransactions(simo_offer.getSimInfo().getTotalTransactions() + 1);
                                simUtil.updateSimInfo(simo_offer.getSimInfo(), session_solde);
                                simUtil.updateSimInfo(simo_offer.getSimInfo(), session);
                                session.getTransaction().commit();
                                session_solde.getTransaction().rollback();
                                session_solde.close();
                                return staticVars.unknownError;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("helpers.dbhelper.updateVirtualBalance() : UNKNOWN ERROR");
                        end = System.currentTimeMillis();
                        transactDuration = (end - start) / 1000F;
                        transactTopUp.setStatusInfo(interrompueST);
                        transactTopUp.setTransactDuration(transactDuration);
                        simo_offer.getSimInfo().setTotalTransactions(simo_offer.getSimInfo().getTotalTransactions() + 1);
                        simUtil.updateSimInfo(simo_offer.getSimInfo(), session_solde);
                        simUtil.updateSimInfo(simo_offer.getSimInfo(), session);
                        session.getTransaction().commit();
                        session_solde.getTransaction().rollback();
                        session_solde.close();
                        return staticVars.unknownError;
                    }
                }
            } else {

                session_solde.getTransaction().rollback();
                checkStatus = staticVars.insufficientBalance;
            }
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.updateVirtualBalance() : UNKNOWN ERROR");
            session_solde.getTransaction().rollback();
            session_solde.close();
            return staticVars.unknownError;
        }
        session_solde.close();
        return checkStatus;
    }

    public int addOffer(userUI user, String operatorName, String offerDesc, String offerTypeDesc, String prenumber, String postnumber, String postpincode,
            double transfertValue, double realValue, int isStatic) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();

            OfferInfo_Util OfferUtil = new OfferInfo_Util();
            if (OfferUtil.getOfferInfo_by_offerDesc(session, offerDesc, "").size() > 0) {
                return staticVars.OfferAlreadyExists;
            }

            UserInfo userInfo = user.getActualUser();
            OfferType offerType = user.getTsUI().getOfferTypeByDesc(offerTypeDesc);
            Operator operator = user.getOperatorUIbyName(operatorName).getOperaror();

            OfferInfo offer = new OfferInfo(offerType, operator, userInfo, offerDesc, new Date(), transfertValue, realValue);
            offer.setIsStatic(isStatic);
            offer.setPrenumber(prenumber);
            offer.setPostnumber(postnumber);
            offer.setPostPinCode(postpincode);

            session.getTransaction().commit();
            session.getTransaction().begin();

            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.addOffer() : UNKNOWN ERROR");
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

    public int addOffer(int userID, int operatorID, String offerDesc, int offerTypeID, String prenumber, String postnumber, String postpincode,
            double transfertValue, double realValue, int isStatic, String[] number) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {

            OfferInfo_Util OfferUtil = new OfferInfo_Util();
            if (OfferUtil.getOfferInfo_by_offerDesc(session, offerDesc, "").size() > 0) {
                return staticVars.OfferAlreadyExists;
            }
            System.out.println("general_helpers.dbhelper.addOffer()" + "=============>");

            UserInfo userInfo = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            OfferType offerType = new OfferType_Util().getOfferType_by_id(session, offerTypeID, "");
            Operator operator = new Operator_Util().getOperator_by_id(session, operatorID, "");
            System.out.println("general_helpers.dbhelper.addOffer()" + "=============>");

            OfferInfo offer = new OfferInfo(offerType, operator, userInfo, offerDesc, new Date(), realValue, transfertValue);
            offer.setIsStatic(isStatic);
            offer.setPrenumber(prenumber);
            offer.setPostnumber(postnumber);
            offer.setPostPinCode(postpincode);
            offer.setFlag(0);
            System.out.println("general_helpers.dbhelper.addOffer()" + "=============>");
            Set<SimOffer> simoffers = new HashSet<SimOffer>();

            session.saveOrUpdate(offer);
            System.out.println("general_helpers.dbhelper.addOffer()in for+ ===" + number.length);
            for (int i = 0; i < number.length; i++) {
                System.out.println("general_helpers.dbhelper.addOffer()in for");
                SimInfo info = new SimInfo_Util().getSimInfo_by_id(session, Integer.parseInt(number[i]), "");
                SimOffer_Util offer_Util = new SimOffer_Util();
                SimOffer adt = new SimOffer();
                adt.setSimInfo(info);
                adt.setOfferInfo(offer);
                adt.setUserInfo(userInfo);
                adt.setDateAffect(new Date());
                session.saveOrUpdate(adt);
            }
            System.out.println("general_helpers.dbhelper.addOffer() end ");
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (Exception e) {
            System.out.println("helpers.dbhelper.addOffer() : UNKNOWN ERROR==>" + e.getMessage());
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }

    }

    public int UpadateOffer(int OfferInfo, int userID, int operatorID, String offerDesc, int offerTypeID, String prenumber, String postnumber, String postpincode,
            double transfertValue, double realValue, int isStatic, String[] number) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
                 OfferInfo_Util OfferUtil = new OfferInfo_Util();
           /* if (OfferUtil.getOfferInfo_by_offerDesc(session, offerDesc, "").size() > 0) {
                return staticVars.OfferAlreadyExists;
            }*/
             UserInfo userInfo = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            OfferType offerType = new OfferType_Util().getOfferType_by_id(session, offerTypeID, "");
            Operator operator = new Operator_Util().getOperator_by_id(session, operatorID, "");
            OfferInfo offer = OfferUtil.getOfferInfo_by_id(session,OfferInfo, "");
            offer.setOfferType(offerType);
            offer.setOperator(operator);
            //offer.setUserInfoByIduserInfoUpdate(userInfo);
            offer.setOfferDesc(offerDesc);
            offer.setRealValue(realValue);
            offer.setTransferedValue(transfertValue);                    
            offer.setIsStatic(0);
            offer.setPrenumber(prenumber);
            offer.setPostnumber(postnumber);
            offer.setPostPinCode(postpincode);
            OfferUtil.updateOfferInfo(offer, session);
            //SimOffer_Util offer_Util = new SimOffer_Util();
           // offer_Util.deleteOfferInfo(session, OfferInfo, "");
            System.out.println("general_helpers.dbhelper.addOffer()in for+ ===" + number.length);
            /*for (int i = 0; i < number.length; i++) {
                System.out.println("general_helpers.dbhelper.addOffer()in for");
                SimInfo info = new SimInfo_Util().getSimInfo_by_id(session, Integer.parseInt(number[i]), "");
                SimOffer adt = new SimOffer();
                adt.setSimInfo(info);
                adt.setOfferInfo(offer);
                adt.setUserInfo(userInfo);
                adt.setDateAffect(new Date());
                session.saveOrUpdate(adt);
            }*/
            session.getTransaction().commit();
            session.close();
            return staticVars.onGoingProcessOK;
        } catch (HibernateException e) {
            System.out.println("helpers.dbhelper.addOffer() : UNKNOWN ERROR==>"+e.getMessage() );
            session.getTransaction().rollback();
            session.close();
            return staticVars.unknownError;
        }
    }

}
