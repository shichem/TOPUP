
<%@page import="model_db.StatusInfo"%>
<%@page import="model_helpers.StatusInfo_Util"%>
<%@page import="model_db.StationType"%>
<%@page import="model_db.ServerProfile"%>
<%@page import="model_helpers.ServerProfile_Util"%>
<%@page import="model_helpers.StationType_Util"%>
<%@page import="model_helpers.StationType_Util"%>
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
    <%@include file="template/head.jsp" %>

    <body>

        <div id="wrapper">

            <!-- Navigation -->
            <%@include file="template/navigation.jsp" %>

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Ajouter Station</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Information sur Station  
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <form role="form" action="../AddStation" method="POST">
                                            <div class="col-lg-12" class="form-group">
                                                <label>Client</label>
                                                <div class="autocomplete" >
                                                    <input id="treader" type="text" required="" name="trader" class=" form-control"  placeholder="nom du client " autocomplete="off">
                                                </div>

                                            </div>

                                            <div class="col-lg-6" class="form-group">
                                                <%                                                     List listTyoeStation = new StationType_Util().getAllStationType("");
                                                %>
                                                <label>type</label>
                                                <select id="typeStationId" required="" name="typeStationId" class="form-control">

                                                    <option value="">Selection Type du station  </option>

                                                    <%                                for (int i = 0; i < listTyoeStation.size(); i++) {
                                                            StationType get = (StationType) listTyoeStation.get(i);
                                                    %>
                                                    <option value="<%=get.getIdstationType()%>"><%=get.getStationTypeDesc()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>

                                            <div class="col-lg-6" class="form-group">
                                                <label>Nom Station</label>
                                                <input id="fname"  required="" name="fname" class="form-control" placeholder="Enter nom du sim">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Numero sn1</label>
                                                <input id="sn1" name="sn1" class="form-control" required="" placeholder="Enter numero serie  de votre station">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Numero sn1</label>
                                                <input id="sn2" name="sn2" class="form-control" required="" placeholder="Enter numero serie de votre station">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <%
                                                    List listStatus = new StatusInfo_Util().getStatusInfo_by_statusInfoDesc_Like("ENT", "");
                                                %>
                                                <label>status</label>
                                                <select id="type" name="statusStationId" required="" class="form-control">
                                                    <option value="">Selection status du station  </option>

                                                    <%                                for (int i = 0; i < listStatus.size(); i++) {
                                                            StatusInfo get = (StatusInfo) listStatus.get(i);
                                                    %>
                                                    <option value="<%=get.getIdstatusInfo()%>"><%=get.getStatusInfoDesc()%></option>
                                                    <%
                                                        }
                                                    %>

                                                </select>
                                            </div>

                                            <div id="serverProfile"   class="col-lg-6" class="form-group">
                                                <label>Server Profile </label>
                                                <%
                                                    List listServer = new ServerProfile_Util().getAllServerProfile("");
                                                %> 
                                                <select  id="serverProfileId" name="serverProfileId" required="" class="form-control">
                                                    <option value="">Selection un server profile  </option>

                                                    <%                                for (int i = 0; i < listServer.size(); i++) {
                                                            ServerProfile get = (ServerProfile) listServer.get(i);
                                                    %>
                                                    <option value="<%=get.getIdProfile()%>"><%=get.getServerAdress1()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>User Name</label>
                                                <input id="userName"  name="userName" required="" class="form-control" value="admin" placeholder="Enter nom du sim">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Password</label>
                                                <input id="password"  name="password" required="" class="form-control" value="admin" placeholder="Enter nom du sim">
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
                          $("#fname").val($("#treader").val() +"-"+ $("#typeStationId :selected").text());
                 });
             
            }
            );


        </script>
    </body>

</html>

