package Service;

import java.util.Map;

public interface TaskManager {

    void addTask(Task task);

    Map<Integer, Task> getAllTasks();

    Task getTaskById(int id);

    Task createTask(String name, String description);

    int createId();

    void updateTask(Task newTask, int oldTaskId);

    void removeTask(int taskId);

}
