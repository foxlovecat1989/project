<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="page-wrapper">

	<div class="row">
		<div class="col-lg-12">
			<c:choose>
				<c:when test="${userForm['new']}">
					<h1 class="page-header">
						<s:message code='addUser' />
					</h1>
				</c:when>
				<c:otherwise>
					<h1 class="page-header">
						<s:message code='updateUser' />
					</h1>
				</c:otherwise>
			</c:choose>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">

				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">

							<s:url value="/user/save" var="userActionUrl" />

							<sf:form class="form-horizontal" method="post"
								modelAttribute="userForm" action="${userActionUrl}">

								<sf:hidden path="id" />

								<s:bind path="name">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Name</label>
										<div class="col-sm-10">
											<sf:input path="name" class="form-control " placeholder="Name" />
											<sf:errors path="name" class="control-label" />
										</div>
									</div>
								</s:bind>

								<s:bind path="email">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Email</label>
										<div class="col-sm-10">
											<sf:input path="email" class="form-control" placeholder="Email" />
											<sf:errors path="email" class="control-label" />
										</div>
									</div>
								</s:bind>

								<s:bind path="password">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Password</label>
										<div class="col-sm-10">
											<sf:password path="password" class="form-control" placeholder="password" />
											<sf:errors path="password" class="control-label" />
										</div>
									</div>
								</s:bind>

								<s:bind path="confirmPassword">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">confirm Password</label>
										<div class="col-sm-10">
											<sf:password path="confirmPassword" class="form-control" placeholder="password" />
											<sf:errors path="confirmPassword" class="control-label" />
										</div>
									</div>
								</s:bind>

								<s:bind path="address">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Address</label>
										<div class="col-sm-10">
											<sf:textarea path="address" rows="5" class="form-control" placeholder="address" />
											<sf:errors path="address" class="control-label" />
										</div>
									</div>
								</s:bind>

								<s:bind path="newsletter">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Newsletter</label>
										<div class="col-sm-10">
											<div class="checkbox">
												<label> <sf:checkbox path="newsletter" /></label>
												<sf:errors path="newsletter" class="control-label" />
											</div>
										</div>
									</div>
								</s:bind>

								<s:bind path="framework">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Web Frameworks</label>
										<div class="col-sm-10">
											<sf:checkboxes path="framework" items="${javaMvcList}" element="label class='checkbox-inline'" />
											<br />
											<sf:errors path="framework" class="control-label" />
										</div>
									</div>
								</s:bind>

								<s:bind path="sex">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Sex</label>
										<div class="col-sm-10">
											<label class="radio-inline"> <sf:radiobutton path="sex" value="M" /> Male </label> 
											<label class="radio-inline"> <sf:radiobutton path="sex" value="F" /> Female </label> <br />
											<sf:errors path="sex" class="control-label" />
										</div>
									</div>
								</s:bind>

								<s:bind path="height">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Height(cm)</label>
										<div class="col-sm-10">
											<sf:radiobuttons path="height" items="${heightList}" element="label class='radio-inline'" />
											<br />
											<sf:errors path="height" class="control-label" />
										</div>
									</div>
								</s:bind>

								<s:bind path="country">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Country</label>
										<div class="col-sm-5">
											<sf:select path="country" class="form-control">
												<sf:option value="NONE" label="--- Select ---" />
												<sf:options items="${countryList}" />
											</sf:select>
											<sf:errors path="country" class="control-label" />
										</div>
										<div class="col-sm-5"></div>
									</div>
								</s:bind>

								<s:bind path="skill">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Web Skills</label>
										<div class="col-sm-5">
											<sf:select path="skill" items="${webSkillList}" multiple="true" size="5" class="form-control" />
											<sf:errors path="skill" class="control-label" />
										</div>
										<div class="col-sm-5"></div>
									</div>
								</s:bind>

								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<c:choose>
											<c:when test="${userForm['new']}">
												<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
											</c:when>
											<c:otherwise>
												<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</sf:form>

						</div>
					</div>
					<!-- /.row (nested) -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->