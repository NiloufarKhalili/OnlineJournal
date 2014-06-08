function search() {
	alert("Currently there is no resources in the system\nplease try again later");
	return false;
}
function getMessage() {
	var message = document.getElementById("hiddenMessage").innerHTML;
	if (message.match("Invalid email or password!"
			|| "An email has been sent to you")) {
		alert(message);
		

	}
}
function validateLogin() {
	var email = document.forms["loginForm"]["email"].value;
	var password = document.forms["loginForm"]["password"].value;
	if (email == null || email == "") {
		alert("Email must be filled out!");
		return false;
	}
	if (password == null || password == "") {
		alert("Password must be filled out!");
		return false;
	}
	if (!email.match("\\w+@\\w+\\.\\w+")) {
		alert("Invalid email format!");
		return false;
	}
}
function validatesignup() {
	var email = document.forms["signup"]["email"].value;
	var password = document.forms["signup"]["password"].value;
	var confirmpassword = document.forms["signup"]["confirmpassword"].value;
	if (email == null || email == "") {
		document.getElementById("emailerror").innerHTML = "Email must be filled out!";
		return false;
	}
	if (!email.match("\\w+@\\w+\\.\\w+")) {
		alert("Invalid email format!");
		return false;
	}
	if (password == null || password == "") {
		document.getElementById("passworderror").innerHTML = "Password must be filled out!";
		return false;
	}
	if (password.length < 7) {
		document.getElementById("passworderror").innerHTML = "Password must be at least 7 characters!";
		return false;
	}
	if (confirmpassword == null || confirmpassword == "") {
		document.getElementById("confirmpassworderror").innerHTML = "Please confirm your password!";
		return false;
	}

	if (confirmpassword !== password) {
		document.getElementById("error").innerHTML = "Password do not matche!";
		return false;
	}

}
