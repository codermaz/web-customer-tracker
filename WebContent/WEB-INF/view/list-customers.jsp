
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<title>Customers list</title>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css" />
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>

	<div id="container">
		<div id="content">

			<!-- Put new Button: add customer -->
			<input type="button" value="Add customer"
				onclick="window.location.href='showFormForAdd'; return false;"
				class="add-button" />
				
			<!--  add a search box -->
            <form:form action="search" method="POST">
                Search customer: <input type="textbox" name="theSearchName" />               
                <input type="submit" value="Search" class="add-button" />
            </form:form>
            	
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>

				<c:forEach var="tempCustomer" items="${customers}">

					<!-- Construct an update link with customer id -->
					<c:url var="updateLink" value="/customer/showFormForUpdate">
						<c:param name="customerId" value="${tempCustomer.id}" />
					</c:url>
					<!-- Construct an delete link with customer id -->
					<c:url var="deleteLink" value="/customer/delete">
						<c:param name="customerId" value="${tempCustomer.id}" />
					</c:url>

					<tr>
						<td>${tempCustomer.firstName}</td>
						<td>${tempCustomer.lastName}</td>
						<td>${tempCustomer.email}</td>
	 					<td><a href="${updateLink}">Update</a> 
	 						<a href="${deleteLink}"
	 							onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">| Delete </a>
	 					</td> 
<%-- 			or---->		<a href="${pageContext.request.contextPath}/customer/updateCustomer?customerId=${tempCustomer.id}">Update</a> --%>
					</tr>
				</c:forEach>


			</table>
		</div>
	</div>


</body>
</html>