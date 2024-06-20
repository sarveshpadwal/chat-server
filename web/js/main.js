"use strict";

var usernamePage = document.querySelector("#username-page");
var chatPage = document.querySelector("#chat-page");
var usernameForm = document.querySelector("#usernameForm");
var messageForm = document.querySelector("#messageForm");
var messageInput = document.querySelector("#message");
var messageArea = document.querySelector("#messageArea");
var attachmentInput = document.querySelector("#attachment");
var connectingElement = document.querySelector(".connectingtochat");
var connectElement = document.querySelector("#connect");
var senderInput = document.querySelector("#sender");
var receiverInput = document.querySelector("#receiver");

var stompClient = null;
var sender = null;
var receiver = null;
var channelId = null;
//mycode

var colors = [
    "#2196F3",
    "#32c787",
    "#00BCD4",
    "#ff5652",
    "#ffc107",
    "#ff85af",
    "#FF9800",
    "#39bbb0",
    "#fcba03",
    "#fc0303",
    "#de5454",
    "#b9de54",
    "#54ded7",
    "#54ded7",
    "#1358d6",
    "#d611c6",
];

function connect(event) {
    const socket = new SockJS("http://localhost:8080/ws");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}

function onConnected() {
    sender = senderInput.value.trim();
    receiver = receiverInput.value.trim();

    fetch("http://localhost:8080/api/chat/channel",
        {
            headers: {
                'Accept': 'text/plain',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({
                user1: sender,
                user2: receiver
            })
        })
        .then(function(res){
            return res.text();
        })
        .then(function(data){
            channelId = data;
            // Subscribe to the Public Topic
            stompClient.subscribe("/topic/chat."+channelId, onMessageReceived);
            connectingElement.classList.add("hidden");
            loadOldMessages();
        })
        .catch(function(res){ console.log(res) })
}

function onError(error) {
    connectingElement.textContent =
        "Could not connect to WebSocket! Please refresh the page and try again or contact your administrator.";
    connectingElement.style.color = "red";
}

async function send(event) {
    const messageContent = messageInput.value.trim();
    let chatMessage = {
        from: sender,
        to: receiver,
    };
    if (stompClient) {
        if (messageContent) {
            chatMessage.payload = messageInput.value;
        }
        if (attachmentInput.files.length > 0) {
            const attachment = attachmentInput.files[0];
            const base64Attachment = await convertFileToBase64(attachment);
            chatMessage.file = base64Attachment;
        }

        stompClient.send("/app/chat."+channelId, {}, JSON.stringify(chatMessage));
        messageInput.value = "";
        attachmentInput.value = "";
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    console.log("msg received", payload.body);
    var message = JSON.parse(payload.body);
    showMessage(message);
}

function showMessage(message) {
    var messageElement = document.createElement("li");

    messageElement.classList.add("chat-message");

    var avatarElement = document.createElement("i");
    var avatarText = document.createTextNode(message.from);
    avatarElement.appendChild(avatarText);
    avatarElement.style["background-color"] = getAvatarColor(message.from);

    messageElement.appendChild(avatarElement);

    var usernameElement = document.createElement("span");
    var usernameText = document.createTextNode(message.from);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);
    // * update
    usernameElement.style["color"] = getAvatarColor(message.from);
    //* update end

    if (message.payload) {
        var textElement = document.createElement("p");
        var messageText = document.createTextNode(message.payload);
        textElement.appendChild(messageText);
        messageElement.appendChild(textElement);
    }

    if(message.file) {
        var image = new Image();
        image.src = message.file;
        image.width=50;
        image.height=50;
        messageElement.appendChild(image);
    }
    // * update
    if (message.from === sender) {
        // Add a class to float the message to the right
        messageElement.classList.add("own-message");
    } // * update end
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

function convertFileToBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result);
        reader.onerror = reject;
        reader.readAsDataURL(file);
    });
}

function loadOldMessages() {
    fetch("http://localhost:8080/api/chat/channel/"+channelId,
        {
            headers: {
                'Accept': 'application/json',
            },
            method: "GET"
        })
        .then(function (res) {
            return res.json();
        })
        .then(function(data){
            console.log(data);
            const messages = data;
            for (let i = 0, len = messages.length; i < len; i++) {
                showMessage(messages[i]);
            }
        })
        .catch(function(res){ console.log(res) })
}

connectElement.addEventListener("click", connect, true);
messageForm.addEventListener("submit", send, true);

