import { Client } from '@stomp/stompjs';


const stompClient = new Client(
{
    brokerURL: 'ws://localhost:8080/chatroom-ws',
    onConnect: () => {
        stompClient.subscribe('/topic/chatroom', (res) => {
            console.log('Received: ' + res.body);

            let parsedMessage = JSON.parse(res.body);
            if (parsedMessage?.messages != null) {
                let chatMessages = document.getElementById('chat-messages');
                chatMessages.innerHTML = '';

                for (let msg of parsedMessage.messages) {
                    chatMessages.innerHTML += '<p>' + msg + '</p>';
                }
            }
        });
        stompClient.publish(
            {
                destination: '/app/message',
                body: JSON.stringify({ 'message': 'a new user has joined the chat!' }),
            }
        );
    },
});

stompClient.onWebSocketError = function (error) {
    console.log('Websocket Error: ' + JSON.stringify(error));
}

stompClient.onStompError = function (frame) {
    console.log('Stomp Error: ' + JSON.stringify(frame));
}


function connect(event) {
    stompClient.activate();
    let connectBtn = document.getElementById('connect-btn');
    connectBtn.disabled = true;
    let disconnectBtn = document.getElementById('disconnect-btn');
    disconnectBtn.disabled = false;
    event.preventDefault();
}

function disconnect(event) {
    stompClient.deactivate();
    let connectBtn = document.getElementById('connect-btn');
    connectBtn.disabled = false;
    let disconnectBtn = document.getElementById('disconnect-btn');
    disconnectBtn.disabled = true;
    event.preventDefault();
}

function sendMessage(event) {
    let messageContent = document.getElementById('message').value;
    if (messageContent) {
        stompClient.publish({ destination: '/app/message', body: JSON.stringify({ 'message': messageContent }) });
        console.log('Message Sent');

    }
    event.preventDefault();
}


// Event Listeners
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('connect-btn').addEventListener('click', connect);
    document.getElementById('disconnect-btn').addEventListener('click', disconnect);
    document.getElementById('send-btn').addEventListener('click', sendMessage);
});


