package de.ng.skypvp.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import de.ng.skypvp.kit.KitType;
import de.ng.skypvp.util.ItemFactory;

public class KitChooseInventory {
	
	public static Inventory INVENTORY;
	
	public static void init() {
		KitType[] kits = KitType.values();
		
		int slot = 0;
		int size = 1;
		
		for(int i = 0; i < kits.length; i++)
			if(slot == 9) {
				size++;
				slot = 0;
			} else
				slot++;
		
		INVENTORY = Bukkit.createInventory(null, 9 * size, "§aWähle dein §2Kit §aaus");
		
		for(KitType kit : kits)
			INVENTORY.setItem(kit.getId(), new ItemFactory(kit.getDisplayMaterial(), kit.getDisplayName(), kit.getDisplayMaterialByte()).setLore(kit.getLore()).addAllFlag().build());
	}
}