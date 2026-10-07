-- Демо-данные каталога. Картинки лежат в src/main/resources/static/images,
-- названия и цены условные: это наполнение для витрины, а не реальные поставки.
-- Применять после db/schema.sql:  psql -d StepWave -f src/main/resources/db/seed-demo.sql

INSERT INTO product (name, description, price, image_url, brand_id, category_id, composition, is_new_arrival, is_on_sale)
VALUES
    ('Runner Low',        'Перфорированная кожа, плоская подошва на пене',        129.00, '/static/images/kicks/1.png', 1, 1, 'Кожа, текстиль',        true,  false),
    ('Runner Mid',        'Средний профиль, замшевые вставки на пятке',           149.00, '/static/images/kicks/2.png', 1, 1, 'Замша, нейлон',        true,  false),
    ('Court Classic',     'Теннисная силуэт на вулканизированной резине',         99.00,  '/static/images/kicks/3.png', 2, 1, 'Кожа, резина',         false, true),
    ('Trail Grip',        'Рифлёный протектор и усиленный носок',                 179.00, '/static/images/kicks/4.png', 2, 2, 'Текстиль, TPU',        false, false),
    ('City Knit',         'Вязаный верх без швов, стелька с памятью формы',       139.00, '/static/images/kicks/5.png', 3, 1, 'Вязаный текстиль',     true,  false),
    ('Retro High',        'Высокий кед на шнуровке до щиколотки',                 159.00, '/static/images/kicks/6.png', 3, 1, 'Кожа, текстиль',        false, true),
    ('Slip On',           'Эластичные вставки по бокам, литая подошва',           89.00,  '/static/images/kicks/7.png', 4, 1, 'Текстиль, EVA',        false, false),
    ('Air Icon',          'Юбилейная пара с воздушной подушкой в пятке',          219.00, '/static/images/air.png',     4, 3, 'Кожа, полиуретан',     true,  false)
ON CONFLICT DO NOTHING;
