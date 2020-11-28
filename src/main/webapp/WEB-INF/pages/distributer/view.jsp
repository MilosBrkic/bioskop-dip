<%-- 
    Document   : view
    Created on : 23.07.2020., 15.55.14
    Author     : milos
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<div>
    
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>
    </c:if>

    <div class="container">
       
        <br>

        <div class="form-group">
            <label for="id"><b>ID:</b> ${distributer.id}</label>
        </div>

        <div class="form-group">
            <label for="broj"><b><fmt:message key="naziv"/></b>: ${distributer.naziv}</label>
        </div>

        <div class="form-group">    
            <label for="sedista"><b><fmt:message key="telefon"/></b>: ${distributer.telefon}</label>
        </div>

        <div class="form-group">    
            <label for="sedista"><b>Email</b>: ${distributer.email}</label>
        </div>

        <sec:authorize  access="hasAuthority('ZAPOSLENI')">                 
            <a class="btn btn-danger" href="<c:url value = "/distributer/${distributer.id}/delete/"></c:url>"><fmt:message key="delete" /></a>            
        </sec:authorize>
            
    </div>
</div>
