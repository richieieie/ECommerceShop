<%-- 
    Document   : dashboard
    Created on : Jun 9, 2024, 9:37:45 PM
    Author     : Trung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="utils.Santinization" %>
<c:set var="product" value="${requestScope.product}"></c:set>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
            <link href="../css/global.css" rel="stylesheet" type="text/css"/>
            <link href="../css/productDetails.css" rel="stylesheet" type="text/css"/>
            <title>${product.getProductName()}</title>
    </head>
    <body>
        <jsp:include page="../jspSegments/navbar.jsp"></jsp:include>
            <div class="container">
                <div class="product row gx-5 mt-3 p-3">
                    <div class="product__image col-md-6"><img src="${product.getProductImage()}" alt="${product.getProductName()}"/></div>
                <div class="product__detail col-md-6">
                    <h1 class="product__name">${product.getProductName()}</h1>
                    <h5 class="text-success">$${Santinization.doubleToInt(product.getPrice() * (100 - product.getDiscount()) / 100)}/ ${product.getUnit()}</h5>
                    <c:if test="${product.getDiscount() > 0}">
                        <p>List price: <del class="text-danger">$${product.getPrice()}</del> <span class="product__discount">(10% off)</span></p>
                    </c:if>
                    <h3>Description:</h3>
                    <p class="product_breif">${product.getBrief()}</p>
                    <c:choose>
                        <c:when test="${sessionScope.account != null && sessionScope.account.getRoleInSystem() != 3}">
                            <a class="product__btn btn btn-dark" href="/account?action=logout">Log out and use customer account</a>
                        </c:when>
                        <c:when test="${sessionScope.account == null}">
                            <a class="product__btn btn btn-dark" href="/account?action=login">Add to cart</a>
                        </c:when>
                        <c:otherwise>
                            <form action="/cart?action=add" method="POST" class="mb-3">
                                <input type="hidden" name="productId" value="${product.getProductId()}"/>
                                <button type="submit" class="product__btn btn btn-dark">Add to cart</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </body>
</html>
