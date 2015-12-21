package com.gmail.trentech.RetroMC.Commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import com.gmail.trentech.RetroMC.Main;

public class CMDHelp implements CommandExecutor {

	public CommandSpec cmdHelp = CommandSpec.builder().description(Texts.of("Help me")).permission("RetroMC.cmd.help")
			.arguments(GenericArguments.optional(GenericArguments.string(Texts.of("command")))).executor(this).build();
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		PaginationBuilder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();
		
		pages.title(Texts.builder().color(TextColors.DARK_PURPLE).append(Texts.of(TextColors.GOLD, "Command List")).build());
		
		List<Text> list = new ArrayList<>();

		if(!args.hasAny("command")) {
			pages.title(Texts.builder().color(TextColors.DARK_PURPLE).append(Texts.of(TextColors.GOLD, "Command List")).build());
			
			if(src.hasPermission("RetroMC.cmd.help")) {
				list.add(Texts.of(TextColors.GOLD, "/retro help [command]"));
			}
			if(src.hasPermission("RetroMC.cmd.reset")) {
				list.add(Texts.of(TextColors.GOLD, "/retro reset <player>"));
			}
		}else{			
			String cmd = args.<String>getOne("command").get();
			switch(cmd) {
			case "reset":
				pages.title(Texts.builder().color(TextColors.DARK_PURPLE).append(Texts.of(TextColors.GOLD, "RESET")).build());

				list.add(Texts.of(TextColors.DARK_PURPLE, TextStyles.UNDERLINE, "Description:"));
				list.add(Texts.of(TextColors.GOLD, "    Resets players lives."));
				list.add(Texts.of(TextColors.DARK_PURPLE, TextStyles.UNDERLINE, "Example:"));
				list.add(Texts.of(TextColors.GOLD, "    /retro reset Notch"));
				break;
			default:
				list.add(Texts.of(TextColors.DARK_RED, "Not a valid command"));
			}
		}
		
		pages.contents(list);
		
		pages.sendTo(src);
		
		return CommandResult.success();
	}

}
