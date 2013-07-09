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
	<h1>Upload a receipt</h1>
 	
	<form name="receiptUploader" action="/expensemanagement/rest/receipt/upload" method="post" enctype="multipart/form-data"> 	   
	   
	   <p>
	   id-user : <input type="text" name="id-user" value="mario.rossi" readonly/>
	   </p>
	   <p>
	   tag : <input type="text" name="tag" value="pranzo"/>
	   </p>
	   <p>
		Select a receipt : <input type="file" name="file" size="45" />
	   </p>
 		
	   <input type="submit" value="Upload It" />
	</form>
 
	<p><a href="index.jsp">Back to home</a></p>
</body>
</html>