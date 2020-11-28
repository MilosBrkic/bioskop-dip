<%-- 
    Document   : login
    Created on : 03.07.2020., 20.55.14
    Author     : milos
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt"  prefix="fmt" %>

<div>
    
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>
    </c:if>
        
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert mb-2">${error}</div>
    </c:if>

    <div class="container">
              
        <form action="${pageContext.request.contextPath}/user/resend-email" method="post">

            <p><fmt:message key="account.resend"/></p>
            
            <div class="form-group">
                <label for="email">Email:</label>
                <div><input type="email" class="form-control" id="email" name="email" required/></div>
            </div>
           
            <p>
            <div><button id="save" class="btn btn-primary"><fmt:message key="send"/></button></div>
            </p>
        </form>
    </div>
</div>
