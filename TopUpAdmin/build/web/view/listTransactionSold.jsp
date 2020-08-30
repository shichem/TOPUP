
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
                        <h1 class="page-header">List Offer</h1>
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
                <% } %>
                <div class="row">
                    <table id="example" class="display nowrap table dtr-inline collapsed" style="width:100%">
                        <thead>
                            <tr>
                                <th>Offer</th>
                                <th>Operateur</th>
                                <th>commande</th>
                                <th>type</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%                              
                                TransactionSolde_Util solde_Util = new TransactionSolde_Util();
                                List listOffre = solde_Util.getAllTransactionSolde("");
                                System.out.println("className.methodName()" + listOffre.size());
                                for (int i = 0; i < listOffre.size(); i++) {
                                    OfferInfo offre = (OfferInfo) listOffre.get(i);
                            %>
                            <tr>
                                <td><%=offre.getOfferDesc()%></td>
                                <td><%=offre.getOperator().getOperatorDesc()%></td>
                                <td><%=offre.getPrenumber() + "0000000000" + offre.getPostnumber() + "0000" + offre.getPostPinCode() + ""%></td>
                                <td><%=offre.getOfferType().getOfferTypeDesc()%></td>                                
                                <td>
                                   <a href="./editOffre.jsp?id=<%=offre.getIdofferInfo()%>"><i class="fa fa-edit fa-fw"></i></a>/ 
                                    <a onclick="desctiveOffreInfo(<%=offre.getIdofferInfo()%>)" href="#"><i class="fa fa-trash fa-fw"></i></a>
                                </td>
                            </tr>
                            <%
                                }
                            %>    

                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Offre</th>
                                <th>Operateur</th>
                                <th>commande</th>
                                <th>type</th>
                                <th>Action</th>
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
        <script src="./data/data_graph.js"></script>
        <script>
                                        $(document).ready(function () {
                                            var table = $('#example').DataTable();

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

