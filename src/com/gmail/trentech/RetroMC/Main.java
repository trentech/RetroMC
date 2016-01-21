package com.gmail.trentech.RetroMC;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.gmail.trentech.RetroMC.Commands.CMDRetro;
import com.gmail.trentech.RetroMC.Managers.ConfigManager;

@Plugin(id = Resource.ID, name = Resource.NAME, dependencies = "after:EconomyLite;after:TotalEconomy", version = Resource.VERSION)
public class Main {

	private static Game game;
	private static Logger log;
	private static PluginContainer plugin;

	@Listener
    public void onPreInitialization(GamePreInitializationEvent event) {
		game = Sponge.getGame();
		plugin = getGame().getPluginManager().getPlugin(Resource.ID).get();
		log = getGame().getPluginManager().getLogger(plugin);
	}
	
	@Listener
	public void onInitialization(GameInitializationEvent event) {
		new ConfigManager();
		
		getGame().getCommandManager().register(this, new CMDRetro().cmdRetro, "retro", "r");
		getGame().getEventManager().registerListeners(this, new EventHandler());
	}
	
	@Listener
    public void onPostInitialization(GamePostInitializationEvent event) {

    }

    @Listener
    public void onStartedServer(GameStartedServerEvent event) {

    }

    @Listener
	public void onStoppingServer(GameStoppingServerEvent event) {
		getLog().info("RetroMC has stopped!");
	}
    
    @Listener
    public void onStoppedServer(GameStoppedServerEvent event) {

    }

	public static Game getGame() {
		return game;
	}

	public static Logger getLog() {
		return log;
	}

	public static PluginContainer getPlugin() {
		return plugin;
	}
	
}
