package storage;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public Epic(int id, String name, String description, Status status, int duration, LocalDateTime startTime,
                LocalDateTime endTime) {
        super(id, name, description, status, duration, startTime, endTime);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", Status='" + getStatus() + '\'' +
                ", duration=" + getDuration() + '\'' +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", subtasks=" + subtasks +
                '}';
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public LocalDateTime getStartTime() {
        LocalDateTime startTime = null;
        for (Subtask subtask : getSubtasks()) {
            if (subtask.getStartTime().isBefore(startTime) || startTime == null) {
                startTime = subtask.getStartTime();
            }
        }
        return startTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        LocalDateTime endTime = null;
        for (Subtask subtask : getSubtasks()) {
            if (subtask.getEndTime().isAfter(endTime) || endTime == null) {
                endTime = subtask.getEndTime();
            }
        }
        return endTime;
    }

    @Override
    public int getDuration() {
        int duration = 0;
        for (Subtask subtask : getSubtasks()) {
            duration += subtask.getDuration();
        }
        return duration;
    }

}
