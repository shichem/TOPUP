
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
    <%
        if (request.getParameter("id") != null) {
            String clientId = request.getParameter("id");
            Trader trader = new Trader_Util().getTradfer_by_id(Integer.parseInt(clientId), "");

            List listStation = new station_Util().getStations_by_trader(trader, "");
            Station station = null;
            if (listStation.size() != 0) {
                station = (Station) listStation.get(0);
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
                        <h1 class="page-header">Modifie client</h1>
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
                <div class="row">
                    <div class="col-lg-12">
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
                                            <div class="col-lg-6" class="form-group">
                                                <label>email 2</label>
                                                <input id="email2" name="email2" value="<%=trader.getEmail2()%>" class="form-control" placeholder="Enter email client">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>telephone  1</label>
                                                <input id="telephone1" name="telephone1" value="<%=trader.getTel1()%>" class="form-control" placeholder="Enter telephone client">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>telephone 2</label>
                                                <input id="telephone2" name="telephone2"  value="<%=trader.getTel2()%>" class="form-control" placeholder="Enter telephone client">
                                            </div>
                                            <%                                                List tistType = new TraderType_Util().getAllTraderType("");
                                            String readonly="";
                                                if(trader.getTraderCategory().getTraderCategoryDesc().equals(staticVars.traderCategory_Grossiste)){
                                                    readonly="readonly=''";
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
                                                <select required="" id="catgory" name="catgory" class="form-control" readonly=" ">
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
                                                String readonly1="";
                                                if(trader.getTraderCategory().getTraderCategoryDesc().equals(staticVars.traderCategory_Grossiste)){
                                                    readonly1="readonly=''";
                                                }
                                            %>
                                            <div class="col-lg-6" class="form-group">
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
                                                    <option value="<%=get.getIdtrader()%>"  <%=select%>   ><%=get.getTraderCompany()%></option>
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
            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

        <%@include file="template/script.jsp" %>
        <!-- data -->
        <script src="./data/data_graph.js"></script>
        <script>

            $(document).ready(function () {
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
                $("#company").change(function () {
                    //alert("The text has been changed.");
                });
            });

        </script>
    </body>
    <%
        } else {
            String redirectURL = "../erreur.jsp";
            // response.sendRedirect(redirectURL);
        }
    %>
</html>

