package eu.luftiger.whitelistbot.discordbot.model;

public class BotUser {

    private final String userId;
    private final String guildId;
    private boolean canConfigure;
    private boolean canWhitelistOthers;
    private boolean canWhitelistSelf;
    private boolean canUnwhitelistOthers;
    private boolean canUnwhitelistSelf;

    public BotUser(String userId, String guildId) {
        this.userId = userId;
        this.guildId = guildId;
    }

    public BotUser setCanConfigure(boolean canConfigure) {
        this.canConfigure = canConfigure;
        return this;
    }

    public BotUser setCanWhitelistOthers(boolean canWhitelistOthers) {
        this.canWhitelistOthers = canWhitelistOthers;
        return this;
    }

    public BotUser setCanWhitelistSelf(boolean canWhitelistSelf) {
        this.canWhitelistSelf = canWhitelistSelf;
        return this;
    }

    public BotUser setCanUnwhitelistOthers(boolean canUnwhitelistOthers) {
        this.canUnwhitelistOthers = canUnwhitelistOthers;
        return this;
    }

    public BotUser setCanUnwhitelistSelf(boolean canUnwhitelistSelf) {
        this.canUnwhitelistSelf = canUnwhitelistSelf;
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

    public boolean isCanWhitelistOthers() {
        return canWhitelistOthers;
    }

    public boolean isCanWhitelistSelf() {
        return canWhitelistSelf;
    }

    public boolean isCanUnwhitelistOthers() {
        return canUnwhitelistOthers;
    }

    public boolean isCanUnwhitelistSelf() {
        return canUnwhitelistSelf;
    }
}
