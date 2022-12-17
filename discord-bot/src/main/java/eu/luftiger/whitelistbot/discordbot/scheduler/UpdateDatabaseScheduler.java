package eu.luftiger.whitelistbot.discordbot.scheduler;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class UpdateDatabaseScheduler {

    private final WhitelistBot bot;
    private Runnable runnable;

    public UpdateDatabaseScheduler(WhitelistBot bot) {
        this.bot = bot;
    }

    public void start(){
        runnable = new Runnable() {
            @Override
            public void run() {
                bot.getGuildsProvider().updateDatabase();
            }
        };


    }
}
