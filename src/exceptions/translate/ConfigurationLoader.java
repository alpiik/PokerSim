package exceptions.translate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationLoader {

    public String readConfiguration(String configFilePath) {
        Path path = Paths.get(configFilePath);
        if (!Files.exists(path)) {
            throw new RuntimeException("Configuration file does not exist: " + configFilePath);
        }
        if (Files.isDirectory(path)) {
            throw new RuntimeException("Configuration file path points to a directory: " + configFilePath);
        }
        try {
            return String.join("\n", Files.readAllLines(path));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read configuration file: " + e.getMessage());
        }
    }
}