package nl.SBDeveloper.ShootingGallery.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import nl.SBDeveloper.ShootingGallery.Managers.ArenaManager;

public class DropItemListener implements Listener {
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if (ArenaManager.getInstance().getArena(e.getPlayer()) == null) return;
		e.setCancelled(true);
	}
}
