var coordinates = new Array();
var datetime = new Array();

$(document).ready(function() {
	$("#childs li").click(function() {
		var val = $(this).html();
		coordinates = [];
		datetime = [];
		$.each(jsonA.children, function(i, item) {
			if (val == item.username) {
				if (item.locobj != null) {
					locobj = item.locobj;
					for ( var x = 0; x < locobj.length; x++) {
						try {
							 coordinates.push(locobj[x].location);
							 datetime.push(locobj[x].time);
						} catch (e) {
							// alert("exception");
						}
					}
				}
			}
		});
		
		if (coordinates.length != 0) {
			initialize();
		}
		
	});

});

function initialize() {
	var strA = coordinates[0].toString().split(',');
	//var myLatlng = new google.maps.LatLng(-25.363882, 131.044922);
	var mylat = new google.maps.LatLng(parseFloat(strA[0]), parseFloat(strA[1]));

	var myOptions = {
		zoom : 10,
		center : mylat,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
	}
	var map = new google.maps.Map(document.getElementById("map_canvas"),
			myOptions);

	var marker1 = new google.maps.Marker({
		position : mylat,
		title : "Maribor"
	});
	marker1.setMap(map);
	
	if (coordinates.length > 1) {
		var marker2 = null;
		for (var x = 0; x < coordinates.length; x++) {
			var pointS = coordinates[x].toString().split(',');
			var newpos = new google.maps.LatLng(parseFloat(pointS[0]), parseFloat(pointS[1]));
			
			marker2 = new google.maps.Marker({
			position : newpos,
			title : datetime[x].toString()
			});
			marker2.setMap(map);
		}
	}
}