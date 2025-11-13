package TaskServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager(new HashMap<>());

        server.createContext("/tasks", new TaskManagerHandler(inMemoryTaskManager));
        server.createContext("/tasks/", new SingleTaskHandler(inMemoryTaskManager));

        server.start();
        System.out.println("Сервер запущен.");
        System.out.println();


    }


}

