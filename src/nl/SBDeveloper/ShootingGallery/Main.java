package nl.SBDeveloper.ShootingGallery;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import nl.SBDeveloper.ShootingGallery.Commands.SGCommand;
import nl.SBDeveloper.ShootingGallery.Utils.Metrics;
import nl.SBDeveloper.ShootingGallery.Utils.Updat3r;

public class Main extends JavaPlugin {
	
	private static JavaPlugin instance;
	
	public void onEnable() {
		instance = this;
		
		//Load the command
		this.getCommand("shootinggallery").setExecutor(new SGCommand());
		
		//Load metrics
		Metrics metrics = new Metrics(this);
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		metrics.addCustomChart(new Metrics.SimplePie("nms_version", () -> version));
		
		//Load updat3r
		Updat3r.getInstance().startTask();
		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onJoin(PlayerJoinEvent e) {
				Updat3r.getInstance().sendUpdateMessageLater(e.getPlayer());
			}
		}, this);
	}
	
	public void onDisable() {
		
	}
	
	public static JavaPlugin getInstance() {
		return instance;
	}
}
