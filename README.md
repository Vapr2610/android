# 

### **Цирукин Данила** **_ИКС-432_** 

---

# 📌 Цель проекта
- Навигационный хаб (MainActivity)
- Калькулятор с арифметическими операциями    
- Медиаплеер для воспроизведения аудиофайлов  
- Модуль определения местоположения устройства    


---
# ✨ Возможности 
## 🧮 Калькулятор
- Базовые арифметические операции: +, -, *, /
- Обработка деления на ноль
- Кнопка очистки (C)
- Адаптивный интерфейс
## 🎵 Медиаплеер
- Воспроизведение MP3, WAV, OGG, M4A
- Play/Pause/Stop управление
- SeekBar для перемотки трека
- Автоматическая пауза при сворачивании
- Динамический запрос разрешений
## 📍 Местоположение
- Определение широты, долготы, высоты
- Отображение текущего времени
- Сохранение данных в JSON-файл
- Кнопка обновления координат

---
# 📦 Зависимости
```
// AndroidX Core
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("com.google.android.material:material:1.11.0")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")

// Location Services
implementation("com.google.android.gms:play-services-location:21.1.0")

// JSON Parsing
implementation("com.google.code.gson:gson:2.10.1")

// Lifecycle Components
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
implementation("androidx.activity:activity-ktx:1.8.2")
```
---
**core-ktx 1.12.0** Kotlin-расширения для Android API   
**appcompat 1.6.1** Обратная совместимость UI   
**material 1.11.0** Material Design компоненты  
**play-services-location 21.1.0** GPS и геолокация  
**gson 2.10.1** Сериализация JSON   
**lifecycle 2.7.0** жизненным циклом    

---
# 📱 Использование
Главный экран (Navigation Hub)  
Запустите приложение    
Выберите нужный модуль:     
- 🧮 Калькулятор   
- 🎵 Медиаплеер        
- 📍 Местоположение   

# 🧮 Калькулятор
1. Введите первое число (0-9)
2. Выберите операцию (+, -, *, /)
3. Введите второе число
4. Нажмите "=" для результата
5. Нажмите "C" для очистк

# 🎵 Медиаплеер
1. Предоставьте разрешение на доступ к файлам
2. Выберите трек из списка
3. Используйте кнопки Play/Pause/Stop
4. Перемотайте через SeekBar  
- Поддерживаемые форматы: MP3, WAV, OGG, M4A

# 📍 Местоположение   
1. Предоставьте разрешение на доступ к GPS
2. Дождитесь определения координат
3. Данные автоматически сохраняются в locations.json
4. Нажмите "🔄 Обновить" для новых данных

# 🔐 Разрешения
- READ_MEDIA_AUDIO Доступ к аудиофайлам   
- READ_EXTERNAL_STORAGE Доступ к файлам   
- ACCESS_FINE_LOCATION Точное местоположение  
- ACCESS_COARSE_LOCATION Приблизительное местоположение   
- FOREGROUND_SERVICE Фоновый сервис
