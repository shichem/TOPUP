
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
                List traders = new ProviderClient_Util().getAllTrader_ForProvider();
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
                                <th>Grossiste</th>
                                <th>Client</th>
                                <th>Operator</th>
                                <th>Solde</th>
                                <th>Pended solde</th> 
                                <th>Date affectation</th> 
                                <th>Limit transact</th> 

                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%                                for (int i = 0; i < traders.size(); i++) {
                                    ProviderClient client = (ProviderClient) traders.get(i);
                            %>
                            <tr>
                                <td><%= client.getTraderByIdprovider().getTraderFname() %></td>
                                <td><%= client.getTraderByIdclient().getTraderFname() %></td>
                                <td><%= client.getOperator().getOperatorDesc()%></td>
                                <td><%=client.getSolde() %></td>
                                <td><%=client.getPendedSolde()%></td>
                                <td><%=client.getDateAffect().toString() %></td>
                                <td><%=client.getLimitTransact()%></td>

                                <td>
                                    <a onclick="addSold(<%= client.getIdproviderClient() %>)"  href="#" data-id="<%= client.getIdproviderClient()%>" id="modal_provider-<%= client.getIdproviderClient() %>" data-toggle="tooltip" data-placement="left" title="ajout sold "> <i class="fa fa-credit-card fa-fw"></i><span>Ajout sold </span></a> /
                                    <a onclick="debitSold(<%= client.getIdproviderClient() %>)" href="#" data-id="<%= client.getIdproviderClient()%>" id="debit-sold-<%= client.getIdproviderClient()%>" data-toggle="tooltip" data-placement="left" title="debit sold "> <i class="fa fa-eraser fa-fw"></i><span>Debit sold </span></a> /
                                    <a onclick="desctiveClient(<%=client.getIdproviderClient()%>)" href="#" data-toggle="tooltip" data-placement="left" title="desactive Client"> <i class="fa fa-trash fa-fw"></i><span>Desactive </span></a>                       
                                </td>

                            </tr>
                            <%
                                }
                            %>

                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Grossiste</th>
                                <th>Client</th>
                                <th>Operator</th>
                                <th>Solde</th>
                                <th>Pended solde</th> 
                                <th>Date affectation</th> 
                                <th>Limit transact</th> 
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
                        <form  action="../AddSoldClientProvider" method="POST">
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
                        <form  action="../DebitSoldClientProvider" method="POST">
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
                                                 responsive: true
                                            });


                                            ('#example tbody').on('click', 'tr', function () {
                                                var data = table.row(this).data();
                                            });

                                        }
                                        );



                                        function desctiveClient(id) {
                                            var r = confirm("vous voulez désactiver le client");
                                            if (r == true) {
                                                window.location.href = "../DesctivateProvider?id=" + id;
                                            } else {

                                            }
                                        }
                                        function addSold(id) {
                                            var clientId = id;

                                            $.ajax({
                                                url: "../OperatorForProviderClient?id=" + clientId,
                                                success: function (data) {
                                                    // alert(data);
                                                    $("#providerdiv").empty();
                                                    $("#providerdiv").append(data);
                                                    $("#providerdivDebit").empty();
                                                    $("#add_credit").modal('show');
                                                    $("#amount").change(function () {
                                                        $('#new').text("")
                                                        if ($("#amount").val().length != 0) {
                                                            var old = parseFloat($('#label').text());
                                                            $('#new').text(old + parseFloat($("#amount").val()));
                                                            $("#new").css("color", "green");
                                                        }

                                                    });
                                                    
                                                }
                                            });

                                        }

                                        function debitSold(id) {

                                            var clientId = id;
                                            $.ajax({
                                                url: "../OperatorForProviderClient?id=" + clientId,
                                                success: function (data) {
                                                    // alert(data);
                                                    $("#providerdiv").empty();
                                                    $("#providerdivDebit").empty();
                                                    $("#providerdivDebit").append(data);
                                                    $("#debit_credit").modal('show');
                                                    $("#amount").change(function () {
                                                        $('#new').text("")
                                                        if ($("#amount").val().length != 0) {
                                                            var old = parseFloat($('#label').text());
                                                            $('#new').text(old - parseFloat($("#amount").val()));
                                                            $("#new").css("color", "red");
                                                        }
                                                    });
                                                    
                                                }
                                            });
                                        }

        </script>
    </body>

</html>

