<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<title>选择菜单</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/admin/dialog.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/datagrid/datagird.formatter.js"></script>
	</head>
	<body>
		<div style="overflow: hidden; padding: 5px;">
			<div id="tb" style="height:auto;border: 1px solid #ccc">
				<div style="margin:10px;">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="javascript:authMenu();">确定</a>
				</div>
				<hr style="border: 1px solid #ccc">
				<div style="padding:5px;overflow:auto;">
					<ul id="treemenu"></ul>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		$.ajaxSetup ({
		   cache: false //关闭AJAX相应的缓存
		});
		
		$(function(){
			$('#treemenu').tree({
				url: '${ctx}/loadAllMenus/?roleId=${roleId}',
				checkbox: true,
				onClick:function(node){
					$(this).tree('toggle', node.target); // 点节点展开树
				}
			});
		});
		
		function authMenu() {
			var nodes = $('#treemenu').tree('getChecked');
			if (nodes.length > 0){
				var parms = '';
				for(var i = 0; i < nodes.length; i++){
					 parms = parms+','+nodes[i].id;
				}
				
				$.messager.confirm('确认框','您确定要授权选中的记录吗?',function(r){
					if (r){
						$.ajax({
				    		url: '${ctx}/admin/menu/authMenu/'+parms,
				    		type: 'post',
				    		contentType : 'application/json',
				    		dataType : "json",
				    		success: function(result){
					            if (result.success){
					            	$.messager.alert('提示信息','操作成功！');
					                reloadMenu();
					            } else {
					            	$.messager.alert('提示信息', result.msg);
					            }
					        }
				    	});
					}
				});
			} else {
				$.messager.alert('提示消息', '请选择您要授权的数据！');
			}
		}
		
		// 查询分类
		function reloadMenu(){
			var _url = '${ctx}/loadAllMenus/?roleId=${roleId}';
            $('#treemenu').tree({ url: _url });
        }
		</script>
	</body>
</html>
