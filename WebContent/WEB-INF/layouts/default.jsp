<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>首页<sitemesh:title/></title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		
		<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
		
		<link href="${ctx}/static/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
		<link href="${ctx}/static/styles/user/fangxin.css" rel="stylesheet">
		<link href="http://apps.bdimg.com/libs/animate.css/3.1.0/animate.min.css" rel="stylesheet">
		<link href="${ctx}/static/styles/user/lrtk.css" type="text/css" rel="stylesheet">
		
		<sitemesh:head />
	
	</head>

	<body>
		<%@ include file="/WEB-INF/layouts/index-header.jsp"%>
		<sitemesh:body />
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
		<script src="${ctx}/static/jquery/jquery.min.js" type="text/javascript"></script>
		<script src="${ctx}/static/bootstrap/3.3.5/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="${ctx}/static/scripts/plugin.js" type="text/javascript"> </script>
		<script src="${ctx}/static/jquery/jquery.hammer-full.min.js" type="text/javascript"></script>
		<script src="${ctx}/static/scripts/lrtk.js" type="text/javascript"></script>
		<script src="${ctx}/static/scripts/scrollPic.js" type="text/javascript"></script>
		<script>
		window.onload = function(){
			scrollPic();
		}
		function scrollPic() {
			var scrollPic = new ScrollPic();
			scrollPic.scrollContId   = "scrollPic"; //内容容器ID
			scrollPic.arrLeftId      = "LeftArr";//左箭头ID
			scrollPic.arrRightId     = "RightArr"; //右箭头ID
		
			scrollPic.frameWidth     = 1000;//显示框宽度
			scrollPic.pageWidth      = 200; //翻页宽度
		
			scrollPic.speed          = 10; //移动速度(单位毫秒，越小越快)
			scrollPic.space          = 10; //每次移动像素(单位px，越大越快)
			scrollPic.autoPlay       = false; //自动播放
			scrollPic.autoPlayTime   = 3; //自动播放间隔时间(秒)
		
			scrollPic.initialize(); //初始化
		}
		</script>
		<script>
		var links = ["http://www.baidu.com/", "http://www.taobao.com/", "http://www.baidu.com/", "http://www.baidu.com/","http://www.baidu.com/","http://www.baidu.com/"];
		$(".slide").click(function(){
		    var index = $(this).attr('index');
			if(index === undefined) {
				window.open(links[0]);
			} else {
				window.open(links[(parseInt(index)+1)]);
			}
		
		});
		//lei动画效果
	   	$(".sy-leibie-list li .lei img").hover(
		 		function(){	$(this).addClass('animated flipInY');},
				function(){$(this).removeClass('flipInY');
				 $(this).addClass('animated flipInY');
				}
		);
		</script>
	</body>
</html>