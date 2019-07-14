package nl.SBDeveloper.ShootingGallery.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import nl.SBDeveloper.ShootingGallery.Managers.ArenaManager;
import nl.SBDeveloper.ShootingGallery.Managers.FileManager;
import nl.SBDeveloper.ShootingGallery.Managers.MessageManager;

public class PlayerTeleportListener implements Listener {
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (ArenaManager.getInstance().getArena(e.getPlayer()) == null) {
			return;
		}
		if (e.getPlayer().hasPermission("shootinggallery.teleport")) {
			return;
		}
		if (FileManager.getInstance().getConfig().getBoolean("CanTeleportWhenInGame")) {
			return;
		}
		
		e.setCancelled(true);
		e.getPlayer().sendMessage(MessageManager.getInstance().getNoTeleport());
	}
}
