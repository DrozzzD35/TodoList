package TaskServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class Server {
    public static void main(String[] args) throws IOException {
        Config config = new Config();
        HttpServer server = HttpServer.create(new InetSocketAddress(config.getPort()), 0);
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager(new HashMap<>());

        server.createContext(config.getBasePath(), new TaskManagerHandler(inMemoryTaskManager));
        server.createContext(config.getBasePath() + "/", new SingleTaskHandler(inMemoryTaskManager));

        server.start();
        System.out.println("Сервер запущен.");
        System.out.println();


    }


}

