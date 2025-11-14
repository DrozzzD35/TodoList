package TaskServer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.Map;

public class TestClient {
    private static Gson gson = new Gson();

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();

        createTask(client.createTask("task1", "task01"));
        createTask(client.createTask("task2", "task02"));
        createTask(client.createTask("task3", "task03"));
        createTask(client.createTask("task4", "task04"));
        createTask(client.createTask("task5", ""));
        createTask(client.createTask("", "task06"));


        printAllTasks(client.getAllTasks());
        getTask(client.getTaskByIdResponse(9));
        getTask(client.getTaskByIdResponse(2));
        removeTask(client.removeTask(3));
        removeTask(client.removeTask(12));
        System.out.println("==========Список после удаления==========");
        printAllTasks(client.getAllTasks());

        Task task = new Task("001", "002");
        task.setName("updateName");
        task.setDescription("updateDescription");

        updateTask(client.updateTask(4, task));
        printAllTasks(client.getAllTasks());

    }

    public static void printAllTasks(HttpResponse<String> response) {
        try {
            if (response.statusCode() == 200) {
                Type type = new TypeToken<Map<Integer, Task>>() {
                }.getType();
                Map<Integer, Task> tasks = gson.fromJson(response.body(), type);

                if (!tasks.isEmpty()) {
                    System.out.println("==========Список всех задач==========");
                    for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                        printTask(response, entry.getValue());
                    }
                } else {
                    System.out.println("Список задач пуст");
                    System.out.println("Код ответа: " + response.statusCode());
                }
            }
            System.out.println();

        } catch (Exception e) {
            System.out.println("Ошибка " + e);
            System.out.println();
        }

    }

    private static void getTask(HttpResponse<String> response) {
        try {
            if (response.statusCode() == 200) {
                Task task = gson.fromJson(response.body(), Task.class);
                System.out.println("==========Задача найдена==========");
                printTask(response, task);
            } else {
                System.out.println("==========Задача не найдена========== ");
                System.out.println("Тело " + response.body());
                System.out.println("Статус код " + response.statusCode());
            }
            System.out.println();

        } catch (Exception e) {
            System.out.println(response.body() + " " + e);
            System.out.println();
        }

    }

    private static void printTask(HttpResponse<String> response, Task task) {
        System.out.println("Имя: " + task.getName());
        System.out.println("Описание: " + task.getDescription());
        System.out.println("Идентификатор: " + task.getId());
        System.out.println("Код ответа: " + response.statusCode());
        System.out.println("Тело: " + response.body());
        System.out.println();
    }

    private static void createTask(HttpResponse<String> response) {
        try {
            if (response.statusCode() == 201) {
                Task task = gson.fromJson(response.body(), Task.class);
                System.out.println("Задача создана");
                printTask(response, task);
            } else {
                System.out.println("При создании задачи произошла ошибка");
                System.out.println("Код ответа " + response.statusCode());
                System.out.println("Тело ответа " + response.body());
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println(response.body());
            System.out.println("Ошибка " + e);
        }


    }

    private static void removeTask(HttpResponse<String> response) {
        try {
            int responseId = gson.fromJson(response.body(), Integer.class);
            System.out.println("==========Удаление задачи==========");
            System.out.println("Задача с идентификатором: " + responseId + " удалена");
            System.out.println();
        } catch (Exception e) {
            System.out.println(response.body());
            System.out.println(e);
        }

    }

    private static void updateTask(HttpResponse<String> response) {
        try {
            Task task = gson.fromJson(response.body(), Task.class);
            System.out.println("==========Обновление задачи==========");
            printTask(response, task);
        } catch (Exception e) {
            System.out.println("Ошибка в методе обновления");
        }

    }
}
