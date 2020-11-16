/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model_db.SimInfo;
import model_db.StatusInfo;
import model_helpers.SimInfo_Util;
import model_helpers.StatusInfo_Util;
import custom_vars.staticVars;
import model_util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/ActiveSim"})
public class ActiveSim extends HttpServlet {

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
            session.getTransaction().begin();
            /* TODO output your page here. You may use following sample code. */
            String offreID = request.getParameter("id");
            SimInfo_Util info_Util = new SimInfo_Util();
            SimInfo offerInfo = (SimInfo) info_Util.getSimInfo_by_id(session, Integer.parseInt(offreID), "");
            StatusInfo_Util util = new StatusInfo_Util();
            StatusInfo si = util.getStatusInfo_by_statusInfoDesc(session, staticVars.status_ENT_Actif, "");
            offerInfo.setStatusInfo(si);
            info_Util.updateSimInfo(offerInfo, session);
            session.getTransaction().commit();
            session.close();
            response.sendRedirect("view/listSim.jsp?active");
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            response.sendRedirect("view/listSim.jsp?erreur");

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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
