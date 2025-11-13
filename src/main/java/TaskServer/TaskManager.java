package TaskServer;

import java.util.Map;
import java.util.Objects;

public interface TaskManager {

    void addTask(Task task);

    Map<Integer, Task> getAllTasks();

    Task getTaskById(int id);

    Task createTask(String name, String description);

    int createId();

    void updateTask(Task newTask, int oldTaskId);

    void removeTask(int taskId);

}
