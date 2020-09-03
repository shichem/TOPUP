
<%@page import="model_db.Station"%>
<%@page import="java.util.List"%>
<%@page import="model_helpers.station_Util"%>
<%-- 
    Document   : dashboard
    Created on : Mar 24, 2019, 3:06:28 PM
    Author     : macbookpro
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">

    <%@include file="template/head.jsp" %>

    <body>

        <div id="wrapper">

            <!-- Navigation -->

            <%@include file="template/navigation.jsp" %>

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">List Station</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <% if (request.getParameter("add") != null) { %>
                <div class="alert alert-success" role="alert">
                    Station ajoutet avec success
                </div>
                <% } %>
                <% if (request.getParameter("edit") != null) { %>
                <div class="alert alert-success" role="alert">
                    Station modifier avec success
                </div>
                <% } %>
                <% if (request.getParameter("del") != null) { %>
                <div class="alert alert-success" role="alert">
                    Station deactivate avec success
                </div>
                <% } %>
                <% if (request.getParameter("erreur") != null) { %>
                <div class="alert alert-danger" role="alert">
                    Erreur dans mis a jour du station contact admin 
                </div>
                <% } %>
                <div class="row">
                    <table id="example" class="display nowrap table dtr-inline collapsed" style="width:100%">
                        <thead>
                            <tr>
                                <th>Raison socaile</th>
                                <th>Status</th>
                                <th>Type</th>
                                <th>Server</th>
                                <th>Sn1</th>
                                <th>Sn2</th>                                     
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%                                station_Util stationType_Util = new station_Util();
                                List listStation = stationType_Util.getAllStation("");
                                System.out.println("className.methodName()" + listStation.size());
                                for (int i = 0; i < listStation.size(); i++) {
                                    Station station = (Station) listStation.get(i);
                            %>
                            <tr>
                                <td><%=station.getTrader().getTraderCompany() %></td>
                                <td><%=station.getStatusInfo().getStatusInfoDesc() %></td>
                                <td><%=station.getStationType().getStationTypeDesc() %></td>
                                <td><%=station.getServerProfile().getServerAdress1() +"/"+station.getServerProfile().getServerAdress2() %></td> 
                                         <td><%=station.getStationSn1() %></td>
                                            <td><%=station.getStationSn2() %></td>
                                <td>
                                   <a href="./editStation.jsp?id=<%=station.getIdstation()%>"><i class="fa fa-edit fa-fw"></i></a>/ 
                                    <a onclick="desctiveStation(<%=station.getIdstation()%>)" href="#"><i class="fa fa-trash fa-fw"></i></a>
                                </td>
                            </tr>
                            <%
                                }
                            %>    

                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Raison socaile</th>
                                <th>Status</th>
                                <th>Type</th>
                                <th>Server</th>
                                <th>Sn1</th>
                                <th>Sn2</th>                                     
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
                                            var table = $('#example').DataTable();

                                            $('#example tbody').on('click', 'tr', function () {
                                                var data = table.row(this).data();
                                                alert('You clicked on ' + data[0] + '\'s row');
                                            });


                                        });
                                        function desctiveStation(id) {
                                            var r = confirm("vous voulez désactiver la station");
                                            if (r == true) {
                                                window.location.href = "../DesactivateStation?id=" + id;
                                            } else {

                                            }
                                        }
        </script>
    </body>

</html>

