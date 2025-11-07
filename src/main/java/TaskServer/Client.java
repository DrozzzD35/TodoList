package TaskServer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class Client {
    private HttpClient client;
    private Gson gson = new Gson();
    private String address = "http://localhost:8080/task";
    private URI uri = URI.create(address);

    public Client() {
        client = HttpClient.newHttpClient();
    }

    public void getAllTasks() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type type = new TypeToken<Map<Integer, Task>>() {
        }.getType();

        try {
            Map<Integer, Task> tasks = gson.fromJson(response.body(), type);

            if (!tasks.isEmpty()) {
                System.out.println("==========Список всех задач==========");
                for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                    printResponse(entry.getValue(), response);
                }
            }

        } catch (Exception e) {
            System.out.println(response.body());
        }


    }

    public void getTaskByIdResponse(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address + "/" + id)).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        try {
            Task task = gson.fromJson(response.body(), Task.class);
            if (task.getName() != null) {
                System.out.println("Задача найдена: ");
                printResponse(task, response);
            }

        } catch (Exception e) {
            System.out.println(response.body());
        }

    }

    public void createTask(String name, String description) throws IOException, InterruptedException {
        Task requestTask = new Task(name, description);
        String json = gson.toJson(requestTask);

        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).header("Content-Type", "application/json; Charset=UTF-8").build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        try {
            Task responseTask = gson.fromJson(response.body(), Task.class);

            if (responseTask != null) {
                System.out.println("Задача создана: ");
                printResponse(responseTask, response);
            }

        } catch (Exception e) {
            System.out.println(response.body());
        }

    }

    public void updateTask(int id, Task updateTask) throws IOException, InterruptedException {
        String json = gson.toJson(updateTask);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address + "/" + id)).PUT(HttpRequest.BodyPublishers.ofString(json)).header("Content-Type", "application/json; Charset=UTF-8").build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Task responseTask = gson.fromJson(response.body(), Task.class);

            printResponse(responseTask, response);

        } catch (Exception e) {
            System.out.println("Ошибка в методе обновления");
        }


    }


    public void removeTask(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address + "/" + id)).DELETE().header("Content-Type", "application/json; Charset=UTF-8").build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        try {
            int responseId = gson.fromJson(response.body(), Integer.class);

            System.out.println("==========Удаление задачи==========");
            System.out.println("Задача с идентификатором: " + responseId + " удалена");

        } catch (Exception e) {
            System.out.println(response.body());
        }

    }


    private void printResponse(Task responseTask, HttpResponse<String> response) {
        System.out.println("Имя: " + responseTask.getName());
        System.out.println("Описание: " + responseTask.getDescription());
        System.out.println("Идентификатор: " + responseTask.getId());
//        System.out.println("Код ответа: " + response.statusCode());
//        System.out.println("Тело: " + response.body());
        System.out.println();
    }


}