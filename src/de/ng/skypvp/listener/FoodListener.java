package de.ng.skypvp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.ng.skypvp.region.PvPManager;

public class FoodListener implements Listener {
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		
		if(PvPManager.isOutside(player))
			return;
		event.setCancelled(true);
	}
	
}