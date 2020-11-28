<%-- 
    Document   : register
    Created on : 30.07.2020., 16.55.14
    Author     : milos
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<div>
       
    <div class="container">
         
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>
    </c:if>
        
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert mb-2">${error}</div>
    </c:if>
        
        <form:form action="${pageContext.request.contextPath}/user/register" method="post" modelAttribute="korisnik">

            <p><fmt:message key="potrebna.registracija"/></p>
            
            
            <div class="form-group">
                <label for="username"><fmt:message key="username"/>:</label>
                <div><form:input type="text" class="form-control" id="username" name="username" path="username" required="true" maxlength="40"/></div>
                <div class="text-danger">
                    <form:errors path="username" cssClass="error" />
                </div>
            </div>

            <div class="form-group">    
                <label for="password"><fmt:message key="password"/>:</label>
                <div><form:input type="password" class="form-control" id="password" name="password" path="password" required="true" maxlength="100"/></div>
                <div class="text-danger">
                    <form:errors path="password" cssClass="error" />
                </div>
            </div>
            
            <div class="form-group">    
                <label for="passwordMatch"><fmt:message key="password.confirm"/>:</label>
                <div><form:input type="password" class="form-control" id="passwordMatch" name="passwordMatch" path="passwordMatch" required="true" maxlength="100"/></div>
                <div class="text-danger">
                    <form:errors path="passwordMatch" cssClass="error" />
                </div>
            </div>
                              
            <div class="form-group">
                <label for="imePrezime"><fmt:message key="ime.prezime"/>:</label>
                <div><form:input type="text" class="form-control" id="imePrezime" name="imePrezime" path="imePrezime" required="true" maxlength="40"/></div>
                <div class="text-danger">
                    <form:errors path="imePrezime" cssClass="error" />
                </div>
            </div>
                
            <div class="form-group">    
                <label for="email">Email:</label>
                <div><form:input type="email" class="form-control" id="email" name="email" path="email" required="true" maxlength="100" value=""/></div>
                <div class="text-danger">
                    <form:errors path="email" cssClass="error" />
                </div>
            </div>
                
            <div class="form-group">
                <label for="telefon"><fmt:message key="telefon"/>:</label>
                <div><form:input type="tel" class="form-control" id="telefon" name="telefon" path="brojTelefona" required="true" maxlength="20"/></div>
                <div class="text-danger">
                    <form:errors path="brojTelefona" cssClass="error" />
                </div>
            </div>
                
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            
            <p>
            <div><button id="save" class="btn btn-primary"><fmt:message key="register"/></button></div>
            </p>
        </form:form>
    </div>
</div>
