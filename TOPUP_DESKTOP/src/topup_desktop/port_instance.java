/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topup_desktop;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author kaa aziz
 */
public class port_instance {
    private String port_name;
    private String port_operator;
    private String port_number;
    private int port_status; //-1: Not checked yet, 0: Inactive, 1: Active
    
    private JTextField port_name_comp; 
    private JComboBox port_operator_comp; 
    private JTextField port_number_comp; 
    private String port_status_image;
    private JCheckBox port_autoCheck;
}
