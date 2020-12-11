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
        
        <c:if test="${distributer.filmovi.isEmpty()}">
            <sec:authorize  access="hasAuthority('ZAPOSLENI')">                 
                <a class="btn btn-danger mb-2" href="<c:url value = "/distributer/${distributer.id}/delete/"></c:url>"><fmt:message key="delete" /></a>            
            </sec:authorize>
        </c:if>
                
        <c:if test="${!distributer.filmovi.isEmpty()}">
            <table class="table table-sm table-hover">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><fmt:message key="film.naziv"/></th>
                        <th scope="col"><fmt:message key="godina"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach
                        items="${distributer.filmovi}"
                        var="film"
                        varStatus="loop">
                        <tr>
                            <td>${loop.index+1}</td>
                            <td><a href="<c:url value = "/film/${film.id}/view/"></c:url>">${film.getNaziv()}</a></td>
                            <td>${film.godina}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>         
    </div>
</div>
