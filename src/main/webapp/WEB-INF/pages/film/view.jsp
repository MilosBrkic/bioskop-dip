<%-- 
    Document   : view
    Created on : 03.07.2020., 20.55.14
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
        <form:form action="${pageContext.request.contextPath}/film/${film.id}/edit" method="get" modelAttribute="film">

            <br>
            <div class="row">
                <div class="col-7">
                    
                    <div class="form-group">
                        <label for="id"><b>ID:</b> ${film.id}</label>
                    </div>

                    <div class="form-group">
                        <label for="naziv"><b><fmt:message key="film.naziv"/></b>:</label>
                        <h2 class="overflow-hidden">${film.naziv}</h2>
                    </div>

                    <div class="form-group">    
                        <label for="trajanje"><b><fmt:message key="trajanje"/></b>: ${film.trajanje} min</label>
                    </div>
                    
                    <div class="form-group">    
                        <label for="godina"><b><fmt:message key="godina"/></b>: ${film.godina}</label>
                    </div>
                    
                    <div class="form-group">    
                        <label for="ocena"><b><fmt:message key="ocena"/></b>: ${film.ocena}</label>
                    </div>
                    
                    <div class="form-group">    
                        <label for="opis"><b><fmt:message key="film.opis"/></b>:</label>
                        <p id="opis">${film.opis}</p>
                    </div>
                    
                    <div class="form-group">    
                        <label for="reziser"><b><fmt:message key="reziser"/></b>: ${film.reziser.imePrezime}</label>
                    </div>
                    
                    <div class="form-group">    
                        <label for="distributer"><b><fmt:message key="distributer"/></b>:</label>
                        <a href="${pageContext.request.contextPath}/distributer/${film.distributer.id}/view/">${film.distributer.naziv}</a>
                    </div>

                    <div class="form-group">
                        <label for="zanroviSvi"><b><fmt:message key="zanrovi"/>:</b></label>
                        <c:forEach items="${film.zanrovi}"
                            var="zanr"
                            varStatus="loop">
                            ${zanr.naziv}<c:if test="${loop.index + 1 < film.zanrovi.size()}">,</c:if>                
                        </c:forEach>
                    </div>      
                        
                    <div class="form-group">  
                        <label for="zanroviSvi"><b><fmt:message key="glumci"/>:</b></label>
                        <c:forEach items="${film.glumci}"
                            var="glumac"
                            varStatus="loop">
                            ${glumac.imePrezime}<c:if test="${loop.index + 1 < film.glumci.size()}">,</c:if>                
                        </c:forEach>    
                     </div>  
                    
                        
                                                                                  
                    <c:if test="${!projekcije.isEmpty()}">
                        <label for="projekcije"><b><fmt:message key="projekcije"/>:</b></label>    
                        <table class="table table-sm table-hover">
                            <thead>
                                <tr>
                                    <th scope="col"><fmt:message key="datum"/></th>
                                    <th scope="col"><fmt:message key="vreme"/></th>
                                    <th scope="col"> </th>
                                </tr>
                            </thead>
                            <tbody>                      
                            <c:forEach items="${projekcije}"
                                var="pro"
                                varStatus="loop">
                                <tr>
                                    <td>${pro.getFormatDatum()}</td>
                                    <td>${pro.getFormatVreme()}</td>                              
                                    <td><a href="${pageContext.request.contextPath}/projekcija/${pro.id}/view/"><fmt:message key="view"/></a></td>
                                </tr>     
                            </c:forEach> 
                          </tbody>
                        </table>    
                    </c:if>            
                       
                        

                    <div class="custom-control custom-checkbox">
                        <form:checkbox path="aktivan" class="custom-control-input" id="aktivan" value="${film.aktivan}" disabled="true"/>
                        <label class="custom-control-label" for="aktivan"><fmt:message key="aktivan"/></label>
                    </div>  
                </div>

                <div class="col-5">
                    <img src="<c:url value = "/film/getImage/${film.id}"/>" class="img-fluid rounded" alt="<fmt:message key="slika.null"/>"> 
                </div>
            </div>
            
             
            
            <sec:authorize  access="hasAuthority('ZAPOSLENI')">    
                <p>
                <div>
                    <button id="save" class="btn btn-primary"><fmt:message key="edit"/></button>
                    <a id="save" href="<c:url value = "/film/${film.id}/delete/"></c:url>" class="btn btn-primary"><fmt:message key="delete"/></a>
                </div>
                </p>
            </sec:authorize>

        </form:form>
                      
    </div>

</div>
