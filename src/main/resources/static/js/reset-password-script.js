// JS для переключения видимости пароля
document.getElementById('toggle-password')?.addEventListener('click', function() {
    const passwordField = document.getElementById('new-password');
    const confirmPasswordField = document.getElementById('confirm-password');
    const eyeIcon = document.getElementById('eye-icon');

    // Проверяем, показывается ли сейчас пароль
    if (passwordField.type === 'password') {
        // Меняем тип на текст, чтобы пароль был виден
        passwordField.type = 'text';
        confirmPasswordField.type = 'text'; // То же самое для поля подтверждения
        eyeIcon.classList.remove('fa-eye'); // Меняем иконку на открытый глаз
        eyeIcon.classList.add('fa-eye-slash'); // Иконка закрытого глаза
    } else {
        // Возвращаем тип в password
        passwordField.type = 'password';
        confirmPasswordField.type = 'password';
        eyeIcon.classList.remove('fa-eye-slash');
        eyeIcon.classList.add('fa-eye');
    }
});

// Обработка формы сброса пароля
document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault(); // Отменяет стандартное поведение формы (перезагрузку страницы)

    const newPassword = document.getElementById('new-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;

    // Проверка на совпадение паролей
    if (newPassword !== confirmPassword) {
        alert('Пароли не совпадают!');
        return; // Прерываем отправку формы
    }

    // Получаем токен из параметров URL
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token'); // Получаем токен из URL

    if (!token) {
        alert('Токен не найден в URL!');
        return; // Если токен отсутствует, прерываем выполнение
    }

    // Отправляем запрос на сервер для сброса пароля
    fetch('http://localhost:8082/api/email/reset-password', { // Используем правильный путь
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            token: token,
            newPassword: newPassword
        })
    })
    .then(response => {
        if (response.ok) {
            // Показать сообщение об успехе
            showSuccessMessage(); // Показываем окно успеха
        } else {
            return response.json().then(data => {
                // Показать ошибку, если она произошла
                alert('Ошибка: ' + (data.error || 'Неизвестная ошибка'));
            });
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Произошла ошибка, попробуйте снова.');
    });
});

// Функция для отображения окна успеха
function showSuccessMessage() {
    // Создаем элемент окна успеха
    const successMessage = document.createElement('div');
    successMessage.classList.add('success-message');
    successMessage.innerHTML = `
        <div class="message-content">
            <h3>Пароль успешно изменен!</h3>
            <p>Теперь вы можете войти в свой аккаунт с новым паролем.</p>
            <button onclick="window.location.href='/signup'">Перейти на страницу входа</button>
        </div>
    `;
    // Добавляем окно на страницу
    document.body.appendChild(successMessage);

    // Делаем окно видимым
    successMessage.style.display = 'block';

    // Автоматический переход через 3 секунды
    setTimeout(function() {
        window.location.href = '/signup'; // Переход на страницу входа
    }, 3000); // Ждем 3 секунды перед переходом
}