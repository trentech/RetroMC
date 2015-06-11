package com.gmail.trentech.RetroMC.Commands;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.gmail.trentech.RetroMC.Utils.Notifications;

public class CMDHelp implements CommandExecutor {

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
					Notifications notify = new Notifications("Permission-Denied", null, null);
					src.sendMessage(Texts.of(notify.getMessage()));
					break;
				}
				src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Description:\n"));
				src.sendMessage(Texts.of(TextColors.YELLOW, "    Reloads the Plugin."));
				src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Example:\n"));
				src.sendMessage(Texts.of(TextColors.YELLOW, "    /retro reload"));
				break;
			case "reset":
				if(!src.hasPermission("RetroMC.cmd.reset")) {
					Notifications notify = new Notifications("Permission-Denied", null, null);
					src.sendMessage(Texts.of(notify.getMessage()));
					break;
				}
				src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Description:\n"));
				src.sendMessage(Texts.of(TextColors.YELLOW, "    Resets players lives."));
				src.sendMessage(Texts.of(TextColors.DARK_GREEN, TextStyles.UNDERLINE, "Example:\n"));
				src.sendMessage(Texts.of(TextColors.YELLOW, "    /retro reset Notch"));
				break;
			default:
				Notifications notify = new Notifications("Invalid-Argument", null, null);
				src.sendMessage(Texts.of(notify.getMessage()));
			}
		}
		return CommandResult.success();
	}

}
