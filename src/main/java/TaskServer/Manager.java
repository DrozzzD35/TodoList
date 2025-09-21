package TaskServer;

import java.util.Map;
import java.util.Objects;

public class Manager {
    private Map<Integer, Task> tasks;

    public Manager(Map<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Map<Integer, Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void createTask(String name, String description) {
        Task task = new Task(name, description);
        addTask(task);
    }

    public void updateTask(Task newTask, int taskId) {
        Task oldTask = getTaskById(taskId);

        if (!Objects.equals(newTask.getDescription(), oldTask.getDescription())) {
            oldTask.setDescription(newTask.getDescription());
        }
        if (!Objects.equals(newTask.getName(), oldTask.getName())) {
            oldTask.setName(newTask.getName());
        }
    }

    public void removeTask(int taskId) {
        tasks.remove(taskId);
    }

}
