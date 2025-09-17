package TaskServer;

import java.io.IOException;

public class TestClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();

        client.createTask("task1", "task01");
        client.createTask("task2", "task02");
        client.createTask("task3", "task03");

        client.getAllTasksResponse();


    }
}
