package TaskServer;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    HttpClient client;
    Gson gson = new Gson();
    String address = "http://localhost:8080/task";
    URI uri = URI.create(address);

    public Client() {
        client = HttpClient.newHttpClient();
    }

    public void getAllTasksResponse() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        printResponse(response);


    }

    public void getTaskByIdResponse(int id) throws IOException, InterruptedException {
        String string = String.valueOf(id);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address + string)).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        printResponse(response);


    }

    public void createTask(String name, String description) throws IOException, InterruptedException {
        Task task = new Task(name, description);
        String json = gson.toJson(task);

        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(json)).header("Content-Type", "text/plain; Charset=UTF-8").build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        printResponse(response);

    }

    public void removeTask(int id) throws IOException, InterruptedException {
        String string = String.valueOf(id);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address + string))
                .DELETE().header("Content-Type", "text/plain; Charset=UTF-8")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        printResponse(response);
    }

    public void printResponse(HttpResponse<String> response) {
        System.out.println("Код ответа: " + response.statusCode());
        System.out.println("Тело: " + response.body());
        System.out.println();
    }


}