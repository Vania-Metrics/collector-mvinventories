package fr.samflix.vaniametrics.module.mvinventories;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.mvplugins.multiverse.inventories.event.GameModeChangeShareHandlingEvent;
import org.mvplugins.multiverse.inventories.event.WorldChangeShareHandlingEvent;

import fr.samflix.vaniametrics.api.Collector;
import fr.samflix.vaniametrics.api.Counter;
import fr.samflix.vaniametrics.api.MetricRegistry;

/**
 * Multiverse-Inventories — inventory switches.
 *
 * <p>What this actually measures: every switch reads and writes a player profile to
 * disk. This counter is therefore as much a gameplay indicator — how often players
 * change worlds — as a load indicator: a burst of switches explains I/O that no
 * other metric would attribute.
 *
 * <p>{@code cause} distinguishes the two origins: a world change, or a game mode
 * change when the configuration separates inventories by mode.
 */
public final class InventoriesCollector implements Collector, Listener {

	private Counter switches;

	@Override
	public String name() {
		return "inventory";
	}

	@Override
	public String source() {
		return "Multiverse-Inventories";
	}

	@Override
	public void declare(MetricRegistry r) {
		switches = r.counter("inventory_switches_total",
				"Inventory switches. cause = world|gamemode. Each one reads and writes a "
						+ "profile to disk: it's also a load indicator.",
				"cause");
	}

	@Override
	public void collect(MetricRegistry r) {
		// Everything is counted in the listeners.
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWorld(WorldChangeShareHandlingEvent e) {
		switches.inc("world");
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onGameMode(GameModeChangeShareHandlingEvent e) {
		switches.inc("gamemode");
	}
}
