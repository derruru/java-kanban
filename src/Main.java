import service.Manager;
import storage.Epic;
import storage.Subtask;
import storage.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task = new Task("name", "des", "NEW");
        Task updateTask = new Task ("name", "updateDes", "NEW");
        Task task2 = new Task("name2", "des2", "NEW");
        Epic epic = new Epic("epic", "des");
        Subtask subtask = new Subtask("sub", "des", "IN_PROGRESS");
        Epic updateEpic = new Epic("name", "newDes");
        Subtask updateSubtask = new Subtask("name", "newDes", "DONE");
        Subtask subtask1 = new Subtask("sub1", "des", "NEW");


        System.out.println(manager.createTask(task));
        System.out.println(manager.createEpic(epic));
        System.out.println(manager.updateTask(updateTask, 1));
        System.out.println(manager.createTask(task2));
        System.out.println(manager.createSubtask(subtask, 2));
        System.out.println(manager.getTask(1));
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
