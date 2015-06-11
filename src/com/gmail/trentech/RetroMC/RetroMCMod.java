package com.gmail.trentech.RetroMC;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;

public class RetroMCMod {
	
    private final RetroMC plugin;
    private final Game game;
    private final Logger logger;
 
    public RetroMCMod(RetroMC plugin, Game game, Logger logger) {
        this.plugin = plugin;
        this.game = game;
        this.logger = logger;
    }
 
    public RetroMC getPluginContainer() {
        return this.plugin;
    }
 
    public Game getGame() {
        return this.game;
    }
 
    public Logger getLogger() {
        return this.logger;
    }

    public Server getServer() {
    	return this.game.getServer();
    }

}
