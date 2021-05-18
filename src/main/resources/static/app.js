var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/videolake');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
    	alert("Connected to /videolake");
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/faces', function (greeting) {
            showGreeting(greeting.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
	var myObj = JSON.parse(message);
    $("#greetings").append("<tr><td>Gender</td><td>" + myObj.captureLibResult[0].faces[0].gender.value + "</td></tr>");
    $("#greetings").append("<tr><td>Age Group</td><td>" + myObj.captureLibResult[0].faces[0].age.ageGroup + "</td></tr>");
    $("#greetings").append("<tr><td>Glass</td><td>" + myObj.captureLibResult[0].faces[0].glass.value + "</td></tr>");
    $("#greetings").append("<tr><td>Mask</td><td>" + myObj.captureLibResult[0].faces[0].mask.value + "</td></tr>");
    $("#greetings").append("<tr><td><img src='" + myObj.captureLibResult[0].image + "'></td></tr>");
    //$("#greetings").append("<tr><td><img src='" + "https://www.w3schools.com/images/lamp.jpg" + "'></td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

