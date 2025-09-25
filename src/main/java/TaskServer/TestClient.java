package TaskServer;

import java.io.IOException;

public class TestClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();

        client.createTask("task1", "task01");
        client.createTask("task2", "task02");
        client.createTask("task3", "task03");
        client.createTask("task4", "task04");

        client.getAllTasks();
        client.getTaskByIdResponse(2);

        client.removeTask(1);
        System.out.println();
        System.out.println("==========Список после удаления==========");
        client.getAllTasks();

        System.out.println("==========Обновление задачи==========");

        Task task = new Task("001", "002");
        task.setName("updateName");
        client.updateTask(3, task);

        System.out.println();
        client.getAllTasks();


    }
}
