
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
    Document   : dashboard
    Created on : Mar 24, 2019, 3:06:28 PM
    Author     : macbookpro
--%>


<%@page import="model_db.SimInfo"%>
<%@page import="java.util.List"%>
<%@page import="custom_vars.staticVars"%>
<%@page import="model_helpers.SimInfo_Util"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">

    <%@include file="template/head.jsp" %>
    <%
    %>
    <body>

        <div id="wrapper">

            <!-- Navigation -->
            <%@include file="template/navigation.jsp" %>


            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <br>
                        <h1 ></h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <div class="row">                    
                    <div class="col-lg-4 col-md-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img src="./template/mobilis.svg" width="130" height="70">                                
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%=new SimInfo_Util().getLasSoldByOperator("MOBILIS")%></div>
                                        <div>Sold Mobilis </div>
                                    </div>
                                </div>
                            </div>
                           
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">                                
                                        <img src="./template/logo.svg" width="60" height="70">                                
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"></div>
                                        <div class="huge"><%=new SimInfo_Util().getLasSoldByOperator("DJEZZY") %></div>
                                        <div>Sold Djezzy</div>
                                    </div>
                                </div>
                            </div>
                          
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="panel panel-default ">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img src="./template/ooredoo.svg" width="130" height="70">                                      </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%=new  SimInfo_Util().getLasSoldByOperator("OOREDOO")%></div>
                                        <div>Sold Ooredoo  </div>
                                    </div>
                                </div>
                            </div>
                         
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                         <i class="fa fa-sim-card fa-5x"></i>                             
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%=new  SimInfo_Util().countSimInfoByStatus(staticVars.status_ENT_Actif) %></div>
                                        <div>Number du sim Actif</div>
                                    </div>
                                </div>
                            </div>
                           
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">                                
                                          <i class="fa fa-ban fa-5x"></i>                              
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"></div>
                                        <div class="huge"><%=new  SimInfo_Util().countSimInfoByStatus(staticVars.status_ENT_Bloque) %></div>
                                        <div>Number du sim bloque</div>
                                    </div>
                                </div>
                            </div>
                           
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="panel panel-default ">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                          <i class="fa fa-times-circle fa-5x"></i>                        
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%=new  SimInfo_Util().countSimInfoByStatus(staticVars.status_ENT_Inactif) %></div>
                                        <div>Number du sim Inactif</div>
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                    </div>

                    <div class="col-lg-12" >
                        <div style="float:right">
                            <br/>
                            <a href="../RefreshSim" type="btn" class="btn btn-success"   >Mettre à jour</a>

                        </div>
                    </div>
                </div>       
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">List Sim</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>

                <!-- /.row -->
                <% if (request.getParameter("add") != null) { %>
                <div class="alert alert-success" role="alert">
                    Sim ajoutet avec success
                </div>
                <% } %>
                Sim modifier avec success
                <% if (request.getParameter("edit") != null) { %>
                <div class="alert alert-success" role="alert">
                </div>
                <% } %>
                <% if (request.getParameter("del") != null) { %>
                <div class="alert alert-success" role="alert">
                    Sim supprime  avec success
                </div>
                <% } %>
                 <% if (request.getParameter("bloque") != null) { %>
                <div class="alert alert-success" role="alert">
                    Sim bloque  avec success
                </div>
                <% } %>
                   <% if (request.getParameter("active") != null) { %>
                <div class="alert alert-success" role="alert">
                    Sim active  avec success
                </div>
                <% } %>
                <% if (request.getParameter("erreur") != null) { %>
                <div class="alert alert-danger" role="alert">
                    Erreur dans mis a jour du Sim contact admin 
                </div>f
                <% } %>
                <div class="row">
                    <table id="example" class="display nowrap table dtr-inline collapsed" style="width:100%" >
                        <thead>
                            <tr>
                                <th>Sim number</th>
                                <th>Operateur</th>
                                <th>Status</th>
                                <th>last sold</th>
                                <th>Sold estimed</th>
                                <th>Port</th>
                                <th>Type</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>

                            <%    List<SimInfo> simInfos = new SimInfo_Util().getAllSimInfo("");

                                System.out.println("className.methodName()" + simInfos.size());
                                for (int i = 0; i < simInfos.size(); i++) {
                                    SimInfo info = (SimInfo) simInfos.get(i);
                            %>
                            <tr>
                                <td><%=info.getSimnumber()%></td>
                                <td><%=info.getOperator().getOperatorDesc()%></td>
                                <td><%=info.getStatusInfo().getStatusInfoDesc()%></td>
                                <td><%=info.getLastSolde()%></td>
                                <td><%=info.getLastEstimatedSolde()%></td>
                                <td><%=info.getPortInfo().getPortDesc()%></td>
                                <td><%=info.getSimType().getSimTypeDesc()%></td>
                                <td>

                                    <% if (info.getStatusInfo().getStatusInfoDesc().equals(staticVars.status_ENT_Bloque)) {
                                    %>
                                    <a onclick="ActivateSimInfo(<%=info.getIdsimInfo()%>)" href="#" data-toggle="tooltip" data-placement="left" title="actviate"><i class="fa fa-sim-card fa-fw"></i></a> / 
                                        <%
                                            }
                                            if (info.getStatusInfo().getStatusInfoDesc().equals(staticVars.status_ENT_Actif)) {
                                        %>                                
                                    <a onclick="BloqueSimInfo(<%=info.getIdsimInfo()%>)" href="#" data-toggle="tooltip" data-placement="left" title="Bloque"><i class="fa fa-ban fa-fw"></i></a> / 
                                        <%
                                            }
                                        %>
                                    <a onclick="desctiveSimInfo(<%=info.getIdsimInfo()%>)" href="#"  data-toggle="tooltip" data-placement="left" title="delete"><i class="fa fa-trash fa-fw"></i></a></td>
                            </tr>
                            <%
                                }
                            %>    
                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Sim number</th>
                                <th>Operateur</th>
                                <th>Status</th>
                                <th>last sold</th>
                                <th>Sold estimed</th>
                                <th>Port</th>
                                <th>Type</th>
                                <th>Action</th>
                            </tr>
                        </tfoot>
                    </table>                         
                </div>
            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

        <%@include file="template/script.jsp" %>
        <!-- data -->
        <script src="./data/data_graph.js"></script>
        <script>
                                $(document).ready(function () {
                                var table =   $('#example').dataTable({
                                                 responsive: true
                                            });
                                $('#example tbody').on('click', 'tr', function () {
                                var data = table.row(this).data();
                                //   alert('You clicked on ' + data[0] + '\'s row');
                                });
                                });
                                function BloqueSimInfo(id){
                                var r = confirm("vous voulez bloque sim");
                                if (r == true) {
                                window.location.href = "../BloqueSim?id=" + id;
                                } else {

                                }
                                }

                                function ActivateSimInfo(id) {
                                var r = confirm("vous voulez active sim ");
                                if (r == true) {
                                window.location.href = "../ActiveSim?id=" + id;
                                } else {

                                }
                                }
                                function desctiveSimInfo(id) {
                                var r = confirm("vous voulez désactiver sim");
                                if (r == true) {
                                window.location.href = "../DesactiveSim?id=" + id;
                                } else {

                                }
                                }
        </script>
    </script>
</body>

</html>

