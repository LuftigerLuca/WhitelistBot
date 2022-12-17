package eu.luftiger.whitelistbot.discordbot;

import eu.luftiger.whitelistbot.discordbot.configuration.ConfigurationHandler;
import eu.luftiger.whitelistbot.discordbot.database.DataSourceProvider;
import eu.luftiger.whitelistbot.discordbot.database.DatabaseQueryHandler;
import eu.luftiger.whitelistbot.discordbot.database.DatabaseSetup;
import eu.luftiger.whitelistbot.discordbot.listener.BotJoinListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Logger;

public class WhitelistBot {

    private Logger logger;
    private ConfigurationHandler configurationHandler;
    private DataSource dataSource;
    private DatabaseQueryHandler databaseQueryHandler;
    private GuildsProvider guildsProvider;
    private JDA jda;

    public static void main(String[] args) {
        try {
            new WhitelistBot().run();
        } catch (InterruptedException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() throws InterruptedException, SQLException, IOException {
        logger = Logger.getLogger("WhitelistBot");
        logger.info("starting WhitelistBot...");

        logger.info("loading configuration...");
        configurationHandler = new ConfigurationHandler(this);
        configurationHandler.loadConfiguration();

        logger.info("loading database...");
        dataSource = DataSourceProvider.initMySQLDataSource(this, configurationHandler.getConfiguration());
        DatabaseSetup.initDatabase(this, dataSource);
        DatabaseQueryHandler databaseQueryHandler = new DatabaseQueryHandler(this, dataSource);

        guildsProvider = new GuildsProvider(databaseQueryHandler);
        guildsProvider.loadGuilds();

        logger.info("starting discord bot...");
        jda = JDABuilder.createDefault(configurationHandler.getConfiguration().getBotToken())
                .setActivity(Activity.of(Activity.ActivityType.valueOf(configurationHandler.getConfiguration().getBotActivityType()), configurationHandler.getConfiguration().getBotActivityName()))
                .setStatus(OnlineStatus.valueOf(configurationHandler.getConfiguration().getBotStatus()))
                .addEventListeners(new BotJoinListener(this))
                .build();

        jda.awaitReady();
        logger.info("WhitelistBot started!");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = reader.readLine();
            if (line.equals("exit")) {
                logger.info("Shutting down...");
                jda.shutdown();
                break;
            }
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public ConfigurationHandler getConfigurationHandler() {
        return configurationHandler;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public DatabaseQueryHandler getDatabaseQueryHandler() {
        return databaseQueryHandler;
    }

    public GuildsProvider getGuildsProvider() {
        return guildsProvider;
    }

    public JDA getJda() {
        return jda;
    }
}