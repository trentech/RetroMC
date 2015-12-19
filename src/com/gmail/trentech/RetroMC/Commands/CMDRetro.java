package com.gmail.trentech.RetroMC.Commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;


public class CMDRetro implements CommandExecutor {

	public CommandSpec cmdRetro = CommandSpec.builder().description(Texts.of("The Main Kit Command")).permission("RetroMC.cmd")	    
			.child(new CMDHelp().cmdHelp, "help").child(new CMDReset().cmdReset, "reset").executor(this).build();
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Command List:\n"));
		src.sendMessage(Texts.of(TextColors.YELLOW, "/retro -or- /r"));
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
