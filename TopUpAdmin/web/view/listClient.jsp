
<%@page import="custom_vars.staticVars"%>
<%@page import="model_db.ProviderClient"%>
<%@page import="model_helpers.ProviderClient_Util"%>
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

        <div id="wrapper">

            <!-- Navigation -->
            <%@include file="template/navigation.jsp" %>
            <%                System.out.println("className.methodName()" + session.getAttribute("Id").toString());
                Integer userID = Integer.parseInt(session.getAttribute("Id").toString());
                System.out.println("className.methodName() userID ==" + userID);
                List traders = new ProviderClient_Util().getAllTrader_ForProvider(userID, "");
            %>
            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">List Client</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->

                <% if (request.getParameter("succesDebitSold") != null) { %>
                <div class="alert alert-success" role="alert">
                    Sold debit avec success pour le client 
                </div>
                <% } %>
                <% if (request.getParameter("succesSold") != null) { %>
                <div class="alert alert-success" role="alert">
                    Sold ajout avec success pour le client 
                </div>
                <% } %>
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

                    <table id="example" border="0" class="display nowrap table dtr-inline collapsed" style="width:100%">
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
                                    ProviderClient client = (ProviderClient) traders.get(i);
                                    Trader get = client.getTraderByIdclient();
                            %>
                            <tr>
                                <td><%=get.getTraderFname() + " " + get.getTraderLname()%></td>
                                <td><%=get.getTraderCompany()%></td>
                                <td><%=get.getTraderType().getTraderTypeDesc()%></td>
                                <td><%=get.getTraderCategory().getTraderCategoryDesc()%></td>
                                <td><a href="./editClient.jsp?id=<%=get.getIdtrader()%>"><i class="fa fa-edit fa-fw" data-toggle="tooltip" data-placement="left" title="Modefie Client " ></i><span>Edit </span></a>/                                    
                                    <a onclick="desctiveClient(<%=get.getIdtrader()%>)" href="#" data-toggle="tooltip" data-placement="left" title="desactive Client"> <i class="fa fa-trash fa-fw"></i><span>Desactive </span></a>
                                    /<a onclick="addSold(<%=get.getIdtrader()%>)"  href="#" data-id="<%=get.getIdtrader()%>" id="modal_provider-<%=get.getIdtrader()%>" data-toggle="tooltip" data-placement="left" title="ajout sold "> <i class="fa fa-credit-card fa-fw"></i><span>Ajout sold </span></a> /
                                    <a onclick="debitSold(<%=get.getIdtrader()%>)" href="#" data-id="<%=get.getIdtrader()%>" id="debit-sold-<%=get.getIdtrader()%>" data-toggle="tooltip" data-placement="left" title="debit sold "> <i class="fa fa-eraser fa-fw"></i><span>Debit sold </span></a> /
                                            <% if (get.getTraderCategory().getTraderCategoryDesc().equals(staticVars.traderCategory_Grossiste)) {%>
                                    <a o href="./listClientGro.jsp?id=<%=get.getIdtrader()%>" > <i class="fa fa-list fa-fw"></i><span>List Client </span></a>
                                            <% }%>
                                </td>

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
                        <form  action="../AddSold" method="POST">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLongTitle">Ajouter sold </h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">

                                <div class="row"  id="providerdiv"></div>

                            </div>


                            <div class="modal-footer">
                                <button type="reset" class="btn btn-info"  data-dismiss="modal">Annuler</button>
                                <button type="submit" class="btn btn-success"  >Enregistrer</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="debit_credit" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <form  action="../DebitSold" method="POST">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLongTitle">Debit sold </h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">

                                <div class="row"  id="providerdivDebit"></div>

                            </div>

                            <div class="modal-footer">
                                <button type="reset" class="btn btn-info"  data-dismiss="modal">Annuler</button>
                                <button type="submit" class="btn btn-success"  >Enregistrer</button>
                            </div>
                        </form>
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



                                            $('#example').dataTable({
                                            });


                                            ('#example tbody').on('click', 'tr', function () {
                                                var data = table.row(this).data();
                                            });

                                        }
                                        );



                                        function desctiveClient(id) {
                                            var r = confirm("vous voulez désactiver le client");
                                            if (r == true) {
                                                window.location.href = "../desactiveClient?id=" + id;
                                            } else {

                                            }
                                        }
                                        function addSold(id) {
                                            var clientId = id;

                                            $.ajax({
                                                url: "../OperatorForTrader?id=" + clientId,
                                                success: function (data) {
                                                    // alert(data);
                                                    $("#providerdiv").empty();
                                                    $("#providerdiv").append(data);
                                                    $("#providerdivDebit").empty();
                                                    $("#add_credit").modal('show');
                                                    $("#amount_DJEZZY").change(function () {
                                                        $('#new_DJEZZY').text("")
                                                        if ($("#amount_DJEZZY").val().length != 0) {
                                                            var old = parseFloat($('#label_DJEZZY').text());
                                                            $('#new_DJEZZY').text(old + parseFloat($("#amount_DJEZZY").val()));
                                                            $("#new_DJEZZY").css("color", "green");
                                                        }

                                                    });
                                                    $("#amount_MOBILIS").change(function () {
                                                        $('#new_MOBILIS').text("")
                                                        if ($("#amount_MOBILIS").val().length != 0) {
                                                            var old = parseFloat($('#label_MOBILIS').text());
                                                            $('#new_MOBILIS').text(old + parseFloat($("#amount_MOBILIS").val()));
                                                            $("#new_MOBILIS").css("color", "green");
                                                        }

                                                    });
                                                    $("#amount_OOREDOO").change(function () {
                                                        $('#new_OOREDOO').text("")
                                                        if ($("#amount_OOREDOO").val().length != 0) {
                                                            var old = parseFloat($('#label_OOREDOO').text());
                                                            $('#new_OOREDOO').text(old + parseFloat($("#amount_OOREDOO").val()));
                                                            $("#new_OOREDOO").css("color", "green");
                                                        }

                                                    });
                                                }
                                            });

                                        }

                                        function debitSold(id) {

                                            var clientId = id;
                                            $.ajax({
                                                url: "../OperatorForTrader?id=" + clientId,
                                                success: function (data) {
                                                    // alert(data);
                                                    $("#providerdiv").empty();
                                                    $("#providerdivDebit").empty();
                                                    $("#providerdivDebit").append(data);
                                                    $("#debit_credit").modal('show');
                                                    $("#amount_DJEZZY").change(function () {
                                                        $('#new_DJEZZY').text("")
                                                        if ($("#amount_DJEZZY").val().length != 0) {
                                                            var old = parseFloat($('#label_DJEZZY').text());
                                                            $('#new_DJEZZY').text(old - parseFloat($("#amount_DJEZZY").val()));
                                                            $("#new_DJEZZY").css("color", "red");
                                                        }
                                                    });
                                                    $("#amount_MOBILIS").change(function () {
                                                        $('#new_MOBILIS').text("")
                                                        if ($("#amount_MOBILIS").val().length != 0) {
                                                            var old = parseFloat($('#label_MOBILIS').text());
                                                            $('#new_MOBILIS').text(old - parseFloat($("#amount_MOBILIS").val()));
                                                            $("#new_MOBILIS").css("color", "red");
                                                        }

                                                    });
                                                    $("#amount_OOREDOO").change(function () {
                                                        $('#new_OOREDOO').text("")
                                                        if ($("#amount_OOREDOO").val().length != 0) {
                                                            var old = parseFloat($('#label_OOREDOO').text());
                                                            $('#new_OOREDOO').text(old - parseFloat($("#amount_OOREDOO").val()));
                                                            $("#new_OOREDOO").css("color", "red");
                                                        }


                                                    });
                                                }
                                            });
                                        }

        </script>
    </body>

</html>

