<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib  prefix="tpl" tagdir="/WEB-INF/tags"   %>
<tpl:basic titulo="Página de Entrada">

  <h2>Julgamento 999999999999999999999</h2>
   <span class="card">A Nova Onda dddddddo Rei às ddddddd ${horas}.</span>
  <br />
  <div class="card">
    <img src="<c:url value = "/assets/img/perfis/normal-640x640.jpg"/>" />
  </div>
  <c:out value="${horas}" />
  
   <br />
    
  
</tpl:basic>