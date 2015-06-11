package com.gmail.trentech.RetroMC.Commands;

import java.io.File;

import ninja.leaping.configurate.ConfigurationNode;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.gmail.trentech.RetroMC.Utils.ConfigLoader;
import com.gmail.trentech.RetroMC.Utils.Notifications;
import com.gmail.trentech.RetroMC.Utils.Utils;

public class CMDReset implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {		
		if(!args.hasAny("playerName")) {
			Notifications notify = new Notifications("Invalid-Argument", null, null);
			src.sendMessage(Texts.of(notify.getMessage()));
			src.sendMessage(Texts.of(TextColors.YELLOW, "/kit reset <player>"));
			return CommandResult.empty();
		}

		String playerName = args.<String>getOne("playerName").get();

		if(Utils.getServer().getPlayer(playerName).get() == null) {
			Notifications notify = new Notifications("No-Player", playerName, null);
			src.sendMessage(Texts.of(notify.getMessage()));
			return CommandResult.empty();
		}		

		Player player = Utils.getServer().getPlayer(playerName).get();
		
        File playerConfigFile = new File("config/RetroMC/Players", player.getUniqueId().toString() + ".conf");
    	ConfigLoader pLoader = new ConfigLoader(playerConfigFile);
    	ConfigurationNode playerConfig = pLoader.getConfig();

        playerConfig.getNode("Lives").setValue(new ConfigLoader(new File("config/RetroMC/config.conf")).getConfig().getNode("Default-Lives").getInt());
        playerConfig.getNode("Banned").setValue(false);
        playerConfig.getNode("Time").setValue(0);

        pLoader.saveConfig();
        
		Notifications notify = new Notifications("Player-Reset", playerName, null);
		src.sendMessage(Texts.of(notify.getMessage()));
		
		return CommandResult.success();
	}

}
