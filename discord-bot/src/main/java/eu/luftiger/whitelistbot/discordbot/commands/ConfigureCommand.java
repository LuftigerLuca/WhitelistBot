package eu.luftiger.whitelistbot.discordbot.commands;

import eu.luftiger.whitelistbot.discordbot.WhitelistBot;
import eu.luftiger.whitelistbot.discordbot.commands.interfaces.BotCommand;
import eu.luftiger.whitelistbot.discordbot.configuration.LanguageConfiguration;
import eu.luftiger.whitelistbot.discordbot.model.BotGuild;
import eu.luftiger.whitelistbot.discordbot.model.BotPermission;
import eu.luftiger.whitelistbot.discordbot.model.BotRole;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ConfigureCommand implements BotCommand {
    @Override
    public void execute(WhitelistBot bot, SlashCommandInteractionEvent event) {
        BotGuild botGuild = bot.getGuildsProvider().getBotGuildByID(event.getGuild().getId());

        LanguageConfiguration languageConfiguration;
        if (botGuild.getLanguage().equals("de")) languageConfiguration = bot.getConfigurationHandler().getGermanLanguageConfiguration();
        else if (botGuild.getLanguage().equals("en")) languageConfiguration = bot.getConfigurationHandler().getEnglishLanguageConfiguration();
        else languageConfiguration = bot.getConfigurationHandler().getEnglishLanguageConfiguration();

        if(botGuild.hasPermission(event.getMember(), BotPermission.CONFIGURE)|| event.getMember().hasPermission(Permission.ADMINISTRATOR)){
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

                case "role" -> {
                    Role role = event.getOption("role").getAsRole();
                    String permission = event.getOption("permission").getAsString();
                    boolean value = event.getOption("value").getAsBoolean();

                    if (!permission.equals("configure") && !permission.equals("whitelist") && !permission.equals("un_whitelist")) {
                        event.reply(languageConfiguration.novalidpermission()).setEphemeral(true).queue();
                        return;
                    }

                    BotPermission botPermission = BotPermission.valueOf(permission.toUpperCase());

                    if (botGuild.getRoleById(role.getId()) == null && value) {
                        botGuild.addRole(new BotRole(role.getId(), event.getGuild().getId())
                                .addPermission(botPermission)
                        );
                    }else{
                        if(value) botGuild.getRoleById(role.getId()).addPermission(botPermission);
                        else botGuild.getRoleById(role.getId()).removePermission(botPermission);
                    }

                    event.reply(languageConfiguration.changedrolepermission().replace("%role", role.getName()).replace("%permission", permission).replace("%value", String.valueOf(value))).setEphemeral(true).queue();
                }

                case "user" -> {
                    User user = event.getOption("user").getAsUser();
                    String permission = event.getOption("permission").getAsString();
                    boolean value = event.getOption("value").getAsBoolean();

                    if (!permission.equals("configure") && !permission.equals("whitelist") && !permission.equals("un_whitelist")) {
                        event.reply(languageConfiguration.novalidpermission()).setEphemeral(true).queue();
                        return;
                    }

                    BotPermission botPermission = BotPermission.valueOf(permission.toUpperCase());

                    if (botGuild.getUserById(user.getId()) == null && value) {
                        botGuild.addUser(new eu.luftiger.whitelistbot.discordbot.model.BotUser(user.getId(), event.getGuild().getId())
                                .addPermission(botPermission)
                        );
                    }else{
                        if(value) botGuild.getUserById(user.getId()).addPermission(botPermission);
                        else botGuild.getUserById(user.getId()).removePermission(botPermission);
                    }

                    event.reply(languageConfiguration.changeduserpermission().replace("%user", user.getAsTag()).replace("%permission", permission).replace("%value", String.valueOf(value))).setEphemeral(true).queue();
                }
            }
        }else {
            event.reply(languageConfiguration.nopermission()).setEphemeral(true).queue();
        }
    }
}
