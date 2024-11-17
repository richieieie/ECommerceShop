<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .ns-submit-btn {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .dropdown-menu {
        background-color: #000 
    }

    .dropdown-item {
        color: #fff;
    }

    .dropdown-item:hover {
        color: #000;
        background-color: #fff
    }
</style>
<script src="../bootstrap.bundle.min.js" type="text/javascript"></script>
<c:set var="currentRole" value="${sessionScope.account.getRoleInSystem()}"/>
<nav class="navbar navbar-expand-lg bg-black fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand text-white">Welcome ${sessionScope.account.getLastName()} ${sessionScope.account.getFirstName()}</a>
        <button class="navbar-toggler text-white" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:if test="${sessionScope.account != null}">
                    <c:if test="${currentRole == 1 || currentRole == 2}">
                        <li class="nav-item">
                            <a class="nav-link text-white" href="/product?action=dashboard&page=0">Dashboard</a>
                        </li>
                    </c:if>
                    <c:if test="${currentRole != 1 && currentRole != 2}">
                        <li class="nav-item">
                            <a class="nav-link text-white" href="/cart?action=get">Cart</a>
                        </li>
                    </c:if>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Account
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="/account?action=update&account=${sessionScope.account.getAccount()}">Info</a></li>
                            <li><a class="dropdown-item" href="/account?action=change_password">Change Password</a></li>
                        </ul>
                    </li>
                    <c:if test="${currentRole == 1}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Manage
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="/product?action=manage&page=0">Products</a></li>
                                <li><a class="dropdown-item" href="/category?action=manage">Categories</a></li>
                                <li><a class="dropdown-item" href="/account?action=manage&page=0">Accounts</a></li>
                            </ul>
                        </li>
                    </c:if>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link text-white" href="/product?action=get_portfolio&page=0">Portfolio</a>
                </li>
            </ul>
            <c:if test="${sessionScope.account != null}">
                <a class="ns-submit-btn btn btn-outline-light" href="/account?action=logout">Logout</a>
            </c:if>
            <c:if test="${sessionScope.account == null}">
                <a class="ns-submit-btn btn btn-outline-light" href="/account?action=login">Log in</a>
            </c:if>
        </div>
    </div>
</nav>