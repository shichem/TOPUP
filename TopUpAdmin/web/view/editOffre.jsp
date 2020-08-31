<%@page import="model_db.SimOffer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model_helpers.OfferType_Util"%>
<%@page import="model_db.OfferType"%>
<%@page import="java.util.List"%>
<%@page import="model_db.OfferInfo"%>
<%@page import="model_db.Operator"%>
<%@page import="model_db.SimInfo"%>
<%@page import="model_helpers.OfferInfo_Util"%>
<%@page import="model_helpers.Operator_Util"%>
<%@page import="model_helpers.SimInfo_Util"%>

<%-- 
    Document   : dashboard
    Created on : Mar 24, 2019, 3:06:28 PM
    Author     : macbookpro
--%>
<%
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">

    <%@include file="template/head.jsp" %>
   <%
        if (request.getParameter("id") != null) {
            String offreID = request.getParameter("id");
            OfferInfo offerInfo = new OfferInfo_Util().getOfferInfo_by_id( Integer.parseInt(offreID), "");

           
            // System.out.println("className.methodName()"+listStation.size());

    %>
    <body>

        <div id="wrapper">

            <!-- Navigation -->
            <%@include file="template/navigation.jsp" %>

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Modifie offer</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                 <% if (request.getParameter("succes") != null) { %>
                <div class="alert alert-success" role="alert">
                    Offer modefie avec success
                </div>
                <% } %>
                <% if (request.getParameter("erreur") != null) { %>
                <div class="alert alert-danger" role="alert">
                    Erreur dans mis a jour du l'offer  contact admin 
                </div>
                <% }%>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Information sur Offer 
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <form role="form"  action="../EditOffer" method="POST" >
                                            <input type="hidden" name="id" value="<%=offreID%>">
                                            <div class="col-lg-6" class="form-group">
                                                <label>Operateur</label>
                                                <%                                                       List listOperateur = new Operator_Util().getAllOperator("");
                                                %>
                                                <select id="operateur" name="operateur" required="" class="form-control">
                                                    <option value="">Select le  Operateur </option>
                                                    <%
                                                        for (int i = 0; i < listOperateur.size(); i++) {
                                                            Operator get = (Operator) listOperateur.get(i);
                                                             String select = "";
                                                            if (get.getIdoperator() == offerInfo.getOperator().getIdoperator() ) {
                                                                select = "selected='selected'";
                                                            }
                                                    %>
                                                    
                                                    <option value="<%=get.getIdoperator()%>"  <%=select%> > <%=get.getOperatorDesc()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Type</label>
                                                <%
                                                    List tistType = new OfferType_Util().getAllOfferType("");
                                                %>
                                                <select id="type" name="type" required="" class="form-control">
                                                    <option value="">Select le type du Offer </option>
                                                    <%
                                                        for (int i = 0; i < tistType.size(); i++) {
                                                            OfferType get = (OfferType) tistType.get(i);
                                                            String select = "";
                                                            if (get.getIdofferType() == offerInfo.getOfferType().getIdofferType() ) {
                                                                select = "selected='selected'";
                                                            }
                                                    %>
                                                    <option value="<%=get.getIdofferType()%>"  <%=select%>>  <%=get.getOfferTypeDesc()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Nom d'offer</label>
                                                <input id="fname"  name="fname" required="" value="<%= offerInfo.getOfferDesc() %>" class="form-control" placeholder="Enter offre desc ">
                                            </div>

                                            <div class="col-lg-6" class="form-group">
                                                <label>limit transaction</label>
                                                <input id="fname"  name="fname" value="<%= offerInfo.getLimitTransact()%>" class="form-control" placeholder="Enter nb transction">
                                            </div>
                                          
                                            <div class="col-lg-6" class="form-group">
                                                <label>Maontant transfere  </label>
                                                <input id="tmaontant" name="tmaontant" type="number"   value="<%= offerInfo.getRealValue()%>" required="" class="form-control" placeholder="Enter pre number">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Montant debite </label>
                                                <input id="dmaontant" name="dmaontant" type="number"  value="<%= offerInfo.getTransferedValue() %>"  required="" class="form-control" placeholder="Enter post number">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Avant le numero de telephone </label>
                                                <input id="preNumber" name="preNumber" class="form-control" required="" value="<%= offerInfo.getPrenumber() %>" placeholder="Avant le numero de telephone">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Apers le numero de telephone est avant le code pin</label>
                                                <input id="posteNumber" name="posteNumber" value="<%= offerInfo.getPostnumber()%>" required="" class="form-control" placeholder="Apers le numero de telephone est avant le code pin">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Apers  code pin</label>
                                                <input id="posteNumberPin" name="posteNumberPin"  value="<%= offerInfo.getPostPinCode()%>"  class="form-control" placeholder="Apers code pin">
                                            </div>

                                            <div class="col-lg-12" class="form-group">
                                                <label>Sim</label>
                                                <%         
                                                     List listSim = new SimInfo_Util().getAllSimInfo("");
                                                    List<SimOffer> listSim1 = new ArrayList<SimOffer> (offerInfo.getSimOffers());
                                                %>

                                                <select multiple required="" class="form-control"> 
                                                    <%
                                                        for (int i = 0; i < listSim.size(); i++) {
                                                            SimInfo get = (SimInfo) listSim.get(i);
                                                                String select = "";
                                                            for (int j = 0; j < listSim1.size(); j++) {
                                                                SimOffer get1 = (SimOffer) listSim1.get(j);
                                                                if(get1.getSimInfo().getIdsimInfo().equals(get.getIdsimInfo())){
                                                                      select = "selected='selected'";
                                                                }
                                                            }
                                                           
                                                    %>
                                                    <option value="<%=get.getIdsimInfo() %>" <%=select%> ><%=get.getSimnumber()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-lg-12" >
                                                <div style="float:right">
                                                    <br/>
                                                    <button type="submit" class="btn btn-success"  >Enregistrer</button>

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
                $('input[type=checkbox][name=mant_iden]').change(function () {
                    if ($(this).is(':checked')) {
                        $("#tmaontant").prop("disabled", true);
                        $("#dmaontant").prop("disabled", true);

                    } else {
                        $("#tmaontant").prop("disabled", false);
                        $("#dmaontant").prop("disabled", false);
                    }
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