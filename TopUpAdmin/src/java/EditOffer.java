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
import model_db.OfferInfo;
import model_db.OfferType;
import model_db.Operator;
import model_db.UserInfo;
import model_helpers.OfferInfo_Util;
import model_helpers.OfferType_Util;
import model_helpers.Operator_Util;
import model_helpers.UserInfo_Util;
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
                Session session = HibernateUtil.getSessionFactory().openSession();

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

             session.getTransaction().begin();
            /* TODO output your page here. You may use following sample code. */
            String offreID = request.getParameter("id");
            OfferInfo_Util info_Util = new OfferInfo_Util();
            OfferInfo offerInfo = (OfferInfo) info_Util.getOfferInfo_by_id(session, Integer.parseInt(offreID), "");
            System.out.println("DesctivateOffer.processRequest()"+offerInfo.getOfferDesc());
            UserInfo userInfo = new UserInfo_Util().getUserInfo_by_id(session, userID, "");
            OfferType offerType = new OfferType_Util().getOfferType_by_id(session, Integer.parseInt(type), "");
            Operator operator = new Operator_Util().getOperator_by_id(session, Integer.parseInt(operateur), "");
            OfferInfo offer = info_Util.getOfferInfo_by_id(session,id, "");
            offer.setOfferType(offerType);
            offer.setOperator(operator);
            //offer.setUserInfoByIduserInfoUpdate(userInfo);
            offer.setOfferDesc(offerDesc);
            offer.setRealValue(tmaontant);
            offer.setTransferedValue(dmaontant);                    
            offer.setIsStatic(0);
            offer.setPrenumber(prenumber);
            offer.setPostnumber(postnumber);
            offer.setPostPinCode(postpincode);
            offerInfo.setFlag(0);
            //System.out.println("general_helpers.dbhelper.addOffer()in for+ ===" + number.length);
            /*for (int i = 0; i < number.length; i++) {
                System.out.println("general_helpers.dbhelper.addOffer()in for");
                SimInfo info = new SimInfo_Util().getSimInfo_by_id(session, Integer.parseInt(number[i]), "");
                SimOffer adt = new SimOffer();
                adt.setSimInfo(info);
                adt.setOfferInfo(offer);
                adt.setUserInfo(userInfo);
                adt.setDateAffect(new Date());
                session.saveOrUpdate(adt);
            }*/
            info_Util.updateOfferInfo(offerInfo, session);
            session.getTransaction().commit();
                        session.close();
            
   
                response.sendRedirect("view/editOffre.jsp?succes&id=" + id);
          
               
        } catch (Exception e) {
             session.getTransaction().rollback();
            session.close();
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
