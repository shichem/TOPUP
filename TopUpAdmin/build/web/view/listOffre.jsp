
<%@page import="model_db.OfferInfo"%>
<%@page import="java.util.List"%>
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
                        <h1 class="page-header">List Offre</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <div class="row">
                    <table id="example" class="display" style="width:100%">
                        <thead>
                            <tr>
                                <th>Offre</th>
                                <th>Operateur</th>
                                <th>commande</th>
                                <th>type</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%                                OfferInfo_Util offerInfo_Util = new OfferInfo_Util();
                                List listOffre = offerInfo_Util.getAllOfferInfo("");
                                System.out.println("className.methodName()"+listOffre.size());
                                for (int i = 0; i < listOffre.size(); i++) {
                                    OfferInfo offre = (OfferInfo) listOffre.get(i);
                            %>
                            <tr>
                                <td><%=offre.getOfferDesc()%></td>
                                <td><%=offre.getOperator().getOperatorDesc()%></td>
                                <td><%=offre.getPrenumber() +offre.getPostnumber() +offre.getPostPinCode() %></td>
                                <td><%=offre.getOfferType().getOfferTypeDesc()  %></td>
                                <td><a onclick="desctiveOffreInfo(<%=offre.getIdofferInfo() %>)" href="#"><i class="fa fa-trash fa-fw"></i></a></td>
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
                
                 function desctiveOffreInfo(id) {
                                            var r = confirm("vous voulez désactiver le client");
                                            if (r == true) {
                                                window.location.href = "../desactiveClient?id=" + id;
                                            } else {

                                            }
                                        }
            });
        </script>
    </body>

</html>

