let stompClient = null;
let currentChatEmail = null;

document.addEventListener("DOMContentLoaded", function () {

    /* ===============================
       ELEMENTS
    ================================ */
    const senderEmail = document.body.getAttribute("data-user-email");
    const messageInput = document.querySelector(".chat-input");
    const sendButton = document.querySelector(".chat-send-btn");
    const messagesContainer = document.querySelector(".chat-messages");
    const usersContainer = document.querySelector(".chat-user-list"); // FIXED

    /* ===============================
       INIT
    ================================ */
    connectWebSocket();
    loadUsers();

    /* ===============================
       WEBSOCKET CONNECT
    ================================ */
    function connectWebSocket() {
        const socket = new SockJS("/ws/chat");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function () {
            console.log("✅ WebSocket connected");

            stompClient.subscribe("/user/queue/messages", function (msg) {
                const message = JSON.parse(msg.body);

                if (message.senderEmail === currentChatEmail) {
                    renderIncomingMessage(message.encryptedContent);
                }
            });
        });
    }

    /* ===============================
       LOAD USERS
    ================================ */
    function loadUsers() {
        fetch("/api/users")
            .then(res => res.json())
            .then(users => {
                usersContainer.innerHTML = "";

                users.forEach(user => {
                    const div = document.createElement("div");
                    div.className = "p-3 border-bottom chat-item";

                    div.innerHTML = `
                        <div class="d-flex justify-content-between">
                            <strong>${user.email}</strong>
                            <small class="text-muted">
                                ${user.online ? "Online" : "Offline"}
                            </small>
                        </div>
                        <div class="text-muted small">Click to chat</div>
                    `;

                    div.addEventListener("click", () => {
                        setActiveChat(div);
                        openChat(user.email);
                    });

                    usersContainer.appendChild(div);
                });
            });
    }

    /* ===============================
       OPEN CHAT
    ================================ */
    function openChat(email) {
        if (currentChatEmail === email) return;

            currentChatEmail = email;
            messagesContainer.innerHTML = "";

            document.getElementById("chatUserName").innerText = email;
            document.getElementById("chatUserStatus").innerText = "Online";

        fetch(`/api/chats/${email}`)
            .then(res => res.json())
            .then(messages => {
                messages.forEach(msg => {
                    if (msg.senderEmail === email) {
                        renderIncomingMessage(msg.encryptedContent);
                    } else {
                        renderOutgoingMessage(msg.encryptedContent);
                    }
                });
                scrollToBottom();
            });
    }

    /* ===============================
       SEND MESSAGE
    ================================ */
    function sendMessage() {
        const text = messageInput.value.trim();
        if (!text || !currentChatEmail || !stompClient) return;

        stompClient.send("/app/chat.private", {}, JSON.stringify({
            senderEmail: senderEmail,
            receiverEmail: currentChatEmail,
            encryptedContent: text,
            messageType: "TEXT"
        }));

        renderOutgoingMessage(text);
        messageInput.value = "";
        scrollToBottom();
    }

    /* ===============================
       UI HELPERS
    ================================ */
    function renderOutgoingMessage(text) {
        messagesContainer.insertAdjacentHTML("beforeend", `
            <div class="mb-3 text-end">
                <div class="bg-primary text-white p-2 rounded d-inline-block">${text}</div>
                <small class="text-muted d-block">${getTime()}</small>
            </div>
        `);
    }

    function renderIncomingMessage(text) {
        messagesContainer.insertAdjacentHTML("beforeend", `
            <div class="mb-3">
                <div class="bg-light text-dark p-2 rounded w-75">${text}</div>
                <small class="text-muted">${getTime()}</small>
            </div>
        `);
    }

    function getTime() {
        return new Date().toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit"
        });
    }

    function scrollToBottom() {
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    }

    function setActiveChat(activeDiv) {
        document.querySelectorAll(".chat-item")
            .forEach(i => i.classList.remove("active"));
        activeDiv.classList.add("active");
    }

    /* ===============================
       EVENTS
    ================================ */
    sendButton.addEventListener("click", sendMessage);

    messageInput.addEventListener("keypress", function (e) {
        if (e.key === "Enter") {
            e.preventDefault();
            sendMessage();
        }
    });

});
