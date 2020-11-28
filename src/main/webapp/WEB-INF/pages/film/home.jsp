<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<div>
    
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert mb-2">${error}</div>
    </c:if>

    <h4 class="text-center"><fmt:message key="film.home"/></h4>

    <form:form class="ml-2 mb-2 form-inline" action="${pageContext.request.contextPath}/film/search" method="get" modelAttribute="film">
        <input class="form-control" type="text" name="naziv" placeholder="<fmt:message key="film.naziv"/>">
        <p></p>
        <button id="save"class="ml-2 btn btn-primary"><fmt:message key="pretrazi" /></button>
    </form:form>

    <div class="container-fluid">
        <table class="table table-sm">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="film.naziv"/></th>
                    <th scope="col"><fmt:message key="trajanje"/></th>
                    <th scope="col"><fmt:message key="godina"/></th>
                    <th scope="col"><fmt:message key="ocena"/></th>
                    <th scope="col"><fmt:message key="distributer"/></th>
                    <th scope="col"><fmt:message key="aktivan"/></th>
                    <th scope="col"><fmt:message key="akcija"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach
                    items="${filmovi}"
                    var="film"
                    varStatus="loop">
                    <tr>
                        <td>${loop.index+1}</td>
                        <td>${film.id}</td>
                        <td><a href="<c:url value = "/film/${film.id}/view/"></c:url>">${film.getNaziv()}</a></td>
                        <td>${film.trajanje}</td>
                        <td>${film.godina}</td>
                        <td>${film.ocena}</td>
                        <td>${film.distributer.naziv}</td>
                        <td>
                            <c:if test="${film.aktivan}">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" id="defaultChecked2" checked disabled>
                                    <label class="custom-control-label" for="defaultChecked2"></label>
                                </div>   
                             </c:if>
                        </td>    
                        <td>
                            <ul class="navbar-nav mr-auto d-flex justify-content-end">
                                <div class="dropdown">
                                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <fmt:message key="akcija"/>
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <a class="dropdown-item" href="<c:url value = "/film/${film.id}/view/"></c:url>"><fmt:message key="view"/></a>
                                        <sec:authorize  access="hasAuthority('ZAPOSLENI')">  
                                            <a class="dropdown-item" href="<c:url value = "/film/${film.id}/delete/"></c:url>"><fmt:message key="delete"/></a>
                                            <a class="dropdown-item" href="<c:url value = "/film/${film.id}/edit/"></c:url>"><fmt:message key="edit"/></a>
                                        </sec:authorize>  
                                    </div>
                                </div>
                            </ul>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>