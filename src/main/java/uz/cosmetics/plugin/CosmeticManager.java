package uz.cosmetics.plugin;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CosmeticManager {

    private final CosmeticsPlugin plugin;

    // Har bir o'yinchining tanlagan zarrachasi
    private final Map<UUID, CosmeticParticle> playerParticles = new HashMap<>();
    // Har bir o'yinchining tanlagan shlyapasi (asl helmet itemini eslab qolish uchun)
    private final Map<UUID, ItemStack> savedHelmets = new HashMap<>();
    private final Map<UUID, CosmeticHat> playerHats = new HashMap<>();

    private BukkitTask particleTask;
    private final NamespacedKey menuItemKey;

    public CosmeticManager(CosmeticsPlugin plugin) {
        this.plugin = plugin;
        this.menuItemKey = new NamespacedKey(plugin, "cosmetics_menu_item");
    }

    // ---------- ZARRACHALAR ----------

    public void setParticle(Player player, CosmeticParticle particle) {
        if (particle == CosmeticParticle.NONE) {
            playerParticles.remove(player.getUniqueId());
        } else {
            playerParticles.put(player.getUniqueId(), particle);
        }
    }

    public CosmeticParticle getParticle(Player player) {
        return playerParticles.getOrDefault(player.getUniqueId(), CosmeticParticle.NONE);
    }

    public void startParticleTask() {
        int interval = plugin.getConfig().getInt("particle-update-interval", 5);
        int count = plugin.getConfig().getInt("particle-count", 3);
        boolean onlyMoving = plugin.getConfig().getBoolean("particles-only-when-moving", false);

        particleTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Map.Entry<UUID, CosmeticParticle> entry : playerParticles.entrySet()) {
                Player player = Bukkit.getPlayer(entry.getKey());
                if (player == null || !player.isOnline()) continue;

                CosmeticParticle cp = entry.getValue();
                if (cp.getParticle() == null) continue;

                if (onlyMoving && player.getVelocity().lengthSquared() < 0.001) continue;

                player.getWorld().spawnParticle(
                        cp.getParticle(),
                        player.getLocation().add(0, 1, 0),
                        count, 0.3, 0.3, 0.3, 0.01
                );
            }
        }, 0L, interval);
    }

    public void stopParticleTask() {
        if (particleTask != null) {
            particleTask.cancel();
        }
    }

    // ---------- SHLYAPALAR ----------

    public void setHat(Player player, CosmeticHat hat) {
        UUID uuid = player.getUniqueId();

        // Avval eski holatni tiklaymiz
        if (savedHelmets.containsKey(uuid)) {
            player.getInventory().setHelmet(savedHelmets.get(uuid));
            savedHelmets.remove(uuid);
        }

        if (hat == CosmeticHat.NONE) {
            playerHats.remove(uuid);
            return;
        }

        // Asl helmetni saqlab qo'yamiz (bo'sh bo'lsa ham null saqlanadi)
        savedHelmets.put(uuid, player.getInventory().getHelmet());

        ItemStack hatItem = new ItemStack(hat.getMaterial());
        ItemMeta meta = hatItem.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§d" + hat.getDisplayName());
            meta.getPersistentDataContainer().set(menuItemKey, PersistentDataType.BYTE, (byte) 1);
            hatItem.setItemMeta(meta);
        }

        player.getInventory().setHelmet(hatItem);
        playerHats.put(uuid, hat);
    }

    public CosmeticHat getHat(Player player) {
        return playerHats.getOrDefault(player.getUniqueId(), CosmeticHat.NONE);
    }

    public void handleQuit(Player player) {
        // O'yinchi chiqib ketganda haqiqiy shlemini tiklaymiz, hotira tozalanadi
        UUID uuid = player.getUniqueId();
        if (savedHelmets.containsKey(uuid)) {
            player.getInventory().setHelmet(savedHelmets.get(uuid));
        }
        savedHelmets.remove(uuid);
        playerHats.remove(uuid);
        playerParticles.remove(uuid);
    }

    public NamespacedKey getMenuItemKey() {
        return menuItemKey;
    }
  }
