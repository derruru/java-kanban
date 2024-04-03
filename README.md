# java-kanban
## Техническое задание проекта №3
Создать трекер задач
### Типы задач
Простейшим кирпичиком такой системы является задача (англ. task). У задачи есть следующие свойства:
1) Название, кратко описывающее суть задачи (например, «Переезд»).
2) Описание, в котором раскрываются детали.
3) Уникальный идентификационный номер задачи, по которому её можно будет найти.
4) Статус, отображающий её прогресс. Мы будем выделять следующие этапы жизни задачи:
* NEW — задача только создана, но к её выполнению ещё не приступили.
* IN_PROGRESS — над задачей ведётся работа.
* DONE — задача выполнена.
Иногда для выполнения какой-нибудь масштабной задачи её лучше разбить на подзадачи (англ. subtask). 
Большую задачу, которая делится на подзадачи, мы будем называть эпиком (англ. epic).
Таким образом, в нашей системе задачи могут быть трёх типов: обычные задачи, эпики и подзадачи. Для них должны  
выполняться следующие условия:
1) Для каждой подзадачи известно, в рамках какого эпика она выполняется.
2) Каждый эпик знает, какие подзадачи в него входят.
3) Завершение всех подзадач эпика считается завершением эпика.  
### Идентификатор задачи
У каждого типа задач есть идентификатор. Это целое число, уникальное для всех типов задач. По нему мы находим,  
обновляем, удаляем задачи. При создании задачи менеджер присваивает ей новый идентификатор.
### Менеджер
Кроме классов для описания задач, вам нужно реализовать класс для объекта-менеджера. Он будет запускаться на  
старте программы и управлять всеми задачами. В нём должны быть реализованы следующие функции:
1) Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
2) Методы для каждого из типа задач(Задача/Эпик/Подзадача):
* Получение списка всех задач.
* Удаление всех задач.
* Получение по идентификатору.
* Создание. Сам объект должен передаваться в качестве параметра.
* Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
* Удаление по идентификатору.
3) Дополнительные методы:
* Получение списка всех подзадач определённого эпика.
Управление статусами осуществляется по следующему правилу:
1) Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о самой задаче.  
По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.

* Для эпиков:

1) если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
2) если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
3) во всех остальных случаях статус должен быть IN_PROGRESS.

## Техническое задание проекта №4
### Менеджер теперь интерфейс
Из темы об абстракции и полиморфизме вы узнали, что при проектировании кода полезно разделять требования к желаемой
функциональности объектов и то, как эта функциональность реализована. То есть набор методов, который должен быть у 
объекта, лучше вынести в интерфейс, а реализацию этих методов – в класс, который его реализует. Теперь нужно применить 
этот принцип к менеджеру задач.
1) Класс TaskManager должен стать интерфейсом. В нём нужно собрать список методов, которые должны быть у любого 
объекта-менеджера. Вспомогательные методы, если вы их создавали, переносить в интерфейс не нужно.
2) Созданный ранее класс менеджера нужно переименовать в InMemoryTaskManager. Именно то, что менеджер хранит всю 
информацию в оперативной памяти, и есть его главное свойство, позволяющее эффективно управлять задачами. Внутри 
класса должна остаться реализация методов. При этом важно не забыть имплементировать TaskManager, ведь в Java класс 
должен явно заявить, что он подходит под требования интерфейса.
### История просмотров задач
Добавьте в программу новую функциональность — нужно, чтобы трекер отображал последние просмотренные пользователем 
задачи. Для этого добавьте метод getHistory() в  TaskManager и реализуйте его — он должен возвращать последние 10 
просмотренных задач. Просмотром будем считаться вызов у менеджера методов получения задачи по идентификатору  
— getTask(), getSubtask() и getEpic(). От повторных просмотров избавляться не нужно.
### Утилитарный класс
Со временем в приложении трекера появится несколько реализаций интерфейса TaskManager.   
Чтобы не зависеть от реализации, создайте утилитарный класс Managers.  На нём будет лежать вся ответственность за 
создание менеджера задач. То есть Managers должен сам подбирать нужную реализацию TaskManagerи возвращать объект 
правильного типа.
У Managersбудет метод  getDefault().  При этом вызывающему неизвестен конкретный класс, только то, что объект, 
который возвращает getDefault(), реализует интерфейс TaskManager.
### Статусы задач как перечисление
Так как варианты возможных статусов у задачи ограничены, для их хранения в программе лучше завести перечисляемый тип enum.
### Сделайте историю задач интерфейсом
В этом спринте возможности трекера ограничены — в истории просмотров допускается дублирование и она может содержать 
только десять задач. В следующем спринте вам нужно будет убрать дубли и расширить её размер. Чтобы подготовиться к 
этому, проведите рефакторинг кода.
Создайте отдельный интерфейс для управления историей просмотров — HistoryManager. У него будет два метода. Первый 
add(Task task) должен помечать задачи как просмотренные, а второй getHistory() — возвращать их список.
Объявите класс InMemoryHistoryManager и перенесите в него часть кода для работы с историей из  класса 
InMemoryTaskManager.  Новый класс InMemoryHistoryManager должен реализовывать интерфейс HistoryManager.
Добавьте в служебный класс Managers статический метод HistoryManager getDefaultHistory().  Он должен возвращать объект 
InMemoryHistoryManager — историю просмотров.
Проверьте, что теперь InMemoryTaskManager обращается к менеджеру истории через интерфейс HistoryManager и использует 
реализацию, которую возвращает метод getDefaultHistory().