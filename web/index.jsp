<%-- 
    Document   : index
    Created on : Jun 19, 2024, 9:37:23 PM
    Author     : Trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="./css/index.css">
    <script src="./bootstrap-5.3.2-dist/js/bootstrap.bundle.js"></script>
    <title>Welcome</title>
</head>

<body>
    <div class="container-md">
        <div class="content-box">
            <h1 class="text-center">Welcome To Product Management Web</h1>
            <div class="nav">
                <a href="/product?action=get_portfolio&page=0" class="portfolio-btn text-center">Products</a>
                <a href="/account?action=login" class="login-btn text-center">Your Account</a>
            </div>
        </div>
    </div>
</body>

</html>
