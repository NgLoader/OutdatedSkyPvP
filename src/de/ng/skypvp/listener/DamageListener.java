package de.ng.skypvp.listener;

import java.util.HashMap;

import org.bson.Document;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.ng.skypvp.SkyPvP;
import de.ng.skypvp.antilogout.KombatLogManager;
import de.ng.skypvp.database.StorageCache;
import de.ng.skypvp.region.PvPManager;

public class DamageListener implements Listener {
	
	public static final HashMap<Player, Player> lastHit = new HashMap<>();
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player))
			if(!(event.getEntity() instanceof FallingBlock)) {
				event.setCancelled(true);
				return;
			}
		Player player = (Player) event.getEntity();
		
		if(event.getCause() == DamageCause.FALL)
			event.setCancelled(true);
		else if(event.getCause() == DamageCause.VOID) {
			player.setHealth(0);
			return;
		}
		
		if(!PvPManager.isOutside(player))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player) || (event.getCause() != DamageCause.PROJECTILE && event.getCause() != DamageCause.ENTITY_ATTACK && event.getCause() != DamageCause.ENTITY_SWEEP_ATTACK))
				return;
		Player player = (Player) event.getEntity();
		
		if(event.getDamager() instanceof Projectile && !(event.getDamager() instanceof EnderPearl)) {
			Projectile projectile = (Projectile) event.getDamager();
			
			if(projectile.getShooter() instanceof Player) {
				Player shooter = (Player) projectile.getShooter();
				
				if(PvPManager.isOutside(player) && PvPManager.isOutside(shooter)) {
					KombatLogManager.add(player);
					KombatLogManager.add(shooter);
					
					lastHit.put(player, shooter);
					return;
				}
				event.setCancelled(true);
				return;
			}
		} else if(event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			
			if(PvPManager.isOutside(player) && PvPManager.isOutside(damager)) {
				KombatLogManager.add(player);
				KombatLogManager.add(damager);
				
				lastHit.put(player, damager);
				return;
			}
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onPlayerDead(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Player killer = player.getKiller();
		
		if(killer == null && lastHit.containsKey(player)) {
			if(lastHit.get(player).isOnline())
				killer = lastHit.remove(player);
			else
				lastHit.remove(player);
		}
		
		if(player.isOnline())
			player.spigot().respawn();
		
		if(killer == null)
			event.setDeathMessage(SkyPvP.PREFIX + "§c" + player.getName() + " §7ist §4gestorben§8.");
		else
			event.setDeathMessage(SkyPvP.PREFIX + "§c" + player.getName() + " §7wurde von §c" + killer.getName() + " §4getötet§8.");
		
		if(KombatLogManager.isContains(player))
			KombatLogManager.remove(player, false);
		
		Document playerDocument = StorageCache.getDocument("skypvp", player.getUniqueId());
		StorageCache.setDocumantValue("skypvp", player.getUniqueId(), "deaths", playerDocument.containsKey("deaths") ? playerDocument.getInteger("deaths") + 1 : 1);
		
		if(killer == null)
			return;
		
		Document killerDocument = StorageCache.getDocument("skypvp", killer.getUniqueId());
		StorageCache.setDocumantValue("skypvp", killer.getUniqueId(), "kills", killerDocument.containsKey("kills") ? killerDocument.getInteger("kills") + 1 : 1);
	}
}