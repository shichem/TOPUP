<%-- 
    Document   : menu
    Created on : Mar 24, 2019, 3:24:56 PM
    Author     : macbookpro
--%>

<%@page import="custom_vars.staticVars"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse">
        <ul class="nav" id="side-menu">

            <li>
                <a href="dashboard.jsp"><i class="fas fa-tachometer-alt fa-fw"></i> Dashboard</a>
            </li>
             <li>
                <a href="statistique.jsp"><i class="fas fa-chart-pie fa-fw"></i> Statistique</a>
            </li>
            <li class="active">
                <a href="#"><i class="fa fa-sim-card fa-fw"></i> Gestion SIM <span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                     <li>
                         <a href="addSim.jsp"><i class="fa fa-sim-card fa-fw"></i>Ajouter SIM</a>
                     </li>
                    <li>
                        <a href="./listSim.jsp"><i class="fa fa-list fa-fw"></i>List SIM</a>
                    </li>
                </ul>
                <!-- /.nav-second-level -->
            </li>


            <li class="active">
                <a href="#"><i class="fa fa-users fa-fw"></i> Gestion des clients<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level ">
                      <li>
                        <a href="./addSGrossiste.jsp"><i class="fa fa-user fa-fw"></i>Ajouter Super grossiste</a>
                    </li>
                         <li>
                        <a href="./addClientGro.jsp"><i class="fa fa-user fa-fw"></i>Ajouter Grossiste</a>
                    </li>
                    <li>
                        <a href="./addClient.jsp"><i class="fa fa-user fa-fw"></i>Ajouter Detaillant</a>
                    </li>
                
                 
                    <li>
                        <a href="./listClient.jsp"><i class="fa fa-list fa-fw"></i>List clients</a>
                    </li>
                </ul>
                <!-- /.nav-second-level -->
            </li>
            <li class="active">
                <a><i class="fa fa-exchange-alt fa-fw"></i> Transaction  <span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li>
                        <a href="./listTransactionSold.jsp"><i class="fa fa-list fa-fw"></i>Sold</a>
                    </li>
                    <li>
                        <a href="./listTransactionTopUp.jsp"><i class="fa fa-list fa-fw"></i>TopUp</a>
                    </li>
                </ul>
                <!-- /.nav-second-level -->
            </li>

            <li class="active">
                <a href="#"><i class="fa fa-laptop fa-fw"></i> Gestion des Station<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li>
                        <a href="./addStation.jsp"><i class="fa fa-user fa-fw"></i>Ajouter Station</a>
                    </li>
                    <li>
                        <a href="./listStation.jsp"><i class="fa fa-list fa-fw"></i>List Station <%=staticVars.traderCategory_Detaillant%></a>

                    </li>
                    <li>
                        <a href="./listStationGro.jsp"><i class="fa fa-list fa-fw"></i>List Station <%=staticVars.traderCategory_Grossiste%></a>

                    </li>
                </ul>
                <!-- /.nav-second-level -->
            </li>
            <li class="active">
                <a href="#"><i class="fa fa-users fa-fw"></i> Gestion Affection<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">

                    <li>
                        <a href="./listProviderClient.jsp"><i class="fa fa-list fa-fw"></i>List grossiste client</a>
                    </li>
                </ul>
                <!-- /.nav-second-level -->
            </li>
            <li class="active">
                <a href="#"><i class="fa fa-cubes fa-fw"></i> Gestion des offres <span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li>
                        <a href="addOffre.jsp"><i class="fa fa-cube fa-fw"></i>Ajouter offre</a>
                    </li>
                    <li>
                        <a href="./listOffre.jsp"><i class="fa fa-list fa-fw"></i>List offre</a>
                    </li>
                </ul>
                <!-- /.nav-second-level -->
            </li>


        </ul>
    </div>
    <!-- /.sidebar-collapse -->
</div>
