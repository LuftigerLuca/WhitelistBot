package eu.luftiger.whitelistbot.discordbot.commands.interfaces;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface BotCommand {
    void execute(WhitelistBot bot, SlashCommandInteractionEvent event);
}
