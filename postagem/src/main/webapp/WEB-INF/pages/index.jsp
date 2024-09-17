<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="jakarta.tags.core"                            prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib  prefix="tpl" tagdir="/WEB-INF/tags"   %>
<tpl:basic titulo="Página de Entrada">


  <div class="card">
      <div class="card-body">
        <img src="<c:url value = "/assets/img/perfis/normal-640x640.jpg"/>" width="640" height="640"  class="img-thumbnail"/>
      </div>
  </div>
  <c:out value="${horas}" />
  
   <br />
    <a href="<c:url value ="/admin" />">Administrador</a><br />
    <a href="<c:url value ="/publico" />">Público</a>
    <div class="card">
        <div class="card-body">
            <p>${user}</p>
            <p>${claims}</p>
            <br />
            <p>Atributos:<sec:authentication property="principal.attributes" /></p>
            <p>Usuário: <sec:authentication property="principal.attributes['preferred_username']" /></p>
            <p>Nome <sec:authentication property="principal.attributes['given_name']" /></p>
            <p>Endereço <sec:authentication property="principal.attributes['address']" /></p>
            
        </div>
    </div>
     <div style="background-color: red;">
         <sec:authorize access="hasRole('AUTENTICADO')">
           <p>This content is only visible to users with the role "autenticado"</p>
        </sec:authorize>
    </div>
    
     <div>
         <a href="<c:url value = "/logout"/>">Logout</a>
        
     </div>
</tpl:basic>