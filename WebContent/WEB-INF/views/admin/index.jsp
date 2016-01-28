<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<title>网站后台首页</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
	</head>
	<body class="easyui-layout">
		<div id="head" data-options="region:'north',split:false,border:true" style="height:58px;">
			<shiro:user>
				<table cellSpacing=0 cellPadding=0 width="100%" background="${ctx}/static/images/admin/header_bg.jpg" border=0>
					<tr height=56>
						<td width=260><img height=56 src="${ctx}/static/images/admin/header_left.jpg" width=260></td>
						<td style="FONT-WEIGHT: bold; COLOR: #fff; PADDING-TOP: 10px" align=middle>
							你好, <shiro:principal property="name"/>&nbsp;&nbsp;
							<a href="${ctx}/logout" class="easyui-linkbutton" plain="false">退出登录</a>
						</td>
					    <td align=right width=268><IMG height=56 src="${ctx}/static/images/admin/header_right.jpg" width=268></td>
					</tr>
				</table>
			</shiro:user>
		</div>
		<div data-options="region:'west',split:true" title="功能导航" style="width:220px;">
			<div style="padding:5px 5px 5px 5px;overflow:auto;">
				<ul id="treemenu"></ul>
			</div>
		</div>
		<div data-options="region:'center'" style="overflow:hidden;">
			<div id="tab" class="easyui-tabs" fit="true" border="false"></div>
		</div>
		
		<div id="menu" class="easyui-menu" style="width:150px;">
		    <div id="m-refresh" iconCls="icon-reload">刷新</div>
		    <div class="menu-sep"></div>
		    <div id="m-close" iconCls="icon-no">关闭</div>
		    <div id="m-closeother" iconCls="icon-no">关闭其他</div>
		    <div id="m-closeall" iconCls="icon-no">关闭全部</div>
		</div>
		
		<script type="text/javascript">
		$(function(){
			$('#treemenu').tree({
				url: '${ctx}/loadTopMenus',
				onClick:function(node){
					if(node.attributes.forward != '') {
						addTab(node.text, "${ctx}" + node.attributes.forward);
					} else {
						$(this).tree('toggle', node.target); // 点节点展开树
					}
				},
				onBeforeExpand:function(node, param){
					$('#treemenu').tree('options').url = "${ctx}/loadChildMenus/"+node.id;
				}
			});
			
			// 刷新
		    $("#m-refresh").click(function(){
		        var currTab = $('#tab').tabs('getSelected'); //获取选中的标签项
		        var url = $(currTab.panel('options').content).attr('src'); //获取该选项卡中内容标签（iframe）的 src 属性
		        
		        /* 重新设置该标签 */
		        $('#tab').tabs('update',{
		            tab:currTab,
		            options:{
		                content: '<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:98%;height:98%;padding: 5px;"></iframe>'
		            }
		        })
		    });
		    
		    // 关闭所有
		    $("#m-closeall").click(function(){
		        $("#tab li").each(function(i, n){
		            var title = $(n).text();
		            $('#tab').tabs('close',title);
		        });
		    });
		    
		    // 除当前之外关闭所有
		    $("#m-closeother").click(function(){
		        var currTab = $('#tab').tabs('getSelected');
		        currTitle = currTab.panel('options').title;
		        
		        $("#tab li").each(function(i, n){
		            var title = $(n).text();
		            if(currTitle != title){
		                $('#tab').tabs('close',title);
		            }
		        });
		    });
		    
		    // 关闭当前
		    $("#m-close").click(function(){
		        var currTab = $('#tab').tabs('getSelected');
		        currTitle = currTab.panel('options').title;
		        $('#tab').tabs('close', currTitle);
			});
			
			//监听右键事件，创建右键菜单
            $('#tab').tabs({
                onContextMenu:function(e, title, index){
                    e.preventDefault();
                    if(index>=0){
                        $('#menu').menu('show', {
                            left: e.pageX,
                            top: e.pageY
                        });
                    }
                }
            });
		});
			
		// 添加TAB页
		function addTab(title, url){
		    if ($('#tab').tabs('exists', title)){
		        $('#tab').tabs('select', title); // 切换至已打开的tab
		        // 刷新已打开的tab
		        var currTab = $('#tab').tabs('getSelected'); //获取选中的标签项
		        var url = $(currTab.panel('options').content).attr('src'); //获取该选项卡中内容标签（iframe）的 src 属性
		        
		        /* 重新设置该标签 */
		        $('#tab').tabs('update',{
		            tab:currTab,
		            options:{
		                content: '<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:98%;height:98%;padding: 5px;"></iframe>'
		            }
		        })
		    } else {
		        var content = '<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:98%;height:98%;padding: 5px;"></iframe>';
		        $('#tab').tabs('add',{
		            title:title,
		            content:content,
		            closable:true
		        });
		    }
		}
		</script>
	</body>
</html>