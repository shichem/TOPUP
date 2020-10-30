<%-- 
    Document   : dashboard
    Created on : Mar 24, 2019, 3:06:28 PM
    Author     : macbookpro
--%>

<%@page import="javax.persistence.Tuple"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="model_helpers.SimInfo_Util"%>
<%@page import="model_helpers.TransactionTopup_Util"%>
<%@page import="model_helpers.TransactionSolde_Util"%>
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
                        <h1 class="page-header">Dashboard</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- row -->
                <div class="row">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-md-6">
                                    <h4>  Etat  sim  </h4>
                                </div>
                                <div  class="col-md-6" >
                                    <button type="btn"  style="float:right" class="btn btn-success"  onclick="rechercher()" > <i class="fas fa-sync-alt"></i> Mettre à jour</button>
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">
                            <table  id="example" class="display nowrap table dtr-inline collapsed" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>Sim number</th>
                                        <th>Operateur</th>                                           
                                        <th>Type</th>                                        
                                        <th>Sold</th>                                        
                                        <th>Date Sold</th>
                                        <th>Port</th>                                        
                                        <th>Etat</th>

                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>   
                    </div>
                </div>                        
                <!-- /.row -->
            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

        <%@include file="template/script.jsp" %>
        <!-- data -->
        <script >
            function rechercher() {
                
                Notiflix.Loading.Standard();
                var table = $('#example').DataTable();

                table.clear().draw();
                table.ajax.reload(function (json) {
                    Notiflix.Loading.Remove();
                });
            }
            $(document).ready(function () {
	
                var table = $('#example').dataTable({
                    responsive: true,
                    "searching": false,
                    "lengthChange": false,
                    iDisplayLength: -1,
                    "paging": false,
                    "ordering": false,
                    "info": false,
                    'ajax': {
                        'url': '../RefreshSimAndList'},
                    "createdRow": function (row, data, dataIndex) {
                        if (data[6] == `Inactif`) {
                            $(row).css({"background-color": "orange"});
                            $(row).find('td').css('color', 'black');

                        }
                            if (data[6] == `bloque`) {
                            $(row).css({"background-color": "red"});
                            $(row).find('td').css('color', 'black');

                        }
                    }

                });
            });
        </script>
    </body>

</html>

