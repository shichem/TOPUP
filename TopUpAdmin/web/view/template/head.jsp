<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><%="EtopUp"%></title>

    <!-- Bootstrap Core CSS -->
    <link href="../lib/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../lib/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../lib/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../lib/vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../lib/vendor/font-awesome/css/all.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="../lib/datatable/dataTables.bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="../lib/datatable/responsive.bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="../lib/datatable/datatables.min.css"/>
<link rel="stylesheet" type="text/css" href="../lib/datatable/buttons.dataTables.min.css"/>
<link rel="stylesheet" type="text/css" href="../lib/vendor/jquery/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="../lib/notiflix/notiflix-2.4.0.min.css"/>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<%
    if (session.getAttribute("username") != null) {
    } else {
        response.sendRedirect("../login.jsp");
    }
%>
