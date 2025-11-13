package TaskServer;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

public class TestClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();

        client.createTask("task1", "task01");
        client.createTask("task2", "task02");
        client.createTask("task3", "task03");
        client.createTask("task4", "task04");

        client.getAllTasks();
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
        task.setDescription("updateDescription");
        client.updateTask(4, task);
        System.out.println();

        client.getAllTasks();


        printMap(client.getAllTasks());

    }


    public static void printMap(Map<Integer, Task> map) {
        for (Map.Entry<Integer, Task> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private void printTask(Task responseTask, HttpResponse<String> response) {
        System.out.println("Имя: " + responseTask.getName());
        System.out.println("Описание: " + responseTask.getDescription());
        System.out.println("Идентификатор: " + responseTask.getId());
        System.out.println("Код ответа: " + response.statusCode());
        System.out.println("Тело: " + response.body());
        System.out.println();
    }


}
