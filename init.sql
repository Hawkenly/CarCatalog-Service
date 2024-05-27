-- Создание таблицы для автомобилей
CREATE TABLE cars (
                      id BIGINT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      popular VARCHAR(255) NOT NULL,
                      country VARCHAR(255) NOT NULL
);

-- Создание таблицы для моделей автомобилей
CREATE TABLE models (
                        id BIGINT PRIMARY KEY,
                        model_name VARCHAR(255) NOT NULL,
                        car_id BIGINT,
                        FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE
);

-- Создание таблицы для цветов автомобилей
CREATE TABLE colors (
                        id BIGINT PRIMARY KEY,
                        color_name VARCHAR(255) NOT NULL
);

-- Создание таблицы для связи автомобилей и цветов (многие ко многим)
CREATE TABLE car_color (
                           car_id BIGINT,
                           color_id BIGINT,
                           PRIMARY KEY (car_id, color_id),
                           FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE,
                           FOREIGN KEY (color_id) REFERENCES colors(id) ON DELETE CASCADE
);
