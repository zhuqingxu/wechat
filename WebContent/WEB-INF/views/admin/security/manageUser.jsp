<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<title>用户维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/admin/dialog.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/datagrid/datagrid.extend.js"></script>
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
			<div id="dicwin" title="选择角色" closed="true"></div>
			<div id="tb" style="padding:1px;height:auto">
				<div>
					<form id="ff" method="post">
						<table border="0" width="100%">
							<tr>
								<td style="width: 45px;">登录名:</td>
								<td style="width: 200px;">
									<input id="search_loginName" class="easyui-textbox" style="width: 200px; border: 1px solid #ccc">
								</td>
								<td style="width: 30px;">状态:</td>
					    		<td style="width: 100px;">
					    			<select class="easyui-combobox" data-options="panelHeight:'auto', editable: false" id="search_status" style="width:100px;">
								        <option value="">全部</option>
								        <option value="enabled">有效</option>
								        <option value="disabled">无效</option>
								    </select>
					    		</td>
								<td style="width: 55px;">
									<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:searchUser()">搜索</a>
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
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:addUser();">新增</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="user:edit">
						<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:editUser();">修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="user:delete">
						<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:deleteUser();">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="user:authRole">
			    		<a href="#" class="easyui-linkbutton" iconCls="icon-config" plain="true" onclick="javascript:selectRole();">选择角色</a>
			    	</shiro:hasPermission>
				</div>
			</div>
		</div>
		<div id="dlg" class="easyui-dialog" style="width:450px;height:260px;padding:5px" closed="true" buttons="#dlg-buttons">
			<form:form id="fm" modelAttribute="user" method="post">
				<table id="mytable" width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
					<tr>
						<td class="tleft" style="width: 100px; font-size: 12px;">登录名：</td>
						<td class="tright">
							<input id="loginName" name="loginName" class="easyui-textbox" data-options="required:true" style="width: 300px;">
							<input type="hidden" id="id" name="id">
							<input type="hidden" id="option">
						</td> 
					</tr>
					<tr>
						<td class="left" style="font-size: 12px;">用户名：</td>
						<td class="right">
							<input id="name" name="name" class="easyui-textbox" data-options="required:true" style="width: 300px;">
						</td> 
					</tr>
					<tr>
						<td class="left" style="font-size: 12px;">邮箱：</td>
						<td class="right">
							<input id="email" name="email" class="easyui-textbox" data-options="validType:'email', required:true" style="width: 300px;">
						</td> 
					</tr>
					<tr>
						<td class="left" style="font-size: 12px;">密码：</td>
						<td class="right">
							<input type="password" id="plainPassword" name="plainPassword" class="easyui-textbox" data-options="required:true" style="width: 300px;">
							<input type="hidden" id="password" name="password">
							<input type="hidden" id="salt" name="salt">
						</td> 
					</tr>
					<tr>
						<td class="bleft" style="font-size: 12px;">状态：</td>
						<td class="bright">
							<select class="easyui-combobox" data-options="panelHeight:'auto', editable: false" id="status" name="status" style="width:300px;">
						        <option value="enabled">有效</option>
						        <option value="disabled">无效</option>
						    </select>
						</td> 
					</tr>
				</table>
			</form:form>
		</div>
		<div id="dlg-buttons">
			<a id="savebtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="$('#savebtn').linkbutton('disable');javascript:saveUser();">确定</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-no" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
		<script type="text/javascript">
		$.ajaxSetup ({
		   cache: false //关闭AJAX相应的缓存
		});
		
		$(function(){
			$('#dg').datagrid({
				url:'${ctx}/admin/user', // 从远程站点请求数据
				method:'post', // 请求类型
				nowrap:true, // 同一行中显示数据（设置为true可以提高加载性能）
				autoRowHeight:false, // 根据行的内容设置行的高度（设置为false可以提高负载性能）
				height:$(window).height(),
				idField:'id', // 标识字段
				fit:false,
				fitColumns:false,
				singleSelect:true, // 只允许选择一行
				rownumbers:true, // 显示行号
				pagination:true, // 显示分页工具栏
				pagePosition:'bottom', // 分页工具栏的位置（'top','bottom','both'）
				pageNumber: 1, // 分页 - 初始化页码
		        pageSize: 15, // 分页 - 初始化每页显示数量
		        pageList: [15,50,100], // 分页- 初始化每页显示数量下拉列表
				columns:[[ // 列配置
					{field:'loginName',title:'登录名',width:200,halign:'center',
						styler: function(index,row){
							if (row.id==1){
								return 'background-color:#6293BB;color:#fff;';
							}
						}
					}, // 列字段
					{field:'name',title:'用户名',width:200,halign:'center',
						formatter:function(val,row){
	                		return formatTip(val,row);
						}	
					},
					{field:'email',title:'邮箱',width:200,halign:'center'},
					{field:'status',title:'状态',width:100,halign:'center',align:'center',
		                formatter:function(val,row){
		                	return formatStatus(val,row);
						}
					}
				]],
				toolbar:'#tb',
				onDblClickRow: function(index,row){
					editUser();
				}
			});

			var pg = $("#dg").datagrid("getPager");
			if(pg){
				$(pg).pagination({
					beforePageText:'第',
					afterPageText:'页, 共 {pages} 页',
					displayMsg:'当前显示 {from} - {to} 条记录, 共 {total} 条记录',
					
					onSelectPage:function(pageNumber,pageSize){
			           	var loginName = $('#search_loginName').val();
			        	var status = $('#search_status').combobox('getValue');
				       	var url = encodeURI('${ctx}/admin/user/?rows='+pageSize+'&page='+pageNumber+'&loginName='+encodeURI(loginName)+'&status='+status);
				       	$.post(url, {}, function(data) {
				            var d = data;
				            $('#dg').datagrid('loadData',d);
				       	}, 'json');
			    	}
				});
			}
		});
			
		// 查询
		function searchUser() {
			var grid = $("#dg");
			var options = grid.datagrid('getPager').data('pagination').options;
			options.pageNumber = 1;
			var currentPageNo = options.pageNumber; // 当前页
			var currentPageSize = options.pageSize; // 每页记录数
			var total = options.total; // 记录总数
			var max = Math.ceil(total/options.pageSize); // 总页数
			
           	var loginName = $('#search_loginName').val();
			var status = $('#search_status').combobox('getValue');
			var url = encodeURI('${ctx}/admin/user/?rows='+currentPageSize+'&page='+currentPageNo+'&loginName='+encodeURI(loginName)+'&status='+status);
	        $.post(url, {}, function(data) {
	            var d = data;
	            $('#dg').datagrid('loadData',d);
	        }, 'json');
	        
	        grid.datagrid('getPager').pagination({pageNumber: 1});
	    }
	    
		// 新增
		function addUser(){
			$('#dlg').dialog('open').dialog('setTitle','添加用户');
   			$('#fm').form('clear');
	    	$('#status').combobox('setValue', 'enabled'); // 默认有效
	    	$("#plainPassword").textbox({
	    		disabled:false
	    	});
	    	$('#option').val('add');
		}
		
		// 修改
		function editUser(){
			var row = $('#dg').datagrid('getSelected');
			if(row){
				$('#dlg').dialog('open').dialog('setTitle','修改用户');
	   			$('#fm').form('load', row);
		    	$("#plainPassword").textbox({
		    		disabled:true
		    	});
		    	$('#option').val('update');
			} else {
				$.messager.alert('提示信息','请选择您要修改的数据！');
			}
		}
		
		// 保存
		function saveUser(){
		    $('#fm').form({
		        url: '${ctx}/admin/user/save',
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
		            		searchUser();
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
		function deleteUser(){
			var row = $('#dg').datagrid('getSelected');
		    if (row){
		        $.messager.confirm('确认框','您确定要删除用户【'+row.loginName+'】吗?',function(r){
		            if (r){
						$.ajax({
				    		url: '${ctx}/admin/user/delete/'+row.id,
				    		type: 'post',
				    		data: {id: row.id},
				    		contentType : 'application/json',
				    		dataType : "json",
				    		success: function(result){
					            if (result.success){
					            	searchUser();
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
		
		// 选择角色
		function selectRole(){
			var row = $('#dg').datagrid('getSelected');
			if(row){
				selectDictionary('#dicwin','${ctx}/admin/user/selectRole/'+row.id);
			} else {
				$.messager.alert('提示信息','请选择您要操作的数据！');
			}
		}
		
		// 清空查询条件
		function clearForm(){
			$('#ff').form('clear');
			$('#search_status').combobox('setValue', '');
		}
		</script>
	</body>
</html>
