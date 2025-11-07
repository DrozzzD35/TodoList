package TaskServer;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class SingleTaskHandler implements HttpHandler {
    Manager manager = new Manager(new HashMap<>());

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod().toUpperCase();
        Gson gson = new Gson();
        String response = "";
        int statusCode = 500;

        switch (method) {
            case "GET" -> {
                String path = exchange.getRequestURI().getPath();
                String[] parts = path.split("/");

                int id = 0;
                try {
                    id = Integer.parseInt(parts[parts.length - 1]);
                    Task task = manager.getTaskById(id);
                    response = gson.toJson(task);
                    statusCode = 200;

                } catch (NumberFormatException e) {
                    response = gson.toJson("Нераспознанный идентификатор: " + id);
                    statusCode = 400;
                }

            }
            case "PUT" -> {
                InputStream is = exchange.getRequestBody();
                String jsonTask = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                Task updateTask = gson.fromJson(jsonTask, Task.class);

                String path = exchange.getRequestURI().getPath();
                String[] parts = path.split("/");

                try {
                    int oldTaskId = Integer.parseInt(parts[parts.length - 1]);
                    manager.updateTask(updateTask, oldTaskId);
                    Task oldTask = manager.getTaskById(oldTaskId);
                    response = gson.toJson(oldTask);
                    statusCode = 201;

                } catch (NullPointerException e) {
                    response = gson.toJson("Задача с таким идентификатором не найдена: " + parts[parts.length - 1]);
                    statusCode = 400;

                } catch (NumberFormatException e) {
                    response = gson.toJson("Не правильный формат идентификатора задачи: " + parts[parts.length - 1]);
                    statusCode = 400;

                }


                break;
            }
            case "DELETE" -> {
                String path = exchange.getRequestURI().getPath();
                String[] split = path.split("/");

                try {
                    int lastPart = Integer.parseInt(split[split.length - 1]);
                    manager.removeTask(lastPart);
                    response = gson.toJson(lastPart);
                    statusCode = 200;
                } catch (Exception e) {
                    response = gson.toJson("Неизвестная команда " + e);
                    statusCode = 400;
                }
            }
            default -> {
                response = gson.toJson("Нераспознанная команда");
                statusCode = 501;

            }

        }


        exchange.getResponseHeaders().add("Content-type", "application/json; Charset=UTF-8");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();

    }

}
