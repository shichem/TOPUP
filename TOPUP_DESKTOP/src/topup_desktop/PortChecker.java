/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topup_desktop;

import custom_vars.uiVars;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author kaa aziz
 */
public class PortChecker implements Runnable {

    private int port;
    private JLabel label;

    public PortChecker(int port, JLabel label) {
        this.port = port;
        this.label = label;
    }

    @Override
    public void run() {
        try {
            uiVars.current_img = new ImageIcon(uiVars.processing_img);
            this.label.setIcon(uiVars.current_img);
            Thread.sleep(3000);
            long waitingtime=200;
            while (true) {
                try {
                    uiVars.current_img = new ImageIcon(uiVars.port_bloque_img_26);
                    this.label.setIcon(uiVars.current_img);
                    //waitingtime = (long) (1000 * Math.random());
                    Thread.sleep(waitingtime);
                    this.label.setIcon(null);
                    Thread.sleep(waitingtime);
                } catch (InterruptedException ex) {
                    Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
