/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topup_desktop;

import com.fazecast.jSerialComm.SerialPort;
import custom_package.operatorUI;
import custom_vars.staticVars;
import custom_vars.uiVars;
import general_helpers.intermediate_process;
import helper.DbResult;
import helper.Utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import model_db.OfferType;
import model_db.Operator;
import model_db.Trader;
import model_db.TraderCategory;
import model_db.TraderType;
import model_helpers.Trader_Util;
import simBox.helper.Gsm;
import simBox.helper.Sim;

/**
 *
 * @author kaa aziz
 */
public class main_interface extends javax.swing.JFrame {

    /**
     * Creates new form main_interface
     */
    intermediate_process interProcess;

    Border errorBorder = new LineBorder(Color.RED, 2, true);
    Border okBorder = new LineBorder(Color.GREEN, 2, true);
    MaskFormatter modelMobileNumber;

    Object[] alltradersColumns = {"N* Client", "Raison social", "Nom", "Prénom", "Numéro de Puce", "Catégorie", "Type"};

    public main_interface() throws ParseException {
        initComponents();

        globaleSolde_Label.setText(String.valueOf(staticVars.globalSolde) + " DA.");
        globaleSoldeDjezzy_Label.setText(String.valueOf(staticVars.globalSoldeDjezzy) + " DA.");
        globaleSoldeMobilis_Label.setText(String.valueOf(staticVars.globalSoldeMobilis) + " DA.");
        globaleSoldeOoredoo_Label.setText(String.valueOf(staticVars.globalSoldeOoredoo) + " DA.");

        interProcess = new intermediate_process();
        modelMobileNumber = new MaskFormatter("## - ## - ## - ## - ##");
        offreST_operator.addItem("-----------------");
        offreST_type.addItem("----------------------------------");
        for (int i = 0; i < staticVars.actualUser.getOpUIVestor().size(); i++) {
            operatorUI elementAt = staticVars.actualUser.getOpUIVestor().elementAt(i);
            offreST_operator.addItem(elementAt.getOperaror().getOperatorDesc());
        }
        for (int i = 0; i < staticVars.actualUser.getTsUI().getOfferTypeVector().size(); i++) {
            OfferType elementAt = staticVars.actualUser.getTsUI().getOfferTypeVector().get(i);
            offreST_type.addItem(elementAt.getOfferTypeDesc());
        }
        for (int i = 0; i < staticVars.actualUser.getTsUI().getTraderTypeVector().size(); i++) {
            TraderType elementAt = staticVars.actualUser.getTsUI().getTraderTypeVector().get(i);
            traderType_Combo.addItem(elementAt.getTraderTypeDesc());
        }
        for (int i = 0; i < staticVars.actualUser.getTsUI().getTraderCategoryVector().size(); i++) {
            TraderCategory elementAt = staticVars.actualUser.getTsUI().getTraderCategoryVector().get(i);
            traderCategory_Combo.addItem(elementAt.getTraderCategoryDesc());
        }
        List possibleParents = new Trader_Util().getTrader_by_traderType_and_Category(staticVars.globalSession,
                staticVars.actualUser.getTsUI().getTraderTypeByDesc(staticVars.traderType_Application),
                staticVars.actualUser.getTsUI().getTraderCategoryByDesc(staticVars.traderCategory_Grossiste), "");
        providerAddTrader_Combo.removeAllItems();
        for (int i = 0; i < possibleParents.size(); i++) {
            Trader get = (Trader) possibleParents.get(i);
            providerAddTrader_Combo.addItem(String.valueOf(get.getIdtrader() + "(" + get.getTraderCompany() + ")"));

        }
        List traders = new Trader_Util().getAllTrader(staticVars.globalSession, "");
        TableModel tradermodel = new DefaultTableModel(alltradersColumns, traders.size());
        usersTrader_Combo.removeAllItems();
        for (int i = 0; i < traders.size(); i++) {
            Trader get = (Trader) traders.get(i);
            usersTrader_Combo.addItem(String.valueOf(get.getIdtrader() + "(" + get.getTraderCompany() + ")"));
            tradermodel.setValueAt(get.getIdtrader(), i, 0);
            tradermodel.setValueAt(get.getTraderCompany(), i, 1);
            tradermodel.setValueAt(get.getTraderLname(), i, 2);
            tradermodel.setValueAt(get.getTraderFname(), i, 3);
            tradermodel.setValueAt(get.getSimnumber(), i, 4);
            tradermodel.setValueAt(get.getTraderCategory().getTraderCategoryDesc(), i, 5);
            tradermodel.setValueAt(get.getTraderType().getTraderTypeDesc(), i, 6);
        }

        allTradersTable.setModel(tradermodel);

        if (staticVars.actualUser.getActualUser().getTrader() != null) {
            jCheckBox1.setVisible(false);
        }
        try {
            try {
                //UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
                UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
                //UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
                //UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
                //UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
                SwingUtilities.updateComponentTreeUI(this);
                //on passe au UIManager le nom complet de la classe du Look and Feel
                //naturellement, celle-ci doit être disponible dans le CLASSPATH
            } catch (InstantiationException e) {
                System.out.println("yop");
            } catch (ClassNotFoundException e) {
                System.out.println("yap");
            } catch (UnsupportedLookAndFeelException e) {
                System.out.println("yip");
            } catch (IllegalAccessException e) {
                System.out.println("yep");
            }

        } catch (Exception eeeee) {

        }

        djezzy_send_button.setEnabled(false);
        mobilis_send_button.setEnabled(false);
        mobilis_send_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mobilis_send_buttonActionPerformed(e);
            }
        });

        ooredoo_send_button.setEnabled(false);
        ooredoo_send_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ooredoo_send_buttonActionPerformed(e);
            }
        });

        djezzy_panel_broker.setIcon(new ImageIcon(uiVars.djezzy80_img));
        djezzy_offers_IconLabel.setIcon(new ImageIcon(uiVars.djezzy64_img));
        djezzy_checkSodle_button.setIcon(new ImageIcon(uiVars.refresh32_img));
        djezzy_configureOffer_button.setIcon(new ImageIcon(uiVars.config32_img));
        djezzy_send_button.setIcon(new ImageIcon(uiVars.valid64_img));
        djezzy_updateClient_button.setIcon(new ImageIcon(uiVars.configuration32_img));
        djezzy_addClient_button.setIcon(new ImageIcon(uiVars.add24_img));
        djezzy_addOffer_button.setIcon(new ImageIcon(uiVars.add24_img));

        ooredoo_panel_broker.setIcon(new ImageIcon(uiVars.ooredoo80_img));
        ooredoo_offers_IconLabel.setIcon(new ImageIcon(uiVars.ooredoo64_img));
        ooredoo_checkSodle_button.setIcon(new ImageIcon(uiVars.refresh32_img));
        ooredoo_configureOffer_button.setIcon(new ImageIcon(uiVars.config32_img));
        ooredoo_send_button.setIcon(new ImageIcon(uiVars.valid64_img));
        ooredoo_updateClient_button.setIcon(new ImageIcon(uiVars.configuration32_img));
        ooredoo_addClient_button.setIcon(new ImageIcon(uiVars.add24_img));
        ooredoo_addOffer_button.setIcon(new ImageIcon(uiVars.add24_img));

        Mobilis_panel_broker.setIcon(new ImageIcon(uiVars.mobilis80_img));
        Mobilis_offers_IconLabel.setIcon(new ImageIcon(uiVars.mobilis64_img));
        mobilis_checkSodle_button.setIcon(new ImageIcon(uiVars.refresh32_img));
        mobilis_configureOffer_button.setIcon(new ImageIcon(uiVars.config32_img));
        mobilis_send_button.setIcon(new ImageIcon(uiVars.valid64_img));
        mobilis_updateClient_button.setIcon(new ImageIcon(uiVars.configuration32_img));
        mobilis_addClient_button.setIcon(new ImageIcon(uiVars.add24_img));
        mobilis_addOffer_button.setIcon(new ImageIcon(uiVars.add24_img));

        offers_panel_broker.setIcon(new ImageIcon(uiVars.ussd80_img));

        operator_panel_broker.setIcon(new ImageIcon(uiVars.operator80_img));

        clients_panel_broker.setIcon(new ImageIcon(uiVars.clients80_img));

        portpuce_panel_broker.setIcon(new ImageIcon(uiVars.modem80_img));

        users_panel_broker.setIcon(new ImageIcon(uiVars.password80_img));

        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setUndecorated(true);
        //this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        globaleSolde_Label = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        djezzyChipsPanel = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        globaleSoldeDjezzy_Label = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        OoredooChipsPanel = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        globaleSoldeOoredoo_Label = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        mobilisChipsPanel = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        globaleSoldeMobilis_Label = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        ooredoo_panel_broker = new javax.swing.JButton();
        Mobilis_panel_broker = new javax.swing.JButton();
        djezzy_panel_broker = new javax.swing.JButton();
        RC_TabbedPane = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        djezzy_1 = new javax.swing.JButton();
        djezzy_0 = new javax.swing.JButton();
        djezzy_6 = new javax.swing.JButton();
        djezzy_7 = new javax.swing.JButton();
        djezzy_delete_button = new javax.swing.JButton();
        djezzy_4 = new javax.swing.JButton();
        djezzy_8 = new javax.swing.JButton();
        djezzy_init_button = new javax.swing.JButton();
        djezzy_2 = new javax.swing.JButton();
        djezzy_9 = new javax.swing.JButton();
        djezzy_3 = new javax.swing.JButton();
        djezzy_5 = new javax.swing.JButton();
        djezzy_show_panel = new javax.swing.JPanel();
        djezzy_offers_IconLabel = new javax.swing.JLabel();
        djezzy_offers_panel = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        djezzy_mobile_ftext = new javax.swing.JFormattedTextField();
        jLabel33 = new javax.swing.JLabel();
        djezzy_amount_text = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        djezzy_idclient_text = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        djezzy_clientname_text = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        djezzy_ciompany_text = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        djezzy_clientTable = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        djezzy_configureOffer_button = new javax.swing.JButton();
        djezzy_checkSodle_button = new javax.swing.JButton();
        djezzy_addOffer_button = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        djezzy_updateClient_button = new javax.swing.JButton();
        djezzy_addClient_button = new javax.swing.JButton();
        djezzy_send_button = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        mobilis_1 = new javax.swing.JButton();
        mobilis_0 = new javax.swing.JButton();
        mobilis_6 = new javax.swing.JButton();
        mobilis_7 = new javax.swing.JButton();
        mobilis_delete_button = new javax.swing.JButton();
        mobilis_4 = new javax.swing.JButton();
        mobilis_8 = new javax.swing.JButton();
        mobilis_init_button = new javax.swing.JButton();
        mobilis_2 = new javax.swing.JButton();
        mobilis_9 = new javax.swing.JButton();
        mobilis_3 = new javax.swing.JButton();
        mobilis_5 = new javax.swing.JButton();
        djezzy_show_panel3 = new javax.swing.JPanel();
        Mobilis_offers_IconLabel = new javax.swing.JLabel();
        mobilis_offers_panel = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        mobilis_mobile_ftext = new javax.swing.JFormattedTextField();
        jLabel46 = new javax.swing.JLabel();
        mobilis_amount_text = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        mobilis_idclient_text = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        mobilis_clientname_text = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        mobilis_ciompany_text = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        mobilis_clientTable = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        mobilis_configureOffer_button = new javax.swing.JButton();
        mobilis_checkSodle_button = new javax.swing.JButton();
        mobilis_addOffer_button = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        mobilis_updateClient_button = new javax.swing.JButton();
        mobilis_addClient_button = new javax.swing.JButton();
        mobilis_send_button = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        djezzy_40 = new javax.swing.JButton();
        djezzy_41 = new javax.swing.JButton();
        djezzy_42 = new javax.swing.JButton();
        djezzy_43 = new javax.swing.JButton();
        djezzy_delete_button4 = new javax.swing.JButton();
        djezzy_44 = new javax.swing.JButton();
        djezzy_45 = new javax.swing.JButton();
        djezzy_init_button4 = new javax.swing.JButton();
        djezzy_46 = new javax.swing.JButton();
        djezzy_47 = new javax.swing.JButton();
        djezzy_48 = new javax.swing.JButton();
        djezzy_49 = new javax.swing.JButton();
        djezzy_show_panel4 = new javax.swing.JPanel();
        ooredoo_offers_IconLabel = new javax.swing.JLabel();
        ooredoo_offers_panel = new javax.swing.JPanel();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        ooredoo_mobile_ftext = new javax.swing.JFormattedTextField();
        jLabel51 = new javax.swing.JLabel();
        ooredoo_amount_text = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        ooredoo_idclient_text = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        ooredoo_clientname_text = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        ooredoo_ciompany_text = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        ooredoo_clientTable = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        ooredoo_configureOffer_button = new javax.swing.JButton();
        ooredoo_checkSodle_button = new javax.swing.JButton();
        ooredoo_addOffer_button = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        ooredoo_updateClient_button = new javax.swing.JButton();
        ooredoo_addClient_button = new javax.swing.JButton();
        ooredoo_send_button = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        port_panel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        operator_panel = new javax.swing.JPanel();
        status_panel = new javax.swing.JPanel();
        number_panel = new javax.swing.JPanel();
        check_panel = new javax.swing.JPanel();
        autocheck_panel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        config_TabbedPanel = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        traderAddCompanyName = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        traderCategory_Combo = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel81 = new javax.swing.JLabel();
        comapnyName_Text = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        Lname_Text = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        Fname_Text = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        mobileNumber_Text = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        Adress_Text = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        Wilaya_Text = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        commune_Text = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        email1_Text = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        email2_Text = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        Tel1_Text = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        Tel2_Text = new javax.swing.JTextField();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jLabel92 = new javax.swing.JLabel();
        providerAddTrader_Combo = new javax.swing.JComboBox<>();
        jLabel93 = new javax.swing.JLabel();
        traderType_Combo = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        allTradersTable = new javax.swing.JTable();
        jButton32 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton33 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        offresST_beforeNum = new javax.swing.JTextField();
        offresST_betweenNumPin = new javax.swing.JTextField();
        offresST_AfterPIN = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        offreDYN_TableBeforeNum = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        offreDYN_TableBetweenNumPin = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        offreDYN_NbBeforeNum = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        offreDYN_NbBetweenNumPin = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        offresDYN_AfterPIN = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        offreST_operator = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        offreST_type = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        offer_checkBox = new javax.swing.JCheckBox();
        offer_value_text = new javax.swing.JSpinner();
        offer_realValueText = new javax.swing.JSpinner();
        jPanel26 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        usersTrader_Combo = new javax.swing.JComboBox<>();
        jLabel95 = new javax.swing.JLabel();
        username_Text = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        password_Text = new javax.swing.JPasswordField();
        jLabel97 = new javax.swing.JLabel();
        passwordConfirm_Text = new javax.swing.JPasswordField();
        jButton36 = new javax.swing.JButton();
        operator_panel_broker = new javax.swing.JButton();
        clients_panel_broker = new javax.swing.JButton();
        portpuce_panel_broker = new javax.swing.JButton();
        offers_panel_broker = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        users_panel_broker = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Cambria Math", 1, 18)); // NOI18N

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder("Les 10 dernières transactions TopUp : "));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
        );

        globaleSolde_Label.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        globaleSolde_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        globaleSolde_Label.setText("------------- DA");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Solde global");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setText("Montant total transféré des transactions TopUp:");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setText("Montant total débité des transactions TopUp:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setText("Montant total reçu des transactions TopUp:");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("------------ DA");
        jLabel40.setToolTipText("");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("------------ DA");
        jLabel41.setToolTipText("");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("------------ DA");
        jLabel42.setToolTipText("");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(globaleSolde_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(globaleSolde_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Djezzy", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        djezzyChipsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Puces Djezzy"));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Solde global : ");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Total des transferts :");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Total des débits :");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("total des recharges :");

        globaleSoldeDjezzy_Label.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        globaleSoldeDjezzy_Label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        globaleSoldeDjezzy_Label.setText("------------------ DA");

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel58.setText("------------------ DA");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel59.setText("------------------ DA");

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel60.setText("------------------ DA");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(djezzyChipsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(globaleSoldeDjezzy_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(globaleSoldeDjezzy_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(djezzyChipsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ooredoo", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        OoredooChipsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Puces Ooredoo"));

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("Solde global : ");

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("Total des transferts :");

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Total des débits :");

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("total des recharges :");

        globaleSoldeOoredoo_Label.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        globaleSoldeOoredoo_Label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        globaleSoldeOoredoo_Label.setText("------------------ DA");

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel66.setText("------------------ DA");

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel67.setText("------------------ DA");

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel68.setText("------------------ DA");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OoredooChipsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(globaleSoldeOoredoo_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel67, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(globaleSoldeOoredoo_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(OoredooChipsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
        );

        jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mobilis", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        mobilisChipsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Puces Mobilis"));

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Solde global : ");

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("Total des transferts :");

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Total des débits :");

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("total des recharges :");

        globaleSoldeMobilis_Label.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        globaleSoldeMobilis_Label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        globaleSoldeMobilis_Label.setText("------------------ DA");

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel74.setText("------------------ DA");

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel75.setText("------------------ DA");

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel76.setText("------------------ DA");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mobilisChipsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel72, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(globaleSoldeMobilis_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(globaleSoldeMobilis_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(mobilisChipsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("          Tableau de bord          ", jPanel6);

        ooredoo_panel_broker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ooredoo_panel_brokerActionPerformed(evt);
            }
        });

        Mobilis_panel_broker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mobilis_panel_brokerActionPerformed(evt);
            }
        });

        djezzy_panel_broker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                djezzy_panel_brokerActionPerformed(evt);
            }
        });

        RC_TabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        RC_TabbedPane.setEnabled(false);

        jPanel5.setAutoscrolls(true);

        djezzy_1.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_1.setText("1");

        djezzy_0.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_0.setText("0");

        djezzy_6.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_6.setText("6");

        djezzy_7.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_7.setText("7");

        djezzy_delete_button.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        djezzy_delete_button.setText("DEL");

        djezzy_4.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_4.setText("4");

        djezzy_8.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_8.setText("8");

        djezzy_init_button.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        djezzy_init_button.setText("INIT");
        djezzy_init_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                djezzy_init_buttonActionPerformed(evt);
            }
        });

        djezzy_2.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_2.setText("2");

        djezzy_9.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_9.setText("9");

        djezzy_3.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_3.setText("3");

        djezzy_5.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_5.setText("5");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(djezzy_7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(djezzy_8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(djezzy_4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(djezzy_5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(djezzy_1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(djezzy_2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(djezzy_delete_button, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(djezzy_0, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(djezzy_9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_init_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(djezzy_7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(djezzy_6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(djezzy_5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(djezzy_4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(djezzy_1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(djezzy_0, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_init_button, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_delete_button, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        djezzy_show_panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Offres Djezzy"));

        djezzy_offers_IconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        djezzy_offers_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton2.setText("100");
        jButton2.setPreferredSize(new java.awt.Dimension(70, 30));
        djezzy_offers_panel.add(jButton2);

        jButton3.setText("200");
        jButton3.setPreferredSize(new java.awt.Dimension(70, 30));
        djezzy_offers_panel.add(jButton3);

        jButton4.setText("500");
        jButton4.setPreferredSize(new java.awt.Dimension(70, 30));
        djezzy_offers_panel.add(jButton4);

        jButton5.setText("1000");
        jButton5.setPreferredSize(new java.awt.Dimension(70, 30));
        djezzy_offers_panel.add(jButton5);

        jButton6.setText("1500");
        jButton6.setPreferredSize(new java.awt.Dimension(70, 30));
        djezzy_offers_panel.add(jButton6);

        jButton7.setText("2000");
        jButton7.setPreferredSize(new java.awt.Dimension(70, 30));
        djezzy_offers_panel.add(jButton7);

        jButton8.setText("2500");
        jButton8.setPreferredSize(new java.awt.Dimension(70, 30));
        djezzy_offers_panel.add(jButton8);

        jButton9.setText("3000");
        jButton9.setPreferredSize(new java.awt.Dimension(70, 30));
        djezzy_offers_panel.add(jButton9);

        try {
            djezzy_mobile_ftext.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("## - ## - ## - ## - ##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        djezzy_mobile_ftext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        djezzy_mobile_ftext.setFont(new java.awt.Font("Algerian", 1, 70)); // NOI18N
        djezzy_mobile_ftext.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                djezzy_mobile_ftextCaretUpdate(evt);
            }
        });
        djezzy_mobile_ftext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                djezzy_mobile_ftextActionPerformed(evt);
            }
        });
        djezzy_mobile_ftext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                djezzy_mobile_ftextKeyReleased(evt);
            }
        });

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("Montant : ");

        djezzy_amount_text.setFont(new java.awt.Font("Algerian", 1, 70)); // NOI18N
        djezzy_amount_text.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("DA.");

        jLabel31.setText("  Id client");

        jLabel32.setText("Nom et prénom");

        jLabel35.setText("Raison sociale");

        djezzy_ciompany_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                djezzy_ciompany_textActionPerformed(evt);
            }
        });

        djezzy_clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(djezzy_clientTable);

        javax.swing.GroupLayout djezzy_show_panelLayout = new javax.swing.GroupLayout(djezzy_show_panel);
        djezzy_show_panel.setLayout(djezzy_show_panelLayout);
        djezzy_show_panelLayout.setHorizontalGroup(
            djezzy_show_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(djezzy_show_panelLayout.createSequentialGroup()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(djezzy_amount_text)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(djezzy_mobile_ftext, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(djezzy_show_panelLayout.createSequentialGroup()
                .addComponent(djezzy_offers_IconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(djezzy_offers_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(djezzy_show_panelLayout.createSequentialGroup()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(djezzy_idclient_text, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(djezzy_clientname_text, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(djezzy_ciompany_text, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
            .addComponent(jScrollPane6)
        );
        djezzy_show_panelLayout.setVerticalGroup(
            djezzy_show_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(djezzy_show_panelLayout.createSequentialGroup()
                .addGroup(djezzy_show_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(djezzy_offers_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(djezzy_offers_IconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(djezzy_mobile_ftext, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(djezzy_show_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(djezzy_amount_text, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(djezzy_show_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(djezzy_show_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(djezzy_idclient_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(djezzy_clientname_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(djezzy_ciompany_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addContainerGap())
        );

        djezzy_configureOffer_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        djezzy_configureOffer_button.setText("Configurer les offres");

        djezzy_checkSodle_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        djezzy_checkSodle_button.setText("Vérifier les soldes");
        djezzy_checkSodle_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                djezzy_checkSodle_buttonActionPerformed(evt);
            }
        });

        djezzy_addOffer_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        djezzy_addOffer_button.setText("Ajouter un offre");
        djezzy_addOffer_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                djezzy_addOffer_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(djezzy_configureOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_checkSodle_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_addOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(djezzy_addOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(djezzy_configureOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(djezzy_checkSodle_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        djezzy_updateClient_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        djezzy_updateClient_button.setText("Modifier un client");

        djezzy_addClient_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        djezzy_addClient_button.setText("Nouveau Client");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(djezzy_updateClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_addClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(djezzy_addClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(djezzy_updateClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        djezzy_send_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        djezzy_send_button.setText("Envoyer");
        djezzy_send_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                djezzy_send_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(djezzy_show_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(djezzy_send_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(djezzy_send_button, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(djezzy_show_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        RC_TabbedPane.addTab("Djezzy", jPanel5);

        jPanel10.setAutoscrolls(true);

        mobilis_1.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        mobilis_1.setText("1");

        mobilis_0.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        mobilis_0.setText("0");

        mobilis_6.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        mobilis_6.setText("6");

        mobilis_7.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        mobilis_7.setText("7");

        mobilis_delete_button.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        mobilis_delete_button.setText("DEL");

        mobilis_4.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        mobilis_4.setText("4");

        mobilis_8.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        mobilis_8.setText("8");

        mobilis_init_button.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        mobilis_init_button.setText("INIT");

        mobilis_2.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        mobilis_2.setText("2");

        mobilis_9.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        mobilis_9.setText("9");

        mobilis_3.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        mobilis_3.setText("3");

        mobilis_5.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        mobilis_5.setText("5");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(mobilis_7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mobilis_8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(mobilis_4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mobilis_5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(mobilis_1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mobilis_2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(mobilis_delete_button, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mobilis_0, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mobilis_9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_init_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mobilis_7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mobilis_6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(mobilis_5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(mobilis_4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mobilis_1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mobilis_0, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_init_button, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_delete_button, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        djezzy_show_panel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Offres Mobilis"));

        Mobilis_offers_IconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        mobilis_offers_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton16.setText("100");
        jButton16.setPreferredSize(new java.awt.Dimension(70, 30));
        mobilis_offers_panel.add(jButton16);

        jButton17.setText("200");
        jButton17.setPreferredSize(new java.awt.Dimension(70, 30));
        mobilis_offers_panel.add(jButton17);

        jButton18.setText("500");
        jButton18.setPreferredSize(new java.awt.Dimension(70, 30));
        mobilis_offers_panel.add(jButton18);

        jButton19.setText("1000");
        jButton19.setPreferredSize(new java.awt.Dimension(70, 30));
        mobilis_offers_panel.add(jButton19);

        jButton20.setText("1500");
        jButton20.setPreferredSize(new java.awt.Dimension(70, 30));
        mobilis_offers_panel.add(jButton20);

        jButton21.setText("2000");
        jButton21.setPreferredSize(new java.awt.Dimension(70, 30));
        mobilis_offers_panel.add(jButton21);

        jButton22.setText("2500");
        jButton22.setPreferredSize(new java.awt.Dimension(70, 30));
        mobilis_offers_panel.add(jButton22);

        jButton23.setText("3000");
        jButton23.setPreferredSize(new java.awt.Dimension(70, 30));
        mobilis_offers_panel.add(jButton23);

        try {
            mobilis_mobile_ftext.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("## - ## - ## - ## - ##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        mobilis_mobile_ftext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mobilis_mobile_ftext.setFont(new java.awt.Font("Algerian", 1, 70)); // NOI18N
        mobilis_mobile_ftext.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                mobilis_mobile_ftextCaretUpdate(evt);
            }
        });
        mobilis_mobile_ftext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobilis_mobile_ftextActionPerformed(evt);
            }
        });
        mobilis_mobile_ftext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mobilis_mobile_ftextKeyReleased(evt);
            }
        });

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel46.setText("Montant : ");

        mobilis_amount_text.setFont(new java.awt.Font("Algerian", 1, 70)); // NOI18N
        mobilis_amount_text.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("DA.");

        jLabel48.setText("  Id client");

        jLabel49.setText("Nom et prénom");

        jLabel50.setText("Raison sociale");

        mobilis_ciompany_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobilis_ciompany_textActionPerformed(evt);
            }
        });

        mobilis_clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(mobilis_clientTable);

        javax.swing.GroupLayout djezzy_show_panel3Layout = new javax.swing.GroupLayout(djezzy_show_panel3);
        djezzy_show_panel3.setLayout(djezzy_show_panel3Layout);
        djezzy_show_panel3Layout.setHorizontalGroup(
            djezzy_show_panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(djezzy_show_panel3Layout.createSequentialGroup()
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mobilis_amount_text)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(mobilis_mobile_ftext, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(djezzy_show_panel3Layout.createSequentialGroup()
                .addComponent(Mobilis_offers_IconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mobilis_offers_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(djezzy_show_panel3Layout.createSequentialGroup()
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mobilis_idclient_text, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mobilis_clientname_text, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mobilis_ciompany_text, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
            .addComponent(jScrollPane9)
        );
        djezzy_show_panel3Layout.setVerticalGroup(
            djezzy_show_panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(djezzy_show_panel3Layout.createSequentialGroup()
                .addGroup(djezzy_show_panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mobilis_offers_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(Mobilis_offers_IconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(mobilis_mobile_ftext, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(djezzy_show_panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mobilis_amount_text, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(djezzy_show_panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(djezzy_show_panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mobilis_idclient_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mobilis_clientname_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mobilis_ciompany_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addContainerGap())
        );

        mobilis_configureOffer_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mobilis_configureOffer_button.setText("Configurer les offres");

        mobilis_checkSodle_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mobilis_checkSodle_button.setText("Vérifier les soldes");
        mobilis_checkSodle_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobilis_checkSodle_buttonActionPerformed(evt);
            }
        });

        mobilis_addOffer_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mobilis_addOffer_button.setText("Ajouter un offre");
        mobilis_addOffer_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobilis_addOffer_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mobilis_configureOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_checkSodle_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_addOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(mobilis_addOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mobilis_configureOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mobilis_checkSodle_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        mobilis_updateClient_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mobilis_updateClient_button.setText("Modifier un client");

        mobilis_addClient_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mobilis_addClient_button.setText("Nouveau Client");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mobilis_updateClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobilis_addClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(mobilis_addClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(mobilis_updateClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        mobilis_send_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mobilis_send_button.setText("Envoyer");
        mobilis_send_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobilis_send_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(djezzy_show_panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mobilis_send_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mobilis_send_button, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(djezzy_show_panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        RC_TabbedPane.addTab("Mobilis", jPanel10);

        jPanel18.setAutoscrolls(true);

        djezzy_40.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_40.setText("1");

        djezzy_41.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_41.setText("0");

        djezzy_42.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_42.setText("6");

        djezzy_43.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_43.setText("7");

        djezzy_delete_button4.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        djezzy_delete_button4.setText("DEL");

        djezzy_44.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_44.setText("4");

        djezzy_45.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_45.setText("8");

        djezzy_init_button4.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        djezzy_init_button4.setText("INIT");

        djezzy_46.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_46.setText("2");

        djezzy_47.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_47.setText("9");

        djezzy_48.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_48.setText("3");

        djezzy_49.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        djezzy_49.setText("5");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(djezzy_43, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(djezzy_45, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addComponent(djezzy_44, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(djezzy_49, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addComponent(djezzy_40, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(djezzy_46, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(djezzy_delete_button4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(djezzy_41, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(djezzy_47, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_48, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_init_button4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(djezzy_43, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_45, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_47, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(djezzy_42, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(djezzy_49, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(djezzy_44, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(djezzy_40, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_46, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_48, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(djezzy_41, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_init_button4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(djezzy_delete_button4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        djezzy_show_panel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Offres Ooredoo"));

        ooredoo_offers_IconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        ooredoo_offers_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton24.setText("100");
        jButton24.setPreferredSize(new java.awt.Dimension(70, 30));
        ooredoo_offers_panel.add(jButton24);

        jButton25.setText("200");
        jButton25.setPreferredSize(new java.awt.Dimension(70, 30));
        ooredoo_offers_panel.add(jButton25);

        jButton26.setText("500");
        jButton26.setPreferredSize(new java.awt.Dimension(70, 30));
        ooredoo_offers_panel.add(jButton26);

        jButton27.setText("1000");
        jButton27.setPreferredSize(new java.awt.Dimension(70, 30));
        ooredoo_offers_panel.add(jButton27);

        jButton28.setText("1500");
        jButton28.setPreferredSize(new java.awt.Dimension(70, 30));
        ooredoo_offers_panel.add(jButton28);

        jButton29.setText("2000");
        jButton29.setPreferredSize(new java.awt.Dimension(70, 30));
        ooredoo_offers_panel.add(jButton29);

        jButton30.setText("2500");
        jButton30.setPreferredSize(new java.awt.Dimension(70, 30));
        ooredoo_offers_panel.add(jButton30);

        jButton31.setText("3000");
        jButton31.setPreferredSize(new java.awt.Dimension(70, 30));
        ooredoo_offers_panel.add(jButton31);

        try {
            ooredoo_mobile_ftext.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("## - ## - ## - ## - ##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ooredoo_mobile_ftext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ooredoo_mobile_ftext.setFont(new java.awt.Font("Algerian", 1, 70)); // NOI18N
        ooredoo_mobile_ftext.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                ooredoo_mobile_ftextCaretUpdate(evt);
            }
        });
        ooredoo_mobile_ftext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ooredoo_mobile_ftextActionPerformed(evt);
            }
        });
        ooredoo_mobile_ftext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ooredoo_mobile_ftextKeyReleased(evt);
            }
        });

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel51.setText("Montant : ");

        ooredoo_amount_text.setFont(new java.awt.Font("Algerian", 1, 70)); // NOI18N
        ooredoo_amount_text.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("DA.");

        jLabel53.setText("  Id client");

        jLabel54.setText("Nom et prénom");

        jLabel55.setText("Raison sociale");

        ooredoo_ciompany_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ooredoo_ciompany_textActionPerformed(evt);
            }
        });

        ooredoo_clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(ooredoo_clientTable);

        javax.swing.GroupLayout djezzy_show_panel4Layout = new javax.swing.GroupLayout(djezzy_show_panel4);
        djezzy_show_panel4.setLayout(djezzy_show_panel4Layout);
        djezzy_show_panel4Layout.setHorizontalGroup(
            djezzy_show_panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(djezzy_show_panel4Layout.createSequentialGroup()
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ooredoo_amount_text)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(ooredoo_mobile_ftext, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(djezzy_show_panel4Layout.createSequentialGroup()
                .addComponent(ooredoo_offers_IconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ooredoo_offers_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(djezzy_show_panel4Layout.createSequentialGroup()
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ooredoo_idclient_text, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ooredoo_clientname_text, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ooredoo_ciompany_text, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
            .addComponent(jScrollPane10)
        );
        djezzy_show_panel4Layout.setVerticalGroup(
            djezzy_show_panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(djezzy_show_panel4Layout.createSequentialGroup()
                .addGroup(djezzy_show_panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ooredoo_offers_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(ooredoo_offers_IconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(ooredoo_mobile_ftext, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(djezzy_show_panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ooredoo_amount_text, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(djezzy_show_panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(djezzy_show_panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ooredoo_idclient_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ooredoo_clientname_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ooredoo_ciompany_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addContainerGap())
        );

        ooredoo_configureOffer_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ooredoo_configureOffer_button.setText("Configurer les offres");

        ooredoo_checkSodle_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ooredoo_checkSodle_button.setText("Vérifier les soldes");
        ooredoo_checkSodle_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ooredoo_checkSodle_buttonActionPerformed(evt);
            }
        });

        ooredoo_addOffer_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ooredoo_addOffer_button.setText("Ajouter un offre");
        ooredoo_addOffer_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ooredoo_addOffer_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ooredoo_configureOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ooredoo_checkSodle_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ooredoo_addOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(ooredoo_addOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ooredoo_configureOffer_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ooredoo_checkSodle_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        ooredoo_updateClient_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ooredoo_updateClient_button.setText("Modifier un client");

        ooredoo_addClient_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ooredoo_addClient_button.setText("Nouveau Client");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ooredoo_updateClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ooredoo_addClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(ooredoo_addClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ooredoo_updateClient_button, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        ooredoo_send_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ooredoo_send_button.setText("Envoyer");
        ooredoo_send_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ooredoo_send_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(djezzy_show_panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ooredoo_send_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ooredoo_send_button, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(djezzy_show_panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        RC_TabbedPane.addTab("Ooredoo", jPanel18);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(300, Short.MAX_VALUE)
                .addComponent(djezzy_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(Mobilis_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(ooredoo_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(301, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(RC_TabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ooredoo_panel_broker, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(djezzy_panel_broker, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Mobilis_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RC_TabbedPane))
        );

        jTabbedPane1.addTab("          Recharge Mobile          ", jPanel1);

        jPanel2.setPreferredSize(new java.awt.Dimension(150, 440));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ports");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Auto-Vérification");
        jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Numéros de puces");
        jLabel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Opérateurs");
        jLabel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Status");
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Vérification");
        jLabel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153)));

        port_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        port_panel.setPreferredSize(new java.awt.Dimension(150, 500));

        jButton1.setText("Verifier Tous les ports");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        operator_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        operator_panel.setPreferredSize(new java.awt.Dimension(150, 500));

        status_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        status_panel.setPreferredSize(new java.awt.Dimension(150, 500));

        number_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        number_panel.setPreferredSize(new java.awt.Dimension(150, 500));

        check_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        check_panel.setPreferredSize(new java.awt.Dimension(150, 500));

        autocheck_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        autocheck_panel.setPreferredSize(new java.awt.Dimension(150, 500));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(port_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(operator_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(number_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(autocheck_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(status_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(check_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(407, 407, 407)
                        .addComponent(jButton1)))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(port_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(operator_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(check_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(status_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(autocheck_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(number_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        jTabbedPane1.addTab("          Configuration          ", jPanel2);

        config_TabbedPanel.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        config_TabbedPanel.setEnabled(false);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mobilis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Djezzy", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ooredoo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        config_TabbedPanel.addTab("Opérateurs", jPanel13);

        jLabel80.setText("Catégorie du client:");

        jCheckBox1.setText("Distributeur");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel81.setText("Raison sociale: ");

        jLabel82.setText("Nom :");

        jLabel83.setText("Prénom : ");

        jLabel84.setText("Numéro de puce : ");

        jLabel85.setText("Adresse : ");

        jLabel86.setText("Wilaya :");

        jLabel87.setText("Commune : ");

        jLabel88.setText("Email 1 :");

        jLabel89.setText("Email 2 :");

        jLabel90.setText("Tel 1 :");

        jLabel91.setText("Tel 2 : ");

        jButton34.setText("Réinitialiser");

        jButton35.setText("Ajouter");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jLabel92.setText("Fournisseur :");

        jLabel93.setText("Type :");

        javax.swing.GroupLayout traderAddCompanyNameLayout = new javax.swing.GroupLayout(traderAddCompanyName);
        traderAddCompanyName.setLayout(traderAddCompanyNameLayout);
        traderAddCompanyNameLayout.setHorizontalGroup(
            traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(providerAddTrader_Combo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel93)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(traderType_Combo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59))
                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                        .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Tel2_Text))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Tel1_Text))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(email1_Text))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(commune_Text))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Wilaya_Text))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Adress_Text))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(mobileNumber_Text))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Fname_Text))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(traderCategory_Combo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(73, 73, 73)
                                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(comapnyName_Text))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Lname_Text))
                                    .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                                        .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(email2_Text))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, traderAddCompanyNameLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        traderAddCompanyNameLayout.setVerticalGroup(
            traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(traderAddCompanyNameLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel93)
                        .addComponent(traderType_Combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel92)
                        .addComponent(providerAddTrader_Combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel80)
                    .addComponent(traderCategory_Combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comapnyName_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lname_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Fname_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobileNumber_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Adress_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Wilaya_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(commune_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email1_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email2_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tel1_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tel2_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(traderAddCompanyNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton34, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jButton35, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addContainerGap())
        );

        allTradersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(allTradersTable);

        jButton32.setText("Rechercher");

        jLabel77.setText("Raison sociale:");

        jLabel78.setText("Nom :");

        jLabel79.setText("Prénom : ");

        jButton33.setText("Modifier");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(traderAddCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2))
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                .addComponent(jButton32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                .addComponent(jButton33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                    .addComponent(traderAddCompanyName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        config_TabbedPanel.addTab("Client", jPanel4);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        config_TabbedPanel.addTab("Puces & Ports", jPanel8);

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Offres statiques"));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Avant le numéro de téléphone:   ");

        jLabel13.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("0XXXXXXXXX");

        jLabel14.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("XXXX");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Après le numéro de téléphone et avant de code PIN :   ");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Après le code PIN:");

        offresST_beforeNum.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        offresST_beforeNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        offresST_betweenNumPin.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        offresST_betweenNumPin.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        offresST_AfterPIN.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        offresST_AfterPIN.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton10.setText("Ajouter ");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Réinitialiser");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(offresST_AfterPIN)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(offresST_betweenNumPin)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(offresST_beforeNum)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jLabel12)
                .addGap(1, 1, 1)
                .addComponent(offresST_beforeNum, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel15)
                .addGap(1, 1, 1)
                .addComponent(offresST_betweenNumPin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel16)
                .addGap(1, 1, 1)
                .addComponent(offresST_AfterPIN, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder("Offres dynamiques"));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Avant le numéro de téléphone:   ");

        jLabel19.setFont(new java.awt.Font("Algerian", 1, 22)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("0XXXXXXXXX");

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Après le numéro de téléphone et avant de code PIN :   ");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Après le code PIN:");

        jButton12.setText("Ajouter ");

        jButton13.setText("Réinitialiser");

        offreDYN_TableBeforeNum.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Avant", "Description du champ", "Après"
            }
        ));
        jScrollPane2.setViewportView(offreDYN_TableBeforeNum);
        if (offreDYN_TableBeforeNum.getColumnModel().getColumnCount() > 0) {
            offreDYN_TableBeforeNum.getColumnModel().getColumn(0).setResizable(false);
            offreDYN_TableBeforeNum.getColumnModel().getColumn(0).setPreferredWidth(15);
            offreDYN_TableBeforeNum.getColumnModel().getColumn(1).setResizable(false);
            offreDYN_TableBeforeNum.getColumnModel().getColumn(2).setResizable(false);
            offreDYN_TableBeforeNum.getColumnModel().getColumn(2).setPreferredWidth(15);
        }

        offreDYN_TableBetweenNumPin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Avant", "Description du champ", "Après"
            }
        ));
        jScrollPane3.setViewportView(offreDYN_TableBetweenNumPin);
        if (offreDYN_TableBetweenNumPin.getColumnModel().getColumnCount() > 0) {
            offreDYN_TableBetweenNumPin.getColumnModel().getColumn(0).setResizable(false);
            offreDYN_TableBetweenNumPin.getColumnModel().getColumn(0).setPreferredWidth(15);
            offreDYN_TableBetweenNumPin.getColumnModel().getColumn(2).setResizable(false);
            offreDYN_TableBetweenNumPin.getColumnModel().getColumn(2).setPreferredWidth(15);
        }

        jLabel23.setFont(new java.awt.Font("Algerian", 1, 22)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("XXXX");

        jLabel24.setText("Nombre de champs:");

        offreDYN_NbBeforeNum.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        offreDYN_NbBeforeNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton14.setText("OK");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel25.setText("Nombre de champs:");

        offreDYN_NbBetweenNumPin.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        offreDYN_NbBetweenNumPin.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton15.setText("OK");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        offresDYN_AfterPIN.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        offresDYN_AfterPIN.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                .addGap(8, 8, 8))
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(offreDYN_NbBeforeNum)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel24Layout.createSequentialGroup()
                                        .addComponent(offreDYN_NbBetweenNumPin)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel24Layout.createSequentialGroup()
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel24Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(offresDYN_AfterPIN, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel24))
                .addGap(6, 6, 6)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(offreDYN_NbBeforeNum, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(offreDYN_NbBetweenNumPin, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(5, 5, 5)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addGap(5, 5, 5)
                .addComponent(offresDYN_AfterPIN, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel11.setText("Opérateur :   ");

        jLabel20.setText("Type :   ");

        jLabel27.setText("Nom de l'offre: ");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel28.setText("Montant transféré: ");

        jLabel29.setText("Montant débité: ");

        jLabel30.setText("Montant indéfini :");

        offer_checkBox.setText("n'importe quel montant");
        offer_checkBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                offer_checkBoxStateChanged(evt);
            }
        });

        offer_value_text.setModel(new javax.swing.SpinnerNumberModel());

        offer_realValueText.setModel(new javax.swing.SpinnerNumberModel());

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(jLabel27)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(offer_checkBox)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(offreST_operator, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(offreST_type, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(offer_value_text)
                                    .addComponent(offer_realValueText))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(20, 20, 20)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(offreST_operator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(offreST_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel27)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(offer_value_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(offer_realValueText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(offer_checkBox))
                        .addGap(5, 5, 5)
                        .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        config_TabbedPanel.addTab("Offres", jPanel9);

        jLabel94.setText("Client :");

        usersTrader_Combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersTrader_ComboActionPerformed(evt);
            }
        });

        jLabel95.setText("Nom d'utilisateur:");

        jLabel96.setText("Mot de passe:");

        jLabel97.setText("Confirmation du Mot de passe :");

        jButton36.setText("Enregistrer");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(username_Text))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(usersTrader_Combo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passwordConfirm_Text))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(password_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94)
                    .addComponent(usersTrader_Combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel95)
                    .addComponent(username_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel96)
                    .addComponent(password_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel97)
                    .addComponent(passwordConfirm_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap(373, Short.MAX_VALUE)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        config_TabbedPanel.addTab("Utilisateurs", jPanel26);

        operator_panel_broker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operator_panel_brokerActionPerformed(evt);
            }
        });

        clients_panel_broker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clients_panel_brokerActionPerformed(evt);
            }
        });

        portpuce_panel_broker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portpuce_panel_brokerActionPerformed(evt);
            }
        });

        offers_panel_broker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offers_panel_brokerActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Opérateurs");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Clients");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Puces & ports");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Offres");

        users_panel_broker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                users_panel_brokerActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Utilisateurs");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(config_TabbedPanel)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(operator_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clients_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(portpuce_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(offers_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(users_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(operator_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clients_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(portpuce_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(offers_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(users_panel_broker, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(config_TabbedPanel))
        );

        jTabbedPane1.addTab("          Paramètres & configuration          ", jPanel7);

        jMenu1.setText("Fichier");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Fonctionnalités");

        jMenu4.setText("Recharge Mobile");

        jMenuItem1.setText("Recharge Djezzy");
        jMenu4.add(jMenuItem1);

        jMenuItem2.setText("Recharge Ooredoo");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuItem3.setText("Recharge Mobilis");
        jMenu4.add(jMenuItem3);

        jMenu2.add(jMenu4);

        jMenuItem4.setText("Configuration des modems");
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Rapports et statistiques");
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("?");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        try {
            int width_comp = 21;
            Font font = new Font(Font.SERIF, Font.BOLD, 16);
            MaskFormatter formatter;
            formatter = new MaskFormatter("##-##-##-##-##");

            JFormattedTextField t2 = new JFormattedTextField(formatter);
            JTextField t1 = new JTextField(10);
            //JTextField t2 = new JTextField(10);
            JComboBox bop;
            JCheckBox auCh;
            JButton btn_check;
            JButton btn;
            JLabel stt;
            JLabel separator_label = new JLabel("--------------------");
            separator_label.setPreferredSize(new Dimension(120, 5));

            Vector vop = new Vector();
            vop.add("-------------");
            vop.add("Djezzy");
            vop.add("Ooredoo");
            vop.add("Mobilis");
            //t1.setPreferredSize(new Dimension(20,100));
            for (int i = 0; i < 16; i++) {
                t1 = new JTextField("COM XX");
                t1.setPreferredSize(new Dimension(120, width_comp));
                //t2 = new JTextField();
                t2 = new JFormattedTextField(formatter);
                t2.setFont(font);
                t2.setText("0777007700");
                t2.setPreferredSize(new Dimension(120, width_comp));
                bop = new JComboBox(vop);
                bop.setPreferredSize(new Dimension(120, width_comp));
                auCh = new JCheckBox();
                auCh.setPreferredSize(new Dimension(20, width_comp));
                btn_check = new JButton("Cocher");
                btn_check.setPreferredSize(new Dimension(90, width_comp));
                btn = new JButton("Vérifier COM XX");
                btn.setPreferredSize(new Dimension(120, width_comp));
                stt = new JLabel();
                stt.setAlignmentY(CENTER_ALIGNMENT);
                stt.setAlignmentX(CENTER_ALIGNMENT);
                stt.setPreferredSize(new Dimension(120, width_comp));
                new Thread(new PortChecker(1, stt)).start();
                port_panel.add(t1);
                operator_panel.add(bop);
                number_panel.add(t2);
                autocheck_panel.add(auCh);
                autocheck_panel.add(btn_check);
                status_panel.add(stt);
                check_panel.add(btn);
                separator_label = new JLabel("--------------------------------------------");
                separator_label.setAlignmentY(CENTER_ALIGNMENT);
                separator_label.setPreferredSize(new Dimension(150, 3));
                port_panel.add(separator_label);
                separator_label = new JLabel("--------------------------------------------");
                separator_label.setAlignmentY(CENTER_ALIGNMENT);
                separator_label.setPreferredSize(new Dimension(150, 3));
                operator_panel.add(separator_label);
                separator_label = new JLabel("--------------------------------------------");
                separator_label.setAlignmentY(CENTER_ALIGNMENT);
                separator_label.setPreferredSize(new Dimension(150, 3));
                number_panel.add(separator_label);
                separator_label = new JLabel("--------------------------------------------");
                separator_label.setAlignmentY(CENTER_ALIGNMENT);
                separator_label.setPreferredSize(new Dimension(150, 3));
                autocheck_panel.add(separator_label);
                separator_label = new JLabel("--------------------------------------------");
                separator_label.setAlignmentY(CENTER_ALIGNMENT);
                separator_label.setPreferredSize(new Dimension(150, 3));
                check_panel.add(separator_label);
                separator_label = new JLabel("--------------------------------------------");
                separator_label.setAlignmentY(CENTER_ALIGNMENT);
                separator_label.setPreferredSize(new Dimension(150, 3));
                status_panel.add(separator_label);
            }
            pack();
        } catch (ParseException ex) {
            Logger.getLogger(main_interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void djezzy_addOffer_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_djezzy_addOffer_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_djezzy_addOffer_buttonActionPerformed

    private void djezzy_checkSodle_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_djezzy_checkSodle_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_djezzy_checkSodle_buttonActionPerformed

    private void djezzy_mobile_ftextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_djezzy_mobile_ftextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_djezzy_mobile_ftextActionPerformed

    private void djezzy_ciompany_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_djezzy_ciompany_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_djezzy_ciompany_textActionPerformed

    private void mobilis_mobile_ftextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobilis_mobile_ftextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobilis_mobile_ftextActionPerformed

    private void mobilis_ciompany_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobilis_ciompany_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobilis_ciompany_textActionPerformed

    private void mobilis_checkSodle_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobilis_checkSodle_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobilis_checkSodle_buttonActionPerformed

    private void mobilis_addOffer_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobilis_addOffer_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobilis_addOffer_buttonActionPerformed

    private void ooredoo_mobile_ftextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ooredoo_mobile_ftextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ooredoo_mobile_ftextActionPerformed

    private void ooredoo_ciompany_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ooredoo_ciompany_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ooredoo_ciompany_textActionPerformed

    private void ooredoo_checkSodle_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ooredoo_checkSodle_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ooredoo_checkSodle_buttonActionPerformed

    private void ooredoo_addOffer_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ooredoo_addOffer_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ooredoo_addOffer_buttonActionPerformed

    private void djezzy_panel_brokerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_djezzy_panel_brokerActionPerformed
        // TODO add your handling code here:
        RC_TabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_djezzy_panel_brokerActionPerformed

    private void Mobilis_panel_brokerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mobilis_panel_brokerActionPerformed
        // TODO add your handling code here:
        RC_TabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_Mobilis_panel_brokerActionPerformed

    private void ooredoo_panel_brokerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ooredoo_panel_brokerActionPerformed
        // TODO add your handling code here:
        RC_TabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_ooredoo_panel_brokerActionPerformed

    private void djezzy_send_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_djezzy_send_buttonActionPerformed
        // TODO add your handling code here:                
        for (Sim currentSim : intermediate_process.avaiableSims) {
            if (currentSim.getNameOperator().toUpperCase().equals(DbResult.OPERATOR_DJEZZY)) {
                System.out.println("PORT NAME: " + currentSim.getPortCom());
                SerialPort port = SerialPort.getCommPort(currentSim.getPortCom());
                Utils.println("port initialisé avec succes ");
                Gsm gsm = new Gsm(port, currentSim);
                gsm.executeTopupOperation(
                        DbResult.getTopupUSSDCodeFromDb(DbResult.OPERATOR_DJEZZY),
                        djezzy_mobile_ftext.getText().replace(" - ", ""),
                        djezzy_amount_text.getText(),
                        DbResult.getPinUSSDCodeFromDb(DbResult.OPERATOR_DJEZZY));
                //gsm.executeGetOperatorNameOperation();
            }
        }
        System.err.println("'" + djezzy_mobile_ftext.getText() + "'  : " + djezzy_mobile_ftext.getText().length());
        System.err.println("'" + interProcess.standardizeMobileNumer(djezzy_mobile_ftext.getText()) + "'  : " + interProcess.standardizeMobileNumer(djezzy_mobile_ftext.getText()).length());
    }//GEN-LAST:event_djezzy_send_buttonActionPerformed


    private void djezzy_mobile_ftextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_djezzy_mobile_ftextCaretUpdate
        // TODO add your handling code here:

    }//GEN-LAST:event_djezzy_mobile_ftextCaretUpdate

    private void mobilis_mobile_ftextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_mobilis_mobile_ftextCaretUpdate
        // TODO add your handling code here:

    }//GEN-LAST:event_mobilis_mobile_ftextCaretUpdate

    private void ooredoo_mobile_ftextCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_ooredoo_mobile_ftextCaretUpdate
        // TODO add your handling code here:

    }//GEN-LAST:event_ooredoo_mobile_ftextCaretUpdate

    private void djezzy_init_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_djezzy_init_buttonActionPerformed
        // TODO add your handling code here:
        djezzy_mobile_ftext = new JFormattedTextField(modelMobileNumber);
    }//GEN-LAST:event_djezzy_init_buttonActionPerformed

    private void operator_panel_brokerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operator_panel_brokerActionPerformed
        // TODO add your handling code here:
        config_TabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_operator_panel_brokerActionPerformed

    private void clients_panel_brokerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clients_panel_brokerActionPerformed
        // TODO add your handling code here:
        config_TabbedPanel.setSelectedIndex(1);
    }//GEN-LAST:event_clients_panel_brokerActionPerformed

    private void portpuce_panel_brokerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portpuce_panel_brokerActionPerformed
        // TODO add your handling code here:
        config_TabbedPanel.setSelectedIndex(2);
    }//GEN-LAST:event_portpuce_panel_brokerActionPerformed

    private void offers_panel_brokerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_offers_panel_brokerActionPerformed
        // TODO add your handling code here:
        config_TabbedPanel.setSelectedIndex(3);
    }//GEN-LAST:event_offers_panel_brokerActionPerformed

    private void djezzy_mobile_ftextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_djezzy_mobile_ftextKeyReleased
        // TODO add your handling code here:
        if (interProcess.isDjezzyValidMobileNumber(djezzy_mobile_ftext.getText())) {
            djezzy_mobile_ftext.setBorder(okBorder);
            djezzy_send_button.setEnabled(true);
        } else {
            djezzy_mobile_ftext.setBorder(errorBorder);
            djezzy_send_button.setEnabled(false);
        }
    }//GEN-LAST:event_djezzy_mobile_ftextKeyReleased

    private void mobilis_mobile_ftextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mobilis_mobile_ftextKeyReleased
        // TODO add your handling code here:
        if (interProcess.isMobilisValidMobileNumber(mobilis_mobile_ftext.getText())) {
            mobilis_mobile_ftext.setBorder(okBorder);
            mobilis_send_button.setEnabled(true);
        } else {
            mobilis_mobile_ftext.setBorder(errorBorder);
            mobilis_send_button.setEnabled(false);
        }
    }//GEN-LAST:event_mobilis_mobile_ftextKeyReleased


    private void ooredoo_mobile_ftextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ooredoo_mobile_ftextKeyReleased
        // TODO add your handling code here:
        if (interProcess.isOoredooValidMobileNumber(ooredoo_mobile_ftext.getText())) {
            ooredoo_mobile_ftext.setBorder(okBorder);
            ooredoo_send_button.setEnabled(true);
        } else {
            ooredoo_mobile_ftext.setBorder(errorBorder);
            ooredoo_send_button.setEnabled(false);
        }
    }//GEN-LAST:event_ooredoo_mobile_ftextKeyReleased

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void offer_checkBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_offer_checkBoxStateChanged
        // TODO add your handling code here:
        if (offer_checkBox.isSelected()) {
            offer_value_text.setValue(0);
            offer_realValueText.setValue(0);

            offer_value_text.setEnabled(false);
            offer_realValueText.setEnabled(false);
        } else {
            offer_value_text.setEnabled(true);
            offer_realValueText.setEnabled(true);
        }
    }//GEN-LAST:event_offer_checkBoxStateChanged

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        int nbcolumn;
        try {
            nbcolumn = Integer.parseInt(offreDYN_NbBeforeNum.getText());
            uiVars.getOfferDynTableModel(offreDYN_TableBeforeNum, nbcolumn);
        } catch (Exception e) {
            e.printStackTrace();
            uiVars.defaultErrorMessage(this, "Erreur", "Veuillez entrer un nombre entier positive !!!");
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        int nbcolumn;
        try {
            nbcolumn = Integer.parseInt(offreDYN_NbBetweenNumPin.getText());
            uiVars.getOfferDynTableModel(offreDYN_TableBetweenNumPin, nbcolumn);
        } catch (Exception e) {
            e.printStackTrace();
            uiVars.defaultErrorMessage(this, "Erreur", "Veuillez entrer un nombre entier positive !!!");
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void users_panel_brokerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_users_panel_brokerActionPerformed
        // TODO add your handling code here:
        config_TabbedPanel.setSelectedIndex(4);
    }//GEN-LAST:event_users_panel_brokerActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
        new Thread() {
            public void run() {
                ProcessingDialog processDiag = new ProcessingDialog();
                try {

                    processDiag.setTitle("Ajout d'un nouveau client");
                    processDiag.setVisible(true);
                    ProcessingDialog.waiting_message.setText("Collecte des données fournies ...");

                    String ttype = traderType_Combo.getSelectedItem().toString();
                    String tcategory = traderCategory_Combo.getSelectedItem().toString();

                    String tcompany = comapnyName_Text.getText();
                    String tfname = Fname_Text.getText();
                    String tlname = Lname_Text.getText();
                    String tnumber = mobileNumber_Text.getText();
                    String tadress = Adress_Text.getText();
                    String twilaya = Wilaya_Text.getText();
                    String tcommune = commune_Text.getText();
                    String temail1 = email1_Text.getText();
                    String temail2 = email2_Text.getText();
                    String ttel1 = Tel1_Text.getText();
                    String ttel2 = Tel2_Text.getText();
                    Vector<Operator> operarorVect = new Vector<Operator>();
                    for (int i = 0; i < staticVars.actualUser.getOpUIVestor().size(); i++) {
                        operarorVect.add(staticVars.actualUser.getOpUIVestor().get(i).getOperaror());

                    }
                    int respeonse = -239;
                    ProcessingDialog.waiting_message.setText("Enregistrement des données ...");
                    if (jCheckBox1.isSelected()) {
                        respeonse = staticVars.requestBroker.addDistributor_forActualUser(staticVars.actualUser, ttype, tfname, tlname, tcompany, tnumber,
                                tadress, tcommune, twilaya, temail1, temail2, ttel1, ttel2, operarorVect);

                    } else {
                        String providerTrader = providerAddTrader_Combo.getSelectedItem().toString();
                        System.out.println(providerTrader);
                        System.out.println(providerTrader.substring(0, providerTrader.indexOf("(")));
                        int idParent = Integer.parseInt(providerTrader.substring(0, providerTrader.indexOf("(")));
                        System.out.println(idParent);
                        Vector<Double> limitSoldes = new Vector<Double>();
                        limitSoldes.add(-1.0);
                        limitSoldes.add(-1.0);
                        limitSoldes.add(-1.0);

                        respeonse = staticVars.requestBroker.addTrader_forActualUser(staticVars.actualUser, tcategory, ttype, tfname, tlname, tcompany, tnumber,
                                tadress, tcommune, twilaya, temail1, temail2, ttel1, ttel2, operarorVect, limitSoldes);
                    }

                    if (respeonse == staticVars.onGoingProcessOK) {
                        ProcessingDialog.waiting_message.setText("Mise à jour des données ...");
                        List possibleParents = new Trader_Util().getTrader_by_traderType_and_Category(staticVars.globalSession,
                                staticVars.actualUser.getTsUI().getTraderTypeByDesc(staticVars.traderType_Application),
                                staticVars.actualUser.getTsUI().getTraderCategoryByDesc(staticVars.traderCategory_Grossiste), "");
                        providerAddTrader_Combo.removeAllItems();
                        for (int i = 0; i < possibleParents.size(); i++) {
                            Trader get = (Trader) possibleParents.get(i);
                            providerAddTrader_Combo.addItem(String.valueOf(get.getIdtrader() + "(" + get.getTraderCompany() + ")"));

                        }
                        List traders = new Trader_Util().getAllTrader(staticVars.globalSession, "");
                        usersTrader_Combo.removeAllItems();
                        for (int i = 0; i < traders.size(); i++) {
                            Trader get = (Trader) traders.get(i);
                            usersTrader_Combo.addItem(String.valueOf(get.getIdtrader() + "(" + get.getTraderCompany() + ")"));

                        }
                        processDiag.dispose();
                        uiVars.defaultOKMessage(main_interface.this, "Confirmation", "Le client a été ajouté avec succés.");
                    } else {
                        processDiag.dispose();
                        uiVars.defaultErrorMessage(main_interface.this, "Erreur", "Erreur lors de l'ajout d'un nouveau Client!!!");
                    }

                    processDiag.dispose();
                } catch (Exception e) {
                    e.printStackTrace();

                    processDiag.dispose();
                    uiVars.defaultErrorMessage(main_interface.this, "Erreur", "Erreur lors de l'ajout d'un nouveau Client!!!");

                }
            }
        }.start();

    }//GEN-LAST:event_jButton35ActionPerformed

    private void usersTrader_ComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersTrader_ComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usersTrader_ComboActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:
        new Thread() {
            public void run() {
                ProcessingDialog processDiag = new ProcessingDialog();
                try {

                    processDiag.setTitle("Ajout d'un nouveau client");
                    processDiag.setVisible(true);
                    ProcessingDialog.waiting_message.setText("Collecte des données fournies ...");

                    String username = username_Text.getText();
                    String password = password_Text.getText();
                    String passwordConfirm = passwordConfirm_Text.getText();
                    if (!password.equals(passwordConfirm)) {
                        uiVars.defaultErrorMessage(main_interface.this, "Erreur", "Les mots de passe doivent être identiques!!!");
                    } else {
                        int response = -2534;
                        String providerTrader = providerAddTrader_Combo.getSelectedItem().toString();
                        System.out.println(providerTrader);
                        System.out.println(providerTrader.substring(0, providerTrader.indexOf("(")));
                        int idParent = Integer.parseInt(providerTrader.substring(0, providerTrader.indexOf("(")));
                        System.out.println(idParent);

                        response = staticVars.requestBroker.addUser(staticVars.actualUser, idParent, username, password, staticVars.userCategory_Administrateur);

                        if (response == staticVars.onGoingProcessOK) {
                            ProcessingDialog.waiting_message.setText("Mise à jour des données ...");
                            List possibleParents = new Trader_Util().getTrader_by_traderType_and_Category(staticVars.globalSession,
                                    staticVars.actualUser.getTsUI().getTraderTypeByDesc(staticVars.traderType_Application),
                                    staticVars.actualUser.getTsUI().getTraderCategoryByDesc(staticVars.traderCategory_Grossiste), "");
                            providerAddTrader_Combo.removeAllItems();
                            for (int i = 0; i < possibleParents.size(); i++) {
                                Trader get = (Trader) possibleParents.get(i);
                                providerAddTrader_Combo.addItem(String.valueOf(get.getIdtrader() + "(" + get.getTraderCompany() + ")"));

                            }
                            processDiag.dispose();
                            uiVars.defaultOKMessage(main_interface.this, "Confirmation", "L'utilisateur a été ajouté avec succés.");
                        } else {
                            processDiag.dispose();
                            uiVars.defaultErrorMessage(main_interface.this, "Erreur", "Erreur lors de l'ajout d'un nouveau utilisateur!!!");
                        }
                    }
                    processDiag.dispose();
                } catch (Exception e) {
                    e.printStackTrace();

                    processDiag.dispose();
                    uiVars.defaultErrorMessage(main_interface.this, "Erreur", "Erreur lors de l'ajout d'un nouveau Client!!!");

                }
            }
        }.start();

    }//GEN-LAST:event_jButton36ActionPerformed

    private void ooredoo_send_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ooredoo_send_buttonActionPerformed
        // TODO add your handling code here:                
        for (Sim currentSim : intermediate_process.avaiableSims) {
            if (currentSim.getNameOperator().toUpperCase().equals(DbResult.OPERATOR_OOREDOO)) {
                System.err.print("1 " + currentSim.getPortCom());
                System.out.println("PORT NAME: " + currentSim.getPortCom());
                SerialPort port = SerialPort.getCommPort(currentSim.getPortCom());
                port.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
                port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                if (port != null) {
                    if (port.isOpen() || port.openPort(1000)) {
                        Gsm gsm = new Gsm(port, currentSim);
                        gsm.executeTopupOperation(
                                DbResult.getTopupUSSDCodeFromDb(DbResult.OPERATOR_OOREDOO),
                                ooredoo_mobile_ftext.getText().replace(" - ", ""),
                                ooredoo_amount_text.getText().toString(),
                                DbResult.getPinUSSDCodeFromDb(DbResult.OPERATOR_OOREDOO));
                    }
                }

            }
        }
        System.err.println("'" + djezzy_mobile_ftext.getText() + "'  : " + djezzy_mobile_ftext.getText().length());
        System.err.println("'" + interProcess.standardizeMobileNumer(djezzy_mobile_ftext.getText()) + "'  : " + interProcess.standardizeMobileNumer(djezzy_mobile_ftext.getText()).length());
    }//GEN-LAST:event_ooredoo_send_buttonActionPerformed

    private void mobilis_send_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobilis_send_buttonActionPerformed
        // TODO add your handling code here:                
        for (Sim currentSim : intermediate_process.avaiableSims) {
            if (currentSim.getNameOperator().toUpperCase().equals(DbResult.OPERATOR_MOBILIS)) {
                SerialPort port = SerialPort.getCommPort(currentSim.getPortCom());
                Utils.println("port initialisé avec succes ");
                Gsm gsm = new Gsm(port, currentSim);
                gsm.executeTopupOperation(
                        DbResult.getTopupUSSDCodeFromDb(DbResult.OPERATOR_MOBILIS),
                        mobilis_mobile_ftext.getText().replace(" - ", ""),
                        mobilis_amount_text.getText(),
                        DbResult.getPinUSSDCodeFromDb(DbResult.OPERATOR_MOBILIS));
            }
        }
        System.err.println("'" + mobilis_mobile_ftext.getText() + "'  : " + mobilis_mobile_ftext.getText().length());
        System.err.println("'" + interProcess.standardizeMobileNumer(mobilis_mobile_ftext.getText()) + "'  : " + interProcess.standardizeMobileNumer(mobilis_mobile_ftext.getText()).length());
    }//GEN-LAST:event_mobilis_send_buttonActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main_interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main_interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main_interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main_interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new main_interface().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(main_interface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Adress_Text;
    private javax.swing.JTextField Fname_Text;
    private javax.swing.JTextField Lname_Text;
    private javax.swing.JLabel Mobilis_offers_IconLabel;
    private javax.swing.JButton Mobilis_panel_broker;
    public static javax.swing.JPanel OoredooChipsPanel;
    private javax.swing.JTabbedPane RC_TabbedPane;
    private javax.swing.JTextField Tel1_Text;
    private javax.swing.JTextField Tel2_Text;
    private javax.swing.JTextField Wilaya_Text;
    private javax.swing.JTable allTradersTable;
    private javax.swing.JPanel autocheck_panel;
    private javax.swing.JPanel check_panel;
    private javax.swing.JButton clients_panel_broker;
    private javax.swing.JTextField comapnyName_Text;
    private javax.swing.JTextField commune_Text;
    private javax.swing.JTabbedPane config_TabbedPanel;
    public static javax.swing.JPanel djezzyChipsPanel;
    private javax.swing.JButton djezzy_0;
    private javax.swing.JButton djezzy_1;
    private javax.swing.JButton djezzy_2;
    private javax.swing.JButton djezzy_3;
    private javax.swing.JButton djezzy_4;
    private javax.swing.JButton djezzy_40;
    private javax.swing.JButton djezzy_41;
    private javax.swing.JButton djezzy_42;
    private javax.swing.JButton djezzy_43;
    private javax.swing.JButton djezzy_44;
    private javax.swing.JButton djezzy_45;
    private javax.swing.JButton djezzy_46;
    private javax.swing.JButton djezzy_47;
    private javax.swing.JButton djezzy_48;
    private javax.swing.JButton djezzy_49;
    private javax.swing.JButton djezzy_5;
    private javax.swing.JButton djezzy_6;
    private javax.swing.JButton djezzy_7;
    private javax.swing.JButton djezzy_8;
    private javax.swing.JButton djezzy_9;
    private javax.swing.JButton djezzy_addClient_button;
    private javax.swing.JButton djezzy_addOffer_button;
    private javax.swing.JTextField djezzy_amount_text;
    private javax.swing.JButton djezzy_checkSodle_button;
    private javax.swing.JTextField djezzy_ciompany_text;
    private javax.swing.JTable djezzy_clientTable;
    private javax.swing.JTextField djezzy_clientname_text;
    private javax.swing.JButton djezzy_configureOffer_button;
    private javax.swing.JButton djezzy_delete_button;
    private javax.swing.JButton djezzy_delete_button4;
    private javax.swing.JTextField djezzy_idclient_text;
    private javax.swing.JButton djezzy_init_button;
    private javax.swing.JButton djezzy_init_button4;
    private javax.swing.JFormattedTextField djezzy_mobile_ftext;
    private javax.swing.JLabel djezzy_offers_IconLabel;
    private javax.swing.JPanel djezzy_offers_panel;
    private javax.swing.JButton djezzy_panel_broker;
    private javax.swing.JButton djezzy_send_button;
    private javax.swing.JPanel djezzy_show_panel;
    private javax.swing.JPanel djezzy_show_panel3;
    private javax.swing.JPanel djezzy_show_panel4;
    private javax.swing.JButton djezzy_updateClient_button;
    private javax.swing.JTextField email1_Text;
    private javax.swing.JTextField email2_Text;
    private javax.swing.JLabel globaleSoldeDjezzy_Label;
    private javax.swing.JLabel globaleSoldeMobilis_Label;
    private javax.swing.JLabel globaleSoldeOoredoo_Label;
    private javax.swing.JLabel globaleSolde_Label;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField mobileNumber_Text;
    public static javax.swing.JPanel mobilisChipsPanel;
    private javax.swing.JButton mobilis_0;
    private javax.swing.JButton mobilis_1;
    private javax.swing.JButton mobilis_2;
    private javax.swing.JButton mobilis_3;
    private javax.swing.JButton mobilis_4;
    private javax.swing.JButton mobilis_5;
    private javax.swing.JButton mobilis_6;
    private javax.swing.JButton mobilis_7;
    private javax.swing.JButton mobilis_8;
    private javax.swing.JButton mobilis_9;
    private javax.swing.JButton mobilis_addClient_button;
    private javax.swing.JButton mobilis_addOffer_button;
    private javax.swing.JTextField mobilis_amount_text;
    private javax.swing.JButton mobilis_checkSodle_button;
    private javax.swing.JTextField mobilis_ciompany_text;
    private javax.swing.JTable mobilis_clientTable;
    private javax.swing.JTextField mobilis_clientname_text;
    private javax.swing.JButton mobilis_configureOffer_button;
    private javax.swing.JButton mobilis_delete_button;
    private javax.swing.JTextField mobilis_idclient_text;
    private javax.swing.JButton mobilis_init_button;
    private javax.swing.JFormattedTextField mobilis_mobile_ftext;
    private javax.swing.JPanel mobilis_offers_panel;
    private javax.swing.JButton mobilis_send_button;
    private javax.swing.JButton mobilis_updateClient_button;
    private javax.swing.JPanel number_panel;
    private javax.swing.JCheckBox offer_checkBox;
    private javax.swing.JSpinner offer_realValueText;
    private javax.swing.JSpinner offer_value_text;
    private javax.swing.JButton offers_panel_broker;
    private javax.swing.JTextField offreDYN_NbBeforeNum;
    private javax.swing.JTextField offreDYN_NbBetweenNumPin;
    private javax.swing.JTable offreDYN_TableBeforeNum;
    private javax.swing.JTable offreDYN_TableBetweenNumPin;
    private javax.swing.JComboBox<String> offreST_operator;
    private javax.swing.JComboBox<String> offreST_type;
    private javax.swing.JTextField offresDYN_AfterPIN;
    private javax.swing.JTextField offresST_AfterPIN;
    private javax.swing.JTextField offresST_beforeNum;
    private javax.swing.JTextField offresST_betweenNumPin;
    private javax.swing.JButton ooredoo_addClient_button;
    private javax.swing.JButton ooredoo_addOffer_button;
    private javax.swing.JTextField ooredoo_amount_text;
    private javax.swing.JButton ooredoo_checkSodle_button;
    private javax.swing.JTextField ooredoo_ciompany_text;
    private javax.swing.JTable ooredoo_clientTable;
    private javax.swing.JTextField ooredoo_clientname_text;
    private javax.swing.JButton ooredoo_configureOffer_button;
    private javax.swing.JTextField ooredoo_idclient_text;
    private javax.swing.JFormattedTextField ooredoo_mobile_ftext;
    private javax.swing.JLabel ooredoo_offers_IconLabel;
    private javax.swing.JPanel ooredoo_offers_panel;
    private javax.swing.JButton ooredoo_panel_broker;
    private javax.swing.JButton ooredoo_send_button;
    private javax.swing.JButton ooredoo_updateClient_button;
    private javax.swing.JPanel operator_panel;
    private javax.swing.JButton operator_panel_broker;
    private javax.swing.JPasswordField passwordConfirm_Text;
    private javax.swing.JPasswordField password_Text;
    private javax.swing.JPanel port_panel;
    private javax.swing.JButton portpuce_panel_broker;
    private javax.swing.JComboBox<String> providerAddTrader_Combo;
    private javax.swing.JPanel status_panel;
    private javax.swing.JPanel traderAddCompanyName;
    private javax.swing.JComboBox<String> traderCategory_Combo;
    private javax.swing.JComboBox<String> traderType_Combo;
    private javax.swing.JTextField username_Text;
    private javax.swing.JComboBox<String> usersTrader_Combo;
    private javax.swing.JButton users_panel_broker;
    // End of variables declaration//GEN-END:variables
}
