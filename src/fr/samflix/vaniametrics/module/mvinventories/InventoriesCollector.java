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
 * Multiverse-Inventories — les bascules d'inventaire.
 *
 * <p>CE QUE ÇA MESURE VRAIMENT : chaque bascule LIT et ÉCRIT un profil de joueur sur le disque. Ce
 * compteur est donc autant un indicateur de jeu — combien de fois on change de monde — qu'un
 * indicateur de charge : une rafale de bascules explique des entrées-sorties qu'aucune autre
 * métrique n'attribuerait.
 *
 * <p>{@code cause} distingue les deux origines : un changement de MONDE, ou un changement de MODE
 * DE JEU quand la configuration sépare les inventaires par mode.
 */
public final class InventoriesCollector implements Collector, Listener {

	private Counter bascules;

	@Override
	public String nom() {
		return "inventory";
	}

	@Override
	public String origine() {
		return "Multiverse-Inventories";
	}

	@Override
	public void declarer(MetricRegistry r) {
		bascules = r.counter("inventory_switches_total",
				"Bascules d'inventaire. cause = world|gamemode. Chacune lit et écrit un profil "
						+ "sur le disque : c'est aussi un indicateur de charge.",
				"cause");
	}

	@Override
	public void relever(MetricRegistry r) {
		// Tout est compté dans les écouteurs.
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onMonde(WorldChangeShareHandlingEvent e) {
		bascules.inc("world");
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onMode(GameModeChangeShareHandlingEvent e) {
		bascules.inc("gamemode");
	}
}
