
<%@page import="model_db.PortInfo"%>
<%@page import="model_helpers.PortInfo_Util"%>
<%@page import="model_db.SimType"%>
<%@page import="model_helpers.SimType_Util"%>
<%@page import="model_db.Operator"%>
<%@page import="model_helpers.Operator_Util"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
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
                        <h1 class="page-header">Ajouter Sim</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                   <% if (request.getParameter("erreur") != null) { %>
                <div class="alert alert-danger" role="alert">
                    Erreur dans ajout du sim contact admin
                </div>
                <% } %>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Information sur sim  
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <form role="form" action="../AddSim" method="POST" >
                                            <div class="col-lg-6" class="form-group">
                                                <label>Numero Sim</label>
                                                <input id="Sim"  name="Sim" class="form-control"  placeholder="Enter numero de  sim" required="">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Code PIN</label>
                                                <input id="pin" name="pin" class="form-control" placeholder="Enter Code PIN" required="">
                                            </div>

                                            <div class="col-lg-6" class="form-group">
                                                <%                                                    List listOperator = new Operator_Util().getAllOperator("");
                                                %>
                                                <label>Operator </label>
                                                <select id="operator" name="operator" required="" class="form-control">
                                                    <option value="">Selection un operator  </option>

                                                    <%
                                                        for (int i = 0; i < listOperator.size(); i++) {
                                                            Operator get = (Operator) listOperator.get(i);
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
                                                    List listType = new SimType_Util().getAllSimType("");
                                                %>
                                                <select id="type" name="type" required="" class="form-control">
                                                    <option value="">Selection un Sim type  </option>

                                                    <%
                                                        for (int i = 0; i < listType.size(); i++) {
                                                            SimType get = (SimType) listType.get(i);
                                                    %>
                                                    <option value="<%=get.getIdsimType()%>"><%=get.getSimTypeDesc()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-lg-12" class="form-check">
                                                <input name="mant_iden" type="checkbox">
                                                <span class="checkmark">Nouveaux Port</span>

                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Port</label> 
                                                <%
                                                    List listPort = new PortInfo_Util().getAllPortInfo("");
                                                %>
                                                <select id="port" name="port" class="form-control">
                                                    <option value="">Selection un Port  </option>

                                                    <%
                                                        for (int i = 0; i < listPort.size(); i++) {
                                                            PortInfo get = (PortInfo) listPort.get(i);
                                                    %>
                                                    <option value="<%=get.getIdportInfo()%>"><%=get.getPortDesc()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                                <input id="portText" name="portText" type="text"  style="display: none;" class="form-control" placeholder="Enter Port COM">
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
        <script>

            $(document).ready(function () {

                $('input[type=checkbox][name=mant_iden]').change(function () {
                    if ($(this).is(':checked')) {
                        $("#portText").prop("required", true);
                        $("#port").prop("required", false);
                        $("#port").hide();
                        $("#portText").show();
                    } else {
                        $("#port").prop("required", true);
                        $("#portText").prop("required", false);
                         $("#portText").hide();
                        $("#port").show();
                    }
                });
            });
        </script>
    </body>

</html>

