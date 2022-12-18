package eu.luftiger.whitelistbot.discordbot.model;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

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
    private List<BotRole> roles;

    public BotGuild(String guildId) {
        this.guildId = guildId;
        this.users = new ArrayList<>();
        this.roles = new ArrayList<>();
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

    public BotGuild setRoles(List<BotRole> roles) {
        this.roles = roles;
        return this;
    }

    public BotGuild addRole(BotRole role) {
        this.roles.add(role);
        return this;
    }

    public BotGuild removeRole(BotRole role) {
        this.roles.remove(role);
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

    public boolean hasPermission(Member member, BotPermission permission){
        boolean hasPermission = false;

        if (getUserById(member.getId()) != null){
            hasPermission = getUserById(member.getId()).hasPermission(permission);
        }

        for (Role role : member.getRoles()) {
            if(getRoleById(role.getId()) != null){
                hasPermission = getRoleById(role.getId()).hasPermission(permission);
            }
        }

        return hasPermission;
    }

    public BotRole getRoleById(String roleId) {
        return roles.stream().filter(role -> role.getRoleId().equals(roleId)).findFirst().orElse(null);
    }

    public List<BotRole> getRoles() {
        return roles;
    }
}
