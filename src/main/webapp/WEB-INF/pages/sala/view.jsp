<%-- 
    Document   : view
    Created on : 29.10.2020., 20.55.14
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
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert mb-2">${error}</div>
    </c:if>

    <div class="container">     
              
        <br>
        <div class="form-group">
            <label for="sala"><b><fmt:message key="sala.brojSale"/>:</b> ${sala.brojSale}</label>
        </div>
        <div class="form-group">
            <label for="sala"><b><fmt:message key="sala.brojSedista"/>:</b> ${sala.brojSedista}</label>
        </div>

        <sec:authorize  access="hasAuthority('ZAPOSLENI')">                         
            <a class="btn btn-danger" href="<c:url value = "/sala/${sala.brojSale}/delete/"></c:url>"><fmt:message key="delete" /></a>
        </sec:authorize>
                                   
        <hr>
                             
        <div class="row justify-content-md-center">
            <div class="col-1">
            <h4 class="d-flex justify-content-center text-right"><fmt:message key="karta.red"/></h4>
            <c:forEach
                items="${sala.redovi}"
                var="red"
                varStatus="loop">
                <div class="text-right" style="margin-bottom: 20px;">
                    ${red.brojReda}
                </div>

            </c:forEach> 
            </div>
            
            <div class="col-11">    
            <h4 class="d-flex justify-content-center"><u><fmt:message key="platno"/></u></h4>
            <c:forEach
                items="${sala.redovi}"
                var="red"
                varStatus="loop">
                    <div class="d-flex justify-content-center">

                        <c:forEach
                        var="i"
                        begin="1"
                        end="${red.brojSedista}"
                        varStatus="loop">
                            <button onClick="return false;"  class="mx-1 mb-3" red="${red.brojReda}" sediste="${i}" izabrano="false" style="width: 40px;"  disabled>
                                ${i}
                            </button>                         
                        </c:forEach>
                    </div>
            </c:forEach>
            </div>
        </div>                 
    </div>                                                  
</div>
