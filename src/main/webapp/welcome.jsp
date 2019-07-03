<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <title>Exchange</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark static-top">
  <div class="container">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span></button>

    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
        <h2 style="color:grey">Welcome ${pageContext.request.userPrincipal.name}</h2>
        <form action="/logout" method="post">
        <input type="submit" id="logout" value="Logout" class="logout" name="logout" style="float: right;">
        </form>
        </li>
      </ul>
    </div>
  </div>
</nav>

<!-- Page Content -->
<div class="container">
<style>

table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}

table {
float:left;
margin-left:10px;
margin-top:10px;
background-color: #f1f1c1;
}

th, td {
  padding: 15px;
  text-align: left;
}

h3 {
  display: block;
  font-size: 1.17em;
  margin-top: 1em;
  margin-bottom: 1em;
  margin-left: 0;
  margin-right: 0;
  font-weight: bold;
}

</style>
<body>
<iframe name="hiddenFrame" width="0" height="0" border="0" style="display: none;"></iframe>


<table>
<tr>
<td>Stock Prices</td>
</tr>
  <tr>
    <td>Company</td>
    <td>Date</td>
    <td>Unit price</td>
    <td>Amount</td>
    <td>Code</td>
    <td>Actions</td>
  </tr>

<script>
function confirmation() {
  confirm("Are you sure?");
}
</script>

<form:form method="GET" modelAttribute="items" class="form-signin">
<c:forEach items="${availableStocks}" var="availableStock">
<tr>
  <form action="/stocks/${availableStock.name}/${availableStock.unit}/buy" method="post">
    <td>${availableStock.name}</td>
    <td>${availableStock.date}</td>
    <td>${availableStock.price}</td>
    <td>${availableStock.unit}</td>
    <td>${availableStock.code}</td>
    <td><button type="submit" onclick="confirmation()">Buy</td>
</tr>
</form>
</c:forEach>
</table>

<table>
<tr><td>Account Stock</td></tr>
<tr>
<td>Company</td>
<td>Unit price</td>
<td>Amount</td>
<td>Actions</td>
</tr>

  <form:form method="GET" modelAttribute="ustocks" class="form-signin">
  <c:forEach var="userStock" items="${userStocks}">
  <tr>
  <form action="/stocks/${userStock.name}/sell" method="post">
    <td>${userStock.name}</td>
    <td>${userStock.price}</td>
    <td>${userStock.unit}</td>
    <td><button type="submit" onclick="confirmation()">Sell</td>
    </tr>
    </form>
    </c:forEach>
</table>

<table>
<tr>
<td>Account Balance</td>
</tr>
<form:form method="GET" modelAttribute="balance" class="form-signin">
<td>${balance}</td>
</tr>
</table>


</div>
<!-- /.container -->
</html>
