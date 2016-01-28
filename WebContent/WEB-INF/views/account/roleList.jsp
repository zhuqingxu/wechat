<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<title>综合演示用例</title>
		<script>
			$(document).ready(function() {
				$("#account-tab").addClass("active");
			});
		</script>
	</head>
	
	<body>
		<h1>角色管理</h1>
		<c:if test="${not empty message}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		</c:if>
		
		<div class="row">
			<div class="offset4">
				<form class="form-search" action="#">
				 	<label>角色名：</label> <input type="text" name="name" class="input-small"> 
				    <button type="submit" class="btn" id="search_btn">Search</button>
			    </form>
		    </div>
		</div>	
				
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>ID</th>
					<th>角色名</th>
					<th>权限</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${roles}" var="role">
					<tr>
						<td>${role.id}&nbsp;</td>
						<td>${role.name}&nbsp;</td>
						<td>${role.permissions}&nbsp;</td>
						<td>
							<shiro:hasPermission name="role:edit">
								<a href="${ctx}/account/role/update/${role.id}" id="editLink-${role.id}">修改</a>
							</shiro:hasPermission>
						</td>
					</tr>
				</c:forEach>
			</tbody>		
		</table>
	</body>
</html>
