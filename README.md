🛠 Технологический стек проекта ShoppingList

🏗 Архитектура
Clean Architecture (чёткое разделение на data/domain/presentation)

MVVM (ViewModel + LiveData)

Repository pattern (абстракция доступа к данным)

Use Cases (инкапсуляция бизнес-логики)

📚 Ключевые библиотеки
Категория	Технологии	Пример использования
UI Components	RecyclerView (DiffUtil)	Адаптер с разными типами ViewHolder
Navigation	Activity/Fragment Navigation	Intent-переходы + FragmentManager
DI	Ручная DI (без Dagger/Hilt)	Прозрачная зависимость UseCases
Асинхронность	LiveData + Callbacks	Реактивное обновление UI
Валидация	Кастомные TextWatchers	Валидация ввода в реальном времени
🔄 Паттерны
Observer (LiveData для UI-обновлений)

Factory (создание фрагментов через newInstance)

Singleton (репозиторий как object)

Delegate (by viewModels() для ViewModel)

Callback (интерфейсы для коммуникации Fragment-Activity)
