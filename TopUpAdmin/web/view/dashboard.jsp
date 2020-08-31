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
                <!-- /.row -->
                <div class="row">

                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="glyphicon glyphicon-transfer fa-5x"></i>
                                    </div>
                                    <%                                        Integer count = new TransactionSolde_Util().getAllTransactionSolde();
                                    %>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%= count%></div>
                                        <div>Number transaction Sold </div>
                                    </div>
                                </div>
                            </div>
                            <a href="#">
                                <div class="panel-footer">
                                    <span class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="glyphicon glyphicon-transfer fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%= new TransactionTopup_Util().getAllTransactionTopup()%></div>
                                        <div>Number transaction TopUp </div>
                                    </div>
                                </div>
                            </div>
                            <a href="#">
                                <div class="panel-footer">
                                    <span class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img src="./template/mobilis.svg" width="130" height="70">                                
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%= new TransactionTopup_Util().getAllTransactionTopupByOperator("MOBILIS")%></div>
                                        <div>Number transaction TopUp </div>
                                    </div>
                                </div>
                            </div>
                            <a href="#">
                                <div class="panel-footer">
                                    <span class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">                                
                                        <img src="./template/logo.svg" width="60" height="70">                                
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%= new TransactionTopup_Util().getAllTransactionTopupByOperator("DJEZZY")%></div>
                                        <div>Number transaction TopUp </div>
                                    </div>
                                </div>
                            </div>
                            <a href="#">
                                <div class="panel-footer">
                                    <span class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-default ">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img src="./template/ooredoo.svg" width="130" height="70">                                      </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%= new TransactionTopup_Util().getAllTransactionTopupByOperator("OOREDOO")%></div>
                                        <div>Number transaction TopUp  </div>
                                    </div>
                                </div>
                            </div>
                            <a href="#">
                                <div class="panel-footer">
                                    <span class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img src="./template/mobilis.svg" width="130" height="70">                                
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%= new SimInfo_Util().getAllSimInfoByOperator("MOBILIS")%></div>
                                        <div>Number Sim</div>
                                    </div>
                                </div>
                            </div>
                            <a href="#">
                                <div class="panel-footer">
                                    <span class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">                                
                                        <img src="./template/logo.svg" width="60" height="70">                                
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%= new SimInfo_Util().getAllSimInfoByOperator("DJEZZY")%></div>
                                        <div>Number Sim </div>
                                    </div>
                                </div>
                            </div>
                            <a href="#">
                                <div class="panel-footer">
                                    <span class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-default ">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img src="./template/ooredoo.svg" width="130" height="70">                                      </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%= new SimInfo_Util().getAllSimInfoByOperator("OOREDOO")%></div>
                                        <div>Number Sim </div>
                                    </div>
                                </div>
                            </div>
                            <a href="#">
                                <div class="panel-footer">
                                    <span class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Sold par operateur 
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div id="morris-sold-chart"></div>
                            </div>
                            <!-- /.panel-body -->
                        </div>
                        <!-- /.panel -->
                    </div>  
                    <!-- /.col-lg-6 -->  
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Soled estimed par operateur 
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div id="morris-soldEstimed-chart"></div>
                            </div>
                            <!-- /.panel-body -->
                        </div>
                        <!-- /.panel -->
                    </div> 
                    <!-- /.col-lg-6 -->
                    <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Transaction par Sim 
                            </div>

                            <div class="panel-body">
                                <div class="flot-chart">
                                    <div class="flot-chart-content" id="flot-pie-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                                        <div class="col-lg-6">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    Transaction par offer
                                                </div>
                                                <div class="panel-body">
                                                    <div id="morris-bar-chart"></div>
                                                </div>
                                            </div>
                                        </div>
                    <!-- /.col-lg-6 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

        <%@include file="template/script.jsp" %>
        <!-- data -->
        <script >

            $(function () {




                Morris.Donut({
                    element: 'morris-sold-chart',
                    data: [{
                            label: "Djezzy",
                            value: <%= new SimInfo_Util().getLasSoldByOperator("DJEZZY")%>
                        }, {
                            label: "Mobilis",
                            value: <%= new SimInfo_Util().getLasSoldByOperator("MOBILIS")%>
                        }, {
                            label: "Ooredoo",
                            value: <%= new SimInfo_Util().getLasSoldByOperator("OOREDOO")%>
                        }],
                    resize: true
                });

                Morris.Donut({
                    element: 'morris-soldEstimed-chart',
                    data: [{
                            label: "Djezzy",
                            value: <%= new SimInfo_Util().getLastSoldByOperatorEstimed("DJEZZY")%>
                        }, {
                            label: "Mobilis",
                            value: <%= new SimInfo_Util().getLastSoldByOperatorEstimed("MOBILIS")%>
                        }, {
                            label: "Ooredoo",
                            value: <%= new SimInfo_Util().getLastSoldByOperatorEstimed("OOREDOO")%>
                        }],
                    resize: true
                });


                //Flot Pie Chart
                $(function () {

                    var data = [<%  List list = new TransactionTopup_Util().getAllTransactionTopupGroupTransactionSim();
                List list1 = new TransactionTopup_Util().getAllTransactionTopupGroupTransactionSim1();
                for (int i = 0; i < list.size(); i++) {
                    int   j =1;
                   out.println("{label: '" + (String) list1.get(i) + "',data: " + list.get(i).toString() + "}");

                if ((j ) < list.size()) {
                    out.print(",");
                }
                j++;
            }

            %>
                    ]
                    var plotObj = $.plot($("#flot-pie-chart"), data, {
                        series: {
                            pie: {
                                show: true
                            }
                        },
                        grid: {
                            hoverable: true
                        },
                        tooltip: true,
                        tooltipOpts: {
                            content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
                            shifts: {
                                x: 20,
                                y: 0
                            },
                            defaultTheme: true
                        }
                    });

                });

    Morris.Bar({
        element: 'morris-bar-chart',
        data: [
                    <%  List listY = new TransactionTopup_Util().getAllTransactionTopupGroupByOffer();
                List list1a = new TransactionTopup_Util().getAllTransactionTopupGroupByOffer1();
                for (int i = 0; i < listY.size(); i++) {
                    int   j =1;
                   out.println("{y :'" + (String) list1a.get(i) + "',a :" + listY.get(i).toString() + "}");

                if ((j ) < listY.size()) {
                    out.print(",");
                }
                j++;
            }

            %>],
        xkey: 'y',
        ykeys: ['a'],
        labels: ['Transaction' ],
        hideHover: 'auto',
        resize: true
    });
            });

        </script>
    </body>

</html>

