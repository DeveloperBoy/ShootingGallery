package nl.SBDeveloper.ShootingGallery.API;

import org.bukkit.Location;

import nl.SBDeveloper.ShootingGallery.Utils.XMaterial;

public class SGBlock {
	private XMaterial mat;
	private Location loc;

	public SGBlock(XMaterial mat, Location loc) {
		this.mat = mat;
		this.loc = loc;
	}

	public XMaterial getXMaterial() {
		return this.mat;
	}

	public Location getLocation() {
		return this.loc;
	}
}
