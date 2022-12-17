package eu.luftiger.whitelistbot.discordbot.database;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import eu.luftiger.whitelistbot.discordbot.WhitelistBot;
import eu.luftiger.whitelistbot.discordbot.configuration.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides a DataSource for the MySQL database
 */
public class DataSourceProvider {

    /**
     * Initializes a MySQL DataSource
     * @param bot the discordbot instance
     * @param configuration the configuration to use
     * @return the initialized DataSource
     * @throws SQLException if an error occurs
     */
    public static DataSource initMySQLDataSource(WhitelistBot bot, Configuration configuration) throws SQLException {

        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUrl("jdbc:mysql://" +  configuration.getDatabaseUser() +":" + configuration.getDatabasePassword() + "@" + configuration.getDatabaseHost() + ":" + configuration.getDatabasePort() + "/" + configuration.getDatabaseName());

        testDataSource(bot, dataSource);
        return dataSource;
    }


    /**
     * Tests the connection to the database
     * @param bot the discordbot instance
     * @param dataSource the DataSource to test
     * @throws SQLException if an error occurs
     */
    private static void testDataSource(WhitelistBot bot, DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            if (!connection.isValid(1000)) {
                throw new SQLException("Could not establish database connection.");
            }
        }
        if (bot != null) {
            bot.getLogger().info("Database connection established!");
        }
    }
}
