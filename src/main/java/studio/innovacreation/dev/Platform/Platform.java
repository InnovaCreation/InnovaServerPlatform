package studio.innovacreation.dev.Platform;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public final class Platform extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("Innova Platform Init");
        WL.father = this;
        WL.startUpdateThread();

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Innova Platform disabled");
        WL.endUpdateThread();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("PullWL")) {
            WL.commandPull();
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("wb")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can open workbench.");
                return false;
            }

            ((Player) sender).openWorkbench(((Player) sender).getLocation(), true);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        getLogger().info( event.getPlayer().getName());
    }
}
