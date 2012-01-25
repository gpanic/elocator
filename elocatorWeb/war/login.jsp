<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="feri.rvir.elocator.dao.*"%>
<%@page import="feri.rvir.elocator.rest.resource.user.*"%>
<%@page import="feri.rvir.elocator.util.*"%>
<%
	UserDao udao = new UserDao();
	User u = null;
	String error = null;
	if (request.getParameter("submit") != null) {
		if (!request.getParameter("username").equals("")
				&& !request.getParameter("password").equals("")) {

			u = udao.getUser(request.getParameter("username"));
			if (u == null) {
				error = "No user";
			} else {
				if (u.getPassword().equals(Crypto.hash(request.getParameter("password").toString(),"SHA-1"))) {
					session.setAttribute("key", u.getKey());
					response.sendRedirect("track.jsp");
				} else {
					error = "Invalid password or username";
				}
			}
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="stil.css" />
<title>Insert title here</title>

</head>
<body>
	<div id="login">
		<%
			if (error != null)
				out.println(error);
		%>
		<form method="post" action="login.jsp">
			<label>Username</label> <input type="text" name="username"> <label>Password
			</label> <input type="password" name="password"> <input type="submit"
				name="submit" value="Log in" />
		</form>
	</div>
</body>
</html>