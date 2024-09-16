<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@taglib  prefix="tpl" tagdir="/WEB-INF/tags"   %>
<tpl:basic titulo="Página de Entrada">

  <h2>Julgamento</h2>
   <span class="card">Página Pública de Acesso Irrestrito ${horas}.</span>
  <br />
  <div class="card">
      <div class="card-body">
       <img src="<c:url value = "/assets/img/perfis/publico-640x640.jpg"/>"  alt="640" height="640" alt="Reunião de bonecos estilizados" />
      </div>
   </div>
  <c:out value="${horas}" />
  
</tpl:basic>