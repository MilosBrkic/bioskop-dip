
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<div class="container">
    <h3><fmt:message key="najnovije"/>:</h3>
        <div class="card-columns">       
            <c:forEach
                items="${filmovi}"
                var="film"
                end="5"
                varStatus="loop">

                <div class="card" style="width: 18rem;">
                    <img class="card-img-top" src="<c:url value = "/film/getImage/${film.id}"/>" alt="Slika nedostaje">
                    <div class="card-body">
                        <h5 class="card-title"><a href="<c:url value = "/film/${film.id}/view/"></c:url>">${film.naziv}</a></h5>
                    </div>
                </div>

            </c:forEach>
        </div>
        <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>
    </c:if>
</div>