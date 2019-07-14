package nl.SBDeveloper.ShootingGallery.Listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import nl.SBDeveloper.ShootingGallery.Managers.ArenaManager;
import nl.SBDeveloper.ShootingGallery.Managers.FileManager;

public class PlayerMoveListener implements Listener {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (ArenaManager.getInstance().getArena(e.getPlayer()) == null) {
			return;
		}
		if (FileManager.getInstance().getConfig().getBoolean("CanWalkWhenInGame")) {
			return;
		}
		
		Location to = e.getFrom();
		to.setYaw(e.getTo().getYaw());
		to.setPitch(e.getTo().getPitch());
		e.setTo(to);
	}
}
