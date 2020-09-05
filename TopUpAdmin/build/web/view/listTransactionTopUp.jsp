
<%@page import="model_db.OfferInfo"%>
<%@page import="java.util.List"%>
<%@page import="model_helpers.OfferInfo_Util"%>
<%@page import="model_helpers.TransactionSolde_Util"%>

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
                        <h1 class="page-header">List Transaction TopUp</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <% if (request.getParameter("add") != null) { %>
                <div class="alert alert-success" role="alert">
                    Offer ajoutet avec success
                </div>
                <% } %>
                <% if (request.getParameter("edit") != null) { %>
                <div class="alert alert-success" role="alert">
                    Offer modifier avec success
                </div>
                <% } %>
                <% if (request.getParameter("del") != null) { %>
                <div class="alert alert-success" role="alert">
                    Offer deactivate avec success
                </div>
                <% } %>
                <% if (request.getParameter("erreur") != null) { %>
                <div class="alert alert-danger" role="alert">
                    Erreur dans mis a jour du Offer contact admin 
                </div>
                <% }%>
                <div class="row">
                    <table id="example" class="display nowrap table dtr-inline collapsed" style="width:100%">
                        <thead>
                            <tr>
                                <th>Client</th>
                                <th>SimClient</th>
                                <th>Offer</th>
                                <th>New sold</th>
                                <th>Transaction Amount</th>
                                <th>Real Transaction Amount</th>
                                <th>Date Transaction  </th>
                                <th>Status Transaction  </th>
                                <th>Type Transaction  </th>
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
                                <th>New sold</th>
                                <th>Transaction Amount</th>
                                <th>Real Transaction Amount</th>
                                <th>Date Transaction  </th>
                                <th>Status Transaction  </th>
                                <th>Type Transaction  </th>
                                <th>Sent Message  </th>
                                <th>Recived Message  </th>
                            </tr>
                        </tfoot>
                    </table>                         
                </div>
            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

        <%@include file="template/script.jsp" %>
        <!-- data -->
        <script>
            $(document).ready(function () {
                $('#example').DataTable({
                    responsive: true,
                    "processing": true,
                    "serverSide": true,
                    "bSort": false,
                    "ajax": "../ListTransactionTopUp",
                });
                $('#example tbody').on('click', 'tr', function () {
                    var data = table.row(this).data();
                    alert('You clicked on ' + data[0] + '\'s row');
                });


            });
            function desctiveOffreInfo(id) {
                var r = confirm("vous voulez désactiver l'offer");
                if (r == true) {
                    window.location.href = "../DesctivateOffer?id=" + id;
                } else {

                }
            }
        </script>
    </body>

</html>

