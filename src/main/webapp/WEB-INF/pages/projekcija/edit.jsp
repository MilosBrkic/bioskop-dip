<%-- 
    Document   : edit
    Created on : 03.07.2020., 20.55.14
    Author     : milos
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<div>
    
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>
    </c:if>

    <div class="container">
        <form:form action="${pageContext.request.contextPath}/projekcija/${projekcija.id}/save" method="post" modelAttribute="projekcija">

            <div class="form-group">
                <label for="id">ID:</label>
                <div><form:input type="text" class="form-control" id="id" path="id" value="${projekcija.id}" readonly="true"/></div>
            </div>
                                       
            <div class="form-group">  
                <label for="film"><fmt:message key="film"/>:</label>
                <form:select name="film" path="film" class="form-control">              
                    <form:options items = "${filmovi}" itemValue="id" />
                </form:select>   
            </div>
            <div class="text-danger">
                <form:errors path="film" cssClass="error" />
            </div> 
                      
            <div class="form-group">  
                <label for="sala"><fmt:message key="sala"/>:</label>
                <form:select name="sala" path="sala" class="form-control">              
                    <form:options items = "${sale}"  itemValue="brojSale" />
                </form:select>   
            </div>
            <div class="text-danger">
                <form:errors path="sala" cssClass="error" />
            </div>    
                
             <div class="form-group">
                <label for="datum"><fmt:message key="datum"/>:</label>
                <div><form:input type="date" class="form-control" id="datum" path="datum" value="${projekcija.datum}"/></div>
                <div class="text-danger">
                    <form:errors path="datum" cssClass="error" />
                </div>
            </div>

            <div class="form-group">    
                <label for="vreme"><fmt:message key="vreme"/>:</label>
                <div><form:input type="time" class="form-control" id="vreme" path="vreme" value="${projekcija.vreme}"/></div>
                <div class="text-danger">
                    <form:errors path="vreme" cssClass="error" />
                </div>
            </div>
                
           <div class="form-group">    
                <label for="cena"><fmt:message key="karta.cena"/>(EUR):</label>
                <div><form:input type="number" class="form-control" id="cena" path="cenaKarte" value="${projekcija.cenaKarte}" step="any" required="true" max="9999999"/></div>
                <div class="text-danger">
                    <form:errors path="cenaKarte" cssClass="error" />
                </div>
            </div>
                                                                
            <p>
            <div><button id="save" onClick="selectAll()" class="btn btn-primary"><fmt:message key="save"/></button> </div>
            </p>
        </form:form>                       
    </div>
</div>
