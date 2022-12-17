package eu.luftiger.whitelistbot.discordbot.listener;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;
import eu.luftiger.whitelistbot.discordbot.model.BotGuild;
import eu.luftiger.whitelistbot.discordbot.model.BotUser;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotJoinListener extends ListenerAdapter {

    private final WhitelistBot bot;

    public BotJoinListener(WhitelistBot bot) {
        this.bot = bot;
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event){
        bot.getGuildsProvider().addGuild(new BotGuild(event.getGuild().getId())
                .setName(event.getGuild().getName())
                .setLanguage("en")
                .setPrefix("!")
                .setWhitelistChannel(event.getGuild().getChannels().get(0).getId())
                .addUser(new BotUser(event.getGuild().getOwnerId(), event.getGuild().getId())
                        .setCanConfigure(true)
                        .setCanUnwhitelistOthers(true)
                        .setCanUnwhitelistSelf(true)
                        .setCanWhitelistOthers(true)
                        .setCanWhitelistSelf(true)));
    }
}
