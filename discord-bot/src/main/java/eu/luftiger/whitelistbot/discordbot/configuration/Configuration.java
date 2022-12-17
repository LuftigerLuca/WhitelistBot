package eu.luftiger.whitelistbot.discordbot.configuration;

/**
 * This class represents the configuration of the discordbot.
 */
public record Configuration(BotConfiguration discordbot,
                            DatabaseConfiguration database) {
    public String getBotToken() {
        return discordbot.token();
    }

    public String getBotActivityType() {
        return discordbot.activitytype();
    }

    public String getBotActivityName() {
        return discordbot.activityname();
    }

    public String getBotStatus() {
        return discordbot.status();
    }

    public String getBotDefaultPrefix() {
        return discordbot.defaultprefix();
    }

    public String getBotDefaultLanguage() {
        return discordbot.defaultlanguage();
    }

    public String getDatabaseHost() {
        return database.host();
    }

    public int getDatabasePort(){
        return database.port();
    }

    public String getDatabaseName() {
        return database.database();
    }

    public String getDatabaseUser() {
        return database.username();
    }

    public String getDatabasePassword() {
        return database.password();
    }

    public int getDatabaseUpdatePeriod() {
        return database.updateperiod();
    }
}

record BotConfiguration(String token,
                        String activitytype,
                        String activityname,
                        String status,
                        String defaultprefix,
                        String defaultlanguage) {

}

record DatabaseConfiguration(String host,
                             int port,
                             String database,
                             String username,
                             String password,
                             int updateperiod) {

}