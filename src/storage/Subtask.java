package storage;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, String status) {
        super(name, description, status);
    }


    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", Status='" + getStatus() + '\'' +
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
