package eu.luftiger.whitelistbot.discordbot.listener;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;
import eu.luftiger.whitelistbot.discordbot.configuration.Configuration;
import eu.luftiger.whitelistbot.discordbot.model.BotGuild;
import eu.luftiger.whitelistbot.discordbot.model.BotUser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotJoinListener extends ListenerAdapter {

    private final WhitelistBot bot;
    private final Configuration config;

    public BotJoinListener(WhitelistBot bot) {
        this.bot = bot;
        this.config = bot.getConfigurationHandler().getConfiguration();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event){
        BotGuild guild = new BotGuild(event.getGuild().getId())
                .setName(event.getGuild().getName())
                .setPrefix(config.getBotDefaultPrefix())
                .setLanguage(config.getBotDefaultLanguage());

        bot.getGuildsProvider().addGuild(guild);
    }
}
