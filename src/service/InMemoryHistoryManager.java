package service;

import storage.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node> receivedTasks;
    private Node head;
    private Node tail;

    public InMemoryHistoryManager() {
        this.receivedTasks = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            remove(task.getId());
            linkLast(task);
        }
    }

    @Override
    public void remove(int id) {
        removeNode(receivedTasks.get(id));
    }

    @Override
    public List<Integer> getHistory() {
        return getTasks();
    }


    private void linkLast(Task task) {
        final Node oldTail = tail;
        final Node newNode = new Node(oldTail, task, null);
        receivedTasks.put(task.getId(), newNode);
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        tail = newNode;
    }

    private List<Integer> getTasks() {
        List<Integer> tasks = new ArrayList<>();
        Node currentNode = head;
        while (!(currentNode == null)) {
            tasks.add(currentNode.data.getId());
            currentNode = currentNode.next;
        }
        return tasks;
    }

    private void removeNode(Node node) {
        if (!(node == null)) {
            final Node next = node.next;
            final Node prev = node.prev;
            node.data = null;
            if (head == node && tail == node) {
                head = null;
                tail = null;
            } else if (head == node) {
                head = next;
                head.prev = null;
            } else if (tail == node) {
                tail = prev;
                tail.next = null;
            } else {
                prev.next = next;
                next.prev = prev;
            }
        }
    }

    class Node {
        public Task data;
        public Node next;
        public Node prev;

        public Node(Node prev, Task data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}


