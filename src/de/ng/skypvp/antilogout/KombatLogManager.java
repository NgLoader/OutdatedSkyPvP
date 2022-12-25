package de.ng.skypvp.antilogout;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.ng.skypvp.SkyPvP;
import de.ng.skypvp.command.SpawnCommand;

public class KombatLogManager {
	
	private static final HashMap<Player, BukkitTask> ANTILOGOUTMAP = new HashMap<>();
	
	public static boolean isContains(Player player) {
		return ANTILOGOUTMAP.containsKey(player);
	}
	
	public static void add(Player player) {
		if(ANTILOGOUTMAP.containsKey(player))
			ANTILOGOUTMAP.get(player).cancel();
		else
			player.sendMessage(SkyPvP.PREFIX + "§7Du darfst dich für §810 §7Sekunden nicht §causloggen§8.");
		
		if(SpawnCommand.COULDOWN.containsKey(player))
			SpawnCommand.COULDOWN.remove(player).cancel();
		
		ANTILOGOUTMAP.put(player, Bukkit.getScheduler().runTaskLaterAsynchronously(SkyPvP.instance, new Runnable() {
			
			@Override
			public void run() {
				ANTILOGOUTMAP.remove(player);
				player.sendMessage(SkyPvP.PREFIX + "§7Du darfst du nun wieder §aausloggen§8.");
			}
		}, 200));
	}
	
	public static void remove(Player player, boolean message) {
		if(ANTILOGOUTMAP.containsKey(player)) {
			ANTILOGOUTMAP.remove(player).cancel();
			
			if(message && player.isOnline())
				player.sendMessage(SkyPvP.PREFIX + "§7Du darfst du nun wieder §aausloggen§8.");
		}
	}
}