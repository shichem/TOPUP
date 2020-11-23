
<%@page import="model_db.SimType"%>
<%@page import="model_helpers.SimType_Util"%>
<%@page import="model_helpers.TransactionTopup_Util"%>
<%@page import="model_helpers.TransactionTopup_Util"%>
<%@page import="model_db.Operator"%>
<%@page import="model_helpers.Operator_Util"%>
<%@page import="model_db.SimInfo"%>
<%@page import="model_helpers.SimOffer_Util"%>
<%@page import="model_db.TransactionType"%>
<%@page import="model_helpers.TransactionType_Util"%>
<%@page import="model_db.StatusInfo"%>
<%@page import="model_helpers.StatusInfo_Util"%>
<%@page import="model_db.OfferInfo"%>
<%@page import="java.util.List"%>
<%@page import="model_helpers.OfferInfo_Util"%>
<%@page import="model_helpers.SimInfo_Util"%>

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
    <style>



        .autocomplete {
            /*the container must be positioned relative:*/
            position: relative;
        }
        input {
            border: 1px solid transparent;
            background-color: #f1f1f1;
            padding: 10px;
            font-size: 16px;
        }
        input[type=text] {
            background-color: #f1f1f1;
            width: 100%;
        }
        input[type=submit] {
            background-color: DodgerBlue;
            color: #fff;
        }
        .autocomplete-items {
            border: 1px solid #d4d4d4;
            border-bottom: none;
            border-top: none;
            z-index: 99;
            /*position the autocomplete items to be the same width as the container:*/
            top: 100%;
            left: 0;
            right: 0;
        }
        .autocomplete-items div {
            padding: 10px;
            cursor: pointer;
            background-color: #fff;
            border-bottom: 1px solid #d4d4d4;
        }
        .autocomplete-items div:hover {
            /*when hovering an item:*/
            background-color: #e9e9e9;
        }
        .autocomplete-active {
            /*when navigating through the items using the arrow keys:*/
            background-color: DodgerBlue !important;
            color: #ffffff;
        }
    </style>
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
                    <div class="col-lg-4 col-md-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img src="./template/mobilis.svg" width="130" height="70">                                
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><span id="MOBILIS">0 DA</span></div>
                                        <div>Sold Mobilis </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">                                
                                        <img src="./template/logo.svg" width="60" height="70">                                
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><span id="DJEZZY">0 DA</span></div>
                                        <div>Sold Djezzy</div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="panel panel-default ">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <img src="./template/ooredoo.svg" width="130" height="70">                                      </div>
                                    <div class="col-xs-9 text-right">

                                        <div class="huge"><span id="OOREDOO">0 DA</span></div>
                                        <div>Sold Ooredoo  </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Recherche   
                        </div>
                        <div class="panel-body">
                            <form>
                            <div class="col-lg-8">
                                <div class="col-lg-6" class="form-group">

                                            <label>date debut</label>
                                            <div class='input-group date' id='datetimepicker1'>
                                                <input id="dateDebut" type='text' class="form-control" />
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="col-lg-6" class="form-group">

                                            <label>date fin</label>
                                            <div class='input-group date' id='datetimepicker2'>
                                                <input  id="dateFin" type='text' class="form-control" />
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                            </div>
                                        </div>

                                        <div class="col-lg-6" class="form-group">

                                            <label>time debut</label>
                                            <div class='input-group date' id='time1'>
                                                <input type='text' id="timeDebut" class="form-control" />
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-time"></span>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="col-lg-6" class="form-group">

                                            <label>time fin</label>
                                             <div class='input-group date' id='time2'>
                                                <input type='text' id="timeFin" class="form-control" />
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-time"></span>
                                                </span>
                                            </div>
                                        </div>
                                <div class="col-lg-12" class="form-group">
                                    <label>Client</label>
                                    <div class="autocomplete" >
                                        <input id="treader" type="text"  name="trader" class=" form-control"  placeholder="nom du client " autocomplete="off">
                                    </div>

                                </div>
                                <div class="col-lg-6" class="form-group">
                                    <%
                                        List listStatus = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc_Like("TCT", "");
                                    %>
                                    <label>status</label>
                                    <select id="statusStation" name="statusStationId" class="form-control">
                                        <option value="">Selection status du transction  </option>

                                        <%                                for (int i = 0; i < listStatus.size(); i++) {
                                                StatusInfo get = (StatusInfo) listStatus.get(i);
                                        %>
                                        <option value="<%=get.getStatusInfoDesc()%>"><%=get.getStatusInfoDesc().replace("TCT_", "") %></option>
                                        <%
                                            }
                                        %>

                                    </select>
                                </div>

                                <div class="col-lg-6" class="form-group">
                                    <%
                                        List listtype = new TransactionType_Util().getAllTransactionType("");
                                    %>
                                    <label>Type</label>
                                    <select id="type" name="typeTransaction"  class="form-control">
                                        <option value="">Selection type du station  </option>

                                        <%                                for (int i = 0; i < listtype.size(); i++) {
                                                TransactionType get = (TransactionType) listtype.get(i);
                                        %>
                                        <option value="<%=get.getTransactionTypeDesc()%>"><%=get.getTransactionTypeDesc()%></option>
                                        <%
                                            }
                                        %>

                                    </select>
                                </div>
                                         <div class="col-lg-6" class="form-group">
                                    <%
                                        List Simtype = new SimType_Util().getAllSimType("");
                                    %>
                                    <label> Sim Type</label>
                                    <select id="simType" name="simType"  class="form-control">
                                        <option value="">Selection type du Sim  </option>

                                        <%                                for (int i = 0; i < Simtype.size(); i++) {
                                                SimType get = (SimType) Simtype.get(i);
                                        %>
                                        <option value="<%=get.getSimTypeDesc()%>"><%=get.getSimTypeDesc()%></option>
                                        <%
                                            }
                                        %>

                                    </select>
                                </div>
                                <div class="col-lg-6" class="form-group">
                                    <%
                                        List listOperator = new Operator_Util().getAllOperator("");
                                    %>
                                    <label>Operator </label>
                                    <select id="operator" name="operator"  class="form-control">
                                        <option value="">Selection un operator  </option>

                                        <%
                                            for (int i = 0; i < listOperator.size(); i++) {
                                                Operator get = (Operator) listOperator.get(i);
                                        %>
                                        <option value="<%=get.getOperatorDesc()%>"><%=get.getOperatorDesc()%></option>
                                        <%
                                            }
                                        %>

                                    </select>
                                </div>
                                <div class="col-lg-6" class="form-group">
                                    <%
                                        List listoffre = new OfferInfo_Util().getAllOfferInfo("");
                                    %>
                                    <label>Offer</label>
                                    <select id="offer" name="offer" class="form-control">
                                        <option value="">Selection type du offer  </option>

                                        <%
                                            for (int i = 0; i < listtype.size(); i++) {
                                                OfferInfo get = (OfferInfo) listoffre.get(i);
                                        %>
                                        <option value="<%=get.getOfferDesc()%>"><%=get.getOfferDesc()%></option>
                                        <%
                                            }
                                        %>

                                    </select>

                                </div>

                                <div class="col-lg-6" class="form-group">
                                    <%
                                        List listsim = new SimInfo_Util().getAllSimInfo("");
                                    %>
                                    <label>Sim</label>
                                    <select id="sim" name="sim"  class="form-control">
                                        <option value="">Selection un sim  </option>

                                        <%
                                            for (int i = 0; i < listsim.size(); i++) {
                                                SimInfo get = (SimInfo) listsim.get(i);
                                        %>
                                        <option value="<%=get.getSimnumber()%>"><%=get.getSimnumber()%></option>
                                        <%
                                            }
                                        %>

                                    </select>
                                </div>

                                <div class="col-lg-6">
                                    <label>Min sold intervalle</label>
                                    <input type="number" id="minSold" value="maxSold" class="form-control"/>
                                    <div hidden="">
                                        <p>
                                            <label for="amount">Sold intervalle  </label>
                                            <input type="text" id="amount" style="border: 0; color: #f6931f; font-weight: bold;" />
                                        </p>
                                        <div id="slider-range" ></div>  
                                        <input type="text" id="minSold1"  value="" />
                                        <input type="text" id="maxSold1"  value="" />
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <label>Max sold intervalle</label>
                                    <input type="number" id="maxSold" value="maxSold" class="form-control"/>
                                </div>
                                <div class="col-lg-6" class="form-group">

                                    <label style="    color: green;">Slod  reussie :</label><label id ="sold"style="    color: green;"></label>
                                    </br>
                                    <label style="    color: red;">Slod  litig :</label><label id ="soldLitig" style="    color: red;"></label>
                                </div>

                                <div class="col-lg-12" >
                                    <div style="float:right">
                                        <br/>
                                        <button type="reset" class="btn btn-info">Annuler</button>
                                        <button type="button" class="btn btn-success"  onclick="rechercher()" >Rechercher</button>

                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="flot-chart">
                                    <div class="flot-chart-content" id="flot-pie-chart-status"></div>
                                </div>
                            </div
                        </div>
                                                                    </form>

                    </div>
                    <table id="example" class="display nowrap table dtr-inline collapsed" style="width:100%">
                        <thead>
                            <tr>
                                <th>Client</th>
                                <th>SimClient</th>
                                <th>Offer</th>
                                <th>Status Transaction  </th>
                                <th>Type Transaction  </th>
                                <th>Date Transaction  </th>
                                <th>New sold</th>
                                <th>Transaction Montant</th>
                                <th>Real Transaction Montant</th>                                                      
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
                                <th>Status Transaction  </th>
                                <th>Type Transaction  </th>
                                <th>Date Transaction  </th>
                                <th>New sold</th>
                                <th>Transaction Montant</th>
                                <th>Real Transaction Montant</th>                                                      
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
              $(function () {
                $('#datetimepicker1').datetimepicker({
                    format: 'DD/MM/YYYY'
                });
                $('#datetimepicker2').datetimepicker({
                    format: 'DD/MM/YYYY'
                });
                 $('#time1').datetimepicker({
                 format: 'HH:mm:ss'
                });
                $('#time2').datetimepicker({
                 format: 'HH:mm:ss'
                });
                $("#datetimepicker1").on("dp.change", function (e) {
                    $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
                });
                $("#datetimepicker2").on("dp.change", function (e) {
                    $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
                });
              /*    $("#time1").on("dp.change", function (e) {
                    $('#time2').data("DateTimePicker").minTime(e.date);
                });
                $("#time2").on("dp.change", function (e) {
                    $('#time1').data("DateTimePicker").MaxTime(e.date);
                });*/

                $("#slider-range").slider({
                    range: true,
                    min: 0,
                    max: 99999,
                    values: [0, 999999],
                    slide: function (event, ui) {
                        $("#amount").val("DA" + ui.values[ 0 ] + " - DA" + ui.values[ 1 ]);
                        $("#minSold").val(ui.values[ 0 ]);
                        $("#maxSold").val(ui.values[ 1 ]);
                    }
                });
                $("#amount").val("DA" + $("#slider-range").slider("values", 0) +
                        " - DA" + $("#slider-range").slider("values", 1));

            });
            $(document).ready(function () {


                jQuery.fn.DataTable.Api.register('buttons.exportData()', function (options) {

                    var jsonResult = $.ajax({
                        url: '../ListTransactionTopUpAllExportExcl',
                        type: 'GET',
                        data: {
                            // Read values
                            status: $('#statusStation :selected').val(),
                            type: $('#type :selected').val(),
                            name: $('#treader').val(),
                            dateDebut: $('#dateDebut').val(),
                            dateFin: $('#dateFin').val(),
                            timeDebut: $('#timeDebut').val(),
                            timeFin: $('#timeFin').val(),
                             simType : $('#simType :selected').val(),
                            operator: $('#operator :selected').val(),
                            offer: $('#offer :selected').val(),
                            sim: $('#sim :selected').val(),
                            minSold: $("#minSold").val(),
                            maxSold: $("#maxSold").val()
                        },
                        success: function (result) {
                            //Do nothing
                        },
                        async: false
                    });
                    console.log(jsonResult);
                    return {body: jsonResult.responseJSON.data, header: $("#example thead tr th").map(function () {
                            return this.innerHTML;
                        }).get()};
                }
                );
                $('#example').DataTable({
                    "dom": "Blfrtip",
                    responsive: true,
                    "processing": true,
                    "serverSide": true,
                    "bFilter": false,
                    "bSort": false,
                    'ajax': {
                        'url': '../ListTransactionTopUp',
                        'data': function (data) {
                            // Read values
                            var status = $('#statusStation :selected').val();
                            var type = $('#type :selected').val();
                            var name = $('#treader').val();
                            var dateDebut = $('#dateDebut').val();
                            var dateFin = $('#dateFin').val();
                            var timeDebut = $('#timeDebut').val();
                            var timeFin = $('#timeFin').val();
                            var simType = $('#simType :selected').val();
                            var operator = $('#operator :selected').val();
                            var offer = $('#offer :selected').val();
                            var sim = $('#sim :selected').val();
                            var minSold = $("#minSold").val();
                            var maxSold = $("#maxSold").val();


                            // Append to data
                            data.status = status;
                            data.type = type;
                            data.name = name;
                            data.dateDebut = dateDebut;
                            data.dateFin = dateFin;
                            data.timeDebut = timeDebut;
                            data.timeFin = timeFin;
                            data.sim = sim;
                            data.offer = offer;
                            data.simType=simType;
                            data.operator = operator;
                            data.minSold = minSold;
                            data.maxSold = maxSold;
                        }
                    },
                    lengthMenu: [[10, 25, 100, -1], [10, 25, 100, "All"]],
                    pageLength: 10,
                    buttons: [
                        {
                            extend: 'excel',
                            text: '<span class="fa fa-file-excel-o"></span> Excel Export',
                            title: 'TransactionTopUp',

                            exportOptions: {
                                modifier: {
                                    search: 'applied',
                                    order: 'applied'
                                }
                            }
                        }
                    ],
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
        <script>

            function autocomplete(inp, arr) {
                console.log("in auto complette");
                /*the autocomplete function takes two arguments,
                 the text field element and an array of possible autocompleted values:*/
                var currentFocus;
                /*execute a function when someone writes in the text field:*/
                inp.addEventListener("input", function (e) {
                    var a, b, i, val = this.value;
                    /*close any already open lists of autocompleted values*/
                    closeAllLists();
                    if (!val) {
                        return false;
                    }
                    currentFocus = -1;
                    /*create a DIV element that will contain the items (values):*/
                    a = document.createElement("DIV");
                    a.setAttribute("id", this.id + "autocomplete-list");
                    a.setAttribute("class", "autocomplete-items");
                    /*append the DIV element as a child of the autocomplete container:*/
                    this.parentNode.appendChild(a);
                    $.get("../FindTrader?find=" + this.value,
                            function (data, status) {
                                arr = JSON.parse(data);
                                /*for each item in the array...*/
                                for (i = 0; i < data.length; i++) {
                                    /*check if the item starts with the same letters as the text field value:*/
                                    //  if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                                    /*create a DIV element for each matching element:*/
                                    b = document.createElement("DIV");
                                    /*make the matching letters bold:*/
                                    b.innerHTML = "<strong>" + arr[i] + "</strong>";
                                    b.innerHTML += arr[i].substr(val.length);
                                    /*insert a input field that will hold the current array item's value:*/
                                    b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                                    /*execute a function when someone clicks on the item value (DIV element):*/
                                    b.addEventListener("click", function (e) {
                                        /*insert the value for the autocomplete text field:*/
                                        inp.value = this.getElementsByTagName("input")[0].value;
                                        $("#fname").val(inp.value);
                                        /*close the list of autocompleted values,
                                         (or any other open lists of autocompleted values:*/
                                        closeAllLists();
                                    });
                                    a.appendChild(b);
                                }
                            });
                });
                /*execute a function presses a key on the keyboard:*/
                inp.addEventListener("keydown", function (e) {
                    var x = document.getElementById(this.id + "autocomplete-list");
                    if (x)
                        x = x.getElementsByTagName("div");
                    if (e.keyCode == 40) {
                        /*If the arrow DOWN key is pressed,
                         increase the currentFocus variable:*/
                        currentFocus++;
                        /*and and make the current item more visible:*/
                        addActive(x);
                    } else if (e.keyCode == 38) { //up
                        /*If the arrow UP key is pressed,
                         decrease the currentFocus variable:*/
                        currentFocus--;
                        /*and and make the current item more visible:*/
                        addActive(x);
                    } else if (e.keyCode == 13) {
                        /*If the ENTER key is pressed, prevent the form from being submitted,*/
                        e.preventDefault();
                        if (currentFocus > -1) {
                            /*and simulate a click on the "active" item:*/
                            if (x)
                                x[currentFocus].click();
                        }
                    }
                });
                function addActive(x) {
                    /*a function to classify an item as "active":*/
                    if (!x)
                        return false;
                    /*start by removing the "active" class on all items:*/
                    removeActive(x);
                    if (currentFocus >= x.length)
                        currentFocus = 0;
                    if (currentFocus < 0)
                        currentFocus = (x.length - 1);
                    /*add class "autocomplete-active":*/
                    x[currentFocus].classList.add("autocomplete-active");
                }
                function removeActive(x) {
                    /*a function to remove the "active" class from all autocomplete items:*/
                    for (var i = 0; i < x.length; i++) {
                        x[i].classList.remove("autocomplete-active");
                    }
                }
                function closeAllLists(elmnt) {
                    /*close all autocomplete lists in the document,
                     except the one passed as an argument:*/
                    var x = document.getElementsByClassName("autocomplete-items");
                    for (var i = 0; i < x.length; i++) {
                        if (elmnt != x[i] && elmnt != inp) {
                            x[i].parentNode.removeChild(x[i]);
                        }
                    }
                }
                /*execute a function when someone clicks in the document:*/
                document.addEventListener("click", function (e) {
                    closeAllLists(e.target);
                });
            }


        </script>

        <script>
            $(document).ready(function () {
                var treader = [];
                autocomplete(document.getElementById("treader"), treader);
                $("#treader").keypress(function () {

                    autocomplete(document.getElementById("treader"), treader);
                });
                $("#typeStationId").change(function () {
                    $("#fname").val($("#treader").val() + "-" + $("#typeStationId :selected").text());
                });
            }
            );
            $.ajax({
                url: '../SoldTopUp',
                type: 'GET',
                data: {
                    // Read values
                    status: $('#statusStation :selected').val(),
                    type: $('#type :selected').val(),
                    name: $('#treader').val(),
                    dateDebut: $('#dateDebut').val(),
                    dateFin: $('#dateFin').val(),
                    timeDebut: $('#timeDebut').val(),
                    timeFin: $('#timeFin').val(),
                    simType : $('#simType :selected').val(),
                    operator: $('#operator :selected').val(),
                    offer: $('#offer :selected').val(),
                    sim: $('#sim :selected').val(),
                    minSold: $("#minSold").val(),
                    maxSold: $("#maxSold").val()

                },
                success: function (result) {
                    //Do nothing                    
                    $('#sold').text(result.sumValid);
                    $('#soldLitig').text(result.sumLitig);
                },
                async: false

            });
            function rechercher() {
                $('#example').DataTable().draw();
                $.ajax({
                    url: '../SoldTopUp',
                    type: 'GET',
                    data: {
                        // Read values
                        status: $('#statusStation :selected').val(),
                        type: $('#type :selected').val(),
                        name: $('#treader').val(),
                        dateDebut: $('#dateDebut').val(),
                        dateFin: $('#dateFin').val(),
                        timeDebut: $('#timeDebut').val(),
                        timeFin: $('#timeFin').val(),
                        simType : $('#simType :selected').val(),
                        operator: $('#operator :selected').val(),
                        offer: $('#offer :selected').val(),
                        sim: $('#sim :selected').val(),
                        minSold: $("#minSold").val(),
                        maxSold: $("#maxSold").val()

                    },
                    success: function (result) {
                        //Do nothing                    
                        $('#sold').text(result.sumValid);
                        $('#soldLitig').text(result.sumLitig);

                    }

                });
                $.ajax({
                    url: '../NombertrasntactionByStatus',
                    type: 'GET',
                    data: {
                        // Read values

                        status: $('#statusStation :selected').val(),
                        type: $('#type :selected').val(),
                        name: $('#treader').val(),
                        dateDebut: $('#dateDebut').val(),
                        dateFin: $('#dateFin').val(),
                        timeDebut: $('#timeDebut').val(),
                        timeFin: $('#timeFin').val(),
                        simType : $('#simType :selected').val(),
                        operator: $('#operator :selected').val(),
                        offer: $('#offer :selected').val(),
                        sim: $('#sim :selected').val(),
                        minSold: $("#minSold").val(),
                        maxSold: $("#maxSold").val()

                    },
                    success: function (responseText) {
                        //Do nothing                    
                        // dataStatusT = parseresult;
                        console.log(responseText);
                        var plotObj = $.plot($("#flot-pie-chart-status"), JSON.parse(responseText), option);
                        plotObj.draw();
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        console.log(xhr.status);
                        console.log(thrownError);
                    }

                });
                $.ajax({
                    url: '../SoldByOperartor',
                    type: 'GET',
                    data: {
                        // Read values

                        status: $('#statusStation :selected').val(),
                        type: $('#type :selected').val(),
                        name: $('#treader').val(),
                        dateDebut: $('#dateDebut').val(),
                        dateFin: $('#dateFin').val(),
                        timeDebut: $('#timeDebut').val(),
                        timeFin: $('#timeFin').val(),
                        simType : $('#simType :selected').val(),
                        operator: $('#operator :selected').val(),
                        offer: $('#offer :selected').val(),
                        sim: $('#sim :selected').val(),
                        minSold: $("#minSold").val(),
                        maxSold: $("#maxSold").val()

                    },
                    success: function (result) {
                        //Do nothing 
                        $("#MOBILIS").text("0 DA");
                        $("#DJEZZY").text("0 DA");
                        $("#OOREDOO").text("0 DA");

                        for (let i = 0; i < result.length; i++) {
                            $("#" + result[i].label).text(result[i].data + " DA")
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        console.log(xhr.status);
                        console.log(thrownError);
                    }

                });

            }
            // chart  bar 

            var dataStatusT = [<%  List listLable = new TransactionTopup_Util().getAllTransactionTopupGroupTransactionBySatusLabel("", "");
                List list1Count = new TransactionTopup_Util().getAllTransactionTopupGroupTransactionBySatusCount("", "");
                for (int i = 0; i < listLable.size(); i++) {
                    int j = 1;
                    out.println("{label: '" + (String) listLable.get(i).toString().replace("TCT_", "").replace("ENT_", "")  + ": " + list1Count.get(i).toString() + "',data: " + list1Count.get(i).toString() + "}");

                    if (i < listLable.size() - 1) {
                        out.print(",");
                    }

                }

            %>
            ]
            var option = {
                series: {
                    pie: {
                        show: true,
                        radius: 3 / 4,
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
            };
            var plotObj = $.plot($("#flot-pie-chart-status"), dataStatusT, option);
            //

            $.ajax({
                url: '../SoldByOperartor',
                type: 'GET',
                data: {
                    // Read values

                    status: $('#statusStation :selected').val(),
                    type: $('#type :selected').val(),
                    name: $('#treader').val(),
                    dateDebut: $('#dateDebut').val(),
                    dateFin: $('#dateFin').val(),
                    timeDebut: $('#timeDebut').val(),
                    timeFin: $('#timeFin').val(),
                    simType : $('#simType :selected').val(),
                    operator: $('#operator :selected').val(),
                    offer: $('#offer :selected').val(),
                    sim: $('#sim :selected').val(),
                    minSold: $("#minSold").val(),
                    maxSold: $("#maxSold").val()
                },
                success: function (result) {
                    //Do nothing 
                    $("#MOBILIS").text("0 DA");
                    $("#DJEZZY").text("0 DA");
                    $("#OOREDOO").text("0 DA");
                    for (let i = 0; i < result.length; i++) {
                        $("#" + result[i].label).text(result[i].data + " DA")
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log(xhr.status);
                    console.log(thrownError);
                }

            });
        </script>
    </body>

</html>

