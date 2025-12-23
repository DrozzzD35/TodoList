package TaskServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final int port;
    private final String url;
    private final String basePath;

    public Config() throws IOException {
        Properties properties = new Properties();

        try (InputStream is =
                     getClass().getClassLoader().getResourceAsStream("application.properties")) {

            if (is == null) {
                throw new RuntimeException("Файл application.properties не найден");
            }

            properties.load(is);

            url = properties.getProperty("server.url");
            port = Integer.parseInt(properties.getProperty("server.port"));
            basePath = properties.getProperty("server.basePath");
        } catch (IOException e) {
            throw new IOException("Ошибка конфига " + e);
        } catch (NullPointerException e) {
            throw new NullPointerException("Неверно указан формат порта" + e);
        }

    }

    public int getPort() {
        return port;
    }

    public String getUrl() {
        return url;
    }

    public String getBasePath() {
        return basePath;
    }
}
