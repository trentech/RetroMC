package com.gmail.trentech.RetroMC;

import java.util.HashMap;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStoppedEvent;

import com.gmail.trentech.RetroMC.Commands.Commands;
import com.gmail.trentech.RetroMC.Utils.ConfigLoader;
import com.gmail.trentech.RetroMC.Utils.Notifications;
import com.gmail.trentech.RetroMC.Utils.Utils;
import com.google.inject.Inject;

public class RetroMC {
	
	public static HashMap<String, String> messages = new HashMap<String, String>();

	@Inject
	private Logger logger;
	   
	@Inject
	private Game game;
	
	@Subscribe
    public void onPreInit(PreInitializationEvent event) {
		new ConfigLoader().initConfig();		
	}
	
	@Subscribe
	public void onServerInit(InitializationEvent event) {
		Utils.setPlugin(new RetroMCMod(this, this.game, this.logger));
		game.getCommandDispatcher().register(this, new Commands().cmdRetro, "retro", "r");
		game.getEventManager().register(this, new EventListener());
	}
	
	@Subscribe
	public void onServerStart(ServerStartedEvent event) {
		new Notifications().getMessages();

		logger.info("RetroMC has started!");
	}
	
	@Subscribe
	public void onServerStop(ServerStoppedEvent event) {
		logger.info("RetroMC has stopped!");
	}
}
