package TaskServer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class TaskManagerHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Manager manager = new Manager(new HashMap<>());
        String method = exchange.getRequestMethod().toUpperCase();
        int statusCode = 500;
        Gson gson = new Gson();
        String response = "";

        switch (method) {
            case "GET" -> {
                response = gson.toJson(manager.getAllTasks());
                statusCode = 200;

            }
            case "POST" -> {
                InputStream is = exchange.getRequestBody();
                String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                try {
                    Task jsonTask = gson.fromJson(jsonString, Task.class);
                    manager.createTask(jsonTask.getName(), jsonTask.getDescription());
                    Task task = manager.getTaskById(jsonTask.getId());
                    response = gson.toJson(task);
                    statusCode = 201;

                } catch (JsonSyntaxException e) {
                    response = gson.toJson("Задача не распознана. Возможно неверно указаны данные " + e);
                    statusCode = 400;

                } catch (NullPointerException e) {
                    response = gson.toJson("Задача не найдена" + e);
                    statusCode = 400;
                }

            }
            default -> {
                response = gson.toJson("Неизвестная команда");
                statusCode = 501;
            }

        }

        exchange.getResponseHeaders().add("Content-type", "application/json; Charset=UTF-8");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        if (bytes.length == 0) {
            exchange.sendResponseHeaders(statusCode, -1);
        } else {
            exchange.sendResponseHeaders(statusCode, bytes.length);
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            System.out.println("Ошибка при отправке данных с сервера");
        }
    }
}
