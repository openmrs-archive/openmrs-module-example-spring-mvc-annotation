<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<h1>List of patients with upcoming visits</h1>

<br/>


<table>
	<tr>
		<th>Patient Name</th>
		<th>Birth Date</th>
	</tr>
	<c:forEach items="${patientVisitList}" var="patient">
		<tr>
			<td>${patient.givenName} ${patient.familyName}</td>
			<td>${patient.birthdate}</td>
		</tr>
	</c:forEach>


</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>
