package uz.cosmetics.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CosmeticsCommand implements CommandExecutor {

    private final CosmeticsPlugin plugin;
    private final CosmeticManager manager;

    public CosmeticsCommand(CosmeticsPlugin plugin, CosmeticManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cBu komandani faqat o'yinchi ishlatishi mumkin.");
            return true;
        }

        if (!player.hasPermission("cosmetics.use")) {
            player.sendMessage("§cSizda bu buyruqdan foydalanish uchun ruxsat yo'q.");
            return true;
        }

        player.openInventory(CosmeticMenus.createMainMenu());
        return true;
    }
}
