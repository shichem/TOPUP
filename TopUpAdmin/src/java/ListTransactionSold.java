/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model_db.TransactionSolde;
import model_helpers.TransactionTopup_Util;
import model_db.TransactionTopup;
import model_helpers.TransactionSolde_Util;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/ListTransactionSold"})
public class ListTransactionSold extends HttpServlet {

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
            Integer start = Integer.parseInt(request.getParameter("start"));
            Integer length = Integer.parseInt(request.getParameter("length"));
            String status = request.getParameter("status");
            String type = request.getParameter("type");
            String name = request.getParameter("name");
            String dateDebut = request.getParameter("dateDebut");
            String provider = request.getParameter("provider");
            String dateFin = request.getParameter("dateFin");
             String minSold =request.getParameter("minSold");
            String maxSold =request.getParameter("maxSold");
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
            int count = solde_Util.getAllTransactionSolde();
            int filtreCount = solde_Util.getAllTransactionSold(0, count,status,type,provider1,name1,dateDebut,dateFin,minSold,maxSold).size();
            List l = solde_Util.getAllTransactionSold(start, length, status,type, provider1, name1, dateDebut, dateFin,minSold,maxSold);

            out.print("{\n \n"
                    + "  \"recordsTotal\": " + count + ",\n"
                    + "  \"recordsFiltered\": " + filtreCount + ","
                    + "" + "\"data\": [");
            int i = 0;
            for (Object object : l) {
                TransactionSolde sold = (TransactionSolde) object;
                out.print("[");
                out.print("\"" + sold.getProviderClient().getTraderByIdprovider().getTraderFname() + "\",");
                out.print("\"" + sold.getProviderClient().getTraderByIdclient().getTraderFname() + "\",");
                out.print("\"" + sold.getTransactAmount() + "\",");   
                out.print("\"" + sold.getNewSolde() + "\",");
                out.print("\"" + sold.getOldSolde()+ "\",");
             
                out.print("\"" + sold.getTransactDate().toString() + "\",");
                out.print("\"" + sold.getStatusInfo().getStatusInfoDesc() + "\",");
                out.print("\"" + sold.getUserInfo().getUsername()+ "\"");
                out.print("]");
                if (i < l.size() - 1) {
                    out.print(",");
                }
                i++;
            }
            out.print("]}");
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
