package service;

import storage.Epic;
import storage.Subtask;
import storage.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Task> allTasks = new HashMap<>();
    int id = 1;

    public Task createTask(Task task) {
        Task newTask = new Task(task.getName(), task.getDescription(), task.getStatus());
        newTask.setId(id);
        tasks.put(id, newTask);
        allTasks.put(id, newTask);
        updateId();
        return newTask;
    }

    public Task updateTask(Task task, int id) {
        if (tasks.containsKey(id)) {
            task.setId(id);
            tasks.put(id, task);
            allTasks.put(id, task);
        } else {
            System.out.println("Задачи с ID " + id + " не существует!");
        }
        return task;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> listTasks = new ArrayList<>();
        for (Task task : allTasks.values()) {
            listTasks.add(task);
        }
        return listTasks;
    }

    public Task getTask(int id) {
        if (allTasks.containsKey(id)) {
            return allTasks.get(id);
        } else {
            System.out.println("Задачи с ID " + id + " не существует!");
        }
        return null;
    }

    public void removeById(int id) {
        if (allTasks.containsKey(id)) {
            allTasks.remove(id);
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

    public void removeAll() {
        allTasks.clear();
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    public Epic createEpic(Epic epic) {
        Epic newEpic = new Epic(epic.getName(), epic.getDescription());
        newEpic.setId(id);
        newEpic.setStatus(findStatus(newEpic));
        epics.put(id, newEpic);
        allTasks.put(id, newEpic);
        updateId();
        return newEpic;
    }

    public Epic updateEpic(Epic epic, int id) {
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
        return epic;
    }

    public Subtask createSubtask(Subtask subtask, int epicId) {
        if (epics.containsKey(epicId)) {
            Subtask newSubtask = new Subtask(subtask.getName(), subtask.getDescription(), subtask.getStatus());
            newSubtask.setId(id);
            newSubtask.setEpicId(epicId);
            subtasks.put(id, newSubtask);
            Epic epic = epics.get(epicId);
            epic.getSubtasks().add(newSubtask);
            updateEpic(epic, epicId);
            id++;
            return newSubtask;
        } else {
            System.out.println("Эпика с ID " + epicId + " не существует!");
        }
        return null;
    }

    public Subtask updateSubtask(Subtask subtask, int id) {
        if (subtasks.containsKey(id)) {
            Subtask oldSubtask = subtasks.get(id);
            subtask.setId(id);
            subtask.setEpicId(oldSubtask.getEpicId());
            subtasks.put(id, subtask);
            Epic epic = epics.get(oldSubtask.getEpicId());
            for (Subtask sub : epic.getSubtasks()) {
                if (sub.getId() == subtask.getId()) {
                    epic.getSubtasks().remove(sub);
                    epic.getSubtasks().add(subtask);
                } else {
                    epic.getSubtasks().add(subtask);
                }
            }
            updateEpic(epic, epic.getId());
            return subtask;
        } else {
            System.out.println("Задачи с ID " + id + " не существует!");
        }
        return null;
    }

    private void updateId() {
        id++;
    }

    private String findStatus(Epic epic) {
        ArrayList<String> newStatus = new ArrayList<>();
        ArrayList<String> doneStatus = new ArrayList<>();
        ArrayList<String> inProgressStatus = new ArrayList<>();
        ArrayList<Subtask> epicSubtasks = epic.getSubtasks();
        if (epicSubtasks.isEmpty()) {
            return "NEW";
        }
        for (Subtask subtask : epicSubtasks) {
            if (subtask.getStatus().equals("NEW")) {
                newStatus.add(subtask.getStatus());
            } else if (subtask.getStatus().equals("IN_PROGRESS")) {
                inProgressStatus.add(subtask.getStatus());
            } else if(subtask.getStatus().contains("DONE")) {
                doneStatus.add(subtask.getStatus());
            }
        }
        if (!newStatus.isEmpty() && doneStatus.isEmpty() && inProgressStatus.isEmpty()) {
            return "NEW";
        } else if (!doneStatus.isEmpty() && newStatus.isEmpty() && inProgressStatus.isEmpty()) {
            return "DONE";
        }
        return "IN_PROGRESS";
    }
}
