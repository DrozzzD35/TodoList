package TaskServer;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
//TODO Создать интерфейс. Реализовать Manager через интерфейс - Готово

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks;

    public InMemoryTaskManager(Map<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public Map<Integer, Task> getAllTasks() {
//        return new HashMap<>(tasks);
        return Collections.unmodifiableMap(tasks);

    }

    @Override
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
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

    @Override
    public int createId() {
        return Identity.IDENTITY.createId();
    }

    @Override
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

    @Override
    public void removeTask(int taskId) {
        tasks.remove(taskId);
    }

}
