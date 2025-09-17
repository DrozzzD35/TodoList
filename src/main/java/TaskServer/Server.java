package TaskServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        HelloHandler handler = new HelloHandler();

        server.createContext("/task", handler);

        server.start();
        System.out.println("Сервер запущен. Адресу http://localhost:8080/task");


    }


}
