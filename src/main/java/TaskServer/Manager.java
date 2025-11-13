package TaskServer;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
//TODO Создать интерфейс. Реализовать Manager через интерфейс

public class Manager {
    private Map<Integer, Task> tasks;

    public Manager(Map<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Map<Integer, Task> getAllTasks() {
//        return new HashMap<>(tasks);
        return Collections.unmodifiableMap(tasks);

    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Task createTask(String name, String description) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя задачи не может быть пустым");
        }
        if (description == null) {
            description = "";
        }
        Task task = new Task(name, description);
        task.setId(createId());
        addTask(task);
        return task;
    }

    public int createId(){
        return Identity.IDENTITY.createId();
    }

    public void updateTask(Task newTask, int oldTaskId) {
        Task oldTask = getTaskById(oldTaskId);

        if (oldTask == null) {
            throw new IllegalArgumentException("Задача не найдена. Идентификатор " + oldTaskId);
        }
        if (newTask == null) {
            throw new IllegalArgumentException("Новая задача не может быть null");
        }

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
