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
        <link href="../css/global.css" rel="stylesheet" type="text/css"/>
        <title>Create A New Product</title>
    </head>
    <body>
        <c:set var="product" value="${requestScope.product}"/>
        <c:set var="product" value="${requestScope.product}"></c:set>
        <jsp:include page="../jspSegments/navbar.jsp"></jsp:include>
            <div class="container">
                <h1 class="text-center mt-3">Add A New Product</h1>
                <form action="/product?action=create" method="post" class="create-product-form" id="create-product-form" enctype="multipart/form-data">
                    <input type="hidden" value="" name="productId" id="productId"/>
                <c:if test="${sessionScope.account.getRoleInSystem() == 1}">
                    <label for="account">Account:</label>
                    <div class="is-form-group form-group mb-3">
                        <div class="svg-container"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-lock" viewBox="0 0 16 16">
                            <path d="M11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0M8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4m0 5.996V14H3s-1 0-1-1 1-4 6-4q.845.002 1.544.107a4.5 4.5 0 0 0-.803.918A11 11 0 0 0 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664zM9 13a1 1 0 0 1 1-1v-1a2 2 0 1 1 4 0v1a1 1 0 0 1 1 1v2a1 1 0 0 1-1 1h-4a1 1 0 0 1-1-1zm3-3a1 1 0 0 0-1 1v1h2v-1a1 1 0 0 0-1-1"/>
                            </svg>
                        </div>
                        <select class="form-select is-form-control form-control shadow-none" id="account" name="account">
                            <c:forEach items="${requestScope.employees}" var="e">
                                <option <c:if test="${product.getAccountDTO().equals(e)}">selected</c:if> value="${e.getAccount()}">${e.getAccount()} - ${e.getLastName()} ${e.getFirstName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>
                <c:if test="${sessionScope.account.getRoleInSystem() != 1}">
                    <input value="${sessionScope.account.getAccount()}" name="account" id="account" type="hidden"/>
                </c:if>
                <c:import url="../jspSegments/input.jsp">
                    <c:param name="svgTag" value='<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
                             <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M3.102 4l1.313 7h8.17l1.313-7zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2m7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2"/>
                             </svg>'/>
                    <c:param name="type" value="text"/>
                    <c:param name="attribute" value="productName"/>
                    <c:param name="placeholder" value="Name"/>
                    <c:param name="value" value="${product.getProductName()}"/>
                </c:import>
                <c:import url="../jspSegments/input.jsp">
                    <c:param name="svgTag" value='<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-image" viewBox="0 0 16 16">
                             <path d="M6.502 7a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3"/>
                             <path d="M14 14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zM4 1a1 1 0 0 0-1 1v10l2.224-2.224a.5.5 0 0 1 .61-.075L8 11l2.157-3.02a.5.5 0 0 1 .76-.063L13 10V4.5h-2A1.5 1.5 0 0 1 9.5 3V1z"/>
                             </svg>'/>
                    <c:param name="type" value="file"/>
                    <c:param name="attribute" value="productImage"/>
                    <c:param name="placeholder" value="Image"/>
                </c:import>
                <c:import url="../jspSegments/input.jsp">
                    <c:param name="svgTag" value='<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chat-left-text" viewBox="0 0 16 16">
                             <path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
                             <path d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5M3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6m0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5"/>
                             </svg>'/>
                    <c:param name="type" value="text"/>
                    <c:param name="attribute" value="brief"/>
                    <c:param name="placeholder" value="Brief"/>
                    <c:param name="value" value="${product.getBrief()}"/>
                </c:import>
                <label for="typeId">Category:</label>
                <div class="is-form-group form-group mb-3">
                    <div class="svg-container"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmark-star" viewBox="0 0 16 16">
                        <path d="M7.84 4.1a.178.178 0 0 1 .32 0l.634 1.285a.18.18 0 0 0 .134.098l1.42.206c.145.021.204.2.098.303L9.42 6.993a.18.18 0 0 0-.051.158l.242 1.414a.178.178 0 0 1-.258.187l-1.27-.668a.18.18 0 0 0-.165 0l-1.27.668a.178.178 0 0 1-.257-.187l.242-1.414a.18.18 0 0 0-.05-.158l-1.03-1.001a.178.178 0 0 1 .098-.303l1.42-.206a.18.18 0 0 0 .134-.098z"/>
                        <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1z"/>
                        </svg>
                    </div>
                    <select class="form-select is-form-control form-control shadow-none" id="typeId" name="typeId">
                        <c:forEach items="${requestScope.categories}" var="c">
                            <option <c:if test="${product.getCategory().getTypeId() == c.getTypeId()}">selected</c:if> value="${c.getTypeId()}">${c.getCategoryName()}</option>
                        </c:forEach>
                    </select>
                </div>
                <c:import url="../jspSegments/input.jsp">
                    <c:param name="svgTag" value='<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive" viewBox="0 0 16 16">
                             <path d="M0 2a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v2a1 1 0 0 1-1 1v7.5a2.5 2.5 0 0 1-2.5 2.5h-9A2.5 2.5 0 0 1 1 12.5V5a1 1 0 0 1-1-1zm2 3v7.5A1.5 1.5 0 0 0 3.5 14h9a1.5 1.5 0 0 0 1.5-1.5V5zm13-3H1v2h14zM5 7.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5"/>
                             </svg>'/>
                    <c:param name="type" value="text"/>
                    <c:param name="attribute" value="unit"/>
                    <c:param name="placeholder" value="Unit"/>
                    <c:param name="value" value="${product.getUnit()}"/>
                </c:import>
                <c:import url="../jspSegments/input.jsp">
                    <c:param name="svgTag" value='<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cash-coin" viewBox="0 0 16 16">
                             <path fill-rule="evenodd" d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8m5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0"/>
                             <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195z"/>
                             <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083q.088-.517.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1z"/>
                             <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 6 6 0 0 1 3.13-1.567"/>
                             </svg>'/>
                    <c:param name="type" value="number"/>
                    <c:param name="attribute" value="price"/>
                    <c:param name="placeholder" value="Price"/>
                    <c:param name="value" value="${product.getPrice()}"/>
                </c:import>
                <c:import url="../jspSegments/input.jsp">
                    <c:param name="svgTag" value='<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-percent" viewBox="0 0 16 16">
                             <path d="M13.442 2.558a.625.625 0 0 1 0 .884l-10 10a.625.625 0 1 1-.884-.884l10-10a.625.625 0 0 1 .884 0M4.5 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5m7 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5"/>
                             </svg>'/>
                    <c:param name="type" value="number"/>
                    <c:param name="attribute" value="discount"/>
                    <c:param name="placeholder" value="Discount"/>
                    <c:param name="value" value="${product.getDiscount()}"/>
                </c:import>
                <div class="text-center">
                    <button type="submit" form="create-product-form" class="btn btn-success"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-indent" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M3 8a.5.5 0 0 1 .5-.5h6.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H3.5A.5.5 0 0 1 3 8"/>
                        <path fill-rule="evenodd" d="M12.5 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5"/>
                        </svg>
                        Create
                    </button>
                </div>
            </form>
        </div>
    </body>
</html>
