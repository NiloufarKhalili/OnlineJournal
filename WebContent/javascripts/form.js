var numberOfCriticism = 0;
var numberOfErrors = 0;
var numberOfTitles = 0;
var form_id ;
var count;

// TODO values to areas when the window is loaded
window.onload = function() {
	count = 0;
};

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);  
    if (r != null) return unescape(r[2]); return null; 
}

function addCriticism() {

	$('.criticism')
			.append(
					'<input type="text" value="Title" class="title" id="'
							+ numberOfTitles
							+ '"/><br/><textarea class="class1" name="criticism" rows="5" cols="10" id="c'
							+ numberOfCriticism + '">Summary</textarea><br/>');
	$('#myCriticism')
			.html(
					'<span class="add-on">Add more criticism<i class="icon-plus"></i></span>');

	// $('#c'+numberOfCriticism).attr('name','c'+numberOfCriticism);
	// $('#'+numberOfTitles).attr('name','t'+numberOfTitles);
	// $("input[id=hiddenc]").html(numberOfCriticism);
	numberOfCriticism += 1;
	numberOfTitles += 1;

}
function addErrors() {

	$('.errors')
			.prepend(
					'<textarea class="errorClass" id="error'
							+ numberOfErrors
							+ '" name="errors" rows="2" cols="5">Error</textarea></br>');
	// $('.errorClass').attr('id','errors'+numberOfErrors);
	// $('#errors'+numberOfErrors).attr('name','e'+numberOfErrors);
	// //$('#hiddene').val(numberOfErrors);
	// $("input[id=hiddene]").html(numberOfErrors);

	numberOfErrors += 1;

}

function createForm(path) {
	// json array for criticism
	var jsonArr_criticism = [];
	for (var i = 0; i < numberOfCriticism; i++) {
		var mytitle = $('input#' + i).val();
		var mycontent = $('textarea#c' + i).val();

		jsonArr_criticism.push({
			id : i,
			title : mytitle,
			content : mycontent
		});
	}
	;

	// json array for error
	var jsonArr_error = [];
	for (var i = 0; i < numberOfErrors; i++) {
		var myerror = $('textarea#error' + i).val();

		jsonArr_error.push({
			error : myerror
		});
	}
	;
	var type = $("#status").val();
	var overall = $("#overall").val();
	var level = $("#level").val();
	var secret = $("#secret").val();
	var summary = $("#summary").val();
	var send_message =$("#secret_message").prop('checked');
	var article_id = getUrlParam('article_id');
	// form the json
	var json = {
		type : type,
		article_id : article_id,
		overall : overall,
		level : level,
		summary : summary,
		secret : secret,
		send_message : send_message,
		criticism : jsonArr_criticism,
		error : jsonArr_error
	};

	var jsonString = JSON.stringify(json);
//	alert(jsonString);
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
	
	http_request.open("POST", "ReviewFormServlet?json=" + jsonString ,
			true);
	http_request.send();
	http_request.onreadystatechange = function() {
		if (http_request.readyState == 4) {
			window.location.replace(path+"/JDBServlet?action=approved_article");
		}
	};
	//TODO send request to servlet; save to database; redirect to a new page
}

function KeepCount() {
	var count_temp = count;
	count_temp+=1;
	if (count_temp == 4)
	{
		alert('Pick Just Three Please');
		return false;
	}
	else{
		count = count_temp;
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
//			alert("success");
			location.reload();
		}
	};	
}

function download(id,href)
{
	connect("POST","JDBServlet?atction=download&article_id="+id);
	var win = window.open(href, '_blank');
    win.focus();
};

function approveComment(id)
{
	connect("POST","JDBServlet?action=approve_comment_reason&article_id="+getUrlParam('article_id')+"&reason_id="+id);
};

function approveError(mistake_id)
{
	connect("POST","JDBServlet?action=approve_mistake&article_id="+getUrlParam('article_id')+"&mistake_id="+mistake_id);
};
