// contact.js

// Функция для добавления сообщений в окно чата
function addMessage(message, fromBot = false) {
    const chatOutput = document.getElementById("chat-output");
    const messageElement = document.createElement("div");
    messageElement.classList.add(fromBot ? "bot-message" : "user-message");
    messageElement.textContent = message;
    chatOutput.appendChild(messageElement);
    chatOutput.scrollTop = chatOutput.scrollHeight;
}

// Функция для обработки отправки сообщения
function sendMessage() {
    const userInput = document.getElementById("user-input");
    const message = userInput.value.trim();

    if (message) {
        addMessage(message, false); // Показываем сообщение пользователя
        userInput.value = "";

        // Отправляем сообщение на сервер для обработки
        fetch("/chat", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ question: message })
        })
        .then(response => response.json())
        .then(data => {
            addMessage(data.answer, true); // Показываем ответ бота
        })
        .catch(error => {
            console.error("Ошибка при отправке запроса:", error);
            addMessage("Произошла ошибка. Пожалуйста, попробуйте позже.", true);
        });
    }
}

// Добавляем обработчик события для кнопки отправки
document.getElementById("send-button").addEventListener("click", sendMessage);

// Добавляем обработчик события для ввода в текстовое поле (Enter)
document.getElementById("user-input").addEventListener("keypress", function(e) {
    if (e.key === "Enter") {
        sendMessage();
    }
});