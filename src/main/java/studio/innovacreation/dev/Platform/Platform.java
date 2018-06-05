package studio.innovacreation.dev.Platform;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public final class Platform extends JavaPlugin {

  @Override
  public void onEnable() {
    getLogger().info("Innova Platform Init");
    WL.father = this;
    WL.startUpdateThread();
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
    return false;
  }
}
