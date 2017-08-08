/**
 * 
 */

var current_user; // Variable to hold the JSON User object returned from the getEmployee() function.
var all_users; // Variable holding all JSON User objects returned from the getAllEmployees() function.
var current_user_firstname; // Holds the first name of the current_user object.
var current_user_lastname; // Holds the last name of the current_user object.
var recent;

window.onload = function() {
	console.log("HTML Loaded");
	
	getEmployee();
	loadNavbar();
	if(recent == "undefined") {
		loadManagerWelcome();
	}else{
		recent = getMostRecent();
		console.log("most recent " + recent);
		window[recent]();
	}
}


function loadManagerWelcome() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Manager Welcome.");
			$("#option").empty();
			document.getElementById("mainview").innerHTML = xhr.responseText;
			$('#managername').text("Welcome, " + current_user_firstname + " " + current_user_lastname);
		}
	}
	xhr.open("GET", "managerWelcome", true);
	xhr.send();
	setMostRecent('loadManagerWelcome');
}


function loadNavbar() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Navbar.");
			document.getElementById("navbar").innerHTML = xhr.responseText;
			document.getElementById("viewAllRequests").addEventListener('click', loadAllRequests, false);
			document.getElementById("viewPendingRequests").addEventListener('click', loadPendingRequests, false);
			document.getElementById("viewAllEmployees").addEventListener('click', viewAllEmployees, false);
			document.getElementById("registerEmployee").addEventListener('click', loadRegistrationForm, false);
			document.getElementById("viewResolvedRequests").addEventListener('click', loadResolvedRequests, false);
			document.getElementById("home").addEventListener('click', loadManagerWelcome, false);
			document.getElementById("logout-nav").addEventListener('click', clearMostRecent, false);
			$('#viewPersonalInfo').css('display', 'none');
			$('#updatePersonalInfo').css('display', 'none');
			$('#viewRequests').css('display', 'none');
			$('#viewMyPendingRequests').css('display', 'none');
			$('#viewMyResolvedRequests').css('display', 'none');
			$('#submitRequest').css('display', 'none');
		}
	}
	xhr.open("GET", "ajaxNavbar", true);
	xhr.send();
}


function loadRegistrationForm() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Registration Form.");
			document.getElementById("mainview").innerHTML = xhr.responseText;
			
		}
	}
	xhr.open("GET", "ajaxRegistrationForm", true);
	xhr.send();
}


function getEmployee() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Getting Manager Info");
			console.log(xhr.responseText);
			current_user = JSON.parse(xhr.responseText);
			current_user_firstname = current_user["firstname"];
			current_user_lastname = current_user["lastname"];
			$('#managername').text("Welcome, " + current_user_firstname + " " + current_user_lastname);
		}
	}
	xhr.open("GET", "getEmployeeInfo", true);
	xhr.send();
}


function getAllEmployees() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Getting All Employees");
			console.log(xhr.responseText);
			all_users = JSON.parse(xhr.responseText);
			console.log("Length of all_users: " + all_users.length);
		}
	}
	xhr.open("GET", "getAllUsers", false);
	xhr.send();
}


function viewAllEmployees(){
	getAllEmployees();
	setMostRecent('viewAllEmployees');
	$("#option").empty();
	$("#mainview").empty();
	$("#mainview").append('<h1 class="white">Employees</h1>');
	$("#mainview").append('<table class="table" id="users"></table>');
	$("#users").append("<thead id='users-head'>");
	$("#users-head").append("<th>ID</th>");
	$("#users-head").append("<th>Name</th>");
	$("#users-head").append("<th>Email</th>");
	$("#users-head").append("<th>Requests</th>");
	$("#users-head").append("</thead>");
	$("#users").append("<tbody id='users-body'></tbody>");
	for(i = 0; i < all_users.length; i++) {
		if(all_users[i]["user_role"] == 2){
			$("#users-body").append('<tr id="user">')
			$("#users-body").append("<td>"+ all_users[i]["user_id"] + "</td>");
			$("#users-body").append("<td>"+ all_users[i]["firstname"] + " " + all_users[i]["lastname"] + "</td>");
			$("#users-body").append("<td>"+ all_users[i]["email"] + "</td>");
			$("#users-body").append('<td><button class="btn btn-primary" id="all-request-button">View Requests</button></td>');
			$("#all-request-button").attr("id", all_users[i]["user_id"]);
			document.getElementById(all_users[i]["user_id"]).addEventListener('click', loadEmployeeRequests, false);
			$("#users-body").append("</tr>");
		}
	}
}


var requests; // Variable holding all requests.
function loadEmployeeRequests() {
	var selectedEmployee = $(this).attr("id"); // Pulling the ID from the button which was assigned the ID of the corresponding emloyee.
	console.log($(this).attr("id"));
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Employee Requests.");
			requests = JSON.parse(xhr.responseText);
			$("#option").empty();
			$("#mainview").empty();
			$("#mainview").append('<h1 class="white">Requests by '+ requests[0]["auth_first_name"] + ' ' + requests[0]["auth_last_name"] + '</h1>');
			$("#mainview").append('<table class="table" id="reimb"></table>');
			$("#reimb").append("<thead id='reim-head'>");
			$("#reim-head").append("<th>Status</th>");
			$("#reim-head").append("<th>Employee</th>");
			$("#reim-head").append("<th>Amount</th>");
			$("#reim-head").append("<th>Type</th>");
			$("#reim-head").append("<th>Resolver</th>");
			$("#reim-head").append("<th>Description</th>");
			$("#reim-head").append("<th></th>");
			$("#reim-head").append("</thead>");
			$("#reimb").append("<tbody id='reim-body'></tr>");
			var i;
			for(i = 0; i < requests.length; i++) {
				console.log(requests[i]["user_id"]);
				if(requests[i]["user_id"] == selectedEmployee){
					console.log(requests[i]);
					$("#reim-body").append('<tr id="reimbursement">')
					$("#reim-body").append("<td>"+ requests[i]["status"] + "</td>");
					$("#reim-body").append("<td>"+ requests[i]["auth_first_name"] + " " + requests[i]["auth_last_name"] + "</td>");
					$("#reim-body").append("<td>$"+ insertDecimal(requests[i]["amount"]) + "</td>");
					$("#reim-body").append("<td>"+ requests[i]["type"] + "</td>");
					if(requests[i]["status"] != "PENDING") {
						$("#reim-body").append("<td>"+ requests[i]["reso_first_name"] + " " + requests[i]["reso_last_name"] + "</td>");
					}else{
						$("#reim-body").append("<td>Under Review</td>");
					}
					$("#reim-body").append("<td>"+ requests[i]["description"] + "</td>");
					$("#reim-body").append("<td><form method='GET' action='retrieveReceipt'>" +
							"<button class='btn btn-primary' id='receipt-button' name='receipt-trigger' value=" + requests[i]['id'] + 
							" type='submit'>Receipt</button></form></td>");
					if(requests[i]["status"] == "PENDING") {
						$("#reim-body").append('<td class="resolve-button"><button class="btn btn-primary" id="' + requests[i]["id"] + '">Resolve</button><td>');
						document.getElementById(requests[i]["id"]).addEventListener('click', resolveReimbursement, false);
					}
				}
			}
			$("#reimb").append("</tbody>");
			$("#mainview").append("<button id='back-button' class='btn btn-primary'>Back</button>");
			document.getElementById("back-button").addEventListener('click', viewAllEmployees, true);
		}
	}
	xhr.open("GET", "getAllReimbursements", true);
	xhr.send();
}


var jsonresponse_all_req; // Variable holding all requests. Making a new AJAX call for each function keeps the list current.
function loadAllRequests() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Navbar.");
			jsonresponse_all_req = JSON.parse(xhr.responseText);
			$("#option").empty();
			$("#mainview").empty();
			$("#mainview").append('<h1 class="white">All Requests</h1>');
			$("#mainview").append('<table class="table" id="reimb"></table>');
			$("#reimb").append("<thead id='reim-head'>");
			$("#reim-head").append("<th>Status</th>");
			$("#reim-head").append("<th>Employee</th>");
			$("#reim-head").append("<th>Amount</th>");
			$("#reim-head").append("<th>Type</th>");
			$("#reim-head").append("<th>Resolver</th>");
			$("#reim-head").append("<th>Description</th><th></th>");
			$("#reim-head").append("</thead>");
			$("#reimb").append("<tbody id='reim-body'>");
			var i;
			for(i = 0; i < jsonresponse_all_req.length; i++) {
				console.log(jsonresponse_all_req[i]);
				$("#reim-body").append('<tr id="reimbursement">')
				$("#reim-body").append("<td>"+ jsonresponse_all_req[i]["status"] + "</td>");
				$("#reim-body").append("<td>"+ jsonresponse_all_req[i]["auth_first_name"] + " " + jsonresponse_all_req[i]["auth_last_name"] + "</td>");
				$("#reim-body").append("<td>$"+ insertDecimal(jsonresponse_all_req[i]["amount"]) + "</td>");
				$("#reim-body").append("<td>"+ jsonresponse_all_req[i]["type"] + "</td>");
				if(jsonresponse_all_req[i]["status"] != "PENDING") {
					$("#reim-body").append("<td>"+ jsonresponse_all_req[i]["reso_first_name"] + " " + jsonresponse_all_req[i]["reso_last_name"] + "</td>");
				}else{
					$("#reim-body").append("<td>Under Review</td>");
				}
				$("#reim-body").append("<td>"+ jsonresponse_all_req[i]["description"] + "</td>");
				$("#reim-body").append("<td><form method='GET' action='retrieveReceipt'>" +
						"<button class='btn btn-primary' id='receipt-button' name='receipt-trigger' value=" + jsonresponse_all_req[i]['id'] + 
						" type='submit'>View Receipt</button></form></td>");
				$("#reim-body").append("</tr>");
			}
			$("#reimb").append("</tbody>");
		}
	}
	xhr.open("GET", "getAllReimbursements", true);
	xhr.send();
	setMostRecent('loadAllRequests');
}


var jsonresponse_pending; // Variable holding all requests.
function loadPendingRequests() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Pending Requests.");
			jsonresponse_pending = JSON.parse(xhr.responseText);
			$("#option").empty();
			$("#mainview").empty();
			$("#mainview").append('<h1 class="white">Pending Requests</h1>')
			$("#mainview").append('<table class="table" id="reimb"></table>');
			$("#reimb").append("<thead id='reim-head'>");
			$("#reim-head").append("<th>#</th>");
			$("#reim-head").append("<th>Employee</th>");
			$("#reim-head").append("<th>Amount</th>");
			$("#reim-head").append("<th>Type</th>");
			$("#reim-head").append("<th>Description</th><th></th><th></th>");
			$("#reim-head").append("</thead>");
			$("#reimb").append("<tbody id='reim-body'>");
			var i;
			for(i = 0; i < jsonresponse_pending.length; i++) {
				console.log(jsonresponse_pending[i]);
				if(jsonresponse_pending[i]["status"] == "PENDING") {
					$("#reim-body").append('<tr id="table-row">');
					$("#reim-body").append("<td>"+jsonresponse_pending[i]["id"]+"</td>");
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["auth_first_name"] + " " + jsonresponse_pending[i]["auth_last_name"] + "</td>");
					$("#reim-body").append("<td>$"+ insertDecimal(jsonresponse_pending[i]["amount"]) + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["type"] + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_pending[i]["description"] + "</td>");
					$("#reim-body").append("<td><form method='GET' action='retrieveReceipt'>" +
							"<button class='btn btn-primary' id='receipt-button' name='receipt-trigger' value=" + jsonresponse_pending[i]['id'] + 
							" type='submit'>View Receipt</button></form></td>");
					$("#reim-body").append('<td class="resolve-button"><button class="btn btn-primary" id="' + jsonresponse_pending[i]["id"] + '">Resolve</button><td>');
					$("#reim-body").append("</tr>");
					document.getElementById(jsonresponse_pending[i]["id"]).addEventListener('click', resolveReimbursement, false);
				}	
			}
			$("#reimb").append("</tbody>");
		}
	}
	xhr.open("GET", "getAllReimbursements", true);
	xhr.send();
	setMostRecent('loadPendingRequests');
}


var jsonresponse_resolved; // Variable holding all requests.
function loadResolvedRequests() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		console.log(xhr.readyState);
		if(xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading Resolved Requests.");
			jsonresponse_resolved = JSON.parse(xhr.responseText);
			$("#option").empty();
			$("#mainview").empty();
			$("#mainview").append('<h1 class="white">Resolved Requests</h1>')
			$("#mainview").append('<table class="table" id="reimb"></table>');
			$("#reimb").append("<thead id='reim-head'>");
			$("#reim-head").append("<th>Status</th>");
			$("#reim-head").append("<th>Employee</th>");
			$("#reim-head").append("<th>Amount</th>");
			$("#reim-head").append("<th>Type</th>");
			$("#reim-head").append("<th>Resolving Manager</th>");
			$("#reim-head").append("<th>Description</th><th></th>");
			$("#reim-head").append("</thead>");
			$("#reimb").append("<tbody id='reim-body'>");
			var i;
			for(i = 0; i < jsonresponse_resolved.length; i++) {
				console.log(jsonresponse_resolved[i]);
				if(jsonresponse_resolved[i]["status"] != "PENDING") {
					$("#reim-body").append('<tr id="reim-row">')
					$("#reim-body").append("<td>"+ jsonresponse_resolved[i]["status"] + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_resolved[i]["auth_first_name"] + " " + jsonresponse_resolved[i]["auth_last_name"] + "</td>");
					$("#reim-body").append("<td>$"+ insertDecimal(jsonresponse_resolved[i]["amount"]) + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_resolved[i]["type"] + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_resolved[i]["reso_first_name"] + " " + jsonresponse_resolved[i]["reso_last_name"] + "</td>");
					$("#reim-body").append("<td>"+ jsonresponse_resolved[i]["description"] + "</td>");
					$("#reim-body").append("<td><form method='GET' action='retrieveReceipt'>" +
							"<button class='btn btn-primary' id='receipt-button' name='receipt-trigger' value=" + jsonresponse_resolved[i]['id'] + 
							" type='submit'>View Receipt</button></form></td>");
					$("#reim-body").append("</tr>");
				}	
			}
			$("#reimb").append("</tbody>");
		}
	}
	xhr.open("GET", "getAllReimbursements", true);
	xhr.send();
	setMostRecent('loadResolvedRequests');
}


var currentRequest; // Variable holding the ID of the request button, which was assigned with the ID of the corresponding request.
function resolveReimbursement () {
	console.log($(this).attr("id"));
	currentRequest = $(this).attr("id");
	$("#option").empty();
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200){
			$("#option").prepend(xhr.responseText);
			$("#request-header").text("Approve or deny request " + currentRequest + "?");
			$("#r_id").attr("value", currentRequest);
		}
	}
	xhr.open("GET", "getResolutionForm", true);
	xhr.send();
}


function setMostRecent(func){

	var funcObject = {"func" : func};
	
	console.log("setting most recent: " + func)

	// Put the object into storage
	localStorage.setItem('mostRecent', JSON.stringify(funcObject));
}

function getMostRecent() {
	
	var retrievedObject = localStorage.getItem('mostRecent');
	var parsed = JSON.parse(retrievedObject);
	var val;
	if(parsed != null) {
		val = parsed["func"];
	}else{
		val = "loadManagerWelcome";
	}
	console.log(val);
	
	return val;
}

function clearMostRecent() {
	localStorage.clear();
	console.log("Storage cleared.")
}


function insertDecimal(n) {
	return (n).toFixed(2);
}