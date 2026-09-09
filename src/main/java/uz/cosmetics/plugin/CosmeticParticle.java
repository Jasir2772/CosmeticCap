package uz.cosmetics.plugin;

import org.bukkit.Material;
import org.bukkit.Particle;

public enum CosmeticParticle {

    NONE("O'chirish", Material.BARRIER, null),
    HEART("Yurak", Material.RED_DYE, Particle.HEART),
    FLAME("Olov", Material.BLAZE_POWDER, Particle.FLAME),
    SNOW("Qor", Material.SNOWBALL, Particle.SNOWFLAKE),
    PORTAL("Portal", Material.ENDER_PEARL, Particle.PORTAL),
    CLOUD("Bulut", Material.WHITE_WOOL, Particle.CLOUD),
    WITCH("Sehr", Material.GLOWSTONE_DUST, Particle.SPELL_WITCH),
    CRIT("Yulduzcha", Material.NETHER_STAR, Particle.CRIT),
    NOTE("Musiqa notasi", Material.NOTE_BLOCK, Particle.NOTE),
    SOUL("Jon", Material.SOUL_SAND, Particle.SOUL),
    DRIP_LAVA("Lava tomchisi", Material.MAGMA_CREAM, Particle.DRIP_LAVA),
    END_ROD("Yulduz chang", Material.END_ROD, Particle.END_ROD),
    HAPPY_VILLAGER("Sparkle", Material.EMERALD, Particle.VILLAGER_HAPPY);

    private final String displayName;
    private final Material icon;
    private final Particle particle;

    CosmeticParticle(String displayName, Material icon, Particle particle) {
        this.displayName = displayName;
        this.icon = icon;
        this.particle = particle;
    }

    public String getDisplayName() {
        return displayName;
    }
