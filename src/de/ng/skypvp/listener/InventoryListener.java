package de.ng.skypvp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import de.ng.skypvp.Locations;
import de.ng.skypvp.SkyPvP;
import de.ng.skypvp.kit.Kit;
import de.ng.skypvp.kit.KitManager;
import de.ng.skypvp.kit.KitType;
import de.ng.skypvp.region.PvPManager;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(!(event.getWhoClicked() instanceof Player))
			return;
		Player player = (Player)event.getWhoClicked();
		
		if(player.getLocation().distance(Locations.WORLD_SPAWN) > SkyPvP.SPAWNRANGE)
			return;
			
		event.setCancelled(true);
		if(event.getSlotType() != SlotType.CONTAINER || !PvPManager.isOutside(player) && event.getClickedInventory().getTitle().equalsIgnoreCase("§aWähle dein §2Kit §aaus")) {
			player.closeInventory();
			
			int slot = event.getSlot();
			
			Kit currentlyKit = KitManager.getCurrentlyKit(player);
			
			if(currentlyKit != null)
				if(slot != currentlyKit.getType().getId()) {
					if(slot >= KitType.values().length)
						return;
					
					KitType type = KitType.findById(slot);
					KitManager.changeKit(player, type);
					
					player.sendMessage(SkyPvP.PREFIX + "§7Du hast das Kit §8\"" + type.getDisplayName() + "§8\" §7ausgewählt§8.");
				} else
					player.sendMessage(SkyPvP.PREFIX + "§7Dieses §cKit §7benutzt du grade§8.");
			else
				player.sendMessage(SkyPvP.PREFIX + "§7Es ist ein §cFehler §7aufgetreten versuch §cneuzuverbinden §7sonst melde dich bitte in Support§8.");
		}
	}
}