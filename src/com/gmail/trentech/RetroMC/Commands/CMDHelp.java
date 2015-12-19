package com.gmail.trentech.RetroMC.Commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class CMDHelp implements CommandExecutor {

	public CommandSpec cmdHelp = CommandSpec.builder().description(Texts.of("Help me")).permission("RetroMC.cmd.help")
			.arguments(GenericArguments.optional(GenericArguments.string(Texts.of("command")))).executor(this).build();
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(!args.hasAny("command")) {
			src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Command List:\n"));
			src.sendMessage(Texts.of(TextColors.YELLOW, "/retro -or- /k"));
			src.sendMessage(Texts.of(TextColors.YELLOW, "/retro help [command]"));
			if(src.hasPermission("RetroMC.cmd.reload")) {
				src.sendMessage(Texts.of(TextColors.YELLOW, "/retro reload"));
			}
			if(src.hasPermission("RetroMC.cmd.reset")) {
				src.sendMessage(Texts.of(TextColors.YELLOW, "/retro reset <player>"));
			}
	
		}else{
			String cmd = args.<String>getOne("command").get();
			switch(cmd) {
			case "reload":
				if(!src.hasPermission("RetroMC.cmd.reload")) {
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Permission Denied!"));
					break;
				}
				src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Description:\n"));
				src.sendMessage(Texts.of(TextColors.YELLOW, "    Reloads the Plugin."));
				src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Example:\n"));
				src.sendMessage(Texts.of(TextColors.YELLOW, "    /retro reload"));
				break;
			case "reset":
				if(!src.hasPermission("RetroMC.cmd.reset")) {
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Permission Denied!"));
					break;
				}
				src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Description:\n"));
				src.sendMessage(Texts.of(TextColors.YELLOW, "    Resets players lives."));
				src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Example:\n"));
				src.sendMessage(Texts.of(TextColors.YELLOW, "    /retro reset Notch"));
				break;
			default:
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Invalid Argument!"));
			}
		}
		return CommandResult.success();
	}

}
