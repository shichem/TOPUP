package org.apache.jsp.view;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import model_helpers.ServerProfile_Util;
import model_db.ServerProfile;
import model_helpers.StationType_Util;
import model_helpers.TraderType_Util;
import model_db.TraderCategory;
import model_helpers.TraderCategory_Util;
import custom_package.typesStatusUI;
import model_db.TraderType;
import model_db.Trader;
import model_db.StationType;
import model_helpers.Trader_Util;
import java.util.List;
import custom_vars.staticVars;

public final class addClient_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(4);
    _jspx_dependants.add("/view/template/head.jsp");
    _jspx_dependants.add("/view/template/navigation.jsp");
    _jspx_dependants.add("/view/template/menu.jsp");
    _jspx_dependants.add("/view/template/script.jsp");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("\n");
      out.write("    ");
      out.write("<head>\n");
      out.write("\n");
      out.write("    <meta charset=\"utf-8\">\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
      out.write("    <meta name=\"description\" content=\"\">\n");
      out.write("    <meta name=\"author\" content=\"\">\n");
      out.write("\n");
      out.write("    <title>");
      out.print("EtopUp");
      out.write("</title>\n");
      out.write("\n");
      out.write("    <!-- Bootstrap Core CSS -->\n");
      out.write("    <link href=\"../lib/vendor/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
      out.write("\n");
      out.write("    <!-- MetisMenu CSS -->\n");
      out.write("    <link href=\"../lib/vendor/metisMenu/metisMenu.min.css\" rel=\"stylesheet\">\n");
      out.write("\n");
      out.write("    <!-- Custom CSS -->\n");
      out.write("    <link href=\"../lib/dist/css/sb-admin-2.css\" rel=\"stylesheet\">\n");
      out.write("\n");
      out.write("    <!-- Morris Charts CSS -->\n");
      out.write("    <link href=\"../lib/vendor/morrisjs/morris.css\" rel=\"stylesheet\">\n");
      out.write("\n");
      out.write("    <!-- Custom Fonts -->\n");
      out.write("    <link href=\"../lib/vendor/font-awesome/css/all.css\" rel=\"stylesheet\" type=\"text/css\">\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/v/bs/dt-1.10.20/datatables.min.css\"/>\n");
      out.write("\n");
      out.write("    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->\n");
      out.write("    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->\n");
      out.write("    <!--[if lt IE 9]>\n");
      out.write("        <script src=\"https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js\"></script>\n");
      out.write("        <script src=\"https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js\"></script>\n");
      out.write("    <![endif]-->\n");
      out.write("\n");
      out.write("</head>\n");
      out.write("     \n");
      out.write("\n");
      out.write("\n");
      out.write("    <body>\n");
      out.write("\n");
      out.write("        <div id=\"wrapper\">\n");
      out.write("\n");
      out.write("            <!-- Navigation -->\n");
      out.write("            ");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("           \n");
      out.write(" <nav class=\"navbar navbar-default navbar-static-top\" role=\"navigation\" style=\"margin-bottom: 0\">\n");
      out.write("            <div class=\"navbar-header\">\n");
      out.write("                <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">\n");
      out.write("                    <span class=\"sr-only\">Toggle navigation</span>\n");
      out.write("                    <span class=\"icon-bar\"></span>\n");
      out.write("                    <span class=\"icon-bar\"></span>\n");
      out.write("                    <span class=\"icon-bar\"></span>\n");
      out.write("                </button>\n");
      out.write("                <a class=\"navbar-brand\" href=\"index.html\">EtopUp</a>\n");
      out.write("            </div>\n");
      out.write("            <!-- /.navbar-header -->\n");
      out.write("   ");

       String User="" ;
                if (session.getAttribute("username") != null) {
                    User = session.getAttribute("username").toString();
                }else{
                    response.sendRedirect("../login.jsp");
                }
            
      out.write("\n");
      out.write("            <ul class=\"nav navbar-top-links navbar-right\">\n");
      out.write("               \n");
      out.write("                <!-- /.dropdown -->\n");
      out.write("                \n");
      out.write("                <!-- /.dropdown -->\n");
      out.write("                <!-- /.dropdown -->\n");
      out.write("                <li class=\"dropdown\">\n");
      out.write("                    <a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">\n");
      out.write("                      \n");
      out.write("            \n");
      out.write("                         <strong>");
      out.print(User);
      out.write("</strong> <i class=\"fa fa-user fa-fw\"></i> <i class=\"fa fa-caret-down\"></i>\n");
      out.write("                    </a>\n");
      out.write("                    <ul class=\"dropdown-menu dropdown-user\">\n");
      out.write("                        <li><a href=\"#\"><i class=\"fa fa-user fa-fw\"></i> User Profile</a>\n");
      out.write("                        </li>\n");
      out.write("                        <li><a href=\"#\"><i class=\"fa fa-gear fa-fw\"></i> Settings</a>\n");
      out.write("                        </li>\n");
      out.write("                        <li class=\"divider\"></li>\n");
      out.write("                        <li><a href=\"../Logout\"><i class=\"fa fa-sign-out fa-fw\"></i> Logout</a>\n");
      out.write("                        </li>\n");
      out.write("                    </ul>\n");
      out.write("                    <!-- /.dropdown-user -->\n");
      out.write("                </li>\n");
      out.write("                <!-- /.dropdown -->\n");
      out.write("            </ul>\n");
      out.write("            <!-- /.navbar-top-links -->\n");
      out.write("\n");
      out.write("            ");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("   <div class=\"navbar-default sidebar\" role=\"navigation\">\n");
      out.write("                <div class=\"sidebar-nav navbar-collapse\">\n");
      out.write("                    <ul class=\"nav\" id=\"side-menu\">\n");
      out.write("                       \n");
      out.write("                        <li>\n");
      out.write("                            <a href=\"dashboard.jsp\"><i class=\"fas fa-tachometer-alt fa-fw\"></i> Dashboard</a>\n");
      out.write("                        </li>\n");
      out.write("                        <li>\n");
      out.write("                            <a href=\"#\"><i class=\"fa fa-users fa-fw\"></i> Gestion des clients<span class=\"fa arrow\"></span></a>\n");
      out.write("                            <ul class=\"nav nav-second-level\">\n");
      out.write("                                <li>\n");
      out.write("                                    <a href=\"./addClient.jsp\"><i class=\"fa fa-user fa-fw\"></i>Ajouter client</a>\n");
      out.write("                                </li>\n");
      out.write("                                <li>\n");
      out.write("                                    <a href=\"./listClient.jsp\"><i class=\"fa fa-list fa-fw\"></i>List clients</a>\n");
      out.write("                                </li>\n");
      out.write("                            </ul>\n");
      out.write("                            <!-- /.nav-second-level -->\n");
      out.write("                        </li>\n");
      out.write("                         <li>\n");
      out.write("                            <a href=\"#\"><i class=\"fa fa-cubes fa-fw\"></i> Gestion des offres <span class=\"fa arrow\"></span></a>\n");
      out.write("                            <ul class=\"nav nav-second-level\">\n");
      out.write("                                <li>\n");
      out.write("                                    <a href=\"addOffre.jsp\"><i class=\"fa fa-cube fa-fw\"></i>Ajouter offre</a>\n");
      out.write("                                </li>\n");
      out.write("                                <li>\n");
      out.write("                                    <a href=\"./listOffre.jsp\"><i class=\"fa fa-list fa-fw\"></i>List offre</a>\n");
      out.write("                                </li>\n");
      out.write("                            </ul>\n");
      out.write("                            <!-- /.nav-second-level -->\n");
      out.write("                        </li>\n");
      out.write("                           <li>\n");
      out.write("                            <a href=\"#\"><i class=\"fa fa-sim-card fa-fw\"></i> Gestion SIM <span class=\"fa arrow\"></span></a>\n");
      out.write("                            <ul class=\"nav nav-second-level\">\n");
      out.write("                                <li>\n");
      out.write("                                    <a href=\"addSim.jsp\"><i class=\"fa fa-sim-card fa-fw\"></i>Ajouter SIM</a>\n");
      out.write("                                </li>\n");
      out.write("                                <li>\n");
      out.write("                                    <a href=\"./listSim.jsp\"><i class=\"fa fa-list fa-fw\"></i>List SIM</a>\n");
      out.write("                                </li>\n");
      out.write("                            </ul>\n");
      out.write("                            <!-- /.nav-second-level -->\n");
      out.write("                        </li>\n");
      out.write("                    </ul>\n");
      out.write("                </div>\n");
      out.write("                <!-- /.sidebar-collapse -->\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <!-- /.navbar-static-side -->\n");
      out.write("        </nav>\n");
      out.write("\n");
      out.write("\n");
      out.write("            <div id=\"page-wrapper\">\n");
      out.write("\n");
      out.write("                <div class=\"row\">\n");
      out.write("                    <div class=\"col-lg-12\">\n");
      out.write("                        <h1 class=\"page-header\">Ajouter client</h1>\n");
      out.write("                    </div>\n");
      out.write("                    <!-- /.col-lg-12 -->\n");
      out.write("                </div>\n");
      out.write("                <!-- /.row -->\n");
      out.write("                ");
 if (request.getParameter("erreur") != null) { 
      out.write("\n");
      out.write("                <div class=\"alert alert-danger\" role=\"alert\">\n");
      out.write("                    Erreur dans raison social du client\n");
      out.write("                </div>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("                <div class=\"row\">\n");
      out.write("                    <div class=\"col-lg-12\">\n");
      out.write("                        <div class=\"panel panel-default\">\n");
      out.write("                            <div class=\"panel-heading\">\n");
      out.write("                                Information sur Client  \n");
      out.write("                            </div>\n");
      out.write("                            <div class=\"panel-body\">\n");
      out.write("                                <div class=\"row\">\n");
      out.write("                                    <div class=\"col-lg-12\">\n");
      out.write("                                        <form role=\"form\" action=\"../AddClient\" method=\"POST\" >\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>Nom</label>\n");
      out.write("                                                <input required=\"\" id=\"fname\"  name=\"fname\" class=\"form-control\" placeholder=\"Enter nom\">\n");
      out.write("                                            </div>\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>Prenom</label>\n");
      out.write("                                                <input  required=\"\" id=\"laneme\" name=\"lname\" class=\"form-control\" placeholder=\"Enter  prenom\">\n");
      out.write("                                            </div>\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>Raison social</label>\n");
      out.write("                                                <input  required=\"\" id=\"company\" name=\"company\" class=\"form-control\" placeholder=\"Enter  Raison social\">\n");
      out.write("                                            </div>\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>Adresse</label>\n");
      out.write("                                                <input  required=\"\" id=\"adresse\" name=\"adresse\" class=\"form-control\" placeholder=\"Ente  adresse  du client\">\n");
      out.write("                                            </div>\n");
      out.write("\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>wilaya</label>\n");
      out.write("                                                <input  required=\"\" id=\"wilaya\" name=\"wilaya\" class=\"form-control\" placeholder=\"Entre wilaya du client \">\n");
      out.write("                                            </div>\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>commune</label>\n");
      out.write("                                                <input  required=\"\" id=\"commune\" name=\"commune\" class=\"form-control\" placeholder=\"Entre commune du client \">\n");
      out.write("                                            </div>\n");
      out.write("\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>email 1</label>\n");
      out.write("                                                <input id=\"email1\" type=\"email\" name=\"email1\" class=\"form-control\" placeholder=\"Enter email client\">\n");
      out.write("                                            </div>\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>email 2</label>\n");
      out.write("                                                <input id=\"email2\" type=\"email\"  name=\"email2\" class=\"form-control\" placeholder=\"Enter email client\">\n");
      out.write("                                            </div>\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>telephone  1</label>\n");
      out.write("                                                <input  required=\"\"  id=\"telephone1\" name=\"telephone1\" class=\"form-control\" placeholder=\"Enter telephone client\">\n");
      out.write("                                            </div>\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>telephone 2</label>\n");
      out.write("                                                <input id=\"telephone2\" name=\"telephone2\" class=\"form-control\" placeholder=\"Enter telephone client\">\n");
      out.write("                                            </div>\n");
      out.write("                                            ");
                                                List tistType = new TraderType_Util().getAllTraderType("");
                                            
      out.write("\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>client type</label>\n");
      out.write("                                                <select  required=\"\" id=\"type\" name=\"type\" class=\"form-control\">\n");
      out.write("                                                    <option value=\"\">Select le type du client </option>\n");
      out.write("\n");
      out.write("                                                    ");

                                                        for (int i = 0; i < tistType.size(); i++) {
                                                            TraderType get = (TraderType) tistType.get(i);
                                                    
      out.write("\n");
      out.write("                                                    <option value=\"");
      out.print(get.getIdtraderType());
      out.write('"');
      out.write('>');
      out.print(get.getTraderTypeDesc());
      out.write("</option>\n");
      out.write("                                                    ");

                                                        }
                                                    
      out.write("\n");
      out.write("                                                </select>\n");
      out.write("                                            </div>\n");
      out.write("\n");
      out.write("\n");
      out.write("                                          \n");
      out.write("                                            <div class=\"col-lg-6\" id=\"simNB\" hidden=\"\" class=\"form-group\">\n");
      out.write("                                                <label>Sim number</label>\n");
      out.write("                                                <input  id=\"simNumber\" name=\"simNumber\" class=\"form-control\" placeholder=\"Ente sim number \">\n");
      out.write("                                            </div>\n");
      out.write("                                                    ");
                                                List listType = new StationType_Util().getAllStationType("");
                                            
      out.write("             \n");
      out.write("                                           <div id=\"typeStation\"  hidden=\"\" class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>Type Station</label>\n");
      out.write("\n");
      out.write("                                                <select  id=\"typeStationId\" name=\"typeStationId\" class=\"form-control\">\n");
      out.write("                                                    <option value=\"\">Select un type  </option>\n");
      out.write("\n");
      out.write("                                                    ");
                                for (int i = 0; i < listType.size(); i++) {
                                                            StationType get = (StationType) listType.get(i);
                                                    
      out.write("\n");
      out.write("                                                    <option value=\"");
      out.print(get.getIdstationType());
      out.write('"');
      out.write('>');
      out.print(get.getStationTypeDesc());
      out.write("</option>\n");
      out.write("                                                    ");

                                                        }
                                                    
      out.write("\n");
      out.write("                                                </select>\n");
      out.write("                                            </div>\n");
      out.write("                                                       ");
                                                
                                                           List listServer = new ServerProfile_Util().getAllServerProfile("");
                                            
      out.write(" \n");
      out.write("                                            <div id=\"serverProfile\"  class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>Server Profile </label>\n");
      out.write("\n");
      out.write("                                                <select  id=\"serverProfileId\" name=\"serverProfileId\" class=\"form-control\">\n");
      out.write("                                                    <option value=\"\">Selection un server profile  </option>\n");
      out.write("\n");
      out.write("                                                    ");
                                for (int i = 0; i < listServer.size(); i++) {
                                                            ServerProfile get = (ServerProfile) listType.get(i);
                                                    
      out.write("\n");
      out.write("                                                    <option value=\"");
      out.print(get.getIdProfile());
      out.write('"');
      out.write('>');
      out.print(get.getServerAdress1());
      out.write("</option>\n");
      out.write("                                                    ");

                                                        }
                                                    
      out.write("\n");
      out.write("                                                </select>\n");
      out.write("                                            </div>\n");
      out.write("\n");
      out.write("                                            <div class=\"col-lg-6\" id=\"sndiv\" hidden=\"\" class=\"form-group\">\n");
      out.write("                                                <label>seriel number</label>\n");
      out.write("                                                <input id=\"sn1\" name=\"sn1\" class=\"form-control\" placeholder=\"Ente seriel number \">\n");
      out.write("                                            </div>\n");
      out.write("                                            <div class=\"col-lg-6\" id=\"sndiv1\" hidden=\"\" class=\"form-group\">\n");
      out.write("                                                <label>seriel number2</label>\n");
      out.write("                                                <input id=\"sn2\" name=\"sn2\" class=\"form-control\" placeholder=\"Ente seriel number \">\n");
      out.write("                                            </div>\n");
      out.write("\n");
      out.write("\n");
      out.write("                                            ");
                                                List listcategory = new TraderCategory_Util().getAllTraderCategory("");
                                            
      out.write("\n");
      out.write("                                            <div  class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>client category</label>\n");
      out.write("                                                <select required=\"\" id=\"catgory\" name=\"catgory\" class=\"form-control\">\n");
      out.write("                                                    <option value=\"\">Select un category client </option>\n");
      out.write("\n");
      out.write("                                                    ");

                                                        for (int i = 0; i < listcategory.size(); i++) {
                                                            TraderCategory get = (TraderCategory) listcategory.get(i);
                                                    
      out.write("\n");
      out.write("                                                    <option value=\"");
      out.print(get.getIdtraderCategory());
      out.write('"');
      out.write('>');
      out.print(get.getTraderCategoryDesc());
      out.write("</option>\n");
      out.write("                                                    ");

                                                        }
                                                    
      out.write("\n");
      out.write("                                                </select>\n");
      out.write("                                            </div>\n");
      out.write("                                            ");

                                                List possibleParents = new Trader_Util().getTrader_by_trader_grossiste("");
                                            
      out.write("\n");
      out.write("                                            <div class=\"col-lg-6\" class=\"form-group\">\n");
      out.write("                                                <label>Fournisseur</label>\n");
      out.write("\n");
      out.write("                                                <select  id=\"fourn\" name=\"fourn\" class=\"form-control\">\n");
      out.write("                                                    <option value=\"\">Select un fournisseur </option>\n");
      out.write("\n");
      out.write("                                                    ");
                                for (int i = 0; i < possibleParents.size(); i++) {
                                                            Trader get = (Trader) possibleParents.get(i);
                                                    
      out.write("\n");
      out.write("                                                    <option value=\"");
      out.print(get.getIdtrader());
      out.write('"');
      out.write('>');
      out.print(get.getTraderCompany());
      out.write("</option>\n");
      out.write("                                                    ");

                                                        }
                                                    
      out.write("\n");
      out.write("                                                </select>\n");
      out.write("                                            </div>\n");
      out.write("                                            <div class=\"col-lg-12\" >\n");
      out.write("                                                <div style=\"float:right\">\n");
      out.write("                                                    <br/>\n");
      out.write("                                                    <button type=\"reset\" class=\"btn btn-info\">Annuler</button>\n");
      out.write("                                                    <button type=\"submit\" class=\"btn btn-success\"  >Enregistrer</button>\n");
      out.write("\n");
      out.write("                                                </div>\n");
      out.write("                                            </div>\n");
      out.write("                                        </form>\n");
      out.write("                                    </div>   \n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("\n");
      out.write("                        </div>\n");
      out.write("\n");
      out.write("                    </div>                        \n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            <!-- /#page-wrapper -->\n");
      out.write("\n");
      out.write("        </div>\n");
      out.write("        <!-- /#wrapper -->\n");
      out.write("\n");
      out.write("        ");
      out.write("\n");
      out.write("\n");
      out.write("<!-- jQuery -->\n");
      out.write("<script src=\"../lib/vendor/jquery/jquery.min.js\"></script>\n");
      out.write("\n");
      out.write("<!-- Bootstrap Core JavaScript -->\n");
      out.write("<script src=\"../lib/vendor/bootstrap/js/bootstrap.min.js\"></script>\n");
      out.write("\n");
      out.write("<!-- Metis Menu Plugin JavaScript -->\n");
      out.write("<script src=\"../lib/vendor/metisMenu/metisMenu.min.js\"></script>\n");
      out.write("\n");
      out.write("<!-- Morris Charts JavaScript -->\n");
      out.write("<script src=\"../lib/vendor/raphael/raphael.min.js\"></script>\n");
      out.write("<script src=\"../lib/vendor/morrisjs/morris.min.js\"></script>\n");
      out.write("\n");
      out.write("<!-- Flot Charts JavaScript -->\n");
      out.write("<script src=\"../lib/vendor/flot/excanvas.min.js\"></script>\n");
      out.write("<script src=\"../lib/vendor/flot/jquery.flot.js\"></script>\n");
      out.write("<script src=\"../lib/vendor/flot/jquery.flot.pie.js\"></script>\n");
      out.write("<script src=\"../lib/vendor/flot/jquery.flot.resize.js\"></script>\n");
      out.write("<script src=\"../lib/vendor/flot/jquery.flot.time.js\"></script>\n");
      out.write("<script src=\"../lib/vendor/flot-tooltip/jquery.flot.tooltip.min.js\"></script>\n");
      out.write("\n");
      out.write("<!-- Custom Theme JavaScript -->\n");
      out.write("<script src=\"../lib/dist/js/sb-admin-2.js\"></script>\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\" src=\"https://cdn.datatables.net/v/bs/dt-1.10.20/datatables.min.js\"></script>");
      out.write("\n");
      out.write("        <!-- data -->\n");
      out.write("        <script src=\"./data/data_graph.js\"></script>\n");
      out.write("        <script>\n");
      out.write("            $(document).ready(function () {\n");
      out.write("                $(\"#type\").change(function () {\n");
      out.write("                    if (this.value == 2) {\n");
      out.write("                        $(\"#simNB\").show();\n");
      out.write("                        $(\"#simNumber\").prop('required',true);\n");
      out.write("                        $('#sndiv').hide();\n");
      out.write("                        $('#sndiv1').hide();\n");
      out.write("                        $('#typeStation').hide();\n");
      out.write("                        $(\"#typeStationId\").prop('required',false);\n");
      out.write("                        $(\"#sn2\").prop('required',false);\n");
      out.write("                        $(\"#sn1\").prop('required',false);\n");
      out.write("\n");
      out.write("\n");
      out.write("                    } else {\n");
      out.write("                        $('#simNB').hide();\n");
      out.write("                        $('#sndiv').show();\n");
      out.write("                        $('#sndiv1').show();\n");
      out.write("                        $('#typeStation').show();\n");
      out.write("                        $(\"#simNumber\").prop('required',false);\n");
      out.write("                         $(\"#typeStationId\").prop('required',true);\n");
      out.write("                        $(\"#sn2\").prop('required',true);\n");
      out.write("                        $(\"#sn1\").prop('required',true);\n");
      out.write("                    }\n");
      out.write("\n");
      out.write("\n");
      out.write("                });\n");
      out.write("                $(\"#company\").change(function () {\n");
      out.write("                    alert(\"The text has been changed.\");\n");
      out.write("                });\n");
      out.write("            });\n");
      out.write("        </script>\n");
      out.write("\n");
      out.write("    </body>\n");
      out.write("\n");
      out.write("</html>\n");
      out.write("\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
