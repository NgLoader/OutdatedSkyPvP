package de.ng.skypvp.kit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.ng.skypvp.SkyPvP;

public abstract class Kit implements Listener {
	
	protected KitType type;
	protected Player player;
	
	public Kit(KitType type, Player player, boolean registerListener) {
		this.type = type;
		this.player = player;
		
		if(registerListener)
			Bukkit.getServer().getPluginManager().registerEvents(this, SkyPvP.instance);
	}
	
	public void destroy() {
		onDestroy();
		
		this.player = null;
		this.type = null;
	}
	
	public abstract void equip();
	public abstract void unequip();
	public abstract void onDestroy();
	
	public Player getPlayer() {
		return player;
	}
	
	public KitType getType() {
		return type;
	}
}