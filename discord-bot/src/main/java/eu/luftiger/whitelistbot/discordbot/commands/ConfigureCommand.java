package eu.luftiger.whitelistbot.discordbot.commands;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;
import eu.luftiger.whitelistbot.discordbot.commands.interfaces.BotCommand;
import eu.luftiger.whitelistbot.discordbot.configuration.LanguageConfiguration;
import eu.luftiger.whitelistbot.discordbot.model.BotGuild;
import eu.luftiger.whitelistbot.discordbot.model.BotUser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ConfigureCommand implements BotCommand {
    @Override
    public void execute(WhitelistBot bot, SlashCommandInteractionEvent event) {
        BotGuild botGuild = bot.getGuildsProvider().getBotGuildByID(event.getGuild().getId());

        LanguageConfiguration languageConfiguration;
        if (botGuild.getLanguage().equals("de")) languageConfiguration = bot.getConfigurationHandler().getGermanLanguageConfiguration();
        else if (botGuild.getLanguage().equals("en")) languageConfiguration = bot.getConfigurationHandler().getEnglishLanguageConfiguration();
        else languageConfiguration = bot.getConfigurationHandler().getEnglishLanguageConfiguration();


        BotUser user = botGuild.getUserById(event.getUser().getId());
        if((user != null && user.isCanConfigure()) || event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            switch (event.getSubcommandName()){
                case "prefix" -> {
                    String prefix = event.getOption("prefix").getAsString();
                    bot.getGuildsProvider().getBotGuildByID(event.getGuild().getId()).setPrefix(prefix);
                    event.reply(languageConfiguration.changedprefix().replace("%prefix", prefix)).setEphemeral(true).queue();
                }
                case "language" -> {
                    String language = event.getOption("language").getAsString().toLowerCase();
                    if(!language.equals("en") && !language.equals("de")){
                        event.reply(languageConfiguration.novalidlanguage()).setEphemeral(true).queue();
                        return;
                    }
                    bot.getGuildsProvider().getBotGuildByID(event.getGuild().getId()).setLanguage(language);
                    event.reply(languageConfiguration.changedlanguage().replace("%language", language)).setEphemeral(true).queue();
                }
            }
        }else {
            event.reply(languageConfiguration.nopermission()).setEphemeral(true).queue();
        }
    }
}
