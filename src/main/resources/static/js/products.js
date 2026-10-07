// Показ/скрытие выпадающего меню для языка
document.getElementById('language-btn')?.addEventListener('click', function (event) {
    const dropdown = document.getElementById('language-dropdown');
    dropdown.classList.toggle('show');
    event.stopPropagation(); // Останавливаем всплытие события
});

// Закрытие всех выпадающих окон при клике вне
document.addEventListener('click', function (event) {
    // Закрываем меню языка
    const dropdown = document.getElementById('language-dropdown');
    if (!dropdown.contains(event.target) && !event.target.closest('#language-btn')) {
        dropdown.classList.remove('show');
    }

    // Закрываем меню сортировки
    const sortOptions = document.getElementById('sortOptions');
    if (!sortOptions.contains(event.target) && !event.target.closest('#sortToggle')) {
        sortOptions.classList.remove('show');
    }
});

// Логика для кнопки "Помощь"
const helpBtn = document.getElementById('helpBtn');
const helpDropdown = document.getElementById('help-dropdown');
if (helpBtn && helpDropdown) {
    helpBtn.addEventListener('mouseenter', () => helpDropdown.style.display = 'block');
    helpBtn.addEventListener('mouseleave', () => {
        setTimeout(() => {
            if (!helpDropdown.matches(':hover')) helpDropdown.style.display = 'none';
        }, 200);
    });

    helpDropdown.addEventListener('mouseenter', () => helpDropdown.style.display = 'block');
    helpDropdown.addEventListener('mouseleave', () => helpDropdown.style.display = 'none');
}

// Логика показа/скрытия фильтров
function toggleFilters() {
    const filters = document.getElementById('filters'); // Панель фильтров
    const productsContainer = document.getElementById('products'); // Контейнер продуктов
    const filterToggle = document.getElementById('filterToggle'); // Кнопка скрытия/показа фильтров

    // Переключаем видимость фильтров
    const isHidden = filters.classList.toggle('hidden');

    // Изменяем отступ для контейнера продуктов
    productsContainer.style.marginLeft = isHidden ? '0' : '270px';

    // Меняем текст кнопки
    filterToggle.textContent = isHidden ? 'Показать фильтры' : 'Скрыть фильтры';
}


// Логика сортировки продуктов
function sortProducts(order) {
    const productsContainer = document.querySelector('.product-grid'); // Сетка продуктов
    const products = Array.from(productsContainer.children); // Все карточки продуктов

    // Сортировка по цене
    products.sort((a, b) => {
        const priceA = parseFloat(a.querySelector('.product-price').textContent.replace(/[^\d.]/g, ''));
        const priceB = parseFloat(b.querySelector('.product-price').textContent.replace(/[^\d.]/g, ''));

        return order === 'lowToHigh' ? priceA - priceB : priceB - priceA;
    });

    // Обновляем сетку продуктов
    productsContainer.innerHTML = ''; // Очищаем контейнер
    products.forEach(product => productsContainer.appendChild(product)); // Добавляем отсортированные продукты
}

// Логика показа/скрытия сортировки
document.getElementById('sortToggle')?.addEventListener('click', function (event) {
    const sortOptions = document.getElementById('sortOptions');
    sortOptions.classList.toggle('show'); // Переключаем видимость
    event.stopPropagation(); // Останавливаем всплытие события
});

// Закрытие меню сортировки при клике вне
document.addEventListener('click', function (event) {
    const sortOptions = document.getElementById('sortOptions');
    if (!sortOptions.contains(event.target)) {
        sortOptions.classList.remove('show'); // Скрываем меню сортировки
    }
});

// Тоггл опций фильтров
function toggleFilterOptions(element) {
    const options = element.nextElementSibling; // Получаем следующий элемент (опции)
    options.classList.toggle('hidden'); // Переключаем видимость
    element.classList.toggle('active'); // Добавляем активный стиль для заголовка
}



// Функция для обновления состояния чекбоксов при загрузке страницы
function restoreCheckboxState() {
    const urlParams = new URLSearchParams(window.location.search);

    // Для каждого чекбокса проверяем, есть ли его значение в URL
    document.querySelectorAll('input[name="category"]').forEach(input => {
        if (urlParams.getAll('categoryId').includes(input.value)) {
            input.checked = true;  // Если значение есть в URL, ставим галочку
        }
    });

    // Для других фильтров, например, брендов
    document.querySelectorAll('input[name="brand"]').forEach(input => {
        if (urlParams.getAll('brandId').includes(input.value)) {
            input.checked = true;  // Если значение есть в URL, ставим галочку
        }
    });

    // Для фильтра по размеру и цвету можно аналогично добавить
}

// Вызов функции после загрузки страницы
window.onload = restoreCheckboxState;




// Функция для сохранения состояния фильтров
function saveFilterState() {
    // Получаем все фильтры (состояния аккордеонов)
    const filterCategories = document.querySelectorAll('.filter-category');

    filterCategories.forEach(category => {
        const id = category.querySelector('.filter-title').innerText.trim(); // Заголовок фильтра как id (Пол, Цвет, Размер, Брэнд)
        const isOpen = category.querySelector('.filter-options').classList.contains('hidden') === false; // Проверяем, открыто ли окно

        // Сохраняем состояние (открыто или закрыто)
        localStorage.setItem(id, isOpen ? 'open' : 'closed');
    });
}

// Функция для восстановления состояния фильтров
function restoreFilterState() {
    const filterCategories = document.querySelectorAll('.filter-category');

    filterCategories.forEach(category => {
        const id = category.querySelector('.filter-title').innerText.trim(); // Заголовок фильтра как id (Пол, Цвет, Размер, Брэнд)
        const state = localStorage.getItem(id); // Получаем сохраненное состояние из localStorage

        if (state === 'open') {
            category.querySelector('.filter-options').classList.remove('hidden'); // Открываем фильтр
        } else {
            category.querySelector('.filter-options').classList.add('hidden'); // Закрываем фильтр
        }
    });
}

// Функция для переключения видимости фильтров
function toggleFilterOptions(titleElement) {
    const filterOptions = titleElement.nextElementSibling; // Соседний элемент (ul с опциями)

    // Переключаем класс 'hidden' для отображения/скрытия фильтров
    filterOptions.classList.toggle('hidden');

    // Сохраняем состояние после каждого изменения
    saveFilterState();
}

// Восстановление состояния фильтров при загрузке страницы
window.onload = function() {
    restoreFilterState(); // Восстанавливаем состояние аккордеонов
    restoreCheckboxState(); // Восстанавливаем состояние чекбоксов (если вы используете это)
};





// Функция для обновления URL в зависимости от выбранных фильтров
function updateFilters() {
    let filters = [];
    let baseUrl = '/products'; // Начальный путь без фильтрации

    // Получаем текущие параметры из URL
    const urlParams = new URLSearchParams(window.location.search);

    // Получение выбранных фильтров для категории (например, пол)
    const selectedCategories = [];
    document.querySelectorAll('input[name="category"]:checked').forEach(input => {
        selectedCategories.push(input.value);
    });

    // Если categoryId уже существует в URL, добавляем новые значения
    const existingCategoryIds = urlParams.getAll('categoryId');
    selectedCategories.forEach(category => {
        if (!existingCategoryIds.includes(category)) {
            urlParams.append('categoryId', category); // Добавляем новый categoryId
        }
    });

    // Получение выбранных фильтров для бренда
    document.querySelectorAll('input[name="brand"]:checked').forEach(input => {
        filters.push(`brandId=${input.value}`);  // Добавляем фильтр по бренду
    });

    // Получение выбранных фильтров для других категорий (например, цвет, размер)
    document.querySelectorAll('input[name="color"]:checked').forEach(input => {
        filters.push(`colorId=${input.value}`);  // Добавляем фильтр по цвету
    });

    document.querySelectorAll('input[name="size"]:checked').forEach(input => {
        filters.push(`sizeId=${input.value}`);  // Добавляем фильтр по размеру
    });

    // Формируем новый URL с фильтрами
    const newUrl = filters.length > 0 || urlParams.toString() ? `${baseUrl}/filter?${urlParams.toString()}&${filters.join('&')}` : baseUrl;

    // Обновляем URL в браузере
    window.history.pushState({}, '', newUrl);

    // Перезагружаем страницу с новым URL (сервер обработает и вернет новую страницу)
    location.reload();
}

// Добавление событий на фильтры
document.querySelectorAll('input[name="category"], input[name="brand"], input[name="color"], input[name="size"]').forEach(input => {
    input.addEventListener('change', updateFilters);  // При изменении фильтров обновляем страницу
});


// Функция для сохранения состояния фильтров
function saveFilterState() {
    // Получаем все фильтры (состояния аккордеонов)
    const filterCategories = document.querySelectorAll('.filter-category');

    filterCategories.forEach(category => {
        const id = category.querySelector('.filter-title').innerText.trim(); // Заголовок фильтра как id (Пол, Цвет, Размер, Брэнд)
        const isOpen = category.querySelector('.filter-options').classList.contains('hidden') === false; // Проверяем, открыто ли окно

        // Сохраняем состояние (открыто или закрыто)
        localStorage.setItem(id, isOpen ? 'open' : 'closed');
    });
}

// Функция для восстановления состояния фильтров
function restoreFilterState() {
    const filterCategories = document.querySelectorAll('.filter-category');

    filterCategories.forEach(category => {
        const id = category.querySelector('.filter-title').innerText.trim(); // Заголовок фильтра как id (Пол, Цвет, Размер, Брэнд)
        const state = localStorage.getItem(id); // Получаем сохраненное состояние из localStorage

        if (state === 'open') {
            category.querySelector('.filter-options').classList.remove('hidden'); // Открываем фильтр
        } else {
            category.querySelector('.filter-options').classList.add('hidden'); // Закрываем фильтр
        }
    });
}

// Функция для переключения видимости фильтров
function toggleFilterOptions(titleElement) {
    const filterOptions = titleElement.nextElementSibling; // Соседний элемент (ul с опциями)

    // Переключаем класс 'hidden' для отображения/скрытия фильтров
    filterOptions.classList.toggle('hidden');

    // Сохраняем состояние после каждого изменения
    saveFilterState();
}

// Восстановление состояния фильтров при загрузке страницы
window.onload = function() {
    restoreFilterState(); // Восстанавливаем состояние аккордеонов
    restoreCheckboxState(); // Восстанавливаем состояние чекбоксов (если вы используете это)
};



// Функция для отправки формы при нажатии Enter
  document.querySelector('.search-input').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
      this.form.submit(); // Отправка формы при нажатии Enter
    }
  });



function goToProductDetails(productId) {
    window.location.href = "http://localhost:8082/productDetails/" + productId;
}