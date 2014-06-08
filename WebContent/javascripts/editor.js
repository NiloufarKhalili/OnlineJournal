
/**
 * Iman Rastkhadiv
 */
function toggleUserForm() {
	
	$("#toggle1").animate({
		position : 'fixed',
		higth : 'toggle'
	}, 1000, function() {

	});
	$("#toggle2").toggle(function() {
		$("toggle2").show();
	});
	

}

$(document).ready(
		function() {
			
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
			$('#message').click(function() {
				if ($('#messagediv').is('.control-group, .error')) {
					$('#messagediv').removeClass();
					$('span[id^="messagespan"]').remove();
					
				}
			});
			$('#title').click(function() {
				if ($('#titlediv').is('.control-group, .error')) {
					$('#titlediv').removeClass();
					$('span[id^="titlespan"]').remove();
					
				}
			});
			$('#aimdiv').click(function() {
				if ($('#aimdiv').is('.control-group, .error')) {
					$('#aimdiv').removeClass();
					$('span[id^="aimspan"]').remove();
					
				}
			});
			$('#guide').click(function() {
				if ($('#guidediv').is('.control-group, .error')) {
					$('#guidediv').removeClass();
					$('span[id^="guidespan"]').remove();
					
				}
			});

		});
function validateInvitationForm() {
	var email = $('#email').val();
	var firstname = $('#firstname').val();
	var lastname = $('#lastname').val();
	var message = $('#message');
	if (firstname === '' || firstname === null) {

		$('#firstnamediv').addClass('control-group error');
		$('#firstnamediv')
				.append(
						'<span id="firstnamespan" class="help-inline">Please enter firstname</span>');

		return false;

	}
	if (lastname === '' || lastname === null) {
		$('#lastnamediv').addClass('control-group error');
		$('#lastnamediv')
				.append(
						'<span id="lastnamespan" class="help-inline">Please enter lastname</span>');

		return false;

	}
	if (email === '' || email === null) {
		$('#emaildiv').addClass('control-group error');
		$('#emaildiv')
				.append(
						'<span id="emailspan" class="help-inline">Please enter an email address</span>');

		return false;
	}

	else if (!email.match("\\w+@\\w+\\.\\w+")) {
		$('#emaildiv').addClass('control-group error');
		$('#emaildiv')
				.append(
						'<span id="emailspan" class="help-inline">Please enter a valid email address</span>');

		return false;
	
	} else if (message === '' || message === null) {
		$('#messagediv').addClass('control-group error');
		$('#messagediv')
				.append(
						'<span id="messagespan" class="help-inline">Please enter a message</span>');

		return false;
	}
	return true;

}
function validateJournal() {
	var aim = $('#aim').val();
	var guide = $('#guide').val();
	var title = $('#title').val();
	var status = 0;
 if (aim === '' || aim === null) {
	$('#aimdiv').addClass('control-group error');
	$('#aimdiv')
			.append(
					'<span id="aimspan" class="help-inline">Please enter a message</span>');
	status = 1;
 }
 if (guide === '' || guide === null) {
	 $('#guidediv').addClass('control-group error');
	 $('#guidediv')
	 .append(
	 '<span id="guidespan" class="help-inline">Please enter a message</span>');
	 status = 1;
 }
 if (aim === '' || aim === null) {
	 $('#titlediv').addClass('control-group error');
	 $('#titlediv')
	 .append(
	 '<span id="titlespan" class="help-inline">Please enter a message</span>');
	 status = 1;
 }
 	if(status == 1){
 		return false;
 	}
	return true;

}
