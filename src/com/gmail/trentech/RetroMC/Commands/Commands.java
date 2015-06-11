package com.gmail.trentech.RetroMC.Commands;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

public class Commands {
	
	public CommandSpec cmdReload = CommandSpec.builder()
		    .description(Texts.of("Reload Plugin"))
		    .permission("RetroMC.cmd.reload")
		    .executor(new CMDReload())
		    .build();
	
	public CommandSpec cmdReset = CommandSpec.builder()
		    .description(Texts.of("Reset Player!"))
		    .permission("RetroMC.cmd.reset")
		    .arguments(GenericArguments.optional(GenericArguments.string(Texts.of("playerName"))))
		    .executor(new CMDReset())
		    .build();
	
	public CommandSpec cmdHelp = CommandSpec.builder()
		    .description(Texts.of("Help me!"))
		    .permission("RetroMC.cmd.help")
		    .arguments(GenericArguments.optional(GenericArguments.string(Texts.of("command"))))
		    .executor(new CMDHelp())
		    .build();
	
	public CommandSpec cmdRetro = CommandSpec.builder()
		    .description(Texts.of("The Main Kit Command"))
		    .permission("RetroMC.cmd")	    
		    .child(cmdReload, "reload")
		    .child(cmdHelp, "help")
		    .child(cmdReset, "reset")
		    .executor(new CMDRetro())
		    .build();
}
