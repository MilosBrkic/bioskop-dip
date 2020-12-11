<%-- 
    Document   : buy
    Created on : 03.07.2020., 20.55.14
    Author     : milos
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<script>
    function disableSeat(red, sediste){
      var seat = $("[red="+red+"][sediste="+sediste+"]");
      
      seat.prop("disabled",true);
      seat.css('background-color', 'grey');
    }
</script>


<div>
    
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert mb-2">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert mb-2">${error}</div>
    </c:if>    

    <div class="container">     
        <form:form action="${pageContext.request.contextPath}/karta/save" method="post" modelAttribute="korpa">    
        <div class="row">
            <div class="col-sm">
                     
                <form:input type="hidden" path="projekcija" value="${korpa.projekcija.id}" readonly="true"/>
                
                <br>
                <div class="form-group">
                    <label for="projekcija"><b>ID:</b> ${korpa.projekcija.id}</label>      
                    <div class="text-danger">
                        <form:errors path="" cssClass="error" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="film"><b><fmt:message key="film.naziv"/>:</b></label>
                    <a href="${pageContext.request.contextPath}/film/${korpa.projekcija.film.id}/view/">${korpa.projekcija.film.naziv}</a>
                </div>

                <div class="form-group">
                    <label for="sala"><b><fmt:message key="sala"/>:</b> ${korpa.projekcija.sala}</label>
                </div>

                <div class="form-group">
                    <label for="sala"><b><fmt:message key="datum"/>:</b> ${korpa.projekcija.getFormatDatum()}</label>
                </div>

                <div class="form-group">
                    <label for="sala"><b><fmt:message key="vreme"/>:</b> ${korpa.projekcija.getFormatVreme()} (${korpa.projekcija.film.trajanje} min)</label>
                </div>
                
                <div class="form-group">
                    <label for="cena"><b><fmt:message key="karta.cena"/>:</b> ${korpa.projekcija.cenaKarte} EUR</label>
                </div>
                <div class="text-danger">
                    <form:errors path="projekcija" cssClass="error" />
                </div>
                                  
            </div>
            <div class="col-sm"> 
                
                <div class="overflow-auto" style="max-height: 0px;">
                    <table id="tabela" class="invisible">                                            
                    </table>
                </div>
                <div class="text-danger">
                    <form:errors path="karte" cssClass="error" />
                </div>
                
                <br>
                <div class="form-group">    
                    <label for="cena"><fmt:message key="broj.karata"/>:</label>
                    <div><input type="text" class="form-control" id="brojKarata" value="0" disabled="true"/></div>
                </div> 
                <div class="form-group">    
                    <label for="cena"><fmt:message key="ukupna.cena"/>:</label>
                    <div><input type="text" class="form-control" id="ukupnaCena" value="0 EUR" disabled="true"/></div>
                </div>    

                <p>
                <div><button id="save" class="btn btn-primary"><fmt:message key="sell"/></button> </div>
                </p>
                <p>
                <a href="<c:url value = "/projekcija"/>"><fmt:message key="projekcija.nazad"/></a>
                </p>
                    
            </div>
        </div>
        </form:form>
        <hr>
        
                     
        <div class="row justify-content-md-center">
            <div class="col-1">
            <h4 class="d-flex justify-content-center text-right"><fmt:message key="karta.red"/></h4>
            <c:forEach
                items="${korpa.projekcija.sala.redovi}"
                var="red"
                varStatus="loop">
                <div class="text-right" style="margin-bottom: 20px;">
                    ${red.brojReda}
                </div>

            </c:forEach> 
            </div>
            
            <div class="col-11">    
            <h4 class="d-flex justify-content-center"><u><fmt:message key="platno"/></u></h4>
            <c:forEach
                items="${korpa.projekcija.sala.redovi}"
                var="red"
                varStatus="loop">
                    <div class="d-flex justify-content-center">

                        <c:forEach
                        var="i"
                        begin="1"
                        end="${red.brojSedista}"
                        varStatus="loop">
                            <button onClick="selectSeat(this); return false;"  class="mx-1 mb-3" red="${red.brojReda}" sediste="${i}" izabrano="false" style="width: 40px;">
                                ${i}
                            </button>                         
                        </c:forEach>

                    </div>

            </c:forEach>
            </div>
        </div>
        <c:forEach
            items="${korpa.projekcija.karte}"
            var="karta"
            varStatus="loop">
                <sec:authentication property="principal.username" var="username"/>
                <c:set var="mine" value="${username eq karta.kupac.username}"/>                               
                <script> disableSeat(${karta.brojReda}, ${karta.brojSedista}, ${mine}) </script>
        </c:forEach>

        <br><br><br><br>             

        <button class="ml-3" disabled>_</button> <fmt:message key="slobodno"/>
        <button class="ml-3" disabled style="background-color: greenyellow">_</button> <fmt:message key="odabrano"/>
        <button class="ml-3" disabled style="background-color: grey">_</button> <fmt:message key="zauzeto"/>
                   
    </div>
                          
                            
</div>
                            
 <script>

  var tabela = $("#tabela");
  var lista = [];
  function selectSeat(but){
      
      var sediste = $(but).attr('sediste');
      var red = $(but).attr('red')

      if($(but).attr('izabrano') == "true"){
          $(but).attr('izabrano',"false");
          $(but).css('background-color', '');
          
          var i;
          for (i = 0; i < lista.length; i++) { 
            if(lista[i].attr('sediste') == sediste && lista[i].attr('red') == red)
                lista.splice(i, 1);
          };
      }
      else
      {
        lista.push($(but));
        $(but).attr('izabrano',"true");
        $(but).css('background-color', 'greenyellow');
    }
    updateTable();
  }
  
  function updateTable(){
        $("table").find("tr:gt(0)").remove();
        var i;
        for (i = 0; i < lista.length; i++) { 
            var sediste = lista[i].attr('sediste');
            var red = lista[i].attr('red');
            $("table").append('<tr><td><input name="karte['+i+'].brojReda" value="'+red+'" readonly/></td>\n\
                                   <td><input name="karte['+i+'].brojSedista" class="form-control" value="'+sediste+'" readonly/></td></tr>');
        }
        $("#brojKarata").val(lista.length);
        $("#ukupnaCena").val(lista.length*${korpa.projekcija.cenaKarte}+" EUR");
  }
  
</script>
                        
