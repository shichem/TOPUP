/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import custom_vars.staticVars;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model_helpers.TransactionSolde_Util;
import model_helpers.TransactionTopup_Util;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/SoldTransaction"})
public class SoldTransaction extends HttpServlet {

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
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            String status = request.getParameter("status");
            String name = request.getParameter("name");
            String dateDebut = request.getParameter("dateDebut");
            String provider = request.getParameter("provider");
            String dateFin = request.getParameter("dateFin");
            String name1 = "";
            if (name != "") {
                String[] arrOfStr = name.split("-", 5);
                name1 = arrOfStr[1].toString();
            }
            String provider1 = "";
            if (provider != "") {
                String[] arrOfStr = provider.split("-", 5);
                provider1 = arrOfStr[1].toString();
            }
          
            TransactionSolde_Util solde_Util = new TransactionSolde_Util();
            double sumValid = 0;
            double sumLitig = 0;
            if (status == "") {
                sumValid = solde_Util.transactionSold(staticVars.status_TCT_Reussie,provider1,name1,dateDebut,dateFin);
                sumLitig = solde_Util.transactionSold( staticVars.status_TCT_AVerifier,provider1,name1,dateDebut,dateFin);
            } else if (status.equals(staticVars.status_TCT_Reussie)) {
                sumValid = solde_Util.transactionSold(staticVars.status_TCT_Reussie,provider1,name1,dateDebut,dateFin);

            } else if (status.equals(staticVars.status_TCT_AVerifier)) {
                sumLitig = solde_Util.transactionSold( staticVars.status_TCT_AVerifier,provider1,name1,dateDebut,dateFin);

            }
            
            out.println("{\"sumValid\" :" + sumValid + ",\"sumLitig\": " + sumLitig + "}");
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
