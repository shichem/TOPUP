<%-- 
    Document   : menu
    Created on : Mar 24, 2019, 3:24:56 PM
    Author     : macbookpro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
   <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                       
                        <li>
                            <a href="dashboard.jsp"><i class="fas fa-tachometer-alt fa-fw"></i> Dashboard</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-users fa-fw"></i> Gestion des clients<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="./addClient.jsp"><i class="fa fa-user fa-fw"></i>Ajouter client</a>
                                </li>
                                <li>
                                    <a href="./listClient.jsp"><i class="fa fa-list fa-fw"></i>List clients</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                         <li>
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
                           <li>
                            <a href="#"><i class="fa fa-sim-card fa-fw"></i> Gestion SIM <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                               <!--  <li>
                                    <a href="addSim.jsp"><i class="fa fa-sim-card fa-fw"></i>Ajouter SIM</a>
                                </li>-->
                                <li>
                                    <a href="./listSim.jsp"><i class="fa fa-list fa-fw"></i>List SIM</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        
                        <li>
                            <a href="#"><i class="fa fa-sim-card fa-fw"></i> Transaction  <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                               <!--  <li>
                                    <a href="addSim.jsp"><i class="fa fa-sim-card fa-fw"></i>Ajouter SIM</a>
                                </li>-->
                                <li>
                                    <a href="./listSim.jsp"><i class="fa fa-list fa-fw"></i>TopUp</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
