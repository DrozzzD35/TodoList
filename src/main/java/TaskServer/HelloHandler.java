package TaskServer;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class HelloHandler implements HttpHandler {
    Manager manager = new Manager(new HashMap<>());

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = exchange.getRequestMethod().toUpperCase();
        Gson gson = new Gson();

        switch (response) {
            case "GET" -> {
                String query = exchange.getRequestURI().getQuery();

                if (query != null && query.startsWith("id=")) {
                    int taskId = Integer.parseInt(query.substring(3));
                    System.out.println("Задача: ");
                    System.out.println(manager.getTaskById(taskId));
                    break;
                }

                System.out.println(manager.getAllTasks());
                break;
            }
            case "POST" -> {
                InputStream is = exchange.getRequestBody();
                String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                Task task = gson.fromJson(json, Task.class);

                manager.createTask(task.getName(), task.getDescription());

                System.out.println("Task создан");
                System.out.println("Имя: " + task.getName());
                System.out.println("Описание: " + task.getDescription());
                break;
            }
            case "PUT" -> {
            }
            case "DELETE" -> {
                String query = exchange.getRequestURI().getQuery();

                if (query != null && query.startsWith("id=")) {
                    int taskId = Integer.parseInt(query.substring(3));
                    manager.removeTask(taskId);
                    System.out.println("Задача с идентификатором "
                            + taskId + " успешно удалена");
                }


            }

        }

        exchange.getResponseHeaders().add("Content-type", "text/plain; Charset=UTF-8");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();


    }
}
