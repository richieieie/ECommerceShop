<%-- 
    Document   : dashboard
    Created on : Jun 9, 2024, 9:37:45 PM
    Author     : Trung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="/WEB-INF/tlds/Pagination" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="utils.Santinization" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="../css/global.css" rel="stylesheet" type="text/css"/>
        <link href="../css/productPortfolio.css" rel="stylesheet" type="text/css"/>
        <title>Portfolio</title>
    </head>
    <body>
        <c:set var="products" value="${requestScope.products}"></c:set>
        <c:set var="curPage" value="${param.page}"></c:set>
        <c:set var="totalPages" value="${requestScope.pages}"></c:set>
        <c:set var="categories" value="${requestScope.categories}"></c:set>
        <c:set var="selectedTypeIds" value="${requestScope.selectedTypeIds}"></c:set>
        <jsp:include page="../jspSegments/navbar.jsp"></jsp:include>
            <div class="container">
                <h1 class="title text-center mt-3">Products</h1>
                <div class="content mt-3 row col-12">
                <p:FilterProductBox></p:FilterProductBox>
                <div class="products col-md-10 col row mt-0 text-center">
                    <c:forEach items="${products}" var="p">
                        <div class="product col-md-2 col mx-3">
                            <div class="product__image col"><img src="${p.getProductImage()}" alt="${p.getProductName()}"/></div>
                            <div class="product__name col">${p.getProductName()}</div>
                            <div class="product__price col">$${Santinization.doubleToInt(p.getPrice() * (100 - p.getDiscount()) / 100)}</div>
                            <a href="/product?action=get_details&productId=${p.getProductId()}" class="product__detail btn btn-dark">More details</a>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <p:ShowPage></p:ShowPage>
        </div>
    </body>
</html>
</body>
</html>
