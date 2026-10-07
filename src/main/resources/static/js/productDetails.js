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



// Получаем элементы модального окна и кнопки
var modal = document.getElementById("compositionModal");
var btn = document.querySelector(".buy-now-button");
var closeBtn = document.getElementById("closeModal");

// Функция для открытия модального окна
function openCompositionModal() {
  modal.style.display = "block";
}

// Функция для закрытия модального окна
function closeCompositionModal() {
  modal.style.display = "none";
}

// Слушаем нажатие на кнопку закрытия "X"
closeBtn.addEventListener("click", closeCompositionModal);

// Слушаем клик вне модального окна для закрытия
window.addEventListener("click", function(event) {
  if (event.target === modal) {
    closeCompositionModal();
  }
});

// Убедитесь, что модальное окно скрыто при загрузке страницы
window.onload = function() {
  modal.style.display = "none"; // Убедитесь, что окно скрыто при первоначальной загрузке страницы
};


function changeMainImage(imageElement) {
    const mainImage = document.querySelector(".main-product-image"); // Находим главное изображение
    if (mainImage) {
        // Добавляем класс для анимации исчезновения
        mainImage.classList.add('fade-out');

        // После завершения анимации исчезновения меняем изображение
        setTimeout(() => {
            // Сохраняем текущий src главного изображения
            const tempSrc = mainImage.src;

            // Меняем src главного изображения на src миниатюры
            mainImage.src = imageElement.src;

            // Устанавливаем старый src главного изображения в миниатюру
            imageElement.src = tempSrc;

            // Убираем класс fade-out и добавляем fade-in
            mainImage.classList.remove('fade-out');
            mainImage.classList.add('fade-in');

            // Через небольшой промежуток времени удаляем fade-in
            setTimeout(() => mainImage.classList.remove('fade-in'), 500);
        }, 500); // Длительность анимации исчезновения
    }
}

// Добавляем обработчик события для каждой миниатюры
document.querySelectorAll('.thumbnail-button img').forEach(thumbnail => {
    thumbnail.addEventListener('click', function() {
        changeMainImage(this); // Передаем элемент миниатюры
    });
});



document.addEventListener("DOMContentLoaded", () => {
    const zoomLens = document.getElementById("zoomLens");
    const zoomContainer = document.querySelector(".image-zoom-container");
    const mainImage = document.getElementById("mainSneakerImage");

    let animationTriggered = false; // Флаг для отслеживания запуска анимации

    // Обработчик движения мыши
    zoomContainer.addEventListener("mousemove", (e) => {
        const rect = zoomContainer.getBoundingClientRect();
        const naturalWidth = mainImage.naturalWidth;
        const naturalHeight = mainImage.naturalHeight;

        let x = e.clientX - rect.left;
        let y = e.clientY - rect.top;

        const lensX = Math.max(0, Math.min(x - zoomLens.offsetWidth / 2, rect.width - zoomLens.offsetWidth));
        const lensY = Math.max(0, Math.min(y - zoomLens.offsetHeight / 2, rect.height - zoomLens.offsetHeight));

        zoomLens.style.left = `${lensX}px`;
        zoomLens.style.top = `${lensY}px`;

        const backgroundX = (x / rect.width) * naturalWidth - zoomLens.offsetWidth / 2;
        const backgroundY = (y / rect.height) * naturalHeight - zoomLens.offsetHeight / 2;

        zoomLens.style.backgroundImage = `url(${mainImage.src})`;
        zoomLens.style.backgroundSize = `${naturalWidth}px ${naturalHeight}px`;
        zoomLens.style.backgroundPosition = `-${backgroundX}px -${backgroundY}px`;

        zoomLens.style.display = "block";
        mainImage.style.filter = "blur(3px)";

        // Запускаем анимацию, если она еще не запущена
        if (!animationTriggered) {
            animationTriggered = true; // Устанавливаем флаг
            mainImage.classList.add("image-distorted");
            mainImage.addEventListener("animationend", () => {
                mainImage.classList.remove("image-distorted"); // Сбрасываем класс после завершения
            });
        }
    });

    // Обработчик ухода мыши
    zoomContainer.addEventListener("mouseleave", () => {
        zoomLens.style.display = "none";
        mainImage.style.filter = "none"; // Убираем размытие
        animationTriggered = false; // Сбрасываем флаг при уходе мыши
    });
});






