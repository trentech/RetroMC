package com.gmail.trentech.RetroMC.Commands;

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
			Notifications notify = new Notifications("Invalid-Argument", null, null, null);
			src.sendMessage(Texts.of(notify.getMessage()));
			src.sendMessage(Texts.of(TextColors.YELLOW, "/kit reset <player>"));
			return CommandResult.empty();
		}

		String playerName = args.<String>getOne("playerName").get();

		if(Utils.getServer().getPlayer(playerName).get() == null) {
			Notifications notify = new Notifications("No-Player", playerName, null, null);
			src.sendMessage(Texts.of(notify.getMessage()));
			return CommandResult.empty();
		}		

		Player player = Utils.getServer().getPlayer(playerName).get();

    	ConfigLoader pLoader = new ConfigLoader("config/RetroMC/Players", player.getUniqueId().toString() + ".conf");
    	ConfigurationNode playerConfig = pLoader.getConfig();

        playerConfig.getNode("Lives").setValue(new ConfigLoader().getConfig().getNode("Default-Lives").getInt());
        playerConfig.getNode("Banned").setValue(false);
        playerConfig.getNode("Time").setValue(0);

        pLoader.saveConfig();
        
		Notifications notify = new Notifications("Player-Reset", playerName, null, null);
		src.sendMessage(Texts.of(notify.getMessage()));
		
		return CommandResult.success();
	}

}
