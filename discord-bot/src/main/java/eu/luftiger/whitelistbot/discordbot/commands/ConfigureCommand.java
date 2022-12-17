package eu.luftiger.whitelistbot.discordbot.commands;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;
import eu.luftiger.whitelistbot.discordbot.commands.interfaces.BotCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ConfigureCommand implements BotCommand {
    @Override
    public void execute(WhitelistBot bot, SlashCommandInteractionEvent event) {
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR) || bot.getGuildsProvider().getBotGuildByID(event.getGuild().getId()).getUserById(event.getMember().getId()).isCanConfigure()){
            switch (event.getSubcommandName()){
                case "prefix" -> {

                }
            }
        }else {
            event.reply("You don't have permission to use this command!").setEphemeral(true).queue();
        }
    }
}
