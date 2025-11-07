package TaskServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        SingleTaskHandler singleTaskHandler = new SingleTaskHandler();
        TaskManagerHandler taskManagerHandler = new TaskManagerHandler();

        server.createContext("/task/id", singleTaskHandler);
        server.createContext("/task", taskManagerHandler);

        server.start();
        System.out.println("Сервер запущен.");


    }


}

