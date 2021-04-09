<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Error Page</h1>
			</div>
			<!-- /.col-lg-12 -->
		</div>

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<p><strong>Error Path: </strong> <c:out value="${url}"/></p>								
						<p><strong>Error message: </strong><c:out value="${ex}"/></p>
						<p><strong>Error Stacks:</strong></p>
						<pre style="line-height: 0.8">
						<c:forEach items="${ex.stackTrace}" var="stackTrace"> 							
<c:out value="${stackTrace}"/>
						</c:forEach>
						</pre>
					</div>
				</div>
			</div>
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->



