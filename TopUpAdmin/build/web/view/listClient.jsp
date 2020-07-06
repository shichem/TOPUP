
<%@page import="model_helpers.Trader_Util"%>
<%@page import="java.util.List"%>
<%@page import="model_db.Trader"%>

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
        <%
            List traders = new Trader_Util().getAllTrader("");
        %>
        <div id="wrapper">

            <!-- Navigation -->
            <%@include file="template/navigation.jsp" %>

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">List Client</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <% if (request.getParameter("add") != null) { %>
                <div class="alert alert-success" role="alert">
                    Client ajoutet avec success
                </div>
                <% } %>
                <% if (request.getParameter("edit") != null) { %>
                <div class="alert alert-success" role="alert">
                    Client modifier avec success
                </div>
                <% } %>
                <% if (request.getParameter("del") != null) { %>
                <div class="alert alert-success" role="alert">
                    Client deactivate avec success
                </div>
                <% } %>
                <% if (request.getParameter("erreur") != null) { %>
                <div class="alert alert-danger" role="alert">
                    Erreur dans mis a jour du client contact admin 
                </div>
                <% } %>
                <div class="row">

                    <table id="example" border="0" class="display nowrap dataTable dtr-inline collapsed" style="width:100%">
                        <thead>
                            <tr>
                                <th>Nom & Prenom</th>
                                <th>Adresse</th>
                                <th>Type</th>
                                <th>Category</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%                                for (int i = 0; i < traders.size(); i++) {
                                    Trader get = (Trader) traders.get(i);
                            %>
                            <tr>
                                <td><%=get.getTraderFname() + " " + get.getTraderLname()%></td>
                                <td><%=get.getTraderCompany()%></td>
                                <td><%=get.getTraderType().getTraderTypeDesc()%></td>
                                <td><%=get.getTraderCategory().getTraderCategoryDesc()%></td>
                                <td><a href="./editClient.jsp?id=<%=get.getIdtrader()%>"><i class="fa fa-edit fa-fw"></i></a>/                                    
                                    <a onclick="desctiveClient(<%=get.getIdtrader()%>)" href="#"><i class="fa fa-trash fa-fw"></i></a>
                                    /<a href="#" id="modal_provider" ><i class="fa fa-credit-card fa-fw"></i></a></td>

                            </tr>
                            <%
                                }
                            %>

                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Nom & Prenom</th>
                                <th>Adresse</th>
                                <th>Type</th>
                                <th>Category</th>
                                <th>Action</th>
                            </tr>
                        </tfoot>
                    </table>                         
                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="add_credit" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form role="form" action="../AddClient" method="POST" >
                                <div class="row"  id="providerdiv"></div>
                                
                            </form>
                        </div>
                        i
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
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
                                            $("#modal_provider").click(function () {
                                                $.ajax({

                                                    url: "../OperatorForTrader",
                                                    success: function (data) {
                                                       // alert(data);
                                                          $("#providerdiv").append(data);

                                                          $("#add_credit").modal('show');
                                                    }
                                                });
                                              
                                            });

                                        });

                                        function desctiveClient(id) {
                                            var r = confirm("vous voulez désactiver le client");
                                            if (r == true) {
                                                window.location.href = "../desactiveClient?id=" + id;
                                            } else {

                                            }
                                        }
        </script>
    </body>

</html>

