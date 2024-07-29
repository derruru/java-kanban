package service;

import storage.Epic;
import storage.Status;
import storage.Subtask;
import storage.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Task> allTasks = new HashMap<>();
    private int id = 1;
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void createTask(Task task) {
        Task newTask = new Task(task.getName(), task.getDescription(), task.getStatus(), task.getDuration(), task.getStartTime());
        newTask.setId(id);
        tasks.put(id, newTask);
        allTasks.put(id, newTask);
        updateId();
    }

    @Override
    public void updateTask(Task task, int id) {
        if (tasks.containsKey(id)) {
            task.setId(id);
            tasks.put(id, task);
            allTasks.put(id, task);
        } else {
            System.out.println("Задачи с ID " + id + " не существует!");
        }
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> listTasks = new ArrayList<>();
        for (Task task : allTasks.values()) {
            listTasks.add(task);
        }
        return listTasks;
    }

    @Override
    public Task getTask(int id) {
        if (allTasks.containsKey(id)) {
            historyManager.add(allTasks.get(id));
            return allTasks.get(id);
        } else {
            System.out.println("Задачи с ID " + id + " не существует!");
        }
        return null;
    }

    @Override
    public void removeById(int id) {
        if (allTasks.containsKey(id)) {
            allTasks.remove(id);
            historyManager.remove(id);
            if (tasks.containsKey(id)) {
                tasks.remove(id);
            } else if (epics.containsKey(id)) {
                epics.remove(id);
            } else if (subtasks.containsKey(id)) {
                subtasks.remove(id);
            }
        } else {
            System.out.println("Задачи с ID " + id + " не существует!");
        }
    }

    @Override
    public void removeAll() {
        for (Task task : allTasks.values()) {
            historyManager.remove(task.getId());
        }
        allTasks.clear();
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void createEpic(Epic epic) {
        Epic newEpic = new Epic(epic.getName(), epic.getDescription());
        newEpic.setId(id);
        newEpic.setStatus(findStatus(newEpic));
        epics.put(id, newEpic);
        allTasks.put(id, newEpic);
        updateId();
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        if (epics.containsKey(id)) {
            epic.setId(id);
            Epic oldEpic = epics.get(id);
            epic.setSubtasks(oldEpic.getSubtasks());
            epic.setStatus(findStatus(epic));
            epics.put(id, epic);
            allTasks.put(id, epic);
        } else {
            System.out.println("Задачи с ID " + id + " не существует!");
        }
    }

    @Override
    public void createSubtask(Subtask subtask, int epicId) {
        if (epics.containsKey(epicId)) {
            Subtask newSubtask = new Subtask(subtask.getName(), subtask.getDescription(), subtask.getStatus(),
                    subtask.getDuration(), subtask.getStartTime(), epicId);
            newSubtask.setId(id);
            newSubtask.setEpicId(epicId);
            subtasks.put(id, newSubtask);
            allTasks.put(id, newSubtask);
            Epic epic = epics.get(epicId);
            epic.getSubtasks().add(newSubtask);
            updateEpic(epic, epicId);
            id++;
        } else {
            System.out.println("Эпика с ID " + epicId + " не существует!");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask, int id) {
        if (subtasks.containsKey(id)) {
            Subtask oldSubtask = subtasks.get(id);
            subtask.setId(id);
            subtask.setEpicId(oldSubtask.getEpicId());
            subtasks.put(id, subtask);
            Epic epic = epics.get(oldSubtask.getEpicId());
            //for (Subtask sub : epic.getSubtasks()) {
            for (int i = 1; i < epic.getSubtasks().size(); i++) {
                if (epic.getSubtasks().get(i).getId() == subtask.getId()) {
                    epic.getSubtasks().remove(epic.getSubtasks().get(i));
                    epic.getSubtasks().add(subtask);
                } else {
                    epic.getSubtasks().add(subtask);
                }
            }
            updateEpic(epic, epic.getId());
        } else {
            System.out.println("Задачи с ID " + id + " не существует!");
        }
    }

    @Override
    public List<Integer> getHistory() {
        return historyManager.getHistory();
    }

    private void updateId() {
        id++;
    }

    private Status findStatus(Epic epic) {
        ArrayList<Status> newStatus = new ArrayList<>();
        ArrayList<Status> doneStatus = new ArrayList<>();
        ArrayList<Status> inProgressStatus = new ArrayList<>();
        ArrayList<Subtask> epicSubtasks = epic.getSubtasks();
        if (epicSubtasks.isEmpty()) {
            return Status.NEW;
        }
        for (Subtask subtask : epicSubtasks) {
            if (subtask.getStatus() == Status.NEW) {
                newStatus.add(subtask.getStatus());
            } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                inProgressStatus.add(subtask.getStatus());
            } else if (subtask.getStatus() == Status.DONE) {
                doneStatus.add(subtask.getStatus());
            }
        }
        if (!newStatus.isEmpty() && doneStatus.isEmpty() && inProgressStatus.isEmpty()) {
            return Status.NEW;
        } else if (!doneStatus.isEmpty() && newStatus.isEmpty() && inProgressStatus.isEmpty()) {
            return Status.DONE;
        }
        return Status.IN_PROGRESS;
    }
}
