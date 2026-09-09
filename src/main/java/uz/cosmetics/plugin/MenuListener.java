package uz.cosmetics.plugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuListener implements Listener {

    private final CosmeticsPlugin plugin;
    private final CosmeticManager manager;

    public MenuListener(CosmeticsPlugin plugin, CosmeticManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (!title.equals(CosmeticMenus.MAIN_TITLE)
                && !title.equals(CosmeticMenus.PARTICLE_TITLE)
                && !title.equals(CosmeticMenus.HAT_TITLE)) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType().isAir()) return;

        ItemMeta meta = clicked.getItemMeta();
        String displayName = (meta != null && meta.hasDisplayName()) ? meta.getDisplayName() : "";

        switch (title) {
            case CosmeticMenus.MAIN_TITLE -> handleMainMenu(player, displayName);
            case CosmeticMenus.PARTICLE_TITLE -> handleParticleMenu(player, clicked, displayName);
            case CosmeticMenus.HAT_TITLE -> handleHatMenu(player, clicked, displayName);
        }
    }

    private void handleMainMenu(Player player, String displayName) {
        if (displayName.contains("Zarracha effektlari")) {
            player.openInventory(CosmeticMenus.createParticleMenu(manager.getParticle(player)));
        } else if (displayName.contains("Shlyapalar")) {
            player.openInventory(CosmeticMenus.createHatMenu(manager.getHat(player)));
        }
    }

    private void handleParticleMenu(Player player, ItemStack clicked, String displayName) {
        if (displayName.contains("Orqaga")) {
            player.openInventory(CosmeticMenus.createMainMenu());
            return;
        }

        for (CosmeticParticle cp : CosmeticParticle.values()) {
            if (clicked.getType() == cp.getIcon()) {
                manager.setParticle(player, cp);
                player.sendMessage("§a✔ Zarracha effekti o'rnatildi: §e" + cp.getDisplayName());
                player.openInventory(CosmeticMenus.createParticleMenu(cp));
                return;
            }
        }
    }

    private void handleHatMenu(Player player, ItemStack clicked, String displayName) {
        if (displayName.contains("Orqaga")) {
            player.openInventory(CosmeticMenus.createMainMenu());
            return;
        }

        for (CosmeticHat hat : CosmeticHat.values()) {
            if (clicked.getType() == hat.getMaterial()) {
                manager.setHat(player, hat);
                player.sendMessage("§a✔ Shlyapa o'rnatildi: §e" + hat.getDisplayName());
                player.closeInventory();
                return;
            }
        }
    }
                  }
