package de.ng.skypvp.updater;

import de.ng.skypvp.SkyPvP;
import de.ng.skypvp.event.UpdateEvent;

public class Updater implements Runnable {
	
	public Updater() {
		SkyPvP.instance.getServer().getScheduler().scheduleSyncRepeatingTask(SkyPvP.instance, this, 0L, 1L);
	}
	
	@Override
	public void run() {
		for(UpdateType updateType : UpdateType.values()) {
			if(updateType.isElapsed())
				SkyPvP.instance.getServer().getPluginManager().callEvent(new UpdateEvent(updateType));
		}
	}
}