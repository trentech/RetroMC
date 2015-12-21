package com.gmail.trentech.RetroMC.Commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.RetroMC.Main;

public class CMDRetro implements CommandExecutor {

	public CommandSpec cmdRetro = CommandSpec.builder().description(Texts.of("The Main Kit Command")).permission("RetroMC.cmd")	    
			.child(new CMDHelp().cmdHelp, "help").child(new CMDReset().cmdReset, "reset").executor(this).build();
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		PaginationBuilder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();
		
		pages.title(Texts.builder().color(TextColors.DARK_PURPLE).append(Texts.of(TextColors.GOLD, "Command List")).build());
		
		List<Text> list = new ArrayList<>();
		
		if(src.hasPermission("RetroMC.cmd.help")) {
			list.add(Texts.of(TextColors.GOLD, "/retro help [command]"));
		}
		if(src.hasPermission("RetroMC.cmd.reset")) {
			list.add(Texts.of(TextColors.GOLD, "/retro reset <player>"));
		}

		pages.contents(list);
		
		pages.sendTo(src);
		
		return CommandResult.success();		
	}

}
