package de.ng.skypvp.kit;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.ng.skypvp.kit.kits.EnderManKit;
import de.ng.skypvp.kit.kits.SurvivalKit;

public class KitManager {
	
	private static final HashMap<UUID, Kit> kits = new HashMap<>();
	
	public static Kit getCurrentlyKit(Player player) {
		if(kits.containsKey(player.getUniqueId()))
			return kits.get(player.getUniqueId());
		return null;
	}
	
	public static boolean hasKit(Player player) {
		return kits.containsKey(player.getUniqueId());
	}
	
	public static Kit changeKit(Player player, KitType type) {
		if(kits.containsKey(player.getUniqueId()))
			kits.remove(player.getUniqueId()).destroy();
		Kit kit = null;
		
		if(type != null) {
			kit = getNewKitInstance(player, type);
			kits.put(player.getUniqueId(), kit);
		}
		return kit;
	}
	
	private static Kit getNewKitInstance(Player player, KitType type) {
		switch (type) {
		case EnderMan:
			return new EnderManKit(type, player);

		default:
			return new SurvivalKit(type, player);
		}
	}
}