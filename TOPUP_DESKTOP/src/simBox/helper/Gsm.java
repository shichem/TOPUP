/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simBox.helper;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import custom_package.simUI;
import helper.DbResult;
import helper.Utils;
import helper.topupOfferModel;
import helper.topupResponseModel;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model_db.SimInfo;
import model_helpers.SimInfo_Util;
import org.hibernate.Session;

/**
 *
 * @author HITCHI
 */
public class Gsm {

    private SerialPort serialPort;
    private Logger logger;

    private InputStream in;
    private OutputStream out;
    private Sim sim;

    private final Pattern PHONE_PATTERN = Pattern.compile("([0,+]?)(\\d\\s?){9,15}");
    private final Pattern MONEY_PATTERN = Pattern.compile("(\\d\\s?)+(\\.(\\d\\s?)+)?(DZD|DA|DINAR|DINARS)");

    private final String TIMEOUT = "timeout";
    private final String OOREDOO_PHONE_FROM_SMS_SUBSTRING = "Ooredoo a le plaisir de vous informer que votre num";
    private final String BONUS_KEY_WORD = "bonus"; // can be other expression according to operator "must be defined with sim params"
    private final String SOLD_KEY_WORD = "sold"; // can be also "crédit" OR "balance" according to operator "must be defined with sim params"
    public final String AT_OPERATOR_CODE = "AT+COPS?";
    static char CTRLZ = '\032';
    static char ENTER = 12;
    private final boolean FIRST_CALL = true;
    public int indice = 0;
    public ArrayList<topupOfferModel> topupOffreModel;
    final String OFFER_SPLITTER = "\\d(\\d?)\\s?\\:.+";
    //important
    // a sequence of topup validation message
    public static String topupMessage;

    public Gsm(SerialPort serialPort) {
        this.serialPort = serialPort;
        sim = new Sim();
        this.out = serialPort.getOutputStream();
        this.in = serialPort.getInputStream();
    }

    public Gsm(SerialPort serialPort, Sim OldSim) {
        this.serialPort = serialPort;
        sim = OldSim;
        this.out = serialPort.getOutputStream();
        this.in = serialPort.getInputStream();
    }

    /* public Sim initModem() {
        DbResult.initListTopupConfirmationSubStringFromDB();
        try {
            executeGetOperatorNameOperation();
            /*if(executeSmsDeleteOperation(DbResult.SMS_DELETE_STATUS_ALL).contains("OK")){
                    if(executeSmsSendOperation("333", "NUM").contains("OK")){
                        Thread.sleep(10000);
                        executeSmsReadOperation(DbResult.SMS_READ_STATUS_REC_UNREAD);             
                    }
             }*/
 /*      executeGetPhoneNumberOperation(DbResult.getPhoneUSSDCodeFromDb(sim.getNameOperator()));
            executeGetSoldOperation(DbResult.getSoldUSSDCodeFromDb(sim.getNameOperator(), sim.getPin()));
        } catch (InterruptedException ex) {
            Logger.getLogger(Gsm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sim;
    }*/
    public simUI initModem(Session session) {
        DbResult.initListTopupConfirmationSubStringFromDB();
        DbResult.test();
        topupOffreModel = new ArrayList();
        simUI simui = null;
        try {
            Thread.sleep(100);
            executeGetOperatorNameOperation();
            /*executeTopupOperation(
                     DbResult.getTopupUSSDCodeFromDb(sim.getNameOperator()),
                     "0795956547",
                     "80",
                     DbResult.getPinUSSDCodeFromDb(sim.getNameOperator()));*/

            if (sim.getNameOperator().toUpperCase().equals(DbResult.OPERATOR_OOREDOO)) {
                executeSmsDeleteOperation(0); // deleate all sms befor sending
                if (executeSmsSendOperation("333", "NUM").contains("OK")) {  // send sms
                    Thread.sleep(10000); // waiting to recive sms
                    sim.setPhone(
                            getPhoneNumberFromSmsList( // get phonenumber from sms List
                                    executeSmsReadOperation(DbResult.SMS_READ_STATUS_ALL),
                                    OOREDOO_PHONE_FROM_SMS_SUBSTRING).trim()); // get unread sms list. Probably one sms willbe found
                    Utils.println("Phone from SMS : " + sim.getPhone());
                }
            } else { //if phone number must be picked from ussd
                executeGetPhoneNumberOperation(DbResult.getPhoneUSSDCodeFromDb(sim.getNameOperator()));
            }

            System.out.println("GSM: " + sim.getNameOperator());
            System.out.println("GSM: " + sim.getPhone());
            System.out.println("GSM: " + sim.getPortCom());
            List simList;
            if (!sim.getNameOperator().equals("")) {
                if (!sim.getPhone().equals("")) {

                    String phoneSearch = sim.getPhone();
                    if (phoneSearch.startsWith("213")) {
                        phoneSearch = phoneSearch.substring(phoneSearch.length() - 9, phoneSearch.length());
                    }
                    simList = new SimInfo_Util().getSimInfo_by_operatorname_simnumberLike(session, sim.getNameOperator(),
                            phoneSearch, "");
                } else {

                    simList = new SimInfo_Util().getSimInfo_by_operatorname_portname(session, sim.getNameOperator(), sim.getPortCom(), "");
                    /*if (simList.isEmpty()) {
                        System.out.println("****************************** WARNING: LOOK BY OPERATOR ONLY ********************************");
                        simList = new SimInfo_Util().getSimInfo_by_operatorname(session, sim.getNameOperator(), "");
                    }*/
                }
                if (simList.isEmpty()) {
                    return null;
                }
                SimInfo simInfo = (SimInfo) simList.get(0);
                sim.setPin(simInfo.getSimPinCode());

                //executeGetSoldOperation(DbResult.getSoldUSSDCodeFromDb(sim.getNameOperator(), sim.getPin()));
                executeGetSoldOperation(DbResult.getSoldUSSDCodeFromDb(sim.getNameOperator(), sim.getPin()), SOLD_KEY_WORD, BONUS_KEY_WORD);
                String Phone = sim.getPhone();
                if (Phone.equals("")) {
                    Phone = simInfo.getSimnumber();
                }
                simui = new simUI(Phone, sim.getNameOperator(), sim.getPortCom(), sim.getSold(), sim.getBonus());
                simui.setPinCode(sim.getPin());
                simui.setSimInfo(simInfo);
                simui.setActualSolde(sim.getSold());
                simui.setActualBonus(sim.getBonus());
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Gsm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return simui;
    }

    public String executeAT(String at, SerialReader sr) {
        at = at + "\r\n";
        serialPort.writeBytes((at).getBytes(), at.getBytes().length);
        int attempts = 0;
        while (sr.recivedMessages.equals("") && attempts != 50) {
            try {
                attempts++;
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (attempts == 50) {
            return TIMEOUT;
        } else {
            attempts = 0;
            if (sr.recivedMessages.contains("ERROR")) {
                return sr.recivedMessages;
            }
            while (!sr.lastReceivedData.endsWith("\r\n") && attempts != 50) { //the received message is incomplete
                try {
                    attempts++;
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return sr.recivedMessages;
        }
    }

    public String cancelUSSD(SerialReader sr) {
        String cmd = "at+cusd=2\r";
        serialPort.writeBytes((cmd).getBytes(), cmd.getBytes().length);
        int attempts = 0;
        while (!sr.recivedMessages.contains("ERROR") && sr.recivedMessages.equals("") && attempts != 100) { // no data received yet 
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (attempts == 100) {
            return TIMEOUT;
        } else {
            attempts = 0;
            if (sr.recivedMessages.contains("ERROR")) {
                return sr.recivedMessages;
            }
            while (!sr.lastReceivedData.contains("\",15") && attempts != 100) { //the received message is incomplete
                try {
                    attempts++;
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return sr.recivedMessages;
        }
    }

    /* public String executeUSSD(String ussd, SerialReader sr) {
        String cmd = "at+cusd=1,\"" + ussd + "\",15\r\n";
        serialPort.writeBytes((cmd).getBytes(), cmd.getBytes().length);
        int attempts = 0;
        while (sr.recivedMessages.equals("") && attempts != 100) { // no data received yet 
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        attempts = 0;
        if (sr.recivedMessages.contains("ERROR")) {
            return sr.recivedMessages;
        }
        while (sr.lastReceivedData.contains("OK") && attempts != 100) { //the received message is incomplete
            try {
                attempts++;
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sr.recivedMessages;
    }*/
    public String executeUSSD(String ussd, SerialReader sr) {
        String cmd = "at+cusd=1,\"" + ussd + "\",15\r\n";
        serialPort.writeBytes((cmd).getBytes(), cmd.getBytes().length);
        int attempts = 0;
        while (!sr.recivedMessages.contains("ERROR") && sr.recivedMessages.equals("") && attempts != 100) { // no data received yet 
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (attempts == 100) {
            return TIMEOUT;
        } else {
            attempts = 0;
            if (sr.recivedMessages.contains("ERROR")) {
                return sr.recivedMessages;
            }
            while (!sr.lastReceivedData.contains("\",15") && attempts != 100) { //the received message is incomplete
                try {
                    attempts++;
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return sr.recivedMessages;
        }
    }

    public void executeGetOperatorNameOperation() {
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.AT_OPERATOR);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            //System.out.println("Adding listener");
            try {
                executeAT(AT_OPERATOR_CODE, sr);
                sr.handleOperatorResponse(sr.recivedMessages);
            } catch (Exception e) {
                e.printStackTrace();
            }
            removeDataListener();
            closePort();
        }

    }

    /*public void executeGetSoldOperation(String code) {
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.USSD_SOLD);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            //System.out.println("Adding listener");
            try {
                executeUSSD(code, sr);
                sr.handleSoldResponse(sr.recivedMessages);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println("Removing listener");
            removeDataListener();
            closePort();
        }
    }*/
    public void executeGetSoldOperation(String code, String soldKeyWord, String bonusKeyWord) {
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.USSD_SOLD);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            //System.out.println("Adding listener");
            try {
                executeUSSD(code, sr);
                sr.handleSoldResponse(sr.recivedMessages, soldKeyWord);
                sr.handleBonusResponse(sr.recivedMessages, bonusKeyWord);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println("Removing listener");
            removeDataListener();
            closePort();
        }
    }

    private void executeGetPhoneNumberOperation(String code) throws InterruptedException {
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.USSD_PHONE);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                executeUSSD(code, sr);
                sr.handlePhoneResponse(sr.recivedMessages);
            } catch (Exception e) {
                e.printStackTrace();
            }
            removeDataListener();
            closePort();
        }

    }

    /* public topupResponseModel executeTopupOperation(String code, String phone, String amount, String pin) {
        topupResponseModel trm = null;
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, phone, amount, DbResult.USSD_TOPUP + "_" + phone);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                //to listen to each topup operation must add a suffix client phone
                executeUSSD("*" + code + "*" + phone + "*" + amount + "*" + pin + "#", sr);
                //executeUSSD(simboxtransact.sentMessage(), sr);

            } catch (Exception e) {
                e.printStackTrace();
            }

            trm = sr.handleTopupResponse(sr.recivedMessages, DbResult.topupRespnse, sr.commandToListenFor);
            if (sr.ussdShouldBeConfirmed) {
                removeDataListener();
                closePort();
                try {
                    trm = executeGetTopupConfirmationOperation(DbResult.USSD_TOPUP_CONFIRMATION_CODE, sr.operationPhoneNumber);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Gsm.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                removeDataListener();
                closePort();
            }
        }
        return trm;
    }*/
    public topupResponseModel executeTopupOperation(String sentMessage) {
        topupResponseModel trm = null;
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.USSD_TOPUP);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                //to listen to each topup operation must add a suffix client phone

                executeUSSD(sentMessage, sr);

            } catch (Exception e) {
                e.printStackTrace();
            }
            DbResult.getTopupRespnses();
            trm = sr.handleTopupResponse(sr.recivedMessages, DbResult.topupRespnse, sr.commandToListenFor, 0);
            if (sr.ussdShouldBeConfirmed) {
                removeDataListener();
                closePort();
                try {
                    trm = executeGetTopupConfirmationOperation(
                            sr,
                            DbResult.USSD_TOPUP_CONFIRMATION_CODE, //use "4" for for more level message test
                            sr.operationPhoneNumber,
                            FIRST_CALL);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Gsm.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                removeDataListener();
                closePort();
            }
        }
        return trm;
    }

    public void executeTopupOperation(String code, String phone, String amount, String pin) {
        topupResponseModel trm = null;
        topupMessage = "";
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, phone, amount, DbResult.USSD_TOPUP + "_" + phone);
        String ussdResponse = "";
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                //to listen to each topup operation must add a suffix client phone
                ussdResponse = executeUSSD("*" + code + "*" + phone + "*" + amount + "*" + pin + "#", sr);
                //ussdResponse = DbResult.test.get(0); 
                //Thread.sleep(2000);

            } catch (Exception e) {
                e.printStackTrace();
            }

            trm = sr.handleTopupResponse(ussdResponse, DbResult.topupRespnse, sr.commandToListenFor);
            if (sr.ussdShouldBeConfirmed) {
                sr.ussdShouldBeConfirmed = false;
                removeDataListener();
                closePort();
                try {
                    trm = executeGetTopupConfirmationOperation(
                            sr,
                            DbResult.USSD_TOPUP_CONFIRMATION_CODE, //use "4" for for more level message test
                            sr.operationPhoneNumber,
                            FIRST_CALL);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Gsm.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                removeDataListener();
                closePort();
            }
        }

    }

    /* public topupResponseModel executeTopupOperation(String sentMessage) {
        topupResponseModel trm = null;
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.USSD_TOPUP);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                //to listen to each topup operation must add a suffix client phone

                executeUSSD(sentMessage, sr);

            } catch (Exception e) {
                e.printStackTrace();
            }
            DbResult.getTopupRespnses();
            trm = sr.handleTopupResponse(sr.recivedMessages, DbResult.topupRespnse, sr.commandToListenFor);
            if (sr.ussdShouldBeConfirmed) {
                removeDataListener();
                closePort();
                try {
                    trm = executeGetTopupConfirmationOperation(DbResult.USSD_TOPUP_CONFIRMATION_CODE, sr.operationPhoneNumber);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Gsm.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                removeDataListener();
                closePort();
            }
        }
        return trm;
    }*/

 /*private topupResponseModel executeGetTopupConfirmationOperation(String code, String phone, int b) throws InterruptedException {
        topupResponseModel trm = null;
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.USSD_CONFIRMATION + "_" + phone);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                executeUSSD(code, sr);
                trm = sr.handleTopupConfirmationResponse(sr.recivedMessages, DbResult.topupRespnse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            removeDataListener();
            closePort();
        }
        return trm;
    }*/
    private topupResponseModel executeGetTopupConfirmationOperation(Gsm.SerialReader sr, String code, String phone, boolean firstCall) throws InterruptedException {
        String ussdResponse = "";
        topupResponseModel trm = null;
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        sr = new Gsm.SerialReader(serialPort, DbResult.USSD_CONFIRMATION + "_" + phone);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);

            try {
                ussdResponse = executeUSSD(code, sr);
                topupMessage += ussdResponse;
                //ussdResponse = DbResult.test.get(indice++);
                trm = sr.handleTopupConfirmationResponse(ussdResponse, DbResult.topupRespnse, sr.commandToListenFor);
                if (sr.ussdShouldBeConfirmed) {
                    sr.ussdShouldBeConfirmed = false;
                    removeDataListener();
                    closePort();
                    executeGetTopupConfirmationOperation(
                            sr,
                            code, //use "1" to stopping expendable message test
                            phone,
                            !FIRST_CALL);
                } else {
                    cancelUSSD(sr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                removeDataListener();
                closePort();
            }
            removeDataListener();
            closePort();
        }
        return trm;
    }

    /* public ArrayList<Sms> executeSmsReadOperation(String status) {
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.AT_SMS_READ);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            if (executeAT("AT+CMGF=?", sr).contains("ERROR")) {
                removeDataListener();
                closePort();
                return null;
            } else {
                try {
                    executeAT("AT+CMGL=\"" + status + "\"", sr);
                    //serialPort.writeBytes((cmd).getBytes(), cmd.getBytes().length);
                    ArrayList<Sms> str = sr.handleSmsRead(sr);
                    removeDataListener();
                    closePort();
                    return str;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }*/
    public ArrayList<Sms> executeSmsReadOperation(String status) {
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.AT_SMS_READ);
        ArrayList<Sms> smsList = new ArrayList();
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                executeAT("AT+CMGL=\"" + status + "\"", sr);
                smsList = sr.handleSmsRead(sr.recivedMessages);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        removeDataListener();
        closePort();
        return smsList;
    }


    /* public String executeSmsSendOperation(String num, String sms) {
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.AT_SMS_SEND);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            if (executeAT("AT+CMGF=?", sr).contains("ERROR")) {
                removeDataListener();
                closePort();
                return null;
            } else {
                executeAT("AT+CMGS=\"" + num + "\"", sr);
                executeAT(sms, sr);
                executeAT(Character.toString((char) 26), sr);
                System.out.println(sr.recivedMessages);
            }
            removeDataListener();
            closePort();
            return sr.recivedMessages;
        }
        return "";
    }*/
    public String executeSmsSendOperation(String phoneNumber, String sms) {
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.AT_SMS_SEND);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                executeAT("AT+CMGF=1", sr);
                Thread.sleep(1000);
                executeAT("AT+CMGS=\"" + phoneNumber + "\"", sr);
                Thread.sleep(2000);
                executeAT(sms + CTRLZ, sr);
                removeDataListener();
                closePort();
                System.out.println("SEND SMS" + sr.recivedMessages);
                return sr.recivedMessages;
            } catch (Exception e) {
                System.out.println("CATCH" + sr.recivedMessages);
                e.printStackTrace();
                return "";
            }
        }
        removeDataListener();
        closePort();
        System.out.println("END" + sr.recivedMessages);
        return "";
    }

    /* public String executeSmsDeleteOperation(int flag) {
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.AT_SMS_DELETE);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            if (executeAT("AT+CMGF=?", sr).contains("ERROR")) {
                removeDataListener();
                closePort();
                return null;
            } else {
                //executeAT("AT+CMGD=1," + flag , sr);
                System.out.println(sr.recivedMessages);
                removeDataListener();
                closePort();
                return sr.recivedMessages;
            }
        }
        return "";
    }*/
    public String executeSmsDeleteOperation(int flag) {
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.AT_SMS_DELETE);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                executeAT("AT+CMGF=1", sr);
                executeAT("AT+CMGD=1,4", sr);
                removeDataListener();
                closePort();
            } catch (Exception e) {
                removeDataListener();
                closePort();
            }
            return sr.recivedMessages;
        }
        return "";
    }

    /* public boolean initialize(String port) {
        logger = Logger.getLogger("SP1");
        logger.info("Application start");
        serialPort = SerialPort.getCommPort(port);
        if (serialPort.openPort()) {
            logger.info("Port \"" + serialPort.getSystemPortName() + "\" was opened");
            serialPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    logger.info("receiving data");
                    byte[] msg = new byte[serialPort.bytesAvailable()];
                    serialPort.readBytes(msg, msg.length);
                    String res = new String(msg);
//                    System.out.println(res);
                    result = res;
                }
            });
            return true;
        } else

        {
            logger.warning("Failed to open port \"" + serialPort.getSystemPortName() + "\", application stopped.");
            return false;
        }

    }*/
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<topupOfferModel> executeGetTopupOffer(String code, String showMoreCode, String showMoreString, String regExSpliter, boolean firstCall) {
        String ussdResponse = "";
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.USSD_TOPUP_OFFER);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                if (firstCall) {
                    topupOffreModel = new ArrayList();
                }
                ussdResponse = executeUSSD(firstCall ? code : showMoreCode, sr);
                Utils.print("TOPUP OFFERS : " + ussdResponse);
                //topupOffreModel.addAll(getTopupOfferFromReceivedMessage(ussdResponse, regExSpliter));
                topupOffreModel.addAll(getTopupOfferFromReceivedMessage(ussdResponse));
                //ussdResponse = DbResult.test.get(indice++);
                //sr.handleTopupOffer(ussdResponse, DbResult.topupRespnse, sr.commandToListenFor);
                if (sr.canShowMoreOffer(ussdResponse, showMoreString)) {
                    removeDataListener();
                    closePort();
                    executeGetTopupOffer(code, showMoreCode, showMoreString, regExSpliter, !FIRST_CALL);
                } else {
                    cancelUSSD(sr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                removeDataListener();
                closePort();
            }
            removeDataListener();
            closePort();
        }
        return topupOffreModel;
    }

    public ArrayList<topupOfferModel> executeGetTopupOffers(String code, boolean firstCall) {
        String ussdResponse = "";
        serialPort.setComPortTimeouts(serialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        Gsm.SerialReader sr = new Gsm.SerialReader(serialPort, DbResult.USSD_TOPUP_OFFER);
        if (openPort() && serialPort.getCTS()) {
            addDataListener(sr);
            try {
                if (firstCall) {
                    topupOffreModel = new ArrayList();
                    DbResult.moreOffersCodes = new HashMap<>();
                    DbResult.initListTopupContents();
                }
                ussdResponse = executeUSSD(code, sr);
                Utils.print("TOPUP OFFERS : " + ussdResponse);
                //topupOffreModel.addAll(getTopupOfferFromReceivedMessage(ussdResponse, regExSpliter));
                ArrayList<topupOfferModel> toms = getTopupOfferFromReceivedMessage(code, ussdResponse);
                for (topupOfferModel tom : toms) {
                    if (DbResult.moreOffersContents.contains(tom.getContent().toUpperCase())) {
                        int star = code.lastIndexOf("*");
                        code = code.substring(0, star + 1) + tom.getActivationCode() + "*" + code.substring(star);
                        Utils.print("new USSD : " + code);
                        toms.remove(tom);
                        executeGetTopupOffers(code, !FIRST_CALL);
                    }
                }
                cancelUSSD(sr);

                topupOffreModel.addAll(toms);
            } catch (Exception e) {
                e.printStackTrace();
                removeDataListener();
                closePort();
            }
            removeDataListener();
            closePort();
        }
        return topupOffreModel;
    }

    public ArrayList<topupOfferModel> getTopupOfferFromReceivedMessage(String message, String regExSpliter) {
        Pattern pattern = Pattern.compile(regExSpliter);
        Matcher matcher = pattern.matcher(message);
        ArrayList<topupOfferModel> toms = new ArrayList();
        topupOfferModel tom;
        String[] splitOffer;
        while (matcher.find()) {
            tom = new topupOfferModel();
            splitOffer = matcher.group().split(":");
            if (splitOffer.length == 2) {
                tom.setActivationCode(splitOffer[0].trim());
                tom.setContent(splitOffer[1]);
            } else {
                tom.setContent(matcher.group());
            }
            //Utils.println("*" + tom.getActivationCode() + " - "+ tom.getContent());
            toms.add(tom);
        }
        return toms;
    }

    public ArrayList<topupOfferModel> getTopupOfferFromReceivedMessage(String message) {
        int CUSD = message.indexOf("CUSD");
        if (CUSD == -1) {
            CUSD = 0;
        }
        try {
            message = message.substring(message.indexOf(",", CUSD) + 1, message.lastIndexOf(","));
        } catch (Exception ere) {
            try {
                message = message.substring(message.indexOf(",", CUSD) + 1);
            } catch (Exception ere1) {
            }
        }
        StringTokenizer st = new StringTokenizer(message, "\n");
        StringTokenizer stElement;
        ArrayList<topupOfferModel> toms = new ArrayList();
        topupOfferModel tom;
        String code = "", content = "";
        while (st.hasMoreTokens()) {
            stElement = new StringTokenizer(st.nextToken(), ":");
            // use tockenizer to divide offers from their codes
            if (stElement.countTokens() > 1) {
                code = stElement.nextToken();
                content = stElement.nextToken();
                tom = new topupOfferModel();
                tom.setActivationCode(code);
                tom.setContent(content);
                toms.add(tom);
            }
        }
        return toms;
    }

    private String getPhoneNumberFromSmsList(ArrayList<Sms> smsList, String smsPhoneNumberSubString /*ex: "votre numéro de tel est" */) {
        boolean found = false;
        int index = 0;
        String smsContent = "";
        while (found == false && index < smsList.size()) {
            smsContent = smsList.get(index).getContent();
            Matcher matcher = PHONE_PATTERN.matcher(smsContent);
            if (smsContent.contains(smsPhoneNumberSubString) && matcher.find()) {
                found = true;
                return matcher.group(0);
            }
            index++;
        }
        return "";
    }

    private String getBonusFromSmsList(ArrayList<Sms> smsList, String smsBonusSubString /*ex: "votre bonus est" */) {
        boolean found = false;
        int index = 0;
        String smsContent = "";
        while (found == false && index < smsList.size()) {
            smsContent = smsList.get(index).getContent();
            Matcher matcher = MONEY_PATTERN.matcher(smsContent);
            if (smsContent.toUpperCase().contains(smsBonusSubString.toUpperCase()) && matcher.find()) {
                found = true;
                return matcher.group(0);
            }
            index++;
        }
        return "";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String[] getSystemPorts() {
        String[] systemPorts = new String[SerialPort.getCommPorts().length];
        for (int i = 0; i < systemPorts.length; i++) {
            systemPorts[i] = SerialPort.getCommPorts()[i].getSystemPortName();
        }
        return systemPorts;
    }

    public boolean openPort() {
        serialPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
        return serialPort.openPort();
    }

    public boolean closePort() {
        return serialPort.closePort();
    }

    public boolean addDataListener(Gsm.SerialReader sr) {
        return serialPort.addDataListener(sr);
    }

    public void removeDataListener() {
        serialPort.removeDataListener();
    }

    public class SerialReader implements SerialPortDataListener {

        public String recivedMessages = "";
        public String lastReceivedData = "";
        public boolean ussdShouldBeConfirmed = false;
        public boolean canShowMoreOffer = false;
        public String operationPhoneNumber = "";
        private final Pattern RESPONSE_PATTERN = Pattern.compile("\\+....:(.*)");
        private final Pattern USSD_PATTERN = Pattern.compile("\\+....:\\s+(\\d)(?:,\\s*\"([^\"]*))?(?:\",\\s*(\\d+)\\s*)?\"?\r?$");
        private final Pattern MONEY_PATTERN = Pattern.compile("(\\d\\s?)+(\\.(\\d\\s?)+)?(DZD|DA|DINAR|DINARS)");
        public final Pattern PHONE_PATTERN = Pattern.compile("([0,+]?)(\\d\\s?){9,15}");
        private final Pattern COMMAND_TYPE_PATTERN = Pattern.compile("^\\+....:");
        private final Pattern OPERATOR_PATTERN = Pattern.compile("\\+COPS:(.*)");
        private final Pattern USSD_TOPUP_PATTERN = Pattern.compile("\\+....:\\s+(\\d)(?:,\\s*\"([^\"]*))?(?:\",\\s*(\\d+)\\s*)?\"?\r?$");
        private final Pattern USSD_TOPUP_RESPONSE_PATTERN = Pattern.compile("\\+....:\\s+(\\d)(?:,\\s*\"([^\"]*))?(?:\",\\s*(\\d+)\\s*)?\"?\r?$");
        private final Pattern USSD_PHONE_PATTERN = Pattern.compile("\\+....:\\s+(\\d)(?:,\\s*\"([^\"]*))?(?:\",\\s*(\\d+)\\s*)?\"?\r?$");
        private final Pattern USSD_TOPUP_ID_TRANSACTION_PATTERN = Pattern.compile("Numero de l'operation \\d+([.,]\\d+)?");//must be reviewed
        private final Pattern NUMBER = Pattern.compile("\\d+([.,]\\d+)?");

        private InputStream is;
        private String commandToListenFor = "";
        private StringBuilder stringBuilder;
        private String Phone = "";
        private String Montant = "";
        private int Response = -1;

        // Begin Hichem
        private String getPhoneNumberFromTopupResponse(String responseLine) {
            Matcher matcher = PHONE_PATTERN.matcher(responseLine);
            if (matcher.find()) {
                return matcher.group(1);
            }
            return null;
        }

        private Double getAmountFromTopupResponse(String responseLine) {
            return 0.0;
        }

        private Integer getIdTransactionFromTopupResponse(String responseLine) {
            Matcher idTransactionMatcher = USSD_TOPUP_ID_TRANSACTION_PATTERN.matcher(responseLine);
            if (idTransactionMatcher.find()) {
                Matcher numberMatcher = NUMBER.matcher(idTransactionMatcher.group(0));
                if (numberMatcher.find()) {
                    return Integer.valueOf(numberMatcher.group(0));
                }
            }
            return null;
        }

        private topupResponseModel getTopupResponseModel(String responseLine, List<topupResponseModel> topupResponsesModel) {
            int i = -1;
            for (topupResponseModel trm : topupResponsesModel) {

                if (responseLine.contains(trm.getResponseSubMessage())) {
                    i = 0;
                    trm.setResponseMessage(responseLine);
                    if (trm.getResponseTag().equals(DbResult.TOPUP_TAG_SUCCES)) // trm.setStatus(status succes);
                    {
                        trm.setResponseStatus(1);
                    } else {
                        trm.setResponseStatus(-1);
                    }
                    //trm.setStatus(status echec);

                    return trm;
                }
            }
            topupResponseModel trm = new topupResponseModel(responseLine);
            trm.setResponseStatus(2);
            return trm;
        }

        private void handleOperatorResponse(String responseLine) {
            Utils.println("Operator Response: " + responseLine);
            if (responseLine.trim().contains(DbResult.SUFFIX_OPERATOR_CODE_MOBILIS)) {
                sim.setNameOperator(DbResult.OPERATOR_MOBILIS);
            } else if (responseLine.trim().contains(DbResult.SUFFIX_OPERATOR_CODE_DJEZZY)) {
                sim.setNameOperator(DbResult.OPERATOR_DJEZZY);
            } else if (responseLine.trim().contains(DbResult.SUFFIX_OPERATOR_CODE_OOREDOO)) {
                sim.setNameOperator(DbResult.OPERATOR_OOREDOO);
            } else {
                sim.setNameOperator("");
            }

            if (!sim.getNameOperator().equals("")) {
                Utils.println("Handeling Operator Response: " + sim.getNameOperator());
            }
        }

        private topupResponseModel handleTopupResponse(String responseLine, List<topupResponseModel> topupResponsesModel, String commandeToListenFor, int b) {
            Map<String, Object> hashMapTokenizer = new HashMap<>();
            DbResult.initListTopupConfirmationSubStringFromDB();
            if (isConfirmationTopupOperation(DbResult.listTopupConfirmationSubString, responseLine)) {
                operationPhoneNumber = commandeToListenFor.replace("_" + DbResult.USSD_TOPUP, "");
                ussdShouldBeConfirmed = true;
            } else {
                Integer idTransaction = getIdTransactionFromTopupResponse(responseLine);
                topupResponseModel trm = getTopupResponseModel(responseLine, topupResponsesModel);

                Utils.println("Handeling Topup response: ");
                Utils.println(DbResult.TAG_TOPUP_ID_TRANSACTION + " : " + idTransaction);
                Utils.println(DbResult.TAG_TOPUP_RESPONSE_CODE + " : " + trm.getResponseCode());
                Utils.println(DbResult.TAG_TOPUP_RESPONSE_TAG + " : " + trm.getResponseTag());
                return trm;
            }
            return null;
        }

        private topupResponseModel handleTopupResponse(String responseLine, List<topupResponseModel> topupResponsesModel, String commandeToListenFor) {
            Map<String, Object> hashMapTokenizer = new HashMap<>();
            topupResponseModel trm = null;
            if (isConfirmationTopupOperation(DbResult.listTopupConfirmationSubString, responseLine)) {
                operationPhoneNumber = commandeToListenFor.replace("_" + DbResult.USSD_TOPUP, "");
                ussdShouldBeConfirmed = true;
            } else {
                Integer idTransaction = getIdTransactionFromTopupResponse(responseLine);
                if (responseLine.equals(TIMEOUT)) {
                    trm = new topupResponseModel(DbResult.TOPUP_STATUS_TIMEOUT, 0, "", "");
                } else {
                    trm = getTopupResponseModel(responseLine, topupResponsesModel);
                }

                Utils.println("Handeling Topup response: ");
                Utils.println(DbResult.TAG_TOPUP_ID_TRANSACTION + " : " + idTransaction);
                Utils.println("Status : " + trm.getResponseStatus());
                Utils.println(DbResult.TAG_TOPUP_RESPONSE_CODE + " : " + trm.getResponseCode());
                Utils.println(DbResult.TAG_TOPUP_RESPONSE_TAG + " : " + trm.getResponseTag());
            }
            return trm;
        }

        /* private topupResponseModel handleTopupConfirmationResponse(String responseLine, List<topupResponseModel> topupResponsesModel) {
            Integer idTransaction = getIdTransactionFromTopupResponse(responseLine);
            topupResponseModel trm = getTopupResponseModel(responseLine, topupResponsesModel);

            Utils.println("Handeling Topup response: ");
            Utils.println(DbResult.TAG_TOPUP_ID_TRANSACTION + " : " + idTransaction);
            Utils.println(DbResult.TAG_TOPUP_RESPONSE_CODE + " : " + trm.getResponseCode());
            Utils.println(DbResult.TAG_TOPUP_RESPONSE_TAG + " : " + trm.getResponseTag());
            return trm;
        }*/
        private topupResponseModel handleTopupConfirmationResponse(String responseLine, List<topupResponseModel> topupResponsesModel, String commandeToListenFor) {
            topupResponseModel trm = getTopupResponseModel(responseLine, topupResponsesModel);
            if (isConfirmationTopupOperation(DbResult.listTopupConfirmationSubString, responseLine)) {
                operationPhoneNumber = commandeToListenFor.replace("_" + DbResult.USSD_TOPUP, "");
                ussdShouldBeConfirmed = true;
            } else {
                Integer idTransaction = getIdTransactionFromTopupResponse(responseLine);
                if (responseLine.equals(TIMEOUT)) {
                    trm = new topupResponseModel(DbResult.TOPUP_STATUS_TIMEOUT, 0, "", "");
                } else {
                    trm = getTopupResponseModel(responseLine, topupResponsesModel);
                }
                Utils.println("Handeling Topup response: ");
                Utils.println(DbResult.TAG_TOPUP_ID_TRANSACTION + " : " + idTransaction);
                Utils.println("Status : " + trm.getResponseStatus());
                Utils.println(DbResult.TAG_TOPUP_RESPONSE_CODE + " : " + trm.getResponseCode());
                Utils.println(DbResult.TAG_TOPUP_RESPONSE_TAG + " : " + trm.getResponseTag());
            }
            return trm;
        }

        private ArrayList<Sms> handleSmsRead(String receivedMessage) {
            int waiting = 0;
            ArrayList<Sms> smsList = new ArrayList<>();
            String[] listMesseges = receivedMessage.split("(?:\r\n)");
            String[] splittedMessage;
            Sms sms;
            for (int i = 0; i < listMesseges.length - 1; i++) {
                //String str1 = strs[i];
                if (listMesseges[i].startsWith("+CMGL:")) {
                    sms = new Sms();
                    splittedMessage = listMesseges[i].replaceAll(",,", ",\"\",").split(",\"");
                    if (splittedMessage.length == 5) {
                        sms.setId(splittedMessage[0]);
                        sms.setStatus(splittedMessage[1]);
                        sms.setPhone_name(splittedMessage[2]);
                        sms.setPhone_num(splittedMessage[3]);
                        sms.setDateTime(splittedMessage[4]);
                        sms.setContent(listMesseges[i + 1]);
                    } else {
                        sms.setContent(listMesseges[i] + "\n" + listMesseges[i + 1]);
                    }
                    smsList.add(sms);
                }
            }
            return smsList;
        }

        public boolean isConfirmationTopupOperation(List<String> listTopupConfirmationSubString, String responseLine) {
            for (String confirmationSubString : listTopupConfirmationSubString) {
                if (responseLine.toUpperCase().contains(confirmationSubString.toUpperCase())) {
                    return true;
                }
            }
            return false;
        }

        public boolean canShowMoreOffer(String recivedMessage, String showMoreCode) {
            return recivedMessage.contains(showMoreCode);
        }

        // End Hichem
//TODO: Tokenize reponse message and get Id transaction
        //======================= MOBILIS ========================
        //1- success : +CUSD: 2,"credit transmis",15 Mobilis
        //2- no sold : OK+CUSD: 2,"echec de transfert: montant superieur a  4999DZD.",15
        //======================= OOREDOO ========================
        //1- success : "Recharge de l'abonne 0551572474 100.00 Dinar realisee. Numero de l'operation 282949018.",15 
        //2- no sold : OK+CUSD: 2,"Valeur minimale autorisee du stock atteinte pour le solde debite. ",15
        //======================= DJEZZY ========================
        //1- success : +CUSD: 2,"100 DA TRANSFERE DE VOTRE COMPTE VERS LE NUMERO 0771716138",1
        //2- no sold : OK+CUSD: 2,"VOUS NE POUVEZ PAS RECHARGER LE COMPTE DE VOTRE CORRESPONDANT CREDIT INSUFFISANT",15
        /*private void handleSoldResponse(String responseLine) {
            Matcher matcher = SOLD_PATTERN.matcher(responseLine.trim());
            String sold = "0.0";
            if (matcher.find()) {
                sold = matcher.group(0).toString();
            }
            Utils.println("Handeling Sold Response: " + sold);
            sim.setSold(Float.parseFloat(sold.replaceAll("(Dinar|DZD|DA)", "")));
        }*/
        private void handleSoldResponse(String responseLine, String lastTextBeforSolde) {
            String[] responseSplited = responseLine.toUpperCase().split(lastTextBeforSolde.toUpperCase(), 2);
            String sold = "0.0";
            String soldPartResponse;
            if (responseSplited.length >= 2) {// if lastTextBeforSold exist in response so the split has been
                soldPartResponse = responseSplited[1];
            } else {
                soldPartResponse = responseSplited[0];
            }
            Matcher matcher = MONEY_PATTERN.matcher(soldPartResponse);
            if (matcher.find()) {
                sold = matcher.group(0).toString();
            }
            Utils.println("Handeling Sold Response: " + sold);
            //sim.setSold(Float.parseFloat(sold.replaceAll("\\D+", "")));
            sold = new StringTokenizer(sold).nextToken();
            sim.setSold(Float.parseFloat(sold));
        }

        private void handleBonusResponse(String responseLine, String lastTextBeforBonus) {
            String[] responseSplited = responseLine.toUpperCase().split(lastTextBeforBonus.toUpperCase(), 2);
            String bonus = "0.0";
            if (responseSplited.length >= 2) {// if lastTextBeforBonus exist in response so the split has been
                String bonusPartResponse = responseSplited[1];
                Matcher matcher = MONEY_PATTERN.matcher(bonusPartResponse);
                if (matcher.find()) {
                    bonus = matcher.group(0).toString();
                }
                Utils.println("Handeling Bonus Response: " + bonus);
                //sim.setBonus(Float.parseFloat(bonus.replaceAll("\\D+", "")));
                bonus = new StringTokenizer(bonus).nextToken();
                sim.setBonus(Float.parseFloat(bonus));
            }

        }

        private void handlePhoneResponse(String responseLine) {
            Matcher matcher = PHONE_PATTERN.matcher(responseLine.trim());
            String phone = "";
            if (matcher.find()) {
                phone = matcher.group(0);
            }
            //System.out.println("Sim phone est : " + phone);
            if (!phone.equals("")) {
                Utils.println("Handeling Phone Response: " + phone);
            }
            sim.setPhone(phone);
        }

        SerialReader(SerialPort sp, String commandType) {
            this.is = sp.getInputStream();
            this.commandToListenFor = commandType;
            this.stringBuilder = new StringBuilder();

        }

        SerialReader(SerialPort sp, String phone, String montant, String commandType) {
            this.is = sp.getInputStream();
            this.commandToListenFor = commandType;
            this.stringBuilder = new StringBuilder();
            this.Phone = phone;

        }

        public int getResponse() {
            return Response;
        }

        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
        }

        @Override
        public void serialEvent(SerialPortEvent spe) {
            byte[] receivedData = new byte[serialPort.bytesAvailable()];
            int numRead = serialPort.readBytes(receivedData, receivedData.length);
            lastReceivedData = new String(receivedData);
            recivedMessages += lastReceivedData;
            if (recivedMessages.endsWith("\r\n")) {
                //closePort();
                //}
                Utils.println("-------------------------------");
                Utils.println("COMMANDE TYPE  - " + commandToListenFor + "\nRECIEVED  DATA - " + recivedMessages);
            }
        }
    }
}
