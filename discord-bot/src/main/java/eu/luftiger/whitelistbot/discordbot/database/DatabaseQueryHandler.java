package eu.luftiger.whitelistbot.discordbot.database;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;
import eu.luftiger.whitelistbot.discordbot.model.BotGuild;
import eu.luftiger.whitelistbot.discordbot.model.BotUser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryHandler {

    private final WhitelistBot bot;
    private Connection connection;


    public DatabaseQueryHandler(WhitelistBot bot, DataSource dataSource) throws SQLException {
        this.bot = bot;
        this.connection = dataSource.getConnection();
    }

    public List<BotGuild> getGuilds(){
        ArrayList<BotGuild> guilds = new ArrayList<>();

        try {
            PreparedStatement statementGuilds = connection.prepareStatement("SELECT * FROM guilds");
            ResultSet resultSetGuild = statementGuilds.executeQuery();

            while (resultSetGuild.next()){
                BotGuild guild = new BotGuild(resultSetGuild.getString("id"))
                        .setName(resultSetGuild.getString("name"))
                        .setPrefix(resultSetGuild.getString("prefix"))
                        .setLanguage(resultSetGuild.getString("language"));

                guilds.add(guild);
            }

            PreparedStatement statementUser = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSetUser = statementUser.executeQuery();

            while (resultSetUser.next()){
                BotGuild guild = guilds.stream().filter(g -> {
                    try {
                        return g.getGuildId().equals(resultSetUser.getString("guild_id"));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).findFirst().orElse(null);
                if (guild != null){
                    guild.addUser(new BotUser(resultSetUser.getString("user_id"), resultSetUser.getString("guild_id"))
                            .setCanConfigure(resultSetUser.getBoolean("can_configure"))
                            .setCanWhitelist(resultSetUser.getBoolean("can_whitelist"))
                            .setCanUnwhitelist(resultSetUser.getBoolean("can_unwhitelist"))
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guilds;
    }

    public void addGuild(BotGuild botGuild){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO guilds (id, name, prefix, language) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE name = ?, prefix = ?, language = ?");
            statement.setString(1, botGuild.getGuildId());
            statement.setString(2, botGuild.getName());
            statement.setString(3, botGuild.getPrefix());
            statement.setString(4, botGuild.getLanguage());
            statement.setString(5, botGuild.getName());
            statement.setString(6, botGuild.getPrefix());
            statement.setString(7, botGuild.getLanguage());

            statement.execute();

            for (BotUser user : botGuild.getUsers()) {
                addUser(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(BotUser botUser){
        try {
            PreparedStatement statement;
            if(!botUser.isCanConfigure() && !botUser.isCanWhitelist()){
                statement = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
                statement.setString(1, botUser.getUserId());
            } else {
                statement = connection.prepareStatement("INSERT INTO users (user_id, guild_id, can_configure, can_whitelist, can_unwhitelist) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE can_configure = ?, can_whitelist = ?, can_unwhitelist = ?");
                statement.setString(1, botUser.getUserId());
                statement.setString(2, botUser.getGuildId());
                statement.setBoolean(3, botUser.isCanConfigure());
                statement.setBoolean(4, botUser.isCanWhitelist());
                statement.setBoolean(5, botUser.isCanUnwhitelist());
                statement.setBoolean(6, botUser.isCanConfigure());
                statement.setBoolean(7, botUser.isCanWhitelist());
                statement.setBoolean(8, botUser.isCanUnwhitelist());
            }
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
