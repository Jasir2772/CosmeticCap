package uz.cosmetics.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class CosmeticsPlugin extends JavaPlugin {

    private static CosmeticsPlugin instance;
    private CosmeticManager cosmeticManager;
    private MenuListener menuListener;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.cosmeticManager = new CosmeticManager(this);
        this.menuListener = new MenuListener(this, cosmeticManager);

        // Komandalar
        getCommand("cosmetics").setExecutor(new CosmeticsCommand(this, cosmeticManager));

        // Eventlar
        getServer().getPluginManager().registerEvents(menuListener, this);
        getServer().getPluginManager().registerEvents(new PlayerListener(cosmeticManager), this);

        // Zarrachalarni doimiy chizadigan task
        cosmeticManager.startParticleTask();

        getLogger().info("CosmeticsPlus muvaffaqiyatli ishga tushdi!");
    }

    @Override
    public void onDisable() {
        if (cosmeticManager != null) {
            cosmeticManager.stopParticleTask();
        }
        getLogger().info("CosmeticsPlus o'chirildi.");
    }

    public static CosmeticsPlugin getInstance() {
        return instance;
    }
}
