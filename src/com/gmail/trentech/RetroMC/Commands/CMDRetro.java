package com.gmail.trentech.RetroMC.Commands;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;


public class CMDRetro implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Command List:\n"));
		src.sendMessage(Texts.of(TextColors.YELLOW, "/retro -or- /k"));
		if(src.hasPermission("RetroMC.cmd.help")) {
			src.sendMessage(Texts.of(TextColors.YELLOW, "/retro help [command]"));
		}
		if(src.hasPermission("RetroMC.cmd.reload")) {
			src.sendMessage(Texts.of(TextColors.YELLOW, "/retro reload"));
		}
		if(src.hasPermission("RetroMC.cmd.reset")) {
			src.sendMessage(Texts.of(TextColors.YELLOW, "/retro reset <player>"));
		}
		
		return CommandResult.success();		
	}

}
