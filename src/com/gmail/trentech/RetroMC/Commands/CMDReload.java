package com.gmail.trentech.RetroMC.Commands;

import java.io.File;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.gmail.trentech.RetroMC.Utils.ConfigLoader;

public class CMDReload implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		ConfigLoader config = new ConfigLoader();
		config.saveConfig();
		File folder = new File("config/RetroMC/Players/");
        File[] files = folder.listFiles();
 	    for(File file : files){
 	    	config = new ConfigLoader("config/RetroMC/Players/", file.getName());
 	    	config.saveConfig();
 	    }
		src.sendMessage(Texts.of(TextColors.DARK_GREEN, "RetroMC Reloaded!"));
		return CommandResult.success();
	}
}
