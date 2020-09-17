/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import custom_vars.staticVars;
import general_helpers.dbhelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model_db.ServerProfile;
import model_db.StationType;
import model_db.StatusInfo;
import model_helpers.ServerProfile_Util;
import model_helpers.StationType_Util;
import model_util.HibernateUtil;
import org.hibernate.Session;
import model_db.Trader;
import model_db.UserInfo;
import model_helpers.StatusInfo_Util;
import model_helpers.TraderType_Util;
import model_helpers.Trader_Util;
import model_helpers.UserInfo_Util;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/AddStation"})
public class AddStation extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            session.getTransaction().begin();

            String TraderStr = request.getParameter("trader");
            String[] arrOfStr = TraderStr.split("-", 5);
            Integer id = Integer.parseInt(arrOfStr[0].toString());
            String tsn1 = request.getParameter("sn1");
            String tsn2 = request.getParameter("sn2");
            String ttypeStation = request.getParameter("typeStationId");
            String tserverProfile = request.getParameter("serverProfileId");
            String statusStationId = request.getParameter("statusStationId");
            String treaderID = request.getParameter("treaderID");
            String stationName = request.getParameter("fname");
            String password = request.getParameter("password");
            String userName = request.getParameter("userName");

            HttpSession httpSession = request.getSession();

            int userID = Integer.parseInt(httpSession.getAttribute("Id").toString());

            StationType stationType = (StationType) new StationType_Util().getStationType_by_id(session, Integer.parseInt(ttypeStation), "");
            Trader trader = (Trader) new Trader_Util().getTradfer_by_id(session, id, "");
            ServerProfile profile = (ServerProfile) new ServerProfile_Util().getStationType_by_id(session, Integer.parseInt(tserverProfile), "");
            UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            dbhelper helper = new dbhelper();
           StatusInfo status = new StatusInfo_Util().getStatusInfo_by_id(session, Integer.parseInt(statusStationId), "");

            int respeonse = helper.addStation(session, user, trader,status, stationType, trader.getTraderCompany(), trader.getTraderCompany(), tsn1, tsn2, "", profile, stationName,userName,password);
            session.getTransaction().commit();
            session.close();
            String gro ="";
            if(trader.getTraderCategory().getTraderCategoryDesc().equals(staticVars.traderCategory_Grossiste)){
            gro="Gro";
            }
            if (respeonse == staticVars.onGoingProcessOK) {
                response.sendRedirect("view/listStation"+gro+".jsp?add");
            } else {
                response.sendRedirect("view/addStation"+gro+".jsp?erreur");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
            response.sendRedirect("view/listStation.jsp?erreur");

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
