/**
 * 
 */
var parsed_user;
var current_user_firstname;
var current_user_lastname;


window.onload = function() {
	console.log("HTML Loaded");
	getEmployee();
	loadNavbar();
	loadEmployeeWelcome();
}


function loadNavbar() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Navbar.");
			document.getElementById("navbar").innerHTML = xhr.responseText;
			document.getElementById("submitRequest").addEventListener('click', loadRequestForm, false);
			document.getElementById("viewMyPendingRequests").addEventListener('click', loadPendingRequests, false);
			document.getElementById("viewMyResolvedRequests").addEventListener('click', loadResolvedRequests, false);
			document.getElementById("viewPersonalInfo").addEventListener('click', viewPersonalInfo, false);
			document.getElementById("updatePersonalInfo").addEventListener('click', updatePersonalInfo, false);
			$('#resolveRequests').css('display', 'none');
			$('#viewAllRequests').css('display', 'none');
			$('#viewAllEmployees').css('display', 'none');
			$('#viewPendingRequests').css('display', 'none');
			$('#viewResolvedRequests').css('display', 'none');
			$('#viewAllEmployees').css('display', 'none');
			$('#registerEmployee').css('display', 'none');
			$('#home').attr('href', 'employeehome.html');
		}
	}
	xhr.open("GET", "ajaxNavbar", true);
	xhr.send();
}


function loadEmployeeWelcome() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Manager Welcome.");
			document.getElementById("mainview").innerHTML = xhr.responseText;
			$('#employeename').text("Welcome, " + current_user_firstname + " " + current_user_lastname);
		}
	}
	xhr.open("GET", "employeeWelcome", false);
	xhr.send();
}


function getEmployee() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Getting Employee Info");
			console.log(xhr.responseText);
			parsed_user = JSON.parse(xhr.responseText);
			console.log(parsed_user)
			current_user_firstname = parsed_user["firstname"];
			current_user_lastname = parsed_user["lastname"];
			$('#employeename').text("Welcome, " + current_user_firstname + " " + current_user_lastname);
		}
	}
	xhr.open("GET", "getEmployeeInfo", false);
	xhr.send();
}


function viewPersonalInfo() {
	$("#mainview").empty();
	$("#mainview").append('<table class="table" id="reimb"></table>');
	$("#reimb").append("<thead id='reim-head'>");
	$("#reim-head").append("<th>Name</th>");
	$("#reim-head").append("<th class='info-name'>" + parsed_user["firstname"] + " " + parsed_user["lastname"] +"</th>");
	$("#reim-head").append("</thead>");
	$("#reimb").append("<tbody id='reim-body'>");
	$("#reim-body").append('<tr id="table-row">')
	$("#reim-body").append('<th scope="row">ID</th>');
	$("#reim-body").append('<td class="info-data">'+parsed_user["user_id"]+'</td>');
	$("#reim-body").append('</tr>');
	$("#reim-body").append('<tr id="table-row">')
	$("#reim-body").append('<th scope="row">Email</th>');
	$("#reim-body").append('<td class="info-data">'+parsed_user["email"]+'</td>');
	$("#reim-body").append('</tr>');
	$("#reim-body").append('</tr>');
	$("#reim-body").append('<tr id="table-row">')
	$("#reim-body").append('<th scope="row">Password</th>');
	$("#reim-body").append('<td class="info-data">'+parsed_user["password"]+'</td>');
	$("#reim-body").append('</tr>');
	$("#reim-body").append('<tr id="table-row">')
	$("#reim-body").append('<th scope="row">User Role</th>');
	$("#reim-body").append('<td class="info-data">Employee</td>');
	$("#reim-body").append('</tr>');
}


function updatePersonalInfo() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById("mainview").innerHTML = xhr.responseText;
			$('#firstname').attr('value', parsed_user["firstname"]);
			$('#lastname').attr('value', parsed_user["lastname"]);
			$('#email').attr('value', parsed_user["email"]);
			$('#password').attr('value', parsed_user["password"]);
		}
	}
	xhr.open("GET", "employeeUpdateForm", true);
	xhr.send();
}


function loadRequestForm() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById("mainview").innerHTML = xhr.responseText;
		}
	}
	xhr.open("GET", "getRequestForm", true);
	xhr.send();
}


var jsonresponse_pending;
function loadPendingRequests() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Pending Requests.");
			jsonresponse_pending = JSON.parse(xhr.responseText);
			$("#mainview").empty();
			$("#mainview").append('<table class="table" id="reimb"></table>');
			$("#reimb").append("<thead id='reim-head'>");
			$("#reim-head").append("<th>Status</th>");
			$("#reim-head").append("<th>Employee</th>");
			$("#reim-head").append("<th>Amount</th>");
			$("#reim-head").append("<th>Type</th>");
			$("#reim-head").append("<th>Description</th>");
			$("#reim-head").append("</thead>");
			$("#reimb").append("<tbody id='reim-body'>");
			var i;
			for(i = 0; i < jsonresponse_pending.length; i++) {
				console.log(jsonresponse_pending[i]);
				if(jsonresponse_pending[i]["status"] == "PENDING" &&
						jsonresponse_pending[i]["auth_last_name"] == parsed_user["lastname"]) {
					$("#reim-body").append('<tr id="table-row">')
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["status"] + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["auth_first_name"] + " " + jsonresponse_pending[i]["auth_last_name"] + "</td>");
					$("#reim-body").append("<td>$"+ insertDecimal(jsonresponse_pending[i]["amount"]) + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["type"] + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["description"] + "</td>");
					$("#reim-body").append("</tr>");
				}	
			}
			$("#reimb").append("</tbody>");
		}
	}
	xhr.open("GET", "getAllReimbursements", true);
	xhr.send();
}


var jsonresponse_pending;
function loadResolvedRequests() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Pending Requests.");
			jsonresponse_pending = JSON.parse(xhr.responseText);
			$("#mainview").empty();
			$("#mainview").append('<table class="table" id="reimb"></table>');
			$("#reimb").append("<thead id='reim-head'>");
			$("#reim-head").append("<th>Status</th>");
			$("#reim-head").append("<th>Employee</th>");
			$("#reim-head").append("<th>Amount</th>");
			$("#reim-head").append("<th>Type</th>");
			$("#reim-head").append("<th>Description</th>");
			$("#reim-head").append("</thead>");
			$("#reimb").append("<tbody id='reim-body'>");
			var i;
			for(i = 0; i < jsonresponse_pending.length; i++) {
				console.log(jsonresponse_pending[i]);
				if(jsonresponse_pending[i]["status"] != "PENDING" &&
						jsonresponse_pending[i]["auth_last_name"] == parsed_user["lastname"]) {
					$("#reim-body").append('<tr id="table-row">')
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["status"] + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["auth_first_name"] + " " + jsonresponse_pending[i]["auth_last_name"] + "</td>");
					$("#reim-body").append("<td>$"+ insertDecimal(jsonresponse_pending[i]["amount"]) + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["type"] + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["description"] + "</td>");
					$("#reim-body").append("</tr>");
				}	
			}
			$("#reimb").append("</tbody>");
		}
	}
	xhr.open("GET", "getAllReimbursements", true);
	xhr.send();
}


function insertDecimal(n) {
	   return (n).toFixed(2);
	}