package uz.cosmetics.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CosmeticMenus {

    public static final String MAIN_TITLE = "§8§lCosmetics Menu";
    public static final String PARTICLE_TITLE = "§8§lZarrachalar";
    public static final String HAT_TITLE = "§8§lShlyapalar";

    public static Inventory createMainMenu() {
        Inventory inv = Bukkit.createInventory(null, 27, MAIN_TITLE);

        inv.setItem(11, namedItem(Material.BLAZE_POWDER, "§eZarracha effektlari",
                "§7Yurganingizda chiqadigan", "§7zarrachalarni tanlang"));

        inv.setItem(15, namedItem(Material.CARVED_PUMPKIN, "§eShlyapalar",
                "§7Boshingizga taqadigan", "§7dekorativ narsalarni tanlang"));

        fillBorder(inv);
        return inv;
    }

    public static Inventory createParticleMenu(CosmeticParticle selected) {
        Inventory inv = Bukkit.createInventory(null, 45, PARTICLE_TITLE);

        int slot = 10;
        for (CosmeticParticle cp : CosmeticParticle.values()) {
            boolean isSelected = cp == selected;
            String prefix = isSelected ? "§a✔ " : "§f";
            ItemStack item = namedItem(cp.getIcon(), prefix + cp.getDisplayName(),
                    isSelected ? "§7Hozir tanlangan" : "§7Bosing va tanlang");
            inv.setItem(slot, item);
            slot++;
            if (slot % 9 == 8) slot += 2; // border chetlariga tegmaslik
        }

        inv.setItem(40, namedItem(Material.ARROW, "§cOrqaga"));
        fillBorder(inv);
        return inv;
    }

    public static Inventory createHatMenu(CosmeticHat selected) {
        Inventory inv = Bukkit.createInventory(null, 45, HAT_TITLE);

        int slot = 10;
        for (CosmeticHat hat : CosmeticHat.values()) {
            boolean isSelected = hat == selected;
            String prefix = isSelected ? "§a✔ " : "§f";
            ItemStack item = namedItem(hat.getMaterial(), prefix + hat.getDisplayName(),
                    isSelected ? "§7Hozir tanlangan" : "§7Bosing va tanlang");
            inv.setItem(slot, item);
            slot++;
            if (slot % 9 == 8) slot += 2;
        }

        inv.setItem(40, namedItem(Material.ARROW, "§cOrqaga"));
        fillBorder(inv);
        return inv;
    }

    private static void fillBorder(Inventory inv) {
        ItemStack filler = namedItem(Material.GRAY_STAINED_GLASS_PANE, " ");
        int size = inv.getSize();
        for (int i = 0; i < size; i++) {
            int row = i / 9;
            int col = i % 9;
            if (row == 0 || row == (size / 9) - 1 || col == 0 || col == 8) {
                if (inv.getItem(i) == null) {
                    inv.setItem(i, filler);
                }
            }
        }
    }

    private static ItemStack namedItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            if (lore.length > 0) {
                meta.setLore(java.util.Arrays.asList(lore));
            }
            item.setItemMeta(meta);
        }
        return item;
    }
}
