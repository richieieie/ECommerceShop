<%-- 
    Document   : dashboard
    Created on : Jun 9, 2024, 9:37:45 PM
    Author     : Trung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="../css/accounts.css" rel="stylesheet" type="text/css"/>
        <link href="../css/global.css" rel="stylesheet" type="text/css"/>
        <title>Dashboard</title>
    </head>
    <body>
        <c:set var="employees" value="${requestScope.employees}"></c:set>
        <jsp:include page="../jspSegments/navbar.jsp"></jsp:include>
            <div class="container">
                <h1 class="text-center mt-3">Accounts</h1>
                <div class="add-box text-center">
                    <a href="/account?action=register" class="btn btn-primary">Create A New Account</a>
                </div>
                <table class="table table-striped table-bordered mt-3">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">Account</th>
                            <th scope="col">Last Name</th>
                            <th scope="col">First Name</th>
                            <th scope="col">Birthday</th>
                            <th scope="col">Gender</th>
                            <th scope="col">Phone</th>
                            <th scope="col">Status</th>
                            <th scope="col">Role</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${employees}" var="e">
                        <c:if test="${!e.getAccount().equals(sessionScope.account.getAccount())}">
                            <tr class="product">
                                <th scope="row">${e.getAccount()}</th>
                                <td>${e.getLastName()}</td>
                                <td>${e.getFirstName()}</td>
                                <td>${e.getBirthday()}</td>
                                <td>${e.isGender() == true ? "Male" : "Female"}</td>
                                <td>${e.getPhone()}</td>
                                <c:if test="${e.isIsUse()}"><td class="text-success">Active</td></c:if>
                                <c:if test="${!e.isIsUse()}"><td class="text-danger">Disable</td></c:if>
                                    <td>
                                    <c:choose>
                                        <c:when test="${e.getRoleInSystem() == 1}">ADMIN</c:when>
                                        <c:when test="${e.getRoleInSystem() == 2}">STAFF</c:when>
                                        <c:otherwise>CUSTOMER</c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="action-box text-center">
                                    <c:choose>
                                        <c:when test="${e.isIsUse() == true}">
                                            <a href="/account?action=delete&account=${e.getAccount()}" class="btn btn-delete btn-danger">Delete</a>
                                        </c:when>
                                        <c:when test="${e.isIsUse() == false}">
                                            <a href="/account?action=active&account=${e.getAccount()}" class="btn btn-active btn-warning">Active</a>
                                        </c:when>
                                    </c:choose>
                                    <a href="/account?action=update&account=${e.getAccount()}" class="btn btn-success">Edit</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script>
            var dels = document.getElementsByClassName('btn-delete');
            var acts = document.getElementsByClassName('btn-active');
            var confirmDel = function (e) {
                if (!confirm('Are you sure to delete this account?'))
                    e.preventDefault();
            };

            var confirmAct = function (e) {
                if (!confirm('Are you sure to activate this account?'))
                    e.preventDefault();
            };

            for (var i = 0, l = dels.length; i < l; i++) {
                dels[i].addEventListener('click', confirmDel, false);
            }

            for (var i = 0, l = acts.length; i < l; i++) {
                acts[i].addEventListener('click', confirmAct, false);
            }

        </script>
    </body>
</html>
