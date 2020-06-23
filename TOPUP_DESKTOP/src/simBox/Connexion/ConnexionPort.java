/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simBox.Connexion;

import javax.comm.CommPortIdentifier;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.InputStream;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 *
 * @author hichem
 */
public class ConnexionPort {

    public String COM;
    private CommPortIdentifier pID;
    private SerialPort serPort;
    //private ModemClient modem;

    public ConnexionPort(String COM) {
        this.COM = COM;
    }

    public boolean connect2port() {
        boolean bool = false;

        return bool;
    }

    public boolean openPort(String portName) {
        serPort = SerialPort.getCommPort(portName);
        //System.out.println("Going to use the following port:" + serPort.getSystemPortName());
        serPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
//        System.out.println("Going to open the port...");
        boolean result = serPort.openPort();
         //modem = new ModemClient(serPort);
        //SerialReader sr = new SerialReader(serPort, "");
        //serPort.addDataListener(sr);
        //System.out.println("Port opened" + portName + "? " + result);
        return result;
    }
    
    public boolean closePort(){
    return serPort.closePort();
    }

    public SerialPort getSerail() {
        return this.serPort;
    }


    public void CloseConnexionToPort() {
        if (this.serPort != null) {
            this.serPort.closePort​();
        }
    }
    // TODO:  +... switch case "+COPS" / "+CUSD" / "+CLIP"
    // TODO: OPERATOR code Error handler 
    // Queue command 
    // Operator name switch case 60301 60303 60302
    //MultiThreading 

    private class SerialReader implements SerialPortDataListener {

        private final Pattern RESPONSE_PATTERN = Pattern.compile("\\+....:\\.+");
        private final Pattern USSD_PATTERN = Pattern.compile("\\+....:\\s+(\\d)(?:,\\s*\"([^\"]*))?(?:\",\\s*(\\d+)\\s*)?\"?\r?$");
        private final Pattern SOLD_PATTERN = Pattern.compile("(\\d+)\\.\\d+|\\d");
        private final Pattern COMMAND_TYPE_PATTERN = Pattern.compile("^\\+....:");
        private final Pattern OPERATOR_PATTERN = Pattern.compile("^\\+....:");
        private final Pattern PATTERN ;
        private InputStream is;
        private String commandToListenFor="";
        SerialReader(SerialPort sp, String commandType) {
            this.is = sp.getInputStream();
            switch(commandType){
                case "USSD_SOLD":
                    PATTERN = SOLD_PATTERN;
                    break;
                case "USSD_OPERATOR": 
                    PATTERN = OPERATOR_PATTERN;
                    break;
                case "USSD":
                    PATTERN = RESPONSE_PATTERN;
                    break;
                default: 
                   PATTERN = RESPONSE_PATTERN;
                    break; 
            }
        }

        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
        }

        @Override
        public void serialEvent(SerialPortEvent spe) {

            byte[] newData = new byte[serPort.bytesAvailable()];
            int numRead = serPort.readBytes(newData, newData.length);
//            System.out.println("Data recieved event " + numRead);
            System.out.println(new String(newData) + serPort.getSystemPortName());
            Matcher matcher = RESPONSE_PATTERN.matcher(new String(newData).trim());

            if (matcher.matches()) {
                System.out.println("Line TYPE " + COM + " Est: " + matcher.group());
                String response = matcher.group().split(",")[1];
                Matcher matcher2 = COMMAND_TYPE_PATTERN.matcher(new String(newData).trim());
                if (matcher2.find()) {
                    switch (matcher2.group()) {
                        case "+CUSD:":
                            Matcher matcher3 = SOLD_PATTERN.matcher(response);
                            if (matcher3.find()) {
                                System.out.println("Votre sold sur " + COM + " Est: " + matcher3.group());
                            } else {
                                System.out.println("get Sold Error ");
                            }
                            break;
                        case "+COPS:":
                            System.out.println("GET OPERATOR COMMAND RESPONSE");
                            break;
                        case "+CLIP:":
                            System.out.println("RING RESPONSE");
                            break;

                    }

                }
            }

        }

    }
}
