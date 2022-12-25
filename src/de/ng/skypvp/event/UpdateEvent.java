package de.ng.skypvp.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.ng.skypvp.updater.UpdateType;

public class UpdateEvent extends Event {
	
	private static final HandlerList handerls = new HandlerList();
	private UpdateType type;
	
	public UpdateEvent(UpdateType type) {
		this.type = type;
	}
	
	public UpdateType getType() {
		return type;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handerls;
	}
	
	public static HandlerList getHandlerList() {
		return handerls;
	}
}