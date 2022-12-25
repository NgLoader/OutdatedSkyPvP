package de.ng.skypvp.kit.kits;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import de.ng.skypvp.event.UpdateEvent;
import de.ng.skypvp.kit.Kit;
import de.ng.skypvp.kit.KitType;
import de.ng.skypvp.updater.UpdateType;
import de.ng.skypvp.util.ItemFactory;

public class SurvivalKit extends Kit {
	
	private static final ItemStack ITEM_DIAMOND_SWORD = new ItemFactory(Material.STONE_SWORD).setDisplayName("§aSchwert").addAllFlag().build();
	private static final ItemStack ITEM_SHILD = new ItemFactory(Material.SHIELD).setDisplayName("§aSchild").setDamage((short) 330).addAllFlag().build();
	private static final ItemStack ITEM_SANDSTONE = new ItemFactory(Material.SANDSTONE, 16).setDisplayName("§aSandstein").addAllFlag().build();
	private static final ItemStack ITEM_SANDSTONE_ONE = new ItemFactory(Material.SANDSTONE).setDisplayName("§aSandstein").addAllFlag().build();
	private static final ItemStack ITEM_COOKED_PORKCHOP = new ItemFactory(Material.COOKED_BEEF, 32).setDisplayName("§aEssen").addAllFlag().build();
	private static final ItemStack ITEM_DIAMOND_HELMET = new ItemFactory(Material.DIAMOND_HELMET).setDisplayName("§aHelm").addAllFlag().build();
	private static final ItemStack ITEM_IRON_CHESTPLATE = new ItemFactory(Material.IRON_CHESTPLATE).setDisplayName("§aBrustplatte").addAllFlag().build();
	private static final ItemStack ITEM_DIAMOND_LEGGINGS = new ItemFactory(Material.DIAMOND_LEGGINGS).setDisplayName("§aHose").addAllFlag().build();
	private static final ItemStack ITEM_GOLD_BOOTS = new ItemFactory(Material.GOLD_BOOTS).setDisplayName("§aSchuhe").addAllFlag().build();
	
	private static final ConcurrentHashMap<Location, Long> PLACED_BLOCK = new ConcurrentHashMap<>();
	
	private Long couldown = 0L;
	private int count = 16;
	
	public SurvivalKit(KitType type, Player player) {
		super(type, player, true);
	}
	
	@Override
	public void equip() {
		player.getInventory().setItem(0, ITEM_DIAMOND_SWORD);
		player.getInventory().setItem(1, ITEM_SANDSTONE);
		player.getInventory().setItem(8, ITEM_COOKED_PORKCHOP);
		player.getInventory().setItemInOffHand(ITEM_SHILD);
		
		player.getInventory().setHelmet(ITEM_DIAMOND_HELMET);
		player.getInventory().setChestplate(ITEM_IRON_CHESTPLATE);
		player.getInventory().setLeggings(ITEM_DIAMOND_LEGGINGS);
		player.getInventory().setBoots(ITEM_GOLD_BOOTS);
	}

	@Override
	public void unequip() {
		couldown = 0L;
		count = 16;
	}
	
	@Override
	public void onDestroy() { }
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(!event.getPlayer().equals(player))
			return;
		
		if(event.getBlock().getType() == Material.SANDSTONE && !PLACED_BLOCK.containsKey(event.getBlock().getLocation())) {
			PLACED_BLOCK.put(event.getBlock().getLocation(), System.currentTimeMillis() + 8000);
			event.getBlock().getWorld().spawnParticle(Particle.CLOUD, event.getBlock().getLocation().add(0.5, 0.5, 0.5), 0, 0, 1, 0);
			
			if(event.getItemInHand().getAmount() == 1)
				couldown = System.currentTimeMillis() + 10000L;
		}
	}
	
	@EventHandler
	public void onUpdate(UpdateEvent event) {
		if(event.getType() != UpdateType.TICK)
			return;
		
		if(couldown != 0 && count != 0 && couldown < System.currentTimeMillis()) {
			count--;
			
			player.getInventory().addItem(ITEM_SANDSTONE_ONE);
			
			if(count == 0) {
				couldown = 0L;
				count = 16;
			}
		}
		
		for(Entry<Location, Long> entry : PLACED_BLOCK.entrySet())
			if(entry.getValue() < System.currentTimeMillis()) {
				if(entry.getKey().getBlock().getType() == Material.SANDSTONE) {
					entry.getKey().getBlock().setType(Material.RED_SANDSTONE);
					entry.setValue(System.currentTimeMillis() + 1000);
				} else {
					entry.getKey().getBlock().getLocation().getBlock().setType(Material.AIR);
					PLACED_BLOCK.remove(entry.getKey());
				}
		}
	}
}