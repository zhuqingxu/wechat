<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<title>权限维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/admin/dialog.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/datagrid/datagird.formatter.js"></script>
		<style type="text/css">
		.textbox textarea.textbox-text {
			white-space: pre-wrap;
		}
		</style>
	</head>
	<body>
		<div style="overflow: hidden; padding: 0px;">
			<table id="dg"></table>
			<div id="tb" style="padding:1px;height:auto">
				<div>
					<form id="ff" method="post">
						<table border="0" width="100%">
							<tr>
								<td style="width: 55px;">权限名称:</td>
								<td style="width: 200px;">
									<input id="search_name" class="easyui-textbox" style="width: 200px; border: 1px solid #ccc">
								</td>
								<td style="width: 55px;">
									<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:searchPermission()">搜索</a>
								</td>
								<td style="width: 55px;">
									<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="clearForm()">清空</a>
								</td>
								<td style="text-align: right;"></td>
							</tr>
						</table>
					</form>
				</div>
				<div style="text-align: right">
					<shiro:hasPermission name="user:add">
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:addPermission();">新增</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="user:edit">
						<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:editPermission();">修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="user:delete">
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:deletePermission();">删除</a>
					</shiro:hasPermission>
				</div>
			</div>
		</div>
		<div id="dlg" class="easyui-dialog" style="width:450px;height:213px;padding:5px" closed="true" buttons="#dlg-buttons">
			<form:form id="fm" modelAttribute="permission" method="post">
				<table id="mytable" width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
					<tr>
						<td class="tleft" style="width: 100px; font-size: 12px;">权限名称：</td>
						<td class="tright">
							<input id="name" name="name" class="easyui-textbox" data-options="required:true" style="width: 300px;">
							<input type="hidden" id="id" name="id">
							<input type="hidden" id="option">
						</td> 
					</tr>
					<tr>
						<td class="bleft" style="font-size: 12px;">描述：</td>
						<td class="bright">
						    <input id="descn" name="descn" class="easyui-textbox" data-options="multiline:true" style="width:300px;height:80px">
						</td> 
					</tr>
				</table>
			</form:form>
		</div>
		<div id="dlg-buttons">
			<a id="savebtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="$('#savebtn').linkbutton('disable');javascript:savePermission();">确定</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-no" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
		<script type="text/javascript">
		$.ajaxSetup ({
		   cache: false //关闭AJAX相应的缓存
		});
		
		$(function(){
			$('#dg').datagrid({
				url:'${ctx}/admin/permission', // 从远程站点请求数据
				method:'post', // 请求类型
				nowrap:true, // 同一行中显示数据（设置为true可以提高加载性能）
				autoRowHeight:false, // 根据行的内容设置行的高度（设置为false可以提高负载性能）
				height:$(window).height(),
				idField:'id', // 标识字段
				fit: false,
				fitColumns: false,
				singleSelect: true, // 只允许选择一行
				rownumbers:true, // 显示行号
				pagination:true, // 显示分页工具栏
				pagePosition:'bottom', // 分页工具栏的位置（'top','bottom','both'）
				pageNumber: 1, // 分页 - 初始化页码
		        pageSize: 15, // 分页 - 初始化每页显示数量
		        pageList: [15,50,100], // 分页- 初始化每页显示数量下拉列表
				columns:[[
					{field:'name',title:'权限名称',width:200,halign:'center'},
					{field:'descn',title:'描述',width:200,halign:'center'}
				]],
				toolbar:'#tb',
				onDblClickRow: function(index, row){
					editPermission();
				}
			});

			var pg = $("#dg").datagrid("getPager");
			if(pg){
				$(pg).pagination({
					beforePageText:'第',
					afterPageText:'页, 共 {pages} 页',
					displayMsg:'当前显示 {from} - {to} 条记录, 共 {total} 条记录',
					
					onSelectPage:function(pageNumber,pageSize){
			           	var name = $('#search_name').val();
				       	var url = encodeURI('${ctx}/admin/permission/?rows='+pageSize+'&page='+pageNumber+'&name='+encodeURI(name));
				       	$.post(url, {}, function(data) {
				            var d = data;
				            $('#dg').datagrid('loadData',d);
				       	}, 'json');
			    	}
				});
			}
		});
			
		// 查询
		function searchPermission() {
			var grid = $("#dg");
			var options = grid.datagrid('getPager').data('pagination').options;
			options.pageNumber = 1;
			var currentPageNo = options.pageNumber; // 当前页
			var currentPageSize = options.pageSize; // 每页记录数
			var total = options.total; // 记录总数
			var max = Math.ceil(total/options.pageSize); // 总页数
			
           	var name = $('#search_name').val();
			var url = encodeURI('${ctx}/admin/permission/?rows='+currentPageSize+'&page='+currentPageNo+'&name='+encodeURI(name));
	        $.post(url, {}, function(data) {
	            var d = data;
	            $('#dg').datagrid('loadData',d);
	        }, 'json');
	        
	        grid.datagrid('getPager').pagination({pageNumber: 1});
	    }
	    
		// 新增
		function addPermission(){
			$('#dlg').dialog('open').dialog('setTitle','添加权限');
   			$('#fm').form('clear');
   			$('#option').val('add');
		}
		
		// 修改
		function editPermission(){
			var row = $('#dg').datagrid('getSelected');
			if(row){
				$('#dlg').dialog('open').dialog('setTitle','修改权限');
	   			$('#fm').form('load', row);
	   			$('#option').val('update');
			} else {
				$.messager.alert('提示信息','请选择您要修改的数据！');
			}
		}
		
		// 保存
		function savePermission(){
		    $('#fm').form({
		        url: '${ctx}/admin/permission/save',
		        onSubmit: function(){
		        	var isValid = $(this).form('validate');
		        	if (!isValid){
		        		$('#savebtn').linkbutton('enable');
		        	}
		        	return isValid;

		        },
		        success: function(result){
		            var result = eval('('+result+')');
		            if (result.success){
		                $('#dlg').dialog('close');
		            	var opt = $('#option').val();
		            	if(opt == 'add') {
		            		searchPermission();
		            	} else if(opt == 'update') {
		            		$("#dg").datagrid("getPager").pagination('select'); // 刷新当前页
		            	}
		            } else {
		                $.messager.show({
		                    title: '提示信息',
		                    msg: result.msg
		                });
		            }
		        },
		    	onChange:function(){
		    		$('#savebtn').linkbutton('enable');
		    	}
		    });
		    $('#fm').submit();
		}
		
		// 删除
		function deletePermission(){
			var row = $('#dg').datagrid('getSelected');
		    if (row){
		        $.messager.confirm('确认框','您确定要删除权限【'+row.name+'】吗?',function(r){
		            if (r){
						$.ajax({
				    		url: '${ctx}/admin/permission/delete/'+row.id,
				    		type: 'post',
				    		data: {id: row.id},
				    		contentType : 'application/json',
				    		dataType : "json",
				    		success: function(result){
					            if (result.success){
					            	searchPermission();
					                $('#dg').datagrid('clearSelections');
					            } else {
					                $.messager.show({
					                    title: '提示信息',
					                    msg: result.msg
					                });
					            }
					        }
				    	});
					}
		        });
		    } else {
		    	$.messager.alert('提示信息','请选择您要删除的数据！');
			}
		}
		
		// 清空查询条件
		function clearForm(){
			$('#ff').form('clear');
		}
		</script>
	</body>
</html>
