package de.ng.skypvp;

import org.bukkit.plugin.java.JavaPlugin;

import de.ng.skypvp.command.SpawnCommand;
import de.ng.skypvp.database.Storage;
import de.ng.skypvp.database.StorageCache;
import de.ng.skypvp.inventory.KitChooseInventory;
import de.ng.skypvp.listener.BlockListener;
import de.ng.skypvp.listener.ConnectingListener;
import de.ng.skypvp.listener.DamageListener;
import de.ng.skypvp.listener.FoodListener;
import de.ng.skypvp.listener.InteractListener;
import de.ng.skypvp.listener.InventoryListener;
import de.ng.skypvp.listener.ItemListener;
import de.ng.skypvp.listener.MoveListener;
import de.ng.skypvp.updater.Updater;

public class SkyPvP extends JavaPlugin {
	
	public static final String PREFIX = "§8[§7Sky§4P§7v§4P§8] §7";
	public static final Integer SPAWNRANGE = 25;
	
	public static SkyPvP instance;
	
	public SkyPvP() {
		instance = this;
	}
	
	@Override
	public void onLoad() {
		new Storage();
	}
	
	@Override
	public void onEnable() {
		new Storage();
		new Updater();
		
		KitChooseInventory.init();
		
		registerListener();
		registerCommands();
	}
	
	@Override
	public void onDisable() {
		StorageCache.shutdown();
		Storage.instance.shutdown();
	}
	
	private void registerListener() {
		getServer().getPluginManager().registerEvents(new InteractListener(), this);
		getServer().getPluginManager().registerEvents(new ConnectingListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new MoveListener(), this);
		getServer().getPluginManager().registerEvents(new DamageListener(), this);
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
		getServer().getPluginManager().registerEvents(new FoodListener(), this);
		getServer().getPluginManager().registerEvents(new ItemListener(), this);
	}
	
	private void registerCommands() {
		getCommand("spawn").setExecutor(new SpawnCommand());
	}
}