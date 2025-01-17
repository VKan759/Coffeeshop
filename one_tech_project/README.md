### Описание
Это консольное приложение для кофейни
- **Spring Framework** для управления зависимостями и конфигурирования бинов.
- **DTO** классы для представления моделей данных.
- **Репозитории** с паттерном **Builder** и **Lombok** для упрощения создания объектов.
- **Сервисный слой** с внедрением зависимостей и использованием **Stream API** для бизнес-логики.

### Структура проекта
- `Coffee`: DTO класс для описания кофе.
- `OrderItem`: DTO класс для описания позиции заказа (кофе + количество).
- `Order`: DTO класс для описания заказа (список позиций + общая сумма).
- `CoffeeRepository` и `OrderRepository`: интерфейсы и их реализации для управления данными.
- `CoffeeShopService`: сервисный слой для добавления кофе и оформления заказов.
- `AppConfig`: класс конфигурации Spring для создания бинов.
- `CoffeeShopApplication`: основной класс приложения.