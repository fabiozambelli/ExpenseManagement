<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expense Management</title>
<script>
function doUpload() {
	document.receiptUploader.submit();
}
</script>
</head>
<body>
	<h1>OCR for Receipt - Demo </h1>
	<p><a href="post.jsp">Post a receipt image</a></p>
	<p><a href="get.jsp">Get results</a></p> 	
	<h2>Usage</h2>
	<ul>
	<li>receipt image must be in portrait orientation</li>
	<li>once the receipt is uploaded wait few minute to get response. the scheduler process is not still optimized ;-)</li>
	</ul>
	
</body>
</html>