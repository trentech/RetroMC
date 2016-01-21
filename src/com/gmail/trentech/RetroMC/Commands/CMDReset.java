package com.gmail.trentech.RetroMC.Commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.RetroMC.Main;
import com.gmail.trentech.RetroMC.Data.PlayerData;
import com.gmail.trentech.RetroMC.Managers.ConfigManager;

public class CMDReset implements CommandExecutor {

	public CommandSpec cmdReset = CommandSpec.builder().description(Text.of("Reset Player!")).permission("retro.cmd.reset")
			.arguments(GenericArguments.optional(GenericArguments.string(Text.of("playerName")))).executor(this).build();
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {		
		if(!args.hasAny("playerName")) {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Invalid Argument!"));
			src.sendMessage(Text.of(TextColors.YELLOW, "/kit reset <player>"));
			return CommandResult.empty();
		}

		String playerName = args.<String>getOne("playerName").get();

		if(Main.getGame().getServer().getPlayer(playerName).get() == null) {
			src.sendMessage(Text.of(TextColors.DARK_RED, playerName, "Not Found!"));
			return CommandResult.empty();
		}		

		Player player = Main.getGame().getServer().getPlayer(playerName).get();

		PlayerData playerData = player.get(PlayerData.class).get();
		
		playerData.lives().set(new ConfigManager().getConfig().getNode("Lives").getInt());
		
		player.offer(playerData);

		src.sendMessage(Text.of(TextColors.DARK_GREEN, "Reset!"));
		
		return CommandResult.success();
	}

}
