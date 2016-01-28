<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>微信用户管理</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/admin/dialog.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>

<body>
	<form id="synchrUserList" action="" method="post">
		<table id="mytable" width="100%" border="0" cellpadding="0" cellspacing="0" >
			<tr>
				<td class="tright" style="width: 100px; font-size: 12px;" colspan="2">同步用户列表</td>
			</tr>
			<tr>
				<td class="tleft" style="width: 100px; font-size: 12px;">access_token：</td>
				<td class="tright">
					<input id="access_token" name="access_token" class="easyui-textbox" data-options="required:true" style="width: 800px;" value="${access_token}">
				</td> 
			</tr>
			<tr>
				<td class="left" style="font-size: 12px;">synchr_user_list_url：</td>
				<td class="right">
					<input id="synchr_user_list_url" name="synchr_user_list_url" class="easyui-textbox" data-options="required:true" style="width: 800px;" value="https://api.weixin.qq.com/cgi-bin/user/get?access_token=">
				</td>
			</tr>
			<tr>
				<td class="left" style="font-size: 12px;">next_openid：</td>
				<td class="right">
					<input id="next_openid" name="next_openid" class="easyui-textbox" style="width: 800px;" value="${next_openid}">
				</td> 
			</tr>
			<tr align="center">
				<td class="right" colspan="2">
					next_openid=${next_openid}<br>
					count=${count}
				</td>
			</tr>
			<tr align="center">
				<td class="bright" colspan="2">
					<a id="synchrUserListbtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="$('#synchrUserListbtn').linkbutton('disable');javascript:synchrUserList();">确定</a>
				</td>
			</tr>
		</table>
	</form>
	<br>
	<form id="synchrUserInfo" action="" method="post">
		<table id="mytable" width="100%" border="0" cellpadding="0" cellspacing="0" >
			<tr>
				<td class="tright" style="width: 100px; font-size: 12px;" colspan="2">同步用户明细</td>
			</tr>
			<tr>
				<td class="tleft" style="width: 100px; font-size: 12px;">access_token：</td>
				<td class="tright">
					<input id="access_token" name="access_token" class="easyui-textbox" data-options="required:true" style="width: 800px;" value="${access_token}">
				</td> 
			</tr>
			<tr>
				<td class="left" style="font-size: 12px;">synchr_user_list_url：</td>
				<td class="right">
					<input id="synchr_user_list_url" name="synchr_user_list_url" class="easyui-textbox" data-options="required:true" style="width: 800px;" value="https://api.weixin.qq.com/cgi-bin/user/info?access_token=">
				</td>
			</tr>
			<tr align="center">
				<td class="bright" colspan="2">
					<a id="synchrUserInfobtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="$('#synchrUserInfobtn').linkbutton('disable');javascript:synchrUserInfo();">确定</a>
				</td>
			</tr>
		</table>
	</form>
	<br>
	<form id="synchrGroup" action="" method="post">
		<table id="mytable" width="100%" border="0" cellpadding="0" cellspacing="0" >
			<tr>
				<td class="tright" style="width: 100px; font-size: 12px;" colspan="2">同步分组</td>
			</tr>
			<tr>
				<td class="tleft" style="width: 100px; font-size: 12px;">access_token：</td>
				<td class="tright">
					<input id="access_token" name="access_token" class="easyui-textbox" data-options="required:true" style="width: 800px;" value="${access_token}">
				</td> 
			</tr>
			<tr>
				<td class="left" style="font-size: 12px;">synchr_group_url：</td>
				<td class="right">
					<input id="synchr_group_url" name="synchr_group_url" class="easyui-textbox" data-options="required:true" style="width: 800px;" value="https://api.weixin.qq.com/cgi-bin/groups/get?access_token=">
				</td>
			</tr>
			<tr align="center">
				<td class="bright" colspan="2">
					<a id="synchrGroupbtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="$('#synchrGroupbtn').linkbutton('disable');javascript:synchrGroup();">确定</a>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		function synchrUserList(){
			document.getElementById("synchrUserList").action="${ctx}/weixin/synchr/userlist";
		    document.getElementById("synchrUserList").submit();
		}
		
		function synchrUserInfo(){
			document.getElementById("synchrUserInfo").action="${ctx}/weixin/synchr/userInfo";
		    document.getElementById("synchrUserInfo").submit();
		}
		
		function synchrGroup(){
			document.getElementById("synchrGroup").action="${ctx}/weixin/synchr/group";
		    document.getElementById("synchrGroup").submit();
		}
	</script>
</body>
</html>
