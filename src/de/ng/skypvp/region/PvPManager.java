package de.ng.skypvp.region;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.ng.skypvp.kit.Kit;
import de.ng.skypvp.kit.KitManager;
import de.ng.skypvp.kit.KitType;
import de.ng.skypvp.util.ItemFactory;

public class PvPManager {
	
	private static final ItemStack KIT_CHOOSER = new ItemFactory(Material.ENDER_CHEST).setDisplayName("§aWähle dein §2Kit §aaus§a.").addAllFlag().build();
	
	private static List<Player> outside = new ArrayList<>();
	
	public static boolean isOutside(Player player) {
		return outside.contains(player);
	}
	
	public static void isNowInSide(Player player) {
		outside.remove(player);
		
		Kit kit = KitManager.getCurrentlyKit(player);
		if(kit != null)
			kit.unequip();
		
		player.setFoodLevel(20);
		player.setHealth(20);
		player.getInventory().clear();
		player.getActivePotionEffects().clear();
		player.getInventory().setItem(4, KIT_CHOOSER);
	}
	
	public static void isNowOutSide(Player player) {
		if(!outside.contains(player))
			outside.add(player);
		
		player.getInventory().clear();
		player.getActivePotionEffects().clear();
		
		Kit kit = KitManager.getCurrentlyKit(player);
		if(kit == null)
			kit = KitManager.changeKit(player, KitType.Survival);
		kit.equip();
	}
}