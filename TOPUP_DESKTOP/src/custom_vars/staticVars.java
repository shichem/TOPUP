/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_vars;

import custom_package.userUI;
import general_helpers.dbhelper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import model_util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author kaa aziz
 */
public class staticVars {

    public static dbhelper requestBroker = new dbhelper();
    /*
    SESSION PARAMETERS     
     */
    public static Session globalSession = HibernateUtil.getSessionFactory().openSession();;
    public static userUI actualUser = null;
    
    /*
    CALCUL PARAMETERS
     */
    public static double globalSolde=0;
    public static double globalSoldeDjezzy=0;
    public static double globalSoldeMobilis=0;
    public static double globalSoldeOoredoo=0;
    
    /*
    DATABASE PARAMETERS
     */

    public static String userCategory_Administrateur = "Administrateur";
    public static String userCategory_Utilisateur = "Utilisateur";

    public static String simType_Details = "Details";
    public static String simType_Gros = "Gros";
    public static String simType_PaiementFacture = "Paiement de facture";

    public static String offerType_Details = "Details";
    public static String offerType_Gros = "Gros";
    public static String offerType_DetailsBonus = "Details_Bonus";
    public static String offerType_GrosBonus = "Gros_Bonus";
    public static String offerType_PaiementFacture = "Paiement de facture";
    public static String offerType_ActivationPuce = "Activation de puce";
    public static String offerType_Solde = "Solde";

    public static String transactType_TrasfertCredit = "Transfert de credit";
    public static String transactType_AlimentSolde = "Possible Alimentation de solde";
    public static String transactType_DebitSolde = "Possible debit de solde";
    public static String transactType_PaiementFacture = "Paiement de facture";
    public static String transactType_ActivationPuce = "Activation de puce";

    public static String status_PreEntity = "ENT_";
    public static String status_PreTransaction = "TCT_";
    public static String status_ENT_Actif = "ENT_Actif";
    public static String status_ENT_Bloque = "ENT_bloque";
    public static String status_ENT_Inactif = "ENT_Inactif";
    public static String status_TCT_EnInstance = "TCT_En instance";
    public static String status_TCT_Envoye = "TCT_Envoyee";
    public static String status_TCT_Annule = "TCT_Annulee";
    public static String status_TCT_Reussie = "TCT_Reussie";
    public static String status_TCT_Echec = "TCT_Echèc";
    public static String status_TCT_AVerifier = "TCT_a verifier";

    public static String traderType_Application = "Application";
    public static String traderType_PuceTOPUP = "Puce TOPUP";
    
    public static String station_type_PC = "PC";
    public static String station_type_Mobile = "Mobile";

    public static String traderCategory_Grossiste = "Grossiste";
    public static String traderCategory_Detaillant = "Detaillant";

    /*
    DATE FORMAT
     */
    public static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    /*
    ERRORS
     */
    public static int onGoingProcessOK = 1;

    public static int unknownError = 99;
    public static int userPasswordError = 0;
    public static int userNameError = 2;
    public static int userNameAlreadyExists = 3;
    public static int userNolongerActive = 4;

    public static int TraderAlreadyExists = 5;
    public static int TraderNotExists = 6;

    public static int ProductAlreadyExists = 12;
    public static int ProductNotExists = 13;

    public static int LimitExceeded = 14;
    public static int TopUpError = 15;
    public static int insufficientBalance = 16;
    public static int noValidSim = 17;
    
    public static int providerClientLinkAlreadyExists = 18;
    public static int OfferAlreadyExists = 19;
    
}
