<%-- 
    Document   : add
    Created on : 03.07.2020., 20.55.14
    Author     : milos
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<div>
    
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>        
    </c:if>

    <div class="container">
        <form:form action="${pageContext.request.contextPath}/sala/save" method="post" modelAttribute="sala">

            <div class="form-group">
                <label for="broj"><fmt:message key="sala.brojSale"/>:</label>
                <div><form:input type="number" class="form-control" id="broj" path="brojSale" required="true"/></div>
                <div class="text-danger">
                    <form:errors path="brojSale" cssClass="error" />
                </div>
            </div>
                            
            <button onClick="insertRow(); return false;" class="btn btn-secondary"><fmt:message key="dodaj.red"/></button>
            <button onClick="removeRow(); return false;" class="btn btn-secondary"><fmt:message key="obrisi.red"/></button>
            <fmt:message key="prvi.red"/>
            <table id="tabela" class="">
                <tr>
                    <th><fmt:message key="karta.red"/></th>
                    <th><fmt:message key="sala.brojSedista"/></th>
                </tr>            
                <tr>
                    <td><input name="redovi[0].brojReda" class="form-control" value="1" readonly/></td>
                    <td><input name="redovi[0].brojSedista" class="form-control" value="5"/></td>
                </tr>                
            </table>	   
            <div class="text-danger">
                <form:errors path="redovi" cssClass="error" />
                <form:errors path="brojSedista" cssClass="error" />
            </div>
            
            <br>
            <div><button id="save" class="btn btn-primary"><fmt:message key="save"/></button></div>
            
        </form:form>
    </div>

    <script>
        function insertRow(){
            var rowCount = $('#tabela >tbody >tr').length;
            $("table").append('<tr><td><input name="redovi['+(rowCount-1)+'].brojReda" class="form-control" value="'+rowCount+'" readonly/></td><td><input name="redovi['+(rowCount-1)+'].brojSedista" class="form-control" value="0"/></td></tr>');
        }
        function removeRow(){
            var rowCount = $('#tabela >tbody >tr').length;
            if(rowCount > 2)
                $('table tr:last').remove();
        }
    </script>
</div>
