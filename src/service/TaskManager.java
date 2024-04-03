package service;

import storage.Epic;
import storage.Subtask;
import storage.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    Task createTask(Task task);

    Task updateTask(Task task, int id);

    ArrayList<Task> getAllTasks();

    Task getTask(int id);

    void removeById(int id);

    void removeAll();

    Epic createEpic(Epic epic);

    Epic updateEpic(Epic epic, int id);

    Subtask createSubtask(Subtask subtask, int epicId);

    Subtask updateSubtask(Subtask subtask, int id);

    List<Task> getHistory();
}
