package de.ng.skypvp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;

import de.ng.skypvp.Locations;

public class ItemListener implements Listener {
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onItemPickup(EntityPickupItemEvent event) {
		event.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onArrowPickup(PlayerPickupArrowEvent event) {
		if(event.getPlayer().getLocation().distance(Locations.WORLD_SPAWN) > 16)
			event.setCancelled(false);
		else
			event.setCancelled(true);
	}
}