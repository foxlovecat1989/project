<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(document).ready(function() {
		$('#dataTables-users').DataTable({
			responsive : true
		});
	});
</script>

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header"><s:message code='listUser'/></h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">	
			
					<c:if test="${not empty msg}">
						<div class="alert alert-${css} alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<strong>${msg}</strong>
						</div>
					</c:if>			
				
				<!-- /.panel-heading -->
				<div class="panel-body">
					<table width="100%" class="table table-striped table-bordered table-hover" 
							id="dataTables-users">
						<thead>
							<tr>
								<th>#ID</th>
								<th>Name</th>
								<th>Email</th>
								<th>framework</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${users}">
								<tr>
									<td>${user.id}</td>
									<td>${user.name}</td>
									<td>${user.email}</td>
									<td>
										<c:forEach var="framework" items="${user.framework}" varStatus="loop">
												${framework} <c:if test="${not loop.last}">,</c:if>
										</c:forEach>
									</td>
									<td>
										<s:url value="/user/${user.id}" var="userUrl" />
										<s:url value="/user/${user.id}/delete" var="deleteUrl" />
										<s:url value="/user/${user.id}/update" var="updateUrl" />
										<s:url value="/users" var="redirectUrl" />

										<button onclick="location.href='${userUrl}'" class="btn btn-info" >Query</button>
										<button onclick="location.href='${updateUrl}'" class="btn btn-primary">Update</button>
										<button onclick="$.post('${deleteUrl}',function(response){window.location.href='${redirectUrl}';})" class="btn btn-danger">Delete</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
</div>
