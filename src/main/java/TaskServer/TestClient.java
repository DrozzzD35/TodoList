package TaskServer;

import java.io.IOException;

public class TestClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();

        client.createTask("task1", "task01");
        client.createTask("task2", "task02");
        client.createTask("task3", "task03");
        client.createTask("task4", "task04");

        System.out.println("==========Cписок всех задач==========");
        client.getAllTasks();
        System.out.println();
        System.out.println("==========Получение задачи==========");
        client.getTaskByIdResponse(2);

        client.removeTask(3);
        System.out.println();
        System.out.println("==========Список после удаления==========");
        client.getAllTasks();
        System.out.println();

        System.out.println("==========Обновление задачи==========");

        Task task = new Task("001", "002");
        task.setName("updateName");
        client.updateTask(4, task);
        System.out.println();

        System.out.println("==========Cписок всех задач==========");
        client.getAllTasks();


    }
}
