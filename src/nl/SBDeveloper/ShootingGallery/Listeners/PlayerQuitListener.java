package nl.SBDeveloper.ShootingGallery.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.SBDeveloper.ShootingGallery.API.Arena;
import nl.SBDeveloper.ShootingGallery.Managers.ArenaManager;

public class PlayerQuitListener {
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		/**
		 * TODO
		 * @todo Make usable for multiple players system
		 */
		if (ArenaManager.getInstance().getArena(e.getPlayer()) == null) {
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(e.getPlayer());
		ArenaManager.getInstance().forceLeaveArena(a);
	}
}
