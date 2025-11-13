package TaskServer;

public class Task {
    private String name;
    private String description;
    private Integer id;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;

    }

    /*TODO в менеджере написать присвоение id */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}
