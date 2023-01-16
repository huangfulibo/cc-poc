package cc.nondeb.poc.ccpoc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientConfigurationUtils {

    public static final String CONFIG_FILE = "client.properties";

    public static Properties loadConfig(final String config_file) throws IOException {
        final Properties cfg = new Properties();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(config_file)) {
            cfg.load(inputStream);
        }
        return cfg;
    }
}
