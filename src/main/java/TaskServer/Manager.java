package TaskServer;

import java.util.HashMap;
import java.util.Map;

public class Manager {
    private Map<Integer, Task> tasks = new HashMap();

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
    }

    public void updateTask(Task newTask, int taskId) {
        Task oldTask = getTaskById(taskId);

        if (newTask.getDescription() != null) {
            oldTask.setDescription(newTask.getDescription());
        }
        if (newTask.getName() != null) {
            oldTask.setName(newTask.getName());
        }
    }

    public void removeTask(int taskId) {
        tasks.remove(taskId);
    }


}
