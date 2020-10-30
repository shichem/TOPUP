/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import general_helpers.dbhelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model_db.SimInfo;
import model_db.TransactionTopup;
import model_helpers.SimInfo_Util;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/RefreshSimAndList"})
public class RefreshSimAndList extends HttpServlet {

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
                 System.out.println("RefreshSim.processRequest()" + "in this controller");
            dbhelper db = new dbhelper();
            db.loadSimsPortsData();
            List<SimInfo> simInfos = new SimInfo_Util().getAllSimInfo("");
            out.print("{\n \n"
                    + "  \"recordsTotal\": " + simInfos.size() + ",\n"
                    + "  \"recordsFiltered\": " + simInfos.size()+ ","
                    + "" + "\"data\": [");
            int i = 0;
            for (Object object : simInfos) {
                SimInfo info = (SimInfo) object;
                out.print("[");
                out.print("\"" + info.getSimnumber()+ "\",");
                   out.print("\"" + info.getOperator().getOperatorDesc()+ "\",");
                out.print("\"" + info.getSimType().getSimTypeDesc() + "\",");
               
                out.print("\"" + String.format("%,.2f", info.getLastSolde() )+ "\",");
                out.print("\"" + info.getDateConsul()+   "\",");
                   out.print("\"" + info.getPortInfo().getPortDesc()+ "\",");
                out.print("\"" + info.getStatusInfo().getStatusInfoDesc().replace("ENT_", "") + "\"");
                out.print("]");
                if (i < simInfos.size() - 1) {
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
