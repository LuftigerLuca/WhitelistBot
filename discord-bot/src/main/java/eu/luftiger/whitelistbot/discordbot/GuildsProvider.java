package eu.luftiger.whitelistbot.discordbot;

import eu.luftiger.whitelistbot.discordbot.database.DatabaseQueryHandler;
import eu.luftiger.whitelistbot.discordbot.model.BotRole;
import eu.luftiger.whitelistbot.discordbot.model.BotGuild;
import eu.luftiger.whitelistbot.discordbot.model.BotUser;

import java.util.ArrayList;
import java.util.List;

public class GuildsProvider {

    private final DatabaseQueryHandler databaseQueryHandler;
    private List<BotGuild> guilds;

    public GuildsProvider(DatabaseQueryHandler databaseQueryHandler) {
        this.databaseQueryHandler = databaseQueryHandler;
        this.guilds = new ArrayList<>();
    }

    public void loadGuilds() {
        this.guilds = databaseQueryHandler.getGuilds();
    }

    public void setGuilds(List<BotGuild> guilds) {
        this.guilds = guilds;
    }
    public void addGuild(BotGuild guild) {
        this.guilds.add(guild);
    }

    public void removeGuild(BotGuild guild) {
        this.guilds.remove(guild);
    }

    public void updateDatabase() {
        for (BotGuild guild : guilds) {

            databaseQueryHandler.addGuild(guild);
            for (BotUser user : guild.getUsers()) {
                databaseQueryHandler.addUser(user);
            }

            for (BotRole role : guild.getRoles()) {
                databaseQueryHandler.addGroup(role);
            }
        }
    }

    public BotGuild getBotGuildByID(String guildId) {
        return guilds.stream().filter(guild -> guild.getGuildId().equals(guildId)).findFirst().orElse(null);
    }

    public List<BotGuild> getGuilds() {
        return guilds;
    }
}
