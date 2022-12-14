package eu.luftiger.whitelistbot.discordbot.model;

import java.util.ArrayList;
import java.util.List;

public class BotUser {

    private final String userId;
    private final String guildId;
    private List<BotPermission> permissions;

    public BotUser(String userId, String guildId) {
        this.userId = userId;
        this.guildId = guildId;
        this.permissions = new ArrayList<>();
    }

    public BotUser setPermissions(List<BotPermission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public BotUser addPermission(BotPermission permission) {
        if(!this.permissions.contains(permission)) {
            this.permissions.add(permission);
        }
        return this;
    }

    public BotUser removePermission(BotPermission permission) {
        this.permissions.remove(permission);
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public String getGuildId() {
        return guildId;
    }

    public boolean hasPermission(BotPermission permission) {
        return permissions.contains(permission);
    }

    public List<BotPermission> getPermissions() {
        return permissions;
    }

}
