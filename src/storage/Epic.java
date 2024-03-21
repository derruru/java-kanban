package storage;

import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", Status='" + getStatus() + '\'' +
                ", subtasks=" + subtasks +
                '}';
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }
}
