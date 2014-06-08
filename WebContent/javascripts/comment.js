/**
 * 
 */
function submitBtn() {
	var content = $("#content").val();
	var method = "POST";
	var urlString = "CommentServlet?reason_id=" + getUrlParam('reason_id')
			+ "&content=" + content;
	if (content == null || content == "") {
		alert("please input a content before submit !");
	} else {
	connect(method, urlString);
	}
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
			location.reload();
		}
	};	
}