package TaskServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private int port;
    private String url;
    private String basePath;

    public Config() {
        Properties properties = new Properties();

        try {
            InputStream is =
                    getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(is);

            url = properties.getProperty("server.url");
            port = Integer.parseInt(properties.getProperty("server.port"));
            basePath = properties.getProperty("server.basePath");
        } catch (IOException e) {
            e.printStackTrace();
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
