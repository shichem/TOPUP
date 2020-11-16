
<%@page import="model_helpers.station_Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model_db.Station"%>
<%@page import="model_db.ServerProfile"%>
<%@page import="model_helpers.ServerProfile_Util"%>
<%@page import="model_db.StationType"%>
<%@page import="model_helpers.StationType_Util"%>
<%@page import="model_helpers.ProviderClient_Util"%>
<%@page import="model_db.ProviderClient"%>
<%@page import="model_db.TraderCategory"%>
<%@page import="model_helpers.TraderCategory_Util"%>
<%@page import="model_helpers.TraderType_Util"%>
<%@page import="model_db.TraderType"%>
<%@page import="custom_vars.staticVars"%>
<%@page import="java.util.List"%>
<%@page import="model_helpers.Trader_Util"%>
<%@page import="model_db.Trader"%>
<%@page import="model_db.Station"%>
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
    <%        if (request.getParameter("id") != null) {
            String clientId = request.getParameter("id");
            Trader trader = new Trader_Util().getTradfer_by_id(Integer.parseInt(clientId), "");

            List listStation = new station_Util().getStations_by_trader(trader, "");
            //         Station station = null;
            if (listStation.size() != 0) {
                //  station = (Station) listStation.get(0);
            }
            // System.out.println("className.methodName()"+listStation.size());

    %>
    <body>

        <div id="wrapper">

            <!-- Navigation -->
            <%@include file="template/navigation.jsp" %>

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Fichier client</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <% if (request.getParameter("succes") != null) { %>
                <div class="alert alert-success" role="alert">
                    Client modefie avec success
                </div>
                <% } %>
                <% if (request.getParameter("erreur") != null) { %>
                <div class="alert alert-danger" role="alert">
                    Erreur dans mis a jour du client contact admin 
                </div>
                <% }%>
                
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
                <div class="row">
                    <div class="col-lg-12">

                        <ul class="nav nav-tabs">
                            <li class="active"><a data-toggle="tab" href="#home">Info Client</a></li>
                            <li><a data-toggle="tab" href="#menu1">Affectation</a></li>
                            <li><a data-toggle="tab" href="#menu2">Station</a></li>
                            <li><a data-toggle="tab" href="#menu3">Sold Transaction</a></li>
                            <li><a data-toggle="tab" href="#menu4">TopUp Transaction</a></li>
                                <% if (!trader.getTraderCategory().getTraderCategoryDesc().equals(staticVars.traderCategory_Detaillant)) { %>
                            <li><a data-toggle="tab" href="#menu5">List client</a></li>
                                <% }%>
                        </ul>
                    </div>
                    <div class="tab-content">
                        <div id="home" class="tab-pane fade in active">

                            <div class="col-lg-12">
                                <br/>
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        Information sur Client  
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <form role="form" action="../EditClient" method="POST" >
                                                    <input type="hidden" name="id" id="id" value="<%= clientId%>" /> 
                                                    <div class="col-lg-6" class="form-group">
                                                        <label>Nom</label>
                                                        <input id="fname" value="<%=trader.getTraderFname()%>"  name="fname" class="form-control" placeholder="Enter nom">
                                                    </div>
                                                    <div class="col-lg-6" class="form-group">
                                                        <label>Prenom</label>
                                                        <input id="laneme" value="<%=trader.getTraderLname()%>" name="lname" class="form-control" placeholder="Enter  prenom">
                                                    </div>
                                                    <div class="col-lg-6" class="form-group">
                                                        <label>Raison social</label>
                                                        <input id="raison_soc" value="<%=trader.getTraderCompany()%>" name="raison_soc" class="form-control" placeholder="Enter  prenom">
                                                    </div>
                                                    <div class="col-lg-6" class="form-group">
                                                        <label>Adresse</label>
                                                        <input id="adresse" value="<%=trader.getAdresse()%>" name="adresse" class="form-control" placeholder="Ente  adrees  du client">
                                                    </div>

                                                    <div class="col-lg-6" class="form-group">
                                                        <label>wilaya</label>
                                                        <input id="wilaya" name="wilaya" value="<%=trader.getWilaya()%>" class="form-control" placeholder="Entre wilaya du client ">
                                                    </div>
                                                    <div class="col-lg-6" class="form-group">
                                                        <label>commune</label>
                                                        <input id="commune" name="commune" value="<%=trader.getCommune()%>" class="form-control" placeholder="Entre commune du client ">
                                                    </div>

                                                    <div class="col-lg-6" class="form-group">
                                                        <label>email 1</label>
                                                        <input id="email1" name="email1" value="<%=trader.getEmail1()%>" class="form-control" placeholder="Enter email client">
                                                    </div>
                                                    <div class="col-lg-6" hidden="" class="form-group">
                                                        <label>email 2</label>
                                                        <input id="email2" name="email2" value="<%=trader.getEmail2()%>" class="form-control" placeholder="Enter email client">
                                                    </div>
                                                    <div class="col-lg-6" class="form-group">
                                                        <label>telephone  1</label>
                                                        <input id="telephone1" name="telephone1" value="<%=trader.getTel1()%>" class="form-control" placeholder="Enter telephone client">
                                                    </div>
                                                    <div class="col-lg-6" hidden="" class="form-group">
                                                        <label>telephone 2</label>
                                                        <input id="telephone2" name="telephone2"  value="<%=trader.getTel2()%>" class="form-control" placeholder="Enter telephone client">
                                                    </div>
                                                    <%                                                List tistType = new TraderType_Util().getAllTraderType("");
                                                        String readonly = "";
                                                        if (trader.getTraderCategory().getTraderCategoryDesc().equals(staticVars.traderCategory_Grossiste)) {
                                                            readonly = "readonly=''";
                                                        }
                                                    %>
                                                    <div class="col-lg-6" class="form-group">
                                                        <label>client type</label>
                                                        <select  required="" id="type" name="type" class="form-control" <%=readonly%>>
                                                            <option value="">Select le type du client </option>

                                                            <%

                                                                for (int i = 0; i < tistType.size(); i++) {
                                                                    TraderType get = (TraderType) tistType.get(i);
                                                                    String select = "";
                                                                    if (get.getIdtraderType() == trader.getTraderType().getIdtraderType()) {
                                                            %>
                                                            <option value="<%=get.getIdtraderType()%>"  selected="selected">  <%=get.getTraderTypeDesc()%></option>
                                                            <%
                                                            } else {
                                                            %>
                                                            <option value="<%=get.getIdtraderType()%>"  ${select}>  <%=get.getTraderTypeDesc()%></option>
                                                            <%}

                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                    <div class="col-lg-6" id="simNB"  ${ 2 == trader.getTraderType().getIdtraderType()  ? 'hidden' : ''}  class="form-group">
                                                        <label>Sim number</label>
                                                        <input id="simNumber"  name="simNumber" value="<%=trader.getSimnumber()%>" class="form-control" placeholder="Ente sim number ">
                                                    </div>


                                                    <%
                                                        List listcategory = new TraderCategory_Util().getAllTraderCategory("");
                                                    %>
                                                    <div  class="col-lg-6" class="form-group">
                                                        <label>client category</label>
                                                           <input class="form-control" value="<%=trader.getTraderCategory().getTraderCategoryDesc()%>" readonly="" />
                                                           <select style="visibility: hidden" required="" id="catgory" name="catgory" class="form-control" readonly=" ">
                                                            <option value="">Select un category client </option>

                                                            <%
                                                                for (int i = 0; i < listcategory.size(); i++) {
                                                                    TraderCategory get = (TraderCategory) listcategory.get(i);
                                                                    String select = "";
                                                                    if (get.getIdtraderCategory() == trader.getTraderCategory().getIdtraderCategory()) {
                                                                        select = "selected='selected'";
                                                                    }

                                                            %>
                                                            <option value="<%=get.getIdtraderCategory()%>"   <%=select%> > <%= get.getTraderCategoryDesc()%></option>

                                                            <%
                                                                }
                                                            %>
                                                        </select>
                                                    </div>

                                                    <%
                                                        List possibleParents = new Trader_Util().getTrader_by_trader_grossiste("");
                                                        String readonly1 = "";
                                                        if (trader.getTraderCategory().getTraderCategoryDesc().equals(staticVars.traderCategory_Grossiste)) {
                                                            readonly1 = "readonly=''";
                                                        }
                                                    %>
                                                         <% 
                                                               String Categoryhidden = "";
                                                               if (trader.getTraderCategory().getTraderCategoryDesc().equals(staticVars.traderCategory_SuperGrossiste)) {
                                                                Categoryhidden = "hidden";
                                                            }
                                                           %>

                                                           <div <%= Categoryhidden %> class="col-lg-6" class="form-group">
                                                        <label>Fournisseur</label>
                                                                                                              <select  id="fourn" name="fourn" class="form-control" <%=readonly1%>>
                                                            <option value="">Select un fournisseur </option>

                                                            <%
                                                                ProviderClient pc = new ProviderClient_Util().getProviderClientForTrader(trader.getIdtrader(), "");

                                                                for (int i = 0; i < possibleParents.size(); i++) {
                                                                    Trader get = (Trader) possibleParents.get(i);
                                                                    String select = "";

                                                                    if (pc != null) {
                                                                        if (get.getIdtrader() == pc.getTraderByIdprovider().getIdtrader()) {
                                                                            select = "selected='selected'";
                                                                        }
                                                                    }
                                                                    System.out.println("==>" + select);
                                                            %>
                                                            <option value="<%=get.getIdtrader()%>"  <%=select%>   ><%=get.getTraderFname()%></option>
                                                            <%
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                    <div class="col-lg-12" >
                                                        <div style="float:right">
                                                            <br/>

                                                            <button type="submit" class="btn btn-success"  >Modifier</button>

                                                        </div>
                                                    </div>
                                                </form>
                                            </div>   
                                        </div>
                                    </div>

                                </div>

                            </div>
                        </div>
                        <div id="menu1" class="tab-pane fade">

                            <div class="col-md-12">
                                <br/>
                                <%                System.out.println("className.methodName()" + session.getAttribute("Id").toString());
                                    Integer userID = Integer.parseInt(session.getAttribute("Id").toString());
                                    System.out.println("className.methodName() userID ==" + userID);
                                    List traders = new ProviderClient_Util().getAllProvider_ForClient(trader.getIdtrader());
                                %>
                                <table id="tabAff" border="0" class="display nowrap table dtr-inline collapsed" style="width:100%">
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
                                            <td><%= client.getTraderByIdprovider().getTraderFname()%></td>
                                            <td><%= client.getTraderByIdclient().getTraderFname()%></td>
                                            <td><%= client.getOperator().getOperatorDesc()%></td>
                                            <td><%=client.getSolde()%></td>
                                            <td><%=client.getPendedSolde()%></td>
                                            <td><%=client.getDateAffect().toString()%></td>
                                            <td><%=client.getLimitTransact()%></td>

                                            <td>
                                                <a onclick="addSold(<%= client.getIdproviderClient()%>)"  href="#" data-id="<%= client.getIdproviderClient()%>" id="modal_provider-<%= client.getIdproviderClient()%>" data-toggle="tooltip" data-placement="left" title="ajout sold "> <i class="fa fa-credit-card fa-fw"></i><span>Ajout sold </span></a> /
                                                <a onclick="debitSold(<%= client.getIdproviderClient()%>)" href="#" data-id="<%= client.getIdproviderClient()%>" id="debit-sold-<%= client.getIdproviderClient()%>" data-toggle="tooltip" data-placement="left" title="debit sold "> <i class="fa fa-eraser fa-fw"></i><span>Debit sold </span></a> 
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
                        <div id="menu2" class="tab-pane fade">

                            <div class="col-md-12"><br/>
                                <table id="tableStation" class="display nowrap table dtr-inline collapsed" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>Status</th>
                                            <th>Type</th>
                                            <th>Server</th>
                                            <th>Sn1</th>
                                            <th>Sn2</th>                                     
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%  station_Util stationType_Util = new station_Util();
                                            List listStationList = stationType_Util.getStations_by_trader(trader, "");
                                            System.out.println("className.methodName()" + listStation.size());
                                            for (int i = 0; i < listStationList.size(); i++) {
                                                Station station = (Station) listStationList.get(i);
                                        %>
                                        <tr>
                                            <td><%=station.getStatusInfo().getStatusInfoDesc()%></td>
                                            <td><%=station.getStationType().getStationTypeDesc()%></td>
                                            <td><%=station.getServerProfile().getServerAdress1() + "/" + station.getServerProfile().getServerAdress2()%></td> 
                                            <td><%=station.getStationSn1()%></td>
                                            <td><%=station.getStationSn2()%></td>
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
                        <div id="menu3" class="tab-pane fade">

                            <div class="col-md-12">  <br/>
                                <table id="tabSold" class="display nowrap table dtr-inline collapsed" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>Grossiste</th>
                                            <th>Client</th>
                                            <th>Transaction Amount</th>
                                            <th>New sold</th>
                                            <th>Old sold</th>
                                            <th>Date Transaction  </th>
                                            <th>Status Transaction  </th>
                                            <th>User  </th>

                                        </tr>
                                    </thead>
                                    <tbody>                      
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th>Grossiste</th>
                                            <th>Client</th>
                                            <th>Transaction Amount</th>
                                            <th>New sold</th>
                                            <th>Old sold</th>
                                            <th>Date Transaction  </th>
                                            <th>Status Transaction  </th>
                                            <th>User  </th>
                                        </tr>
                                    </tfoot>
                                </table>                    
                            </div>
                        </div>
                        <div id="menu4" class="tab-pane fade">
                            <div class="col-md-12">
                                <br/>
                                <table id="tabTopUp" class="display nowrap table dtr-inline collapsed" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>Client</th>
                                            <th>SimClient</th>
                                            <th>Offer</th>
                                            <th>Status Transaction  </th>
                                            <th>Type Transaction  </th>
                                            <th>Date Transaction  </th>
                                            <th>New sold</th>
                                            <th>Transaction Amount</th>
                                            <th>Real Transaction Amount</th>                                                      
                                            <th>Sent Message  </th>
                                            <th>Recived Message  </th>
                                        </tr>
                                    </thead>
                                    <tbody>                      
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th>Client</th>
                                            <th>SimClient</th>
                                            <th>Offer</th>
                                            <th>Status Transaction  </th>
                                            <th>Type Transaction  </th>
                                            <th>Date Transaction  </th>
                                            <th>New sold</th>
                                            <th>Transaction Amount</th>
                                            <th>Real Transaction Amount</th>                                                      
                                            <th>Sent Message  </th>
                                            <th>Recived Message  </th>
                                        </tr>
                                    </tfoot>
                                </table>                         
                            </div>
                        </div>
                        <% if (!trader.getTraderCategory().getTraderCategoryDesc().equals(staticVars.traderCategory_Detaillant)) { %>
                        <div id="menu5" class="tab-pane fade">
                            <div class="col-md-12">
                                <br/>
                                <table id="tabClient" border="0" class="display nowrap table dtr-inline collapsed" style="width:100%">
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
                                        <%

                                            List treaderCLient = new ProviderClient_Util().getAllTrader_ForProvider(trader.getIdtrader());
                                            for (int i = 0; i < treaderCLient.size(); i++) {
                                                ProviderClient client = (ProviderClient) treaderCLient.get(i);
                                                Trader get = client.getTraderByIdclient();
                                        %>
                                        <tr>
                                            <td><%=get.getTraderFname() + " " + get.getTraderLname()%></td>
                                            <td><%=get.getTraderCompany()%></td>
                                            <td><%=get.getTraderType().getTraderTypeDesc()%></td>
                                            <td><%=get.getTraderCategory().getTraderCategoryDesc()%></td>
                                            <td><a href="./editClient.jsp?id=<%=get.getIdtrader()%>"><i class="fa fa-edit fa-fw" data-toggle="tooltip" data-placement="left" title="Modefie Client " ></i><span>Edit </span></a>                                  
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

                        <% }%>
                    </div>
                </div>
            </div>
            <!-- /#page-wrapper -->
            <!-- add cerdit by  trader oprator-->
            <!-- Modal -->
            <div class="modal fade" id="add_credit" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <form  action="../AddSoldClientProviderEdit" method="POST">
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
                        <form  action="../DebitSoldClientProviderEdit" method="POST">
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

                                                    $(document).ready(function ()
                                                    {
                                                        $('#tabAff').dataTable({
                                                            responsive: true,
                                                            "bFilter": false
                                                        });
                                                        $('#tableStation').dataTable({responsive: true,
                                                            "bFilter": false});
                                                        $('#tabClient').dataTable({responsive: true,
                                                            "bFilter": false});
                                                        $('#tabSold').DataTable({
                                                            responsive: true,
                                                            "processing": true,
                                                            "serverSide": true,
                                                            "bFilter": false,
                                                            "bSort": false,
                                                            'ajax': {
                                                                'url': '../ListTransactionSold',
                                                                'data': function (data) {
                                                                    // Read values


                                                                    // Append to data
                                                                    data.status = "";
                                                                    data.type = "";
                                                                    data.provider = "";
                                                                    data.name = "<%= trader.getIdtrader() + "-" + trader.getTraderFname()%>";
                                                                    data.dateDebut = "";
                                                                    data.dateFin = "";
                                                                    data.minSold = "";
                                                                    data.maxSold = "";

                                                                }
                                                            },

                                                            lengthMenu: [[10, 25, 100, -1], [10, 25, 100, "All"]],

                                                            pageLength: 10
                                                        });

                                                        $('#tabTopUp').DataTable({
                                                            responsive: true,
                                                            "processing": true,
                                                            "serverSide": true,
                                                            "bFilter": false,
                                                            "bSort": false,
                                                            'ajax': {
                                                                'url': '../ListTransactionTopUp',
                                                                'data': function (data) {
                                                                    // Read values

                                                                    // Append to data
                                                                    data.status = "";
                                                                    data.type = "";
                                                                    data.name = "<%= trader.getIdtrader() + "-" + trader.getTraderFname()%>";
                                                                    ;
                                                                    data.dateDebut = "";
                                                                    data.dateFin = "";
                                                                    data.sim = "";
                                                                    data.offer = "";
                                                                    data.operator = "";
                                                                    data.minSold = "";
                                                                    data.maxSold = "";
                                                                }
                                                            },
                                                            lengthMenu: [[10, 25, 100, -1], [10, 25, 100, "All"]],
                                                            pageLength: 10,
                                                            buttons: [
                                                                {
                                                                    extend: 'excel',
                                                                    text: '<span class="fa fa-file-excel-o"></span> Excel Export',
                                                                    title: 'TransactionTopUp',

                                                                    exportOptions: {
                                                                        modifier: {
                                                                            search: 'applied',
                                                                            order: 'applied'
                                                                        }
                                                                    }
                                                                }
                                                            ],
                                                        });

                                                        if ($("#type").val() == 2) {
                                                            $("#simNB").show();
                                                            $("#simNumber").prop('required', true);



                                                        } else {
                                                            $('#simNB').hide();

                                                            $("simNumber").prop('required', false);

                                                        }
                                                        $("#type").change(function () {
                                                            if ($("#type").val() == 2) {
                                                                $("#simNB").show();
                                                                $("#simNumber").prop('required', true);



                                                            } else {
                                                                $('#simNB').hide();

                                                                $("simNumber").prop('required', false);
                                                            }


                                                        });
                                                        $("#company").keypress(function () {
                                                            //  alert("The text has been changed.");
                                                            $.ajax({
                                                                url: '../FindTraderRs',
                                                                type: 'post',
                                                                data: {
                                                                    // Read values             
                                                                    find: $('#company').val()
                                                                },
                                                                success: function (result) {
                                                                    //Do nothing                    
                                                                    if (result == "1") {
                                                                        alert("raison sociale existe")
                                                                    }
                                                                    // $('#soldLitig').text(result.sumLitig);
                                                                }

                                                            });

                                                        });
                                                        $("#company").change(function () {
                                                            //  alert("The text has been changed.");
                                                            $.ajax({
                                                                url: '../FindTraderRs',
                                                                type: 'post',
                                                                data: {
                                                                    // Read values             
                                                                    find: $('#company').val()
                                                                },
                                                                success: function (result) {
                                                                    //Do nothing                  
                                                                    if (result == "1") {
                                                                        alert("raison sociale existe");
                                                                        $('#company').val("")
                                                                    }
                                                                    // $('#soldLitig').text(result.sumLitig);
                                                                }

                                                            });
                                                        });
                                                    }
                                                    );
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
                                                    function desctiveClient(id) {
                                            var r = confirm("vous voulez désactiver affectation");
                                            if (r == true) {
                                                window.location.href = "../DesctivateProvider?id=" + id;
                                            } else {

                                            }
                                        }
                                        
                                          function desctiveClientTrader(id) {
                                            var r = confirm("vous voulez désactiver le client");
                                            if (r == true) {
                                                window.location.href = "../desactiveClient?id=" + id;
                                            } else {

                                            }
                                        }

        </script>
    </body>
    <%
        } else {
            String redirectURL = "../erreur.jsp";
            // response.sendRedirect(redirectURL);
        }
    %>
</html>

