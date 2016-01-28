<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
		<title><sitemesh:title/></title>
		<sitemesh:head />
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		  <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
		<%@ include file="/WEB-INF/layouts/default-header.jsp"%>
		<sitemesh:body />
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</body>
</html>