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
import model_helpers.TransactionTopup_Util;
import model_db.TransactionTopup;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/ListTransactionTopUp"})
public class ListTransactionTopUp extends HttpServlet {

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
            TransactionTopup_Util topup_Util = new TransactionTopup_Util();
            int count = topup_Util.getAllTransactionTopup();
            List l = topup_Util.getAllTransactionTopup(start, length);

            out.print("{\n \n"
                    + "  \"recordsTotal\": " + count + ",\n"
                    + "  \"recordsFiltered\": " + count + ","
                    + "" + "\"data\": [");
            int i = 0;
            for (Object object : l) {
                TransactionTopup topup = (TransactionTopup) object;
                out.print("[");
                out.print("\"" + topup.getIdtransacttopup() +"__"+topup.getProviderClient().getTraderByIdclient().getTraderFname() + "\",");
                out.print("\"" + topup.getSimClient() + "\",");
                out.print("\"" + topup.getSimOffer().getOfferInfo().getOfferDesc() + "\",");
                out.print("\"" + topup.getNewSolde() + "\",");
                out.print("\"" + topup.getTransactAmount() + "\",");
                out.print("\"" + topup.getRealTransactAmount() + "\",");
                out.print("\"" + topup.getTransactDate().toString() + "\",");
                out.print("\"" + topup.getStatusInfo().getStatusInfoDesc() + "\",");
                out.print("\"" + topup.getTransactionType().getTransactionTypeDesc() + "\",");
                out.print("\"" + topup.getSentMessage() + "\",");
                out.print("\"<i>" + topup.getRecievedMessage().replaceAll("\"", "'").replaceAll("[\r\n]+", "")+ "</i>\"");
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
