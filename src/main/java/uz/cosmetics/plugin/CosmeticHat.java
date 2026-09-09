package uz.cosmetics.plugin;

import org.bukkit.Material;

public enum CosmeticHat {

    NONE("Yechish", Material.BARRIER),
    PUMPKIN("Qovoq", Material.CARVED_PUMPKIN),
    PLAYER_HEAD("O'zining boshi", Material.PLAYER_HEAD),
    DIAMOND_BLOCK("Olmos blok", Material.DIAMOND_BLOCK),
    GOLD_BLOCK("Oltin blok", Material.GOLD_BLOCK),
    EMERALD_BLOCK("Zumrad blok", Material.EMERALD_BLOCK),
    TNT("TNT", Material.TNT),
    JACK_O_LANTERN("Fonarcha", Material.JACK_O_LANTERN),
    BEACON("Mayoq", Material.BEACON),
    CAKE("Tort", Material.CAKE),
    DRAGON_EGG("Ajdaho tuxumi", Material.DRAGON_EGG),
    SKELETON_SKULL("Skelet bosh suyagi", Material.SKELETON_SKULL),
    CRAFTING_TABLE("Verstak", Material.CRAFTING_TABLE);

    private final String displayName;
    private final Material material;

    CosmeticHat(String displayName, Material material) {
        this.displayName = displayName;
        this.material = material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterial() {
        return material;
    }
}
