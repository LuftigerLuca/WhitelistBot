package eu.luftiger.whitelistbot.discordbot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import eu.luftiger.whitelistbot.discordbot.WhitelistBot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is responsible for loading the configuration from the file system.
 */
public class ConfigurationHandler {

    private final ObjectMapper objectMapper;
    private Configuration configuration;

    public ConfigurationHandler(WhitelistBot whitelistBot) {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
    }

    /**
     * Loads the configuration from the file
     * @throws IOException if an error occurs
     */
    public void loadConfiguration() throws IOException {
        File file = new File("config.yaml");
        if(!file.exists()){
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yaml");
            Files.copy(inputStream, Paths.get("config.yaml"));
        }

        configuration = objectMapper.readValue(file, Configuration.class);
    }


    public Configuration getConfiguration() {
        return configuration;
    }
}
