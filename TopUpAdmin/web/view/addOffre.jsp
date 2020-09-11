<%@page import="java.util.List"%>
<%@page import="model_db.OfferType"%>
<%@page import="model_db.Operator"%>
<%@page import="model_db.SimInfo"%>
<%@page import="model_db.OfferInfo"%>
<%@page import="model_helpers.OfferType_Util"%>
<%@page import="model_helpers.Operator_Util"%>
<%@page import="model_helpers.SimInfo_Util"%>
<%@page import="model_helpers.OfferInfo_Util"%>

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
                        <h1 class="page-header">Ajouter offer</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Information sur Offer 
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <form role="form"  action="../AddOffer" method="POST" >
                                            <div class="col-lg-6" class="form-group">
                                                <label>Operateur</label>
                                                <%                                                       List listOperateur = new Operator_Util().getAllOperator("");
                                                %>
                                                <select id="operateur" name="operateur" required="" class="form-control">
                                                    <option value="">Select le  Operateur </option>
                                                    <%
                                                        for (int i = 0; i < listOperateur.size(); i++) {
                                                            Operator get = (Operator) listOperateur.get(i);
                                                    %>
                                                    <option value="<%=get.getIdoperator()%>"><%=get.getOperatorDesc()%></option>
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
                                                    %>
                                                    <option value="<%=get.getIdofferType()%>"><%=get.getOfferTypeDesc()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Nom d'offer</label>
                                                <input id="fname"  name="fname" required="" class="form-control" placeholder="Enter offre desc ">
                                            </div>


                                            <div class="col-lg-12" class="form-group">
                                                <input name="mant_iden" type="checkbox">
                                                <span class="checkmark">Montant indefini</span>

                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Maontant transfere  </label>
                                                <input id="tmaontant" name="tmaontant" type="number"  required="" class="form-control" placeholder="Enter pre number">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Montant debite </label>
                                                <input id="dmaontant" name="dmaontant" type="number" required=""  class="form-control" placeholder="Enter post number">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Avant le numero de telephone </label>
                                                <input id="preNumber" name="preNumber" required="" class="form-control" placeholder="Avant le numero de telephone">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Apers le numero de telephone est avant le code pin</label>
                                                <input id="posteNumber" name="posteNumber"  required="" class="form-control" placeholder="Apers le numero de telephone est avant le code pin">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Apers  code pin</label>
                                                <input id="posteNumberPin" name="posteNumberPin"  class="form-control" placeholder="Apers code pin">
                                            </div>

                                            <div class="col-lg-12" class="form-group">
                                                <label>Sim</label>
                                                <%                                                       List listSim = new SimInfo_Util().getAllSimInfo("");
                                                %>

                                                <select multiple name="Number" id="Number" required="" class="form-control"> 
                                                    <%
                                                        for (int i = 0; i < listSim.size(); i++) {
                                                            SimInfo get = (SimInfo) listSim.get(i);
                                                    %>
                                                    <option value="<%=get.getIdsimInfo()%>"><%=get.getSimnumber()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-lg-12" >
                                                <div style="float:right">
                                                    <br/>
                                                    <button type="reset" class="btn btn-info">Annuler</button>
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

                        $("#tmaontant").prop("required", false);

                        $("#dmaontant").prop("required", false);

                    } else {
                        $("#tmaontant").prop("disabled", false);
                        $("#dmaontant").prop("disabled", false);
                        $("#tmaontant").prop("required", true);

                        $("#dmaontant").prop("required", true);


                    }
                });

                $("#operateur").change(function () {

                    var operatorID = $("#operateur").val();
                    $.ajax({
                        url: "../SimByOperator?OpeatorId=" + operatorID,
                        success: function (data) {
                            // alert(data);
                            $("#Number").empty();
                            $("#Number").append(data);
                        }
                    });
                });
            });
        </script>
    </body>

</html>