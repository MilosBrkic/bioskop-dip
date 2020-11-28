<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring' %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <c:set var="title"><tiles:getAsString name="title"/></c:set>     
        <title>                  
            <spring:message code ="${title}"/>
        </title>
        
        <link rel='stylesheet' href='${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css'>
        <!--<link rel='stylesheet' href='${pageContext.request.contextPath}/webjars/bootstrap-table/1.16.0/dist/bootstrap-table.min.css'>x-->
        <link rel='stylesheet' href='${pageContext.request.contextPath}/webjars/font-awesome/5.6.3/css/all.min.css'>
        <!--<link rel="stylesheet" href='${pageContext.request.contextPath}/resources/css/styles.css' />x-->

        <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/popper.js/1.16.0/umd/popper.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.1.1/jquery.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
        <!--<script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap-table/1.16.0/dist/bootstrap-table.min.js"></script>x-->
        
    </head>
    <body>
        <header id ="header">
            <tiles:insertAttribute name="header"/>
        </header>

        <section id="sidemenu">
            <tiles:insertAttribute name="menu"/>
        </section>

        <section id="site-content">
            <tiles:insertAttribute name="body"/>
        </section>

        <footer id ="footer">
            <tiles:insertAttribute name="footer"/>
        </footer>

        
    </body>
</html>

<style>
    body{
        position: relative;
        min-height: 90vh;
    }
    #site-content{
        padding-bottom: 2.5rem;
    }
</style>