/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HITCHI
 */
public class DbResult {

    public static final String PREFFIX_OPERATOR_USSD_SOLD_DJEZZY = "*766*";
    public static final String PREFIX_OPERATOR_USSD_SOLD_MOBILIS = "*632*01*";
    public static final String PREFIX_OPERATOR_USSD_SOLD_OOREDOO = "*570*";

    public static final String SUFFIX_OPERATOR_CODE_DJEZZY = "60302";
    public static final String SUFFIX_OPERATOR_CODE_OOREDOO = "60303";
    public static final String SUFFIX_OPERATOR_CODE_MOBILIS = "60301";

    public static final int PREFIX_PHONE_DJEZZY = 7;
    public static final int PREFIX_PHONE_OOREDOO = 5;
    public static final int PREFIX_PHONE_MOBILIS = 6;

    public static final String DEVISE_DINAR = "Dinar";
    public static final String DEVISE_DA = "DA";
    //codes des types de response de topup
    public static final int TOPUP_CODE_SUCCES = 1;
    public static final int TOPUP_CODE_FAIL_WITH_NO_REASON = 2;
    public static final int TOPUP_CODE_INSUFFICIENT_CREDIT = 3;
    public static final int TOPUP_CODE_UNDER_MIN_AMOUT = 4;
    public static final int TOPUP_CODE_OVER_MAX_AMOUNT = 5;
    //MORE CODE
    public static final int TOPUP_STATUS_SUCCESS = 1;
    public static final int TOPUP_STATUS_FAIL = -1;
    public static final int TOPUP_STATUS_TIMEOUT = 3;
    //MORE CODE
    //MORE CODE

    //TAG des types de response de topup
    public static final String TOPUP_TAG_SUCCES = "TOPUP_TAG_SUCCES";
    public static final String TOPUP_TAG_FAIL_WITH_NO_REASON = "TOPUP_TAG_FAIL_WITH_NO_REASON";
    public static final String TOPUP_TAG_INSUFFICIENT_CREDIT = "TOPUP_TAG_INSUFFICIENT_CREDIT";
    public static final String TOPUP_TAG_UNDER_MIN_AMOUT = "TOPUP_TAG_UNDER_MIN_AMOUT";
    public static final String TOPUP_TAG_OVER_MAX_AMOUNT = "TOPUP_TAG_OVER_MAX_AMOUNT";
    //MORE TAG
    //MORE TAG
    //MORE TAG

//template du sous message de la reponse topup
//List des reponses de l'operation topup pour chaque operateur
    public static Map<Integer, String> TOPUP_RESPONSE_OOREDOO;
    public static Map<Integer, String> TOPUP_RESPONSE_DJEZZY;
    public static Map<Integer, String> TOPUP_RESPONSE_MOBILIS;

    public static final String OPERATOR_DJEZZY = "DJEZZY";
    public static final String OPERATOR_OOREDOO = "OOREDOO";
    public static final String OPERATOR_MOBILIS = "MOBILIS";

    public static final String TAG_TOPUP = "TOPUP";
    public static final String TAG_BALANCE = "BALANCE";
    public static final String TAG_SIM_NUMBER = "SIM_NUMBER";
    public static final String TAG_OPERATOR = "OPERATOR";
    public static final String TAG_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String TAG_TOPUP_PHONE_NUMBER = "TOPUP_PHONE_NUMBER";
    public static final String TAG_TOPUP_AMOUNT = "TOPUP_AMOUNT";
    public static final String TAG_TOPUP_ID_TRANSACTION = "TPOPUP_ID_TRANSACTION";
    public static final String TAG_TOPUP_RESPONSE_CODE = "TOPUP_RESPONSE_CODE";
    public static final String TAG_TOPUP_RESPONSE_TAG = "TOPUP_RESPONSE_TAG";

    public static final String USSD_SOLD = "USSD_SOLD";
    public static final String AT_OPERATOR = "AT_OPERATOR";
    public static final String USSD_TOPUP = "USSD_TOPUP";
    public static final String USSD_PHONE = "USSD_PHONE";
    public static final String USSD = "USSD";
    public static final String USSD_CONFIRMATION = "USSD_CONFIRMATION";
    //
    public static final String USSD_TOPUP_OFFER = "USSD_TOPUP_OFFER";
    //
    public static final String AT_SMS_READ = "AT_SMS_READ";
    public static final String AT_SMS_SEND = "AT_SMS_SEND";
    public static final String AT_SMS_DELETE = "AT_SMS_DELETE";
    public static final String USSD_TOPUP_CONFIRMATION_CODE = "1";

    public static final String SMS_READ_STATUS_ALL = "ALL";
    public static final String SMS_READ_STATUS_REC_UNREAD = "REC UNREAD";
    public static final String SMS_READ_STATUS_REC_READ = "REC READ";

    public static final int SMS_DELETE_STATUS_READ = 1;
    public static final int SMS_DELETE_STATUS_READ_AND_SENT = 2;
    public static final int SMS_DELETE_STATUS_EXCEPT_UNREAD = 3;
    public static final int SMS_DELETE_STATUS_ALL = 4;

    public static List<String> listTopupConfirmationSubString;
    //
    public static List<String> test;
    //
    public static List<topupResponseModel> topupRespnse;
    public static HashMap<Integer, String> topupRespnseTagByCode;
    public static HashMap<Integer, String> operatorByPhonePrefix;
    
    public static HashMap<Integer, String> moreOffersCodes = new HashMap<>();
    public static List<String> moreOffersContents;

    public static void initListTopupConfirmationSubStringFromDB() {
        listTopupConfirmationSubString = new ArrayList<String>();
        listTopupConfirmationSubString.add("voulez vous transfer".toUpperCase());
        listTopupConfirmationSubString.add("vous allez transfe".toUpperCase());
        listTopupConfirmationSubString.add("etes vous sure de vouloir trans".toUpperCase());
        listTopupConfirmationSubString.add("VOUS VOULEZ TRANSFERER".toUpperCase());
        listTopupConfirmationSubString.add("Vous voulez transf".toUpperCase());
        // multi level confirmation
        listTopupConfirmationSubString.add("message de confirmation level 1".toUpperCase());
        listTopupConfirmationSubString.add("message de confirmation level 2".toUpperCase());
        listTopupConfirmationSubString.add("message de confirmation level 3".toUpperCase());
        listTopupConfirmationSubString.add("message de confirmation level 4".toUpperCase());
        listTopupConfirmationSubString.add("message de confirmation level 5".toUpperCase());
    }
    public static void initListTopupContents() {
        moreOffersContents = new ArrayList<String>();
        moreOffersContents.add("MORE".toUpperCase());
        moreOffersContents.add("Offers".toUpperCase());
        moreOffersContents.add("Offer".toUpperCase());
    }
    
    public static void test(){
        test = new ArrayList<String>();
            // multi level confirmation
        test.add("message de confirmation level 1 sqdqsd".toUpperCase());
                test.add("credit transmis".toUpperCase());
        test.add("message de confirmation level 2 qsd qddsqq".toUpperCase());
        test.add("dqsd  fdfmessage de confirmation level 3".toUpperCase());
        test.add(" qsdddz message de confirmation level 4 zjjzzjzj".toUpperCase());
        test.add("sss qsd   aze message de confirmation level 5".toUpperCase());
    }

    public static void initOperatorByPhonePrefix() {
        //must be initialized from database
        operatorByPhonePrefix.put(PREFIX_PHONE_DJEZZY, OPERATOR_DJEZZY);
        operatorByPhonePrefix.put(PREFIX_PHONE_OOREDOO, OPERATOR_OOREDOO);
        operatorByPhonePrefix.put(PREFIX_PHONE_MOBILIS, OPERATOR_MOBILIS);
    }
  
    public static String getOperatorByPhonePrefix(String phone) {
        String formatedPhone;
        phone = phone.replaceAll(" ", "");
        if (phone.startsWith("+")) {
            phone = phone.replaceFirst("+", "");
        }
        if (phone.startsWith("213")) {
            phone = phone.replaceFirst("213", "");
        }
        if (phone.startsWith("0")) {
            phone = phone.replaceFirst("0", "");
        }

        switch (Integer.valueOf(phone.charAt(0))) {
            case PREFIX_PHONE_DJEZZY:
                return operatorByPhonePrefix.get(PREFIX_PHONE_DJEZZY).toString();
            case PREFIX_PHONE_OOREDOO:
                return operatorByPhonePrefix.get(PREFIX_PHONE_OOREDOO).toString();
            case PREFIX_PHONE_MOBILIS:
                return operatorByPhonePrefix.get(PREFIX_PHONE_MOBILIS).toString();
            default:
                return null;
        }
    }

    public static void getTopupRespnses() {
        topupRespnse = new ArrayList();

        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_OVER_MAX_AMOUNT, "echec de transfert: montant superieur a", TOPUP_TAG_OVER_MAX_AMOUNT));
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_OVER_MAX_AMOUNT, "incorrect\nEntez la somme", TOPUP_TAG_OVER_MAX_AMOUNT));

        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_SUCCES, "credit transmis", TOPUP_TAG_SUCCES));
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_SUCCES, "credit transmis", TOPUP_TAG_SUCCES)); //Djezzy
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_SUCCES, "Recharge de l'abonne", TOPUP_TAG_SUCCES)); //Mobilis
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_SUCCES, "TRANSFERE DE VOTRE COMPTE", TOPUP_TAG_SUCCES)); //Ooredoo
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_SUCCES, "Success", TOPUP_TAG_SUCCES)); //Other
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_SUCCES, "OK num", TOPUP_TAG_SUCCES)); //Other

        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_INSUFFICIENT_CREDIT, "montant superieur a", TOPUP_TAG_INSUFFICIENT_CREDIT));
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_INSUFFICIENT_CREDIT, "Valeur minimale autorisee du stock atteinte", TOPUP_TAG_INSUFFICIENT_CREDIT));
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_INSUFFICIENT_CREDIT, "Le montant Areselli demande est insuffisant", TOPUP_TAG_INSUFFICIENT_CREDIT));

        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_UNDER_MIN_AMOUT, "VOUS NE POUVEZ PAS RECHARGER LE COMPTE DE VOTRE CORRESPONDANT MONTANT NON AUTORISE", TOPUP_TAG_UNDER_MIN_AMOUT));
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_UNDER_MIN_AMOUT, "VOUS NE POUVEZ PAS RECHARGER LE COMPTE DE VOTRE CORRESPONDANT CREDIT INSUFFISANT OU MONTANT NON AUTORISE", TOPUP_TAG_UNDER_MIN_AMOUT));
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_UNDER_MIN_AMOUT, "CREDIT INSUFFISANT", TOPUP_TAG_UNDER_MIN_AMOUT));
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_UNDER_MIN_AMOUT, "MONTANT NON AUTORISE", TOPUP_TAG_UNDER_MIN_AMOUT));
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_UNDER_MIN_AMOUT, "echec de transfert: montant inferieur a", TOPUP_TAG_UNDER_MIN_AMOUT));
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_UNDER_MIN_AMOUT, "incorrect\nEntez la somme", TOPUP_TAG_UNDER_MIN_AMOUT));
        topupRespnse.add(new topupResponseModel(TOPUP_STATUS_FAIL, TOPUP_CODE_UNDER_MIN_AMOUT, "Desole, le montant est errone", TOPUP_TAG_UNDER_MIN_AMOUT));

    }

    public static String getSoldUSSDCodeFromDb(String operatorName, String pinCode) {
        //Select BalenceOperationCodeFromDb
        switch (operatorName) {
            case OPERATOR_DJEZZY:
                //pinCode="1090";
                return PREFFIX_OPERATOR_USSD_SOLD_DJEZZY + pinCode + "#";
            case OPERATOR_MOBILIS:
                //pinCode="19942";
                //return PREFIX_OPERATOR_USSD_SOLD_MOBILIS+"19942"+"#";    
                return PREFIX_OPERATOR_USSD_SOLD_MOBILIS + pinCode + "#";
            case OPERATOR_OOREDOO:
                //pinCode="4655";
                //return PREFIX_OPERATOR_USSD_SOLD_OOREDOO+"4655#";
                return PREFIX_OPERATOR_USSD_SOLD_OOREDOO + pinCode + "#";
        }
        return null;
    }

    public static String getPinUSSDCodeFromDb(String operatorName) {
        //Select BalenceOperationCodeFromDb
        switch (operatorName) {
            case OPERATOR_DJEZZY:
                return "19942";
            case OPERATOR_MOBILIS:
                return "19942";
            case OPERATOR_OOREDOO:
                return "4655";
        }
        return null;
    }

    public static String getPhoneUSSDCodeFromDb(String operatorName) {
        //Select BalenceOperationCodeFromDb
        switch (operatorName) {
            case OPERATOR_DJEZZY:
                return "*99#";
            case OPERATOR_MOBILIS:
                return "*101#";
            case OPERATOR_OOREDOO:
                return "*113*1*1*1#";
        }
        return null;
    }

    public static String getTopupUSSDCodeFromDb(String operatorName) {
        //Select BalenceOperationCodeFromDb
        switch (operatorName) {
            case OPERATOR_DJEZZY:
                return "760";
            case OPERATOR_MOBILIS:
                return "630";
            case OPERATOR_OOREDOO:
                return "580";
        }
        return null;
    }

}
