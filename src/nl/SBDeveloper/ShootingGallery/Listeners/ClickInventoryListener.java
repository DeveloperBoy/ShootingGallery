package nl.SBDeveloper.ShootingGallery.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import nl.SBDeveloper.ShootingGallery.Managers.ArenaManager;

public class ClickInventoryListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;
		
		Player p = (Player) e.getWhoClicked();
		if (ArenaManager.getInstance().getArena(p) == null) return;
		
		e.setCancelled(true);
	}
	
}
