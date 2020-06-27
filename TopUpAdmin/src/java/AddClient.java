/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import custom_package.operatorUI;
import custom_vars.staticVars;
import custom_vars.uiVars;
import general_helpers.dbhelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model_db.Operator;
import topup_desktop.main_interface;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/AddClient"})
public class AddClient extends HttpServlet {

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
            HttpSession session = request.getSession();
            try {
                String ttype = request.getParameter("type");
                String tcategory = request.getParameter("catgory");
                String providerTrader = request.getParameter("fourn");

                String tcompany = request.getParameter("company");
                String tfname = request.getParameter("fname");
                String tlname = request.getParameter("lname");
                String tnumber = request.getParameter("simNumber");
                String tadress = request.getParameter("adresse");
                String twilaya = request.getParameter("wilaya");
                String tcommune = request.getParameter("commune");
                String temail1 = request.getParameter("email1");
                String temail2 = request.getParameter("email2");
                String ttel1 = request.getParameter("telephone1");
                String ttel2 = request.getParameter("telephone2");
                String tsn1 = request.getParameter("sn1");
                String tsn2 = request.getParameter("sn2");
                String ttypeStation = request.getParameter("typeStationId");
                String tserverProfile = request.getParameter("serverProfileId");
                dbhelper helper = new dbhelper();
                Vector<Integer> operarorVect = new Vector<Integer>();
                Vector<operatorUI> listoperateur = helper.loadOperatorsData();
                for (int i = 0; i < listoperateur.size(); i++) {
                    operarorVect.add(listoperateur.get(i).getOperaror().getIdoperator());

                }
                System.out.println(providerTrader);
                System.out.println(providerTrader);
                int idParent = Integer.parseInt(providerTrader);
                System.out.println(idParent);
                Vector<Double> limitSoldes = new Vector<Double>();
                limitSoldes.add(-1.0);
                limitSoldes.add(-1.0);
                limitSoldes.add(-1.0);
                System.out.println(tcompany);
            int userID = Integer.parseInt(session.getAttribute("Id").toString());
                int respeonse = helper.addTrader_forActualUser_AndLink(userID,providerTrader,
                        tcategory, ttype, tfname, tlname, tcompany, tnumber,tadress, tcommune, twilaya,
                        temail1, temail2, ttel1, ttel2, operarorVect, limitSoldes,tsn1,tsn2,ttypeStation,tserverProfile);
               if(respeonse == staticVars.onGoingProcessOK){
              response.sendRedirect("view/listClient.jsp");
         }else{
            response.sendRedirect("view/addClient.jsp?erreur");
         }
            } catch (Exception e) {
                e.printStackTrace();

            }
            out.print("test");
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
