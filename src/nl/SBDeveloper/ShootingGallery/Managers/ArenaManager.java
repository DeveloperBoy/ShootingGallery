package nl.SBDeveloper.ShootingGallery.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import nl.SBDeveloper.ShootingGallery.API.Arena;
import nl.SBDeveloper.ShootingGallery.API.ArenaPlayer;
import nl.SBDeveloper.ShootingGallery.API.ArenaTask;
import nl.SBDeveloper.ShootingGallery.Utils.Cuboid;
import nl.SBDeveloper.ShootingGallery.Utils.XMaterial;

public class ArenaManager {
	private static ArenaManager instance = new ArenaManager();
	private List<Arena> arenas;
	private Plugin plugin;

	public static ArenaManager getInstance() {
		return instance;
	}

	public void onEnable(Plugin plugin) {
		this.plugin = plugin;
		this.arenas = new ArrayList<Arena>();
		this.arenas.clear();
		ConfigurationSection conf = FileManager.getInstance().getArenas().getConfigurationSection("Arenas");
		if (conf != null) {
			Set<String> ids = conf.getKeys(false);
			for (String id : ids) {
				boolean enabled = conf.getBoolean(id + ".Enabled");
				int neededplayers = conf.getInt(id + ".NeededPlayers");
				int maxplayers = conf.getInt(id + ".MaxPlayers");
				World world = Bukkit.getWorld(UUID.fromString(conf.getString(id + ".World")));
				Location loc1 = new Location(world, conf.getDouble(id + ".Location1.X"),
						conf.getDouble(id + ".Location1.Y"), conf.getDouble(id + ".Location1.Z"));
				Location loc2 = new Location(world, conf.getDouble(id + ".Location2.X"),
						conf.getDouble(id + ".Location2.Y"), conf.getDouble(id + ".Location2.Z"));
				Cuboid selection = new Cuboid(loc1, loc2);
				ConfigurationSection spawnConf = conf.getConfigurationSection(id + ".Spawn");
				if (spawnConf != null) {
					Location spawn = new Location(world, spawnConf.getDouble("X"), spawnConf.getDouble("Y"),
							spawnConf.getDouble("Z"), (float) spawnConf.getDouble("Yaw"),
							(float) spawnConf.getDouble("Pitch"));
					addArenaFromConfig(id, neededplayers, maxplayers, selection, spawn, enabled);
				} else {
					addArena(id, neededplayers, maxplayers, selection, enabled);
				}
			}
		}
	}

	public void reloadArenasToConfig() {
		FileManager.getInstance().getArenas().set("Arenas", null);
		FileManager.getInstance().saveArenas();
		if (!this.arenas.isEmpty()) {
			ConfigurationSection conf = FileManager.getInstance().getArenas().createSection("Arenas");
			for (Arena a : this.arenas) {
				conf.set(a.getId() + ".Enabled", Boolean.valueOf(a.isEnabled()));
				conf.set(a.getId() + ".NeededPlayers", Integer.valueOf(a.getNeededplayers()));
				conf.set(a.getId() + ".MaxPlayers", Integer.valueOf(a.getMaxplayers()));
				conf.set(a.getId() + ".World", a.getSelection().getWorld().getUID().toString());
				conf.set(a.getId() + ".Location1.X", Integer.valueOf(a.getSelection().getLowerLocation().getBlockX()));
				conf.set(a.getId() + ".Location1.Y", Integer.valueOf(a.getSelection().getLowerLocation().getBlockY()));
				conf.set(a.getId() + ".Location1.Z", Integer.valueOf(a.getSelection().getLowerLocation().getBlockZ()));
				conf.set(a.getId() + ".Location2.X", Integer.valueOf(a.getSelection().getUpperLocation().getBlockX()));
				conf.set(a.getId() + ".Location2.Y", Integer.valueOf(a.getSelection().getUpperLocation().getBlockY()));
				conf.set(a.getId() + ".Location2.Z", Integer.valueOf(a.getSelection().getUpperLocation().getBlockZ()));
				if (a.getSpawn() != null) {
					conf.set(a.getId() + ".Spawn.X", Double.valueOf(a.getSpawn().getBlockX() + 0.5D));
					conf.set(a.getId() + ".Spawn.Y", Integer.valueOf(a.getSpawn().getBlockY()));
					conf.set(a.getId() + ".Spawn.Z", Double.valueOf(a.getSpawn().getBlockZ() + 0.5D));
					conf.set(a.getId() + ".Spawn.Yaw", Float.valueOf(a.getSpawn().getYaw()));
					conf.set(a.getId() + ".Spawn.Pitch", Float.valueOf(a.getSpawn().getPitch()));
				}
			}
			FileManager.getInstance().saveArenas();
		}
	}

	public void addArenaFromConfig(String id, int neededplayers, int maxplayers, Cuboid selection, Location spawn, boolean enabled) {
		Arena a = new Arena(id, neededplayers, maxplayers, selection, spawn, enabled);
		this.arenas.add(a);
	}

	public void addArena(String id, int neededplayers, int maxplayers, Cuboid selection, boolean enabled) {
		Arena a = new Arena(id, neededplayers, maxplayers, selection, enabled);
		this.arenas.add(a);
		ConfigurationSection conf = FileManager.getInstance().getArenas()
				.getConfigurationSection("Arenas." + a.getId());
		if (conf == null) {
			conf = FileManager.getInstance().getArenas().createSection("Arenas." + a.getId());
		}
		conf.set("Enabled", Boolean.valueOf(a.isEnabled()));
		conf.set("NeededPlayers", Integer.valueOf(a.getNeededplayers()));
		conf.set("MaxPlayers", Integer.valueOf(a.getMaxplayers()));
		conf.set("World", a.getSelection().getWorld().getUID().toString());
		conf.set("Location1.X", Integer.valueOf(a.getSelection().getLowerLocation().getBlockX()));
		conf.set("Location1.Y", Integer.valueOf(a.getSelection().getLowerLocation().getBlockY()));
		conf.set("Location1.Z", Integer.valueOf(a.getSelection().getLowerLocation().getBlockZ()));
		conf.set("Location2.X", Integer.valueOf(a.getSelection().getUpperLocation().getBlockX()));
		conf.set("Location2.Y", Integer.valueOf(a.getSelection().getUpperLocation().getBlockY()));
		conf.set("Location2.Z", Integer.valueOf(a.getSelection().getUpperLocation().getBlockZ()));
		FileManager.getInstance().saveArenas();
	}

	public void addSpawn(Arena a, Location spawn) {
		a.setSpawn(spawn);
		ConfigurationSection spawnConf = FileManager.getInstance().getArenas()
				.getConfigurationSection("Arenas." + a.getId() + ".Spawn");
		if (spawnConf == null) {
			spawnConf = FileManager.getInstance().getArenas().createSection("Arenas." + a.getId() + ".Spawn");
		}
		spawnConf.set("X", Double.valueOf(spawn.getBlockX() + 0.5D));
		spawnConf.set("Y", Integer.valueOf(spawn.getBlockY()));
		spawnConf.set("Z", Double.valueOf(spawn.getBlockZ() + 0.5D));
		spawnConf.set("Yaw", Float.valueOf(spawn.getYaw()));
		spawnConf.set("Pitch", Float.valueOf(spawn.getPitch()));
		FileManager.getInstance().saveArenas();
	}

	public void changeEnabled(Arena a, boolean enabled) {
		a.setEnabled(enabled);
		FileManager.getInstance().getArenas().set("Arenas." + a.getId() + ".Enabled", Boolean.valueOf(enabled));
		FileManager.getInstance().saveArenas();
	}
	
	public void startArena(Arena a) {
		int roundTime = FileManager.getInstance().getConfig().getInt("RoundTimeInSeconds");
		ArenaTask aTask = new ArenaTask(roundTime + 30, a);
		aTask.runTaskTimer(this.plugin, 0L, 20L);
	}

	public boolean joinArena(Player p, Arena a) {
		if (a.getMaxplayers() == a.getPlayers().size()) {
			//It's FULL
			return false;
		}
		p.teleport(new Location(a.getSpawn().getWorld(), a.getSpawn().getBlockX() + 0.5D, a.getSpawn().getBlockY(),
				a.getSpawn().getBlockZ() + 0.5D));
		p.setExp(0.0F);
		p.setLevel(0);
		p.getInventory().clear();
		a.joinArena(p);
		if (a.getNeededplayers() == a.getPlayers().size()) {
			//The arena can start
			this.startArena(a);
		}
		return true;
	}

	public void forceLeaveArena(Arena a) {
		ArrayList<ArenaPlayer> aplayers = a.getPlayers();
		for (int x = ((Integer) a.getTask().getCoords().get("minX"))
				.intValue(); x <= ((Integer) a.getTask().getCoords().get("maxX")).intValue(); x++) {
			for (int y = ((Integer) a.getTask().getCoords().get("minY"))
					.intValue(); y <= ((Integer) a.getTask().getCoords().get("maxY")).intValue(); y++) {
				for (int z = ((Integer) a.getTask().getCoords().get("minZ"))
						.intValue(); z <= ((Integer) a.getTask().getCoords().get("maxZ")).intValue(); z++) {
					if (a.getSelection().getWorld().getBlockAt(x, y, z).getType() == XMaterial.RED_WOOL.parseMaterial() || a.getSelection().getWorld().getBlockAt(x, y, z).getType() == XMaterial.GREEN_WOOL.parseMaterial()) {
						a.getSelection().getWorld().getBlockAt(x, y, z).setType(Material.AIR);
					}
				}
			}
		}
		for (ArenaPlayer ap : aplayers) {
			ap.getPlayer().teleport(ap.getPLocation());
			ap.getPlayer().setLevel(ap.getXPLevel());
			ap.getPlayer().setExp(ap.getXPFloat());
			ap.getPlayer().getInventory().clear();
			for (int i = 0; i < ap.getInvContent().length; i++) {
				if (ap.getInvContent()[i] != null) {
					ap.getPlayer().getInventory().setItem(i, ap.getInvContent()[i]);
				}
			}
		}
		a.forceStop();
	}

	public Arena getArena(String id) {
		for (Arena a : this.arenas) {
			if (a.getId().equalsIgnoreCase(id)) {
				return a;
			}
		}
		return null;
	}

	public Arena getArena(Player p) {
		for (Arena a : this.arenas) {
			if ((a.getPlayers() != null) && (a.containsPlayer(p))) {
				return a;
			}
		}
		return null;
	}

	public void delArena(Arena a) {
		this.arenas.remove(a);
		reloadArenasToConfig();
	}
}
