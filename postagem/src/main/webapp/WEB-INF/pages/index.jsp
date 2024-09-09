<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib  prefix="tpl" tagdir="/WEB-INF/tags"   %>
<tpl:basic titulo="Página de Entrada">

  <h2>Julgamento 777777777777777777777++++++++++++++</h2>
   <span class="card">A Nova Onda dddddddo Rei às ddddddd ${horas}.</span>
  <br />
  <div class="card">
    <img src="<c:url value = "/assets/img/perfis/normal-640x640.jpg"/>" />
  </div>
  <c:out value="${horas}" />
  
   <br />
    <div style="background-color: red;">
         <sec:authorize access="hasRole('AUTENTICADO')">
           <p>This content is only visible to users with the role "autenticado"</p>
           <sec:authentication property="principal.authorities" />
        </sec:authorize>
    </div>

    <div>

        <hr />
        <div style="background-color: green;">
        <sec:authentication property="principal.attributes" />
        </div>
        <div>
          <sec:authentication property="principal.attributes['preferred_username']" />
        </div>
        <div>
            <sec:authentication property="principal.attributes['given_name']" />
        </div>
        <div>
            <sec:authentication property="principal.attributes['name']" />
        </div>
        <div>
            <sec:authentication property="principal.attributes['family_name']" />
        </div>
        <div>
            <sec:authentication property="principal.attributes['address']" />
        </div>
        <div>
            <sec:authentication property="principal.attributes['auth_time']" />
        </div>






    </div>

</tpl:basic>