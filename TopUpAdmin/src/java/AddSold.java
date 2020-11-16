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

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/AddSold"})
public class AddSold extends HttpServlet {

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

            int idtrader =  Integer.parseInt(request.getParameter("idtrader"));
            String id_DJEZZY = request.getParameter("id_DJEZZY");
            String id_MOBILIS = request.getParameter("id_MOBILIS");
            String id_OOREDOO = request.getParameter("id_OOREDOO");
            String amount_DJEZZY = request.getParameter("amount_DJEZZY");
            String amount_MOBILIS = request.getParameter("amount_MOBILIS");
            String amount_OOREDOO = request.getParameter("amount_OOREDOO");
            dbhelper db = new dbhelper();
            if (!amount_DJEZZY.isEmpty()) {
                System.out.println("AddSold.processRequest() Djezzy ="+amount_DJEZZY);
                System.out.println("AddSold.processRequest() userId ="+userID);
                System.out.println("AddSold.processRequest() traderID ="+idtrader);
                System.out.println("AddSold.processRequest() idOperator ="+id_DJEZZY);
                db.updateVirtualBalance(userID, idtrader,Integer.parseInt(id_DJEZZY), Double.parseDouble(amount_DJEZZY));
            }
            if (!amount_MOBILIS.isEmpty()) {
                System.out.println("AddSold.processRequest() Mobilis"+amount_MOBILIS);
                db.updateVirtualBalance(userID, idtrader, Integer.parseInt(id_MOBILIS),  Double.parseDouble(amount_MOBILIS));
            }
            if (!amount_OOREDOO.isEmpty()) {
                System.out.println("AddSold.processRequest() Ooredoo"+amount_OOREDOO);

                db.updateVirtualBalance(userID, idtrader, Integer.parseInt(id_OOREDOO),  Double.parseDouble(amount_OOREDOO));
            }
                   response.sendRedirect("view/listClient.jsp?succesSold");
            } catch (Exception e) {
                       response.sendRedirect("view/listClient.jsp?erreur");
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
