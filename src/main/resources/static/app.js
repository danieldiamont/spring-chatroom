import { Client } from '@stomp/stompjs';

const MAX_RECONNECT_ATTEMPTS = 5;

const stompClient = new Client(
{
    brokerURL: 'ws://localhost:8080/chatroom-ws',
    onConnect: () => {
        stompClient.subscribe('/topic/chatroom', (res) => {
            console.log('Received: ' + res.body);

            let parsedMessage = JSON.parse(res.body);
            if (parsedMessage?.type != null) {
                if (parsedMessage.type === 'STATUS') {
                    let activeUsers = document.getElementById('chat-active-users');
                    activeUsers.innerHTML = 'Active Users: ' + parsedMessage.activeConnections;
                }
                else if (parsedMessage.type === 'MESSAGES') {
                    let chatMessages = document.getElementById('chat-messages');
                    chatMessages.innerHTML = '';
                    if (parsedMessage?.messages != null) {
                        for (let msg of parsedMessage.messages) {
                            chatMessages.innerHTML += '<p>' + msg + '</p>';
                        }
                    }
                }
            }
        });
        /*
        stompClient.publish(
            {
                destination: '/app/message',
                body: JSON.stringify({ 'message': 'a new user has joined the chat!' }),
            }
        );
        */
    },
});

stompClient.onWebSocketError = function (error) {
    console.log('Websocket Error: ' + JSON.stringify(error));
    disconnect();
    reconnect();
}

stompClient.onStompError = function (frame) {
    console.log('Stomp Error: ' + JSON.stringify(frame));
    disconnect();
    reconnect();
}

function reconnect() {
    let attempts = 0;
    let isConnected = false;
    while (attempts < MAX_RECONNECT_ATTEMPTS && !isConnected) {
        setTimeout(() => {
            console.log('Reconnect Attempt: ' + attempts);
            stompClient.activate();
        }, 1000);
        isConnected = stompClient.connected;
        attempts++;
    }
    if (!isConnected) {
        console.log('Failed to reconnect');
    }
}


function connect(event) {
    stompClient.activate();
    let connectBtn = document.getElementById('connect-btn');
    connectBtn.disabled = true;
    let disconnectBtn = document.getElementById('disconnect-btn');
    disconnectBtn.disabled = false;
    if (event != null) {
        event.preventDefault();
    }
}

function disconnect(event) {
    stompClient.deactivate();
    let connectBtn = document.getElementById('connect-btn');
    connectBtn.disabled = false;
    let disconnectBtn = document.getElementById('disconnect-btn');
    disconnectBtn.disabled = true;
    if (event != null) {
        event.preventDefault();
    }
}

function sendMessage(event) {
    let messageContent = document.getElementById('message').value;
    if (messageContent) {
        stompClient.publish({ destination: '/app/message', body: JSON.stringify({ 'message': messageContent }) });
        console.log('Message Sent');

    }
    if (event != null) {
        event.preventDefault();
    }
}


// Event Listeners
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('connect-btn').addEventListener('click', connect);
    document.getElementById('disconnect-btn').addEventListener('click', disconnect);
    document.getElementById('send-btn').addEventListener('click', sendMessage);
});


