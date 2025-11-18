package TaskServer;

import Service.InMemoryTaskManager;
import Service.Task;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class SingleTaskHandler implements HttpHandler {
    private InMemoryTaskManager inMemoryTaskManager;
    private static final Gson gson = new Gson();


    public SingleTaskHandler(InMemoryTaskManager inMemoryTaskManager) {
        this.inMemoryTaskManager = inMemoryTaskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod().toUpperCase();
        String path = exchange.getRequestURI().getPath();
        String[] parts = path.split("/");
        int statusCode;
        String response;
        int id;


        switch (method) {
            case "GET" -> {

                try {
                    id = Integer.parseInt(parts[parts.length - 1]);
                    Task task = inMemoryTaskManager.getTaskById(id);

                    if (task == null) {
                        response = gson
                                .toJson("Задача с таким идентификатором не найдена "
                                        + parts[parts.length - 1]);
                        statusCode = 404;

                    } else {
                        response = gson.toJson(task);
                        statusCode = 200;
                    }

                } catch (NumberFormatException e) {
                    response = gson
                            .toJson("Неверный JSON " + e.getMessage());
                    statusCode = 404;
                }

            }
            case "PUT" -> {
                InputStream is = exchange.getRequestBody();
                String jsonTask = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                try {
                    Task updateTask = gson.fromJson(jsonTask, Task.class);
                    int oldTaskId = Integer.parseInt(parts[parts.length - 1]);
                    inMemoryTaskManager.updateTask(updateTask, oldTaskId);
                    Task oldTask = inMemoryTaskManager.getTaskById(oldTaskId);
                    response = gson.toJson(oldTask);
                    statusCode = 200;

                } catch (NullPointerException e) {
                    response = gson
                            .toJson("Задача с таким идентификатором не найдена: "
                                    + parts[parts.length - 1] + " " + e.getMessage());
                    statusCode = 404;

                } catch (NumberFormatException e) {
                    response = gson
                            .toJson("Не правильный формат идентификатора задачи: "
                                    + parts[parts.length - 1] + " " + e.getMessage());
                    statusCode = 400;

                } catch (JsonSyntaxException e) {
                    response = gson.toJson("Задача не распознана. Неверный JSON "
                            + e.getMessage());
                    statusCode = 400;
                }

            }
            case "DELETE" -> {
                try {
                    id = Integer.parseInt(parts[parts.length - 1]);
                    Task task = inMemoryTaskManager.getTaskById(id);
                    inMemoryTaskManager.removeTask(id);

                    if (task == null) {
                        response = gson
                                .toJson("Задача с таким идентификатором не найдена "
                                        + parts[parts.length - 1]);
                        statusCode = 404;
                    } else {
                        response = gson.toJson(id);
                        statusCode = 200;
                    }

                } catch (NumberFormatException e) {
                    response = gson.toJson(e.getMessage());
                    statusCode = 400;
                }
            }
            default -> {
                response = gson.toJson("Неизвестная команда. Мы находимся в SingleTaskHandler");
                statusCode = 501;

            }

        }


        exchange.getResponseHeaders()
                .add("Content-type", "application/json; Charset=UTF-8");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        } catch (IOException e) {
            System.out.println("Ошибка при отправке данных " + e);
        }

    }

}
