<%-- 
    Document   : input
    Created on : Jun 11, 2024, 3:45:50 PM
    Author     : Trung
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .is-form-group {
        position: relative;
        display: flex;
        align-items: center;
        background-color: transparent;
        border: 1px solid #000;
        border-radius: 8px;
        /*overflow: hidden;*/
    }

    .svg-container {
        padding-left: 12px;
        display: flex;
        align-items: center;
    }

    .is-form-control {
        border-radius: 0%;
        border: none;
        background-color: transparent
    }

    .is-form-text {
        display: inline-block;
        position: absolute;
        top: 100%;
        left: 0;
        z-index: 10
    }

    .is-form-group:has(input:focus), .is-form-group:has(select:focus) {
        outline: 4px solid #000;
    }

    /* Chrome, Safari, Edge */
    input[type="number"]::-webkit-outer-spin-button,
    input[type="number"]::-webkit-inner-spin-button {
        -webkit-appearance: none !important;
        margin: 0 !important;
    }

    /* Firefox */
    input[type="number"] {
        -moz-appearance: textfield !important;
    }
</style>
<c:set var="errorName" value="${param.attribute}Error"/>
<c:if test="${param.attribute == 'productImage'}">
    <c:set var="acceptAttr" value='accept="image/png, image/jpeg, image/jpg"' />
</c:if>
<div>
    <label for="${param.attribute}">${param.placeholder}:</label>
    <div class="is-form-group form-group mb-3">
        <div class="svg-container">
            ${param.svgTag}
        </div>
        <input type="${param.type}" class="is-form-control form-control shadow-none" id="${param.attribute}" name="${param.attribute}" ${acceptAttr != null ? acceptAttr : ''}
               aria-describedby="${param.attribute}" value="${param.value}" <c:if test="${param.type == 'number'}">min="0"</c:if> <c:if test="${param.attribute == 'discount'}">max="100"</c:if>/>
        <c:if test="${requestScope[errorName] != null}">
            <span class="is-form-text text-danger m-0">${requestScope[errorName]}</span>
        </c:if>
    </div>
</div>
