<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<title>选择角色</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/admin/dialog.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/datagrid/datagird.formatter.js"></script>
		<style type="text/css">
		body {
			margin-left: 3px;
			margin-top: 3px;
			margin-right: 3px;
			margin-bottom: 3px;
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
								<td style="width: 55px;">角色名称:</td>
								<td style="width: 200px;">
									<input id="search_name" class="easyui-textbox" style="width: 200px; border: 1px solid #ccc">
								</td>
								<td style="width: 55px;">
									<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:searchRole()">搜索</a>
								</td>
								<td style="width: 55px;">
									<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="clearForm()">清空</a>
								</td>
								<td style="text-align: right;"></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		$.ajaxSetup ({
		   cache: false //关闭AJAX相应的缓存
		});
		
		$(function(){
			$('#dg').datagrid({
				url:'${ctx}/admin/role/authorize/?userId=${userId}',
				method:'post', // 请求类型
				nowrap:true, // 同一行中显示数据（设置为true可以提高加载性能）
				autoRowHeight:false, // 根据行的内容设置行的高度（设置为false可以提高负载性能）
				height:$(window).height()*0.99,
				idField:'id',
				fit: false,
				fitColumns: false,
				singleSelect: true,
				rownumbers:true,
				pagination:true,
				pageNumber: 1,
		        pageSize: 15,
		        pageList: [15,50,100],
				columns:[[
					{field:'name',title:'角色名称',width:200,halign:'center'},
					{field:'descn',title:'描述',width:200,halign:'center'},
					{field:'authorize',title:'授权',width:100,halign:'center',align:'center',
		                formatter:function(val,row){
		                	if(val=='1') {
		                		return '<img src="${ctx}/static/easyui/themes/icons/btn_yes.gif" alt="已授权"/>';
		                	} else if(val=='0') {
		                		return "未授权";
		                	}
						}
					},
					{field:'operate',title:'操作',width:100,halign:'center',align:'center',
						formatter:function(val,row){
							if(row.authorize == 0) {
								return '<a href="javascript:authRole();">授权</a>';
							} else if(row.authorize == 1) {
								return '<a href="javascript:unauthRole();">撤销授权</a>';
							}
   						}
					}
				]],
				toolbar:'#tb'
			});

			var pg = $("#dg").datagrid("getPager");
			if(pg){
				$(pg).pagination({
					beforePageText:'第',
					afterPageText:'页, 共 {pages} 页',
					displayMsg:'当前显示 {from} - {to} 条记录, 共 {total} 条记录',
					
					onSelectPage:function(pageNumber,pageSize){
			           	var name = $('#search_name').val();
				       	var url = encodeURI('${ctx}/admin/role/authorize/?userId=${userId}&rows='+pageSize+'&page='+pageNumber+'&name='+encodeURI(name));
				       	$.post(url, {}, function(data) {
				            var d = data;
				            $('#dg').datagrid('loadData',d);
				       	}, 'json');
			    	}
				});
			}
		});
			
		// 查询
		function searchRole() {
			var grid = $("#dg");
			var options = grid.datagrid('getPager').data('pagination').options;
			options.pageNumber = 1;
			var currentPageNo = options.pageNumber; // 当前页
			var currentPageSize = options.pageSize; // 每页记录数
			var total = options.total; // 记录总数
			var max = Math.ceil(total/options.pageSize); // 总页数
			
           	var name = $('#search_name').val();
			var url = encodeURI('${ctx}/admin/role/authorize/?userId=${userId}&rows='+currentPageSize+'&page='+currentPageNo+'&name='+encodeURI(name));
	        $.post(url, {}, function(data) {
	            var d = data;
	            $('#dg').datagrid('loadData',d);
	        }, 'json');
	    }
	    
		// 授权角色
		function authRole(){
			var row = $('#dg').datagrid('getSelected');
		    if (row){
				$.ajax({
		    		url: '${ctx}/admin/role/authRole/'+row.id,
		    		type: 'post',
		    		data: {id: row.id},
		    		contentType : 'application/json',
		    		dataType : "json",
		    		success: function(result){
			            if (result.success){
			            	searchRole();
			                $('#dg').datagrid('clearSelections');
			            } else {
			                $.messager.show({
			                    title: '提示信息',
			                    msg: result.msg
			                });
			            }
			        }
		    	});
		    } else {
		    	$.messager.alert('提示信息','请选择您要操作的数据！');
			}
		}
		
		// 撤销授权角色
		function unauthRole(){
			var row = $('#dg').datagrid('getSelected');
		    if (row){
				$.ajax({
		    		url: '${ctx}/admin/role/unauthRole/'+row.id,
		    		type: 'post',
		    		data: {id: row.id},
		    		contentType : 'application/json',
		    		dataType : "json",
		    		success: function(result){
			            if (result.success){
			            	searchRole();
			                $('#dg').datagrid('clearSelections');
			            } else {
			                $.messager.show({
			                    title: '提示信息',
			                    msg: result.msg
			                });
			            }
			        }
		    	});
		    } else {
		    	$.messager.alert('提示信息','请选择您要操作的数据！');
			}
		}
		
		// 清空查询条件
		function clearForm(){
			$('#ff').form('clear');
		}
		</script>
	</body>
</html>
