<%-- 
    Document   : view
    Created on : 14.07.2020., 20.55.14
    Author     : milos
--%>

<%@page import="java.util.Date"%>
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
        <div class="row">
            <div class="col-sm">
                <form:form action="${pageContext.request.contextPath}/projekcija/${projekcija.id}/edit" method="get" modelAttribute="projekcija">

                    <div class="form-group">
                        <label for="projekcija"><b>ID:</b> ${projekcija.id}</label>                       
                    </div>

                    <div class="form-group">
                        <label for="film"><b><fmt:message key="film.naziv"/>:</b></label>
                        <a href="${pageContext.request.contextPath}/film/${projekcija.film.id}/view/">${projekcija.film.naziv}</a>
                    </div>

                    <div class="form-group">
                        <label for="sala"><b><fmt:message key="sala"/>:</b></label>
                        <a href="${pageContext.request.contextPath}/sala/${projekcija.sala.brojSale}/view/">${projekcija.sala}</a>                      
                    </div>

                    <div class="form-group">
                        <label for="sala"><b><fmt:message key="datum"/>:</b> ${projekcija.getFormatDatum()}</label>
                    </div>
                    
                    <div class="form-group">
                        <label for="sala"><b><fmt:message key="vreme"/>:</b> ${projekcija.vreme}</label>
                    </div>
                    
                    <div class="form-group">
                        <label for="cena"><b><fmt:message key="karta.cena"/>:</b> ${projekcija.cenaKarte} EUR</label>
                    </div>
                  
                    <p>
                    <sec:authorize  access="hasAuthority('ZAPOSLENI')">                           
                        <button id="save" class="btn btn-primary"><fmt:message key="edit"/></button>
                        <a id="save" href="<c:url value = "/projekcija/${projekcija.id}/delete/"></c:url>" class="btn btn-primary"><fmt:message key="delete"/></a>
                    </sec:authorize>
                                                                    
                    <c:if test="${!projekcija.isInPast()}">
                        <sec:authorize  access="hasAuthority('KUPAC')">
                            <a href="<c:url value = "/karta/${projekcija.id}/buy"/>" class="btn btn-primary"><fmt:message key="karta.kupi"/></a>
                        </sec:authorize>   
                        <sec:authorize  access="hasAuthority('ZAPOSLENI')">
                            <a href="<c:url value = "/karta/${projekcija.id}/sell"/>" class="btn btn-primary"><fmt:message key="karta.sell"/></a>
                        </sec:authorize>   
                    </c:if>                                                         
                    </p>
                    
                    <p>
                    <a href="<c:url value = "/projekcija"/>"><fmt:message key="projekcija.nazad"/></a>
                    </p>

                </form:form>
            </div>
            <div class="col-sm">  
                <br>
                <h5><fmt:message key="karta.prodate"/>:</h5>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"><fmt:message key="karta.red"/></th>
                            <th scope="col"><fmt:message key="karta.sediste"/></th>
                            <th scope="col"><fmt:message key="karta.cena"/></th>
                            <th scope="col">Status</th>
                            <sec:authorize  access="hasAuthority('ZAPOSLENI')">  
                            <th scope="col">Code</th>
                            </sec:authorize>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach
                            items="${projekcija.karte}"
                            var="karta"
                            varStatus="loop">
                            <tr>
                                <td>${loop.index+1}</td>
                                <td>${karta.brojReda}</td>
                                <td>${karta.brojSedista}</td>
                                <td>${karta.cena}</td>
                                <td>${karta.status}</td>
                                <sec:authorize  access="hasAuthority('ZAPOSLENI')"> 
                                <td>${karta.code}</td>
                                </sec:authorize>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
