/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_vars;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author kaa aziz
 */
public class uiVars {

    /*
    ICONS
     */
    public static String port_bloque_img_64 = "Icons\\Red_Light.png";
    public static String processing_img_64 = "Icons\\loading.gif";
    public static String port_bloque_img_26 = "Icons\\Red_Light_26.png";
    public static String processing_img = "Icons\\loading.gif";
    public static String ooredoo120_img = "Icons\\ooredo_120.png";
    public static String djezzy120_img = "Icons\\djezzy_120.png";
    public static String mobilis120_img = "Icons\\mobilis_120.png";
    public static String ooredoo80_img = "Icons\\ooredo_80.png";
    public static String djezzy80_img = "Icons\\djezzy_80.png";
    public static String mobilis80_img = "Icons\\mobilis_80.png";
    public static String ooredoo64_img = "Icons\\ooredo_64.png";
    public static String djezzy64_img = "Icons\\djezzy_64.png";
    public static String mobilis64_img = "Icons\\mobilis_64.png";
    public static String ooredoo32_img = "Icons\\ooredo_32.png";
    public static String djezzy32_img = "Icons\\djezzy_32.png";
    public static String mobilis32_img = "Icons\\mobilis_32.png";
    public static String ooredoo24_img = "Icons\\ooredo_24.png";
    public static String djezzy24_img = "Icons\\djezzy_24.png";
    public static String mobilis24_img = "Icons\\mobilis_24.png";
    public static String ooredoo16_img = "Icons\\ooredo_16.png";
    public static String djezzy16_img = "Icons\\djezzy_16.png";
    public static String mobilis16_img = "Icons\\mobilis_16.png";

    public static String refresh16_img = "Icons\\refresh_16.png";
    public static String refresh32_img = "Icons\\refresh_32.png";

    public static String config32_img = "Icons\\config_32.png";

    public static String valid64_img = "Icons\\valider_64.png";
    public static String valid32_img = "Icons\\valider_32.png";

    public static String configuration16_img = "Icons\\configuration_16.png";
    public static String configuration24_img = "Icons\\configuration_24.png";
    public static String configuration32_img = "Icons\\configuration_32.png";
    public static String configuration64_img = "Icons\\configuration_64.png";
    public static String configuration80_img = "Icons\\configuration_80.png";
    public static String configuration128_img = "Icons\\configuration_128.png";

    public static String ussd16_img = "Icons\\ussd_16.png";
    public static String ussd24_img = "Icons\\ussd_24.png";
    public static String ussd32_img = "Icons\\ussd_32.png";
    public static String ussd64_img = "Icons\\ussd_64.png";
    public static String ussd80_img = "Icons\\ussd_80.png";
    public static String ussd128_img = "Icons\\ussd_128.png";

    public static String operator16_img = "Icons\\operator_16.png";
    public static String operator24_img = "Icons\\operator_24.png";
    public static String operator32_img = "Icons\\operator_32.png";
    public static String operator64_img = "Icons\\operator_64.png";
    public static String operator80_img = "Icons\\operator_80.png";
    public static String operator128_img = "Icons\\operator_128.png";

    public static String clients16_img = "Icons\\clients_16.png";
    public static String clients24_img = "Icons\\clients_24.png";
    public static String clients32_img = "Icons\\clients_32.png";
    public static String clients64_img = "Icons\\clients_64.png";
    public static String clients80_img = "Icons\\clients_80.png";
    public static String clients128_img = "Icons\\clients_128.png";

    public static String modem16_img = "Icons\\modem_16.png";
    public static String modem24_img = "Icons\\modem_24.png";
    public static String modem32_img = "Icons\\modem_32.png";
    public static String modem64_img = "Icons\\modem_64.png";
    public static String modem80_img = "Icons\\modem_80.png";
    public static String modem128_img = "Icons\\modem_128.png";

    public static String password16_img = "Icons\\password_16.png";
    public static String password24_img = "Icons\\password_24.png";
    public static String password32_img = "Icons\\password_32.png";
    public static String password64_img = "Icons\\password_64.png";
    public static String password80_img = "Icons\\password_80.png";
    public static String password128_img = "Icons\\password_128.png";

    public static String add16_img = "Icons\\Add_16.png";
    public static String add24_img = "Icons\\Add_24.png";

    public static ImageIcon current_img;

    /*
    UI Components
     */
    private static Object[] offerDynColumn = {"Avant", "Description du champs", "Après"};
    private static Object[] offerDynCcomboBox = {"*", "#"};

    private static TableModel offerDynTableModel;

    public static void defaultErrorMessage(Component parentComponent, String title, String message) {
        JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void defaultOKMessage(Component parentComponent, String title, String message) {
        JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void getOfferDynTableModel(JTable offerDynTable, int nbColumn) {
        offerDynTableModel = new DefaultTableModel(offerDynColumn, nbColumn);

        offerDynTable.setModel(offerDynTableModel);
        JComboBox comboBox = new JComboBox(offerDynCcomboBox);
        comboBox.setSelectedIndex(0);
        DefaultTableCellRenderer renderer
                = new DefaultTableCellRenderer();
        renderer.setToolTipText("Cliquer pour choisir");
        for (int i = 0; i < nbColumn; i++) {
            offerDynTable.getModel().setValueAt(new JComboBox(), i, 0);
            offerDynTable.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(comboBox));
            offerDynTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
            offerDynTable.editCellAt(i, 0);
            offerDynTable.getModel().setValueAt(new JComboBox(), i, 2);
            offerDynTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox));
            offerDynTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
            offerDynTable.editCellAt(i, 2);
        }
        offerDynTable.getColumnModel().getColumn(0).setPreferredWidth(15);
        offerDynTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        offerDynTable.getColumnModel().getColumn(2).setPreferredWidth(15);

        offerDynTable.setShowGrid(true);

    }

}
