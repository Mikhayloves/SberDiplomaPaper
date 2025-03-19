# SberDiplomaPaper

Проект `SberDiplomaPaper` представляет собой веб-приложение для управления заказами, продуктами и категориями. Включает в себя аутентификацию пользователей, работу с JWT-токенами, CRUD-операции для продуктов, категорий и заказов, а также интеграцию с почтовым сервисом для подтверждения регистрации.

## Технологии

- **Java 21**
- **Spring Boot 3.4.3**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **PostgreSQL**
- **Lombok**
- **ModelMapper**
- **JavaMailSender**
- **Hibernate**
- **Docker**

---

## Установка и запуск


### Требования

- Установленная **Java 21**.
- Установленная **PostgreSQL**.
- Настройте почтовый сервис (например, Yandex или Gmail) для отправки писем.

Напримере проекта все поднимаеться в [**`Docker`**](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/docker/db/compose.yml) в файле compose.yml

## Структура проекта:

![photo_2025-02-14_11-49-19.jpg](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/photo/2025-03-19_13-13-42.png)

В данном проекте представлена архитектура с тремя основными уровнями.

1. Контроллеры (Controller): 
   - Они находятся в пакете  [**`controller`**](https://github.com/Mikhayloves/SberDiplomaPaper/tree/main/src/main/java/ru/Sber/SberDiplomaPaper/controller)
   - Этот уровень отвечает за обработку HTTP-запросов и отправку ответов клиентам. Контроллеры получают данные от клиента, отправляют их на бизнес-логику и передают обратно результат.

2. Сервисный слой (Service):
   - Реализован в пакете  [**`service`**](https://github.com/Mikhayloves/SberDiplomaPaper/tree/main/src/main/java/ru/Sber/SberDiplomaPaper/service)
   - Здесь выполняется основная бизнес-логика приложения. Сервисы обрабатывают данные, полученные от контроллеров, взаимодействуют с репозиториями для получения или изменения данных и возвращают результат обратно контроллерам.

3. Уровень доступа к данным (Repository):
   - Находится в пакете [**`repository`**](https://github.com/Mikhayloves/SberDiplomaPaper/tree/main/src/main/java/ru/Sber/SberDiplomaPaper/repository)
   - Этот уровень отвечает за взаимодействие с базой данных. Репозитории предоставляют интерфейсы для выполнения CRUD-операций и поиска данных.


## Проектирование БД

![photo_2025-02-14_11-49-19.jpg](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/photo/Таблица.png)

### Пользователь (`User`)

Сущность [**`User`**]( https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/src/main/java/ru/Sber/SberDiplomaPaper/domain/model/User.java) представляет собой модель пользователя в системе. Она включает следующие поля:

- **id** (Long): Уникальный идентификатор пользователя.
- **name** (String): Имя пользователя.
- **email** (String): Электронная почта пользователя.
- **passwordHash** (String): Хэш пароля пользователя.
- **role** (UserRole): Роль пользователя (например, `USER`, `ADMIN`).
- **orders** (List<Order>): Список заказов пользователя.
- **bucketProducts** (List<BucketProduct>): Список продуктов в корзине пользователя.
- **confirmTokens** (List<ConfirmToken>): Список токенов для подтверждения регистрации.
- **enabled** (Boolean): Флаг, указывающий, активирован ли пользователь.
- **createdAt** (Timestamp): Время создания пользователя.
- **updatedAt** (Timestamp): Время последнего обновления пользователя.

Любая работа с сущностью осуществляеться через [**`DTO`**](https://github.com/Mikhayloves/SberDiplomaPaper/tree/main/src/main/java/ru/Sber/SberDiplomaPaper/domain/dto)

**DTO (Data Transfer Object)** в Java — это объект, который используется для передачи данных между подсистемами приложения. 
Он содержит только поля и getter/setter методы для доступа к ним. 
DTO не содержит бизнес-логики и используется только для передачи данных.

# UserRegistrationDto

Класс [**`UserRegistrationDto`**](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/src/main/java/ru/Sber/SberDiplomaPaper/domain/dto/auth/UserRegistrationDto.java) используется для передачи данных о регистрации пользователя в системе. Он содержит поля для email, пароля, подтверждения пароля и имени пользователя. Все поля валидируются с использованием аннотаций из библиотеки `javax.validation`.

---

## Описание полей

### 1. **email**
- **Тип:** `String`
- **Описание:** Электронная почта пользователя.
- **Валидация:**
    - Поле не может быть пустым (`@NotNull`).
    - Должно соответствовать формату email (`@Pattern`).

### 2. **password**
- **Тип:** `String`
- **Описание:** Пароль пользователя.
- **Валидация:**
    - Поле не может быть пустым (`@NotNull`).
    - Должно содержать от 8 до 16 символов, включая буквы и цифры (`@Pattern`).

### 3. **passwordConfirm**
- **Тип:** `String`
- **Описание:** Подтверждение пароля.
- **Валидация:**
    - Поле не может быть пустым (`@NotNull`).

### 4. **name**
- **Тип:** `String`
- **Описание:** Имя пользователя.
- **Валидация:**
    - Поле не может быть пустым (`@NotNull`).
    - Должно содержать от 2 до 50 символов и состоять только из букв латинского алфавита (`@Pattern`).

---

## Пример JSON-запроса

Пример JSON-запроса для регистрации пользователя:

```json
{
  "email": "example@example.com",
  "password": "password123",
  "passwordConfirm": "password123",
  "name": "JohnDoe"
}
```

## Пример JSON-представления пользователя:

```json
{
  "id": 1,
  "name": "Иван Иванов",
  "email": "ivan@example.com",
  "passwordHash": "hashedpassword123",
  "role": "USER",
  "enabled": true,
  "createdAt": "2023-10-01T12:00:00",
  "updatedAt": "2023-10-01T12:00:00"
}
```

## Что происходит при валидации:

- Если данные не соответствуют требованиям (например, email невалидный или пароль слишком короткий), то метод контроллера генерирует соответствующие исключение.

- Если все данные корректны, они будут переданы в сервисный слой для дальнейшей обработки (например, сохранения пользователя в базе данных).

### Для подтверждениz регистрации пользователя JavaMailSender.

Система Mail в проекте:

Используется для отправки писем с подтверждением регистрации.

Связана с процессом регистрации через генерацию токенов и отправку писем.

Позволяет пользователю активировать учетную запись, переходя по ссылке из письма. 

Пример работы системы:

### 1. Пользователь регистрируется:

Вызывается метод register в AuthServiceImpl [**`AuthServiceImpl`**](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/src/main/java/ru/Sber/SberDiplomaPaper/service/auth/AuthServiceImpl.java)

### 2. Формирование письма:

В  [**` RegistrationMailGenerator`**](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/src/main/java/ru/Sber/SberDiplomaPaper/service/mail/RegistrationMailGenerator.java) создается HTML-шаблон письма

### 3. Отправка письма:

В   [**`EmailSenderServiceImpl`**](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/src/main/java/ru/Sber/SberDiplomaPaper/service/mail/EmailSenderServiceImpl.java) вызывается метод sendEmail

Отправка на почту: 


![photo_2025-02-14_11-49-19.jpg](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/photo/2025-03-19_12-39-18.png)


# Защита данных пользователя осуществляеться с помошью JWT Token

Что такое JWT?

JWT (Json Web Token) — ключ аутентификации пользователя. Используется для запросов к защищенным методам API.

Для чего нужны JWT: чтобы не передавать учетные данные пользователя с каждым запросом к серверу.

Так же в при успешной аутентификации пользователя

Плюсы такого подхода:

- Токены доступа имеют ограниченный срок годности (обычно ~15 минут)
- Не тратяться дополнительные ресусурсы и время при проверке данных

Токен формируется после успешной аутентификации пользователя, это происходит в методе login контроллера [**`AuthController`**](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/src/main/java/ru/Sber/SberDiplomaPaper/controller/AuthController.java), а сам token генерируется в классе  [**`JwtServiceImpl`**](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/src/main/java/ru/Sber/SberDiplomaPaper/service/jwt/JwtServiceImpl.java)
Вот как это работает:

Пользователь отправляет запрос на вход (логин), передавая свои учетные данные (email и пароль).

Сервер проверяет учетные данные:

Если данные верны, сервер вызывает метод generateAccessToken (и generateRefreshToken, если используется) для создания JWT токена.

Токен возвращается клиенту в ответе на запрос. 

### Пример создания User:
![photo_2025-02-14_11-49-19.jpg](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/photo/users.png)

#  [**`API Endpoints`**](https://github.com/Mikhayloves/SberDiplomaPaper/tree/main/src/main/java/ru/Sber/SberDiplomaPaper/controller)

## Аутентификация ссылка
- Регистрация: POST /api/auth/registration
- Логин: POST /api/auth/login
- Подтверждение регистрации: GET /api/auth/confirm?token=ваш-токен

## Продукты ссылка
- Создание продукта: POST /api/products/create
- Редактирование продукта: PUT /api/products/edit
- Удаление продукта: DELETE /api/products/delete/{id}
- Получение списка продуктов: GET /api/products/list
- Получение продукта по ID: GET /api/products/{id}
- Привязка категории к продукту: POST /api/products/attachCategory
- Отвязка категории от продукта: POST /api/products/detachCategory

## Категории ссылка
- Создание категории: POST /api/categories/create
- Редактирование категории: PUT /api/categories/edit
- Удаление категории: DELETE /api/categories/delete/{id}
- Получение списка категорий: GET /api/categories/list
- Получение категории по ID: GET /api/categories/{id}

## Заказы ссылка
- Создание заказа: POST /api/orders/create
- Обновление статуса заказа: PUT /api/orders/update
- Получение заказа по ID: GET /api/orders/{id}
- Получение списка заказов пользователя: GET /api/orders/{user_id}/list

## Корзина ссылка
- Добавление продукта в корзину: POST /api/bucketProducts/add
- Обновление количества продукта в корзине: PUT /api/bucketProducts/update/{id}
- Удаление продукта из корзины: DELETE /api/bucketProducts/delete/{id}
- Получение списка продуктов в корзине: GET /api/bucketProducts/list/{userId}

_______________________

## Данные в БД 

Данные заполняются с помощью вспомогательного класса DataSeeder. 
Это класс, который используется для заполнения базы данных начальными данными (seed data). 
Он реализует интерфейс CommandLineRunner, что позволяет ему автоматически выполняться при запуске Spring Boot приложения.

За создание самих продуктов отвечает библеотека Faker.  
Эта библиотека в Java, которая используется для генерации фейковых данных. 
Она позволяет создавать случайные значения, такие как имена, адреса, номера телефонов, даты, e-mail и много других типов данных. 
Это полезно для тестирования, демонстраций программного обеспечения или при необходимости заполнить базы данных тестовыми данными.

![photo_2025-02-14_11-49-19.jpg](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/photo/продукты.png)



## Тестирование
______________________

Тестирование использовалось с помощью JUnit & Mockito

Для знакомства с тестами репозитрного слоя : 

![photo_2025-02-14_11-49-19.jpg](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/photo/тест%20репозиторного%20слоя.png)


Для знакомства с тестами сервисов : 

![photo_2025-02-14_11-49-19.jpg](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/photo/User%20сервис%20тест.png)

![photo_2025-02-14_11-49-19.jpg](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/photo/сервис%20продукт.png)



POSTMAN фото 

## Docker 
**Docker** - это платформа для разработки, доставки и запуска приложений в контейнерах. Контейнеры - это среда, включающие в себя необходимые зависимости, библиотеки и файлы, что обеспечивает легкость при развертывании приложения на различных машинах.

![photo_2025-02-14_11-49-19.jpg](https://github.com/Mikhayloves/SberDiplomaPaper/blob/main/photo/Докер1.png)




