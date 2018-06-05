package studio.innovacreation.dev.Platform;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VirtualToolbox  implements CommandExecutor {
    private final Platform plugin;

    public VirtualToolbox(Platform plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // 工作台
        if (cmd.getName().equalsIgnoreCase("wb")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can open workbench.");
                return false;
            }

            ((Player) sender).openWorkbench(((Player) sender).getLocation(), true);
            return true;
        }
        // 附魔台
        if (cmd.getName().equalsIgnoreCase("enchantment")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can open enchantment table.");
                return false;
            }

            ((Player) sender).openEnchanting(((Player) sender).getLocation(), true);
            return true;
        }
        return false;
    }
}