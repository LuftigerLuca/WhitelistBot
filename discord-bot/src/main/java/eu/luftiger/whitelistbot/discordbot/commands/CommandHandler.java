package eu.luftiger.whitelistbot.discordbot.commands;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;
import eu.luftiger.whitelistbot.discordbot.configuration.Configuration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class CommandHandler extends ListenerAdapter {

    private final WhitelistBot bot;
    private final JDA jda;
    private final Configuration config;

    public CommandHandler(WhitelistBot bot) {
        this.bot = bot;
        this.jda = bot.getJda();
        this.config = bot.getConfigurationHandler().getConfiguration();
    }

    public void registerSlashCommands() {
        SlashCommandData whitelistCommand = Commands.slash("whitelist", "Whitelist a user")
                .addOption(OptionType.STRING, "user", "The user to whitelist", true);

        SlashCommandData unwhitelistCommand = Commands.slash("unwhitelist", "Unwhitelist a user")
                .addOption(OptionType.STRING, "user", "The user to unwhitelist", true);

        SlashCommandData configureCommand = Commands.slash("configure", "Configure the bot")
                .addSubcommands(new SubcommandData("prefix", "Change the prefix")
                                .addOption(OptionType.STRING, "prefix", "The new prefix", true),
                        new SubcommandData("language", "Change the language")
                                .addOption(OptionType.STRING, "language", "The new language", true),
                        new SubcommandData("role", "Edit a roles permissions")
                                .addOption(OptionType.ROLE, "role", "The role to edit", true)
                                .addOption(OptionType.STRING, "permission", "the permission to edit, either configure, whitelist or un_whitelist", true)
                                .addOption(OptionType.BOOLEAN, "value", "The new value of the permission", true),
                        new SubcommandData("user", "Edit a users permissions")
                                .addOption(OptionType.USER, "user", "The user to edit", true)
                                .addOption(OptionType.STRING, "permission", "the permission to edit, either configure, whitelist or un_whitelist", true)
                                .addOption(OptionType.BOOLEAN, "value", "The new value of the permission", true));

        jda.updateCommands().addCommands(whitelistCommand, unwhitelistCommand, configureCommand).queue();

    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        switch (event.getName()){
            case "configure" -> new ConfigureCommand().execute(bot, event);
            case "whitelist" -> new WhitelistCommand().execute(bot, event);
            case "unwhitelist" -> new UnwhitelistCommand().execute(bot, event);
        }
    }
}
