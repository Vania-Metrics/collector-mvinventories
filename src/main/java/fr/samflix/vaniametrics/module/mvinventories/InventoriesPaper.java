package fr.samflix.vaniametrics.module.mvinventories;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.samflix.vaniametrics.api.VaniaMetrics;
import fr.samflix.vaniametrics.api.VaniaMetricsProvider;

/**
 * Métriques de bascule d'inventaire.
 *
 * <p>Autant un indicateur de jeu qu'un indicateur de charge : chaque bascule lit et écrit un profil sur le disque.
 */
public final class InventoriesPaper extends JavaPlugin {

	private InventoriesCollector collecteur;

	@Override
	public void onEnable() {
		VaniaMetrics metriques = VaniaMetricsProvider.get();
		collecteur = new InventoriesCollector();
		metriques.enregistrer(collecteur);
		Bukkit.getPluginManager().registerEvents(collecteur, this);
	}

	@Override
	public void onDisable() {
		if (collecteur != null) {
			VaniaMetricsProvider.chercher().ifPresent(m -> m.retirer(collecteur));
		}
	}
}
