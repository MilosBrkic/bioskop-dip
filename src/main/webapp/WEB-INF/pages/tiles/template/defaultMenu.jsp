<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top mb-2">

    <a class="navbar-brand" href="<c:url value = "/home"/>"><fmt:message key="bioskop"/></a>
    
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse" id="navbarSupportedContent">  
    <ul class="navbar-nav mr-auto d-flex justify-content-end">
     
        
        
      <sec:authorize  access="hasAuthority('ZAPOSLENI')">  
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle text-light" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <fmt:message key="sala"/>
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="<c:url value = "/sala"/>"><fmt:message key="sala.home"/></a>
          <a class="dropdown-item" href="<c:url value = "/sala/add"/>"><fmt:message key="sala.add"/></a>
        </div>
      </li>
      
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle text-light" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <fmt:message key="distributer"/>
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="<c:url value = "/distributer"/>"><fmt:message key="distributer.home"/></a>
          <a class="dropdown-item" href="<c:url value = "/distributer/add"/>"><fmt:message key="distributer.add"/></a>
        </div>
      </li>
      </sec:authorize>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle text-light" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <fmt:message key="filmovi"/>
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="<c:url value = "/film"/>"><fmt:message key="film.home"/></a>
          <sec:authorize  access="hasAuthority('ZAPOSLENI')">  
              <a class="dropdown-item" href="<c:url value = "/film/add"/>"><fmt:message key="film.add"/></a>
              </sec:authorize>
        </div>
      </li>
      
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle text-light" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <fmt:message key="projekcije"/>
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="<c:url value = "/projekcija"/>"><fmt:message key="projekcija.home"/></a>
          <sec:authorize  access="hasAuthority('ZAPOSLENI')">  
          <a class="dropdown-item" href="<c:url value = "/projekcija/add"/>"><fmt:message key="projekcija.add"/></a>
          </sec:authorize>
        </div>
      </li>
      
      <sec:authorize  access="hasAuthority('ADMIN')">
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle text-light" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <fmt:message key="admin"/>
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="<c:url value = "/user/registerZaposleni"/>"><fmt:message key="register.zaposleni"/></a>
        </div>
      </li>
      </sec:authorize>
            
    </ul>
    <ul class="navbar-nav ml-auto d-flex justify-content-end">
        
        <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle text-light" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <fmt:message key="jezik"/>
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="/Bioskop/home?lang=sr"><fmt:message key="jezik.srpski"/></a>
          <a class="dropdown-item" href="/Bioskop/home?lang=en"><fmt:message key="jezik.engleski"/></a>
        </div>
      </li>
        
      <sec:authorize  access="hasAuthority('USER')">
          <li class="nav-item">
              <a class="btn btn-secondary" href="<c:url value = "/logout"/>"><fmt:message key="logout"/> <u><sec:authentication property="principal.username" /></u></a>
          <li>
          </sec:authorize>
              
              
        <sec:authorize  access="!hasAuthority('USER')">
            <li class="nav-item">
                <a class="btn btn-dark" href="<c:url value = "/user/login"/>"><fmt:message key="login"/></a>
            <li>
            <li class="nav-item">
                <a class="btn btn-dark" href="<c:url value = "/user/register"/>"><fmt:message key="register"/></a>
            <li>
        </sec:authorize>
    </ul>
    </div>      
</nav>
