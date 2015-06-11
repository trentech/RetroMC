package com.gmail.trentech.RetroMC.Utils;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.text.format.TextColor;

import com.gmail.trentech.RetroMC.RetroMC;
import com.gmail.trentech.RetroMC.RetroMCMod;
import com.google.common.base.Optional;

public class Utils {
	
    private static RetroMCMod plugin;
	
    public static void setPlugin(RetroMCMod plugin) {
        if (Utils.plugin != null) {
            throw new IllegalStateException("Cannot redefine RetroMCMod plugin!");
        }
        Utils.plugin = plugin;
    }
 
    public static RetroMCMod getplugin() {
        return Utils.plugin;
    }
 
    public static RetroMC getPluginContainer() {
        return Utils.plugin.getPluginContainer();
    }
 
    public static Game getGame() {
        return Utils.plugin.getGame();
    }
    
    public static Server getServer() {
    	return Utils.plugin.getServer();
    }
 
    public static Logger getLogger() {
        return Utils.plugin.getLogger();
    }

	public static int getTimeInSeconds(String time) {
		String[] times = time.split(" ");
		int seconds = 0;
		for(String t : times) {
			if(t.matches("(\\d+)[s]$")) {
				seconds = Integer.parseInt(t.replace("s", "")) + seconds;
			}else if(t.matches("(\\d+)[m]$")) {
				seconds = (Integer.parseInt(t.replace("m", "")) * 60) + seconds;
			}else if(t.matches("(\\d+)[h]$")) {
				seconds = (Integer.parseInt(t.replace("h", "")) * 3600) + seconds;
			}else if(t.matches("(\\d+)[d]$")) {
				seconds = (Integer.parseInt(t.replace("d", "")) * 86400) + seconds;
			}else if(t.matches("(\\d+)[w]$")) {
				seconds = (Integer.parseInt(t.replace("w", "")) * 604800) + seconds;
			}else if(t.matches("(\\d+)[mo]$")) {
				seconds = (Integer.parseInt(t.replace("mo", "")) * 2592000) + seconds;
			}else if(t.matches("(\\d+)[y]$")) {
				seconds = (Integer.parseInt(t.replace("y", "")) * 31557600) + seconds;
			}
		}
		return seconds;
	}
	
	public static String getReadableTime(long timeInSec) {
		long weeks = timeInSec / 604800;
		long wRemainder = timeInSec % 604800;
		long days = wRemainder / 86400;
		long dRemainder = wRemainder % 86400;
		long hours = dRemainder / 3600;
		long hRemainder = dRemainder % 3600;
		long minutes = hRemainder / 60;
		long seconds = hRemainder % 60;
		String time = null;	
		if(weeks > 0) {
			String wks = " Weeks";
			if(weeks == 1) {
				wks = " Week";
			}
			time = weeks + wks;
		}
		if(days > 0 || (days == 0 && weeks > 0)) {
			String dys = " Days";
			if(days == 1) {
				dys = " Day";
			}
			if(time != null) {
				time = time + ", " + days + dys;
			}else{
				time = days + dys;
			}
		}		
		if((hours > 0) || (hours == 0 && days > 0)) {
			String hrs = " Hours";
			if(hours == 1) {
				hrs = " Hour";
			}
			if(time != null) {
				time = time + ", " + hours + hrs;
			}else{
				time = hours + hrs;
			}		
		}
		if((minutes > 0) || (minutes == 0 && days > 0) || (minutes == 0 && hours > 0)) {
			String min = " Minutes";
			if(minutes == 1) {
				min = " Minute";
			}
			if(time != null) {
				time = time + ", " + minutes + min;	
			}else{
				time = minutes + min;
			}			
		}
		if(seconds > 0) {
			String sec = " Seconds";
			if(seconds == 1) {
				sec = " Second";
			}
			if(time != null) {
				time = time + " and " + seconds + sec;
			}else{
				time = seconds + sec;
			}			
		}
		return time;
	}

    public static String translateColorCodes(String textToTranslate) {
    	char colorCode = '&';
    	//final char COLOR_CHAR = '§';
    	final char COLOR_CHAR = '\u00A7';
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == colorCode && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i+1]) > -1) {
                b[i] = COLOR_CHAR;
                b[i+1] = Character.toLowerCase(b[i+1]);
            }
        }
        return new String(b);
    }
    
    public static Optional<TextColor> getColor(String name) {
    	return getGame().getRegistry().getType(TextColor.class, name.toUpperCase());
    }

}
