<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
    <div class="page-footer blue text-center py-3 myFooter" >

        <hr>
        
        <button type="button" class="btn btn-light" onclick="window.scrollTo(500, 0)"><fmt:message key="na.vrh"/></button>    
        <h5 id="date" class="pt-2"></h5>
    </div>
    
    <script>
        var now = new Date();
	m = now.getMonth()+1;
        var x = document.getElementById("date"); 
        x.innerHTML = now.getUTCDate()+". "+m+". "+now.getUTCFullYear()+".";      
    </script>
    
    <style>
        .myFooter{
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 2.5rem;
        }           
    </style>