package nl.SBDeveloper.ShootingGallery.Listeners;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import nl.SBDeveloper.ShootingGallery.Managers.ArenaManager;
import nl.SBDeveloper.ShootingGallery.Managers.SignManager;

public class SignBreakListener implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (ArenaManager.getInstance().getArena(e.getPlayer()) != null) {
			e.setCancelled(true);
		}
		if (!SignManager.getInstance().isSign(e.getBlock().getLocation())) {
			return;
		}
		if (!(e.getBlock().getState() instanceof Sign)) {
			SignManager.getInstance().removeSign(e.getBlock().getLocation());
			return;
		}
		if (e.getPlayer().hasPermission("shootinggallery.createsign")) {
			SignManager.getInstance().removeSign(e.getBlock().getLocation());
			return;
		}
		e.setCancelled(true);
	}
}
