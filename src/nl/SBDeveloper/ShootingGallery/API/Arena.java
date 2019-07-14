package nl.SBDeveloper.ShootingGallery.API;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import nl.SBDeveloper.ShootingGallery.Utils.Cuboid;

public class Arena {
	private String id;
	private Cuboid selection = null;
	private Location spawn = null;
	private boolean enabled;
	private boolean isInUse = false;
	private ArrayList<ArenaPlayer> players;
	private ArenaTask task;
	private Integer neededplayers;
	private Integer maxplayers;

	public Arena(String id, int neededplayers, int maxplayers, Cuboid selection, boolean enabled) {
		this.setId(id);
		this.setNeededplayers(neededplayers);
		this.setMaxplayers(maxplayers);
		this.setSelection(selection);
		this.setEnabled(enabled);
	}

	public Arena(String id, int neededplayers, int maxplayers, Cuboid selection, Location spawn, boolean enabled) {
		this.setId(id);
		this.setNeededplayers(neededplayers);
		this.setMaxplayers(maxplayers);
		this.setSelection(selection);
		this.setSpawn(spawn);
		this.setEnabled(enabled);
	}
	
	public void joinArena(Player p) {
		ArenaPlayer ap = new ArenaPlayer();
		ap.setPlayer(p);
		ap.setInvContent(p.getInventory().getContents());
		ap.setPLocation(p.getLocation());
		ap.setScores(0);
		ap.setXPFloat(0.0F);
		ap.setXPLevel(0);
		this.players.add(ap);
	}
	
	public void startArena(ArenaTask task) {
		this.isInUse = true;
		this.task = task;
	}
	
	public void stopArena() {
		this.players = null;
		this.isInUse = false;
		this.task = null;
	}

	public void forceStop() {
		this.players = null;
		this.isInUse = false;
		try {
			Bukkit.getScheduler().cancelTask(this.task.getTaskId());
		} catch (NullPointerException e) {
		}
		this.task = null;
	}
	
	public boolean containsPlayer(Player p) {
		for (ArenaPlayer ap : this.players) {
			if (ap.getPlayer().equals(p)) {
				return true;
			}
		}
		return false;
	}
	
	public ArenaPlayer getAPfromPlayer(Player p) {
		for (ArenaPlayer ap : this.players) {
			if (ap.getPlayer().equals(p)) {
				return ap;
			}
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Cuboid getSelection() {
		return selection;
	}

	public void setSelection(Cuboid selection) {
		this.selection = selection;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isInUse() {
		return isInUse;
	}

	public void setInUse(boolean isInUse) {
		this.isInUse = isInUse;
	}

	public ArrayList<ArenaPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<ArenaPlayer> player) {
		this.players = player;
	}

	public ArenaTask getTask() {
		return task;
	}

	public void setTask(ArenaTask task) {
		this.task = task;
	}

	public Integer getMaxplayers() {
		return maxplayers;
	}

	public void setMaxplayers(Integer maxplayers) {
		this.maxplayers = maxplayers;
	}

	public Integer getNeededplayers() {
		return neededplayers;
	}

	public void setNeededplayers(Integer neededplayers) {
		this.neededplayers = neededplayers;
	}

	
}
