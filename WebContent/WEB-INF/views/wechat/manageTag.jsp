<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<title>用户标签</title>
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
				<div>
					<form id="ff" method="post">
						<table border="0" width="100%">
							<tr>
								<td style="width: 55px;">标签名称:</td>
								<td style="width: 200px;">
									<input id="search_name" class="easyui-textbox" style="width: 200px; border: 1px solid #ccc">
								</td>
								<td style="width: 55px;">
									<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:searchItem()">搜索</a>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:syncTag();">同步用户标签</a>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		$.ajaxSetup ({
		   cache: false //关闭AJAX相应的缓存
		});
		
		$(function(){
			$('#dg').datagrid({
				url:'${ctx}/wechat/tag/list', // 从远程站点请求数据
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
					{field:'tagName',title:'标签名称',width:300,halign:'center',
						formatter:function(val,row){
	                		return formatTip(val,row);
						}
					},
					{field:'count',title:'标签下粉丝数',width:200,halign:'center',
						formatter:function(val,row){
	                		return formatTip(val,row);
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
				       	var url = encodeURI('${ctx}/wechat/tag/list/?rows='+pageSize+'&page='+pageNumber+'&name='+encodeURI(name));
				       	$.post(url, {}, function(data) {
				            var d = data;
				            $('#dg').datagrid('loadData',d);
				       	}, 'json');
			    	}
				});
			}
		});
			
		// 查询
		function searchItem() {
			var grid = $("#dg");
			var options = grid.datagrid('getPager').data('pagination').options;
			options.pageNumber = 1;
			var currentPageNo = options.pageNumber; // 当前页
			var currentPageSize = options.pageSize; // 每页记录数
			var total = options.total; // 记录总数
			var max = Math.ceil(total/options.pageSize); // 总页数
			
           	var name = $('#search_name').val();
			var url = encodeURI('${ctx}/wechat/tag/list/?rows='+currentPageSize+'&page='+currentPageNo+'&name='+encodeURI(name));
	        $.post(url, {}, function(data) {
	            var d = data;
	            $('#dg').datagrid('loadData',d);
	        }, 'json');
	        
	        grid.datagrid('getPager').pagination({pageNumber: 1});
	        $('#dg').datagrid('clearSelections');
	    }
	    
		// 清空查询条件
		function clearForm(){
			$('#ff').form('clear');
		}
		
		// 同步用户标签
		function syncTag(){
			$.messager.confirm('确认框','您确定要同步用户标签吗?',function(r){
	            if (r){
					$.ajax({
			    		url: '${ctx}/wechat/tag/sync',
			    		type: 'post',
			    		data: {},
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
		}
		</script>
	</body>
</html>
