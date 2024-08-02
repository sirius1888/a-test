**Тестовое задание**

Описание
В файле "MobileEditProfile.json" содержится структура JSON для редактирования профиля пользователя. В ней всего две страницы:

1. Первая страница отвечает за отображение личной информации пользователя.
2. Вторая страница отвечает за изменение "номера телефона" пользователя.

Объекты модели профиля
    - Profile elements – Перечень элементов профиля.
    - Id – Уникальный идентификатор элемента.
    - Column – Страница с вертикальными элементами.
    - Row – Перечень элементов в горизонтальном виде.
    - Hide – Отображение элемента.
        o Примечание: если указано значение false, элемент отображается; если true, элемент скрыт.
    - Disabled – Активность элемента.
        o Примечание: если указано значение false, элемент активен; если true, элемент не активен.
    - Last name – Поле ввода фамилии.
    - Gender – Список выбора ("Picker") для выбора пола.
    - Birthday – Установить дату рождения ("Date picker").
    - Button – Кнопка.
        o Action – Событие кнопки.

Примечание: смотреть раздел "События модели по Action".
    События модели по Action
    - Hide element action – Скрыть элемент.
    - Show element action – Показать элемент.
    - Disable element action – Отключить элемент.
    - Enable element action – Включить элемент.
    - Change value by value action – Изменение значения на другое в конкретном элементе.
    - Change value by reference action – Изменение значения в конкретном элементе по идентификатору.
    - Get validation code info action – Получение информации о валидации кода.
    - Check validation code action – Проверка введенного кода подтверждения.
    - Reload data action – Вернуть исходные данные.
    - Save customer action – Сохранить изменения.

**Требования к реализации мобильного приложения**

1. Необходимо реализовать рабочее мобильное приложение с использованием данной структуры JSON.
2. На странице редактирования личной информации пользователь должен иметь возможность вводить информацию, выбирать из списка или указывать данные, например, через элементы Picker или DatePicker.
3. В мобильном приложении должна использоваться архитектура MVVM или MVI.
4. Отобразить следующие поля:
5. Button – Кнопка.
6. Last name – Поле ввода фамилии.
7. Gender – Список выбора ("Picker") для выбора пола.
8. Birthday – Установить дату рождения ("Date picker").
9. Остальные элементы – вывести сообщение "В разработке".
10. Сделать возможность редактирования значений и их сохранения.