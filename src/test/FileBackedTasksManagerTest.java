package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.FileBackedTasksManager;
import storage.Status;
import storage.Task;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class FileBackedTasksManagerTest {

    @Test
    public void loadFromFileWithHistory() {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(Path.of("history.src"));
        Task task = new Task("name", "des", Status.DONE, 125,
                LocalDateTime.of(2020, 03, 11, 12, 30));
        Task task2 = new Task("name1", "des", Status.DONE, 125,
                LocalDateTime.of(2021, 03, 11, 12, 30));
        fileBackedTasksManager.createTask(task);
        fileBackedTasksManager.createTask(task2);
        fileBackedTasksManager.getTask(1);

        FileBackedTasksManager manager = FileBackedTasksManager.loadFromFile(Path.of("history.src"));
        Task createdTask = manager.getTask(1);


        Assertions.assertEquals(createdTask, new Task(1,"name", "des", Status.DONE, 125,
                LocalDateTime.of(2020, 03, 11, 12, 30)), "Задачи не совпадают");
    }

    @Test
    public void loadFromFileWithEmptyHistory() {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(Path.of("history.src"));
        Task task = new Task("name", "des", Status.DONE, 125,
                LocalDateTime.of(2020, 03, 11, 12, 30));
        Task task2 = new Task("name1", "des", Status.DONE, 125,
                LocalDateTime.of(2021, 03, 11, 12, 30));
        fileBackedTasksManager.createTask(task);
        fileBackedTasksManager.createTask(task2);

        FileBackedTasksManager manager = FileBackedTasksManager.loadFromFile(Path.of("history.src"));
        Task createdTask = manager.getTask(1);


        Assertions.assertEquals(createdTask, new Task(1,"name", "des", Status.DONE, 125,
                LocalDateTime.of(2020, 03, 11, 12, 30)), "Задачи не совпадают");
    }
}
