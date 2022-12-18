package eu.luftiger.whitelistbot.discordbot.model;

import java.util.ArrayList;
import java.util.List;

public class BotRole {

    private final String roleId;
    private final String guildId;

    private List<BotPermission> permissions;
    public BotRole(String roleId, String guildId) {
        this.roleId = roleId;
        this.guildId = guildId;
        this.permissions = new ArrayList<>();
    }

    public BotRole setPermissions(List<BotPermission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public BotRole addPermission(BotPermission permission) {
        if(!this.permissions.contains(permission)) {
            this.permissions.add(permission);
        }
        return this;
    }

    public BotRole removePermission(BotPermission permission) {
        this.permissions.remove(permission);
        return this;
    }

    public String getRoleId() {
        return roleId;
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
