package fr.samflix.vaniametrics.module.mvinventories;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.samflix.vaniametrics.api.VaniaMetrics;
import fr.samflix.vaniametrics.api.VaniaMetricsProvider;

/**
 * Inventory switch metrics.
 *
 * <p>As much a gameplay indicator as a load indicator: every switch reads and writes a profile to disk.
 */
public final class InventoriesPaper extends JavaPlugin {

	private InventoriesCollector collector;

	@Override
	public void onEnable() {
		VaniaMetrics metrics = VaniaMetricsProvider.get();
		collector = new InventoriesCollector();
		metrics.register(collector);
		Bukkit.getPluginManager().registerEvents(collector, this);
	}

	@Override
	public void onDisable() {
		if (collector != null) {
			VaniaMetricsProvider.find().ifPresent(m -> m.unregister(collector));
		}
	}
}
