##Webshop

Представлен функционал небольшого онлайн магазан.

Реализовано Basic Auth Security с двумя ролями (ADMIN, USER)

#####Для ADMIN предоставлен доступ к следующим entry-point:

(POST) http://localhost:8081/product/create - создание нового товара


    {"name": "",    - название товара
    "price": "",    - цена товара
    "discount": ,   - имя скидки (из существующих)
    "quantity": ,   - количество добавляемого товара
    "category": ""} - название категории товара (из существующих)

(GET) http://localhost:8081/category - просмотр категорий товаров

(POST) http://localhost:8081/category - создание новой категории товара

    {"name": ""}   - навзание категории товара

(GET) http://localhost:8081/discount - просмотр существующих типов скидок

(POST) http://localhost:8081/discount - создание новой скидки

    {"name": }    - значение скидки

#####Для USER предоставлен доступ к следующим entry-point:

(POST) http://localhost:8081/user/pay - оплата выбранных товаров

    {"userId": ,            - id пользователя
     "products": [          
        {"id":"",           - id товара
     	"quantity":""},     - количество товара, который покупается
     	{"id":"",           
     	"quantity":""}]
     }

(POST) http://localhost:8081/user/add_money - пополнение счета пользователем

    {
      "id": "",     - id пользователя
      "money": ""   - пополняемая сумма
     }

##### Для всех существуют следующие entry-point: 

(POST) http://localhost:8081/user/create_user - регистрация нового пользователя

    {"name":"",             - имя нового пользователя
     "password":"",         - пароль
     "repeatPassword":""}   - повторный ввод пароля

(POST) http://localhost:8081/login - аутентификация пользователя

    {"name":"",
     "password":""}

(GET) http://localhost:8081/product - просмотр всех товаров
(GET) http://localhost:8081/product?category={навзание категории} - просмотр всех товаров по категории


####Приложение инициализируется со следующими параметрами по умолчанию:

Пользователь (ADMIN):

__username:__ ADMIN

__password:__ 123

__Скидки:__ 0, 5, 10, 20

__Категории товаров:__ Books, Headphones, Pencils

__Следующие товары:__ http://localhost:8081/product
