<%-- 
    Document   : login
    Created on : 03.07.2020., 20.55.14
    Author     : milos
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<div>
     
    <div class="container">
        
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>
    </c:if>
        
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert mb-2">${error}
            <c:if test="${not empty resendLink}">
                <a href="<c:url value = "/user/resend-email"/>">${resendLink}</a>
            </c:if>
        </div>
    </c:if>    
        
        <form action="${pageContext.request.contextPath}/user/login" method="post">

            <div class="form-group">
                <label for="username"><fmt:message key="username"/>:</label>
                <div><input type="text" class="form-control" id="username" name="username" required/></div>
            </div>

            <div class="form-group">    
                <label for="password"><fmt:message key="password"/>:</label>
                <div><input type="password" class="form-control" id="password" name="password" required/></div>
            </div>
                
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            
            <p>
            <div><button id="save" class="btn btn-primary"><fmt:message key="login"/></button></div>
            </p>
        </form>
    </div>
</div>
