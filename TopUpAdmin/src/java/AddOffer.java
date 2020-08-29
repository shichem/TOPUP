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
import model_db.OfferInfo;
import general_helpers.dbhelper;
import java.util.Date;
import javax.servlet.http.HttpSession;
import model_db.SimInfo;
import model_db.SimOffer;
import model_db.UserInfo;
import model_helpers.OfferInfo_Util;
import model_helpers.SimInfo_Util;
import model_helpers.SimOffer_Util;
import model_helpers.UserInfo_Util;
import model_util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/AddOffer"})
public class AddOffer extends HttpServlet {

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
        HttpSession session = request.getSession();

        try {
            //operateur
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
            if (checked == "0") {
                tmaontant = Double.valueOf(request.getParameter("tmaontant"));
                dmaontant = Double.valueOf(request.getParameter("dmaontant"));
            }
            System.out.println("AddOffer.processRequest()" + "size===>" + items.length);
            int isstatique = 0;
            dbhelper db = new dbhelper();
            int userID = Integer.parseInt(session.getAttribute("Id").toString());
            UserInfo user = new UserInfo_Util().getUserInfo_by_id(userID, "");
            int respeonse = db.addOffer(userID, Integer.parseInt(operateur), offerDesc, Integer.parseInt(type), prenumber, postnumber, postpincode, tmaontant, dmaontant, isstatique, items);
            if (respeonse == staticVars.onGoingProcessOK) {

                response.sendRedirect("view/listOffre.jsp?add");
            } else {
                response.sendRedirect("view/listOffre.jsp?erreur");
            }
        } catch (Exception e) {
            e.printStackTrace();

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
