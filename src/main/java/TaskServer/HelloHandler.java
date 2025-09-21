package TaskServer;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;


public class HelloHandler implements HttpHandler {
    Manager manager = new Manager(new HashMap<>());

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod().toUpperCase();
        Gson gson = new Gson();
        String response = "";

        switch (method) {
            case "GET" -> {
                String query = exchange.getRequestURI().getQuery();

                if (query != null && query.startsWith("id=")) {
                    int taskId = Integer.parseInt(query.substring(3));
                    Task task = manager.getTaskById(taskId);
                    response = gson.toJson(task);

                    break;
                }
                response = gson.toJson(manager.getAllTasks());

                break;
            }
            case "POST" -> {
                InputStream is = exchange.getRequestBody();
                String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                Task jsonTask = gson.fromJson(jsonString, Task.class);

                manager.createTask(jsonTask.getName(), jsonTask.getDescription());
                Task task = manager.getTaskById(jsonTask.getId());
                response = gson.toJson(task);
                break;
            }
            case "PUT" -> {
                InputStream is = exchange.getRequestBody();
                String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                Task jsonTask = gson.fromJson(jsonString, Task.class);

                String query = exchange.getRequestURI().getQuery();
                if (query != null && query.startsWith("id=")) {
                    int id = Integer.parseInt(query.substring(3));
                    manager.updateTask(jsonTask, id);
                }

                response = gson.toJson(jsonTask);

                break;
            }
            case "DELETE" -> {
                String query = exchange.getRequestURI().getQuery();

                if (query != null && query.startsWith("id=")) {
                    int taskId = Integer.parseInt(query.substring(3));
                    manager.removeTask(taskId);
                    response = gson.toJson(taskId);
                }


            }

        }

        exchange.getResponseHeaders().add("Content-type", "application/json; Charset=UTF-8");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();


    }

}
