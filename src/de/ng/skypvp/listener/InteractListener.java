package de.ng.skypvp.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import de.ng.skypvp.Locations;
import de.ng.skypvp.SkyPvP;
import de.ng.skypvp.inventory.KitChooseInventory;

public class InteractListener implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getHand() != EquipmentSlot.HAND)
			return;
		
		if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getType() == Material.ENDER_CHEST && event.getPlayer().getLocation().distance(Locations.WORLD_SPAWN) < SkyPvP.SPAWNRANGE) {
			event.setCancelled(true);
			event.getPlayer().openInventory(KitChooseInventory.INVENTORY);
		}
	}
}