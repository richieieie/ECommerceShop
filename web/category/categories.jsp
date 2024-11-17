<%-- 
    Document   : dashboard
    Created on : Jun 9, 2024, 9:37:45 PM
    Author     : Trung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="categories" value="${requestScope.categories}"></c:set>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
            <link href="../css/categories.css" rel="stylesheet" type="text/css"/>
            <link href="../css/global.css" rel="stylesheet" type="text/css"/>
            <title>Dashboard</title>
        </head>
        <body>
        <jsp:include page="../jspSegments/navbar.jsp"></jsp:include>
            <div class="container">
                <h1 class="text-center mt-3">Products</h1>
                <div class="add-box text-center">
                    <a href="/category?action=create" class="btn btn-primary">Create A New Category</a>
                </div>
                <table class="table table-striped .table-bordered mt-3">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">Index</th>
                            <th scope="col">Name</th>
                            <th scope="col">Memo</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="i" begin="1" end="${categories.size()}">
                        <tr class="product">
                            <th scope="row">${i}</th>
                            <td>${categories.get(i - 1).getCategoryName()}</td>
                            <td>${categories.get(i - 1).getMemo()}</td>
                            <td class="action-box">
                                <a href="/category?action=delete&typeId=${categories.get(i - 1).getTypeId()}" class="btn btn-delete btn-danger">Delete</a>
                                <a href="/category?action=update&typeId=${categories.get(i - 1).getTypeId()}" class="btn btn-success">Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script>
            var elems = document.getElementsByClassName('btn-delete');
            var confirmIt = function (e) {
                if (!confirm('Are you sure to delete this category?'))
                    e.preventDefault();
            };
            for (var i = 0, l = elems.length; i < l; i++) {
                elems[i].addEventListener('click', confirmIt, false);
            }
        </script>
    </body>
</html>
