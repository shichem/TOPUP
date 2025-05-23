/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general_helpers;

import com.fazecast.jSerialComm.SerialPort;
import custom_package.simBoxTransaction;
import custom_package.simUI;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Pattern;
import custom_vars.staticVars;
import simBox.Connexion.ConnexionPort;
import simBox.helper.ModemClient;
import simBox.helper.Sim;

/**
 *
 * @author kaa aziz
 */
public class intermediate_process {

    private int[] numberPos = {0, 1, 5, 6, 10, 11, 15, 16, 20, 21};

    /*
    SIMBOX INTERACTION BEGIN
     */
    public Vector<simUI> getAvailableSimUI_FromSimBox() {
        Vector<simUI> simUIVect = new Vector<simUI>();
        /* ADD HERE*/
        SerialPort[] ports = SerialPort.getCommPorts();
        for (int i = 0; i < ports.length; ++i) {
            Sim sim = new Sim(ports[i].getSystemPortName());
            ConnexionPort connexionPort = new ConnexionPort(ports[i].getSystemPortName());
            if (connexionPort.openPort(ports[i].getSystemPortName()) && connexionPort.getSerail().getCTS()) {
                ModemClient modem = new ModemClient(connexionPort.getSerail(), sim);
                sim = modem.initModem();
                System.out.println(sim.getNameOperator());
                System.out.println(sim.getPhone());
                System.out.println(sim.getPortCom());
                if(!sim.getNameOperator().equals("")){
                simUI simui = new simUI(sim.getPhone(), sim.getNameOperator(), sim.getPortCom(), sim.getSold());
                simUIVect.add(simui);
                }
            }
        }
        /**
         * ********
         */
        return simUIVect;
    }

    public double getSolde_FromSimBox(String simNumber, String portName) {
        double solde = -1;
        /* ADD HERE*/

        /**
         * ********
         */
        return solde;
    }

    public simUI getSimUIByport_FromSimBox(String portName) {
        simUI sim = null;
        /* ADD HERE*/

        /**
         * ********
         */
        return sim;
    }

    public simBoxTransaction sendTopUp_FromSimBox(simBoxTransaction request) {
        /* ADD HERE*/

        /**
         * ********
         */
        return request;
    }

    /*
       SIMBOX INTERACTION END
     */
    private boolean isValidMobileNumber(String mobileNumber, char ch, int posToCheck) {
        if (mobileNumber.charAt(posToCheck) != ch) {
            return false;
        }

        if (posToCheck == 5) {
            for (int i = 0; i < numberPos.length; i++) {
                try {
                    Integer.parseInt(String.valueOf(mobileNumber.charAt(numberPos[i])));
                } catch (Exception ee) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < mobileNumber.length(); i++) {
                try {
                    Integer.parseInt(String.valueOf(mobileNumber.charAt(i)));
                } catch (Exception ee) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isDjezzyValidMobileNumber(String mobileNumber) {
        if (this.isValidMobileNumber(mobileNumber, '7', 5) || this.isValidMobileNumber(mobileNumber, '9', 5)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMobilisValidMobileNumber(String mobileNumber) {
        return this.isValidMobileNumber(mobileNumber, '6', 5);
    }

    public boolean isOoredooValidMobileNumber(String mobileNumber) {
        return this.isValidMobileNumber(mobileNumber, '5', 5);
    }

    public boolean isDjezzyValidMobileNumber_draft(String mobileNumber) {
        if (this.isValidMobileNumber(mobileNumber, '7', 2) || this.isValidMobileNumber(mobileNumber, '9', 2)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMobilisValidMobileNumber_draft(String mobileNumber) {
        return this.isValidMobileNumber(mobileNumber, '6', 2);
    }

    public boolean isOoredooValidMobileNumber_draft(String mobileNumber) {
        return this.isValidMobileNumber(mobileNumber, '5', 2);
    }

    public String standardizeMobileNumer(String mobileNumber) {
        return mobileNumber.replaceAll("[\\.$| |-]", "");
    }

    public String standardizeDateFormat(String date) {
        String standardDate = "";

        standardDate = standardDate + date.substring(6, 8) + "-";
        standardDate = standardDate + date.substring(4, 6) + "-";
        standardDate = standardDate + date.substring(0, 4);

        return standardDate;
    }

    public String standardizeDateTimeFormat(String date) {
        String standardDate = "";

        standardDate = standardDate + date.substring(6, 8) + "-";
        standardDate = standardDate + date.substring(4, 6) + "-";
        standardDate = standardDate + date.substring(0, 4);

        return standardDate;
    }

    public Date stringToDate(String sDate) {
        Date d = null;
        sDate = this.standardizeDateFormat(sDate);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            ParsePosition pos = new ParsePosition(0);
            d = formatter.parse(sDate, pos);
            //return stringToDateInv(dateToStringInv(d));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return d;
    }

    public Date stringToDateInv(String sDate) {
        Date d = null;
        //sDate = this.standardizeDateFormat(sDate);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            d = formatter.parse(sDate, pos);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return d;
    }

    public Date stringToDateTime(String sDate) {
        Date d = null;
        //sDate = this.standardizeDateFormat(sDate);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            d = formatter.parse(sDate, pos);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return d;
    }

    public String dateToString(Date sDate) {
        String d = null;
        //sDate = this.standardizeDateFormat(sDate);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            d = formatter.format(sDate);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return d;
    }

    public String TimeToString(Date sDate) {
        String d = null;
        //sDate = this.standardizeDateFormat(sDate);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            d = formatter.format(sDate);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return d;
    }

    public String dateToStringInv(Date sDate) {
        String d = null;
        //sDate = this.standardizeDateFormat(sDate);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            d = formatter.format(sDate);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return d;
    }

    public String DateTimeToSTring(Date sDate) {
        String d = null;
        //sDate = this.standardizeDateFormat(sDate);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = formatter.format(sDate);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return d;
    }

    public String buildDateFormat(Date myDate) {
        DateFormat shortDateFormatEN = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT, new Locale("EN", "en"));
        return shortDateFormatEN.format(myDate);
    }

    public boolean verifyAlphabetic(String str) {
        boolean bool = true;
        if (Pattern.matches("[a-zA-Z]+", str)) {
            //System.out.println("Yep!");
        } else {
            bool = false;
            System.out.println(str + " is not alphabetic");
        }
        return bool;
    }

    public boolean verifyAlphabeticNumeric(String str) {
        boolean bool = true;
        if (Pattern.matches("[a-zA-Z0-9]+", str)) {
            //System.out.println("Yep!");
        } else {
            bool = false;
            System.out.println(str + " is not alphaNumeric");
        }
        return bool;
    }

    public boolean verifyNumeric(String str) {
        boolean bool = true;
        if (Pattern.matches("[0-9]+", str)) {
            //System.out.println("Yep!");
        } else {
            bool = false;
            System.out.println(str + " is not numeric");
        }
        return bool;
    }

    public int check_If_String_Exist_In_StringVector(String toVecrify, Vector<String> stringVect) {
        int check = -1;

        int i = 0;
        while ((i < stringVect.size()) && (check == -1)) {
            if (toVecrify.equals(stringVect.get(i))) {
                check = i;
            }
            i++;
        }

        return check;
    }

    public int getIndex_InTable(int object, Vector<Integer> vect) {
        int index = -1;
        boolean bool = false;
        int i = 0;
        while ((i < vect.size()) && (bool == false)) {
            if (vect.get(i) == object) {
                bool = true;
                index = i;
            } else {
                i++;
            }
        }
        return index;
    }

    public String getYearFromDate(Date date) {
        String st = staticVars.dateFormat.format(date);
        return st.substring(st.length() - 4);
    }

}
