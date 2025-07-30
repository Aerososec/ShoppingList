# 🛠 Технологический стек проекта ShoppingList

## 🏗 Архитектура
- **Clean Architecture** (чёткое разделение на data/domain/presentation)
- **MVVM** (ViewModel + LiveData)
- **Repository pattern** (абстракция доступа к данным)
- **Use Cases** (инкапсуляция бизнес-логики)

## 📚 Ключевые библиотеки и компоненты

| Категория       | Технологии                     | Пример использования              |
|-----------------|-------------------------------|-----------------------------------|
| **UI**         | RecyclerView + DiffUtil       | Адаптер с разными типами ViewHolder |
| **Навигация**  | Activity/Fragment Navigation  | Intent-переходы + FragmentManager  |
| **DI**         | Ручное внедрение зависимостей | Прозрачные зависимости UseCases    |
| **Асинхронность** | LiveData + Callbacks        | Реактивное обновление UI           |
| **Валидация**  | Кастомные TextWatchers        | Валидация ввода в реальном времени |

## 🔄 Реализованные паттерны
- **Observer** (LiveData для UI-обновлений)
- **Factory** (`newInstance()` для фрагментов)
- **Singleton** (репозиторий как `object`)
- **Delegate** (`by viewModels()` для ViewModel)
- **Callback** (интерфейсы Fragment ↔ Activity)

## 🎯 Особенности реализации
```kotlin
// Пример Clean Architecture
data <-> domain <-> presentation
