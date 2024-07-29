package storage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, Status status, int duration, LocalDateTime startTime, int epicId) {
        super(name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, Status status, int duration, LocalDateTime startTime, int epicId) {
        super(id, name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, Status status, int duration, LocalDateTime startTime,
                   LocalDateTime endTime, int epicId) {
        super(id, name, description, status, duration, startTime, endTime);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", Status='" + getStatus() + '\'' +
                ", duration=" + getDuration() + '\'' +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", epicId=" + epicId +
                '}';
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
