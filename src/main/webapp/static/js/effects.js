/**
 * 
 */

$(".boo").hover(function() {
	$("body").css("background-image", "url('static/img/boo.jpg')");
	$(".goodbye-message").text("BYE!");
	$(".goodbye-message").css("color", "red");
	}, function(){
	$("body").css("background-image", "");
	$(".goodbye-message").css("display", "block");
	$(".goodbye-message").text("Stay safe...");
	});