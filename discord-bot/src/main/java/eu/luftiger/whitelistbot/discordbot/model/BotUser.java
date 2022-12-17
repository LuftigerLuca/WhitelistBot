package eu.luftiger.whitelistbot.discordbot.model;

public class BotUser {

    private final String userId;
    private final String guildId;
    private boolean canConfigure;
    private boolean canWhitelist;
    private boolean canUnwhitelist;

    public BotUser(String userId, String guildId) {
        this.userId = userId;
        this.guildId = guildId;
    }

    public BotUser setCanConfigure(boolean canConfigure) {
        this.canConfigure = canConfigure;
        return this;
    }

    public BotUser setCanWhitelist(boolean canWhitelist) {
        this.canWhitelist = canWhitelist;
        return this;
    }

    public BotUser setCanUnwhitelist(boolean canUnwhitelist) {
        this.canUnwhitelist = canUnwhitelist;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public String getGuildId() {
        return guildId;
    }

    public boolean isCanConfigure() {
        return canConfigure;
    }

    public boolean isCanWhitelist() {
        return canWhitelist;
    }

    public boolean isCanUnwhitelist() {
        return canUnwhitelist;
    }

}
