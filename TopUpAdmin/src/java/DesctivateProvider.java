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
import model_db.OfferInfo;
import model_db.ProviderClient;
import model_helpers.OfferInfo_Util;
import model_helpers.ProviderClient_Util;
import model_util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/DesctivateProvider"})
public class DesctivateProvider extends HttpServlet {

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
       Session session = HibernateUtil.getSessionFactory().openSession();
        try (PrintWriter out = response.getWriter()) {
                session.getTransaction().begin();
            /* TODO output your page here. You may use following sample code. */
            String providerClientId = request.getParameter("id");
            ProviderClient_Util client_Util = new ProviderClient_Util();
            ProviderClient client = (ProviderClient) client_Util.getProviderClient_by_id(session, Integer.parseInt(providerClientId), "");
            System.out.println("DesctivateOffer.processRequest()"+client.getDateAffect());
            client.setFlag(1);
            client_Util.updateProviderClient(client, session);
            session.getTransaction().commit();
                        session.close();
            response.sendRedirect("view/listProviderClient.jsp?del");
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            response.sendRedirect("view/listProviderClient.jsp?erreur");
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
