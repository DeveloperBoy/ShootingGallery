package nl.SBDeveloper.ShootingGallery.API;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArenaPlayer {
	private Player p;
	private Location loc;
	private ItemStack[] invContent;
	private int XPLevel;
	private float XPFloat;
	private int scores;
	
	public Player getPlayer() {
		return p;
	}
	
	public void setPlayer(Player p) {
		this.p = p;
	}

	public Location getPLocation() {
		return loc;
	}

	public void setPLocation(Location loc) {
		this.loc = loc;
	}

	public ItemStack[] getInvContent() {
		return invContent;
	}

	public void setInvContent(ItemStack[] invContent) {
		this.invContent = invContent;
	}

	public int getXPLevel() {
		return XPLevel;
	}

	public void setXPLevel(int xPLevel) {
		XPLevel = xPLevel;
	}

	public float getXPFloat() {
		return XPFloat;
	}

	public void setXPFloat(float xPFloat) {
		XPFloat = xPFloat;
	}

	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}
}
