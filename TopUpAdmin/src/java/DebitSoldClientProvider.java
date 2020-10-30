/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import general_helpers.dbhelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model_db.ProviderClient;
import model_helpers.ProviderClient_Util;

/**
 *
 * @author GarandaTech
 */
    @WebServlet(urlPatterns = {"/DebitSoldClientProvider"})
public class DebitSoldClientProvider extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            try {

                int userID = Integer.parseInt(session.getAttribute("Id").toString());
                int Clientprovider = Integer.parseInt(request.getParameter("idclientProvider"));
                ProviderClient_Util client_Util = new ProviderClient_Util();
                ProviderClient client = client_Util.getProviderClient_by_id( Clientprovider, "");               
                String amount = request.getParameter("amount");                                
                dbhelper db = new dbhelper(); 
                System.out.println("DebitSoldClientProvider.processRequest()"+client.getTraderByIdprovider().getIdtrader()+"op:"+client.getOperator().getIdoperator()+" client :"+client.getTraderByIdclient().getIdtrader()+ " amount"+amount);
                db.updateVirtualBalanceProvider(userID, client.getTraderByIdprovider().getIdtrader(), client.getTraderByIdclient().getIdtrader(), client.getOperator().getIdoperator(),-Double.parseDouble(amount));              
                response.sendRedirect("view/listProviderClient.jsp?succesDebitSold");
            } catch (Exception e) {
                response.sendRedirect("view/listProviderClient.js?erreur");
            }
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
