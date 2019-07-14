package nl.SBDeveloper.ShootingGallery.Listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import nl.SBDeveloper.ShootingGallery.Managers.MessageManager;
import nl.SBDeveloper.ShootingGallery.Managers.SelectionManager;
import nl.SBDeveloper.ShootingGallery.Utils.XMaterial;

public class SelectionListener implements Listener {
	@EventHandler
	public void onPlayerMakesSelection(PlayerInteractEvent e) {
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			return;
		}
		if (!e.getPlayer().hasPermission("shootinggallery.create")) {
			return;
		}
		if (!(e.getPlayer().getInventory().getItemInMainHand().getType().equals(XMaterial.STICK.parseMaterial()))) {
			return;
		}
		
		EquipmentSlot eslot = e.getHand();
        if (!eslot.equals(EquipmentSlot.HAND)) {
        	return;
        }
		
		e.setCancelled(true);
		
		Location clickedloc = e.getClickedBlock().getLocation();
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			//POS 1
			SelectionManager.setLocOne(e.getPlayer(), clickedloc);
			e.getPlayer().sendMessage(MessageManager.getInstance().getSelectedpointone());
		} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			//POS 2
			SelectionManager.setLocTwo(e.getPlayer(), clickedloc);
			e.getPlayer().sendMessage(MessageManager.getInstance().getSelectedpointtwo());
		}
			
	}
}
