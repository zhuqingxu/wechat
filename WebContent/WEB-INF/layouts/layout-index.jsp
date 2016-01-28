<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/meta.jsp"%>
		<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
		<title><sitemesh:title/></title>
		<sitemesh:head />
	</head>

	<body>
		<%@ include file="/WEB-INF/layouts/index-header.jsp"%>
		<sitemesh:body />
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</body>
</html>