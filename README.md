# Симулятор производственного процесса

## Описание
Этот проект реализует симуляцию производственного процесса, в котором детали проходят через последовательность производственных центров, обрабатываются рабочими и в конечном итоге завершают цикл обработки.

Производственные центры взаимодействуют между собой, передавая обработанные детали дальше по цепочке. Рабочие распределяются по центрам в зависимости от загруженности и наличия доступных деталей в буфере.

## Функциональность
- Чтение входных данных из Excel-файла
- Инициализация рабочих и деталей
- Создание сети производственных центров
- Динамическое распределение работников между центрами
- Логирование процесса производства в CSV-файл
- Подсчет времени завершения производства

## Установка и запуск
### Требования
- Java 17+
- Apache POI (для работы с Excel)

### Сборка и запуск
1. Склонируйте репозиторий:
   ```sh
   git clone https://github.com/kochemaskin/production-simulator.git
   cd production-simulator
   ```
2. Скомпилируйте проект:
   ```sh
   mvn clean package
   ```

3. Запуск тестов:
   ```sh
   mvn test package
   ```

4. Запустите симуляцию:
   ```sh
   java -jar target/production-simulator.jar
   ```

## Структура кода
- `Detail` — класс, описывающий деталь и её состояние.
- `Fabric` — класс, управляющий процессом производства.
- `ProductionCenter` — производственные центры, обрабатывающие детали.
- `Worker` — рабочие, выполняющие обработку деталей.
- `Timer` — вспомогательный класс для отслеживания времени симуляции.
- `Main` — точка входа, загружающая сценарий из Excel и запускающая симуляцию.

## Использование
Для запуска симуляции требуется Excel-файл с описанием сценария. Файл должен содержать:
- Лист `Scenario` с числом рабочих и количеством деталей.
- Лист `ProductionCenter` с параметрами производственных центров (производительность, максимальное число рабочих).
- Лист `Connection` с описанием связей между центрами.

После завершения симуляции результат сохраняется в `output.csv`.

## Пример выходного лога
```
Time; ProductionCenter; WorkersCount; BufferCount
0.0, Производственный центр №1, 2, 498
0.0, Производственный центр №2, 0, 0
0.0, Производственный центр №3, 0, 0
0.0, Производственный центр №4, 0, 0
0.0, Производственный центр №5, 0, 0
1.0, Производственный центр №1, 2, 498
1.0, Производственный центр №2, 0, 0
1.0, Производственный центр №3, 0, 0
1.0, Производственный центр №4, 0, 0
1.0, Производственный центр №5, 0, 0
2.0, Производственный центр №1, 2, 498
2.0, Производственный центр №2, 2, 0
2.0, Производственный центр №3, 0, 0
2.0, Производственный центр №4, 2, 0
2.0, Производственный центр №5, 0, 0
...
```

## Лицензия
Проект распространяется под лицензией MIT.

