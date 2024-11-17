<%-- 
    Document   : carts
    Created on : Jul 1, 2024, 1:14:43 PM
    Author     : Trung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="utils.Santinization" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="../css/dashboard.css" rel="stylesheet" type="text/css"/>
        <link href="../css/global.css" rel="stylesheet" type="text/css"/>
        <title>Cart</title>
    </head>
    <body>
        <c:set var="cart" value="${requestScope.cart}">            
        </c:set>
        <jsp:include page="../jspSegments/navbar.jsp"></jsp:include>
        <div class="container">
            <h1 class="text-center mt-3">Your Cart</h1>
            <table class="table table-striped table-bordered mt-3">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">Product</th>
                        <th scope="col">Image</th>
                        <th scope="col">Category</th>
                        <th scope="col">Price</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Subtotal</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${cart.keySet()}" var="p">
                    <tr>
                        <c:set var="currentPrice" value="${Santinization.doubleToInt(p.getPrice() * (100 - p.getDiscount()) / 100)}">                            
                        </c:set>
                        <td>${p.getProductName()}</td>
                        <td class="text-center"><img src="${p.getProductImage()}" width="150px" height="150px"/></td>
                        <td>${p.getCategory().getCategoryName()}</td>
                        <td>$${currentPrice}</td>
                        <td>${cart.get(p)} ${p.getUnit()}</td>
                        <td>$${currentPrice * cart.get(p)}</td>
                        <td class="text-center"><a class="btn btn-danger" href="/cart?action=delete&productId=${p.getProductId()}">Delete</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
