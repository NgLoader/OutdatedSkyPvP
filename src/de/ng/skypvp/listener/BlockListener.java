package de.ng.skypvp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.ng.skypvp.Locations;
import de.ng.skypvp.SkyPvP;

public class BlockListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onBlockBreak(BlockBreakEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.getBlock().getLocation().distance(Locations.WORLD_SPAWN) < SkyPvP.SPAWNRANGE)
			event.setCancelled(true);
	}
}