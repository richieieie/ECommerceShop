<%-- 
    Document   : dashboard
    Created on : Jun 9, 2024, 9:37:45 PM
    Author     : Trung
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="model.Product"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="/WEB-INF/tlds/Pagination" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="../css/dashboard.css" rel="stylesheet" type="text/css"/>
        <link href="../css/global.css" rel="stylesheet" type="text/css"/>
        <title>Dashboard</title>
    </head>
    <body>
        <c:set var="curPage" value="${param.page}"></c:set>
        <c:set var="totalPages" value="${requestScope.pages}"></c:set>
        <jsp:include page="../jspSegments/navbar.jsp"></jsp:include>
            <div class="container">
                <h1 class="text-center mt-3">Products</h1>
                <div class="add-box text-center">
                    <a href="/product?action=create" class="btn btn-primary">Create A New Product</a>
                </div>
                <form action="/product" method="get" class="filter col mt-3 bg-dark text-white">
                    <input type="hidden" name="action" value="${param.action}"/>
                <input type="hidden" name="page" value="0"/>
                <c:forEach items="${requestScope.categories}" var="c">
                    <div class="m-3">
                        <input class="form-check-input" 
                               type="checkbox" 
                               value="${c.getTypeId()}" 
                               id="${c.getTypeId()}" 
                               name="typeId"
                               <c:if test="${not empty selectedTypeIds}">
                                   <c:forEach var="selectedId" items="${selectedTypeIds}">
                                       <c:if test="${selectedId == c.getTypeId()}">
                                           checked
                                       </c:if>
                                   </c:forEach>
                               </c:if>
                               />
                        <label class="form-check-label" for="${c.getTypeId()}">
                            ${c.getCategoryName()}
                        </label>
                    </div>
                </c:forEach>
                <input type="text" class="mx-3" id="productName" name="productName" placeholder="Product name"/>
                <div class="text-center">
                    <button type="submit" class="btn btn-dark btn-outline-light">Filter</button>
                </div>
            </form>
            <table class="table table-striped table-bordered mt-3">
                <thead class="table-dark">
                    <tr>
                        <th scope="col" class="col-1">ID</th>
                        <th scope="col" class="col-1">Name</th>
                        <th scope="col" class="col-4">Brief</th>
                        <th scope="col" class="col-1">Posted Date</th>
                        <th scope="col">Type</th>
                        <th scope="col">Unit</th>
                        <th scope="col" class="col-1">Price</th>
                        <th scope="col">Discount</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.products}" var="product">
                        <tr class="product">
                            <th scope="row">${product.getProductId()}</th>
                            <td>${product.getProductName()}</td>
                            <td>${product.getBrief()}</td>
                            <td>${product.getPostedDate()}</td>
                            <td>${product.getCategory().getCategoryName()}</td>
                            <td>${product.getUnit()}</td>
                            <td>${product.getPrice()}</td>
                            <td>${product.getDiscount()}</td>
                            <td class="action-box text-center">
                                <a href="/product?action=update&productId=${product.getProductId()}" class="btn btn-success">Edit</a>
                                <a href="/product?action=delete&productId=${product.getProductId()}&account=${product.getAccountDTO().getAccount()}" class="btn btn-danger btn-delete">Delete</a>
                                <a href="${product.getProductImage()}" class="btn btn-primary">See Image</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!--            <nav class="pages mt-5">
                            <ul class="pagination justify-content-center">
                                <li class="page-item"><a class="page-link <c:if test="${curPage < 1}">disabled</c:if>" href="${requestScope.curURL}&page=${curPage - 1}">Previous</a></li>
            <c:forEach var="i" begin="1" end="${totalPages}">
            <li class="page-item"><a class="page-link <c:if test="${curPage + 1 == i}">bg-black text-white</c:if>" href="${requestScope.curURL}&page=${i - 1}">${i}</a></li>
            </c:forEach>
        <li class="page-item"><a class="page-link <c:if test="${curPage + 1 >= totalPages}">disabled</c:if>" href="${requestScope.curURL}&page=${curPage + 1}">Next</a></li>
        </ul>
    </nav>-->
            <p:ShowPage></p:ShowPage>
        </div>
        <script>
            var elems = document.getElementsByClassName('btn-delete');
            var confirmIt = function (e) {
                if (!confirm('Are you sure to delete this product?'))
                    e.preventDefault();
            };
            for (var i = 0, l = elems.length; i < l; i++) {
                elems[i].addEventListener('click', confirmIt, false);
            }
        </script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
