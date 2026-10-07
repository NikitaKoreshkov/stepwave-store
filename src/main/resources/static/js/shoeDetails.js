document.addEventListener('DOMContentLoaded', function () {
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
});






