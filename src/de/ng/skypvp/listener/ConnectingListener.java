package de.ng.skypvp.listener;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.ng.skypvp.Locations;
import de.ng.skypvp.SkyPvP;
import de.ng.skypvp.antilogout.KombatLogManager;
import de.ng.skypvp.database.StorageCache;
import de.ng.skypvp.kit.Kit;
import de.ng.skypvp.kit.KitManager;
import de.ng.skypvp.kit.KitType;
import de.ng.skypvp.region.PvPManager;

public class ConnectingListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.teleport(Locations.WORLD_SPAWN);
		PvPManager.isNowInSide(player);
		
		event.setJoinMessage("§8[§2+§8] §a" + player.getName());
		
		Document document = StorageCache.getDocument("skypvp", player.getUniqueId());
		
		int kitId = document.containsKey("kitid") ? document.getInteger("kitid") : 0;
		
		KitManager.changeKit(player, KitType.findById(kitId));
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		player.getInventory().clear();
		player.getActivePotionEffects().clear();
		
		event.setQuitMessage("§8[§4-§8] §c" + player.getName());
		
		if(KombatLogManager.isContains(player)) {
			KombatLogManager.remove(player, false);
			
			if(DamageListener.lastHit.containsKey(player)) {
				if(DamageListener.lastHit.get(player).isOnline()) {
					Player killer = DamageListener.lastHit.remove(player);
					
					Bukkit.broadcastMessage(SkyPvP.PREFIX + "§c" + player.getName() + " §7wurde von §c" + killer.getName() + " §4getötet§8.");
					
					Document playerDocument = StorageCache.getDocument("skypvp", player.getUniqueId());
					StorageCache.setDocumantValue("skypvp", player.getUniqueId(), "deaths", playerDocument.containsKey("deaths") ? playerDocument.getInteger("deaths") + 1 : 1);
					
					Document killerDocument = StorageCache.getDocument("skypvp", killer.getUniqueId());
					StorageCache.setDocumantValue("skypvp", killer.getUniqueId(), "kills", killerDocument.containsKey("kills") ? killerDocument.getInteger("kills") + 1 : 1);
				} else
					DamageListener.lastHit.remove(player);
			}
		}
		
		if(DamageListener.lastHit.containsKey(player))
			DamageListener.lastHit.remove(player);
		
		Kit kit = KitManager.getCurrentlyKit(player);
		
		if(kit != null) {
			StorageCache.setDocumantValue("skypvp", player.getUniqueId(), "kitid", kit.getType().getId());
			KitManager.changeKit(player, null);
		}
		StorageCache.removeFromCache("skypvp", player.getUniqueId());
	}
}