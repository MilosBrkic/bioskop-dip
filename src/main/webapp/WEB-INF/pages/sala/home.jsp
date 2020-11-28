<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<div>
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert mb-2">${error}</div>
    </c:if>

    <div class="container-fluid">
        <table class="table table-sm">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><fmt:message key="sala.brojSale" /></th>
                    <th scope="col"><fmt:message key="sala.brojSedista" /></th>
                    <th scope="col"><fmt:message key="akcija" /></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach
                    items="${sale}"
                    var="sala"
                    varStatus="loop">
                    <tr>
                        <td>${loop.index+1}</td>
                        <td><a href="<c:url value = "/sala/${sala.brojSale}/view/"></c:url>">${sala.brojSale}</a></td>
                        <td>${sala.brojSedista}</td>
                        <td>
                            <a class="btn btn-danger" href="<c:url value = "/sala/${sala.brojSale}/delete/"></c:url>"><fmt:message key="delete" /></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>