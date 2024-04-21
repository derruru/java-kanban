package service;

import exception.ManagerSaveException;
import storage.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final Path path;

    public FileBackedTasksManager(Path file) {
        try {
            if (!Files.exists(file)) {
                path = Files.createFile(file);
            } else {
                path = file;
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask, int epicId) {
        super.createSubtask(subtask, epicId);
        save();
    }

    @Override
    public Task getTask(int id) {
        super.getTask(id);
        save();
        return allTasks.get(id);
    }

    public static FileBackedTasksManager loadFromFile(Path file) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(file)))) {
            List<String> lines = new ArrayList<>();
            while (reader.ready()) {
                lines.add(reader.readLine());
            }
            for (int i = 1; i < lines.size() - 3; i++) {
                Task task = fromString(lines.get(i));
                manager.allTasks.put(task.getId(), task);
                if (task.getClass().equals(Task.class)) {
                    manager.tasks.put(task.getId(), task);
                } else if (task.getClass().equals(Epic.class)) {
                    manager.epics.put(task.getId(), (Epic) task);
                } else {
                    manager.subtasks.put(task.getId(), (Subtask) task);
                }
            }
            List<Integer> history = historyFromString(lines.get(lines.size()-1));
            for(int id : history) {
                manager.getTask(id);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения в файл");
        }
        return manager;
    }

    private static String historyToString(HistoryManager manager) {
        List<Integer> taskList = manager.getHistory();
        String[] taskId = new String[taskList.size()];
        for (int i = 0; i < taskId.length; i++) {
            taskId[i] = taskList.get(i).toString();
        }
        return String.join(",", taskId);
    }

    private static List<Integer> historyFromString(String value) {
        String[] parts = value.split(",");
        List<Integer> taskId = new ArrayList<>();
        for (int i = 0; i < parts.length; i++) {
            taskId.add(Integer.valueOf(parts[i]));
        }
        return taskId;
    }

    private void save() {
        try (Writer writer = new FileWriter(String.valueOf(path))) {
            writer.write("id,type,name,status,description,epic\n");
            for (Task task : allTasks.values()) {
                writer.write(toString(task));
            }
            writer.write("\n");
            writer.write(historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения в файл");
        }
    }

    private String toString(Task task) {
        if (!task.getClass().equals(Subtask.class)) {
            return task.getId() + "," + getType(task) + "," + task.getName() + "," + task.getStatus() + "," +
                    task.getDescription() + "\n";
        } else {
            return task.getId() + "," + getType(task) + "," + task.getName() + "," + task.getStatus() + "," +
                    task.getDescription() + "," + ((Subtask) task).getEpicId() + "\n";
        }
    }

    private static Task fromString(String s) {
        String[] parts = s.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        if (parts[1].equals(TypeTasks.TASK.toString())) {
            return new Task(id, name, description, status);
        } else if (parts[1].equals(TypeTasks.EPIC.toString())) {
            return new Epic(id, name, description, status);
        } else {
            int epicId = Integer.parseInt(parts[5]);
            return new Subtask(id, name, description, status, epicId);
        }
    }

    private TypeTasks getType(Task task) {
        if (task.getClass().equals(Task.class)) {
            return TypeTasks.TASK;
        } else if (task.getClass().equals(Subtask.class)) {
            return TypeTasks.SUBTASK;
        } else if (task.getClass().equals(Epic.class)) {
            return TypeTasks.EPIC;
        }
        return null;
    }

    public static void main(String[] args) {
        Task task = new Task("name", "des", Status.NEW);
        Task task2 = new Task("name2", "des2", Status.NEW);
        Epic epic = new Epic("epic", "des");
        Epic epic2 = new Epic("epic2", "des");
        Subtask subtask = new Subtask("sub", "des", Status.IN_PROGRESS);
        Subtask subtask1 = new Subtask("sub1", "des", Status.NEW);
        Subtask subtask2 = new Subtask("sub2", "des", Status.DONE);

        TaskManager taskManager = Managers.getDefault();
        taskManager.createTask(task);
        taskManager.createTask(task2);
        taskManager.createEpic(epic);
        taskManager.createEpic(epic2);
        taskManager.createSubtask(subtask, 3);
        taskManager.createSubtask(subtask1, 3);
        taskManager.createSubtask(subtask2, 3);

        taskManager.getTask(2);
        taskManager.getTask(1);

        TaskManager taskManager1 = loadFromFile(Paths.get("history.src"));
        System.out.println(taskManager1.getHistory());
        System.out.println(taskManager1.getAllTasks());
    }
}
