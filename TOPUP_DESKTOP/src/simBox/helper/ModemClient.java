/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simBox.helper;


import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Soulsoft
 */
public class ModemClient {

    private SerialPort serPort;

    private InputStream in;
    private OutputStream out;
    private Sim sim;
    private Boolean OperatorValue = false;

    public ModemClient(SerialPort serPort) {
        this.serPort = serPort;
        sim = new Sim();
        this.out = serPort.getOutputStream();
        this.in = serPort.getInputStream();
    }

    public ModemClient(SerialPort serPort, Sim OldSim) {
        this.serPort = serPort;
        sim = OldSim;
        this.out = serPort.getOutputStream();
        this.in = serPort.getInputStream();
    }

    // AT COMMAND send function 

    private void send(String cmd, String desc) {
        try {
            this.out.write(cmd.getBytes());
//            this.in = serPort.getInputStream();
//            formatStringBeforePrint("[Send cmd to serial port] " + cmd + " : " + desc);
//            System.out.printf("[Input Stream] %s%n ", in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getOperatorName() throws InterruptedException {
        SerialReader sr = new SerialReader(serPort, "operator");
        serPort.addDataListener(sr);
        //System.out.println("Adding listener");
        send("AT+COPS?\r\n", "Get Operator name");
        Thread.sleep(3000);// TODO: check if this timout is necessary 
        //System.out.println("Removing listener");
        serPort.removeDataListener();

    }

    public Sim initModem() {

        try {
            send("ATE0\r\n", "No ECHO");
            Thread.sleep(1000);
            this.getOperatorName();
            if (OperatorValue) {

                switch (sim.getNameOperator().toUpperCase()) {
                    case "DJEZZY":
                        // TODO: get USSD CODE From DB 
                        this.getBalanceOperation("766", "19942");
                        //this.getPhoneNumber("#99#");
                        break;
                    case "MOBILIS":
                        // TODO: get USSD CODE From DB 
                        this.getBalanceOperation("632*01*19942", "19942");
                        this.getPhoneNumber("*101#");
                        break;
                    case "OOREDOO":
                        // TODO: get USSD CODE From DB 
                        this.getBalanceOperation("570*4655", "4655");
                        //this.getPhoneNumber("*113*1*1#");
                        break;
                }
            }

            return sim;
        } catch (InterruptedException ex) {
            Logger.getLogger(ModemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    // TODO: before Topup task we have to check our Balance 
    // ,"Vous voulez transf�rer 100 DA de votre compte vers le num�ro 0551572474, tapez 1 pour confirmer. Ou tapez 2 pour annuler",15
    //K+CUSD: 1,"VOUS VOULEZ TRANSFERER 100 DA DE VOTRE COMPTE VERS LE NUMERO 0771716138.
    //1:POUR CONFIRMER
    //2:POUR ANNULER",15

    public void topupOperation(String phone, String Montant, String PIN, String code) {
        System.out.println("Topup Operation: " + phone + " / " + code + " / " + PIN + " / " + Montant);
        try {
            SerialReader sr = new SerialReader(serPort, "USSD_TOPUP", phone, Montant);
            serPort.addDataListener(sr);
            send("AT+CUSD=1,\"*" + code + "*" + phone + "*" + Montant + "*" + PIN + "#\",15\r\n", "TOPUP");
            Thread.sleep(6000);
            int status = sr.getResponse();
            serPort.removeDataListener();
            if (status == 1) {
                this.ussdConfirmationResponse("1");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ModemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getBalanceOperation(String code, String PIN) {
        try {
            SerialReader sr = new SerialReader(serPort, "USSD_SOLD");
            serPort.addDataListener(sr);
            send("AT+CUSD=1,\"*" + code + "#\",15\r\n", "balance operation");
            Thread.sleep(6000);
            //System.out.println("Removing listener");
            serPort.removeDataListener();
        } catch (InterruptedException ex) {
            Logger.getLogger(ModemClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void getPhoneNumber(String code) throws InterruptedException {

        SerialReader sr = new SerialReader(serPort, "USSD_PHONE");
        serPort.addDataListener(sr);
        //System.out.println("Adding listener");
        send("AT+CUSD=1,\"" + code + "\",15\r\n", "Get Phone number operation");
        Thread.sleep(4000);
        System.out.println("Remove phone listener");
        serPort.removeDataListener();
    }

    private void ussdConfirmationResponse(String code) throws InterruptedException {

        SerialReader sr = new SerialReader(serPort, "USSD_CONFIRMATION");
        serPort.addDataListener(sr);
        //System.out.println("Adding listener");
        send("AT+CUSD=1,\"" + code + "\",15\r\n", "USSD_CONFIRMATION");
        Thread.sleep(4000);
        serPort.removeDataListener();
    }

    private void formatStringBeforePrint(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class SerialReader implements SerialPortDataListener {

        private final Pattern RESPONSE_PATTERN = Pattern.compile("\\+....:(.*)");
        private final Pattern USSD_PATTERN = Pattern.compile("\\+....:\\s+(\\d)(?:,\\s*\"([^\"]*))?(?:\",\\s*(\\d+)\\s*)?\"?\r?$");
        private final Pattern SOLD_PATTERN = Pattern.compile("(\\d+)\\.\\d+|\\d+|\\d");
        private final Pattern PHONE_PATTERN = Pattern.compile("\\d+");
        private final Pattern COMMAND_TYPE_PATTERN = Pattern.compile("^\\+....:");
        private final Pattern OPERATOR_PATTERN = Pattern.compile("\\+COPS:(.*)");
        private final Pattern USSD_TOPUP_PATTERN = Pattern.compile("\\+....:\\s+(\\d)(?:,\\s*\"([^\"]*))?(?:\",\\s*(\\d+)\\s*)?\"?\r?$");
        private final Pattern USSD_TOPUP_RESPONSE_PATTERN = Pattern.compile("\\+....:\\s+(\\d)(?:,\\s*\"([^\"]*))?(?:\",\\s*(\\d+)\\s*)?\"?\r?$");
        private final Pattern USSD_PHONE_PATTERN = Pattern.compile("\\+....:\\s+(\\d)(?:,\\s*\"([^\"]*))?(?:\",\\s*(\\d+)\\s*)?\"?\r?$");
        private final Pattern PATTERN;
        private InputStream is;
        private String commandToListenFor = "";
        private StringBuilder stringBuilder;
        private String Phone = "";
        private String Montant = "";
        private int Response = -1;

        SerialReader(SerialPort sp, String commandType) {
            this.is = sp.getInputStream();
            this.commandToListenFor = commandType.toUpperCase();
            this.stringBuilder = new StringBuilder();
            switch (commandToListenFor) {
                case "USSD_SOLD":
                    PATTERN = RESPONSE_PATTERN;
                    break;
                case "OPERATOR":
                    PATTERN = OPERATOR_PATTERN;
                    break;
                case "USSD_TOPUP":
                    PATTERN = USSD_TOPUP_PATTERN;
                    break;
                case "USSD_PHONE":
                    PATTERN = RESPONSE_PATTERN;
                    break;
                case "USSD":
                    PATTERN = RESPONSE_PATTERN;
                    break;
                    case "USSD_CONFIRMATION":
                    PATTERN = USSD_TOPUP_RESPONSE_PATTERN;
                    break;
                default:
                    PATTERN = RESPONSE_PATTERN;
                    break;
            }
        }

        SerialReader(SerialPort sp, String commandType, String phone, String montant) {
            this.is = sp.getInputStream();
            this.commandToListenFor = commandType.toUpperCase();
            this.stringBuilder = new StringBuilder();
            this.Phone = phone;
            switch (commandToListenFor) {

                case "USSD_TOPUP":
                    PATTERN = USSD_TOPUP_PATTERN;
                    break;
                default:
                    PATTERN = RESPONSE_PATTERN;
                    break;
            }
        }

        public int getResponse() {
            return Response;
        }

        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
        }

        private void handleOperatorResponse(Matcher matcher) {
            String operatorName = null;
            String responseLine = matcher.group().split(":")[1];
            String operator = responseLine.split(",")[2];
            switch (operator) {
                case "60301":
                    operatorName = "Mobilis";
                    break;
                case "60302":
                    operatorName = "Djezzy";
                    break;
                case "60303":
                    operatorName = "Ooredoo";
                    break;
                default:
                    System.out.println(operator);
            }
            sim.setNameOperator(operatorName);
            OperatorValue = true;
        }

        private void handleTopupResponse(Matcher matcher) {
            String responseLine = matcher.group().split(":")[1];
            String operatorMsg = responseLine.split(",")[1];
            System.out.println("Operator topup Response : " + operatorMsg.trim());
            if (operatorMsg.trim().contains(this.Phone.substring(1))) {
                System.out.println("Yes :D Confirme topup ");
                this.Response = 1;
            } else {
                this.Response = 0;
            }
        }
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
        private void handleTopupConfirmation() {
        }

        private void handleGetSoldResponse(Matcher matcher) {
            String responseLine = matcher.group().split(":")[1];
            String operatorMsg = responseLine.split(",")[1];

            Matcher matcher2 = SOLD_PATTERN.matcher(operatorMsg.trim());
            Float sold = null;
            if (matcher2.find()) {
                if (matcher2.groupCount() > 1) {
                    //System.out.println("Votre sold est : " + matcher2.group(1));
                    sold = Float.parseFloat(matcher2.group(1).toString());
                } else {
                    //System.out.println("Votre sold est : " + matcher2.group());
                    sold = Float.parseFloat(matcher2.group().toString());
                }
                sim.setSold(sold);
            }
        }

        private void handleGetPhoneResponse(Matcher matcher) {
            System.out.println("GetPhone Handler MSG : " + matcher.group().toString());
            String responseLine = matcher.group().split(":")[2];
            String operatorMsg = responseLine.split(",")[0];
            //.out.println("Sim phone MSG : " + operatorMsg.trim());
            Matcher matcher2 = PHONE_PATTERN.matcher(operatorMsg.trim());
            String phone = null;
            if (matcher2.find()) {
                if (matcher2.groupCount() > 1) {
                    //System.out.println("Votre sold est : " + matcher2.group(1));
                    phone = matcher2.group(1);
                } else {
                    //System.out.println("Votre sold est : " + matcher2.group());
                    phone = matcher2.group();
                }

            }
            //System.out.println("Sim phone est : " + phone);
            sim.setPhone(phone);
        }

        @Override
        public void serialEvent(SerialPortEvent spe)  {
//            if (spe.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE){ 
//                System.out.println("EVENT TYPE LISTENING_EVENT_DATA_AVAILABLE");
//            } else {
//            System.out.println("EVENT TYPE != LISTENING_EVENT_DATA_AVAILABLE");
//            }
            byte[] newData = new byte[serPort.bytesAvailable()];
            Boolean goahead = false;
            Boolean append = false;
            int numRead = serPort.readBytes(newData, newData.length);
            String responseLine;

            String operator;
            String operatorName = null;
//            System.out.println("Data recieved event " + numRead);
            // System.out.println(new String(newData) + "END");
            responseLine = new String(newData);
            System.out.println("GOAHEAD: Begin " + goahead);
            if (commandToListenFor.equals("OPERATOR")){
                System.out.println("Operator yes go a head :)");
                goahead = true;
            } else {
//                while (!responseLine.contains(",15")){
//                    System.out.println("keep listening...." + new String(newData).trim());
//                    stringBuilder.append(new String(newData).trim());
//                    responseLine = stringBuilder.toString();
//                }
//                System.out.println("Containe :)");
                //goahead = true;
                if (responseLine.endsWith(",15")) {
                    System.out.println("Containe :)");
                    stringBuilder.append(new String(newData).trim());
                    responseLine = stringBuilder.toString();
                    goahead = true;
                } else {
                    System.out.println("keep listening....");
                    stringBuilder.append(new String(newData).trim());
                    responseLine = stringBuilder.toString();
                    if (responseLine.trim().endsWith(",15")) {
                        System.out.println("keep listening.... goAhead 2 ");
                        goahead = true;
                    }
                }

                // end else 
            }
            System.out.println("GOAHEAD: after " + goahead);

            System.out.println("Response after append : " + responseLine);
            if (goahead) {
                Matcher matcher = PATTERN.matcher(responseLine);
                if (matcher.find()) {
                    //System.out.println("Response in: ");
                    switch (commandToListenFor) {
                        case "OPERATOR":
                            this.handleOperatorResponse(matcher);
                            break;
                        case "USSD_SOLD":
                            //System.out.println("Response after append in : " + responseLine + "/n");
                            this.handleGetSoldResponse(matcher);
                            break;
                        case "USSD_PHONE":
                            System.out.println("Response after append in get phone : " + responseLine + "/n");
                            this.handleGetPhoneResponse(matcher);
                            break;
                        case "USSD_TOPUP":
                            this.handleTopupResponse(matcher);
                            break;
                        case "USSD_CONFIRMATION":
                            System.out.println("TOPUP RESPONSE : " + responseLine + "/n");
                            //TODO: Response Handler and get ID transactions
                            break;
                    }
                } else {
                    //System.out.println("doesn't match :( ");
                }
            } else {
                System.out.println("Error :(");

            }
        }

    }

    
}
