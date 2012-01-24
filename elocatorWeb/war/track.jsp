<%@ page import="feri.rvir.elocator.dao.*"%>
<%@ page import="org.json.*"%>
<%@ page import="feri.rvir.elocator.rest.resource.user.*"%>
<%@ page import="feri.rvir.elocator.rest.resource.tracking.*"%>
<%@ page import="feri.rvir.elocator.rest.resource.location.*"%>
<%@ page import="java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%
UserDao udao = new UserDao();
List<User> users = udao.getAll(); 
JSONObject obj = new JSONObject();
LocationDao ldao = new LocationDao();
TrackingDao tdao = new TrackingDao();
List<Tracking> trackings = tdao.getTrackingsByUser(users.get(0).getKey());

List<Location> locations = null;

for (Tracking t:trackings) {
	User u = udao.getUser(t.getChild());
	
	JSONObject jsonu = new JSONObject();
	jsonu.put("username",u.getUsername());
	locations = ldao.getAllUserLocations(u.getKey());
	
	for (Location l : locations) {
		JSONObject loc = new JSONObject();
		loc.append("location",l.getLatitude() + "," + l.getLongitude());
		loc.append("time",l.getTimestamp().toString());
		jsonu.append("locobj",loc);
	}
	
	obj.append("children",jsonu);
}

System.out.println(obj.toString());
System.out.println("trackings " + trackings.size());
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script src="http://jquery.com/src/jquery-latest.js"></script>
<script src="script.js"></script>
<link rel="stylesheet" type="text/css" href="stil.css" />
<style type="text/css">
html {
	height: 100%
}

#map_canvas {
	height: 100%
}
</style>
<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBNI2AffTfDJrP4qBR9PNqtHrBNr3DEwPs&sensor=false">
	
</script>
<script type="text/javascript">
	var jsonA =
<%=obj.toString()%>
	
</script>
</head>
<body>
	<div id="meni">To je meni</div>
	<div id="levo">
		<%
			if (trackings.size() != 0) {
		%>
		<ul id="childs">
			<%
				for (Tracking t : trackings) {
						User temp = udao.getUser(t.getChild());
			%>
			<li><%=temp.getUsername()%></li>
			<%
				}
			%>
		</ul>
		<%
			} else {
				out.print("No tracked children");
			}
		%>

	</div>
	<div id="map_canvas" style="width: 70%; height: 80%"></div>
</body>
</html>