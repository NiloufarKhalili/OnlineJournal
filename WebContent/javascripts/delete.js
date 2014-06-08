/**
 * 
 */

function deleteBtn( id, type){
	var urlString;
	var method;
	switch(type)
	{
	case "await":
		method="POST";
		urlString="JDBServlet?action=delete_select_await&article_id="+id;
//		alert(id);
		break;
	default:
		break;
	
	};
	if(typeof urlString === 'undefined'){
		   // your code here.
		alert("no url String!");
		 }
	else if(typeof method === 'undefined'){
		alert("no method String!");
	}
	else{
		connect(method,urlString);
	}
	;
}

function connect(method, url)
{
var http_request;
	
	try {
		http_request = new ActiveXObject('Msxml2.XMLHTTP');
	}
	catch (e) {
		try {
			http_request = new ActiveXObject('Microsoft.XMLHTTP');
		}
		catch (e2) {
			try {
				http_request = new XMLHttpRequest();
			}
			catch (e3) {
				http_request = false;
			}
		}
	}
	
	http_request = new XMLHttpRequest();
	
	http_request.open(method, url ,
			true);
	http_request.send();
	http_request.onreadystatechange = function() {
		if (http_request.readyState == 4) {
//			alert("success");
			location.reload();
		}
	};	
}