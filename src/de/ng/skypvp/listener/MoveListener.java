package de.ng.skypvp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.ng.skypvp.Locations;
import de.ng.skypvp.SkyPvP;
import de.ng.skypvp.command.SpawnCommand;
import de.ng.skypvp.region.PvPManager;

public class MoveListener implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if(player.getLocation().distance(Locations.WORLD_SPAWN) > SkyPvP.SPAWNRANGE && !PvPManager.isOutside(player))
			PvPManager.isNowOutSide(player);
		else if(player.getLocation().distance(Locations.WORLD_SPAWN) < SkyPvP.SPAWNRANGE - 2 && PvPManager.isOutside(player))
			PvPManager.isNowInSide(player);
		
		if(event.getFrom().getX() != event.getTo().getX()
				|| event.getFrom().getZ() != event.getTo().getY()
				|| event.getFrom().getY() != event.getTo().getZ()) {
			if(SpawnCommand.COULDOWN.containsKey(player)) {
				SpawnCommand.COULDOWN.remove(player).cancel();
				player.sendMessage(SkyPvP.PREFIX + "§7Der §aTeleport §7wurde §cabgebrocken§8.");
			}
		}
	}
}