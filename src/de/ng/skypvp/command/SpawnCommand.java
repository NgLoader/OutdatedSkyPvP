package de.ng.skypvp.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.ng.skypvp.Locations;
import de.ng.skypvp.SkyPvP;
import de.ng.skypvp.antilogout.KombatLogManager;
import de.ng.skypvp.region.PvPManager;

public class SpawnCommand implements CommandExecutor {
	
	public static final HashMap<Player, BukkitTask> COULDOWN = new HashMap<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		
		Player player = (Player) sender;
		
		if(!COULDOWN.containsKey(player)) {
			if(!KombatLogManager.isContains(player))
				if(PvPManager.isOutside(player)) {
					player.sendMessage(SkyPvP.PREFIX + "§7Du wirst in §a10 §7Sekunden zum §aSpawn §7teleportiert§8.");
					COULDOWN.put(player, Bukkit.getScheduler().runTaskLaterAsynchronously(SkyPvP.instance, new Runnable() {
						
						@Override
						public void run() {
							player.teleport(Locations.WORLD_SPAWN);
							player.sendMessage(SkyPvP.PREFIX + "§7Du wurdest nun zum §aSpawn §7teleportiert§8.");
							COULDOWN.remove(player);
						}
					}, 200));
				} else
					player.sendMessage(SkyPvP.PREFIX + "§7Du bist bereits am §aSpawn§8.");
			 else
				player.sendMessage(SkyPvP.PREFIX + "§7Du darfst dich nicht zum Spawn teleportieren wenn du noch in §ckombatlog §7bist§8.");
		} else
			player.sendMessage(SkyPvP.PREFIX + "§7Bitte warte bis du diese §cCommand §7wieder nutzen kannst§8.");
		return true;
	}
}