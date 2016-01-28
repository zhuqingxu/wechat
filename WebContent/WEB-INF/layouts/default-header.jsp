<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
	<nav class="navbar navbar-default" role="navigation">
    	<div class="navbar-header nav_fangxin">
      		<button type="button" class="navbar-toggle " data-toggle="collapse" 
         		data-target="#example-navbar-collapse"> <span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      		<a class="navbar-brand" href="${ctx}/index"><img src="${ctx}/static/images/product/logo.png" /></a>
      	</div>
    	<div class="collapse navbar-collapse" id="example-navbar-collapse">
	      	<ul class="nav navbar-nav">
	        	<li><a href="${ctx}/index">首页</a></li>
				<li><a href="${ctx}/fxsp">白名单</a></li>
				<li><a href="${ctx}/zxbz">甄选标准</a></li>
				<li><a href="http://bbs.fangxin365.com">社区</a></li>
				<li><a href="${ctx}/zc">众筹</a></li>
				<li><a href="${ctx}/news">企业新闻</a></li>
	        	<li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"> 企业合作 <b class="caret"></b> </a>
		          	<ul class="dropdown-menu sy_dropdownmenu">
			            <li><a href="${ctx}/aqfj">安全分级</a></li>
						<li><a href="${ctx}/hzal">合作案例</a></li>
						<li><a href="${ctx}/fwnl">服务能力</a></li>
		          	</ul>
	        	</li>
	        	<li><a href="${ctx}/about">关于我们</a></li>
		        <li>
		        	<div class="search"><a href="javascript:;" data-toggle="modal" data-target="#myModal"><img src="${ctx}/static/images/product/fdj.png" /></a></div>
		        </li>
	      	</ul>
    	</div>
  	</nav>
</div>
<div class="modal fade" id="myModal">
	<div class="modal-dialog sy-modal-dialog" style="margin-top:85px;">
    	<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="close sy-close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      		</div>
      		<div class="container modal-body sy-modal-body">
				<div class="search_box" >
            		<div class="input-group search_sbox">
            			<form action="${ctx}/search" method="post">
	              			<input type="text" class="form-control" style="height:70px;" placeholder="输入大米、小米等">
	              			<span class="fdj"></span>
	              			<span class="input-group-btn">
	               				<button class="btn btn-default btn-ss" type="button">搜索</button>
	              			</span>
              			</form>
            		</div>
            	</div>      
      		</div>
		</div>
	</div>
</div>