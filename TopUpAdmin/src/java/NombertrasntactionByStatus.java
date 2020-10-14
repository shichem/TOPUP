/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import custom_vars.staticVars;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model_helpers.TransactionTopup_Util;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/NombertrasntactionByStatus"})
public class NombertrasntactionByStatus extends HttpServlet {

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

            String status = request.getParameter("status");
            String type = request.getParameter("type");
            String name = request.getParameter("name");
            String dateDebut = request.getParameter("dateDebut");
            String dateFin = request.getParameter("dateFin");
            String operator = request.getParameter("operator");
            String offer = request.getParameter("offer");
            String sim = request.getParameter("sim");
            String minSold =request.getParameter("minSold");
            String maxSold =request.getParameter("maxSold");
            String name1 = "";
            if (name != "") {
//                String[] arrOfStr = name.split("-", 5);
                //               name1 = arrOfStr[1].toString();
            }
            System.out.println("NombertrasntactionByStatus.processRequest()" + dateDebut + "--" + dateFin);

            TransactionTopup_Util topup_Util = new TransactionTopup_Util();

            List listLable = new TransactionTopup_Util().getAllTransactionTopupGroupTransactionBySatusLabel( status, type, name1, dateDebut, dateFin,operator,offer,sim,minSold,maxSold);
            List list1Count = new TransactionTopup_Util().getAllTransactionTopupGroupTransactionBySatusCount( status, type, name1, dateDebut, dateFin,operator,offer,sim,minSold,maxSold);
            out.print("[");
            for (int i = 0; i < listLable.size(); i++) {
                out.print("{\"label\":\"" + (String) listLable.get(i) + ": " + list1Count.get(i).toString() + "\",\"data\": " + list1Count.get(i).toString() + "}");
                if (i < listLable.size() - 1) {
                    out.print(",");
                }
            }
            out.print("]");
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
