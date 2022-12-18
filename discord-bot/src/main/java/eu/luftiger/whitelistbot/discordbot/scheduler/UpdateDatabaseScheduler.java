package eu.luftiger.whitelistbot.discordbot.scheduler;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdateDatabaseScheduler {

    private final WhitelistBot bot;
    private ScheduledExecutorService executor;

    public UpdateDatabaseScheduler(WhitelistBot bot) {
        this.bot = bot;
    }

    public void start(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                bot.getGuildsProvider().updateDatabase();
            }
        };

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnable, 0, bot.getConfigurationHandler().getConfiguration().getDatabaseUpdatePeriod(), TimeUnit.SECONDS);
    }

    public void stop(){
        executor.shutdown();
    }
}
