/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import custom_vars.staticVars;
import general_helpers.dbhelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model_db.Operator;
import model_db.PortInfo;
import model_db.SimInfo;
import model_db.SimType;
import model_db.StatusInfo;
import model_db.UserInfo;
import model_helpers.Operator_Util;
import model_helpers.PortInfo_Util;
import model_helpers.SimInfo_Util;
import model_helpers.SimType_Util;
import model_helpers.StatusInfo_Util;
import model_helpers.UserInfo_Util;
import model_util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/AddSim"})
public class AddSim extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Session session = HibernateUtil.getSessionFactory().openSession();

        try  {
            /* TODO output your page here. You may use following sample code. */
            session.getTransaction().begin();

            String sim = request.getParameter("Sim");
            String pin = request.getParameter("pin");
            String operatorId = request.getParameter("operator");
            String type = request.getParameter("type");
            String port = request.getParameter("port");
            String portText = request.getParameter("portText");
      ;
   
            String checked = request.getParameter("mant_iden");
            PortInfo portInfo = null;
            if (checked == "0") {
                System.out.println("AddSim.processRequest() potrt 1:"+port);
                portInfo = new PortInfo_Util().getPortInfo_by_id(session, Integer.parseInt(port), "");
            } else {
                System.out.println("AddSim.processRequest() potrt :"+portText);
                portInfo = new PortInfo(new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, ""), portText);
                new PortInfo_Util().addPortInfo(portInfo, session);
                session.refresh(portInfo);
            }
            Operator operator = new Operator_Util().getOperator_by_id(session, Integer.parseInt(operatorId), "");
            StatusInfo info = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, "");
            SimType simType = new SimType_Util().getOfferType_by_id(session, Integer.parseInt(type), "");
            SimInfo_Util siu = new SimInfo_Util();

            SimInfo actualSim = new SimInfo(operator, portInfo, info, sim, pin);
            actualSim.setDateConsul(new Date());
            actualSim.setFlag(0);
            actualSim.setSimType(simType);
            actualSim.setAverageResponseTime(-1.0);
            actualSim.setLastSignalStrength(-1.0);
            actualSim.setWeightedIndex(-1.0);
            actualSim.setTotalTransactions(0);
            actualSim.setSuccededTransactions(0);
            actualSim.setLastEstimatedSolde(0.0);
            actualSim.setLastSolde(0.0);
            siu.addSimInfo(actualSim, session);
            session.getTransaction().commit();
            session.close();

            response.sendRedirect("view/listSim.jsp?add");

        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            System.out.println("AddSim.processRequest()"+e.getMessage());
            response.sendRedirect("view/addSim.jsp?erreur");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
