/**
 * Iman Rastkhadiv
 */
$(document).ready(
		function() {
			$('.nav-tabs li').on(
					'click',
					function(event) {
						$('.nav-tabs li').removeClass('active'); // remove
																	// active
						// class from tabs
						$(this).addClass('active'); // add active class to
													// clicked tab
						var v = $('.active').attr('id');
						if (v === 'keyword') {
							$("#input")
									.attr("placeholder", "eg key1,key2,key3");
						} else if (v === 'title') {
							$("#input").attr("placeholder", "eg Article1");

						} else if (v === 'author') {
							$("#input").attr("placeholder",
									"eg author firstname or lastname");
						}

					});
			$('#search').on(
					'click',
					function(event) {
						var v = $('.active').attr('id');

						if (v === 'date') {
							$("#search").attr(
									"href",
									$('#hidden').val()+"/ReaderController?action=" + v + "&from="
											+ $('#datepicker1').val() + "&to="
											+ $('#datepicker2').val());

						} else {
							$("#search").attr(
									"href",
									"ReaderController?action=" + v + "&value="
											+ $('#input').val());

						}
					});

			$("#datepicker1").datepicker({
				changeMonth : true,
				changeYear : true
			});
			$("#datepicker2").datepicker({
				changeMonth : true,
				changeYear : true
			});

			$("#forth").click(function() {
				$("#main").css("display", "none");
				$("#date-search").css("display", "");
			});

			$("#first").click(function() {
				$("#main").css("display", "");
				$("#date-search").css("display", "none");

			});
			$("#second").click(function(){
				$("#date-search").css("display", "none");
				$("#main").css("display", "");
			});
			$("#third").click(function(){
				$("#date-search").css("display", "none");
				$("#main").css("display", "");
			});

//			$('#email').click(function() {
//				if ($('#emaildiv').is('.control-group, .error')) {
//					$('#emaildiv').removeClass();
//					$('#emaildiv').find('span').remove();
//
//				}
//			});
			$('#email').click(function() {
				if ($('#emaildiv').is('.control-group, .error')) {
					$('#emaildiv').removeClass();
					$('span[id^="emailspan"]').remove();

				}
			});
			$('#title').click(function() {
				if ($('#titlediv').is('.control-group, .error')) {
					$('#emaildiv').removeClass();
					$('span[id^="titlespan"]').remove();
					
				}
			});
//			$('#title').click(function() {
//				if ($('#titlediv').is('.control-group, .error')) {
//					$('#titlediv').removeClass();
//					$('#titlediv').find('span').remove();
//
//				}
//			});
//			$('#comment').click(function() {
//				if ($('#commentdiv').is('.control-group, .error')) {
//					$('#commentdiv').removeClass();
//					$('#commentdiv').find('span').remove();
//
//				}
//			});
			$('#comment').click(function() {
				if ($('#commentdiv').is('.control-group, .error')) {
					$('#commentdiv').removeClass();
					$('#commentdiv').find('commentspan').remove();
					
				}
			});

		});

function validateComment() {
	var email = $('#email').val();
	var title = $('#title').val();
	var comment = $('#comment').val();
	if (email === '' || email === null) {
		$('#emaildiv').addClass('control-group error');
		$('#emaildiv')
				.append(
						'<span id="emailspan" class="help-inline">Please enter your email address</span>');

		return false;
	}

	else if (!email.match("\\w+@\\w+\\.\\w+")) {
		$('#emaildiv').addClass('control-group error');
		$('#emaildiv')
				.append(
						'<span id="emailspan" class="help-inline">Please enter a valid email address</span>');

		return false;
	} else if (title === '' || title === null) {
		$('#titlediv').addClass('control-group error');
		$('#titlediv')
				.append(
						'<span id="titlespan" class="help-inline">Please enter a title</span>');

		return false;
	} else if (comment === '' || comment === null) {
		$('#commentdiv').addClass('control-group error');
		$('#commentdiv')
				.append(
						'<span id="commenspan" class="help-inline">Please enter a comment</span>');

		return false;
	}
	return true;

}
