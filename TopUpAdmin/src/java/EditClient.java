/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import general_helpers.dbhelper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model_db.ServerProfile;
import model_db.Station;
import model_db.StationType;
import model_db.Trader;
import model_db.UserInfo;
import model_helpers.ServerProfile_Util;
import model_helpers.StationType_Util;
import model_helpers.TraderCategory_Util;
import model_helpers.TraderType_Util;
import model_helpers.Trader_Util;
import model_helpers.UserInfo_Util;
import model_helpers.station_Util;
import model_util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author GarandaTech
 */
@WebServlet(urlPatterns = {"/EditClient"})
public class EditClient extends HttpServlet {

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
        String idTrader = request.getParameter("id");
        Session session = HibernateUtil.getSessionFactory().openSession();
                    HttpSession session_ = request.getSession();

         int userID = Integer.parseInt(session_.getAttribute("Id").toString());

        try {
            /* TODO output your page here. You may use following sample code. */
            String ttype = request.getParameter("type");
            String tcategory = request.getParameter("catgory");
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
            Trader_Util trader_Util = new Trader_Util();
            dbhelper helper = new dbhelper();
            session.getTransaction().begin();
            Trader trader = trader_Util.getTradfer_by_id(session, Integer.parseInt(idTrader), "");
            trader.setTraderFname(tfname);
            trader.setTraderLname(tlname);
            trader.setAdresse(tadress);
            trader.setCommune(tcommune);
            trader.setWilaya(twilaya);
            trader.setEmail1(temail1);
            trader.setEmail2(temail2);
            trader.setTel1(ttel1);
            trader.setTel2(ttel2);
            trader.setSimnumber(tnumber);
            trader.setTraderType(new TraderType_Util().geTraderType_by_id(session, Integer.parseInt(ttype), ""));
            trader.setTraderCategory(new TraderCategory_Util().getTraderCategory_by_id(session, Integer.parseInt(tcategory), ""));
            trader_Util.updateTrader(trader, session);

            if (ttype.equals("1")) {
                List listStation = new station_Util().getStations_by_trader(session,trader, "");
                Station station = null;
                if (listStation.size() != 0) {
                    station = (Station) listStation.get(0);
                    StationType stationType = (StationType) new StationType_Util().getStationType_by_id(session, Integer.parseInt(ttypeStation), "");
                    ServerProfile profile = (ServerProfile) new ServerProfile_Util().getStationType_by_id(session, Integer.parseInt(tserverProfile), "");
                    station.setStationType(stationType);
                    station.setServerProfile(profile);
                    station.setStationSn1(tsn1);
                    station.setStationSn2(tsn2);
                    new station_Util().updateStation(station, session);
                } else {
                    StationType stationType = (StationType) new StationType_Util().getStationType_by_id(session, Integer.parseInt(ttypeStation), "");
                    ServerProfile profile = (ServerProfile) new ServerProfile_Util().getStationType_by_id(session, Integer.parseInt(tserverProfile), "");
                      UserInfo user = new UserInfo_Util().getUserInfo_by_id(session, userID, "");

                    new dbhelper().addStation(session, user, trader, stationType, trader.getTraderCompany(), trader.getTraderCompany(), tsn1, tsn2, "", profile);
                }
            }
            session.getTransaction().commit();
            session.close();
            response.sendRedirect("view/editClient.jsp?succes&id=" + idTrader);

        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            response.sendRedirect("view/editClient.jsp?erreur&id=" + idTrader);

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
