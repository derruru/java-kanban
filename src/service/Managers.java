package service;

import java.nio.file.Paths;

public class Managers {

    public static TaskManager getDefault() {
        //return new InMemoryTaskManager();
        return new FileBackedTasksManager(Paths.get("history.src"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
