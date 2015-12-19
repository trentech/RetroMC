package com.gmail.trentech.RetroMC.Commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.RetroMC.Main;
import com.gmail.trentech.RetroMC.Managers.ConfigManager;

import ninja.leaping.configurate.ConfigurationNode;

public class CMDReset implements CommandExecutor {

	public CommandSpec cmdReset = CommandSpec.builder().description(Texts.of("Reset Player!")).permission("RetroMC.cmd.reset")
			.arguments(GenericArguments.optional(GenericArguments.string(Texts.of("playerName")))).executor(this).build();
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {		
		if(!args.hasAny("playerName")) {
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Invalid Argument!"));
			src.sendMessage(Texts.of(TextColors.YELLOW, "/kit reset <player>"));
			return CommandResult.empty();
		}

		String playerName = args.<String>getOne("playerName").get();

		if(Main.getGame().getServer().getPlayer(playerName).get() == null) {
			src.sendMessage(Texts.of(TextColors.DARK_RED, playerName, "Not Found!"));
			return CommandResult.empty();
		}		

		Player player = Main.getGame().getServer().getPlayer(playerName).get();

    	ConfigManager playerConfigManager = new ConfigManager("config/RetroMC/Players", player.getUniqueId().toString() + ".conf");
    	ConfigurationNode playerConfig = playerConfigManager.getConfig();

        playerConfig.getNode("Lives").setValue(new ConfigManager().getConfig().getNode("Lives").getInt());
        playerConfig.getNode("Banned").setValue(false);
        playerConfig.getNode("Time").setValue(0);

        playerConfigManager.save();

		src.sendMessage(Texts.of(TextColors.DARK_GREEN, "Reset!"));
		
		return CommandResult.success();
	}

}
