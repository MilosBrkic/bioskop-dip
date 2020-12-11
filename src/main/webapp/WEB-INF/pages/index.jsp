
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<div class="container">
    <h3><fmt:message key="najnovije"/>:</h3>
         
            <c:forEach
                items="${filmovi}"
                var="film"
                end="5"
                varStatus="loop">

                <c:if test="${loop.index %3 == 0}">
                    <div class="card-deck mb-2 p-2">      
                </c:if>
                <div class="card">
                    <img class="card-img-top" style="height: 85%" src="<c:url value = "/film/getImage/${film.id}"/>" alt="Slika nedostaje">
                    <div class="card-body">
                        <h5 class="card-title" style="font-size: 1.5vw;"><a href="<c:url value = "/film/${film.id}/view/"></c:url>">${film.naziv}</a></h5>
                    </div>
                </div>
                <c:if test="${(loop.index - 2) %3 == 0 && loop.index > 0}">
                    </div>      
                </c:if>    

            </c:forEach>
        
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>
    </c:if>
        
</div>