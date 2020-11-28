<%-- 
    Document   : add
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
        <form:form action="${pageContext.request.contextPath}/film/save" method="post" modelAttribute="film">

            <div class="form-group">
                <label for="naziv"><fmt:message key="film.naziv"/>:</label>
                <div><form:input type="text" class="form-control" id="naziv" path="naziv" maxlength="100"/></div>
                <div class="text-danger">
                    <form:errors path="naziv" cssClass="error" />
                </div>
            </div>

            <div class="form-group">    
                <label for="trajanje"><fmt:message key="trajanje.minuti" />:</label>
                <div><form:input type="number" class="form-control" id="trajanje" path="trajanje" min="0" max="9999"/></div>
                <div class="text-danger">
                    <form:errors path="trajanje" cssClass="error" />
                </div>
            </div>
                
            <div class="form-group">    
                <label for="godina"><fmt:message key="godina" />:</label>
                <div><form:input type="number" class="form-control" id="godina" path="godina" min="0" max="999999" value="2000"/></div>
                <div class="text-danger">
                    <form:errors path="godina" cssClass="error" />
                </div>
            </div>

            <div class="form-group">    
                <label for="url"><fmt:message key="film.url" />:</label>
                <div><form:input type="text" class="form-control" id="url" path="url" maxlength="200"/></div>
                <div class="text-danger">
                    <form:errors path="url" cssClass="error" />
                </div>
            </div>
                
            <div class="form-group">    
                <label for="ocena"><fmt:message key="ocena" />:</label>
                <div><form:input type="number" class="form-control" id="ocena" path="ocena" min="1" step=".1" max="10" value="5.0"/></div>
                <div class="text-danger">
                    <form:errors path="ocena" cssClass="error" />
                </div>
            </div>

            <div class="form-group">    
                <label for="sedista"><fmt:message key="film.opis" />:</label>
                <div><form:textarea type="text" class="form-control" id="sedista" path="opis" /></div>
                <div class="text-danger">
                    <form:errors path="opis" cssClass="error" />
                </div>
            </div>
                
            <div class="form-group">  
                <label for="distributer"><fmt:message key="distributer" />:</label>
                <form:select name="distributer" path="distributer" class="form-control">              
                    <form:options items = "${distributeri}" itemLabel="naziv" itemValue="id" />
                </form:select>   
            </div>
                
            <div class="form-group">  
                <label for="reziser"><fmt:message key="reziser" />:</label>
                <form:select name="reziser" path="reziser" class="form-control">              
                    <form:options items = "${osobe}" itemLabel="imePrezime" itemValue="id" />
                </form:select>   
                <div class="text-danger">
                    <form:errors path="reziser" cssClass="error" />
                </div>
            </div>
             
                
            <!-- GLUMCI -->  
            <div class="row">
                <div class="col-5">
                    <div class="form-group">
                        <label for="glumciSvi"><fmt:message key="glumci" />:</label>
                        <form:select multiple="false" path="" name="glumciSvi" class="custom-select" id="glu1" size="8">
                            <form:options items="${osobe}" itemValue="id" itemLabel="imePrezime"/>
                        </form:select>
                    </div>
                </div>
                <div class="col-2 align-self-center container">
                    <div class="row mb-3">
                        <button onClick="insertGlumac(); return false;" class="btn btn-secondary btn-lg btn-block"><fmt:message key="insert"/></button>
                    </div>

                    <div class="row">
                        <button onClick="removeGlumac(); return false;" class="btn btn-secondary btn-lg btn-block"><fmt:message key="remove"/></button>
                    </div>
                </div>
                <div class="col-5">
                    <div class="form-group">     
                        <label for="glumciIzabrani"><fmt:message key="glumci.izabrani" />:</label>
                        <form:select multiple="true" path="glumci" name="glumciIzabrani" class="custom-select" id="glu2" size="8">
                            <options items="" itemValue="" itemLabel=""/>
                        </form:select>
                    </div>
                </div>
            </div>    
                
                                                            
             <!-- ZANROVI -->   
            <div class="row">
                <div class="col-5">
                    <div class="form-group">
                        <label for="zanroviSvi"><fmt:message key="zanrovi" />:</label>
                        <form:select multiple="false" path="" name="zanroviSvi" class="custom-select" id="zan1" size="8">
                            <form:options items="${zanroviSvi}" itemValue="id" itemLabel="naziv"/>
                        </form:select>
                    </div>
                </div>
                <div class="col-2 align-self-center container">
                    <div class="row mb-3">
                        <button onClick="insertZanr(); return false;" class="btn btn-secondary btn-lg btn-block"><fmt:message key="insert"/></button>
                    </div>

                    <div class="row">
                        <button onClick="removeZanr(); return false;" class="btn btn-secondary btn-lg btn-block"><fmt:message key="remove"/></button>
                    </div>
                </div>
                <div class="col-5">
                    <div class="form-group">     
                        <label for="zanroviIzabrani"><fmt:message key="zanrovi.izabrani" />:</label>
                        <form:select multiple="true" path="zanrovi" name="zanroviIzabrani" class="custom-select" id="zan2" size="8">
                            <options items="" itemValue="" itemLabel=""/>
                        </form:select>
                    </div>
                </div>
            </div>                                            
            <div class="text-danger">
                <form:errors path="zanrovi" cssClass="error" />
            </div>    
                     
            
            <div class="custom-control custom-checkbox">
                <form:checkbox path="aktivan" class="custom-control-input" id="aktivan" checked="true" />
                <label class="custom-control-label" for="aktivan"><fmt:message key="aktivan" /></label>              
            </div>    
            <div class="text-danger">
                <form:errors path="aktivan" cssClass="error" />
            </div>
                
            <p>
            <div><button id="save" onClick="selectAll()" class="btn btn-primary"><fmt:message key="save" /></button></div>
            </p>
                     
        </form:form>
                       
        <script>
            function insertZanr(){
                var text = $( "#zan1 option:selected" ).text();
                var value = $( "#zan1 option:selected" ).val();
                var exist = $("#zan2 option[value="+value+"]").length > 0;
                if(!exist)
                    $("#zan2").append(new Option(text, value));
            }
            function removeZanr(){
                $( "#zan2 option:selected" ).remove();
            }

            function insertGlumac(){
                var text = $( "#glu1 option:selected" ).text();
                var value = $( "#glu1 option:selected" ).val();
                var exist = $("#glu2 option[value="+value+"]").length > 0;
                if(!exist)
                    $("#glu2").append(new Option(text, value));
            }
            function removeGlumac(){
                $( "#glu2 option:selected" ).remove();
            }


            function selectAll(){
                $('#zan2 option').prop('selected', true);
                $('#glu2 option').prop('selected', true);
            }
        </script>
    </div>
</div>
