<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="titulo" required="true" description="Título da página"  %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ attribute name="extraStyle" fragment="true"  %>
<%@ attribute name="extraScript" fragment="true"  %>
<!doctype html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" /> 
    <title>${titulo}</title>
    <link rel="apple-touch-icon"      sizes="57x57"   href="<c:url value="/assets/img/icons/apple-icon-57x57.png" />" />
    <link rel="apple-touch-icon"      sizes="60x60"   href="<c:url value="/assets/img/icons/apple-icon-60x60.png" />" />
    <link rel="apple-touch-icon"      sizes="72x72"   href="<c:url value="/assets/img/icons/apple-icon-72x72.png" />" />
    <link rel="apple-touch-icon"      sizes="76x76"   href="<c:url value="/assets/img/icons/apple-icon-76x76.png" />" />
    <link rel="apple-touch-icon"      sizes="114x114" href="<c:url value="/assets/img/icons/apple-icon-114x114.png" />" />
    <link rel="apple-touch-icon"      sizes="120x120" href="<c:url value="/assets/img/icons/apple-icon-120x120.png" />" />
    <link rel="apple-touch-icon"      sizes="144x144" href="<c:url value="/assets/img/icons/apple-icon-144x144.png" />" />
    <link rel="apple-touch-icon"      sizes="152x152" href="<c:url value="/assets/img/icons/apple-icon-152x152.png" />" />
    <link rel="apple-touch-icon"      sizes="180x180" href="<c:url value="/assets/img/icons/apple-icon-180x180.png" />" />
    <link rel="icon" type="image/png" sizes="192x192" href="<c:url value="/assets/img/icons/android-icon-192x192.png" />" />
    <link rel="icon" type="image/png" sizes="32x32"   href="<c:url value="/assets/img/icons/favicon-32x32.png" />" />
    <link rel="icon" type="image/png" sizes="96x96"   href="<c:url value="/assets/img/icons/favicon-96x96.png" />" />
    <link rel="icon" type="image/png" sizes="16x16"   href="<c:url value="/assets/img/icons/favicon-16x16.png" />" />
    <link rel="manifest" href="<c:url value="/assets/manifest.json" />" />
    <meta name="msapplication-TileColor" content="#ffffff" />
    <meta name="msapplication-TileImage" content="<c:url value="/assets/img/icons/ms-icon-144x144.png" />" />
    <meta name="theme-color" content="#ffffff" />

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
    <jsp:invoke fragment="extraStyle" />  
  </head>
  <body>
     <main class="container">
    <jsp:doBody />
    </main>
    <jsp:invoke fragment="extraScript" />  
  </body>
</html>