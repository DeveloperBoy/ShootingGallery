package nl.SBDeveloper.ShootingGallery.Listeners;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import nl.SBDeveloper.ShootingGallery.API.Arena;
import nl.SBDeveloper.ShootingGallery.Managers.ArenaManager;
import nl.SBDeveloper.ShootingGallery.Managers.MessageManager;
import nl.SBDeveloper.ShootingGallery.Managers.SignManager;

public class ClickSignListener implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		if (!e.getPlayer().hasPermission("shootinggallery.use")) {
			return;
		}
		if (!(e.getClickedBlock().getState() instanceof Sign)) {
			return;
		}
		Sign s = (Sign) e.getClickedBlock().getState();
		if (!SignManager.getInstance().isSign(s.getLocation())) {
			return;
		}
		if (ArenaManager.getInstance().getArena(s.getLine(1).substring(s.getLine(1).indexOf(":") + 4)) == null) {
			e.getPlayer().sendMessage(s.getLine(1).substring(s.getLine(1).indexOf(":") + 4));
			e.getPlayer().sendMessage(
					MessageManager.getInstance().getErrorMessage("Sign does not point to an valid arena!"));
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(s.getLine(1).substring(s.getLine(1).indexOf(":") + 4));
		if (!a.isEnabled()) {
			e.getPlayer().sendMessage(MessageManager.getInstance().getNotEnabled(a.getId()));
			return;
		}
		if (a.isInUse()) {
			e.getPlayer().sendMessage(MessageManager.getInstance().getArenaInUse(a.getId()));
			return;
		}
		ArenaManager.getInstance().joinArena(e.getPlayer(), a);
		e.getPlayer().sendMessage(MessageManager.getInstance().getJoinArena(a.getId()));
	}
}
