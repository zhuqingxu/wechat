<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%!
//设置全局变量
int count=0;
%>

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
              			<input type="text" class="form-control" id="search_para" style="height:70px;">
              			<span class="fdj"></span>
              			<span class="input-group-btn">
               				<button class="btn btn-default btn-ss" type="button" onclick="searchProduct()" >搜索</button>
              			</span>
            		</div>
            		<div class="modal-body" id="searchMsg" style="display: none;">
				    	<p style="color: red">请输入您要搜索的关键词</p>
				    </div>
            	</div>      
      		</div>
		</div>
	</div>
</div>
<c:if test="${showNav eq true}">
	<div class="container">
		<ul id="breadcrumb">
			<c:forEach var="category" items="${categorys}">
				<c:choose>
					<c:when test="${category.id == parentCategoryId}">
						<li><a class="active" href="${ctx}/${category.attributes.flag}"><span class="icon icon-home">${category.text}</span></a></li>
						<c:choose>
							<c:when test="${category.attributes.seq == 1}"><%count = 0; %></c:when>
							<c:when test="${category.attributes.seq == 2}"><%count = 1; %></c:when>
							<c:when test="${category.attributes.seq == 3}"><%count = 2; %></c:when>
							<c:when test="${category.attributes.seq == 4}"><%count = 3; %></c:when>
							<c:when test="${category.attributes.seq == 5}"><%count = 4; %></c:when>
							<c:when test="${category.attributes.seq == 6}"><%count = 5; %></c:when>
							<c:when test="${category.attributes.seq == 7}"><%count = 6; %></c:when>
							<c:when test="${category.attributes.seq == 8}"><%count = 7; %></c:when>
							<c:when test="${category.attributes.seq == 9}"><%count = 8; %></c:when>
							<c:when test="${category.attributes.seq == 10}"><%count = 9; %></c:when>
							<c:otherwise><%count = 0; %></c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<li><a href="${ctx}/${category.attributes.flag}"><span class="icon icon-beaker"></span>${category.text}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
	</div>
	<div class="container-fluid xiaolei-list mb20px">
		<div class="container ">
			<div class="row">
				<div class="col-md-offset-2 col-md-10">
					<ul id="concon">
						<c:forEach var="category" items="${categorys}">
							<li style="${category.id == parentCategoryId ? 'display:block;' : 'display: none;'}">
								<c:forEach var="childCategory" items="${category.children}">
									<a class="${childCategory.id == childCategoryId ? 'on' : ''}" href="${ctx}/fxsp_list/${childCategory.id}">${childCategory.text}</a>
								</c:forEach>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="container f16px text-center mb20px green">
		<c:choose>
			<c:when test="${categoryCount != null}">
				发布品类<c:if test="${categoryCount != null}">【${categoryCount}】</c:if>&nbsp;发布产品【${productCount}】
			</c:when>
			<c:otherwise>
				<c:if test="${navigation4 == null}">
					<a href="${ctx}/fxsp_list/${childCategoryId}">推荐【${productCount}】</a>
					<c:if test="${navigation3 != null}">
						&nbsp;&nbsp;|&nbsp;&nbsp;<a href="${ctx}/fxsp_list/db/${childCategoryId}">达标【${dbCount}】</a>
					</c:if>
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>
<div class="container mb10px">
	<h1>
		<a href="${ctx}/index">首页</a>
		<c:if test="${navigation0 != null}">
			&nbsp;>&nbsp;${navigation0}
		</c:if>
		<c:if test="${navigation1 != null}">
			&nbsp;>&nbsp;<a href="${ctx}/fxsp">${navigation1}</a>
		</c:if>
		<c:if test="${navigation2 != null}">
			&nbsp;>&nbsp;<a href="${ctx}/${navigation2Url}">${navigation2}</a>
		</c:if>
		<c:if test="${navigation3 != null}">
			<c:choose>
				<c:when test="${navigation3Url != null}">&nbsp;>&nbsp;<a href="${ctx}/${navigation3Url}">${navigation3}</a></c:when>
				<c:otherwise>&nbsp;>&nbsp;${navigation3}</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${navigation4 != null}">
			&nbsp;>&nbsp;${navigation4}
		</c:if>
	</h1>
</div>

<script type="text/javascript" src="${ctx}/static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(function() {
	var timeoutId = <%=count%>;
	var $breadcrumb = $("#breadcrumb");
	var $breadcrumbLi = $breadcrumb.find("li");
	var $concon = $("#concon");
	var $conconLi = $concon.find("li");

	var currIndex = $breadcrumbLi.find(".active").parent().index();//页面开始初始值
    $breadcrumbLi.mouseenter(function () {
		var $this = $(this);
		var index = $this.index();
		clearTimeout(timeoutId);
		$breadcrumbLi.find("a").removeClass("active");
		$this.find("a").addClass("active");
		$conconLi.hide();
		$($conconLi.get(index)).show();
    });

    $breadcrumb.mouseleave(mouseleaveHandler);
    $concon.mouseleave(mouseleaveHandler);

    function mouseleaveHandler() {
		timeoutId = setTimeout(function () {
			$breadcrumbLi.find("a").removeClass("active");
			$($breadcrumbLi.get(currIndex)).find("a").addClass("active");
			$conconLi.hide()
			$($conconLi.get(currIndex)).show();
		}, 400);
	}

    $concon.mouseenter(function () {
      clearTimeout(timeoutId);
    });
});

function searchProduct(){
	var para = $("#search_para").val();
	if(para.replace(/^\s+|\s+$/g, "")=='' || para==undefined || para==null){
		$("#searchMsg").show();
	}else{
		$("#searchMsg").hide();
		var url = "${ctx}/fxsp/search?para="+encodeURI(para);
		window.location.href=url;
	}
}
</script>