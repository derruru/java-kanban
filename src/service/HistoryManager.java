package service;

import storage.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);
    void remove(int id);
    List<Integer> getHistory();
}
