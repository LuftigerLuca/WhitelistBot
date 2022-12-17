package eu.luftiger.whitelistbot.discordbot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import eu.luftiger.whitelistbot.discordbot.WhitelistBot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * This class is responsible for loading the configuration from the file system.
 */
public class ConfigurationHandler {

    private final ObjectMapper objectMapper;
    private Configuration configuration;
    private LanguageConfiguration germanLanguageConfiguration;
    private LanguageConfiguration englishLanguageConfiguration;

    public ConfigurationHandler(WhitelistBot whitelistBot) {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
    }

    /**
     * Loads the configuration from the file
     * @throws IOException if an error occurs
     */
    public void loadConfiguration() throws IOException {
        File configurationFile = new File("config.yaml");
        if(!configurationFile.exists()){
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yaml");
            Files.copy(inputStream, Paths.get("config.yaml"));
        }
        configuration = objectMapper.readValue(configurationFile, Configuration.class);

        File germanLanguageFile = new File("de.yaml");
        if(!germanLanguageFile.exists()){
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("de.yaml");
            Files.copy(inputStream, Paths.get("de.yaml"));
        }
        germanLanguageConfiguration = objectMapper.readValue(germanLanguageFile, LanguageConfiguration.class);

        File englishLanguageFile = new File("en.yaml");
        if(!englishLanguageFile.exists()){
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("en.yaml");
            Files.copy(inputStream, Paths.get("en.yaml"));
        }
        englishLanguageConfiguration = objectMapper.readValue(englishLanguageFile, LanguageConfiguration.class);
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public LanguageConfiguration getGermanLanguageConfiguration() {
        return germanLanguageConfiguration;
    }

    public LanguageConfiguration getEnglishLanguageConfiguration() {
        return englishLanguageConfiguration;
    }
}
