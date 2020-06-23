/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simBox.Connexion;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.CommDriver;

/**
 *
 * @author Administrateur
 */
public class InitDriver {

    public InitDriver() {
    }

    public static void intiDriverConnexion() {
        String driverName = "com.sun.comm.Win32Driver";
        CommDriver commdriver = null;
        try {
            /* TODO output your page here. You may use following sample code. */
            try {
                commdriver = (CommDriver) Class.forName(driverName).newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(InitDriver.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            } catch (IllegalAccessException ex) {
                Logger.getLogger(InitDriver.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InitDriver.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        try {
            commdriver.initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
