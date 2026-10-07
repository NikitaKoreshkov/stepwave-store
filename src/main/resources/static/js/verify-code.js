document.querySelector('#verifyCodeForm').addEventListener('submit', function (event) {
    event.preventDefault();  // Отменяет стандартное поведение формы (перезагрузку страницы)

    const code = document.getElementById('code').value;

    // Проверяем, что код введен
    if (!code) {
        document.getElementById('errorMessage').style.display = 'block';
        document.getElementById('errorMessage').textContent = 'Пожалуйста, введите код.';
        return;
    }

    // Показываем индикатор загрузки
    document.getElementById('loadingIndicator').style.display = 'block';

    // Отправляем запрос на сервер для проверки кода
    fetch('http://localhost:8082/api/email/verify-code/check', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ code: code }),
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        if (data.message && data.message === "Токен для сброса пароля отправлен на ваш email.") {
            // Если код правильный, показываем сообщение об успехе
            document.getElementById('successMessage').style.display = 'block';
            document.getElementById('successMessage').textContent = 'Код подтвержден! Ссылка для сброса пароля отправлена на ваш email.';

            // Перенаправляем на страницу входа через 3 секунды
            setTimeout(() => {
                window.location.href = '/login';  // Перенаправление на страницу входа
            }, 3000);
        } else {
            // Если код неправильный
            document.getElementById('errorMessage').style.display = 'block';
            document.getElementById('errorMessage').textContent = 'Неверный или истекший код подтверждения.';
        }
    })
    .catch(error => {
        console.error('Ошибка:', error);
        document.getElementById('errorMessage').style.display = 'block';
        document.getElementById('errorMessage').textContent = 'Произошла ошибка. Попробуйте снова.';
    })
    .finally(() => {
        // Скрываем индикатор загрузки после выполнения запроса
        document.getElementById('loadingIndicator').style.display = 'none';
    });
});