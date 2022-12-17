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



}

record BotConfiguration(String token,
                        String activitytype,
                        String activityname,
                        String status) {

}

record DatabaseConfiguration(String host,
                             int port,
                             String database,
                             String username,
                             String password){

}