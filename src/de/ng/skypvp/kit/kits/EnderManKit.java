package de.ng.skypvp.kit.kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

import de.ng.skypvp.Locations;
import de.ng.skypvp.SkyPvP;
import de.ng.skypvp.antilogout.KombatLogManager;
import de.ng.skypvp.event.UpdateEvent;
import de.ng.skypvp.kit.Kit;
import de.ng.skypvp.kit.KitType;
import de.ng.skypvp.updater.UpdateType;
import de.ng.skypvp.util.ItemFactory;

public class EnderManKit extends Kit {
	
	private static final ItemStack ITEM_WOOD_SWORD = new ItemFactory(Material.WOOD_SWORD).setDisplayName("§aSchwert").addAllFlag().build();
	private static final ItemStack ITEM_ENDER_PEARL = new ItemFactory(Material.ENDER_PEARL).setDisplayName("§aEnderperle").addAllFlag().build();
	private static final ItemStack ITEM_COOKED_PORKCHOP = new ItemFactory(Material.COOKED_BEEF, 32).setDisplayName("§aEssen").addAllFlag().build();
	private static final ItemStack ITEM_GOLD_HELMET = new ItemFactory(Material.GOLD_HELMET).setDisplayName("§aHelm").addAllFlag().build();
	private static final ItemStack ITEM_LEATHER_CHESTPLATE = new ItemFactory(Material.LEATHER_CHESTPLATE).setColor(Color.OLIVE).setDisplayName("§aBrustplatte").addAllFlag().build();
	private static final ItemStack ITEM_LEATHER_LEGGINGS = new ItemFactory(Material.LEATHER_LEGGINGS).setColor(Color.OLIVE).setDisplayName("§aHose").addAllFlag().build();
	private static final ItemStack ITEM_DIAMOND_BOOTS = new ItemFactory(Material.GOLD_BOOTS).setDisplayName("§aSchuhe").addAllFlag().build();
	
	private Long couldown = 0L;
	
	public EnderManKit(KitType type, Player player) {
		super(type, player, true);
	}
	
	@Override
	public void equip() {
		player.getInventory().setItem(0, ITEM_WOOD_SWORD);
		player.getInventory().setItem(1, ITEM_ENDER_PEARL);
		player.getInventory().setItem(8, ITEM_COOKED_PORKCHOP);
		
		player.getInventory().setHelmet(ITEM_GOLD_HELMET);
		player.getInventory().setChestplate(ITEM_LEATHER_CHESTPLATE);
		player.getInventory().setLeggings(ITEM_LEATHER_LEGGINGS);
		player.getInventory().setBoots(ITEM_DIAMOND_BOOTS);
	}

	@Override
	public void unequip() {
		couldown = 0L;
	}
	
	@Override
	public void onDestroy() { }
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onBlockPlace(PlayerInteractEvent event) {
		if(!event.getPlayer().equals(player))
			return;
		
		if(event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL)
			couldown = System.currentTimeMillis() + 30000;
	}
	
	
	@EventHandler
	public void onEnderpearlTeleport(PlayerTeleportEvent event) {
		if(event.getCause() == TeleportCause.ENDER_PEARL) {
			if(event.getTo().distance(Locations.WORLD_SPAWN) < SkyPvP.SPAWNRANGE && KombatLogManager.isContains(player)) {
				event.setCancelled(true);
				player.sendMessage(SkyPvP.PREFIX + "§7Du darfst die nicht in der §cKombatLogPhase §7zum spawn Teleportieren§8.");
				player.getInventory().addItem(ITEM_ENDER_PEARL);
			}
		}
	}
	
	@EventHandler
	public void onUpdate(UpdateEvent event) {
		if(event.getType() != UpdateType.FAST || couldown == 0)
			return;
		
		if(couldown < System.currentTimeMillis()) {
			couldown = 0L;
			player.getInventory().addItem(ITEM_ENDER_PEARL);
		}
	}
}