/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.ArrayList;
import simBox.helper.Sms;



/**
 *
 * @author HITCHI
 */
public class Utils {

    //Devises
   
    public Utils() {
    }
    
    public static void println(String message){
        System.out.println(message);
    }
    
        public static void print(String message){
        System.out.print(message);
    }

    
    public static int stringCount(String string, String substring){
        int counter = 0;
        while (string.contains(substring)){
            counter++;
            string = string.replaceFirst(substring, "");
        }
        return counter;
    }
}
