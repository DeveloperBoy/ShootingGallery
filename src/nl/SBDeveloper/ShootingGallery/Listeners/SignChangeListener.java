package nl.SBDeveloper.ShootingGallery.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import nl.SBDeveloper.ShootingGallery.API.Arena;
import nl.SBDeveloper.ShootingGallery.Managers.ArenaManager;
import nl.SBDeveloper.ShootingGallery.Managers.MessageManager;
import nl.SBDeveloper.ShootingGallery.Managers.SignManager;

public class SignChangeListener implements Listener {
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if (!e.getPlayer().hasPermission("shootinggallery.createsign")) {
			return;
		}
		if (!e.getLine(0).contains("[ShootingG]")) {
			return;
		}
		String id = e.getLine(1);
		if (ArenaManager.getInstance().getArena(id) == null) {
			e.getPlayer().sendMessage(MessageManager.getInstance().getNotCreated(id));
			e.setCancelled(true);
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(id);
		e.setLine(0, ChatColor.DARK_BLUE + "[ShootingG]");
		e.setLine(1, ChatColor.DARK_BLUE + "Arena: " + ChatColor.DARK_RED + a.getId());
		SignManager.getInstance().addSign(e.getBlock().getLocation(), a);
		e.getPlayer().sendMessage(MessageManager.getInstance().getSignCreated(id));
	}
}
