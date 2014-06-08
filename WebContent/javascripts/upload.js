/**
 * author Iman Rastkhdiv
 */
var numberOfFirstname = 0;
var numberOfLastname = 0;
var numberOfEmail = 0;
var numberOfAffiliation = 0;
var numberOfAuthors = 0;
var count = 0;

function addAuthor() {

	count += 1;
	

	if (count >= 1) {
		$('#remove-author-btn').removeAttr('disabled');

	}

	$('.authors')
			.append(
					'<div id='
							+ count
							+ '><div id="fdiv'+count+'"><div style="width:200px;display:inline-block"><b>Firstname:</b> </div><div class="input-prepend" style="width:250px;display:inline-block"><span class="add-on"><i class="icon-user"></i></span> <input type="text" size="30" name="f'
							+ numberOfFirstname
							+ '" placeholder="Firstname" id="'
							+ numberOfFirstname
							+ '" /></div></div><div id="ladiv'+count+'"><div style="width:200px;display:inline-block"><b>Lastname:</b> </div><div class="input-prepend" style="width:250px;display:inline-block"><span class="add-on"><i class="icon-user"></i></span> <input type="text" size="30" name="la'
							+ numberOfLastname
							+ '" id="'
							+ numberOfLastname
							+ '" placeholder="Lastname" /></div></div><div id="ediv'+count+'"><div style="width:200px;display:inline-block"><b>Email:</b> </div><div class="input-prepend" style="width:250px;display:inline-block"><span class="add-on"><i class="icon-envelope"></i></span> <input type="text" size="30" name="e'
							+ numberOfEmail + '" placeholder="Email" id="'
							+ numberOfEmail + '" /></div></div><div id="adiv'+count+'"><div  style="width:200px;display:inline-block"><b>Affiliation:</b> </div><div class="input-prepend" style="width:250px;display:inline-block"><span class="add-on"><i class="icon-pencil"></i></span> <input placeholder="Affiliation" type="text" size="30" name="a'
							+ numberOfAffiliation
							+ '" id="'
							+ numberOfAffiliation + '" /></div></div><br/></div>');

//	$('#author')
//			.html(
//					'<span class="add-on">Add more authors<i class="icon-plus"></i></span>');
	$('#f' + numberOfFirstname).attr('name', 'f' + numberOfFirstname);
	$('#la' + numberOfLastname).attr('name', 'la' + numberOfLastname);
	$('#e' + numberOfEmail).attr('name', 'e' + numberOfEmail);
	$('#a' + numberOfAffiliation).attr('name', 'a' + numberOfAffiliation);
	$('#hiddenc').val(count);

	numberOfFirstname += 1;
	numberOfLastname += 1;
	numberOfEmail += 1;
	numberOfAffiliation +=1;
	numberOfAuthors = count;

}
function removeAuthor() {
	if(count==0)
		return;

	$('.authors').find('#' + count).remove();
	count -= 1;
	$('#hiddenc').val(count);
	if (count <= 0) {
		$('#remove-author-btn').attr('disabled', 'disabled');
		return;

	}
	
}

function validate() {
	var email = $('#email').val();
	var firstname = $('#firstname').val();
	var lastname = $('#lastname').val();
	var abs = $('#abs').val();
	var title = $('#title').val();
	var keywords = $('#keywords').val();
	var file = $('#file').val();
	var affiliation = $('#affiliation').val();
	var status = 0;
	
	if (firstname === '' || firstname === null) {
		$('#firstnamediv').addClass('control-group error');
		$('#firstnamediv')
				.append(
						'<span id="firstnamespan" class="help-inline">Please enter your firstname</span>');
		status = 1;
	}
	if (lastname === '' || lastname === null) {
		$('#lastnamediv').addClass('control-group error');
		$('#lastnamediv')
				.append(
						'<span id="lastnamespan" class="help-inline">Please enter your lastname</span>');
		status = 1;
	}
	if (email === '' || email === null) {
		$('#emaildiv').addClass('control-group error');
		$('#emaildiv')
				.append(
						'<span id="emailspan" class="help-inline">Please enter your email address</span>');

		status = 1;
	}

	else if (!email.match("\\w+@\\w+\\.\\w+")) {
		$('#emaildiv').addClass('control-group error');
		$('#emaildiv')
				.append(
						'<span id="emailspan" class="help-inline">Please enter a valid email address</span>');

		status = 1;
	}
	if (affiliation == '' || affiliation === null) {
		$('#affiliationdiv').addClass('control-group error');
		$('#affiliationdiv')
				.append(
						'<span id="affiliationspan" class="help-inline">Please enter your affiliation</span>');

		status = 1;
	}
	if (title === '' || title === null) {
		$('#titlediv').addClass('control-group error');
		$('#titlediv')
				.append(
						'<span id="titlespan" class="help-inline">Please enter Title</span>');

		status = 1;
	}

	
	if (abs === '' || abs === null || $('#abs').val().split(' ').length > 250) {
		$('#absdiv').addClass('control-group error');
		$('#absdiv')
				.append(
						'<span id="absspan" class="help-inline">Please enter max 250 words</span>');
		status = 1;

	}
	if (keywords === '' || keywords === null || $('#keywords').val().split(' ').length > 1) {
		$('#keywordsdiv').addClass('control-group error');
		$('#keywordsdiv')
				.append(
						'<span id="keywordsspan" class="help-inline">Please enter Max 10 keywords</span>');
		status = 1;
	}
	
	if (!file.match("\.+.pdf")) {
		$('#filediv').addClass('control-group error');
		$('#filediv')
				.append(
						'<span id="filespan" class="help-inline">Please upload a pdf file</span>');

		status = 1;
	}
	
//	for(var i= 0 ; i < count;i++) {
//		f = $("input[name=f"+i+"]").val();
//		la = $("input[name=la"+i+"]").val();
//		e = $("input[name=e"+i+"]").val();
//		a = $("input[name=a"+i+"]").val();
//		if(validateInput(f) == 1){
//			$("#fdiv"+count).addClass('control-group error');
//			$("#fdiv"+count)
//					.append(
//							'<span id="fspan'+count+'" class="help-inline">Please enter your first name</span>');
//			status = 1;
//		
//		}
//		if(validateInput(la) == 1){
//			$("#ladiv"+count).addClass('control-group error');
//			$("#ladiv"+count)
//			.append(
//			'<span id="laspan'+count+'" class="help-inline">Please enter your last name</span>');
//			status = 1;
//			
//		}
//		if(validateInput(e) == 1){
//			$("#ediv"+count).addClass('control-group error');
//			$("#ediv"+count)
//			.append(
//			'<span id="espan'+count+'" class="help-inline">Please enter a valid email address</span>');
//			status = 1;
//			
//		}
//		if(validateInput(a) == 1){
//			$("#adiv"+count).addClass('control-group error');
//			$("#adiv"+count)
//			.append(
//					'<span id="aspan'+count+'" class="help-inline">Please enter an affiliation</span>');
//			status = 1;
//			
//		}
//		status = validateInput(e);
//		status = validateInput(f);
//		status = validateInput(la);
//		status = validateInput(a);
	//}
	
	
	
	
	if (status == 1) {
		return false;
	}
	return true;

}

$(document).ready(function() {

	$('#email').click(function() {
		if ($('#emaildiv').is('.control-group, .error')) {
			$('#emaildiv').removeClass();
			$('span[id^="emailspan"]').remove();

		}
	});
	$('#firstname').click(function() {
		if ($('#firstnamediv').is('.control-group, .error')) {
			$('#firstnamediv').removeClass();
			$('span[id^="firstnamespan"]').remove();
		}
	});
	$('#lastname').click(function() {
		if ($('#lastnamediv').is('.control-group, .error')) {
			$('#lastnamediv').removeClass();
			$('span[id^="lastnamespan"]').remove();

		}
	});
	$('#title').click(function() {
		if ($('#titlediv').is('.control-group, .error')) {
			$('#titlediv').removeClass();
			$('span[id^="titlespan"]').remove();
		}
	});
	$('#abs').click(function() {
		if ($('#absdiv').is('.control-group, .error')) {
			$('#absdiv').removeClass();
			$('span[id^="absspan"]').remove();
		}
	});
	$('#keywords').click(function() {
		if ($('#keywordsdiv').is('.control-group, .error')) {
			$('#keywordsdiv').removeClass();
			$('span[id^="keywordsspan"]').remove();
		}
	});
	$('#file').click(function() {
		if ($('#filediv').is('.control-group, .error')) {
			$('#filediv').removeClass();
			$('span[id^="filespan"]').remove();
		}
	});
	$('#affiliation').click(function() {
		if ($('#affiliationdiv').is('.control-group, .error')) {
			$('#affiliationdiv').removeClass();
			$('span[id^="affiliationspan"]').remove();
		}
	});
	$('#abs').keyup( function() {
	    var words = $('#abs').val().split(' ');
	    if(words.lenth > 250){
	    ('#abs').css('disable','true');
	    }
	});
	$('#keywords').keyup( function() {
		var words = $('#keywords').val().split(' ');
		if(words.length > 1) {
			if (!$('#keywordsdiv').is('.control-group, .error')) {

			$('#keywordsdiv').addClass('control-group error');
			$('#keywordsdiv input').val("");
			$('#keywordsdiv')
			.append(
					'<span id="keywordsspan" class="help-inline">Please enter Max 10 keywords</span>');
					
		}}
		
		//alert(words.length)
		//('#abs').css('disable','true');
	});
	
		
//		$("e"+0).click(function() {
//			alert("hello");
//			if ($("ediv"+i).is('.control-group, .error')) {
//				$("ediv"+i).removeClass();
//				//$("#espan"+i).remove();
//				//$('span[id^="espan'+i+'"]').remove();
//				$('span[id^="espan0'+i+'"]').remove();
//
//			}
//		});
//		$('#firstname').click(function() {
//			if ($('#firstnamediv').is('.control-group, .error')) {
//				$('#firstnamediv').removeClass();
//				$('span[id^="firstnamespan"]').remove();
//			}
//		});
//		$('#lastname').click(function() {
//			if ($('#lastnamediv').is('.control-group, .error')) {
//				$('#lastnamediv').removeClass();
//				$('span[id^="lastnamespan"]').remove();
//
//			}
//		});
//		
//		
//		
//		
//		
//		
//		$('#affiliation').click(function() {
//			if ($('#affiliationdiv').is('.control-group, .error')) {
//				$('#affiliationdiv').removeClass();
//				$('span[id^="affiliationspan"]').remove();
//			}
//		});
//		
	


});

// function addAuthor(){
// var numberOfAuthors = $('.authors-containor').children().size();
// generateAuthors(numberOfAuthors);
// if(numberOfAuthors > 0){
// $('#remove-author-btn').removeAttr('disabled');
// }
// };
//
// function removeAuthor(){
// var numberOfAuthors = $('.authors-containor').children().size();
// $('.authors-containor').children().last().remove();
// if(numberOfAuthors == 2){
// $('#remove-author-btn').attr('disabled','disabled');
// }
// };
//
// function generateAuthors(numberOfAuthors) {
// var nextId = numberOfAuthors +1;
// $('.authors-containor').append(generateAuthorsField(nextId));
//
// };
//
// function generateAuthorsField(id){
// var authorHtml="<div class='author span3'>";
// authorHtml +=generateAuthorField(id,"firstname","First Name","user");
// authorHtml +=generateAuthorField(id,"lastname","Last Name","user");
// authorHtml +=generateAuthorField(id,"email","Email","envelope");
// authorHtml +=generateAuthorField(id,"affiliation","Affiliation","pencil");
// authorHtml +="</div>";
// return authorHtml;
// }
//
// function generateAuthorField(id,name,text,iconname){
// var testHTML = '<div class="control-group">\
// <label class ="control-label" for="article-'+name+'-'+id+'">'+text+'</label>\
// <div class="controls input-prepend">\
// <span class="add-on"><i class="icon-'+iconname+'"></i></span>\
// <input type="text" id="article-'+name+'-'+id+'
// name="article-'+name+'-'+id+'>\
// <span class="help-inline hide">Something may have gone wrong</span>\
// </div></div>';
// return testHTML;
// };
//function validateInput(input) {
//	if(input == null || input == ''){
//		return 1;
//	}
//	return 0;
//};


