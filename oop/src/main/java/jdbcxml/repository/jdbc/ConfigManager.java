package jdbcxml.repository.jdbc;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Менеджер загрузки конфигурации БД.
 */
public class ConfigManager {
    private static final ConfigManager configManager = new ConfigManager();
    private static final Config config = ConfigFactory.load();

    public static ConfigManager getInstance() {
        return configManager;
    }

    public String getString(String key) {
        return config.getString(key);
    }

    public int getInt(String key) {
        return config.getInt(key);
    }

}
