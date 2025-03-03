<Всякие команды>

{Добавление списка параметров через конструктор
Map<String, String> params = new HashMap<>();
params.put("name", "Dmitry");
...
.given()
.queryParams(params)
}

{Генерация параметров в формате json для post,put и тд
    Map<String, Object> body = new HashMap<>();
    body.put("param1", "value1");
    body.put("param2", "value2");
...
	.given()
	.body(body)	
}

{Определение и вывод статус кода ответа
    int statusCode = response.getStatusCode();
    System.out.println(statusCode);
}

{
	
	
	
}

allure serve allure-results - просмотр результатов тестов в аллюр