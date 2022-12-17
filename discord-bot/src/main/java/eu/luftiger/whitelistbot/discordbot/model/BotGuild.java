package eu.luftiger.whitelistbot.discordbot.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BotGuild {

    private final String guildId;
    private String name;
    private String prefix;
    private String language;
    private List<BotUser> users;

    public BotGuild(String guildId) {
        this.guildId = guildId;
        this.users = new ArrayList<>();
    }

    public BotGuild setName(String name) {
        this.name = name;
        return this;
    }

    public BotGuild setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public BotGuild setLanguage(String language) {
        this.language = language;
        return this;
    }

    public BotGuild setUsers(List<BotUser> users) {
        this.users = users;
        return this;
    }

    public BotGuild addUser(BotUser user) {
        this.users.add(user);
        return this;
    }

    public BotGuild removeUser(BotUser user) {
        this.users.remove(user);
        return this;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getLanguage() {
        return language;
    }

    public BotUser getUserById(String userId) {
        return users.stream().filter(user -> user.getUserId().equals(userId)).findFirst().orElse(null);
    }

    public List<BotUser> getUsers() {
        return users;
    }
}
