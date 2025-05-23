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
import model_db.ProviderClient;
import model_helpers.ProviderClient_Util;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/OperatorForProviderClient"})
public class OperatorForProviderClient extends HttpServlet {

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
            String idTrader = request.getParameter("id");
            int idclient = Integer.parseInt(idTrader);
            ProviderClient_Util pro_u = new ProviderClient_Util();
            ProviderClient provider = pro_u.getProviderClient_by_id(idclient, "");
            List listProcide = pro_u.getProviderClient_by_client(idclient, "");
            out.println("<input type=\"hidden\" name=\"idclientProvider\"  id=\"idclientProvider\" value=\"" + idclient + "\" />");
            if (provider == null) {
                out.println("<label > le client il n'a pas un fournisseur </label>");

            } else {

                out.println(" <div class=\"col-lg-12\" class=\"form-group\">");
                out.println("<label >" + provider.getOperator().getOperatorDesc() + "</label>");
                out.println("<label id=\"label\" >" + provider.getSolde() + "</label>");
                out.println("<label id=\"new\" ></label>");
                out.println("<input type=\"hidden\" name=\"id_" + provider.getOperator().getOperatorDesc() + "\"  "
                        + "id=\"id_" + provider.getOperator().getOperatorDesc() + "\" value=\"" + provider.getOperator().getIdoperator() + "\" />");
                out.println("<input type=\"number\" id=\"amount\" "
                        + "name=\"amount\" "
                        + "class=\"form-control\"  placeholder=\"Enter  montant " + provider.getOperator().getOperatorDesc() + "\">");
                out.println("</div>");
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
