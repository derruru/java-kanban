package service;

import storage.Epic;
import storage.Subtask;
import storage.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    void createTask(Task task);

    void updateTask(Task task, int id);

    ArrayList<Task> getAllTasks();

    Task getTask(int id);

    void removeById(int id);

    void removeAll();

    void createEpic(Epic epic);

    void updateEpic(Epic epic, int id);

    void createSubtask(Subtask subtask, int epicId);

    void updateSubtask(Subtask subtask, int id);

    List<Integer> getHistory();
}
