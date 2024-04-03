import service.HistoryManager;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;
import storage.Epic;
import storage.Status;
import storage.Subtask;
import storage.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager manager =  Managers.getDefault();

        Task task = new Task("name", "des", Status.NEW);
        Task updateTask = new Task ("name", "updateDes", Status.NEW);
        Task task2 = new Task("name2", "des2", Status.NEW);
        Epic epic = new Epic("epic", "des");
        Subtask subtask = new Subtask("sub", "des", Status.IN_PROGRESS);
        Epic updateEpic = new Epic("name", "newDes");
        Subtask updateSubtask = new Subtask("name", "newDes", Status.DONE);
        Subtask subtask1 = new Subtask("sub1", "des", Status.NEW);


        System.out.println(manager.createTask(task));
        System.out.println(manager.createEpic(epic));
        System.out.println(manager.updateTask(updateTask, 1));
        System.out.println(manager.createTask(task2));
        System.out.println(manager.createSubtask(subtask, 2));
        System.out.println(manager.getTask(1));
        System.out.println(manager.getTask(3));
        System.out.println(manager.getTask(2));
        System.out.println(manager.getTask(1));
        System.out.println(manager.getHistory());
        System.out.println(manager.updateEpic(updateEpic, 2));
        System.out.println(manager.updateSubtask(updateSubtask, 4));
        System.out.println(manager.getAllTasks());
        manager.removeById(1);
        System.out.println(manager.createSubtask(subtask1, 2));
        System.out.println(manager.getAllTasks());
        manager.removeAll();
        System.out.println(manager.getAllTasks());

    }
}
