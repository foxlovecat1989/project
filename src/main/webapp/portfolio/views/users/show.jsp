<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(document).ready(function() {
		$('#dataTables-user').DataTable({
			responsive : true
		});
	});
</script>

<!-- Page Content -->
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">User Detail</h1>
			</div>
			<!-- /.col-lg-12 -->
		</div>

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

					<div class="panel-body">
					
                            <table id="dataTables-user" width="100%" class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>屬性</th>
                                        <th>值</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="odd gradeA">
                                        <td>ID</td>
                                        <td>${user.id}</td>
                                    </tr>
                                    <tr class="even gradeA">
                                        <td>Name</td>
                                        <td>${user.name}</td>
                                    </tr>
                                      <tr class="odd gradeA">
                                        <td>Email</td>
                                        <td>${user.email}</td>
                                    </tr>
                                    <tr class="even gradeA">
                                        <td>Address</td>
                                        <td>${user.address}</td>
                                    </tr>
                                    <tr class="odd gradeA">
                                        <td>Newsletter</td>
                                        <td>${user.newsletter}</td>
                                    </tr>
                                    <tr class="even gradeA">
                                        <td>Web Frameworks</td>
                                        <td>${user.framework}</td>
                                    </tr>
                                      <tr class="odd gradeA">
                                        <td>Sex</td>
                                        <td>${user.sex}</td>
                                    </tr>
                                    <tr class="even gradeA">
                                        <td>Height</td>
                                        <td>${user.height}</td>
                                    </tr>
                                    <tr class="odd gradeA">
                                        <td>Country</td>
                                        <td>${user.country}</td>
                                    </tr>
                                    <tr class="even gradeA">
                                        <td>Skill</td>
                                        <td>${user.skill}</td>
                                </tbody>
                               </table>     
  
					</div>
				</div>
			</div>
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container-fluid -->
</div>
<!-- /#page-wrapper -->

