function checkNomeCognome(inputtxt) {
	var name = /^[A-Za-z]+$/;
	if(inputtxt.value.trim().match(name)) 
		return true;

	return false;	
}

function checkUsername(inputtxt) {
	var username = /^[A-Za-z0-9]+$/;
	if(inputtxt.value.trim().match(username)) 
		return true;

	return false;	
}


function checkPassword(inputtxt) {
	var pw = /^.*(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@!#$%-/=^_{}~+]).*$/;
	if(inputtxt.value.trim().match(pw) && inputtxt.value.trim().match(/^[A-Za-z0-9@!#$%-/=^_{}~+]+$/)) 
		return true;

	return false;	
}



