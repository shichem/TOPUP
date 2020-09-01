
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
                        <h1 class="page-header">Ajouter Station</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Information sur sim  
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <form role="form">
                                             <div class="col-lg-6" class="form-group">
                                                <label>Treader</label>
                                                 <select id="operateur" name="operateur" class="form-control">
                                                    <option value="1">Djezzy</option>
                                                    <option value="2">Mobilis</option>
                                                    <option value="3">Ooredoo</option>
                                                    
                                                </select>
                                            </div>

                                            <div class="col-lg-6" class="form-group">
                                                <label>Nom Station</label>
                                                <input id="fname"  name="fname" class="form-control" placeholder="Enter nom du sim">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>Numero sn1</label>
                                                <input id="sn2" name="sn2" class="form-control" placeholder="Enter numero serie  de votre station">
                                            </div>
                                               <div class="col-lg-6" class="form-group">
                                                <label>Numero sn1</label>
                                                <input id="sn2" name="sn2" class="form-control" placeholder="Enter numero serie de votre station">
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>type</label>
                                                 <select id="type" name="type" class="form-control">
                                                    <option value="1">Djezzy</option>
                                                    <option value="2">Mobilis</option>
                                                    <option value="3">Ooredoo</option>
                                                    
                                                </select>
                                            </div>
                                            <div class="col-lg-6" class="form-group">
                                                <label>status</label>
                                                <select id="status" name="status" class="form-control">
                                                   
                                                </select>
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
        <script src="./data/data_graph.js"></script>
    </body>

</html>

