document.addEventListener('DOMContentLoaded', function () {
    let errorTimeout; // Глобальная переменная для хранения таймера ошибки

    // Функция для смены языка
    function changeLanguage() {
        const languageSelect = document.getElementById('language-select');
        // Переключателя языка нет на всех страницах, раньше падало с TypeError
        if (!languageSelect) {
            return;
        }
        const language = languageSelect.value;
        const storeName = document.getElementById("store-name");
        const welcomeMessage = document.getElementById("welcome-message");
        const welcomeSubtext = document.querySelector(".welcome-subtext");
        const loginBtn = document.getElementById("loginBtn");
        const registerBtn = document.getElementById("registerBtn");
        const loginHeader = document.getElementById("login-header");
        const registerHeader = document.getElementById("register-header");

        // Меняем текст на нужный в зависимости от языка
        if (language === 'en') {
            storeName.textContent = "StepWave";
            welcomeMessage.textContent = "Welcome!";
            welcomeSubtext.textContent = "Please log in to continue.";
            loginBtn.textContent = "Log In";
            registerBtn.textContent = "Create Account";
            loginHeader.textContent = "Log In";
            registerHeader.textContent = "Create Account";
        } else if (language === 'kk') {
            storeName.textContent = "StepWave";
            welcomeMessage.textContent = "Қош келдіңіздер!";
            welcomeSubtext.textContent = "Жүйеге кіру үшін кіру жасаңыз.";
            loginBtn.textContent = "Жүйеге кіру";
            registerBtn.textContent = "Аккаунт жасау";
            loginHeader.textContent = "Жүйеге кіру";
            registerHeader.textContent = "Аккаунт жасау";
        } else {
            storeName.textContent = "StepWave";
            welcomeMessage.textContent = "Добро пожаловать!";
            welcomeSubtext.textContent = "Пожалуйста, войдите в систему, чтобы продолжить.";
            loginBtn.textContent = "Войти в систему";
            registerBtn.textContent = "Создать аккаунт";
            loginHeader.textContent = "Вход";
            registerHeader.textContent = "Регистрация";
        }

        // Обновление текста кнопок и placeholder'ов
        const elements = {
            loginSubmit: document.getElementById("login-submit"),
            registerSubmit: document.getElementById("register-submit"),
            backToMain: document.getElementById("back-to-main"),
            backToMainRegister: document.getElementById("back-to-main-register"),
            usernameField: document.getElementById("username"),
            passwordField: document.getElementById("password"),
            newUsernameField: document.getElementById("new-username"),
            newPasswordField: document.getElementById("new-password"),
            resetEmailField: document.getElementById("reset-email"),
            sendResetLink: document.getElementById("send-reset-link"),
            backToLogin: document.getElementById("back-to-login"),
            forgotPasswordLink: document.getElementById("forgot-password-link"),
            resetPasswordHeader: document.getElementById("resetPasswordHeader"),
            errorMessage: document.getElementById("error-message"),
            successMessage: document.getElementById("success-message"),
            loginPasswordError: document.getElementById("login-password-error"),
            passwordError: document.getElementById("password-error")
        };

        if (language === 'en') {
            elements.loginSubmit.textContent = "Log In";
            elements.registerSubmit.textContent = "Create Account";
            elements.backToMain.textContent = "Back";
            elements.backToMainRegister.textContent = "Back";
            elements.usernameField.placeholder = "Enter username";
            elements.passwordField.placeholder = "Enter password";
            elements.newUsernameField.placeholder = "Enter username";
            elements.newPasswordField.placeholder = "Enter password";
            elements.resetEmailField.placeholder = "Enter your email";
            elements.sendResetLink.textContent = "Send reset link";
            elements.backToLogin.textContent = "Back";
            elements.forgotPasswordLink.textContent = "Forgot password?";
            elements.resetPasswordHeader.textContent = "Password Reset";
            elements.loginPasswordError.textContent = "Password must contain at least 8 characters and one uppercase letter.";
            elements.passwordError.textContent = "Password must contain at least 8 characters and one uppercase letter.";
            elements.errorMessage.textContent = "User already exists.";
            elements.successMessage.textContent = "Account successfully created!";
        } else if (language === 'kk') {
            elements.loginSubmit.textContent = "Жүйеге кіру";
            elements.registerSubmit.textContent = "Аккаунт жасау";
            elements.backToMain.textContent = "Артқа";
            elements.backToMainRegister.textContent = "Артқа";
            elements.usernameField.placeholder = "Пайдаланушы атын енгізіңіз";
            elements.passwordField.placeholder = "Құпия сөзді енгізіңіз";
            elements.newUsernameField.placeholder = "Пайдаланушы атын енгізіңіз";
            elements.newPasswordField.placeholder = "Құпия сөзді енгізіңіз";
            elements.resetEmailField.placeholder = "Электронды поштаны енгізіңіз";
            elements.sendResetLink.textContent = "Сілтемені жіберу";
            elements.backToLogin.textContent = "Артқа";
            elements.forgotPasswordLink.textContent = "Құпия сөзді ұмыттыңыз ба?";
            elements.resetPasswordHeader.textContent = "Құпия сөзді қалпына келтіру";
            elements.loginPasswordError.textContent = "Құпия сөз кемінде 8 символ мен 1 бас әріптен тұруы керек.";
            elements.passwordError.textContent = "Құпия сөз кемінде 8 символ мен 1 бас әріптен тұруы керек.";
            elements.errorMessage.textContent = "Бұл пайдаланушы бар.";
            elements.successMessage.textContent = "Аккаунт сәтті құрылды!";
        } else {
            elements.loginSubmit.textContent = "Войти";
            elements.registerSubmit.textContent = "Создать аккаунт";
            elements.backToMain.textContent = "Назад";
            elements.backToMainRegister.textContent = "Назад";
            elements.usernameField.placeholder = "Введите имя пользователя";
            elements.passwordField.placeholder = "Введите пароль";
            elements.newUsernameField.placeholder = "Введите имя пользователя";
            elements.newPasswordField.placeholder = "Введите пароль";
            elements.resetEmailField.placeholder = "Введите ваш email";
            elements.sendResetLink.textContent = "Отправить ссылку для сброса пароля";
            elements.backToLogin.textContent = "Назад";
            elements.forgotPasswordLink.textContent = "Забыли пароль?";
            elements.resetPasswordHeader.textContent = "Сброс пароля";
            elements.loginPasswordError.textContent = "Пароль должен содержать более 8 символов и хотя бы одну заглавную букву.";
            elements.passwordError.textContent = "Пароль должен содержать более 8 символов и хотя бы одну заглавную букву.";
            elements.errorMessage.textContent = "Такой пользователь уже существует.";
            elements.successMessage.textContent = "Аккаунт успешно создан!";
        }
    }

    // Смена языка
    const languageSelect = document.getElementById('language-select');
    if (languageSelect) {
        languageSelect.addEventListener('change', changeLanguage);
    }

    // Инициализация языка при загрузке страницы
    changeLanguage();

    // Функция для показа/скрытия пароля
    function togglePasswordVisibility(passwordFieldId, eyeIconId) {
        const passwordField = document.getElementById(passwordFieldId);
        const eyeIcon = document.getElementById(eyeIconId);

        const openEyeIcon = '/static/images/icons/open.png'; // Путь для открытого глаза
        const closeEyeIcon = '/static/images/icons/close.png'; // Путь для закрытого глаза

        // Проверка, если есть ошибка, то не сбрасывать стили
        const isInvalid = passwordField.classList.contains("invalid");

        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            eyeIcon.src = openEyeIcon; // Меняем на открытый глаз
        } else {
            passwordField.type = 'password';
            eyeIcon.src = closeEyeIcon; // Меняем на закрытый глаз
        }

        // Если ошибка была, оставляем красную обводку
        if (isInvalid) {
            passwordField.classList.add("invalid");
            eyeIcon.classList.add("invalid"); // Добавляем класс и для иконки
            // Добавляем обводку, если поле не сбрасывается
            passwordField.style.border = '2px solid red';
            passwordField.style.backgroundColor = '#000';
        }
    }

    // Переходы между страницами
    document.getElementById('loginBtn')?.addEventListener('click', function() {
        document.querySelector('.container').style.display = 'none';
        document.getElementById('login-form').style.display = 'block';
        changeLanguage();
    });

    document.getElementById('registerBtn')?.addEventListener('click', function() {
        document.querySelector('.container').style.display = 'none';
        document.getElementById('register-form').style.display = 'block';
        changeLanguage();
    });

    document.getElementById('back-to-main')?.addEventListener('click', function() {
        document.querySelector('.container').style.display = 'block';
        document.getElementById('login-form').style.display = 'none';

        document.getElementById("username").value = '';
        document.getElementById("password").value = '';

        // Сброс ошибок и восстановления состояния
        document.getElementById("error-message").style.display = "none";
        document.getElementById("password").classList.remove("invalid");
        document.getElementById("login-submit").classList.remove("invalid");
        document.getElementById('toggle-password-login').classList.remove('invalid');

        // Сброс стилей поля ввода и кнопки при ошибке
        document.getElementById("new-username").classList.remove("invalid");
        document.getElementById("register-submit").classList.remove("invalid");

        changeLanguage();
    });

    document.getElementById('back-to-main-register')?.addEventListener('click', function() {
        document.querySelector('.container').style.display = 'block';
        document.getElementById('register-form').style.display = 'none';

        document.getElementById("new-username").value = '';
        document.getElementById("new-password").value = '';

        // Сброс ошибок и восстановления состояния
        document.getElementById("error-message").style.display = "none";
        document.getElementById("new-password").classList.remove("invalid");
        document.getElementById("register-submit").classList.remove("invalid");
        document.getElementById('toggle-password-register').classList.remove('invalid');

        // Сброс стилей
        const passwordField = document.getElementById("new-password");
        passwordField.style.border = '';
        passwordField.style.backgroundColor = '';

        // Сброс стилей поля ввода и кнопки при ошибке
        document.getElementById("new-username").classList.remove("invalid");
        document.getElementById("register-submit").classList.remove("invalid");

        changeLanguage();
    });

    // Показываем/скрываем пароль
    document.getElementById('toggle-password-login')?.addEventListener('click', function() {
        togglePasswordVisibility('password', 'toggle-password-login');
    });

    document.getElementById('toggle-password-register')?.addEventListener('click', function() {
        togglePasswordVisibility('new-password', 'toggle-password-register');
    });







    // Функция для отображения формы сброса пароля
    document.getElementById('forgot-password-link')?.addEventListener('click', function(event) {
        event.preventDefault();
        document.getElementById('login-form').style.display = 'none';
        document.getElementById('reset-password-form').style.display = 'block';
        changeLanguage();
    });

    // Обработка формы сброса пароля
    document.getElementById('send-reset-link')?.addEventListener('click', function(event) {
        event.preventDefault(); // Отменяет стандартное поведение формы (перезагрузку страницы)

        const email = document.getElementById('reset-email').value;

        fetch("/api/email/forgot-password", {  // Используем правильный путь
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username: email }) // Отправляем как username
        })
        .then(response => {
            if (response.ok) {
                // Показать сообщение об успехе
                document.getElementById("success-message").style.display = "block";
                document.getElementById("success-message").textContent = "Ссылка для сброса пароля отправлена на вашу почту!";
                document.getElementById("error-message").style.display = "none";
            } else {
                return response.json();
            }
        })
        .then(data => {
            if (data && data.error) {
                // Показать ошибку, если она произошла
                document.getElementById("error-message").style.display = "block";
                document.getElementById("error-message").textContent = data.error;
            }
        })
        .catch(error => {
            console.error("Error:", error);
            document.getElementById("error-message").style.display = "block";
            document.getElementById("error-message").textContent = "Произошла ошибка, попробуйте снова.";
        });
    });

    // Обработка возвращения на форму входа
    document.getElementById('back-to-login')?.addEventListener('click', function() {
        document.getElementById('reset-password-form').style.display = 'none';
        document.getElementById('login-form').style.display = 'block';
        document.getElementById('reset-email').value = '';
        changeLanguage();
    });









    // Обработка формы регистрации через AJAX
    document.getElementById('register-form')?.addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById("new-username").value;
        const password = document.getElementById("new-password").value;

        // Проверка пароля (длина и заглавная буква)
        const passwordRegex = /^(?=.*[A-Z]).{8,}$/;
        if (!passwordRegex.test(password)) {
            showError("Пожалуйста используйте пароль из 8 символов и хотя бы 1 заглавной буквы");

            // Добавляем класс invalid для поля ввода и кнопки
            document.getElementById("new-password").classList.add("invalid");
            document.getElementById("register-submit").classList.add("invalid");

            // Меняем иконку на белую при ошибке
            document.getElementById('toggle-password-register').classList.add('invalid');

            return;
        } else {
            document.getElementById("error-message").style.display = "none";

            // Убираем красную обводку, если пароль верный
            document.getElementById("new-password").classList.remove("invalid");
            document.getElementById("register-submit").classList.remove("invalid");

            // Убираем белую иконку при исправленной ошибке
            document.getElementById('toggle-password-register').classList.remove('invalid');
        }

        // Проверка на уникальность логина
        fetch(`/api/users/check-username/${username}`)
            .then(response => response.json())
            .then(data => {
                if (data.exists) {
                    // Логин занят, показываем ошибку
                    document.getElementById("registration-error").style.display = "block"; // Показать ошибку
                    document.getElementById("registration-error").textContent = "Данный логин уже существует.";
                    return;
                }

                // Если логин не занят, продолжаем регистрацию
                fetch("/api/users", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        username: username,
                        password: password
                    })
                })
                .then(response => {
                    if (response.ok) {
                        document.getElementById("success-account-message").style.display = "block";
                        setTimeout(function() {
                            window.location.href = "http://localhost:8082/";
                        }, 5000);
                    } else {
                        return response.json();
                    }
                })
                .then(data => {
                    if (data.error) {
                        document.getElementById("success-account-message").style.display = "none";
                        document.getElementById("error-message").style.display = "block";
                        document.getElementById("error-message").textContent = data.error;
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                });
            })
            .catch(error => {
                console.error("Error:", error);
            });
    });

    // Функция для показа модального окна ошибки
    function showError(message) {
        document.getElementById("modal-error-message").textContent = message;
        document.getElementById("error-modal").style.display = "flex";
    }

    // Закрытие модального окна
    document.getElementById("close-error-modal").addEventListener('click', function() {
        document.getElementById("error-modal").style.display = "none";
    });

    // Функция для отображения ошибки
    function showError(message) {
        const errorMessage = document.getElementById("error-message");

        // Если ошибка уже показывается, сбрасываем таймер
        if (errorTimeout) {
            clearTimeout(errorTimeout); // Очищаем предыдущий таймер
            errorMessage.style.animation = "none"; // Сбрасываем анимацию
            setTimeout(() => {
                errorMessage.style.animation = ""; // Применяем анимацию снова
            }, 0);
        }

        // Установить текст ошибки
        errorMessage.textContent = message;

        // Показать сообщение
        errorMessage.style.display = "block";

        // Скрыть через 5 секунд
        errorTimeout = setTimeout(function() {
            errorMessage.style.animation = "slideUp 0.5s ease forwards";
            setTimeout(() => {
                errorMessage.style.display = "none";
            }, 500); // время окончания анимации
        }, 5000); // Таймер на 5 секунд
    }
});







 document.getElementById('send-reset-link')?.addEventListener('click', function (event) {
    event.preventDefault(); // Отменяем стандартное поведение кнопки

    const emailInput = document.getElementById('reset-email');
    const email = emailInput.value;

    if (!email) {
        showError('Пожалуйста, введите ваш email!');
        return;
    }

    // Отправляем запрос на сервер для получения кода подтверждения
    fetch('http://localhost:8082/api/email/send-confirmation-code', {  // Используем правильный эндпоинт
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username: email }), // Отправляем email как username
    })
    .then((response) => {
        if (response.ok) {
            return response.json(); // Парсим ответ от сервера
        } else {
            // Обрабатываем ошибку, если сервер вернул не 200 OK
            response.json().then((data) => {
                showError(`Ошибка: ${data.message || 'Не удалось отправить код подтверждения.'}`);
            });
        }
    })
    .then((data) => {
        // Если код подтверждения был отправлен
        if (data.message === "Код подтверждения отправлен на указанный email.") {
            // Показываем сообщение об успехе
            const successMessage = document.getElementById('password-reset-success');
            successMessage.style.display = 'block';

            // Скрываем форму сброса
            const resetForm = document.getElementById('reset-password-form');
            resetForm.style.display = 'none';
        }
    })
    .catch((error) => {
        console.error('Ошибка запроса:', error);
        showError('Произошла ошибка при отправке запроса. Попробуйте позже.');
    });
});



document.getElementById("login-form-data").addEventListener("submit", async function (event) {
    event.preventDefault(); // Предотвращаем стандартную отправку формы

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const loadingMessage = document.getElementById("login-submit");
    loadingMessage.textContent = "Загрузка...";
    loadingMessage.classList.add("btn-loading"); // Добавляем класс анимации

    // Задержка перед отправкой запроса
    setTimeout(async () => {
        try {
            const response = await fetch("/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                // Перенаправление на dashboard через 2 секунды после успешного логина
                window.location.href = "http://localhost:8082/dashboard"; // Перенаправление на нужный адрес
            } else {
                // Если ошибка авторизации
                const errorMessage = await response.text();
                document.getElementById("login-password-error").textContent = errorMessage;
                document.getElementById("login-password-error").style.display = "block";
            }
        } catch (error) {
            alert("Ошибка сервера. Попробуйте позже.");
        } finally {
            // Восстанавливаем кнопку после загрузки
            loadingMessage.textContent = "Войти";
            loadingMessage.classList.remove("btn-loading"); // Убираем анимацию
        }
    }, 2000); // Задержка 2 секунды перед отправкой запроса
});





