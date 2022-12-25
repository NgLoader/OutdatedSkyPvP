package de.ng.skypvp.kit;

import org.bukkit.Material;

public enum KitType {
	
	Survival(0, "§aSurvival", Material.WOOD_PICKAXE, (byte)0, "Test1234"),
	EnderMan(1, "§6Enderman", Material.ENDER_PEARL, (byte)0, "Kann sich teleportieren oder so");
	
	private final int id;
	private final String displayName;
	private final Material displayMaterial;
	private final byte displayMaterialByte;
	private final String[] lore;
	
	private KitType(int id, String displayName, Material displayMaterial, byte displayMaterialByte, String... lore) {
		this.id = id;
		this.displayName = displayName;
		this.displayMaterial = displayMaterial;
		this.displayMaterialByte = displayMaterialByte;
		this.lore = lore;
	}
	
	public Material getDisplayMaterial() {
		return displayMaterial;
	}
	
	public byte getDisplayMaterialByte() {
		return displayMaterialByte;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String[] getLore() {
		return lore;
	}
	
	public int getId() {
		return id;
	}
	
	public static KitType findById(int id) {
		for(KitType kit : values())
			if(kit.getId() == id)
				return kit;
		return KitType.Survival;
	}
}