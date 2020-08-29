/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import custom_vars.staticVars;
import general_helpers.dbhelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model_util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/EditOffer"})
public class EditOffer extends HttpServlet {

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
        //operateur
        HttpSession session_ = request.getSession();

        int userID = Integer.parseInt(session_.getAttribute("Id").toString());
        Integer id = Integer.parseInt(request.getParameter("id"));
        try {

            String operateur = request.getParameter("operateur");
            String type = request.getParameter("type");
            String offerDesc = request.getParameter("fname");
            String prenumber = request.getParameter("preNumber");
            String postnumber = request.getParameter("posteNumber");
            String postpincode = request.getParameter("posteNumberPin");
            double tmaontant = 0;
            double dmaontant = 0;
            String[] items = request.getParameterValues("Number");
            String checked = request.getParameter("mant_iden");

            tmaontant = Double.valueOf(request.getParameter("tmaontant"));
            dmaontant = Double.valueOf(request.getParameter("dmaontant"));
            dbhelper db = new dbhelper();

            int respeonse = db.UpadateOffer(id, userID, Integer.parseInt(operateur), offerDesc, Integer.parseInt(type), prenumber, postnumber, postpincode, tmaontant, dmaontant, 0, items);

            
            if (respeonse == staticVars.onGoingProcessOK) {
                response.sendRedirect("view/editOffre.jsp?succes&id=" + id);
            } else {
                response.sendRedirect("view/editOffre.jsp?erreur&id=" + id);
            }
        } catch (Exception e) {
       
            response.sendRedirect("view/editOffre.jsp?erreur&id=" + id);

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
