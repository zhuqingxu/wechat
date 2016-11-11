<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<title>微信接口配置</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/admin/dialog.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/datagrid/datagird.formatter.js"></script>
	</head>
	<body>
		<div style="overflow: hidden; padding: 0px;">
			<table id="dg"></table>
			<div id="tb" style="padding:1px;height:auto">
				<div style="text-align: right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:addWechatConfig();">新增</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:editWechatConfig();">修改</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:deleteWechatConfig();">删除</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-config" plain="true" onclick="javascript:getAccessToken();">获取Token</a>
				</div>
			</div>
		</div>
		<div id="dlg" class="easyui-dialog" style="width:450px;height:155px;padding:5px" closed="true" buttons="#dlg-buttons">
			<form:form id="fm" modelAttribute="wechatConfig" method="post">
				<table id="mytable" width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
					<tr>
						<td class="tleft" style="width: 120px; font-size: 12px;">appId：</td>
						<td class="tright">
							<input id="appId" name="appId" class="easyui-textbox" data-options="required:true" style="width: 300px;" />
							<input type="hidden" id="id" name="id" />
							<input type="hidden" id="option" name="option" />
						</td>
					</tr>
					<tr>
						<td class="bleft" style="font-size: 12px;">appSecret：</td>
						<td class="bright">
							<input id="appSecret" name="appSecret" class="easyui-textbox" data-options="required:true" style="width: 300px;" />
						</td>
					</tr>
				</table>
			</form:form>
		</div>
		<div id="dlg-buttons">
			<a id="savebtn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="$('#savebtn').linkbutton('disable');javascript:saveWechatConfig();">确定</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-no" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
		<script type="text/javascript">
		$.ajaxSetup ({
		   cache: false //关闭AJAX相应的缓存
		});
		
		$(function(){
			$('#dg').datagrid({
				url:'${ctx}/wechat/config/list', // 从远程站点请求数据
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
				pageNumber:1, // 分页 - 初始化页码
		        pageSize:20, // 分页 - 初始化每页显示数量
		        pageList:[20,50,100], // 分页- 初始化每页显示数量下拉列表
				columns:[[ // 列配置
					{field:'appId',title:'appId',width:120,halign:'center',
						formatter:function(val,row){
	                		return formatTip(val,row);
						}
					},
					{field:'appSecret',title:'appSecret',width:220,halign:'center',
						formatter:function(val,row){
	                		return formatTip(val,row);
						}
					},
					{field:'token',title:'token',width:400,halign:'center',
						formatter:function(val,row){
	                		return formatTip(val,row);
						}	
					},
					{field:'expiresTime',title:'过期时间',width:150,halign:'center',align:'center'}
				]],
				toolbar:'#tb',
				onDblClickRow: function(index,row){
					editWechatConfig();
				},
				rowStyler:function(index,row){
					if (row.validStatus==0){
						return 'color:red;';
					}
				}
			});

			var pg = $("#dg").datagrid("getPager");
			if(pg){
				$(pg).pagination({
					beforePageText:'第',
					afterPageText:'页, 共 {pages} 页',
					displayMsg:'当前显示 {from} - {to} 条记录, 共 {total} 条记录',
					
					onSelectPage:function(pageNumber,pageSize){
				       	var url = encodeURI('${ctx}/wechat/config/list/?rows='+pageSize+'&page='+pageNumber);
				       	$.post(url, {}, function(data) {
				            var d = data;
				            $('#dg').datagrid('loadData',d);
				       	}, 'json');
			    	}
				});
			}
		});
			
		// 新增
		function addWechatConfig(){
			$('#dlg').dialog('open').dialog('setTitle','添加微信接口配置');
   			$('#fm').form('clear');
	    	$("#option").val('create'); // 新增标识
		}
		
		// 修改
		function editWechatConfig(){
			var row = $('#dg').datagrid('getSelected');
			if(row){
				$('#dlg').dialog('open').dialog('setTitle','修改微信接口配置');
	   			$('#fm').form('load', row);
		    	$("#option").val('update'); // 修改标识
			} else {
				$.messager.alert('提示信息','请选择您要修改的数据！');
			}
		}
		
		// 保存
		function saveWechatConfig(){
		    $('#fm').form({
		        url: '${ctx}/wechat/config/save',
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
		            	if(opt == 'create') {
		            		$('#dg').datagrid('reload');
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
		function deleteWechatConfig(){
			var row = $('#dg').datagrid('getSelected');
		    if (row){
		        $.messager.confirm('确认框','您确定要删除吗?',function(r){
		            if (r){
						$.ajax({
				    		url: '${ctx}/wechat/config/delete',
				    		type: 'post',
				    		data: {id: row.id},
				    		dataType : "json",
				    		success: function(result){
					            if (result.success){
					            	$('#dg').datagrid('reload');
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
		
		// 获取AccessToken
		function getAccessToken(){
			var row = $('#dg').datagrid('getSelected');
		    if (row){
		        $.messager.confirm('确认框','您确定要获取AccessToken吗?',function(r){
		            if (r){
						$.ajax({
				    		url: '${ctx}/wechat/config/getAccessToken',
				    		type: 'post',
				    		data: {id: row.id},
				    		dataType : "json",
				    		success: function(result){
					            if (result.success){
					            	$('#dg').datagrid('reload');
					            	$('#dg').datagrid('clearSelections');
					            	$.messager.show({
					                    title: '提示信息',
					                    msg: result.msg
					                });
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
		    	$.messager.alert('提示信息','请选择您要操作的数据！');
			}
		}
		</script>
	</body>
</html>
