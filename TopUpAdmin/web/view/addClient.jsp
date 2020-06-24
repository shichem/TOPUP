
<%@page import="model_helpers.StationType_Util"%>
<%@page import="model_helpers.TraderType_Util"%>
<%@page import="model_db.TraderCategory"%>
<%@page import="model_helpers.TraderCategory_Util"%>
<%@page import="custom_package.typesStatusUI"%>
<%@page import="model_db.TraderType"%>
<%@page import="model_db.Trader"%>
<%@page import="model_db.StationType"%>
<%@page import="model_helpers.Trader_Util"%>
<%@page import="java.util.List"%>
<%@page import="custom_vars.staticVars"%>
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
                        <h1 class="page-header">Ajouter client</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <% if (request.getParameter("erreur") != null) { %>
                <div class="alert alert-danger" role="alert">
                    Erreur dans raison social du client
                </div>
                <% } %>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Information sur Client  
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <form role="form" action="../AddClient" method="POST" >
                                            <div class="col-lg-6" class="form-group">
                                                <label>Nom</label>
                                                <input required="" id="fname"  name="fname" class="form-control" placeholder="Enter nom">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Prenom</label>
                                                <input  required="" id="laneme" name="lname" class="form-control" placeholder="Enter  prenom">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Raison social</label>
                                                <input  required="" id="company" name="company" class="form-control" placeholder="Enter  Raison social">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Adresse</label>
                                                <input  required="" id="adresse" name="adresse" class="form-control" placeholder="Ente  adresse  du client">
                                            </div>

                                            <div class="col-lg-6" class="form-group">
                                                <label>wilaya</label>
                                                <input  required="" id="wilaya" name="wilaya" class="form-control" placeholder="Entre wilaya du client ">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>commune</label>
                                                <input  required="" id="commune" name="commune" class="form-control" placeholder="Entre commune du client ">
                                            </div>

                                            <div class="col-lg-6" class="form-group">
                                                <label>email 1</label>
                                                <input id="email1" type="email" name="email1" class="form-control" placeholder="Enter email client">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>email 2</label>
                                                <input id="email2" type="email"  name="email2" class="form-control" placeholder="Enter email client">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>telephone  1</label>
                                                <input  required=""  id="telephone1" name="telephone1" class="form-control" placeholder="Enter telephone client">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>telephone 2</label>
                                                <input id="telephone2" name="telephone2" class="form-control" placeholder="Enter telephone client">
                                            </div>
                                            <%                                                List tistType = new TraderType_Util().getAllTraderType("");
                                            %>
                                            <div class="col-lg-6" class="form-group">
                                                <label>client type</label>
                                                <select  required="" id="type" name="type" class="form-control">
                                                    <option value="">Select le type du client </option>

                                                    <%
                                                        for (int i = 0; i < tistType.size(); i++) {
                                                            TraderType get = (TraderType) tistType.get(i);
                                                    %>
                                                    <option value="<%=get.getIdtraderType()%>"><%=get.getTraderTypeDesc()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>


                                          
                                            <div class="col-lg-6" id="simNB" hidden="" class="form-group">
                                                <label>Sim number</label>
                                                <input  id="simNumber" name="simNumber" class="form-control" placeholder="Ente sim number ">
                                            </div>
                                                    <%                                                List listType = new StationType_Util().getAllStationType("");
                                            %>             
                                           <div id="typeStation"  hidden="" class="col-lg-6" class="form-group">
                                                <label>Type Station</label>

                                                <select  id="typeStationId" name="typeStationId" class="form-control">
                                                    <option value="">Select un type  </option>

                                                    <%                                for (int i = 0; i < listType.size(); i++) {
                                                            StationType get = (StationType) listType.get(i);
                                                    %>
                                                    <option value="<%=get.getIdstationType()%>"><%=get.getStationTypeDesc()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                           
                                            <div class="col-lg-6" id="sndiv" hidden="" class="form-group">
                                                <label>seriel number</label>
                                                <input id="sn1" name="sn1" class="form-control" placeholder="Ente seriel number ">
                                            </div>
                                            <div class="col-lg-6" id="sndiv1" hidden="" class="form-group">
                                                <label>seriel number2</label>
                                                <input id="sn2" name="sn2" class="form-control" placeholder="Ente seriel number ">
                                            </div>


                                            <%                                                List listcategory = new TraderCategory_Util().getAllTraderCategory("");
                                            %>
                                            <div  class="col-lg-6" class="form-group">
                                                <label>client category</label>
                                                <select required="" id="catgory" name="catgory" class="form-control">
                                                    <option value="">Select un category client </option>

                                                    <%
                                                        for (int i = 0; i < listcategory.size(); i++) {
                                                            TraderCategory get = (TraderCategory) listcategory.get(i);
                                                    %>
                                                    <option value="<%=get.getIdtraderCategory()%>"><%=get.getTraderCategoryDesc()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <%
                                                List possibleParents = new Trader_Util().getTrader_by_trader_grossiste("");
                                            %>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Fournisseur</label>

                                                <select  id="fourn" name="fourn" class="form-control">
                                                    <option value="">Select un fournisseur </option>

                                                    <%                                for (int i = 0; i < possibleParents.size(); i++) {
                                                            Trader get = (Trader) possibleParents.get(i);
                                                    %>
                                                    <option value="<%=get.getIdtrader()%>"><%=get.getTraderCompany()%></option>
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
                $("#type").change(function () {
                    if (this.value == 2) {
                        $("#simNB").show();
                        $("#simNumber").prop('required',true);
                        $('#sndiv').hide();
                        $('#sndiv1').hide();
                        $('#typeStation').hide();
                        $("#typeStationId").prop('required',false);
                        $("#sn2").prop('required',false);
                        $("#sn1").prop('required',false);


                    } else {
                        $('#simNB').hide();
                        $('#sndiv').show();
                        $('#sndiv1').show();
                        $('#typeStation').show();
                        $("#simNumber").prop('required',false);
                         $("#typeStationId").prop('required',true);
                        $("#sn2").prop('required',true);
                        $("#sn1").prop('required',true);
                    }


                });
                $("#company").change(function () {
                    alert("The text has been changed.");
                });
            });
        </script>

    </body>

</html>

