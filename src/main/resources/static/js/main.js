// Для кнопки выбора языка
document.getElementById('language-btn')?.addEventListener('click', function(event) {
    const dropdown = document.getElementById('language-dropdown');
    dropdown.classList.toggle('show');
    // Блокируем всплытие события, чтобы клик на кнопку не закрыл окно
    event.stopPropagation();
});

// Для закрытия выпадающего окна при клике в любое место экрана
document.addEventListener('click', function(event) {
    const dropdown = document.getElementById('language-dropdown');
    const button = document.getElementById('language-btn');

    // Проверка, что клик был не по кнопке и не по выпадающему меню
    if (!button.contains(event.target) && !dropdown.contains(event.target)) {
        dropdown.classList.remove('show');
    }
});

// Для кнопки "Помощь"
document.getElementById('helpBtn')?.addEventListener('mouseenter', function() {
    const helpDropdown = document.getElementById('help-dropdown');
    helpDropdown.style.display = 'block';
});

document.getElementById('helpBtn')?.addEventListener('mouseleave', function() {
    const helpDropdown = document.getElementById('help-dropdown');
    // Проверяем, если курсор не внутри выпадающего окна, скрываем его
    setTimeout(function() {
        if (!helpDropdown.matches(':hover')) {
            helpDropdown.style.display = 'none';
        }
    }, 200);
});

// Когда курсор находит внутри выпадающего окна помощи, оно остается открытым
document.getElementById('help-dropdown')?.addEventListener('mouseenter', function() {
    const helpDropdown = document.getElementById('help-dropdown');
    helpDropdown.style.display = 'block';
});

// Когда курсор уходит с окна помощи, оно скрывается
document.getElementById('help-dropdown')?.addEventListener('mouseleave', function() {
    const helpDropdown = document.getElementById('help-dropdown');
    helpDropdown.style.display = 'none';
});

// Для кнопки "Войти"
document.getElementById('loginButton')?.addEventListener('click', function() {
    const registerWindow = document.getElementById('registerWindow');
    const overlay = document.createElement('div');
    overlay.classList.add('overlay');
    document.body.appendChild(overlay);

    registerWindow.classList.add('show');  // Показываем окно регистрации
    overlay.addEventListener('click', function() {
        registerWindow.classList.remove('show');  // Скрываем окно при клике на фон
        document.body.removeChild(overlay);  // Удаляем фон
    });
});


document.addEventListener("DOMContentLoaded", () => {
    const wrapper = document.querySelector(".shoes-list-wrapper");
    const items = document.querySelectorAll(".shoe-item");
    const itemWidth = items[0].offsetWidth + 20; // Ширина элемента + gap
    let currentOffset = 0;
    let scrollInterval;

    // Дублируем элементы для бесконечного цикла
    items.forEach(item => {
        const clone = item.cloneNode(true);
        wrapper.appendChild(clone);
    });

    // Функция для анимации прокрутки
    function animate() {
        currentOffset -= 1; // Скорость движения
        wrapper.style.transform = `translateX(${currentOffset}px)`;

        // Если первая карточка полностью ушла
        if (Math.abs(currentOffset) >= itemWidth) {
            wrapper.style.transition = "none"; // Убираем анимацию
            currentOffset = 0; // Сбрасываем позицию
            wrapper.appendChild(wrapper.firstElementChild); // Перемещаем первый элемент в конец
            wrapper.style.transform = `translateX(0px)`;
        }
    }

    // Запуск прокрутки
    function startScroll() {
        scrollInterval = setInterval(animate, 16); // Каждые ~16ms (60fps)
    }

    // Остановка прокрутки
    function stopScroll() {
        clearInterval(scrollInterval);
    }

    // Добавляем обработчики событий на контейнер
    const container = document.querySelector(".shoes-list");
    container.addEventListener("mouseenter", stopScroll); // Остановка при наведении
    container.addEventListener("mouseleave", startScroll); // Перезапуск при уходе курсора

    // Запускаем анимацию при загрузке
    startScroll();
});



document.addEventListener("DOMContentLoaded", () => {
    const aboutBtn = document.getElementById("about-btn");
    const modal = document.getElementById("about-modal");
    const closeBtn = document.querySelector(".close-btn");

    // Проверка, если окно уже было открыто в текущей сессии
    if (sessionStorage.getItem('modalOpened') === 'true') {
        modal.style.display = 'flex'; // Показываем модальное окно
    } else {
        modal.style.display = 'none'; // Скрываем модальное окно
    }

    // Открытие модального окна
    aboutBtn.addEventListener("click", () => {
        modal.style.display = "flex"; // Показываем модальное окно
        sessionStorage.setItem('modalOpened', 'true'); // Сохраняем состояние окна
    });

    // Закрытие модального окна
    closeBtn.addEventListener("click", () => {
        modal.style.display = "none"; // Скрываем модальное окно
        sessionStorage.setItem('modalOpened', 'false'); // Сохраняем, что окно закрыто
    });

    // Закрытие модального окна при клике вне его
    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none"; // Скрываем модальное окно
            sessionStorage.setItem('modalOpened', 'false'); // Сохраняем, что окно закрыто
        }
    });
});


document.addEventListener("DOMContentLoaded", () => {
    const contactBtn = document.getElementById("contact-btn");
    const contactModal = document.getElementById("contact-modal");
    const contactCloseBtn = document.getElementById("contact-close-btn");

    // Проверка, если окно уже было открыто в текущей сессии
    if (sessionStorage.getItem('contactModalOpened') === 'true') {
        contactModal.style.display = 'flex'; // Показываем модальное окно
    } else {
        contactModal.style.display = 'none'; // Скрываем модальное окно
    }

    // Открытие модального окна
    contactBtn.addEventListener("click", () => {
        contactModal.style.display = "flex"; // Показываем модальное окно
        sessionStorage.setItem('contactModalOpened', 'true'); // Сохраняем состояние окна
    });

    // Закрытие модального окна
    contactCloseBtn.addEventListener("click", () => {
        contactModal.style.display = "none"; // Скрываем модальное окно
        sessionStorage.setItem('contactModalOpened', 'false'); // Сохраняем, что окно закрыто
    });

    // Закрытие модального окна при клике вне его
    window.addEventListener("click", (event) => {
        if (event.target === contactModal) {
            contactModal.style.display = "none"; // Скрываем модальное окно
            sessionStorage.setItem('contactModalOpened', 'false'); // Сохраняем, что окно закрыто
        }
    });
});



document.addEventListener("DOMContentLoaded", () => {
    // Для кнопки "Доставка"
    const deliveryBtn = document.getElementById("delivery-btn");
    const deliveryModal = document.getElementById("delivery-modal");
    const deliveryCloseBtn = document.getElementById("delivery-close-btn");

    if (sessionStorage.getItem('deliveryModalOpened') === 'true') {
        deliveryModal.style.display = 'flex';
    } else {
        deliveryModal.style.display = 'none';
    }

    deliveryBtn.addEventListener("click", () => {
        deliveryModal.style.display = "flex";
        sessionStorage.setItem('deliveryModalOpened', 'true');
    });

    deliveryCloseBtn.addEventListener("click", () => {
        deliveryModal.style.display = "none";
        sessionStorage.setItem('deliveryModalOpened', 'false');
    });

    window.addEventListener("click", (event) => {
        if (event.target === deliveryModal) {
            deliveryModal.style.display = "none";
            sessionStorage.setItem('deliveryModalOpened', 'false');
        }
    });

    // Для кнопки "Возврат и обмен"
    const returnBtn = document.getElementById("return-btn");
    const returnModal = document.getElementById("return-modal");
    const returnCloseBtn = document.getElementById("return-close-btn");

    if (sessionStorage.getItem('returnModalOpened') === 'true') {
        returnModal.style.display = 'flex';
    } else {
        returnModal.style.display = 'none';
    }

    returnBtn.addEventListener("click", () => {
        returnModal.style.display = "flex";
        sessionStorage.setItem('returnModalOpened', 'true');
    });

    returnCloseBtn.addEventListener("click", () => {
        returnModal.style.display = "none";
        sessionStorage.setItem('returnModalOpened', 'false');
    });

    window.addEventListener("click", (event) => {
        if (event.target === returnModal) {
            returnModal.style.display = "none";
            sessionStorage.setItem('returnModalOpened', 'false');
        }
    });
});



document.addEventListener("DOMContentLoaded", () => {
    // Для кнопки "Политика конфиденциальности"
    const privacyBtn = document.getElementById("privacy-btn");
    const privacyModal = document.getElementById("privacy-modal");
    const privacyCloseBtn = document.getElementById("privacy-close-btn");

    if (sessionStorage.getItem('privacyModalOpened') === 'true') {
        privacyModal.style.display = 'flex';
    } else {
        privacyModal.style.display = 'none';
    }

    privacyBtn.addEventListener("click", () => {
        privacyModal.style.display = "flex";
        sessionStorage.setItem('privacyModalOpened', 'true');
    });

    privacyCloseBtn.addEventListener("click", () => {
        privacyModal.style.display = "none";
        sessionStorage.setItem('privacyModalOpened', 'false');
    });

    window.addEventListener("click", (event) => {
        if (event.target === privacyModal) {
            privacyModal.style.display = "none";
            sessionStorage.setItem('privacyModalOpened', 'false');
        }
    });
});



document.addEventListener("DOMContentLoaded", () => {
    let lastScrollTop = 0; // Инициализация переменной для отслеживания предыдущего положения прокрутки
    const secondRectangle = document.querySelector('.second-rectangle');
    const grayLine = document.querySelector('.gray-line');

    // Обработчик прокрутки
    window.addEventListener('scroll', function() {
        let currentScroll = window.pageYOffset || document.documentElement.scrollTop; // Текущая позиция прокрутки

        // Если прокручиваем вниз
        if (currentScroll > lastScrollTop) {
            secondRectangle.classList.add('hidden');  // Скрываем второй прямоугольник
            grayLine.classList.add('hidden');  // Скрываем серую линию
            console.log("Elements hidden"); // Для отладки
        } else {
            // Если прокручиваем вверх
            secondRectangle.classList.remove('hidden');  // Показываем второй прямоугольник
            grayLine.classList.remove('hidden');  // Показываем серую линию
            console.log("Elements visible"); // Для отладки
        }

        // Обновляем lastScrollTop для следующей итерации
        lastScrollTop = currentScroll <= 0 ? 0 : currentScroll;
    });
});


// Функция для отправки формы при нажатии Enter
  document.querySelector('.search-input').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
      this.form.submit(); // Отправка формы при нажатии Enter
    }
  });


