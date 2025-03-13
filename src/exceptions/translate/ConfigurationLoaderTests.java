package exceptions.translate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConfigurationLoaderTests {

    @Test
    public void testConfigurationFileDoesNotExist() {
        String configPath = "./non-existent";

        ConfigurationLoader loader = new ConfigurationLoader();
        assertThrows(ConfigurationException.class, () -> loader.readConfiguration(configPath));
    }
    @Test
    public void testConfigurationFileIsDirectory() {
        String configPath = "./";

        ConfigurationLoader loader = new ConfigurationLoader();
        assertThrows(InstallationException.class, () -> loader.readConfiguration(configPath));
    }
    @Test
    public void testReadConfigurationSuccessfully() throws Exception {
        String configPath = "./existing-config-file.txt";
        ConfigurationLoader loader = new ConfigurationLoader();
        String configContent = loader.readConfiguration(configPath);
        System.out.println("Configuration content: " + configContent);
    }
}