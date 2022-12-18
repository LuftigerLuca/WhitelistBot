package eu.luftiger.whitelistbot.discordbot.database;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;
import eu.luftiger.whitelistbot.discordbot.model.BotRole;
import eu.luftiger.whitelistbot.discordbot.model.BotGuild;
import eu.luftiger.whitelistbot.discordbot.model.BotPermission;
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

    /**
     * @return List of all guilds in the database
     */
    public List<BotGuild> getGuilds(){
        ArrayList<BotGuild> guilds = new ArrayList<>();

        try {
            //Get all guilds from database
            PreparedStatement statementGuilds = connection.prepareStatement("SELECT * FROM guilds");
            ResultSet resultSetGuild = statementGuilds.executeQuery();

            while (resultSetGuild.next()){
                BotGuild guild = new BotGuild(resultSetGuild.getString("id"))
                        .setName(resultSetGuild.getString("name"))
                        .setPrefix(resultSetGuild.getString("prefix"))
                        .setLanguage(resultSetGuild.getString("language"));

                guilds.add(guild);
            }

            //Get all users from Database
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
                    BotUser botUser = new BotUser(resultSetUser.getString("user_id"), resultSetUser.getString("guild_id"));
                    if(resultSetUser.getBoolean("can_configure")) botUser.addPermission(BotPermission.CONFIGURE);
                    if(resultSetUser.getBoolean("can_whitelist")) botUser.addPermission(BotPermission.WHITELIST);
                    if(resultSetUser.getBoolean("can_unwhitelist")) botUser.addPermission(BotPermission.UN_WHITELIST);

                    guild.addUser(botUser);
                }
            }

            //Get all groups from database
            PreparedStatement statementGroup = connection.prepareStatement("SELECT * FROM roles");
            ResultSet resultSetGroup = statementGroup.executeQuery();

            while (resultSetGroup.next()){
                BotGuild guild = guilds.stream().filter(g -> {
                    try {
                        return g.getGuildId().equals(resultSetGroup.getString("guild_id"));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).findFirst().orElse(null);
                if (guild != null){
                    BotRole botRole = new BotRole(resultSetGroup.getString("role_id"), resultSetGroup.getString("guild_id"));
                    if(resultSetGroup.getBoolean("can_configure")) botRole.addPermission(BotPermission.CONFIGURE);
                    if(resultSetGroup.getBoolean("can_whitelist")) botRole.addPermission(BotPermission.WHITELIST);
                    if(resultSetGroup.getBoolean("can_unwhitelist")) botRole.addPermission(BotPermission.UN_WHITELIST);

                    guild.addRole(botRole);
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

            for (BotRole group : botGuild.getRoles()) {
                addGroup(group);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(BotUser botUser){
        try {
            PreparedStatement statement;
            if(!botUser.hasPermission(BotPermission.CONFIGURE) && !botUser.hasPermission(BotPermission.WHITELIST) && !botUser.hasPermission(BotPermission.UN_WHITELIST)){
                statement = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
                statement.setString(1, botUser.getUserId());
            } else {
                statement = connection.prepareStatement("INSERT INTO users (user_id, guild_id, can_configure, can_whitelist, can_unwhitelist) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE can_configure = ?, can_whitelist = ?, can_unwhitelist = ?");
                statement.setString(1, botUser.getUserId());
                statement.setString(2, botUser.getGuildId());
                statement.setBoolean(3, botUser.hasPermission(BotPermission.CONFIGURE));
                statement.setBoolean(4, botUser.hasPermission(BotPermission.WHITELIST));
                statement.setBoolean(5, botUser.hasPermission(BotPermission.UN_WHITELIST));
                statement.setBoolean(6, botUser.hasPermission(BotPermission.CONFIGURE));
                statement.setBoolean(7, botUser.hasPermission(BotPermission.WHITELIST));
                statement.setBoolean(8, botUser.hasPermission(BotPermission.UN_WHITELIST));

            }
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addGroup(BotRole botRole){
        try {
            PreparedStatement statement;
            if(!botRole.hasPermission(BotPermission.CONFIGURE) && !botRole.hasPermission(BotPermission.WHITELIST) && !botRole.hasPermission(BotPermission.UN_WHITELIST)){
                statement = connection.prepareStatement("DELETE FROM roles WHERE role_id = ?");
                statement.setString(1, botRole.getRoleId());
            } else {
                statement = connection.prepareStatement("INSERT INTO roles (role_id, guild_id, can_configure, can_whitelist, can_unwhitelist) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE can_configure = ?, can_whitelist = ?, can_unwhitelist = ?");
                statement.setString(1, botRole.getRoleId());
                statement.setString(2, botRole.getGuildId());
                statement.setBoolean(3, botRole.hasPermission(BotPermission.CONFIGURE));
                statement.setBoolean(4, botRole.hasPermission(BotPermission.WHITELIST));
                statement.setBoolean(5, botRole.hasPermission(BotPermission.UN_WHITELIST));
                statement.setBoolean(6, botRole.hasPermission(BotPermission.CONFIGURE));
                statement.setBoolean(7, botRole.hasPermission(BotPermission.WHITELIST));
                statement.setBoolean(8, botRole.hasPermission(BotPermission.UN_WHITELIST));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
