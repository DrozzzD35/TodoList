package Client;

import TaskServer.Config;
import TaskServer.Task;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//TODO Exception не обобщать, так же отредактировать статусКод - Готово
//TODO Вынести в конфиг URL и port, считать данные из файла application.properties - Готово
//TODO printLN = плохо. Вызывающая сторона печатает (TestClient) - Готово

public class Client {
    private HttpClient client;
    private Gson gson = new Gson();
    Config config = new Config();
    String fullUrl = config.getUrl() + ":" + config.getPort() + config.getBasePath();


    public Client() {
        client = HttpClient.newHttpClient();
    }

    public HttpResponse<String> getAllTasks() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl)).GET().build();


        return client.send(request
                , HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getTaskByIdResponse(int id)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl + "/" + id)).GET().build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public HttpResponse<String> createTask(String name, String description) throws IOException, InterruptedException {
        Task requestTask = new Task(name, description);
        String json = gson.toJson(requestTask);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fullUrl))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type",
                        "application/json; Charset=UTF-8")
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> updateTask(int id, Task updateTask) throws IOException, InterruptedException {
        String json = gson.toJson(updateTask);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl + "/" + id))
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json; Charset=UTF-8").build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> removeTask(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fullUrl + "/" + id))
                .DELETE()
                .header("Content-Type", "application/json; Charset=UTF-8").build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}